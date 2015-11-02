package datastructure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import common.NotEndException;
import common.UnknownCharException;

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
		resSb.append("开始状态为：").append(nextline);
		resSb.append(this.start).append(nextline);
		resSb.append("结束状态集合为：").append(nextline);
		for (Division endDivision : this.endSet) {
			resSb.append(endDivision).append(nextline);
		}
		resSb.append("字符集为：").append(nextline);
		for (char c : this.charSet) {
			resSb.append(c).append(nextline);
		}
		resSb.append("邻接转换表为：").append(nextline);
		for (Division division : this.linkTable.keySet()) {
			resSb.append(division).append(":  ");
			for (int i = 0; i < linkTable.get(division).size(); i++) {
				resSb.append(linkTable.get(division).get(i).toString());
			}
			resSb.append(nextline);
		}
		return resSb.toString();
	}

	public List<String> getTokenList(String str) throws UnknownCharException, NotEndException {
		Division curState = start;
		List<String> tokenList = new ArrayList<String>();
		String token = "";
		for (int i = 0; i < str.length(); i++) {
			if (this.charSet.contains(str.charAt(i))) {
				ArrayList<DivisionEdge> linkEdge = this.linkTable.get(curState);
				boolean hasNext = false;
				for (DivisionEdge edge : linkEdge) {
					if (edge.getWeight() == str.charAt(i)) {
						token += str.charAt(i);
						curState = edge.getTargetDivision();
						hasNext = true;
					}
				}
				if (!hasNext) {
					if (this.endSet.contains(curState)) {
						tokenList.add(token);
						token = "";
						curState = start;
						str = str.substring(i);
						i = -1;
					}
					else {
						throw new NotEndException();
					}
				}
				if (i == str.length() - 1) {
					if (this.endSet.contains(curState)) {
						tokenList.add(token);
					}
					else {
						throw new NotEndException();
					}
				}
			}
			else {
				throw new UnknownCharException();
			}
		}
		return tokenList;
	}
}
