package NetworkDevelopment;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.graph.util.Pair;

public class TestGraphMLOut {

	public static void main(String[] args)
	{
		Graph<String, Long> testGraph = new SparseMultigraph<String, Long>();
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
		
		testGraph.addEdge(0, p1, edgeType)
		
	}
}
