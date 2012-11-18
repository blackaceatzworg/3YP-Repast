import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.Hypergraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.graph.util.Pair;
import edu.uci.ics.jung.io.GraphMLWriter;

import org.apache.commons.collections15.Transformer;

public class GraphUtils {
	
	public static void writeToGraphML(Graph<Long, Long> g, String filename)
	{
		GraphMLObject template = new GraphMLObject();
		String headerString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
				"<graphml xmlns=\"http://graphml.graphdrawing.org/xmlns\"\n" +
				"xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
				"xsi:schemaLocation=\"http://graphml.graphdrawing.org/xmlns \n" +
				"http://graphml.graphdrawing.org/xmlns/1.0/graphml.xsd\">";
		
		boolean directed = false;
		ArrayList<Long> nodesToRemove = new ArrayList<Long>();
		for(Long l : g.getVertices())
		{
			template.addNode("n" + l.toString());
		}
		
		for(Long l : g.getEdges())
		{
			//For each edge, we need the end points as well
			if(g.getEdgeType(l) == EdgeType.DIRECTED)
				directed = true;
			Pair<Long> vPair = g.getEndpoints(l);
			if(g.containsVertex(vPair.getFirst()) && g.containsVertex(vPair.getSecond()))
				template.addEdge("e" + l.toString(), "n" + vPair.getFirst().toString(), "n" + vPair.getSecond().toString(), directed);
		}
		
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(GraphMLObject.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			      
			FileWriter fstream = new FileWriter(filename);
			BufferedWriter out = new BufferedWriter(fstream);
			/*out.write(headerString);*/
			jaxbMarshaller.marshal(template, out);
			
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	//FILTERS
	//Dirty filter (chop out x %)
	//Self Reference Filter
	//Edge count filter
	
	//Removes percentage of nodes
	public static Graph<Long, Long> percentVertexFilter(Graph<Long, Long> graph, double percentage)
	{
		ArrayList<Long> verticesToRemove = new ArrayList<Long>();
		ArrayList<Long> edgesToRemove = new ArrayList<Long>();
		for(Long l : graph.getVertices())
		{
			if(Math.random() < percentage)
			{
				verticesToRemove.add(l);
				for(Long e : graph.getInEdges(l))
				{
					edgesToRemove.add(e);
				}
				for(Long e : graph.getOutEdges(l))
				{
					edgesToRemove.add(e);
				}
			}
		}
		for(Long l : verticesToRemove)
		{
			graph.removeVertex(l);
		}
		
		//Now update edges and remove any invalid ones
		for(Long l : edgesToRemove)
		{
			if(graph.containsEdge(l))
				graph.removeEdge(l);
		}
		return graph;
	}
	
	public static void writeLayoutToGraphML(Graph<Long, Long> g, String filename)
	{
		GraphMLObject template = new GraphMLObject();
		String headerString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
				"<graphml xmlns=\"http://graphml.graphdrawing.org/xmlns\"\n" +
				"xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
				"xsi:schemaLocation=\"http://graphml.graphdrawing.org/xmlns \n" +
				"http://graphml.graphdrawing.org/xmlns/1.0/graphml.xsd\">";
		
		boolean directed = false;
		ArrayList<Long> nodesToRemove = new ArrayList<Long>();
		for(Long l : g.getVertices())
		{
			template.addNode("n" + l.toString());
		}
		
		for(Long l : g.getEdges())
		{
			//For each edge, we need the end points as well
			if(g.getEdgeType(l) == EdgeType.DIRECTED)
				directed = true;
			Pair<Long> vPair = g.getEndpoints(l);
			if(g.containsVertex(vPair.getFirst()) && g.containsVertex(vPair.getSecond()))
				template.addEdge("e" + l.toString(), "n" + vPair.getFirst().toString(), "n" + vPair.getSecond().toString(), directed);
		}
		
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(GraphMLObject.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			      
			FileWriter fstream = new FileWriter(filename);
			BufferedWriter out = new BufferedWriter(fstream);
			/*out.write(headerString);*/
			jaxbMarshaller.marshal(template, out);
			
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	//FILTERS
	//Dirty filter (chop out x %)
	//Self Reference Filter
	//Edge count filter
	
	//Removes percentage of nodes
	public static Graph<Long, Long> percentVertexFilter(Graph<Long, Long> graph, double percentage)
	{
		ArrayList<Long> verticesToRemove = new ArrayList<Long>();
		ArrayList<Long> edgesToRemove = new ArrayList<Long>();
		for(Long l : graph.getVertices())
		{
			if(Math.random() < percentage)
			{
				verticesToRemove.add(l);
				for(Long e : graph.getInEdges(l))
				{
					edgesToRemove.add(e);
				}
				for(Long e : graph.getOutEdges(l))
				{
					edgesToRemove.add(e);
				}
			}
		}
		for(Long l : verticesToRemove)
		{
			graph.removeVertex(l);
		}
		
		//Now update edges and remove any invalid ones
		for(Long l : edgesToRemove)
		{
			if(graph.containsEdge(l))
				graph.removeEdge(l);
		}
		return graph;
	}
	
	//need to be able to preserve edges that connect into nodes in the gap
	public static Graph<Long, Long> degreeVertexFilter(Graph<Long, Long> graph, int lowerDegreeLimit, int upperDegreeLimit, boolean preserve)
	{
		ArrayList<Long> verticesToRemove = new ArrayList<Long>();
		ArrayList<Long> edgesToRemove = new ArrayList<Long>();
		ArrayList<Long> edgesToMaintain = new ArrayList<Long>();
		for(Long l : graph.getVertices())
		{
			if(graph.degree(l) <= lowerDegreeLimit || graph.degree(l) >= upperDegreeLimit)
			{
				verticesToRemove.add(l);
				for(Long e : graph.getInEdges(l))
				{
					edgesToRemove.add(e);
				}
				for(Long e : graph.getOutEdges(l))
				{
					edgesToRemove.add(e);
				}
			}
		}
		
		//Now update edges and remove any invalid ones
		for(Long l : edgesToRemove)
		{
			if(graph.containsEdge(l))
				graph.removeEdge(l);
		}
		
		for(Long l : verticesToRemove)
		{
			graph.removeVertex(l);
		}
		

		return graph;
	}
	
	
	

}
