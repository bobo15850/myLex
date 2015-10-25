package datastructure;

public class GraphEdge {
	/*
	 * 表示图的一个边
	 */
	private char value;// 权值
	private GraphNode targetNode;// 边的终点

	public GraphEdge(char value, GraphNode targetNode) {
		this.value = value;
		this.targetNode = targetNode;
	}

	public char getValue() {
		return value;
	}

	public void setValue(char value) {
		this.value = value;
	}

	public GraphNode getTargetNode() {
		return targetNode;
	}

	public void setTargetNode(GraphNode targetNode) {
		this.targetNode = targetNode;
	}
}
