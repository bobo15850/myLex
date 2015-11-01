package datastructure;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class DFA {
	private DFANode startNode;// 开始节点
	private Set<DFANode> endNodeSet = new TreeSet<DFANode>();// 终止节点集合
	private Set<Character> edgeWeightSet = new TreeSet<Character>();// 所有转化因子的集合
	private Map<DFANode, ArrayList<DFAEdge>> linkTable = new TreeMap<DFANode, ArrayList<DFAEdge>>();// 邻接表

	public DFA(NFA nfa) {
		this.edgeWeightSet = nfa.getEdgeWeightSet();// 得到所有的边上的值的集合
		this.startNode = nfa.getStartNodeEpsilonClosure();// 得到开始节点
		Queue<DFANode> queue = new LinkedList<DFANode>();
		queue.add(startNode);
		while (!queue.isEmpty()) {
			DFANode dfaNode = queue.poll();
			ArrayList<DFAEdge> thisDfaNodeLink = new ArrayList<DFAEdge>();
			if (nfa.isHasEndNFANode(dfaNode)) {
				this.endNodeSet.add(dfaNode);
			}// 判断是否为终结节点
			for (char c : this.edgeWeightSet) {
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
		for (char c : this.edgeWeightSet) {
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
		return resSb.toString();
	}
}
