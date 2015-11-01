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

	public void print() {
		System.out.println("开始节点如下：");
		System.out.println(this.startNode.toString());
		System.out.println("终结节点如下：");
		for (DFANode node : this.endNodeSet) {
			System.out.println(node.toString());
		}
		System.out.println("所有的转换值如下：");
		for (char c : this.edgeWeightSet) {
			System.out.println(c);
		}
		System.out.println("邻接表如下：");
		for (DFANode node : this.linkTable.keySet()) {
			ArrayList<DFAEdge> linkEdge = linkTable.get(node);
			System.out.print(node.toString() + ":   ");
			if (linkEdge.size() != 0) {
				System.out.print(linkEdge.get(0).getValue() + "-->" + linkEdge.get(0).getTargetNode().toString());
				for (int i = 1; i < linkEdge.size(); i++) {
					System.out.print(";  " + linkEdge.get(i).getValue() + "-->" + linkEdge.get(i).getTargetNode().toString());
				}
			}
			System.out.println();
		}

	}
}
