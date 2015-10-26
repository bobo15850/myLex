package process;

import java.util.Stack;

import common.NFAException;
import common.NotFoundREsException;
import common.Util;
import datastructure.NFA;

public class _1_RE_to_NFA {
	private final String RE;// 最终的正则表达式，只存在(),|,*和连接运算

	public _1_RE_to_NFA(String RE) throws NotFoundREsException {
		this.RE = RE;
		if (RE == null) {
			throw new NotFoundREsException();
		}
	}

	public NFA getNFA() {
		String posOrderRE = this.inOrderToPosOrder();
		NFA nfa = posOrderREToNFA(posOrderRE);
		return nfa;
	}// 得到NFA

	public String inOrderToPosOrder() {
		Stack<Character> stack = new Stack<Character>();// 栈用以存放暂时取得的但是不做处理的操作数
		StringBuilder builder = new StringBuilder();// 后续的正则表达
		for (int i = 0; i < this.RE.length(); i++) {
			char ch = this.RE.charAt(i);
			if (Util.isOperand(ch) || ch == Util.BasicOperator.closure) {
				builder.append(ch);
			}// 当前为一个操作数或者是闭包符号则放入输出流
			else {
				if (ch == Util.Pair.left) {
					stack.push(ch);
				}// 左括号直接入栈
				else if (ch == Util.Pair.right) {
					char temp;
					while ((temp = stack.pop()) != Util.Pair.left) {
						builder.append(temp);
					}
				}// 右括号时，将栈中元素输出到输出流知道对应的左括号
				else if (ch == Util.BasicOperator.connect) {
					while (!stack.empty() && stack.peek() == Util.BasicOperator.connect) {
						builder.append(stack.pop());
					}
					stack.push(ch);
				}// 遇到连接时将栈中的连接符全部弹出，直到遇到（，|然后再将该连接符压入栈
				else if (ch == Util.BasicOperator.select) {
					while (!stack.empty() && (stack.peek() == Util.BasicOperator.connect || stack.peek() == Util.BasicOperator.select)) {
						builder.append(stack.pop());
					}
					stack.push(ch);
				}
			}// 当前不是操作数
		}
		while (!stack.empty()) {
			builder.append(stack.pop());
		}// 将栈中的操作符全部弹出
		return builder.toString();
	}// 将中缀表达式转化为后缀表达式

	private NFA posOrderREToNFA(String posOrderRE) {
		if (posOrderRE == null || posOrderRE.length() == 0) {
			return null;
		}
		Stack<Object> stack = new Stack<Object>();
		for (int i = 0; i < posOrderRE.length(); i++) {
			char ch = posOrderRE.charAt(i);
			if (Util.isOperand(ch)) {
				stack.push(ch);
			}// 操作数直接入栈
			else if (ch == Util.BasicOperator.closure) {
				Object obj = stack.pop();
				if (obj instanceof NFA) {
					((NFA) obj).closure();
					stack.push(obj);
				}
				else {
					char tempChar = (char) obj;
					try {
						stack.push(new NFA(tempChar, Util.UnaryBasicOperator.closure));
					} catch (NFAException e) {
						e.printStackTrace();
						return null;
					}
				}
			}// 闭包操作只要取一个操作数
			else if (ch == Util.BasicOperator.connect) {
				Object operand2 = stack.pop();
				Object operand1 = stack.pop();
				if (operand1 instanceof NFA) {
					if (operand2 instanceof NFA) {
						NFA nfa1 = (NFA) operand1;
						NFA nfa2 = (NFA) operand2;
						nfa1.connectedByNFA(nfa2);
						stack.push(nfa1);
					}
					else {
						NFA nfa1 = (NFA) operand1;
						char c2 = (char) operand2;
						nfa1.connectedByChar(c2);
						stack.push(nfa1);
					}
				}
				else {
					if (operand2 instanceof NFA) {
						char c1 = (char) operand1;
						NFA nfa2 = (NFA) operand2;
						nfa2.connectToChar(c1);
						stack.push(nfa2);
					}
					else {
						char c1 = (char) operand1;
						char c2 = (char) operand2;
						try {
							NFA nfa = new NFA(c1, c2, Util.BinaryBasicOperator.connect);
							stack.push(nfa);
						} catch (NFAException e) {
							e.printStackTrace();
							return null;
						}
					}
				}
			}// 链接操作需要两个操作数，而且要注意顺序
			else if (ch == Util.BasicOperator.select) {
				Object operand2 = stack.pop();
				Object operand1 = stack.pop();
				if (operand1 instanceof NFA) {
					if (operand2 instanceof NFA) {
						NFA nfa1 = (NFA) operand1;
						NFA nfa2 = (NFA) operand2;
						nfa1.selectNFA(nfa2);
						stack.push(nfa1);
					}
					else {
						NFA nfa1 = (NFA) operand1;
						char c2 = (char) operand2;
						nfa1.selectChar(c2);
						stack.push(nfa1);
					}
				}
				else {
					if (operand2 instanceof NFA) {
						char c1 = (char) operand1;
						NFA nfa2 = (NFA) operand2;
						nfa2.selectChar(c1);
						stack.push(nfa2);
					}
					else {
						char c1 = (char) operand1;
						char c2 = (char) operand2;
						try {
							NFA nfa = new NFA(c1, c2, Util.BinaryBasicOperator.select);
							stack.push(nfa);
						} catch (NFAException e) {
							e.printStackTrace();
							return null;
						}
					}
				}
			}// 选择运算需要两个操作数，操作数可以交换顺序
			else {
				return null;
			}
		}
		if (stack.empty()) {
			return null;
		}
		Object resObj = stack.pop();
		if (resObj instanceof NFA) {
			return (NFA) resObj;
		}// 返回一个NFA
		else {
			return null;
		}// 说明正则表达式就是一个字符
	}// 将后缀表达式转化为NFA
}
