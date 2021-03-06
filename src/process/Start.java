package process;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import common.NotEndException;
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
		StandardRE standardRE = handler.getStandardRE();
		NFA nfa = standardRE.getNFA();
		DFA dfa = new DFA(nfa);
		MinDFA minDFA = dfa.getMinDFA();
		try {
			System.out.println("token��������");
			File inputFile = new File("files/input.txt");
			BufferedReader br = new BufferedReader(new FileReader(inputFile));
			String temp = null;
			while ((temp = br.readLine()) != null) {
				List<String> tokenList = minDFA.getTokenList(temp);
				for (int i = 0; i < tokenList.size(); i++) {
					System.out.println(tokenList.get(i));
				}
			}
			br.close();
		} catch (UnknownCharException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		} catch (NotEndException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
