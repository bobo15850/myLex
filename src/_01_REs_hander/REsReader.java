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
	private final String REsPath;// �ļ�·��

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
			return null;// �Ҳ����ļ�
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
			return null;// �ļ��򿪴���
		}
		return REsList;
	}// �õ��ļ���ԭʼ��������ʽ

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
	}// �õ����л���֮��Ļ���������ʽ��ֻ�������ӣ�ѡ�񣬺ͱհ�������Լ�����

	private String simplifyRE(String originalRE) {
		if (originalRE == null) {
			return null;
		}
		for (int i = 0; i < originalRE.length(); i++) {
			if (originalRE.charAt(i) == Operator.pos_closure) {
				originalRE = this.replace_pos_closure(originalRE, i);
			}
		}// �滻���հ�+
		for (int i = 0; i < originalRE.length(); i++) {
			if (originalRE.charAt(i) == Operator.is_exist) {
				originalRE = this.replace_is_exist(originalRE, i);
			}
		}// �滻���ڷ�?
		return originalRE;
	}

	private String replace_is_exist(String originalRE, int position) {
		int start = this.getStartOfOperand(originalRE, position);
		String before = originalRE.substring(0, start);
		String target = originalRE.substring(start, position);
		target = Operator.l_pair + target + Operator.select + Operator.�� + Operator.r_pair;// r?=(r|$)
		String after = originalRE.substring(position + 1);
		String newRE = before + target + after;// �����ڲ�����ȥ�����������ʽ
		return newRE;
	}// �滻���ڲ�����

	private String replace_pos_closure(String originalRE, int position) {
		int start = this.getStartOfOperand(originalRE, position);
		String before = originalRE.substring(0, start);
		String target = originalRE.substring(start, position);// ���հ�������
		target = Operator.l_pair + target + target + Operator.closure + Operator.r_pair;// r+ = (rr*)
		String after = originalRE.substring(position + 1);
		String newRE = before + target + after;// �����հ�ȥ�����������ʽ
		return newRE;
	}// �滻���հ�+

	private int getStartOfOperand(String str, int operatorPosition) {
		int pairNum = 0;// ���е����Ŷ���
		boolean isSingle = true;// �Ƿ��ǵ����ַ�
		int k = 0;
		for (k = operatorPosition - 1; k >= 0; k--) {
			if (str.charAt(k) == Operator.r_pair) {
				isSingle = false;
				pairNum++;
			}
			else {
				break;
			}
		}// ����+ǰ��������������
		int start = operatorPosition - 1;// ��������ʼλ��
		if (!isSingle) {
			for (start = k; start >= 0; start--) {
				if (str.charAt(start) == Operator.l_pair) {
					pairNum--;
					if (pairNum == 0) {
						break;
					}
				}
			}
		}// +ǰ�Ǹ��ϲ��������������������������Ϊ������
		return start;
	}// ���㵥Ŀ�������Ĳ�������ʼλ��

	public static void main(String[] args) {
		REsReader rr = new REsReader("files/REs.txt");
		for (String RE : rr.getBasicREs()) {
			System.out.println(RE);
		}
	}
}
