package process;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import common.Util;

public class _0_REsHandler {
	private final String REsPath;// 文件路径

	public _0_REsHandler(String REsPath) {
		this.REsPath = REsPath;
	}

	public String getTargetRE() {
		StringBuilder builder = new StringBuilder();
		List<String> REs = this.getBasicREs();
		if (REs.size() > 0) {
			builder.append(Util.Pair.left).append(REs.get(0)).append(Util.Pair.right);
			for (int i = 1; i < REs.size(); i++) {
				builder.append(Util.BasicOperator.select).append(Util.Pair.left).append(REs.get(i)).append(Util.Pair.right);
			}
			return builder.toString();
		}
		return null;
	}// 将所有的分开的正则表达式用|符号做选择，合并成一个总的正则表达式

	private List<String> getBasicREs() {
		List<String> originalREs = this.getOriginalREs();
		if (originalREs == null) {
			return null;
		}
		List<String> simplifiedREsList = new LinkedList<String>();
		for (String RE : originalREs) {
			String temp = simplifyRE(RE);
			if (temp != null) {
				simplifiedREsList.add(temp);
			}
		}
		return simplifiedREsList;
	}// 得到所有化简之后的基本正则表达式，只包含连接，选择，和闭包运算符以及括号

	private List<String> getOriginalREs() {
		LinkedList<String> REsList = new LinkedList<String>();
		File file = null;
		FileReader fr = null;
		file = new File(REsPath);
		try {
			fr = new FileReader(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			e.printStackTrace();
			return null;// 找不到文件
		}
		@SuppressWarnings("resource")
		BufferedReader br = new BufferedReader(fr);
		try {
			String temp = null;
			while ((temp = br.readLine()) != null) {
				if (temp.length() > 0) {
					REsList.add(temp);
				}
			}// 把空格当作普通字符处理，但是去除空行（有空格除外）
		} catch (IOException e) {
			e.printStackTrace();
			return null;// 文件打开错误
		}
		return REsList;
	}// 得到文件中原始的正则表达式

	private String simplifyRE(String originalRE) {
		if (originalRE == null) {
			return null;
		}
		for (int i = 0; i < originalRE.length(); i++) {
			if (originalRE.charAt(i) == Util.ExpandOperator.pos_closure) {
				originalRE = this.replace_pos_closure(originalRE, i);
			}
		}// 替换正闭包+
		for (int i = 0; i < originalRE.length(); i++) {
			if (originalRE.charAt(i) == Util.ExpandOperator.is_exist) {
				originalRE = this.replace_is_exist(originalRE, i);
			}
		}// 替换存在符?
		originalRE = this.addConnectOperator(originalRE);// 添加连接运算符
		return originalRE;
	}

	private String addConnectOperator(String originalRE) {
		for (int i = 0; i < originalRE.length() - 1; i++) {
			char thisChar = originalRE.charAt(i);
			char nextChar = originalRE.charAt(i + 1);
			if ((thisChar == Util.Pair.right) || thisChar == Util.BasicOperator.closure || Util.isOperand(thisChar)) {
				if ((nextChar == Util.Pair.left) || Util.isOperand(nextChar)) {
					originalRE = this.insertConnectOperator(originalRE, i);
					i++;
				}
			}// 右括号或者闭包符号或者操作数和左括号或者操作数之间有连接运算
		}
		return originalRE;
	}// 添加连接运算符

	private String insertConnectOperator(String str, int position) {
		String before = str.substring(0, position + 1);
		String after = str.substring(position + 1);
		String res = before + Util.BasicOperator.connect + after;
		return res;
	}// 在字符串某一位插入一连接字符

	private String replace_is_exist(String originalRE, int position) {
		int start = this.getStartOfOperand(originalRE, position);
		String before = originalRE.substring(0, start);
		String target = originalRE.substring(start, position);
		target = Util.Pair.left + target + Util.BasicOperator.select + Util.SpecialOperand.ε + Util.Pair.right;// r?=(r|$)
		String after = originalRE.substring(position + 1);
		String newRE = before + target + after;// 将存在操作符去除后的正则表达式
		return newRE;
	}// 替换存在操作符

	private String replace_pos_closure(String originalRE, int position) {
		int start = this.getStartOfOperand(originalRE, position);
		String before = originalRE.substring(0, start);
		String target = originalRE.substring(start, position);// 正闭包操作数
		target = Util.Pair.left + target + Util.BasicOperator.connect + target + Util.BasicOperator.closure + Util.Pair.right;// r+=(rr*)
		String after = originalRE.substring(position + 1);
		String newRE = before + target + after;// 将正闭包去除后的正则表达式
		return newRE;
	}// 替换正闭包+

	private int getStartOfOperand(String str, int operatorPosition) {
		int pairNum = 0;// 含有的括号对数
		boolean isSingle = true;// 是否是单个字符
		int k = 0;
		for (k = operatorPosition - 1; k >= 0; k--) {
			if (str.charAt(k) == Util.Pair.right) {
				isSingle = false;
				pairNum++;
			}
			else {
				break;
			}
		}// 计算+前所含的右括号数
		int start = operatorPosition - 1;// 操作数起始位置
		if (!isSingle) {
			for (start = k; start >= 0; start--) {
				if (str.charAt(start) == Util.Pair.left) {
					pairNum--;
					if (pairNum == 0) {
						break;
					}
				}
			}
		}// +前是复合操作数，将整个括号里的内容作为操作数
		return start;
	}// 计算单目操作符(+,?)的操作数起始位置以括号为分界
}