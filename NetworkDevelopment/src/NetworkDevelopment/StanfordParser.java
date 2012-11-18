package NetworkDevelopment;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.graph.util.Pair;


public class StanfordParser {

	private String filePath;
	private String fileComments = "" ;
	private ArrayList<String[]> edges = new ArrayList<String[]>();
	private Graph<String, Long> graphRep = new SparseMultigraph<String, Long>();
	
	
	StanfordParser(String filePath)
	{
		this.filePath = filePath;
	}
	
	public boolean Parse() 
	{
		boolean error = false;
		try
		{
			FileInputStream fstream = new FileInputStream(filePath);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			String tempArr[];
			
			
			//Code from http://www.roseindia.net/java/beginners/java-write-to-file.shtml
			while ((strLine = br.readLine()) != null)   {
				//System.out.println(strLine);
				if(strLine.startsWith("#") == true )
				{
					fileComments += strLine + "\n";
				}
				else
				{
					tempArr = strLine.split("\t");
					String insertArr[] = new String[2];
					for(int i = 0; i < insertArr.length; i++)
					{
						insertArr[i] = tempArr[i];
					}
					//System.out.println("Adding " + insertArr[0] + " to " + insertArr[1]);
					edges.add(insertArr);
				}
			}
			in.close();
		}
		catch (FileNotFoundException e){
			System.err.println("File not found!");
			error = true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println("I/O Error!");
			error = true;
		}
		return error;
	}
	
	public ArrayList<String[]> getDataSet()
	{
		return edges;
	}
	
	public String getComments() 
	{
		return fileComments;
	}
	
/*	private boolean findNode(ArrayList<String> list, String newString)
	{
		for(Node n: list)
		{
			if(n.getID() == newString)
				return true;
		}
		return false;
	}*/
	
	
	
	public Graph<String, Long> toGraph()
	{
		//Add vertices to graph first
		//STORE ADDED IDS IN COLLECTION
		//CHECK FOR ID BEFORE ADDING NEW NODE
		HashSet<String> nodes = new HashSet<String>();
		Long edgeName = (long) 0;
		EdgeType edgeType = EdgeType.DIRECTED;
		Pair<String> vPair;
		for(int i = 0; i < edges.size(); i++)
		{
			//System.out.println("Adding " + edges.get(i)[0] + " to " + edges.get(i)[1]);
			
			if(!nodes.contains(edges.get(i)[0]))
			{
				graphRep.addVertex(edges.get(i)[0]);
				nodes.add(edges.get(i)[0]);
				//System.out.println(graphRep.getVertexCount());
			}
			if(!nodes.contains(edges.get(i)[1]))
			{
				graphRep.addVertex(edges.get(i)[1]);
				nodes.add(edges.get(i)[1]);
				//System.out.println(graphRep.getVertexCount());
			}
			vPair = new Pair<String>(edges.get(i)[0], edges.get(i)[1]);
			edgeName++;
			graphRep.addEdge(edgeName, vPair, edgeType);
			//System.out.println(graphRep.getVertexCount());
		}
		
	/*	Collection<Node> nList = graphRep.getVertices();
		ArrayList<Node> nodeArrList = new ArrayList<Node>();
		
		
		
		Iterator<Node> iIter = nList.iterator();
		Iterator<Node> jIter = nList.iterator();
		while(iIter.hasNext())
		{
			nodeArrList.add(iIter.next());
		}
		Collections.sort(nodeArrList);
		
		for(Node n: nodeArrList)
		{
			System.out.println(n.getID());
		}*/
		
		/*int i = 0, j = 0;
		
		while(iIter.hasNext())
		{
			while(jIter.hasNext())
			{
				if(iIter.next().getID() == jIter.next().getID() && i != j)
					System.out.println("Duplicate edge at " + iIter.next().getID() + " --> " + jIter.next().getID() + " Position " + i + ", " +j);
				j++;
			}
			i++;
		}*/
		
		/*for(int i = 0; i < nList.size(); i++)
		{
			for(int j = 0; j < nList.size(); j++)
			{
				if(nList.get(i).getID() == nList.get(j).getID() && i != j)
					System.out.println("Mismatch at " + i + " " + j);
			}
		}*/
		return graphRep;
	}
}
