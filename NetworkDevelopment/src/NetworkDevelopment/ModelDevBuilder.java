package NetworkDevelopment;

import org.apache.poi.hssf.record.formula.functions.T;

import repast.simphony.context.Context;
import repast.simphony.context.space.graph.NetworkBuilder;
import repast.simphony.context.space.graph.NetworkGenerator;
import repast.simphony.context.space.graph.WattsBetaSmallWorldGenerator;
import repast.simphony.dataLoader.ContextBuilder;
import repast.simphony.space.graph.Network;

public class ModelDevBuilder implements ContextBuilder<Object> {

	@Override
	public Context build(Context<Object> context) {
		// TODO Auto-generated method stub
		context.setId("testContext");
		int nodeCount = 500;
		for( int i = 0; i < nodeCount ; i ++) {
			context.add(new Node("n" + i));
		}
		
		NetworkGenerator gen = new WattsBetaSmallWorldGenerator(0.2, 2, false);
		NetworkBuilder builder = new NetworkBuilder("TestNet", context, true);
		builder.setGenerator(gen);
		Network net = builder.buildNetwork();
		return context;
	}
	
	

}
