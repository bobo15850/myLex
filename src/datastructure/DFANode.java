package datastructure;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;

import common.Util;

public class DFANode implements Comparable<DFANode> {
	private final Set<NFANode> value;

	public DFANode(Set<NFANode> value) {
		this.value = value;
	}

	public Set<NFANode> getValue() {
		return value;
	}

	public boolean cantains(NFANode nfaNode) {
		if (this.value == null) {
			return false;
		}
		return value.contains(nfaNode);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DFANode other = (DFANode) obj;
		if (value == null) {
			if (other.value != null)
				return false;
		}
		else if (!value.equals(other.value))// 因为set接口已经重写了equals方法所以可以直接比较
			return false;
		return true;
	}

	public void ebsilonClosure(NFA nfa) {
		Map<NFANode, ArrayList<NFAEdge>> linkTable = nfa.getLinkTable();// nfa的邻接表
		if (this.value.size() != 0) {
			Queue<NFANode> queue = new LinkedList<NFANode>();
			Set<NFANode> visitedNode = new TreeSet<NFANode>();
			queue.addAll(value);
			while (!queue.isEmpty()) {
				NFANode nfaNode = queue.poll();
				ArrayList<NFAEdge> linkedEdge = linkTable.get(nfaNode);
				for (int i = 0; i < linkedEdge.size(); i++) {
					char val = linkedEdge.get(i).getValue();
					NFANode node = linkedEdge.get(i).getTargetNode();
					if (val == Util.SpecialOperand.ε) {
						if (!(nfaNode.equals(node) || visitedNode.contains(node) || queue.contains(node))) {
							queue.add(node);
							this.value.add(node);
						}
					}
				}
				visitedNode.add(nfaNode);
			}
		}
	}// 求改DFANode的ebsilon闭包

	public String toString() {
		String resStr = "(";
		Iterator<NFANode> itera = this.value.iterator();
		if (itera.hasNext()) {
			resStr += itera.next();
		}
		while (itera.hasNext()) {
			resStr += ",";
			resStr += itera.next();
		}
		resStr += ")";
		return resStr;
	}

	@Override
	public int compareTo(DFANode dfaNode) {
		Iterator<NFANode> thisItera = this.value.iterator();
		Iterator<NFANode> otherItera = dfaNode.value.iterator();
		while (thisItera.hasNext()) {
			if (otherItera.hasNext()) {
				int thisId = thisItera.next().getId();
				int otherId = otherItera.next().getId();
				if (thisId == otherId) {
					continue;
				}
				else {
					return thisId - otherId;
				}
			}
			else {
				return 1;
			}
		}
		if (otherItera.hasNext()) {
			return -1;
		}
		else {
			return 0;
		}
	}
}
