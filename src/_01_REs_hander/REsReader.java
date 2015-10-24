package _01_REs_hander;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import common.Operator;

public class REsReader {
	private final String REsPath;// 文件路径

	public REsReader(String REsPath) {
		this.REsPath = REsPath;
	}

	private List<String> getOriginalREs() {
		LinkedList<String> REsList = new LinkedList<String>();
		File file = null;
		FileReader fr = null;
		file = new File(REsPath);
		try {
			fr = new FileReader(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;// 找不到文件
		}
		@SuppressWarnings("resource")
		BufferedReader br = new BufferedReader(fr);
		try {
			String temp = null;
			while ((temp = br.readLine()) != null) {
				if (temp.trim().length() >= 2) {
					if (!(temp.charAt(0) == Operator.ignore && temp.charAt(1) == Operator.ignore)) {
						REsList.add(temp);
					}
				}
				else {
					REsList.add(temp);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;// 文件打开错误
		}
		return REsList;
	}// 得到文件中原始的正则表达式

	public List<String> getBasicREs() {
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

	private String simplifyRE(String originalRE) {
		if (originalRE == null) {
			return null;
		}
		for (int i = 0; i < originalRE.length(); i++) {
			if (originalRE.charAt(i) == Operator.pos_closure) {
				originalRE = this.replace_pos_closure(originalRE, i);
			}
		}// 替换正闭包+
		for (int i = 0; i < originalRE.length(); i++) {
			if (originalRE.charAt(i) == Operator.is_exist) {
				originalRE = this.replace_is_exist(originalRE, i);
			}
		}// 替换存在符?
		return originalRE;
	}

	private String replace_is_exist(String originalRE, int position) {
		int start = this.getStartOfOperand(originalRE, position);
		String before = originalRE.substring(0, start);
		String target = originalRE.substring(start, position);
		target = Operator.l_pair + target + Operator.select + Operator.ε + Operator.r_pair;// r?=(r|$)
		String after = originalRE.substring(position + 1);
		String newRE = before + target + after;// 将存在操作符去除后的正则表达式
		return newRE;
	}// 替换存在操作符

	private String replace_pos_closure(String originalRE, int position) {
		int start = this.getStartOfOperand(originalRE, position);
		String before = originalRE.substring(0, start);
		String target = originalRE.substring(start, position);// 正闭包操作数
		target = Operator.l_pair + target + target + Operator.closure + Operator.r_pair;// r+ = (rr*)
		String after = originalRE.substring(position + 1);
		String newRE = before + target + after;// 将正闭包去除后的正则表达式
		return newRE;
	}// 替换正闭包+

	private int getStartOfOperand(String str, int operatorPosition) {
		int pairNum = 0;// 含有的括号对数
		boolean isSingle = true;// 是否是单个字符
		int k = 0;
		for (k = operatorPosition - 1; k >= 0; k--) {
			if (str.charAt(k) == Operator.r_pair) {
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
				if (str.charAt(start) == Operator.l_pair) {
					pairNum--;
					if (pairNum == 0) {
						break;
					}
				}
			}
		}// +前是复合操作数，将整个括号里的内容作为操作数
		return start;
	}// 计算单目操作符的操作数起始位置

	public static void main(String[] args) {
		REsReader rr = new REsReader("files/REs.txt");
		for (String RE : rr.getBasicREs()) {
			System.out.println(RE);
		}
	}
}
