package datastructure;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import common.NFAException;
import common.Util;

public class NFA {
	private NFANode startNode;// 起始点
	private NFANode endNode;// 结束点
	private Set<Character> edgeWeightSet = new TreeSet<Character>();// 权重的集合
	private Map<NFANode, ArrayList<NFAEdge>> linkTable = new TreeMap<NFANode, ArrayList<NFAEdge>>();// 邻接表

	public NFA(char c1, char c2, Util.BinaryBasicOperator operator) throws NFAException {
		if (operator == Util.BinaryBasicOperator.connect) {
			this.createConnectNFA(c1, c2);
		}
		else if (operator == Util.BinaryBasicOperator.select) {
			this.createSelectNFA(c1, c2);
		}
		else {
			throw new NFAException();
		}
	}// 以两个字符构造基础的NFA,分为连接和选择两种方式

	public NFA(char c, Util.UnaryBasicOperator operator) throws NFAException {
		if (operator == Util.UnaryBasicOperator.closure) {
			this.createClosureNFA(c);
		}
		else {
			throw new NFAException();
		}
	}// 以一个字符闭包的形式构造基础的NFA

	private void createConnectNFA(char c1, char c2) {
		this.edgeWeightSet.add(c1);
		this.edgeWeightSet.add(c2);
		// 初始化三个节点
		this.startNode = new NFANode();
		NFANode node = new NFANode();
		this.endNode = new NFANode();
		// 初始化两条边
		NFAEdge start_node = new NFAEdge(c1, node);
		NFAEdge node_end = new NFAEdge(c2, endNode);
		// 初始化三个节点的邻接边链表
		ArrayList<NFAEdge> startList = new ArrayList<NFAEdge>();
		ArrayList<NFAEdge> nodeList = new ArrayList<NFAEdge>();
		ArrayList<NFAEdge> endList = new ArrayList<NFAEdge>();
		// 连接边和节点
		startList.add(start_node);
		nodeList.add(node_end);
		// 组合邻接表
		this.linkTable.put(startNode, startList);// 第零个（startNode）的邻接边
		this.linkTable.put(node, nodeList);// 第一个点的邻接边
		this.linkTable.put(endNode, endList);// 结束节点的邻接边
	}// 以两个字符连接构造基础的NFA

	private void createSelectNFA(char c1, char c2) {
		this.edgeWeightSet.add(c1);
		this.edgeWeightSet.add(c2);
		// 初始化六个节点
		this.startNode = new NFANode();
		NFANode node1 = new NFANode();
		NFANode node2 = new NFANode();
		NFANode node3 = new NFANode();
		NFANode node4 = new NFANode();
		this.endNode = new NFANode();
		// 初始化六条边
		NFAEdge start_node1 = new NFAEdge(Util.SpecialOperand.ε, node1);
		NFAEdge start_node3 = new NFAEdge(Util.SpecialOperand.ε, node3);
		NFAEdge node1_node2 = new NFAEdge(c1, node2);
		NFAEdge node2_end = new NFAEdge(Util.SpecialOperand.ε, this.endNode);
		NFAEdge node3_node4 = new NFAEdge(c2, node4);
		NFAEdge node4_end = new NFAEdge(Util.SpecialOperand.ε, this.endNode);
		// 初始化六个节点的邻接节点链表
		ArrayList<NFAEdge> startList = new ArrayList<NFAEdge>();
		ArrayList<NFAEdge> node1List = new ArrayList<NFAEdge>();
		ArrayList<NFAEdge> node2List = new ArrayList<NFAEdge>();
		ArrayList<NFAEdge> node3List = new ArrayList<NFAEdge>();
		ArrayList<NFAEdge> node4List = new ArrayList<NFAEdge>();
		ArrayList<NFAEdge> endList = new ArrayList<NFAEdge>();
		// 连接边和节点
		startList.add(start_node1);
		startList.add(start_node3);
		node1List.add(node1_node2);
		node3List.add(node3_node4);
		node2List.add(node2_end);
		node4List.add(node4_end);
		// 组合邻接表
		this.linkTable.put(this.startNode, startList);// 起始点邻接边
		this.linkTable.put(node1, node1List);// 左上方节点邻接边
		this.linkTable.put(node3, node3List);// 左下方节点邻接边
		this.linkTable.put(node2, node2List);// 右上方节点邻接边
		this.linkTable.put(node4, node4List);// 右下方节点邻接边
		this.linkTable.put(this.endNode, endList);// 结束节点
	}// 以两个字符选择构造基础的NFA

