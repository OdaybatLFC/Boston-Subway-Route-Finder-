import java.util.AbstractMap;
import java.util.ArrayList;

public interface IMultiGraph {

    public void addNode(int id, String stationName);
    public void addEdge(String name, INode src, INode dest);
    public boolean hasNode(INode station);
    public INode getNode(String name);
    public ArrayList<AbstractMap.SimpleEntry<INode, Edge>> findPath(String src, String dest);
}
