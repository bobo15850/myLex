package datastructure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;
import java.util.TreeSet;

import common.Util;

public class DFA {
	private DFANode startNode;// 开始节点
	private Set<DFANode> endNodeSet = new TreeSet<DFANode>();// 终止节点集合
	private Set<Character> charSet = new TreeSet<Character>();// 所有转化因子的集合
	private Map<DFANode, ArrayList<DFAEdge>> linkTable = new TreeMap<DFANode, ArrayList<DFAEdge>>();// 邻接表
	private Set<Division> divisionSet = new HashSet<Division>();// 划分集合

	public DFA(NFA nfa) {
		this.charSet = this.getDeepCopy(nfa.getCharSet());// 得到所有的边上的值的集合
		if (this.charSet.contains(Util.SpecialOperand.ε)) {
			this.charSet.remove(Util.SpecialOperand.ε);
		}
		this.startNode = nfa.getStartNodeEpsilonClosure();// 得到开始节点
		Queue<DFANode> queue = new LinkedList<DFANode>();
		queue.add(startNode);
		while (!queue.isEmpty()) {
			DFANode dfaNode = queue.poll();
			ArrayList<DFAEdge> thisDfaNodeLink = new ArrayList<DFAEdge>();
			if (nfa.isHasEndNFANode(dfaNode)) {
				this.endNodeSet.add(dfaNode);
			}// 判断是否为终结节点
			for (char c : this.charSet) {
				DFANode newDfaNode = nfa.gettargetDFANode(dfaNode, c);
				if (newDfaNode != null) {
					newDfaNode.ebsilonClosure(nfa);// 求闭包
					if (!(queue.contains(newDfaNode) || linkTable.containsKey(newDfaNode) || dfaNode.equals(newDfaNode))) {
						queue.offer(newDfaNode);
					}// 表明是新的状态集合，将其添加到队列中
					DFAEdge dfaEdge = new DFAEdge(c, newDfaNode);
					thisDfaNodeLink.add(dfaEdge);
				}// 确保得到的新的DFANode不为空
			}
			this.linkTable.put(dfaNode, thisDfaNodeLink);// 将该节点的邻接节点链表加入邻接表中
		}
	}// 由NFA构造DFA

	public MinDFA getMinDFA() {
		Division unacceptedDivison = new Division(endNodeSet);// 不可接受状态集
		Set<DFANode> acceptedNodeSet = new HashSet<DFANode>();
		for (DFANode node : this.linkTable.keySet()) {
			if (!this.endNodeSet.contains(node)) {
				acceptedNodeSet.add(node);
			}
		}
		Division acceptedDivision = new Division(acceptedNodeSet);// 可接受状态集
		Stack<Division> stack = new Stack<Division>();
		if (acceptedNodeSet.size() != 0) {
			this.divisionSet.add(acceptedDivision);
		}
		this.divisionSet.add(unacceptedDivison);
		Set<Division> preDivisionSet = new HashSet<Division>();
		preDivisionSet = this.getDeepCopy(this.divisionSet);
		while (true) {
			for (Division division : this.divisionSet) {
				stack.push(division);
			}
			while (!stack.isEmpty()) {
				Division oldDivision = stack.pop();
				lb: for (char c : this.charSet) {
					Set<Division> newDivisionSet = this.split(oldDivision, c);
					if (newDivisionSet.size() > 1) {
						this.divisionSet.remove(oldDivision);
						this.divisionSet.addAll(newDivisionSet);
						for (Division temp : newDivisionSet) {
							stack.push(temp);
						}
						break lb;
					}// 可划分
				}
			}
			if (preDivisionSet.size() == this.divisionSet.size()) {
				break;
			}
			else {
				preDivisionSet = this.getDeepCopy(divisionSet);
			}
		}
		Division startDivision = this.getDivisionOfNode(startNode);// 起始划分
		Set<Division> endDivisionSet = new HashSet<Division>();// 终结划分集合
		for (DFANode endNode : this.endNodeSet) {
			endDivisionSet.add(this.getDivisionOfNode(endNode));
		}
		Map<Division, ArrayList<DivisionEdge>> minDFALinkTable = new Hashtable<Division, ArrayList<DivisionEdge>>();
		for (Division division : this.divisionSet) {
			ArrayList<DivisionEdge> linkEdge = new ArrayList<DivisionEdge>();
			for (char c : this.charSet) {
				Division targetDivision = this.getTargetDivision(division.getOneDFANode(), c);
				if (targetDivision != null) {
					linkEdge.add(new DivisionEdge(c, targetDivision));
				}
			}
			minDFALinkTable.put(division, linkEdge);
		}
		return new MinDFA(startDivision, endDivisionSet, charSet, minDFALinkTable);
	}

