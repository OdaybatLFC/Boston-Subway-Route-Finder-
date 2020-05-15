import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

/**
 * @about: This class reads a text description of a metro subway system
 * and generates a graph representation of the metro.
 */

public class MetroMapParser {
	
	private BufferedReader fileInput;
    
    /**
     * @effects: creates a new parser that will read from the file 
     * filename unless the file does not exist.
     * 
     * @throws java.io.IOException if filename cannot be read
     *
     * @returns a new MetroMapParser that will parse the file filename
     */
    
    public MetroMapParser(String filename) throws IOException
    {
    	fileInput = new BufferedReader(new FileReader(filename));
    }
    
    /**
     * @name: loadGraph()
     * 
     * @effects: parses the file, and generates a graph from it, unless there
     * is a problem reading the file, or there is a problem with the format of the
     * file.
     *
     * @throws java.io.IOException if there is a problem reading the file 
     *  
     * @returns the Graph generated by the file
     */
    
    public MultiGraph loadGraph() throws IOException
    {
    	String line = fileInput.readLine();
    	StringTokenizer st;
    	int stationID, outboundID, inboundID;
    	String stationName, lineName;
    	MultiGraph graph = new MultiGraph(); /////////////////////////////
    	HashMap<Integer, Node> map = new HashMap<>();
    	ArrayList<int[]> edges = new ArrayList<>();
    	ArrayList<String> labels = new ArrayList<>();
    	
    	while(line != null)
    	{
    	    st = new StringTokenizer(line);

    	    //We want to handle empty lines effectively, we just ignore them!
    	    if(!st.hasMoreTokens())
    	    {
    	    	line = fileInput.readLine();
    		    continue;
    	    }
    	    
    	    stationID = Integer.parseInt(st.nextToken());
    	    
    	    if(!st.hasMoreTokens())
    	    {
    	    	throw new IOException("no station name");
    	    }

    	    stationName = st.nextToken();
    	    
    	    //adding stations to the graph
    	    graph.addNode(stationID, stationName);
    	    Node station = new Node(stationID, stationName);
    	    map.put(stationID, station);
    	    
    	    if(!st.hasMoreTokens())
    	    {
    	    	throw new IOException("station is on no lines");
    	    }
    	    
    	    while(st.hasMoreTokens())
    	    {
    	    	lineName = st.nextToken();
    			
    		    if(!st.hasMoreTokens())
    		    {
    		    	throw new IOException("poorly formatted line info");
    		    }

    		    outboundID = Integer.parseInt(st.nextToken());
    		
    		    if(!st.hasMoreTokens())
    		    {
    		    	throw new IOException("poorly formatted adjacent stations");
    		    }

    		    inboundID = Integer.parseInt(st.nextToken());
    		    
    		    //saving edges to the edges array, also keeping track of the label of that edge in a separate array
    		    if(outboundID != 0) {
    		    	int[] edge = {stationID, outboundID};
    		    	edges.add(edge);
    		    	labels.add(lineName);
    		    }
    		    if(inboundID != 0) {
    		    	int[] edge = {stationID, inboundID};
        		    edges.add(edge);
        		    labels.add(lineName);
    		    }
    	    }
    	    
    	    line = fileInput.readLine();
    	}
    	
    /** @test uncomment to test that it works @Alex */
//    	for(int i = 0; i < edges.size() && i < labels.size(); i++) { 
//    		System.out.print(i);
//    		System.out.println(labels.get(i) + " " + map.get(edges.get(i)[0]).getStation() + " " +map.get(edges.get(i)[1]).getStation());
//    	}
    	
    	//adding edges with slightly complicated technique 
    	for(int i = 0; i < edges.size() && i < labels.size(); i++) {
    		graph.addEdge(labels.get(i), map.get(edges.get(i)[0]), map.get(edges.get(i)[1]));
    	}
    	
    	//returning the generated graph from the file 
    	return graph;
    }

}