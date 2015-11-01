package datastructure;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class DFA {
	private DFANode startNode;// ��ʼ�ڵ�
	private Set<DFANode> endNodeSet = new TreeSet<DFANode>();// ��ֹ�ڵ㼯��
	private Set<Character> edgeWeightSet = new TreeSet<Character>();// ����ת�����ӵļ���
	private Map<DFANode, ArrayList<DFAEdge>> linkTable = new TreeMap<DFANode, ArrayList<DFAEdge>>();// �ڽӱ�

	public DFA(NFA nfa) {
		this.edgeWeightSet = nfa.getEdgeWeightSet();// �õ����еı��ϵ�ֵ�ļ���
		this.startNode = nfa.getStartNodeEpsilonClosure();// �õ���ʼ�ڵ�
		Queue<DFANode> queue = new LinkedList<DFANode>();
		queue.add(startNode);
		while (!queue.isEmpty()) {
			DFANode dfaNode = queue.poll();
			ArrayList<DFAEdge> thisDfaNodeLink = new ArrayList<DFAEdge>();
			if (nfa.isHasEndNFANode(dfaNode)) {
				this.endNodeSet.add(dfaNode);
			}// �ж��Ƿ�Ϊ�ս�ڵ�
			for (char c : this.edgeWeightSet) {
				DFANode newDfaNode = nfa.gettargetDFANode(dfaNode, c);
				if (newDfaNode != null) {
					newDfaNode.ebsilonClosure(nfa);// ��հ�
					if (!(queue.contains(newDfaNode) || linkTable.containsKey(newDfaNode) || dfaNode.equals(newDfaNode))) {
						queue.offer(newDfaNode);
					}// �������µ�״̬���ϣ�������ӵ�������
					DFAEdge dfaEdge = new DFAEdge(c, newDfaNode);
					thisDfaNodeLink.add(dfaEdge);
				}// ȷ���õ����µ�DFANode��Ϊ��
			}
			this.linkTable.put(dfaNode, thisDfaNodeLink);// ���ýڵ���ڽӽڵ���������ڽӱ���
		}
	}// ��NFA����DFA

	public String toString() {
		StringBuilder resSb = new StringBuilder();
		String nextLine = "\r\n";
		resSb.append("��ʼ�ڵ����£�").append(nextLine);
		resSb.append(this.startNode.toString()).append(nextLine);
		resSb.append("�ս�ڵ����£�").append(nextLine);
		for (DFANode node : this.endNodeSet) {
			resSb.append(node.toString()).append(nextLine);
		}
		resSb.append("���е�ת��ֵ���£�").append(nextLine);
		for (char c : this.edgeWeightSet) {
			resSb.append(c).append(nextLine);
		}
		resSb.append("�ڽӱ����£�").append(nextLine);
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
