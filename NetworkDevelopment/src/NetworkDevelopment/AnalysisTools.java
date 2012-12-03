package NetworkDevelopment;



import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.poi.hssf.record.formula.functions.T;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.graph.util.Pair;

import repast.simphony.context.Context;
import repast.simphony.relogo.ide.dynamics.NetLogoSystemDynamicsParser.intg_return;
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
	
	public static void JUNGGraphToGraphML(Graph<String, Long> g, String filename)
	{
		GraphMLObject template = new GraphMLObject();
		String headerString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
				"<graphml xmlns=\"http://graphml.graphdrawing.org/xmlns\"\n" +
				"xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
				"xsi:schemaLocation=\"http://graphml.graphdrawing.org/xmlns \n" +
				"http://graphml.graphdrawing.org/xmlns/1.0/graphml.xsd\">";
		
		boolean directed = false;
		ArrayList<String> nodesToRemove = new ArrayList<String>();
		for(String l : g.getVertices())
		{
			template.addXMLNode("n" + l.toString());
		}
		
		for(Long l : g.getEdges())
		{
			//For each edge, we need the end points as well
			if(g.getEdgeType(l) == EdgeType.DIRECTED)
				directed = true;
			Pair<String> vPair = g.getEndpoints(l);
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
		
		System.out.println("GraphML Created.");
	}
	
	public static Graph<String, Long> SnowballSampler(Graph<String, Long> inGraph, int sampleSize)
	{
		Graph<String, Long> rtnGraph = new SparseMultigraph<String, Long>();
		
		int sampleCount = 0;
		Collection<String> vertices = inGraph.getVertices();
		Iterator<String> vertIter = vertices.iterator();
		boolean startVFound = false;
		int startVCount = 0;
		int startVertexIndex = Math.round((float)(Math.random() * inGraph.getVertexCount()));
		System.out.println("Start at " + startVertexIndex);
		String startVertex = null;
		while(vertIter.hasNext() && !startVFound)
		{
			//System.out.println("Node is " + startVCount);
			if(startVCount == startVertexIndex)
			{
				System.out.println("Here!");
				startVertex = vertIter.next();
			}
			else
				vertIter.next();
			startVCount++;
		}
		
		if(startVertex != null)
		{
			rtnGraph.addVertex(startVertex);
			
			sampleCount++;
		
			//Collection<Object> currentVertexBoundary;
			//Collection<Object> workingEdgeSet = inGraph.getOutEdges(startVertex);
			//Iterator<Object> edgeSet = workingEdgeSet.iterator();
			ArrayList<String> vertexSet = new ArrayList<String>();
			vertexSet.add(startVertex);
			//Now grab the out edges
//			Object currVertex = startVertex;
//			Pair<Object> workingPair;
			
			return SnowballSampler(inGraph, rtnGraph, vertexSet, sampleSize, rtnGraph.getVertexCount());
			
			//while < sampleSize
			//for each current Vertex Boundary
			//	Iterate over workingEdgeSet
				//	if node not in graph, add it
				//		add to vertex boundary
				//	if edge not in graph, add it
			//	get rid of edge set
			//	expand next currVertexBoundary edge into edgeSet
			
		
			
//			while(sampleCount < sampleSize)
//			{
//				while(edgeSet.hasNext())
//				{
//					workingPair = inGraph.getEndpoints(edgeSet.next());
//					if(workingPair.getFirst().equals(startVertex))
//					{
//						rtnGraph.addVertex(workingPair.getSecond());
//						currentVertexBoundary.add(workingPair.getSecond());
//						
//					}
//					else
//					{
//						rtnGraph.addVertex(workingPair.getFirst());			
//						currentVertexBoundary.add(workingPair.getFirst());
//					}
//					rtnGraph.addEdge(null, workingPair, EdgeType.DIRECTED);
//					
//				}
//				
//			}
			
			//add nodes to vertex boundary
			
		}
		else
			return null;
		
	}
	//for each in nodeSet
	//	Expand to get all edges
	//	if other v not in rtnGraph
	//		add it to rtnGraph, with edge
	//		add it to nextNodeSet
	//	elif edge not in rtnGraph
	//		add it
	//if(rtnGraph size < sampleSize)
	//	call self
	//else
	//	return rtnGraph
	
	private static Graph<String, Long> SnowballSampler(Graph<String, Long> inGraph, Graph<String, Long> currGraph, List<String> vertexSet, int sampleSize, int prevSize)
	{
		Collection<Long> workingEdgeSet; 
		Iterator<Long> edgeIter;
		Pair<String> workingPair;
		List<String> nextVertexSet = new ArrayList<String>();
		
		String newNode;
		
		System.out.println("Graph size is " + currGraph.getVertexCount());
		
		//Snowball sampler needs to pick randomly
		for(String o : vertexSet)
		{
			workingEdgeSet = inGraph.getOutEdges(o);
			edgeIter = workingEdgeSet.iterator();
			
			while(edgeIter.hasNext())
			{
				Long currentEdge = edgeIter.next();
				workingPair = inGraph.getEndpoints(currentEdge);
				if(workingPair.getFirst().equals(o))
					newNode = workingPair.getSecond();
				else
					newNode = workingPair.getFirst();
				
				if(!currGraph.containsVertex(newNode))
				{
					currGraph.addVertex(newNode);
					nextVertexSet.add(newNode);
				}
				if(!currGraph.containsEdge(currentEdge))
					currGraph.addEdge(currentEdge, workingPair, EdgeType.DIRECTED);
			}
		}
		
		if(currGraph.getVertexCount() < sampleSize  && (prevSize < currGraph.getVertexCount()))
			currGraph = SnowballSampler(inGraph, currGraph, nextVertexSet, sampleSize, currGraph.getVertexCount());
		
		return currGraph;
	}
	
}
