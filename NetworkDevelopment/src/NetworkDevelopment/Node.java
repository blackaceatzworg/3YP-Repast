package NetworkDevelopment;
import java.util.ArrayList;

import repast.simphony.context.Context;
import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.space.graph.Network;
import repast.simphony.space.graph.RepastEdge;


public class Node {
	String id;
	double weight;
	Context<?> context;
	Network<Node> network;
	
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
		
		Iterable<Node> nodes = network.getNodes();
		//System.out.println("WORKING ON NODE " + id);
		ArrayList<ArrayList<Node>> newEdges = new ArrayList<ArrayList<Node>>();
		for(Node current : nodes)
		{
			//System.out.println("Checking " + id + " against " + current.id);
			double iDegree = network.getDegree(current);
			double jSumDegree = 0;
			for(Node n : network.getNodes())
			{
				jSumDegree += network.getDegree(n);
				//System.out.println("NODE " + n.id + " has degree " +network.getDegree(n) );
			}
			
			double prob = iDegree / jSumDegree;
			//System.out.println("PROB: " + prob + " SUM-J " + jSumDegree + " I " + iDegree);
			if(Math.random() <= prob)
			{
				/*network.addEdge(this, current);*/
				ArrayList<Node> pair = new ArrayList<Node>();
				pair.add(this);
				pair.add(current);
				newEdges.add(pair);
				//System.out.println("\tAdding edge");
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
		Iterable<RepastEdge<Node>> edges = network.getEdges(this);
		boolean removed = false;
		RepastEdge rmEdge = null;
		double rmvRnd = Math.random();
		int rmCount = 0;
		int rmIndex = (int)Math.round(Math.random() * network.getDegree(this));
		
		if(rmvRnd < 0.3)
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
		if(rnd < 0.7)
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
	
}
