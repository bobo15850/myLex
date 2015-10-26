package datastructure.test;

import org.junit.Before;
import org.junit.Test;

import common.BasicOperator;
import common.NFAException;
import datastructure.NFA;

public class NFA_test {
	private NFA nfa_connect = null;// 连接关系的NFA
	private NFA nfa_select = null;// 选择关系的NFA
	private NFA nfa_closure = null;// 闭包关系的NFA

	@Before
	public void init() {
		try {
			this.nfa_connect = new NFA('a', 'b', BasicOperator.connect);
			this.nfa_select = new NFA('a', 'b', BasicOperator.select);
			this.nfa_closure = new NFA('a', BasicOperator.closure);
		} catch (NFAException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testCreateNFA_connect() {
		this.nfa_connect.print();
	}

	@Test
	public void testCreateNFA_select() {
		this.nfa_select.print();
	}

	@Test
	public void testCreateNFA_closure() {
		this.nfa_closure.print();
	}

	@Test
	public void testConnectToChar() {
		this.nfa_connect.connectToChar('b');
		this.nfa_connect.print();
	}

	@Test
	public void testConnectedByChar() {
		this.nfa_connect.connectedByChar('b');
		this.nfa_connect.print();
	}

	@Test
	public void testConnectToNFA() {
		this.nfa_connect.connectToNFA(nfa_select);
		this.nfa_connect.print();
	}

	@Test
	public void testConnectedByNFA() {
		this.nfa_connect.connectedByNFA(nfa_select);
		this.nfa_connect.print();
	}

	@Test
	public void testSelectChar() {
		this.nfa_connect.selectChar('c');
		this.nfa_connect.print();
	}

	@Test
	public void testSelectNFA() {
		this.nfa_connect.selectNFA(this.nfa_select);
		this.nfa_connect.print();
	}

	@Test
	public void testClosure() {
		this.nfa_connect.closure();
		this.nfa_connect.print();
	}
}
