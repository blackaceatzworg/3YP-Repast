package NetworkDevelopment;

import edu.uci.ics.jung.graph.Graph;

public class SamplerMain {
	public static void main(String[] args)
	{
		System.out.println("Whattup");
		//AnalysisTools.repastNetworkToGraphML(context, net, "test.graphml");
		StanfordParser sp = new StanfordParser("soc-Epinions1.txt");
		sp.Parse();
		
		Graph<String, Long> testGraph;
		testGraph = sp.toGraph();
		
		System.out.println("Nodes: " + testGraph.getVertexCount());
		
		
	}
}
