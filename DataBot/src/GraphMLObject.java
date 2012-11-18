import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "graph")
public class GraphMLObject {
	@XmlAttribute
	String id;
	
	@XmlAttribute(name="for")
	String attrFor;
	
	@XmlAttribute(name="attr.name")
	String attrName;
	
	@XmlAttribute(name="attr.weight")
	String attrWeight;
	
	@XmlAttribute(name="attr.type")
	String attrType;
	
	@XmlAttribute
	String edgedefault;
	
	@XmlElement(name = "node", type = Node.class)
	ArrayList<Node> nodes = new ArrayList<Node>();
	
	@XmlElement(name = "edge", type = Edge.class)
	ArrayList<Edge> edges = new ArrayList<Edge>();
	

	GraphMLObject()
	{
		this.id = null;
		this.attrFor = null;
		this.attrName = null;
		this.attrWeight = null;
		this.attrType = null;
		this.edgedefault = "undirected";
	}
	
	GraphMLObject(String id, String attrFor, String attrName, String attrWeight, String attrType, String edgedefault)
	{
		this.id = id;
		this.attrFor = attrFor;
		this.attrName = attrName;
		this.attrWeight = attrWeight;
		this.attrType = attrType;
		this.edgedefault = edgedefault;
	}
	
	public void addEdge(String id, String source, String target)
	{
		edges.add(new Edge(id, source, target));
	}
	public void addEdge(String id, String source, String target, boolean directed)
	{
		edges.add(new Edge(id, source, target, directed));
	}
	
	public void addNode(String id)
	{
		nodes.add(new Node(id));
	}
	
}


@XmlRootElement(name="edge")
class Edge 
{
	@XmlAttribute
	String id;
	@XmlAttribute
	String source;
	@XmlAttribute
	String target;
	@XmlAttribute
	boolean directed;
	
	Edge()
	{
		this.id = null;
		this.source = null;
		this.target = null;
		this.directed = false;
		
	}
	Edge(String id, String source, String target)
	{
		this.id = id;
		this.source = source;
		this.target = target;
		this.directed = false;
	}
	
	Edge(String id, String source, String target, boolean directed)
	{
		this.id = id;
		this.source = source;
		this.target = target;
		this.directed = directed;
	}
}

@XmlRootElement(name="node")
class Node
{
	@XmlAttribute
	String id;
	ArrayList<Attribute> data = new ArrayList<Attribute>();
	
	Node()
	{
		this.id = null;
		
	}
	Node(String id)
	{
		this.id = id;
	}
	Node(String id, double xCoord, double yCoord)
	{
		this.id = id;
		data.add(new Attribute("x", xCoord));
		data.add(new Attribute("y", yCoord));
	}
	
	
	
}

class Attribute
{
	@XmlAttribute
	String key;
	@XmlElement
	Object value;
	
	Attribute(String key, Object value)
	{
		this.key = key;
		this.value = value;
	}
}