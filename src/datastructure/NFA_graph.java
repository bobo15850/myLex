package datastructure;

import java.util.ArrayList;
import java.util.TreeMap;

import common.BasicOperator;
import common.NFAException;
import common.Operator;

public class NFA_graph {
	private GraphNode startNode;// 起始点
	private GraphNode endNode;// 结束点
	private TreeMap<GraphNode, ArrayList<GraphEdge>> linkTable = new TreeMap<GraphNode, ArrayList<GraphEdge>>();// 邻接表

	public NFA_graph(char c1, char c2, BasicOperator operator) throws NFAException {
		if (operator == BasicOperator.connect) {
			this.createConnectNFA(c1, c2);
		}
		else if (operator == BasicOperator.select) {
			this.createSelectNFA(c1, c2);
		}
		else {
			throw new NFAException();
		}
	}// 以两个字符构造基础的NFA,分为连接和选择两种方式

	public NFA_graph(char c, BasicOperator operator) throws NFAException {
		if (operator == BasicOperator.closure) {
			this.createClosureNFA(c);
		}
		else {
			throw new NFAException();
		}
	}// 以一个字符闭包的形式构造基础的NFA

	private void createConnectNFA(char c1, char c2) {
		// 初始化三个节点
		this.startNode = new GraphNode();
		GraphNode node = new GraphNode();
		this.endNode = new GraphNode();
		// 初始化两条边
		GraphEdge start_node = new GraphEdge(c1, node);
		GraphEdge node_end = new GraphEdge(c2, endNode);
		// 初始化三个节点的邻接边链表
		ArrayList<GraphEdge> startList = new ArrayList<GraphEdge>();
		ArrayList<GraphEdge> nodeList = new ArrayList<GraphEdge>();
		ArrayList<GraphEdge> endList = new ArrayList<GraphEdge>();
		// 连接边和节点
		startList.add(start_node);
		nodeList.add(node_end);
		// 组合邻接表
		this.linkTable.put(startNode, startList);// 第零个（startNode）的邻接边
		this.linkTable.put(node, nodeList);// 第一个点的邻接边
		this.linkTable.put(endNode, endList);// 结束节点的邻接边
	}// 以两个字符连接构造基础的NFA