	private void createClosureNFA(char c) {
		this.edgeWeightSet.add(c);
		// 初始化四个节点
		this.startNode = new NFANode();
		NFANode node1 = new NFANode();
		NFANode node2 = new NFANode();
		this.endNode = new NFANode();
		// 初始化五条边
		NFAEdge start_to_node1 = new NFAEdge(Util.SpecialOperand.ε, node1);
		NFAEdge start_to_end = new NFAEdge(Util.SpecialOperand.ε, endNode);
		NFAEdge node1_to_node2 = new NFAEdge(c, node2);
		NFAEdge node2_to_end = new NFAEdge(Util.SpecialOperand.ε, endNode);
		NFAEdge node2_to_node1 = new NFAEdge(Util.SpecialOperand.ε, node1);
		// 初始化四个节点的邻接节点链表
		ArrayList<NFAEdge> startList = new ArrayList<NFAEdge>();
		ArrayList<NFAEdge> node1List = new ArrayList<NFAEdge>();
		ArrayList<NFAEdge> node2List = new ArrayList<NFAEdge>();
		ArrayList<NFAEdge> endList = new ArrayList<NFAEdge>();
		// 连接边和节点
		startList.add(start_to_node1);
		startList.add(start_to_end);
		node1List.add(node1_to_node2);
		node2List.add(node2_to_end);
		node2List.add(node2_to_node1);
		// 组合邻接表
		this.linkTable.put(startNode, startList);
		this.linkTable.put(node1, node1List);
		this.linkTable.put(node2, node2List);
		this.linkTable.put(endNode, endList);
	}// 以一个字符求闭包构造基础NFA

	public void connectToChar(char c) {
		this.edgeWeightSet.add(c);
		NFANode node = new NFANode();
		NFAEdge node_start = new NFAEdge(c, this.startNode);
		ArrayList<NFAEdge> nodeList = new ArrayList<NFAEdge>();
		nodeList.add(node_start);
		this.linkTable.put(node, nodeList);
		this.startNode = node;
	}// 连接到一个字符之后,相当于添加一个起始点，该点有一条到原先起始点权重为c的边

	public void connectedByChar(char c) {
		this.edgeWeightSet.add(c);
		NFANode node = new NFANode();
		NFAEdge end_node = new NFAEdge(c, node);
		ArrayList<NFAEdge> nodeList = new ArrayList<NFAEdge>();
		this.linkTable.get(this.endNode).add(end_node);
		this.linkTable.put(node, nodeList);
		this.endNode = node;
	}// 在之后连接一个字符,相当于添加一个终点，原来的终点有一条权重为c的边指向该点

	public void connectToNFA(NFA NFA) {
		this.edgeWeightSet.addAll(NFA.edgeWeightSet);
		NFAEdge NFAEnd_thisStart = new NFAEdge(Util.SpecialOperand.ε, this.startNode);
		NFA.linkTable.get(NFA.endNode).add(NFAEnd_thisStart);
		for (NFANode node : NFA.linkTable.keySet()) {
			this.linkTable.put(node, NFA.linkTable.get(node));
		}
		this.startNode = NFA.startNode;
	}// 连接在一个NFA之后

