public interface IEdge {

    public String getLabel();
    public INode getSrcNode();
    public INode getDestNode();
    public void setLabel(String label);
    public void setSrcNode(INode node);
    public void setDestNode(INode node);

}