	private void createSelectNFA(char c1, char c2) {
		// 初始化六个节点
		this.startNode = new GraphNode();
		GraphNode node1 = new GraphNode();
		GraphNode node2 = new GraphNode();
		GraphNode node3 = new GraphNode();
		GraphNode node4 = new GraphNode();
		this.endNode = new GraphNode();
		// 初始化六条边
		GraphEdge start_node1 = new GraphEdge(Operator.ε, node1);
		GraphEdge start_node3 = new GraphEdge(Operator.ε, node3);
		GraphEdge node1_node2 = new GraphEdge(c1, node2);
		GraphEdge node2_end = new GraphEdge(Operator.ε, this.endNode);
		GraphEdge node3_node4 = new GraphEdge(c2, node4);
		GraphEdge node4_end = new GraphEdge(Operator.ε, this.endNode);
		// 初始化六个节点的邻接节点链表
		ArrayList<GraphEdge> startList = new ArrayList<GraphEdge>();
		ArrayList<GraphEdge> node1List = new ArrayList<GraphEdge>();
		ArrayList<GraphEdge> node2List = new ArrayList<GraphEdge>();
		ArrayList<GraphEdge> node3List = new ArrayList<GraphEdge>();
		ArrayList<GraphEdge> node4List = new ArrayList<GraphEdge>();
		ArrayList<GraphEdge> endList = new ArrayList<GraphEdge>();
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
		// 初始化四个节点
		this.startNode = new GraphNode();
		GraphNode node1 = new GraphNode();
		GraphNode node2 = new GraphNode();
		this.endNode = new GraphNode();
		// 初始化五条边
		GraphEdge start_to_node1 = new GraphEdge(Operator.ε, node1);
		GraphEdge start_to_end = new GraphEdge(Operator.ε, endNode);
		GraphEdge node1_to_node2 = new GraphEdge(c, node2);
		GraphEdge node2_to_end = new GraphEdge(Operator.ε, endNode);
		GraphEdge node2_to_node1 = new GraphEdge(Operator.ε, node1);
		// 初始化四个节点的邻接节点链表
		ArrayList<GraphEdge> startList = new ArrayList<GraphEdge>();
		ArrayList<GraphEdge> node1List = new ArrayList<GraphEdge>();
		ArrayList<GraphEdge> node2List = new ArrayList<GraphEdge>();
		ArrayList<GraphEdge> endList = new ArrayList<GraphEdge>();
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

	public void connectTo(char c) {
		GraphNode node = new GraphNode();
		GraphEdge node_start = new GraphEdge(c, this.startNode);
		ArrayList<GraphEdge> nodeList = new ArrayList<GraphEdge>();
		nodeList.add(node_start);
		this.linkTable.put(node, nodeList);
		this.startNode = node;
	}// 连接到一个字符之后,相当于添加一个起始点，该点有一条到原先起始点权重为c的边

	public void connectedBy(char c) {
		GraphNode node = new GraphNode();
		GraphEdge end_node = new GraphEdge(c, node);
		ArrayList<GraphEdge> nodeList = new ArrayList<GraphEdge>();
		this.linkTable.get(this.endNode).add(end_node);
		this.linkTable.put(node, nodeList);
		this.endNode = node;
	}// 在之后连接一个字符,相当于添加一个终点，原来的终点有一条权重为c的边指向该点

	public void connectTo(NFA_graph NFA) {
		GraphEdge NFAEnd_thisStart = new GraphEdge(Operator.ε, this.startNode);
		NFA.linkTable.get(NFA.endNode).add(NFAEnd_thisStart);
		for (GraphNode node : NFA.linkTable.keySet()) {
			this.linkTable.put(node, NFA.linkTable.get(node));
		}
		this.startNode = NFA.startNode;
	}// 连接在一个NFA之后

	public void connectedBy(NFA_graph NFA) {
		GraphEdge thisEnd_NFAStart = new GraphEdge(Operator.ε, NFA.startNode);
		this.linkTable.get(this.endNode).add(thisEnd_NFAStart);
		for (GraphNode node : NFA.linkTable.keySet()) {
			this.linkTable.put(node, NFA.linkTable.get(node));
		}
		this.endNode = NFA.endNode;
	}// 之后连接一个NFA

	public void select(char c) {
		// 新建节点
		GraphNode node0 = new GraphNode();
		GraphNode node1 = new GraphNode();
		GraphNode node2 = new GraphNode();
		GraphNode node3 = new GraphNode();
		// 新建边
		GraphEdge node0_node1 = new GraphEdge(Operator.ε, node1);
		GraphEdge node1_node2 = new GraphEdge(c, node2);
		GraphEdge node2_node3 = new GraphEdge(Operator.ε, node3);
		GraphEdge node0_start = new GraphEdge(Operator.ε, this.startNode);
		GraphEdge end_node3 = new GraphEdge(Operator.ε, node3);
		//
		ArrayList<GraphEdge> node0List = new ArrayList<GraphEdge>();
		ArrayList<GraphEdge> node1List = new ArrayList<GraphEdge>();
		ArrayList<GraphEdge> node2List = new ArrayList<GraphEdge>();
		ArrayList<GraphEdge> node3List = new ArrayList<GraphEdge>();
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

	public void select(NFA_graph NFA) {
		GraphNode node0 = new GraphNode();
		GraphNode node1 = new GraphNode();
		//
		GraphEdge node0_thisStart = new GraphEdge(Operator.ε, this.startNode);
		GraphEdge node0_NFAStart = new GraphEdge(Operator.ε, NFA.startNode);
		GraphEdge thisEnd_node1 = new GraphEdge(Operator.ε, node1);
		GraphEdge NFAEnd_node1 = new GraphEdge(Operator.ε, node1);
		//
		ArrayList<GraphEdge> node0List = new ArrayList<GraphEdge>();
		ArrayList<GraphEdge> node1List = new ArrayList<GraphEdge>();
		//
		node0List.add(node0_thisStart);
		node0List.add(node0_NFAStart);
		this.linkTable.get(this.endNode).add(thisEnd_node1);
		NFA.linkTable.get(NFA.endNode).add(NFAEnd_node1);
		//
		this.linkTable.put(node0, node0List);
		this.linkTable.put(node1, node1List);
		for (GraphNode node : NFA.linkTable.keySet()) {
			this.linkTable.put(node, NFA.linkTable.get(node));
		}
		//
		this.startNode = node0;
		this.endNode = node1;
	}// 与一个NFA做选择

	public void closure() {
		// 新建两个节点
		GraphNode node1 = new GraphNode();
		GraphNode node2 = new GraphNode();
		// 新建四条边
		GraphEdge node1_start = new GraphEdge(Operator.ε, startNode);
		GraphEdge node1_node2 = new GraphEdge(Operator.ε, node2);
		GraphEdge end_start = new GraphEdge(Operator.ε, startNode);
		GraphEdge end_node2 = new GraphEdge(Operator.ε, node2);
		// 新建两个节点的邻接节点链表
		ArrayList<GraphEdge> node1List = new ArrayList<GraphEdge>();
		ArrayList<GraphEdge> node2List = new ArrayList<GraphEdge>();
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

	public void print() {
		for (GraphNode node : this.linkTable.keySet()) {
			ArrayList<GraphEdge> temp = this.linkTable.get(node);
			System.out.print(node.getId() + ":  ");
			if (temp.size() > 0) {
				System.out.print(temp.get(0).getValue() + "," + temp.get(0).getTargetNode().getId());
			}
			for (int i = 1; i < temp.size(); i++) {
				System.out.print(";  " + temp.get(i).getValue() + "," + temp.get(i).getTargetNode().getId());
			}
			System.out.println();
		}
	}// 打印输出图的邻接表
}
