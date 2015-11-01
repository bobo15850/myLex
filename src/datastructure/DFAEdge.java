package datastructure;

public class DFAEdge {
	private final char value;// Ȩֵ
	private final DFANode targetNode;// �ߵ�Ŀ��ڵ�

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
