package common;

public class Util {
	public class Pair {
		public static final char left = '(';
		public static final char right = ')';
	}// Բ����

	public class BasicOperator {
		public static final char connect = '.';// ���������
		public static final char select = '|';// ѡ������
		public static final char closure = '*';// �հ�����
	}// �����������

	public enum BinaryBasicOperator {
		connect, // ����
		select// ѡ��
	}

	public enum UnaryBasicOperator {
		closure, // �հ�
	}

	public class ExpandOperator {
		public static final char pos_closure = '+';// ���հ����㣨һ��������
		public static final char is_exist = '?';// ���������㣨�����һ����
	}// ��չ�������

	public class SpecialOperand {
		public static final char �� = '��';// ��ʾ"����ϰ��"
	}// ����Ĳ�����

	public static boolean isOperand(char c) {
		if ((c == Util.BasicOperator.closure) || (c == Util.BasicOperator.connect) || (c == Util.BasicOperator.select) || (c == Util.Pair.left) || (c == Util.Pair.right) || (c == Util.ExpandOperator.is_exist) || (c == Util.ExpandOperator.pos_closure)) {
			return false;
		}
		else {
			return true;
		}
	}// �ж��Ƿ�Ϊ������
}
