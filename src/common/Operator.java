package common;

public class Operator {
	// 表示正则表达式中所有可能出现的操作符
	public static final char connect = '.';// 连接运算符
	public static final char select = '|';// 选择运算
	public static final char closure = '*';// 闭包运算
	public static final char l_pair = '(';// 左括号
	public static final char r_pair = ')';// 右括号
	//
	public static final char ε = 'ε';// 表示"哎补习咯"
	//
	public static final char pos_closure = '+';// 正闭包运算（一个或多个）
	public static final char is_exist = '?';// 存在性运算（零个或一个）
}
