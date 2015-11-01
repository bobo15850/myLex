package datastructure;

public class DFAEdge {
	private final char value;// 权值
	private final DFANode targetNode;// 边的目标节点

	public DFAEdge(char value, DFANode targetNode) {
		this.value = value;
		this.targetNode = targetNode;
	}

	public char getValue() {
		return value;
	}

	public DFANode getTargetNode() {
		return targetNode;
	}

	public String toString() {
		return value + "-->" + this.targetNode.toString();
	}
}
