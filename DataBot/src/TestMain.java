import edu.uci.ics.jung.graph.Graph;


public class TestMain {
	public static void main(String[] args)
	{
		StanfordParser parser = new StanfordParser("G:\\My Documents\\3rd Year Project\\3YP-RnD\\DataBot\\Email-EuAll.txt");
		parser.Parse();
		System.out.println(parser.getComments());
		System.out.println(parser.getDataSet().get(1)[0]);
		
		Graph<Long, Long> g = parser.toGraph();
		System.out.println("We have " + g.getEdgeCount() + " edges and " + g.getVertexCount() + " vertices.");
		
		System.out.println("Filtering graph...");
		//g = GraphUtils.degreeVertexFilter(g, 10, 15);		
		/*Graph<Long, Long> smallWorld = GraphGenerators.smallWorldGenerator(10, 5, 0.8);
		System.out.println("We now have " + smallWorld.getEdgeCount() + " edges and " + smallWorld.getVertexCount() + " vertices.");
		*/GraphUtils.writeToGraphML(g, "testGraph.graphml");
		
		
	}
}
