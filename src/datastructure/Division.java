package datastructure;

import java.util.Set;

public class Division {
	private static int ID = 0;
	private final int id;
	private final Set<DFANode> dfaNodeSet;

	public Division(Set<DFANode> nfaNodeSet) {
		this.dfaNodeSet = nfaNodeSet;
		this.id = ID++;
	}

	public Set<DFANode> getDfaNodeSet() {
		return dfaNodeSet;
	}

	public boolean hasDFANode(DFANode dfaNode) {
		return this.dfaNodeSet.contains(dfaNode);
	}

	public DFANode getOneDFANode() {
		if (this.dfaNodeSet != null && this.dfaNodeSet.size() != 0) {
			return this.dfaNodeSet.iterator().next();
		}
		return null;
	}

	public String toString() {
		return "[" + String.valueOf(this.id) + "]";
	}

	public String toDetailString() {
		StringBuilder resSb = new StringBuilder();
		resSb.append(this.toString()).append(":  ").append("(");
		for (DFANode node : this.dfaNodeSet) {
			resSb.append(node.toString());
		}
		resSb.append(")");
		return resSb.toString();
	}

	public int getId() {
		return id;
	}
}
