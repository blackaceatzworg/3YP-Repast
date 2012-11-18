package NetworkDevelopment;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import repast.simphony.context.Context;
import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.space.graph.Network;
import repast.simphony.space.graph.RepastEdge;

public class Node implements Comparable{
	String id;
	private double weight;
	private Context<?> context;
	private Network<Node> network;
	
	
	private boolean smokes;
	private double peerPressure;
	private double willPower;
	ArrayList<DataAttribute> attributeList;
	
	Node(String id)
	{
		this.id = id;
		this.weight = 1;
	}
	
	Node(String id, Context<?> context, Network<Node> network)
	{
		this.id = id;
		this.weight = 1;
		this.network = network;
		this.context = context;
		
		if(Math.random() < 0.5)
			this.smokes = true;
		else
			this.smokes = false;
		
		this.peerPressure = Math.random();
		this.willPower = Math.random();
		DecimalFormat df = new DecimalFormat("#.00");
		
		this.id = this.id + " " + df.format(this.peerPressure);
		
		//Add to scale-free network
		Iterable<Node> nodes = network.getNodes();

		ArrayList<ArrayList<Node>> newEdges = new ArrayList<ArrayList<Node>>();
		for(Node current : nodes)
		{
			double iDegree = network.getDegree(current);
			double jSumDegree = 0;
			for(Node n : network.getNodes())
			{
				jSumDegree += network.getDegree(n);
			}
			
			double prob = iDegree / jSumDegree;
			/*if(this.isSmoker() != current.isSmoker())
				prob = prob * 0.5;*/
			
			if(Math.random() <= prob)
			{
				ArrayList<Node> pair = new ArrayList<Node>();
				pair.add(this);
				pair.add(current);
				newEdges.add(pair);
				
				ArrayList<Node> rPair = new ArrayList<Node>();
				rPair.add(current);
				rPair.add(this);
				newEdges.add(rPair);
			}
		}
		
		for(ArrayList<Node> pair : newEdges)
		{
			network.addEdge(pair.get(0), pair.get(1));
		}
		
	}
	
	Node(String id, double weight)
	{
		this.id = id;
		this.weight = weight;
	}
	
	@ScheduledMethod(start = 1, interval = 100)
	public void step() {
			

		//System.out.println("Removing an edge from " + this.id + "..."); 
		Iterable<RepastEdge<Node>> edges = network.getInEdges(this);
		boolean removed = false;
		RepastEdge rmEdge = null;
		//Check connected nodes - if smoker, add to count
		int localSmokerCount = 0;
		int localNonSmokerCount = 0;
		
		for(RepastEdge<?> e : edges)
		{
			Node src = (Node) e.getSource();
		
			
			if(src.smokes)
				localSmokerCount++;
			else
				localNonSmokerCount++;
		}
		
		//work out the net smokers
		//normalise to 0-1 (0.5 being net 0 smokers)
		if((localSmokerCount + localNonSmokerCount) > 0)
		{
			double normalisedDiff = Math.abs(localSmokerCount - localNonSmokerCount) / (localSmokerCount + localNonSmokerCount);
			double smokingChance = this.peerPressure * normalisedDiff;
			
			DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
			Date date = new Date();
			
			if(localSmokerCount > localNonSmokerCount)
			{
				if(!this.smokes)
				{
					if(Math.random() < smokingChance)
					{
						this.smokes = true;
						System.out.println(dateFormat.format(date) + " " + this.getID() + " just started smoking!");
					}
					else
						this.smokes = false;
				}
			}
			else if(localSmokerCount < localNonSmokerCount)
			{
				if(this.smokes)
				{
					//CHANGE THIS OLOLOLOL
					if(Math.random() < smokingChance * willPower)
					{
						this.smokes = false;
						
						System.out.println(dateFormat.format(date) + " " + this.getID() + " just gave up smoking!");
					}
					else
						this.smokes = true;
				}
			}
			
		}
		
		//Now pick a random node and consider adding an edge
		Node newNode = network.getRandomAdjacent(this);
		//double startProb = 
		

	}
	
	public boolean isSmoker()
	{
		return this.smokes;
	}
	
	public String getID()
	{
		return this.id;
	}
	public void setID(String id)
	{
		this.id = id;
	}
	
	private void randomChange() 
	{
		//System.out.println("Removing an edge from " + this.id + "..."); 
		Iterable<RepastEdge<Node>> edges = network.getEdges(this);
		boolean removed = false;
		RepastEdge rmEdge = null;
		double rmvRnd = Math.random();
		int rmCount = 0;
		int rmIndex = (int)Math.round(Math.random() * network.getDegree(this));
		
		if(rmvRnd < 0.7)
		{
			//System.out.println("REMOVING INDEX " + rmIndex + " from node " + this.id);
			for(RepastEdge<?> e : edges)
			{
				if(rmCount == rmIndex)
				{
					Node src = (Node) e.getSource();
					if(src.id == this.id )
						rmEdge = e;
					else
						rmEdge = e;
				}
				rmCount++;
			}
			//System.out.println("e is an edge between " + this.id + " and " + removeNode.id);
			if(rmEdge != null) 
			{
				
				/*System.out.println("Removing edge between " + this.id + " and " + removeNode.id );
				System.out.println(network.containsEdge(eSrc) + " " +network.containsEdge(eDest));*/
				if( network.containsEdge(rmEdge))
				{
					//System.out.println("Removing");
					network.removeEdge(rmEdge);
				}
			}
		}
		
		//Now add a random edge.
		boolean added = false;
		Iterable<Node> nodes = network.getNodes();
		double rnd = Math.random();
		int counter = 0;
		int index = (int)Math.round(Math.random() * network.getDegree(this));
		if(rnd < 0.3)
		{
			for(Node n: nodes)
			{
				if(counter == index)
				{
					//System.out.println("Adding edge between " + this.id + " and " + n.id);
					network.addEdge(this, n);
					added = true;
				}
				counter++;
			}
		}
		
		//System.out.println("Number of Edges in graph: " + network.numEdges());
	}
	
	public void setAttributeList(ArrayList<DataAttribute> list)
	{
		this.attributeList = list;
	}
	
	public Object findAttribute(String key)
	{
		for(DataAttribute d: attributeList)
		{
			if(d.getKey() == key)
				return d.getValue();
		}
		return false;
	}
	
	public boolean addAttribute(String key, Object value)
	{
		for(DataAttribute d: attributeList)
		{
			if(d.getKey() == key)
				return false;
		}
		attributeList.add(new DataAttribute(key, value));
		return true;
	}
	
	public boolean changeAttribute(String key, Object value)
	{
		boolean changed = false;
		for(DataAttribute d: attributeList)
		{
			if(d.getKey() == key)
			{
				d.setKey(key);
				changed = true;
			}
		}
		return changed;
	}
	
	public boolean removeAttribute(String key)
	{
		int delIndex = -1, trackIndex = 0;
		for(DataAttribute d: attributeList)
		{
			if(d.getKey() == key)
				delIndex = trackIndex;
			
			trackIndex++;
		}
		if(delIndex >= 0)
		{
			attributeList.remove(delIndex);
			return true;
		}
		else
			return false;
	}

	@Override
	public int compareTo(Object arg0) {
		// TODO Auto-generated method stub
		Node other = (Node) arg0;
		return this.id.compareTo(other.getID());
	}
	
}
