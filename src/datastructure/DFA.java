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
	private DFANode startNode;// ��ʼ�ڵ�
	private Set<DFANode> endNodeSet = new TreeSet<DFANode>();// ��ֹ�ڵ㼯��
	private Set<Character> charSet = new TreeSet<Character>();// ����ת�����ӵļ���
	private Map<DFANode, ArrayList<DFAEdge>> linkTable = new TreeMap<DFANode, ArrayList<DFAEdge>>();// �ڽӱ�
	private Set<Division> divisionSet = new HashSet<Division>();// ���ּ���

	public DFA(NFA nfa) {
		this.charSet = this.getDeepCopy(nfa.getCharSet());// �õ����еı��ϵ�ֵ�ļ���
		if (this.charSet.contains(Util.SpecialOperand.��)) {
			this.charSet.remove(Util.SpecialOperand.��);
		}
		this.startNode = nfa.getStartNodeEpsilonClosure();// �õ���ʼ�ڵ�
		Queue<DFANode> queue = new LinkedList<DFANode>();
		queue.add(startNode);
		while (!queue.isEmpty()) {
			DFANode dfaNode = queue.poll();
			ArrayList<DFAEdge> thisDfaNodeLink = new ArrayList<DFAEdge>();
			if (nfa.isHasEndNFANode(dfaNode)) {
				this.endNodeSet.add(dfaNode);
			}// �ж��Ƿ�Ϊ�ս�ڵ�
			for (char c : this.charSet) {
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

	public MinDFA getMinDFA() {
		Division unacceptedDivison = new Division(endNodeSet);// ���ɽ���״̬��
		Set<DFANode> acceptedNodeSet = new HashSet<DFANode>();
		for (DFANode node : this.linkTable.keySet()) {
			if (!this.endNodeSet.contains(node)) {
				acceptedNodeSet.add(node);
			}
		}
		Division acceptedDivision = new Division(acceptedNodeSet);// �ɽ���״̬��
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
					}// �ɻ���
				}
			}
			if (preDivisionSet.size() == this.divisionSet.size()) {
				break;
			}
			else {
				preDivisionSet = this.getDeepCopy(divisionSet);
			}
		}
		Division startDivision = this.getDivisionOfNode(startNode);// ��ʼ����
		Set<Division> endDivisionSet = new HashSet<Division>();// �սỮ�ּ���
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
	}// ��һ������ͨ��ĳ���ַ����ֳɶ��

	private Division getTargetDivision(DFANode dfaNode, char c) {
		ArrayList<DFAEdge> linkEdge = this.linkTable.get(dfaNode);
		for (int i = 0; i < linkEdge.size(); i++) {
			if (linkEdge.get(i).getValue() == c) {
				return this.getDivisionOfNode(linkEdge.get(i).getTargetNode());
			}
		}
		return null;
	}// �õ�һ��DFANode����һ���ַ���ת�ƻ���

	private Division getDivisionOfNode(DFANode dfaNode) {
		for (Division division : this.divisionSet) {
			if (division.hasDFANode(dfaNode)) {
				return division;
			}
		}
		return null;
	}// �õ�һ��DFANode���ڵĻ���

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
		for (char c : this.charSet) {
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
		resSb.append("�������£�").append(nextLine);
		for (Division division : this.divisionSet) {
			resSb.append(division.toDetailString()).append(nextLine);
		}
		return resSb.toString();
	}
}
