import java.util.ArrayList;

public interface INode {

    public int getId();
    public String getName();
    public void setId(int id);
    public void setStation(String label);
    public int hashCode();
    public boolean equals(Object obj);
}
