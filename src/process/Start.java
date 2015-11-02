package process;

import java.util.List;

import common.NotFoundREsException;
import common.UnknownCharException;
import datastructure.DFA;
import datastructure.MinDFA;
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
			StandardRE standardRE = new StandardRE(targetRE);
			NFA nfa = standardRE.getNFA();
			System.out.println(nfa.toString());
			DFA dfa = new DFA(nfa);
			System.out.println(dfa.toString());
			MinDFA minDFA = dfa.getMinDFA();

			System.out.println(minDFA.toString());
			try {
				List<String> tokenList = minDFA.getTokenList("bbaababaaababababaaaaab");
				for (int i = 0; i < tokenList.size(); i++) {
					System.out.println(tokenList.get(i));
				}

			} catch (UnknownCharException e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
		} catch (NotFoundREsException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
}
