package _02_NFA_hander;

import java.util.Stack;

import common.NotFoundREsException;

public class REs_to_NFA {
	private final String RE;// ���յ�������ʽ��ֻ����(),|,*����������

	public REs_to_NFA(String RE) throws NotFoundREsException {
		this.RE = RE;
		if (RE == null) {
			throw new NotFoundREsException();
		}
	}

	private String inOrderToPosOrder() {
		Stack<Character> stack = new Stack<Character>();
		for (int i = 0; i < this.RE.length(); i++) {
			char ch = this.RE.charAt(i);
		}
		return null;
	}// ����׺���ʽת��Ϊ��׺���ʽ
}
