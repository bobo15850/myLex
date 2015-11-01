package datastructure;

public class NFAEdge {
	/*
	 * 表示图的一个边
	 */
	private final char value;// 权值
	private final NFANode targetNode;// 边的终点

	public NFAEdge(char value, NFANode targetNode) {
		this.value = value;
		this.targetNode = targetNode;
	}

	public char getValue() {
		return value;
	}

	public NFANode getTargetNode() {
		return targetNode;
	}
}
