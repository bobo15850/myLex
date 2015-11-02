package datastructure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MinDFA {
	private final Division start;
	private final Set<Division> endSet;
	private final Set<Character> charSet;
	private Map<Division, ArrayList<DivisionEdge>> linkTable = new HashMap<Division, ArrayList<DivisionEdge>>();

	public MinDFA(Division start, Set<Division> endSet, Set<Character> charSet, Map<Division, ArrayList<DivisionEdge>> linkTable) {
		this.start = start;
		this.endSet = endSet;
		this.charSet = charSet;
		this.linkTable = linkTable;
	}

	public String toString() {
		StringBuilder resSb = new StringBuilder();
		String nextline = "\r\n";
		resSb.append("��ʼ״̬Ϊ��").append(nextline);
		resSb.append(this.start).append(nextline);
		resSb.append("����״̬����Ϊ��").append(nextline);
		for (Division endDivision : this.endSet) {
			resSb.append(endDivision).append(nextline);
		}
		resSb.append("�ַ���Ϊ��").append(nextline);
		for (char c : this.charSet) {
			resSb.append(c).append(nextline);
		}
		resSb.append("�ڽ�ת����Ϊ��").append(nextline);
		for (Division division : this.linkTable.keySet()) {
			resSb.append(division).append(":  ");
			for (int i = 0; i < linkTable.get(division).size(); i++) {
				resSb.append(linkTable.get(division).get(i).toString());
			}
			resSb.append(nextline);
		}
		return resSb.toString();
	}
}
