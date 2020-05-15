import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.StringTokenizer;

import org.junit.jupiter.api.Test;

class JUnitTest {

	@Test
	/*
	 * This tests if all station names from txt file were entered into the graph
	 * It also tests addNode method in MultiGraph class
	 * It also tests the getNode also
	 */
	void testAddNode() throws IOException{
		MetroMapParser parser = new MetroMapParser("bostonmetro.txt");
		MultiGraph graph = parser.loadGraph();

		ArrayList<String> stationNames = new ArrayList<>();
		BufferedReader fileInput = new BufferedReader(new FileReader("bostonmetro.txt"));
		String line = fileInput.readLine();
		StringTokenizer st;
		while(line!=null){
			st = new StringTokenizer(line);
			if(!st.hasMoreTokens()){
				line = fileInput.readLine();
			}
			Integer.parseInt(st.nextToken());
			if(!st.hasMoreTokens()){
				throw new IOException("no station name - file corrupt or incorrect format");
			}
			stationNames.add(st.nextToken());
			line = fileInput.readLine();
		}
		for(String station : stationNames){
			assertEquals(station, graph.getNode(station).getName());
		}
	}
	
	@Test
	/* Following test will create graph with the parser and then will check if we have
	 * correctly added station names with their ids.
	 */
	void testParser() throws IOException {
		MetroMapParser parser = new MetroMapParser("bostonmetro.txt");
		MultiGraph graph = parser.loadGraph();
		INode good = graph.getNode("Malden");  // correct node
		INode bad = graph.getNode("MaldenN"); // not correctly  spelled node
		assertEquals(true, graph.hasNode(good)); 
		assertEquals(false, graph.hasNode(bad));
		assertEquals(2, good.getId());  // id of Malden is 2 according to the .txt file
	}
	
	@Test
	/* Following test will give an example of a good path and a bad path from 
	 * the Boston metro system. If the path was good then we will get the
	 * final destination station name, else we will get nothing. 
	 */
	void testFindPath() throws IOException {
		MetroMapParser parser = new MetroMapParser("bostonmetro.txt");
		MultiGraph graph = parser.loadGraph();
		ArrayList<AbstractMap.SimpleEntry<INode, Edge>> goodPath = graph.findPath("Davis", "Central");
		ArrayList<AbstractMap.SimpleEntry<INode, Edge>> badPath = graph.findPath("Davis", "Center");
		assertEquals("Central", goodPath.get(goodPath.size()-1).getKey().getName());
		assertEquals(null, badPath);
	}
}