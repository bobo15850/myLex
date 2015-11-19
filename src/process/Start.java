package process;

import java.util.List;

import common.NotEndException;
import common.NotFoundREsException;
import common.UnknownCharException;
import datastructure.DFA;
import datastructure.MinDFA;
import datastructure.NFA;
import datastructure.REFile;
import datastructure.StandardRE;

public class Start {
	public static void main(String[] args) {
		String path = "files/REs.l";
		REFile handler = new REFile(path);
		String targetRE = handler.getTargetRE();
		System.out.println(targetRE);
		try {
			StandardRE standardRE = new StandardRE(targetRE);
			NFA nfa = standardRE.getNFA();
			DFA dfa = new DFA(nfa);
			MinDFA minDFA = dfa.getMinDFA();
			System.out.println(nfa.toString());
			System.out.println(dfa.toString());
			System.out.println(minDFA.toString());
			try {
				List<String> tokenList = minDFA
						.getTokenList("public static s = dgfdg;s=asd;s=dfg;int i=23;for [int i=100;i>0;i--]{cout>>sdd;}");
				for (int i = 0; i < tokenList.size(); i++) {
					System.out.println(tokenList.get(i));
				}
			} catch (UnknownCharException e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			} catch (NotEndException e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
		} catch (NotFoundREsException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
}
