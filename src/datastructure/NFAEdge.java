package datastructure;

public class NFAEdge {
	/*
	 * ��ʾͼ��һ����
	 */
	private final char value;// Ȩֵ
	private final NFANode targetNode;// �ߵ��յ�

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
