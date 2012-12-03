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
	
	@XmlAttribute
	String edgedefault;
	
	//Stores full detail on keys 
	@XmlElement(name="key", type = XMLNodeKey.class)
	ArrayList<XMLNodeKey> nodeKeyRegister = new ArrayList<XMLNodeKey>();
	
	@XmlElement(name="key", type = XMLEdgeKey.class)
	ArrayList<XMLEdgeKey> edgeKeyRegister = new ArrayList<XMLEdgeKey>();
	
	@XmlElement(name = "node", type = XMLNode.class)
	ArrayList<XMLNode> nodes = new ArrayList<XMLNode>();
	
	@XmlElement(name = "edge", type = Edge.class)
	ArrayList<Edge> edges = new ArrayList<Edge>();
	

	GraphMLObject()
	{
		this.id = null;
		this.edgedefault = "directed";
	}
	
	GraphMLObject(String id, String edgedefault)
	{
		this.id = id;
		this.edgedefault = edgedefault;
	}
	
	/**
	 * @param attrName Attribute name
	 * @param attrType Attribute data type
	 * @param forNode Whether it is node attribute (if false, then edge is assumed)
	 * @param attrDefault Default value, can be null
	 * @return key to add future attributes by if successful, else null
	 */
	public String addAttributeKey(String attrName, String attrType, boolean forNode, String attrDefault)
	{
		String key = "";
		if(forNode) 
			key += "dn" + nodeKeyRegister.size(); 
		else 
			key += "de" + edgeKeyRegister.size();
		
		//check attribute doesn't exist already
		if(forNode)
		{
			for(XMLNodeKey x : nodeKeyRegister)
				if(x.attrName == attrName)
					return null;
			nodeKeyRegister.add(new XMLNodeKey(key, attrName, attrType, attrDefault));
		}
		else
		{
			for(XMLEdgeKey x : edgeKeyRegister)
				if(x.attrName == attrName)
					return null;
			edgeKeyRegister.add(new XMLEdgeKey(key, attrName, attrType, attrDefault));
		}

		return key;
	}
	
	public void addEdge(String id, String source, String target, HashMap<String, Object> attrs)
	{
		edges.add(new Edge(id, source, target, attrs));
	}
	public void addEdge(String id, String source, String target, boolean directed, HashMap<String, Object> attrs)
	{
		edges.add(new Edge(id, source, target, directed, attrs));
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

	ArrayList<XMLAttribute> attrList = new ArrayList<XMLAttribute>();
	
	Edge()
	{
		this.id = null;
		this.source = null;
		this.target = null;
		this.directed = true;
		
	}
	Edge(String id, String source, String target, HashMap<String, Object> attrs)
	{
		this.id = id;
		this.source = source;
		this.target = target;
		this.directed = true;
		
		Iterator iter = attrs.entrySet().iterator();
		while(iter.hasNext())
		{
			Map.Entry pair = (Map.Entry)iter.next();
			attrList.add(new XMLAttribute((String)pair.getKey(), pair.getValue()));
		}
	}
	
	Edge(String id, String source, String target, boolean directed, HashMap<String, Object> attrs)
	{
		this.id = id;
		this.source = source;
		this.target = target;
		this.directed = directed;
		Iterator iter = attrs.entrySet().iterator();
		while(iter.hasNext())
		{
			Map.Entry pair = (Map.Entry)iter.next();
			attrList.add(new XMLAttribute((String)pair.getKey(), pair.getValue()));
		}
	}
}

@XmlRootElement(name="node")
class XMLNode
{
	@XmlAttribute
	String id;
	ArrayList<XMLAttribute> data = new ArrayList<XMLAttribute>();
	
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
		data.add(new XMLAttribute("x", xCoord));
		data.add(new XMLAttribute("y", yCoord));
	}
}


@XmlRootElement(name="data")
class XMLAttribute
{
	@XmlAttribute
	String key;
	@XmlElement
	Object value;
	
	XMLAttribute(String key, Object value)
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
@XmlRootElement(name="key")
class XMLEdgeKey extends XMLKey
{
	@XmlAttribute(name="for")
	String forAttr = "edge";
	
	XMLEdgeKey(String key, String attrName, String attrType)
	{
		super(key, attrName, attrType);
	}
	
	XMLEdgeKey(String key, String attrName, String attrType, String defaultString)
	{
		super(key, attrName, attrType, defaultString);
	}
}