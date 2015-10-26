package process;

import java.util.Stack;

import common.NFAException;
import common.NotFoundREsException;
import common.Util;
import datastructure.NFA;

public class _1_RE_to_NFA {
	private final String RE;// ���յ�������ʽ��ֻ����(),|,*����������

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
	}// �õ�NFA

	public String inOrderToPosOrder() {
		Stack<Character> stack = new Stack<Character>();// ջ���Դ����ʱȡ�õĵ��ǲ�������Ĳ�����
		StringBuilder builder = new StringBuilder();// ������������
		for (int i = 0; i < this.RE.length(); i++) {
			char ch = this.RE.charAt(i);
			if (Util.isOperand(ch) || ch == Util.BasicOperator.closure) {
				builder.append(ch);
			}// ��ǰΪһ�������������Ǳհ���������������
			else {
				if (ch == Util.Pair.left) {
					stack.push(ch);
				}// ������ֱ����ջ
				else if (ch == Util.Pair.right) {
					char temp;
					while ((temp = stack.pop()) != Util.Pair.left) {
						builder.append(temp);
					}
				}// ������ʱ����ջ��Ԫ������������֪����Ӧ��������
				else if (ch == Util.BasicOperator.connect) {
					while (!stack.empty() && stack.peek() == Util.BasicOperator.connect) {
						builder.append(stack.pop());
					}
					stack.push(ch);
				}// ��������ʱ��ջ�е����ӷ�ȫ��������ֱ����������|Ȼ���ٽ������ӷ�ѹ��ջ
				else if (ch == Util.BasicOperator.select) {
					while (!stack.empty() && (stack.peek() == Util.BasicOperator.connect || stack.peek() == Util.BasicOperator.select)) {
						builder.append(stack.pop());
					}
					stack.push(ch);
				}
			}// ��ǰ���ǲ�����
		}
		while (!stack.empty()) {
			builder.append(stack.pop());
		}// ��ջ�еĲ�����ȫ������
		return builder.toString();
	}// ����׺���ʽת��Ϊ��׺���ʽ

	private NFA posOrderREToNFA(String posOrderRE) {
		if (posOrderRE == null || posOrderRE.length() == 0) {
			return null;
		}
		Stack<Object> stack = new Stack<Object>();
		for (int i = 0; i < posOrderRE.length(); i++) {
			char ch = posOrderRE.charAt(i);
			if (Util.isOperand(ch)) {
				stack.push(ch);
			}// ������ֱ����ջ
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
			}// �հ�����ֻҪȡһ��������
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
			}// ���Ӳ�����Ҫ����������������Ҫע��˳��
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
			}// ѡ��������Ҫ���������������������Խ���˳��
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
		}// ����һ��NFA
		else {
			return null;
		}// ˵��������ʽ����һ���ַ�
	}// ����׺���ʽת��ΪNFA
}
