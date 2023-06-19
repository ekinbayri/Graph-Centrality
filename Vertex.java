import java.util.ArrayList;

public class Vertex {
	private String name;
	private ArrayList<Edge> edges;
	private Vertex parent;

	public Vertex(String name) {
		this.name = name;
		edges = new ArrayList<Edge>();
		parent = null;
	}

	public void addEdge(Edge e) {
		edges.add(e);
	}

	public ArrayList<Edge> getEdges() {
		return this.edges;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Vertex getParent() {
		return parent;
	}

	public void setParent(Vertex parent) {
		this.parent = parent;
	}

}
