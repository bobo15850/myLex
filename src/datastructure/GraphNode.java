package datastructure;

public class GraphNode implements Comparable<GraphNode> {
	private static int nodeId = 0;// 静态变量，保证图上所有点的编号不同
	private final int id;// 图的编号，是自动获取

	public GraphNode() {
		this.id = nodeId++;
	}

	public int getId() {
		return id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GraphNode other = (GraphNode) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public int compareTo(GraphNode node) {
		return this.id - node.id;
	}
}
