package datastructure;

import java.util.ArrayList;
import java.util.TreeMap;

import common.BasicOperator;
import common.NFAException;
import common.Operator;

public class NFA_graph {
	private GraphNode startNode;// ��ʼ��
	private GraphNode endNode;// ������
	private TreeMap<GraphNode, ArrayList<GraphEdge>> linkTable = new TreeMap<GraphNode, ArrayList<GraphEdge>>();// �ڽӱ�

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
	}// �������ַ����������NFA,��Ϊ���Ӻ�ѡ�����ַ�ʽ

	public NFA_graph(char c, BasicOperator operator) throws NFAException {
		if (operator == BasicOperator.closure) {
			this.createClosureNFA(c);
		}
		else {
			throw new NFAException();
		}
	}// ��һ���ַ��հ�����ʽ���������NFA

	private void createConnectNFA(char c1, char c2) {
		// ��ʼ�������ڵ�
		this.startNode = new GraphNode();
		GraphNode node = new GraphNode();
		this.endNode = new GraphNode();
		// ��ʼ��������
		GraphEdge start_node = new GraphEdge(c1, node);
		GraphEdge node_end = new GraphEdge(c2, endNode);
		// ��ʼ�������ڵ���ڽӱ�����
		ArrayList<GraphEdge> startList = new ArrayList<GraphEdge>();
		ArrayList<GraphEdge> nodeList = new ArrayList<GraphEdge>();
		ArrayList<GraphEdge> endList = new ArrayList<GraphEdge>();
		// ���ӱߺͽڵ�
		startList.add(start_node);
		nodeList.add(node_end);
		// ����ڽӱ�
		this.linkTable.put(startNode, startList);// �������startNode�����ڽӱ�
		this.linkTable.put(node, nodeList);// ��һ������ڽӱ�
		this.linkTable.put(endNode, endList);// �����ڵ���ڽӱ�
	}// �������ַ����ӹ��������NFA

	private void createSelectNFA(char c1, char c2) {
		// ��ʼ�������ڵ�
		this.startNode = new GraphNode();
		GraphNode node1 = new GraphNode();
		GraphNode node2 = new GraphNode();
		GraphNode node3 = new GraphNode();
		GraphNode node4 = new GraphNode();
		this.endNode = new GraphNode();
		// ��ʼ��������
		GraphEdge start_node1 = new GraphEdge(Operator.��, node1);
		GraphEdge start_node3 = new GraphEdge(Operator.��, node3);
		GraphEdge node1_node2 = new GraphEdge(c1, node2);
		GraphEdge node2_end = new GraphEdge(Operator.��, this.endNode);
		GraphEdge node3_node4 = new GraphEdge(c2, node4);
		GraphEdge node4_end = new GraphEdge(Operator.��, this.endNode);
		// ��ʼ�������ڵ���ڽӽڵ�����
		ArrayList<GraphEdge> startList = new ArrayList<GraphEdge>();
		ArrayList<GraphEdge> node1List = new ArrayList<GraphEdge>();
		ArrayList<GraphEdge> node2List = new ArrayList<GraphEdge>();
		ArrayList<GraphEdge> node3List = new ArrayList<GraphEdge>();
		ArrayList<GraphEdge> node4List = new ArrayList<GraphEdge>();
		ArrayList<GraphEdge> endList = new ArrayList<GraphEdge>();
		// ���ӱߺͽڵ�
		startList.add(start_node1);
		startList.add(start_node3);
		node1List.add(node1_node2);
		node3List.add(node3_node4);
		node2List.add(node2_end);
		node4List.add(node4_end);
		// ����ڽӱ�
		this.linkTable.put(this.startNode, startList);// ��ʼ���ڽӱ�
		this.linkTable.put(node1, node1List);// ���Ϸ��ڵ��ڽӱ�
		this.linkTable.put(node3, node3List);// ���·��ڵ��ڽӱ�
		this.linkTable.put(node2, node2List);// ���Ϸ��ڵ��ڽӱ�
		this.linkTable.put(node4, node4List);// ���·��ڵ��ڽӱ�
		this.linkTable.put(this.endNode, endList);// �����ڵ�
	}// �������ַ�ѡ���������NFA

	private void createClosureNFA(char c) {
		// ��ʼ���ĸ��ڵ�
		this.startNode = new GraphNode();
		GraphNode node1 = new GraphNode();
		GraphNode node2 = new GraphNode();
		this.endNode = new GraphNode();
		// ��ʼ��������
		GraphEdge start_to_node1 = new GraphEdge(Operator.��, node1);
		GraphEdge start_to_end = new GraphEdge(Operator.��, endNode);
		GraphEdge node1_to_node2 = new GraphEdge(c, node2);
		GraphEdge node2_to_end = new GraphEdge(Operator.��, endNode);
		GraphEdge node2_to_node1 = new GraphEdge(Operator.��, node1);
		// ��ʼ���ĸ��ڵ���ڽӽڵ�����
		ArrayList<GraphEdge> startList = new ArrayList<GraphEdge>();
		ArrayList<GraphEdge> node1List = new ArrayList<GraphEdge>();
		ArrayList<GraphEdge> node2List = new ArrayList<GraphEdge>();
		ArrayList<GraphEdge> endList = new ArrayList<GraphEdge>();
		// ���ӱߺͽڵ�
		startList.add(start_to_node1);
		startList.add(start_to_end);
		node1List.add(node1_to_node2);
		node2List.add(node2_to_end);
		node2List.add(node2_to_node1);
		// ����ڽӱ�
		this.linkTable.put(startNode, startList);
		this.linkTable.put(node1, node1List);
		this.linkTable.put(node2, node2List);
		this.linkTable.put(endNode, endList);
	}// ��һ���ַ���հ��������NFA

	public void connectTo(char c) {
		GraphNode node = new GraphNode();
		GraphEdge node_start = new GraphEdge(c, this.startNode);
		ArrayList<GraphEdge> nodeList = new ArrayList<GraphEdge>();
		nodeList.add(node_start);
		this.linkTable.put(node, nodeList);
		this.startNode = node;
	}// ���ӵ�һ���ַ�֮��,�൱�����һ����ʼ�㣬�õ���һ����ԭ����ʼ��Ȩ��Ϊc�ı�

	public void connectedBy(char c) {
		GraphNode node = new GraphNode();
		GraphEdge end_node = new GraphEdge(c, node);
		ArrayList<GraphEdge> nodeList = new ArrayList<GraphEdge>();
		this.linkTable.get(this.endNode).add(end_node);
		this.linkTable.put(node, nodeList);
		this.endNode = node;
	}// ��֮������һ���ַ�,�൱�����һ���յ㣬ԭ�����յ���һ��Ȩ��Ϊc�ı�ָ��õ�

	public void connectTo(NFA_graph NFA) {
		GraphEdge NFAEnd_thisStart = new GraphEdge(Operator.��, this.startNode);
		NFA.linkTable.get(NFA.endNode).add(NFAEnd_thisStart);
		for (GraphNode node : NFA.linkTable.keySet()) {
			this.linkTable.put(node, NFA.linkTable.get(node));
		}
		this.startNode = NFA.startNode;
	}// ������һ��NFA֮��

	public void connectedBy(NFA_graph NFA) {
		GraphEdge thisEnd_NFAStart = new GraphEdge(Operator.��, NFA.startNode);
		this.linkTable.get(this.endNode).add(thisEnd_NFAStart);
		for (GraphNode node : NFA.linkTable.keySet()) {
			this.linkTable.put(node, NFA.linkTable.get(node));
		}
		this.endNode = NFA.endNode;
	}// ֮������һ��NFA

	public void select(char c) {
		// �½��ڵ�
		GraphNode node0 = new GraphNode();
		GraphNode node1 = new GraphNode();
		GraphNode node2 = new GraphNode();
		GraphNode node3 = new GraphNode();
		// �½���
		GraphEdge node0_node1 = new GraphEdge(Operator.��, node1);
		GraphEdge node1_node2 = new GraphEdge(c, node2);
		GraphEdge node2_node3 = new GraphEdge(Operator.��, node3);
		GraphEdge node0_start = new GraphEdge(Operator.��, this.startNode);
		GraphEdge end_node3 = new GraphEdge(Operator.��, node3);
		//
		ArrayList<GraphEdge> node0List = new ArrayList<GraphEdge>();
		ArrayList<GraphEdge> node1List = new ArrayList<GraphEdge>();
		ArrayList<GraphEdge> node2List = new ArrayList<GraphEdge>();
		ArrayList<GraphEdge> node3List = new ArrayList<GraphEdge>();
		// ���ӱߺͽڵ�
		node0List.add(node0_node1);
		node0List.add(node0_start);
		node1List.add(node1_node2);
		node2List.add(node2_node3);
		this.linkTable.get(this.endNode).add(end_node3);
		// �޸��ڽӱ�
		this.linkTable.put(node0, node0List);
		this.linkTable.put(node1, node1List);
		this.linkTable.put(node2, node2List);
		this.linkTable.put(node3, node3List);
		// �޸Ŀ�ʼ�ͽ����ڵ�ָ��
		this.startNode = node0;
		this.endNode = node3;
	}// ��һ���ַ���ѡ��

	public void select(NFA_graph NFA) {
		GraphNode node0 = new GraphNode();
		GraphNode node1 = new GraphNode();
		//
		GraphEdge node0_thisStart = new GraphEdge(Operator.��, this.startNode);
		GraphEdge node0_NFAStart = new GraphEdge(Operator.��, NFA.startNode);
		GraphEdge thisEnd_node1 = new GraphEdge(Operator.��, node1);
		GraphEdge NFAEnd_node1 = new GraphEdge(Operator.��, node1);
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
	}// ��һ��NFA��ѡ��

	public void closure() {
		// �½������ڵ�
		GraphNode node1 = new GraphNode();
		GraphNode node2 = new GraphNode();
		// �½�������
		GraphEdge node1_start = new GraphEdge(Operator.��, startNode);
		GraphEdge node1_node2 = new GraphEdge(Operator.��, node2);
		GraphEdge end_start = new GraphEdge(Operator.��, startNode);
		GraphEdge end_node2 = new GraphEdge(Operator.��, node2);
		// �½������ڵ���ڽӽڵ�����
		ArrayList<GraphEdge> node1List = new ArrayList<GraphEdge>();
		ArrayList<GraphEdge> node2List = new ArrayList<GraphEdge>();
		// ���ӱߺͽڵ�
		node1List.add(node1_start);
		node1List.add(node1_node2);
		this.linkTable.get(this.endNode).add(end_start);
		this.linkTable.get(this.endNode).add(end_node2);
		// �����ڽӱ�
		this.linkTable.put(node1, node1List);
		this.linkTable.put(node2, node2List);
		// ���¿�ʼ�ͽ���ָ��
		this.startNode = node1;
		this.endNode = node2;
	}// ����NFA��һ���հ�

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
	}// ��ӡ���ͼ���ڽӱ�
}
