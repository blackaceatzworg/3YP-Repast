package NetworkDevelopment;
import java.util.ArrayList;

import repast.simphony.context.Context;
import repast.simphony.space.graph.Network;


public class Node {
	String id;
	double weight;
	Context context;
	Network network;
	
	ArrayList<DataAttribute> attributeList;
	
	Node(String id)
	{
		this.id = id;
		this.weight = 1;
	}
	
	Node(String id, Context context, Network<Node> network)
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
