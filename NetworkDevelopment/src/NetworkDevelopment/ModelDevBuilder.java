package NetworkDevelopment;

import org.apache.poi.hssf.record.formula.functions.T;

import repast.simphony.context.Context;
import repast.simphony.context.space.graph.NetworkBuilder;
import repast.simphony.context.space.graph.NetworkGenerator;
import repast.simphony.context.space.graph.WattsBetaSmallWorldGenerator;
import repast.simphony.dataLoader.ContextBuilder;
import repast.simphony.space.graph.Network;
import repast.simphony.space.graph.RepastEdge;
import repast.simphony.util.collections.IndexedIterable;

public class ModelDevBuilder implements ContextBuilder<Object> {

	@Override
	public Context build(Context<Object> context) {
		// TODO Auto-generated method stub
		context.setId("NetworkDevelopment");
		NetworkBuilder<Node> builder = new NetworkBuilder("TestNet", context, true);
		Network<Node> net = builder.buildNetwork();
		context.add(new Node("n0", context, net));
		context.add(new Node("n1", context, net));
		context.add(new Node("n2", context, net));
		context.add(new Node("n3", context, net));
		
		IndexedIterable<Object> startNodes = context.getObjects(Node.class);
		net.addEdge((Node)startNodes.get(0), (Node)startNodes.get(1));
		net.addEdge((Node)startNodes.get(1), (Node)startNodes.get(2));
		net.addEdge((Node)startNodes.get(2), (Node)startNodes.get(3));
		net.addEdge((Node)startNodes.get(3), (Node)startNodes.get(0));
		
		//net.addEdge((Node)startNodes.get(1), (Node)startNodes.get(0));
		
		int nodeCount = 10;
		for( int i = 2; i < nodeCount ; i ++) {
			context.add(new Node("n" + i, context, net));
		}
		
		
		/*NetworkGenerator gen = new WattsBetaSmallWorldGenerator(0.2, 2, false);
		
		builder.setGenerator(gen);
		*/
		
		return context;
	}
	
	

}
