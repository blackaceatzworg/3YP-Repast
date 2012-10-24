package NetworkDevelopment;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;



public class StanfordParser {

	private String filePath;
	private String fileComments = "" ;
	private ArrayList<long[]> edges = new ArrayList<long[]>();
	private Graph<Long, Long> graphRep = new SparseMultigraph<Long, Long>();
	
	
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
				if(strLine.startsWith("#") == true )
				{
					fileComments += strLine + "\n";
				}
				else
				{
					tempArr = strLine.split("\t");
					long insertArr[] = new long[2];
					for(int i = 0; i < insertArr.length; i++)
					{
						insertArr[i] = Long.parseLong(tempArr[i]);
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
	
	public ArrayList<long[]> getDataSet()
	{
		return edges;
	}
	
	public String getComments() 
	{
		return fileComments;
	}
	
	public Graph<Long, Long> toGraph()
	{
		//Add vertices to graph first
		for(int i = 0; i < edges.size(); i++)
		{
			//System.out.println("Adding " + edges.get(i)[0] + " to " + edges.get(i)[1]);
			graphRep.addVertex(edges.get(i)[0]);
		}
		
		//Add edges to graph
		Long edgeName = (long) 0;
		EdgeType edgeType = EdgeType.DIRECTED;
		Pair<Long> vPair;
		
		for(long[] arr: edges)
		{
			vPair = new Pair<Long>(arr[0], arr[1]);
			edgeName++;
			graphRep.addEdge(edgeName, vPair, edgeType);
			
		}
		
		return graphRep;
	}
}
