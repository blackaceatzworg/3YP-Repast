package NetworkDevelopment;



import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.poi.hssf.record.formula.functions.T;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.graph.util.Pair;

import repast.simphony.context.Context;
import repast.simphony.space.graph.Network;
import repast.simphony.space.graph.RepastEdge;

public class AnalysisTools {
	
	public static void repastNetworkToGraphML(Context context, Network network, String filename)
	{
		System.out.println("Creating "  + filename + " ...");
		GraphMLObject template = new GraphMLObject();
		Iterable<Node> nodes = network.getNodes();
		for(Node n: nodes)
		{
			template.addXMLNode(n.getID());
		}
		
		Iterable<RepastEdge> edges = network.getEdges();
		Integer i = 0;
		for(RepastEdge<Node> e: edges)
		{
			template.addEdge(i.toString(), ((Node) e.getSource()).getID(), ((Node) e.getTarget()).getID());
			i++;
		}
		
		String headerString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
				"<graphml xmlns=\"http://graphml.graphdrawing.org/xmlns\"\n" +
				"xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
				"xsi:schemaLocation=\"http://graphml.graphdrawing.org/xmlns \n" +
				"http://graphml.graphdrawing.org/xmlns/1.0/graphml.xsd\">";
		
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(GraphMLObject.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			      
			FileWriter fstream = new FileWriter(filename);
			BufferedWriter out = new BufferedWriter(fstream);
			/*out.write(headerString);*/
			jaxbMarshaller.marshal(template, out);
			
			out.close();
			fstream.close();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("GraphML Creation Finished");
	}
	
	public static void JUNGGraphToGraphML(Graph<Node, Node> g, String filename)
	{
		GraphMLObject template = new GraphMLObject();
		String headerString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
				"<graphml xmlns=\"http://graphml.graphdrawing.org/xmlns\"\n" +
				"xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
				"xsi:schemaLocation=\"http://graphml.graphdrawing.org/xmlns \n" +
				"http://graphml.graphdrawing.org/xmlns/1.0/graphml.xsd\">";
		
		boolean directed = false;
		ArrayList<Node> nodesToRemove = new ArrayList<Node>();
		for(Node l : g.getVertices())
		{
			template.addXMLNode("n" + l.toString());
		}
		
		for(Node l : g.getEdges())
		{
			//For each edge, we need the end points as well
			if(g.getEdgeType(l) == EdgeType.DIRECTED)
				directed = true;
			Pair<Node> vPair = g.getEndpoints(l);
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
	
}
