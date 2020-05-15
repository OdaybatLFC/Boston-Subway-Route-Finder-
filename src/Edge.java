public class Edge implements IEdge{

	// private Node src, dest;
	private INode src, dest;
	private String label;

	public Edge(String lineName, INode outboundID, INode inboundID) {
		src=outboundID;
		dest=inboundID;
		label=lineName;
	}

	public INode getDestNode(){
		return dest;
	}

	public INode getSrcNode(){
		return src;
	}

	//Getters and Setters
//	public int getDest() {
//		return dest;
//	}

//	public int getSrc() {
//		return src;
//	}

	public String getLabel() {
		return label;
	}

	public void setDestNode(INode dest) {
		this.dest = dest;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public void setSrcNode(INode src) {
		this.src = src;
	}

}
