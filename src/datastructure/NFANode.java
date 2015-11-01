package datastructure;

public class NFANode implements Comparable<NFANode> {
	private static int nodeId = 0;// ��̬��������֤ͼ�����е�ı�Ų�ͬ
	private final int id;// ͼ�ı�ţ����Զ���ȡ

	public NFANode() {
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
		NFANode other = (NFANode) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public int compareTo(NFANode node) {
		return this.id - node.id;
	}

	@Override
	public String toString() {
		return String.valueOf(this.id);
	}
}