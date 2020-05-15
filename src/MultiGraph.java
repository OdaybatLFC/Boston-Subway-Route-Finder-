import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.Collections;
import java.util.AbstractMap;

public class MultiGraph implements IMultiGraph {

	private ArrayList<Edge> edges = new ArrayList<>();
	private HashMap<String, INode> nodeMap = new HashMap<>();
	private HashMap<INode, ArrayList<Edge>> map = new HashMap<>();
	
	public boolean hasNode(INode station) {
		return nodeMap.containsValue(station);
	}
	
	public INode getNode(String name) {
		return nodeMap.get(name);
	}

	public boolean adjacent(INode src, INode dest){
	    for(Edge e : map.get(src)){
	        if(e.getDestNode() == dest){
	            return true;
            }
        }
	    return false;
	}

	public ArrayList<INode> neighbours(INode node){
		ArrayList<Edge> edges_of_node = map.get(node);
		ArrayList<INode> neighbour_nodes = new ArrayList<>();
		edges_of_node.forEach((edge) -> neighbour_nodes.add(edge.getDestNode()));
		return neighbour_nodes;
	}

	public void addEdge(String name, INode outboundID, INode inboundID) {
		Edge metroLine = new Edge(name, outboundID, inboundID);
        ArrayList<Edge> adding;

		//add Edge to outbound node (graph is undirectional)
		edges.add(metroLine);
		adding = map.get(outboundID);
		adding.add(metroLine);
		map.put(outboundID, adding);

        //add Edge to inbound node (graph is undirectional)
        adding = map.get(inboundID);
        adding.add(metroLine);
		map.put(inboundID, adding);
	}

	public void addNode(int id, String stationName){
	    INode station = new Node(id, stationName);
	    nodeMap.put(stationName, station);
	    map.put(station, new ArrayList<>()); //node has no edge yet
        }
        
    /* Weighted breadth-first search. 
     * Returns optimal node path from source to destination.
     * Returns null if either source or destination do not exist. 
     * Returns null if no such path exists. */
    public ArrayList<AbstractMap.SimpleEntry<INode, Edge>> findPath(String src, String dest)
    {
        ArrayList<AbstractMap.SimpleEntry<INode, Edge>> result = new ArrayList<>();
        ArrayList<INode> agenda = new ArrayList<>();
        ArrayList<INode> visited = new ArrayList<>();
        ArrayList<ArrayList<INode>> candidates = new ArrayList<>();
        HashMap<INode, Edge> parentedges = new HashMap<>();
        HashMap<INode, INode> parents = new HashMap<>();
        HashMap<INode, Integer> costs = new HashMap<>();
        INode source = nodeMap.get(src);
        INode goal = nodeMap.get(dest);
        INode current;
        Edge currEdge;
        Edge prevEdge = new Edge("", source, source); /* Dummy edge */
        
        if(source == null || goal == null)
            return null;
        else if (source.equals(goal)) {
            result.add(new AbstractMap.SimpleEntry<>(source, prevEdge));
            return result;
        }

        current = source;
        costs.put(current, 1);

        /* Search for goal */
        do {
            if (!agenda.isEmpty())
                agenda.remove(0);

            /* Build candidate path */
            if (current.equals(goal)) {
                ArrayList<INode> candidate = new ArrayList<>();
                INode path_ptr = current;
                while (!path_ptr.equals(source)) {
                    candidate.add(path_ptr);
                    path_ptr = parents.get(path_ptr);
                }
                candidates.add(candidate);
                if (!agenda.isEmpty()) {
                    current = agenda.get(0);
                }
                continue;
            }
            int cost = 1;
            ArrayList<INode> adjNodes = neighbours(current);

            /* Detect line change and adjust cost */
            if (current != source) {
                ArrayList<Edge> edges = map.get(current);
                INode parent, s, d;
                Edge edge;
                for (int i = 0; i < edges.size(); i++) {
                    parent = parents.get(current);
                    edge = edges.get(i);
                    s = edge.getSrcNode();
                    d = edge.getDestNode();
                    if ((current.equals(s) && parent.equals(d)) ||
                        (current.equals(d) && parent.equals(s))) {
                        currEdge = edge;
                        if (!currEdge.getLabel().equals(prevEdge.getLabel()))
                            cost += 10;
                        parentedges.put(current, currEdge);
                        prevEdge = currEdge;
                        break;
                    }
                }
            } else {

            }

            /* Expand agenda */
            for (int i = 0; i < adjNodes.size(); i++) {
                INode next = adjNodes.get(i);
                if (visited.contains(next)) {
                    adjNodes.remove(i);
                    i--;
                    continue;
                } else {
                    parents.put(next, current);
                    costs.put(next, cost);
                    if (!next.equals(goal))
                        visited.add(next);
                    agenda.add(next);
                }
            }
            
            if (!agenda.isEmpty()) {
                current = agenda.get(0);
            }
        } while (!agenda.isEmpty());

        /* Select optimal path */
        int[] pathCosts = new int[candidates.size()];
        ArrayList<INode> candidatePath;
        for (int i = 0; i < candidates.size(); i++) {
            pathCosts[i] = 0;
            candidatePath = candidates.get(i);
            for (int j = 0; j < candidatePath.size(); j++) {
                current = candidatePath.get(j);
                pathCosts[i] += costs.get(current);
            }
        }
        int min = pathCosts[0];
        int min_index = 0;
        for (int i = 0; i < pathCosts.length; i++) {
            if (pathCosts[i] < min) {
                min = pathCosts[i];
                min_index = i;
            }
        }

        /* Package results together with their parent edges */
        ArrayList<INode> resultpath = candidates.get(min_index);

        for (int i = 0; i < resultpath.size(); i++) {
            INode nextnode = resultpath.get(i);
            result.add(new AbstractMap.SimpleEntry<>(nextnode, parentedges.get(nextnode)));
        }
        if(result.size()>1){
            result.add(new AbstractMap.SimpleEntry<>(source,
                    new Edge(result.get(result.size() - 1).getValue().getLabel(), source, source)));
        }
        else{
            result.add(new AbstractMap.SimpleEntry<>(source,
                    new Edge(map.get(source).get(0).getLabel(), source, source)));
        }

        Collections.reverse(result);

        return result;
    }
}
