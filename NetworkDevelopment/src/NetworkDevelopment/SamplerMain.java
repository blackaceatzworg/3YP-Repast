package NetworkDevelopment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import edu.uci.ics.jung.graph.Graph;

public class SamplerMain {
	public static void main(String[] args)
	{
		System.out.println("Whattup");
		//AnalysisTools.repastNetworkToGraphML(context, net, "test.graphml");
		StanfordParser sp = new StanfordParser("/Users/matt/Documents/3YP-Repast/NetworkDevelopment/email-EuAll.txt");
		sp.Parse();
		
		Graph<String, Long> testGraph;
		testGraph = sp.toGraph();
		Collection<String> v = testGraph.getVertices();
		Iterator iter = v.iterator();
		double deg = 0;
		while(iter.hasNext())
		{
			String s = (String) iter.next();
			deg = deg + testGraph.degree(s);
		}
		AnalysisTools.JUNGGraphToGraphML(testGraph, "euAll-Full.graphml");
		
		System.out.println("total deg = " + deg + " avg deg = " + deg / testGraph.getVertexCount());
//		for(int i = 0; i < 3; i++)
//		{
//			
//			Graph<String, Long> sampledGraph = AnalysisTools.SnowballSampler(testGraph, 400);
//			
//			System.out.println("Nodes: " + sampledGraph.getVertexCount());
//			
//			AnalysisTools.JUNGGraphToGraphML(sampledGraph, "eu-"+ i +".graphml");
//		}
	}
}
