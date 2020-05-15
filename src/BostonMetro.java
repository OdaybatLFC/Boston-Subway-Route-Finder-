import java.io.IOException;
import java.util.AbstractMap;
import java.util.ArrayList;

public class BostonMetro {

    private MetroMapParser metroParser;
    private IMultiGraph metro;

    public BostonMetro() throws IOException {
        metro = new MultiGraph();
        metroParser = new MetroMapParser("bostonmetro.txt");
    }

    public void readFile() throws IOException {
        metro = metroParser.loadGraph();
    }

    public ArrayList<AbstractMap.SimpleEntry<INode, Edge>> findPath(String src, String dest){
        return metro.findPath(src, dest);
    }
}
