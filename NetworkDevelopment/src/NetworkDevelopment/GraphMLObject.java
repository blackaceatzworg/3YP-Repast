package NetworkDevelopment;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
	
	@XmlElement(name="key", type = XMLNodeKey.class)
	ArrayList<XMLNodeKey> attrKeys = new ArrayList<XMLNodeKey>();
	
	@XmlElement(name = "node", type = XMLNode.class)
	ArrayList<XMLNode> nodes = new ArrayList<XMLNode>();
	
	@XmlElement(name = "edge", type = Edge.class)
	ArrayList<Edge> edges = new ArrayList<Edge>();
	

	GraphMLObject()
	{
		this.id = null;
		this.attrFor = null;
		this.attrName = null;
		this.attrWeight = null;
		this.attrType = null;
		this.edgedefault = "directed";
	}
	
	GraphMLObject(String id, String edgedefault, HashMap<String, Object> attrList)
	{
		this.id = id;
		this.edgedefault = edgedefault;
		//Based on code from http://stackoverflow.com/questions/1066589/java-iterate-through-hashmap
		Iterator iter = attrList.entrySet().iterator();
		while(iter.hasNext())
		{
			Map.Entry pair = (Map.Entry)iter.next();
			
		}
	}
	
	public void addEdge(String id, String source, String target)
	{
		edges.add(new Edge(id, source, target));
	}
	public void addEdge(String id, String source, String target, boolean directed)
	{
		edges.add(new Edge(id, source, target, directed));
	}
	
	public void addXMLNode(String id)
	{
		nodes.add(new XMLNode(id));
	}
	
	public void addXMLNode(String id, HashMap<String, Object> attrList)
	{
		nodes.add(new XMLNode(id));
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
		this.directed = true;
		
	}
	Edge(String id, String source, String target)
	{
		this.id = id;
		this.source = source;
		this.target = target;
		this.directed = true;
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
class XMLNode
{
	@XmlAttribute
	String id;
	ArrayList<XMLNodeAttribute> data = new ArrayList<XMLNodeAttribute>();
	
	XMLNode()
	{
		this.id = null;
		
	}
	XMLNode(String id)
	{
		this.id = id;
	}
	XMLNode(String id, double xCoord, double yCoord)
	{
		this.id = id;
		data.add(new XMLNodeAttribute("x", xCoord));
		data.add(new XMLNodeAttribute("y", yCoord));
	}
}

@XmlRootElement(name="data")
class XMLNodeAttribute
{
	@XmlAttribute
	String key;
	@XmlElement
	Object value;
	
	XMLNodeAttribute(String key, Object value)
	{
		this.key = key;
		this.value = value;
	}
}

class XMLKey
{
	@XmlAttribute
	String key;
	@XmlElement(name="default")
	String defaultString;
	@XmlAttribute(name="attr.name")
	String attrName;
	@XmlAttribute(name="attr.type")
	String attrType;
	
	XMLKey(String key, String attrName, String attrType)
	{
		this.key = key;
		this.attrName = attrName;
		this.attrType = attrType;
	}
	
	XMLKey(String key, String attrName, String attrType, String defaultString)
	{
		this.key = key;
		this.attrName = attrName;
		this.attrType = attrType;
		this.defaultString = defaultString;
	}
		
	public String getKey()
	{
		return this.key;
	}
}

@XmlRootElement(name="key")
class XMLNodeKey extends XMLKey
{
	@XmlAttribute(name="for")
	String forAttr = "node";
	
	XMLNodeKey(String key, String attrName, String attrType)
	{
		super(key, attrName, attrType);
	}
	
	XMLNodeKey(String key, String attrName, String attrType, String defaultString)
	{
		super(key, attrName, attrType, defaultString);
	}
}