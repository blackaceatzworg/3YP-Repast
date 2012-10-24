package NetworkDevelopment;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

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

}