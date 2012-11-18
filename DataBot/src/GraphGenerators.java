import java.util.ArrayList;
import java.util.Collection;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.graph.util.Pair;


public class GraphGenerators {

	/*public static Graph<Long, Long> smallWorldGen(int maxNodes, int meanDegree)
	{
		
	}*/
	
	public static Graph<Long, Long> smallWorldGenerator(int nodes, int meanDegree, double prob)
	{
		Graph<Long, Long> newGraph = new SparseMultigraph<Long, Long>();
		Long edgeName = (long) 0;
		for(int i = 0; i < nodes; i++)
			newGraph.addVertex((long)i);
		
		newGraph.containsEdge(1);
		
		//Create circle lattice
		for(Long start : newGraph.getVertices())
		{
			for(Long end : newGraph.getVertices())
			{
				if( Math.abs(start - end) > 0 && Math.abs(start - end) <= meanDegree / 2 && newGraph.findEdge(start, end) == null)
				{
					EdgeType edgeType = EdgeType.UNDIRECTED;
					Pair<Long> vPair = new Pair<Long>(start, end);
					edgeName++;
					newGraph.addEdge(edgeName, vPair, edgeType);
				}
				
			}
		}
		
		for(Long v : newGraph.getVertices())
		{
			Collection<Long> incidentEdges = newGraph.getIncidentEdges(v);
			for(Long e : incidentEdges)
			{
				Pair<Long> vPair = newGraph.getEndpoints(e);
				long other = vPair.getFirst();
				if(vPair.getFirst() == v)
					other = vPair.getSecond();
				
				if(v < other && Math.random() <= prob)
				{
					//now choose a random vertex from all of the vertices
					
					newGraph.removeEdge(e);
					int numVert = newGraph.getVertexCount();
					int randomVertex = (int) Math.floor(Math.random() * numVert); 
					while(randomVertex == v || newGraph.findEdge(v, (long)randomVertex) != null)
					{
						randomVertex = (int) Math.floor(Math.random() * numVert); 
					}
					System.out.println("Tweaking edge " + v + " -> " + other + " to be " + v + " -> " + randomVertex );
					EdgeType edgeType = EdgeType.UNDIRECTED;
					Pair<Long> newPair = new Pair<Long>(v, (long)randomVertex);
					newGraph.addEdge(e, newPair, edgeType);
				}
			}
		}
		return newGraph;
	}
}