	private <T> Set<T> getDeepCopy(Set<T> set) {
		Set<T> resSet = new HashSet<T>();
		for (T t : set) {
			resSet.add(t);
		}
		return resSet;
	}

	private Set<Division> split(Division oldDivision, char c) {
		Map<Division, Set<DFANode>> map = new HashMap<Division, Set<DFANode>>();
		Set<DFANode> dfaNodeSet = oldDivision.getDfaNodeSet();
		for (DFANode node : dfaNodeSet) {
			Division targetDivision = this.getTargetDivision(node, c);
			if (map.containsKey(targetDivision)) {
				map.get(targetDivision).add(node);
			}
			else {
				Set<DFANode> set = new HashSet<DFANode>();
				set.add(node);
				map.put(targetDivision, set);
			}
		}
		Set<Division> newDivisionSet = new HashSet<Division>();
		for (Set<DFANode> nodeSet : map.values()) {
			Division division = new Division(nodeSet);
			newDivisionSet.add(division);
		}
		return newDivisionSet;
	}// 将一个划分通过某个字符划分成多个

	private Division getTargetDivision(DFANode dfaNode, char c) {
		ArrayList<DFAEdge> linkEdge = this.linkTable.get(dfaNode);
		for (int i = 0; i < linkEdge.size(); i++) {
			if (linkEdge.get(i).getValue() == c) {
				return this.getDivisionOfNode(linkEdge.get(i).getTargetNode());
			}
		}
		return null;
	}// 得到一个DFANode对于一个字符的转移划分

	private Division getDivisionOfNode(DFANode dfaNode) {
		for (Division division : this.divisionSet) {
			if (division.hasDFANode(dfaNode)) {
				return division;
			}
		}
		return null;
	}// 得到一个DFANode所在的划分

	public String toString() {
		StringBuilder resSb = new StringBuilder();
		String nextLine = "\r\n";
		resSb.append("开始节点如下：").append(nextLine);
		resSb.append(this.startNode.toString()).append(nextLine);
		resSb.append("终结节点如下：").append(nextLine);
		for (DFANode node : this.endNodeSet) {
			resSb.append(node.toString()).append(nextLine);
		}
		resSb.append("所有的转换值如下：").append(nextLine);
		for (char c : this.charSet) {
			resSb.append(c).append(nextLine);
		}
		resSb.append("邻接表如下：").append(nextLine);
		for (DFANode node : this.linkTable.keySet()) {
			ArrayList<DFAEdge> linkEdge = linkTable.get(node);
			resSb.append(node.toString() + ":   ");
			if (linkEdge.size() != 0) {
				resSb.append(linkEdge.get(0));
				for (int i = 1; i < linkEdge.size(); i++) {
					resSb.append(";  " + linkEdge.get(i).toString());
				}
			}
			resSb.append(nextLine);
		}
		resSb.append("划分如下：").append(nextLine);
		for (Division division : this.divisionSet) {
			resSb.append(division.toDetailString()).append(nextLine);
		}
		return resSb.toString();
	}
}
