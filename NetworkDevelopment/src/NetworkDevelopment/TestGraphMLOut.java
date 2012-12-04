package NetworkDevelopment;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.graph.util.Pair;

public class TestGraphMLOut {

	public static void main(String[] args)
	{
		/*Graph<String, Long> testGraph = new SparseMultigraph<String, Long>();
		testGraph.addVertex("n1");
		testGraph.addVertex("n2");
		testGraph.addVertex("n3");
		testGraph.addVertex("n4");
		
		Long edgeName = (long) 0;
		EdgeType edgeType = EdgeType.DIRECTED;
		Pair<String> p1, p2, p3, p4;
		
		p1 = new Pair<String>("n1", "n2");
		p2 = new Pair<String>("n2", "n3");
		p3 = new Pair<String>("n3", "n4");
		p4 = new Pair<String>("n4", "n1");
		
		testGraph.addEdge((long) 1, p1, edgeType);
		testGraph.addEdge((long) 2, p2, edgeType);
		testGraph.addEdge((long) 3, p3, edgeType);
		testGraph.addEdge((long) 4, p4, edgeType);
		*/
		GraphMLObject GML = new GraphMLObject("g1", "directed");
		
		String id = GML.addAttributeKey("mofo", "double", false, "0");
		
		GML.addXMLNode("n1");
		GML.addXMLNode("n2");
		GML.addXMLNode("n3");
		GML.addXMLNode("n4");
		
		HashMap<String, Object> edgeAt = new HashMap<String, Object>();
		edgeAt.put(id, (Double) 1.2 );
		GML.addEdge("e1", "n1", "n2", edgeAt);
		edgeAt.clear();
		
		edgeAt.put(id, (Double) 0.1 );
		GML.addEdge("e2", "n2", "n3", edgeAt);
		edgeAt.clear();
		
		edgeAt.put(id, (Double) 0.4 );
		GML.addEdge("e3", "n3", "n4", edgeAt);
		edgeAt.clear();
		
		edgeAt.put(id, (Double) 2.0 );
		GML.addEdge("e4", "n4", "n1", edgeAt);
		edgeAt.clear();
	
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(GraphMLObject.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			      
			FileWriter fstream = new FileWriter("attrTest.graphml");
			BufferedWriter out = new BufferedWriter(fstream);
			/*out.write(headerString);*/
			jaxbMarshaller.marshal(GML, out);
			
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("GraphML Created.");
		
		
		//AnalysisTools.JUNGGraphToGraphML(testGraph, "attrTest.graphml");
		
		
	}
}