	public void connectedByNFA(NFA NFA) {
		this.edgeWeightSet.addAll(NFA.edgeWeightSet);
		NFAEdge thisEnd_NFAStart = new NFAEdge(Util.SpecialOperand.ε, NFA.startNode);
		this.linkTable.get(this.endNode).add(thisEnd_NFAStart);
		for (NFANode node : NFA.linkTable.keySet()) {
			this.linkTable.put(node, NFA.linkTable.get(node));
		}
		this.endNode = NFA.endNode;
	}// 之后连接一个NFA

	public void selectChar(char c) {
		this.edgeWeightSet.add(c);
		// 新建节点
		NFANode node0 = new NFANode();
		NFANode node1 = new NFANode();
		NFANode node2 = new NFANode();
		NFANode node3 = new NFANode();
		// 新建边
		NFAEdge node0_node1 = new NFAEdge(Util.SpecialOperand.ε, node1);
		NFAEdge node1_node2 = new NFAEdge(c, node2);
		NFAEdge node2_node3 = new NFAEdge(Util.SpecialOperand.ε, node3);
		NFAEdge node0_start = new NFAEdge(Util.SpecialOperand.ε, this.startNode);
		NFAEdge end_node3 = new NFAEdge(Util.SpecialOperand.ε, node3);
		//
		ArrayList<NFAEdge> node0List = new ArrayList<NFAEdge>();
		ArrayList<NFAEdge> node1List = new ArrayList<NFAEdge>();
		ArrayList<NFAEdge> node2List = new ArrayList<NFAEdge>();
		ArrayList<NFAEdge> node3List = new ArrayList<NFAEdge>();
		// 连接边和节点
		node0List.add(node0_node1);
		node0List.add(node0_start);
		node1List.add(node1_node2);
		node2List.add(node2_node3);
		this.linkTable.get(this.endNode).add(end_node3);
		// 修改邻接表
		this.linkTable.put(node0, node0List);
		this.linkTable.put(node1, node1List);
		this.linkTable.put(node2, node2List);
		this.linkTable.put(node3, node3List);
		// 修改开始和结束节点指针
		this.startNode = node0;
		this.endNode = node3;
	}// 与一个字符做选择

	public void selectNFA(NFA NFA) {
		this.edgeWeightSet.addAll(NFA.edgeWeightSet);
		NFANode node0 = new NFANode();
		NFANode node1 = new NFANode();
		//
		NFAEdge node0_thisStart = new NFAEdge(Util.SpecialOperand.ε, this.startNode);
		NFAEdge node0_NFAStart = new NFAEdge(Util.SpecialOperand.ε, NFA.startNode);
		NFAEdge thisEnd_node1 = new NFAEdge(Util.SpecialOperand.ε, node1);
		NFAEdge NFAEnd_node1 = new NFAEdge(Util.SpecialOperand.ε, node1);
		//
		ArrayList<NFAEdge> node0List = new ArrayList<NFAEdge>();
		ArrayList<NFAEdge> node1List = new ArrayList<NFAEdge>();
		//
		node0List.add(node0_thisStart);
		node0List.add(node0_NFAStart);
		this.linkTable.get(this.endNode).add(thisEnd_node1);
		NFA.linkTable.get(NFA.endNode).add(NFAEnd_node1);
		//
		this.linkTable.put(node0, node0List);
		this.linkTable.put(node1, node1List);
		for (NFANode node : NFA.linkTable.keySet()) {
			this.linkTable.put(node, NFA.linkTable.get(node));
		}
		//
		this.startNode = node0;
		this.endNode = node1;
	}// 与一个NFA做选择

