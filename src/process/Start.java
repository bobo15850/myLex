package process;
import common.NotFoundREsException;
import datastructure.NFA;

public class Start {
	public static void main(String[] args) {
		String path = "D:/study/javaÎÄ¼þ/myLex/files/REs.txt";
		_0_REsHandler handler = new _0_REsHandler(path);
		String targetRE = handler.getTargetRE();
		System.out.println(targetRE);
		try {
			_1_RE_to_NFA re_to_nfa = new _1_RE_to_NFA(targetRE);
			NFA nfa = re_to_nfa.getNFA();
			nfa.print();
		} catch (NotFoundREsException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
}
