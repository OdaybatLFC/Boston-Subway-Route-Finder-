public class Node implements INode {

	private int id;
	private String station;

	public Node(int stationID, String stationName) {
		id=stationID;
		station=stationName;
	}

	//Getters and Setters
	public int getId(){
		return id;
	}

	public String getName() {
		return station;
	}

	public void setStation(String station) {
		this.station = station;
	}

	public void setId(int id) {
		this.id = id;
	}

        public int hashCode() {
            return this.id;
        }

        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null || obj.getClass() != this.getClass())
                return false;
            Node node = (Node) obj;
            return (node.getName() == this.station && node.getId() == this.id);
        }
}
