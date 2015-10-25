import common.BasicOperator;
import common.NFAException;

import datastructure.NFA_graph;

public class Start {
	public static void main(String[] args) {
		try {
			NFA_graph NFA = new NFA_graph('a', 'b', BasicOperator.select);
			NFA.print();
			NFA_graph NFA2 = new NFA_graph('c', BasicOperator.closure);
			NFA2.print();
			NFA.connectedBy(NFA2);
			NFA.print();
			NFA.closure();
			NFA.closure();
			NFA.print();
		} catch (NFAException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
}
