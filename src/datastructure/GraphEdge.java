package datastructure;

public class GraphEdge {
	/*
	 * ��ʾͼ��һ����
	 */
	private char value;// Ȩֵ
	private GraphNode targetNode;// �ߵ��յ�

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
