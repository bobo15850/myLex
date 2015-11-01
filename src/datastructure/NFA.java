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
	private NFANode startNode;// ��ʼ��
	private NFANode endNode;// ������
	private Set<Character> edgeWeightSet = new TreeSet<Character>();// Ȩ�صļ���
	private Map<NFANode, ArrayList<NFAEdge>> linkTable = new TreeMap<NFANode, ArrayList<NFAEdge>>();// �ڽӱ�

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
	}// �������ַ����������NFA,��Ϊ���Ӻ�ѡ�����ַ�ʽ

	public NFA(char c, Util.UnaryBasicOperator operator) throws NFAException {
		if (operator == Util.UnaryBasicOperator.closure) {
			this.createClosureNFA(c);
		}
		else {
			throw new NFAException();
		}
	}// ��һ���ַ��հ�����ʽ���������NFA

	private void createConnectNFA(char c1, char c2) {
		this.edgeWeightSet.add(c1);
		this.edgeWeightSet.add(c2);
		// ��ʼ�������ڵ�
		this.startNode = new NFANode();
		NFANode node = new NFANode();
		this.endNode = new NFANode();
		// ��ʼ��������
		NFAEdge start_node = new NFAEdge(c1, node);
		NFAEdge node_end = new NFAEdge(c2, endNode);
		// ��ʼ�������ڵ���ڽӱ�����
		ArrayList<NFAEdge> startList = new ArrayList<NFAEdge>();
		ArrayList<NFAEdge> nodeList = new ArrayList<NFAEdge>();
		ArrayList<NFAEdge> endList = new ArrayList<NFAEdge>();
		// ���ӱߺͽڵ�
		startList.add(start_node);
		nodeList.add(node_end);
		// ����ڽӱ�
		this.linkTable.put(startNode, startList);// �������startNode�����ڽӱ�
		this.linkTable.put(node, nodeList);// ��һ������ڽӱ�
		this.linkTable.put(endNode, endList);// �����ڵ���ڽӱ�
	}// �������ַ����ӹ��������NFA

	private void createSelectNFA(char c1, char c2) {
		this.edgeWeightSet.add(c1);
		this.edgeWeightSet.add(c2);
		// ��ʼ�������ڵ�
		this.startNode = new NFANode();
		NFANode node1 = new NFANode();
		NFANode node2 = new NFANode();
		NFANode node3 = new NFANode();
		NFANode node4 = new NFANode();
		this.endNode = new NFANode();
		// ��ʼ��������
		NFAEdge start_node1 = new NFAEdge(Util.SpecialOperand.��, node1);
		NFAEdge start_node3 = new NFAEdge(Util.SpecialOperand.��, node3);
		NFAEdge node1_node2 = new NFAEdge(c1, node2);
		NFAEdge node2_end = new NFAEdge(Util.SpecialOperand.��, this.endNode);
		NFAEdge node3_node4 = new NFAEdge(c2, node4);
		NFAEdge node4_end = new NFAEdge(Util.SpecialOperand.��, this.endNode);
		// ��ʼ�������ڵ���ڽӽڵ�����
		ArrayList<NFAEdge> startList = new ArrayList<NFAEdge>();
		ArrayList<NFAEdge> node1List = new ArrayList<NFAEdge>();
		ArrayList<NFAEdge> node2List = new ArrayList<NFAEdge>();
		ArrayList<NFAEdge> node3List = new ArrayList<NFAEdge>();
		ArrayList<NFAEdge> node4List = new ArrayList<NFAEdge>();
		ArrayList<NFAEdge> endList = new ArrayList<NFAEdge>();
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
		this.edgeWeightSet.add(c);
		// ��ʼ���ĸ��ڵ�
		this.startNode = new NFANode();
		NFANode node1 = new NFANode();
		NFANode node2 = new NFANode();
		this.endNode = new NFANode();
		// ��ʼ��������
		NFAEdge start_to_node1 = new NFAEdge(Util.SpecialOperand.��, node1);
		NFAEdge start_to_end = new NFAEdge(Util.SpecialOperand.��, endNode);
		NFAEdge node1_to_node2 = new NFAEdge(c, node2);
		NFAEdge node2_to_end = new NFAEdge(Util.SpecialOperand.��, endNode);
		NFAEdge node2_to_node1 = new NFAEdge(Util.SpecialOperand.��, node1);
		// ��ʼ���ĸ��ڵ���ڽӽڵ�����
		ArrayList<NFAEdge> startList = new ArrayList<NFAEdge>();
		ArrayList<NFAEdge> node1List = new ArrayList<NFAEdge>();
		ArrayList<NFAEdge> node2List = new ArrayList<NFAEdge>();
		ArrayList<NFAEdge> endList = new ArrayList<NFAEdge>();
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

	public void connectToChar(char c) {
		this.edgeWeightSet.add(c);
		NFANode node = new NFANode();
		NFAEdge node_start = new NFAEdge(c, this.startNode);
		ArrayList<NFAEdge> nodeList = new ArrayList<NFAEdge>();
		nodeList.add(node_start);
		this.linkTable.put(node, nodeList);
		this.startNode = node;
	}// ���ӵ�һ���ַ�֮��,�൱�����һ����ʼ�㣬�õ���һ����ԭ����ʼ��Ȩ��Ϊc�ı�

	public void connectedByChar(char c) {
		this.edgeWeightSet.add(c);
		NFANode node = new NFANode();
		NFAEdge end_node = new NFAEdge(c, node);
		ArrayList<NFAEdge> nodeList = new ArrayList<NFAEdge>();
		this.linkTable.get(this.endNode).add(end_node);
		this.linkTable.put(node, nodeList);
		this.endNode = node;
	}// ��֮������һ���ַ�,�൱�����һ���յ㣬ԭ�����յ���һ��Ȩ��Ϊc�ı�ָ��õ�

	public void connectToNFA(NFA NFA) {
		this.edgeWeightSet.addAll(NFA.edgeWeightSet);
		NFAEdge NFAEnd_thisStart = new NFAEdge(Util.SpecialOperand.��, this.startNode);
		NFA.linkTable.get(NFA.endNode).add(NFAEnd_thisStart);
		for (NFANode node : NFA.linkTable.keySet()) {
			this.linkTable.put(node, NFA.linkTable.get(node));
		}
		this.startNode = NFA.startNode;
	}// ������һ��NFA֮��

	public void connectedByNFA(NFA NFA) {
		this.edgeWeightSet.addAll(NFA.edgeWeightSet);
		NFAEdge thisEnd_NFAStart = new NFAEdge(Util.SpecialOperand.��, NFA.startNode);
		this.linkTable.get(this.endNode).add(thisEnd_NFAStart);
		for (NFANode node : NFA.linkTable.keySet()) {
			this.linkTable.put(node, NFA.linkTable.get(node));
		}
		this.endNode = NFA.endNode;
	}// ֮������һ��NFA

	public void selectChar(char c) {
		this.edgeWeightSet.add(c);
		// �½��ڵ�
		NFANode node0 = new NFANode();
		NFANode node1 = new NFANode();
		NFANode node2 = new NFANode();
		NFANode node3 = new NFANode();
		// �½���
		NFAEdge node0_node1 = new NFAEdge(Util.SpecialOperand.��, node1);
		NFAEdge node1_node2 = new NFAEdge(c, node2);
		NFAEdge node2_node3 = new NFAEdge(Util.SpecialOperand.��, node3);
		NFAEdge node0_start = new NFAEdge(Util.SpecialOperand.��, this.startNode);
		NFAEdge end_node3 = new NFAEdge(Util.SpecialOperand.��, node3);
		//
		ArrayList<NFAEdge> node0List = new ArrayList<NFAEdge>();
		ArrayList<NFAEdge> node1List = new ArrayList<NFAEdge>();
		ArrayList<NFAEdge> node2List = new ArrayList<NFAEdge>();
		ArrayList<NFAEdge> node3List = new ArrayList<NFAEdge>();
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

	public void selectNFA(NFA NFA) {
		this.edgeWeightSet.addAll(NFA.edgeWeightSet);
		NFANode node0 = new NFANode();
		NFANode node1 = new NFANode();
		//
		NFAEdge node0_thisStart = new NFAEdge(Util.SpecialOperand.��, this.startNode);
		NFAEdge node0_NFAStart = new NFAEdge(Util.SpecialOperand.��, NFA.startNode);
		NFAEdge thisEnd_node1 = new NFAEdge(Util.SpecialOperand.��, node1);
		NFAEdge NFAEnd_node1 = new NFAEdge(Util.SpecialOperand.��, node1);
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
	}// ��һ��NFA��ѡ��

	public void closure() {
		// �½������ڵ�
		NFANode node1 = new NFANode();
		NFANode node2 = new NFANode();
		// �½�������
		NFAEdge node1_start = new NFAEdge(Util.SpecialOperand.��, startNode);
		NFAEdge node1_node2 = new NFAEdge(Util.SpecialOperand.��, node2);
		NFAEdge end_start = new NFAEdge(Util.SpecialOperand.��, startNode);
		NFAEdge end_node2 = new NFAEdge(Util.SpecialOperand.��, node2);
		// �½������ڵ���ڽӽڵ�����
		ArrayList<NFAEdge> node1List = new ArrayList<NFAEdge>();
		ArrayList<NFAEdge> node2List = new ArrayList<NFAEdge>();
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

	public Set<Character> getEdgeWeightSet() {
		return edgeWeightSet;
	}

	public Map<NFANode, ArrayList<NFAEdge>> getLinkTable() {
		return linkTable;
	}

	public String toString() {
		StringBuilder resSb = new StringBuilder();
		String nextLine = "\r\n";
		resSb.append("��ʼ�ڵ�Ϊ��").append(nextLine);
		resSb.append(this.startNode).append(nextLine);
		resSb.append("�����ڵ�Ϊ��").append(nextLine);
		resSb.append(this.endNode).append(nextLine);
		resSb.append("�ڽӱ�Ϊ��").append(nextLine);
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
	}// ��ӡ���ͼ���ڽӱ�

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
				else if (val == Util.SpecialOperand.��) {
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
	}// �õ�ĳһ��DFAͨ��ĳһȨ�ؿ��Դﵽ��NFA�ڵ�ļ��������ɵ�DFANode

	public DFANode getStartNodeEpsilonClosure() {
		Set<NFANode> nfaNodeSet = new TreeSet<NFANode>();
		nfaNodeSet.add(startNode);
		DFANode startDFANode = new DFANode(nfaNodeSet);
		startDFANode.ebsilonClosure(this);
		return startDFANode;
	}// �õ���ʼ�ڵ��EpsilonClosure

	public boolean isHasEndNFANode(final DFANode dfaNode) {
		if (dfaNode == null) {
			return false;
		}
		return dfaNode.cantains(endNode);
	}// �ж�һ��dfa�ڵ��Ƿ���NFA���ս�ڵ�
}