	public void closure() {
		// 新建两个节点
		NFANode node1 = new NFANode();
		NFANode node2 = new NFANode();
		// 新建四条边
		NFAEdge node1_start = new NFAEdge(Util.SpecialOperand.ε, startNode);
		NFAEdge node1_node2 = new NFAEdge(Util.SpecialOperand.ε, node2);
		NFAEdge end_start = new NFAEdge(Util.SpecialOperand.ε, startNode);
		NFAEdge end_node2 = new NFAEdge(Util.SpecialOperand.ε, node2);
		// 新建两个节点的邻接节点链表
		ArrayList<NFAEdge> node1List = new ArrayList<NFAEdge>();
		ArrayList<NFAEdge> node2List = new ArrayList<NFAEdge>();
		// 连接边和节点
		node1List.add(node1_start);
		node1List.add(node1_node2);
		this.linkTable.get(this.endNode).add(end_start);
		this.linkTable.get(this.endNode).add(end_node2);
		// 更新邻接表
		this.linkTable.put(node1, node1List);
		this.linkTable.put(node2, node2List);
		// 更新开始和结束指针
		this.startNode = node1;
		this.endNode = node2;
	}// 将该NFA做一个闭包

	public Set<Character> getEdgeWeightSet() {
		return edgeWeightSet;
	}

	public Map<NFANode, ArrayList<NFAEdge>> getLinkTable() {
		return linkTable;
	}

	public String toString() {
		StringBuilder resSb = new StringBuilder();
		String nextLine = "\r\n";
		resSb.append("开始节点为：").append(nextLine);
		resSb.append(this.startNode).append(nextLine);
		resSb.append("结束节点为：").append(nextLine);
		resSb.append(this.endNode).append(nextLine);
		resSb.append("邻接表为：").append(nextLine);
		for (NFANode node : this.linkTable.keySet()) {
			ArrayList<NFAEdge> temp = this.linkTable.get(node);
			resSb.append(node.toString() + ":  ");
			if (temp.size() > 0) {
				resSb.append(temp.get(0).toString());
			}
			for (int i = 1; i < temp.size(); i++) {
				resSb.append(";  " + temp.get(i).toString());
			}
			resSb.append(nextLine);
		}
		return resSb.toString();
	}// 打印输出图的邻接表

	public DFANode gettargetDFANode(final DFANode dfaNode, char c) {
		final Set<NFANode> rawNfaNode = dfaNode.getValue();
		Set<NFANode> newNfaNode = new TreeSet<NFANode>();
		Set<NFANode> vistedNfaNode = new TreeSet<NFANode>();
		Queue<NFANode> queue = new LinkedList<NFANode>();
		queue.addAll(rawNfaNode);
		while (!queue.isEmpty()) {
			NFANode tempNode = queue.poll();
			ArrayList<NFAEdge> linkEdge = this.linkTable.get(tempNode);
			for (int i = 0; i < linkEdge.size(); i++) {
				char val = linkEdge.get(i).getValue();
				NFANode node = linkEdge.get(i).getTargetNode();
				if (val == c) {
					newNfaNode.add(node);
				}
				else if (val == Util.SpecialOperand.ε) {
					if (!(vistedNfaNode.contains(node) || tempNode.equals(node) || queue.contains(node))) {
						queue.offer(node);
					}
				}
			}
			vistedNfaNode.add(tempNode);
		}
		DFANode newDfaNode = null;
		if (newNfaNode.size() != 0) {
			newDfaNode = new DFANode(newNfaNode);
		}
		return newDfaNode;
	}// 得到某一个DFA通过某一权重可以达到的NFA节点的集合所构成的DFANode

	public DFANode getStartNodeEpsilonClosure() {
		Set<NFANode> nfaNodeSet = new TreeSet<NFANode>();
		nfaNodeSet.add(startNode);
		DFANode startDFANode = new DFANode(nfaNodeSet);
		startDFANode.ebsilonClosure(this);
		return startDFANode;
	}// 得到开始节点的EpsilonClosure

	public boolean isHasEndNFANode(final DFANode dfaNode) {
		if (dfaNode == null) {
			return false;
		}
		return dfaNode.cantains(endNode);
	}// 判断一个dfa节点是否含有NFA的终结节点
}
