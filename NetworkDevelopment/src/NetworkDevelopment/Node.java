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
		System.out.println("WORKING ON NODE " + id);
		ArrayList<ArrayList<Node>> newEdges = new ArrayList<ArrayList<Node>>();
		for(Node current : nodes)
		{
			System.out.println("Checking " + id + " against " + current.id);
			double iDegree = network.getDegree(current);
			double jSumDegree = 0;
			for(Node n : network.getNodes())
			{
				jSumDegree += network.getDegree(n);
				//System.out.println("NODE " + n.id + " has degree " +network.getDegree(n) );
			}
			
			double prob = iDegree / jSumDegree;
			System.out.println("PROB: " + prob + " SUM-J " + jSumDegree + " I " + iDegree);
			if(Math.random() <= prob)
			{
				/*network.addEdge(this, current);*/
				ArrayList<Node> pair = new ArrayList<Node>();
				pair.add(this);
				pair.add(current);
				newEdges.add(pair);
				System.out.println("\tAdding edge");
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
	
	@ScheduledMethod(start = 1, interval = 10)
	public void step() {
			
		double changeChance = Math.random();
		if(changeChance < 0.6 && network != null)
		{
			Iterable<RepastEdge<Node>> edges = network.getEdges(this);
			boolean removed = false;
			Node removeNode = null;
			for(RepastEdge<?> e : edges)
			{
				if(Math.random() < 0.2 && !removed)
				{
					Node src = (Node) e.getSource();
					if(src.id == this.id )
						removeNode = (Node) e.getTarget();
					else
						removeNode = (Node) e.getSource();
					removed = true;
					
				}
			}
			RepastEdge<Node> e = new RepastEdge<Node>(this, removeNode, true);
			if(removeNode != null && network.containsEdge(e))
			{
				System.out.println("Removing edge between " + this.id + " and " + removeNode.id );
				
				network.removeEdge(e);
			}
			
			//Now add a random edge.
			boolean added = false;
			Iterable<Node> nodes = network.getNodes();
			for(Node n: nodes)
			{
				if(!added)
				{
					if(Math.random() < 0.2)
					{
						System.out.println("Adding edge between " + this.id + " and " + n.id);
						network.addEdge(this, n);
						added = true;
					}
				}
			}
		}
		System.out.println("FINISHED WORKING ON " + this.id + "-------------------------");

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
