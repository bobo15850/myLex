package _02_NFA_hander;

import java.util.Stack;

import common.NotFoundREsException;

public class REs_to_NFA {
	private final String RE;// 最终的正则表达式，只存在(),|,*和连接运算

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
	}// 将中缀表达式转化为后缀表达式
}
