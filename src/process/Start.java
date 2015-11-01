package process;

import common.NotFoundREsException;
import datastructure.DFA;
import datastructure.NFA;
import datastructure.REFile;
import datastructure.StandardRE;

public class Start {
	public static void main(String[] args) {
		String path = "D:/study/javaÎÄ¼þ/myLex/files/REs.txt";
		REFile handler = new REFile(path);
		String targetRE = handler.getTargetRE();
		System.out.println(targetRE);
		try {
			StandardRE re_to_nfa = new StandardRE(targetRE);
			NFA nfa = re_to_nfa.getNFA();
			System.out.println(nfa.toString());
			DFA dfa = new DFA(nfa);
			System.out.println(dfa.toString());
		} catch (NotFoundREsException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
}
