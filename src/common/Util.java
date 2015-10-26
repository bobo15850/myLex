package common;

public class Util {
	public class Pair {
		public static final char left = '(';
		public static final char right = ')';
	}// 圆括号

	public class BasicOperator {
		public static final char connect = '.';// 连接运算符
		public static final char select = '|';// 选择运算
		public static final char closure = '*';// 闭包运算
	}// 基础运算符号

	public enum BinaryBasicOperator {
		connect, // 连接
		select// 选择
	}

	public enum UnaryBasicOperator {
		closure, // 闭包
	}

	public class ExpandOperator {
		public static final char pos_closure = '+';// 正闭包运算（一个或多个）
		public static final char is_exist = '?';// 存在性运算（零个或一个）
	}// 扩展运算符号

	public class SpecialOperand {
		public static final char ε = 'ε';// 表示"哎补习咯"
	}// 特殊的操作数

	public static boolean isOperand(char c) {
		if ((c == Util.BasicOperator.closure) || (c == Util.BasicOperator.connect) || (c == Util.BasicOperator.select) || (c == Util.Pair.left) || (c == Util.Pair.right) || (c == Util.ExpandOperator.is_exist) || (c == Util.ExpandOperator.pos_closure)) {
			return false;
		}
		else {
			return true;
		}
	}// 判断是否为操作数
}
