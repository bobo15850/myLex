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
	private final String REsPath;// �ļ�·��

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
	}// �����еķֿ���������ʽ��|������ѡ�񣬺ϲ���һ���ܵ�������ʽ

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
	}// �õ����л���֮��Ļ���������ʽ��ֻ�������ӣ�ѡ�񣬺ͱհ�������Լ�����

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
			return null;// �Ҳ����ļ�
		}
		@SuppressWarnings("resource")
		BufferedReader br = new BufferedReader(fr);
		try {
			String temp = null;
			while ((temp = br.readLine()) != null) {
				if (temp.length() > 0) {
					REsList.add(temp);
				}
			}// �ѿո�����ͨ�ַ���������ȥ�����У��пո���⣩
		} catch (IOException e) {
			e.printStackTrace();
			return null;// �ļ��򿪴���
		}
		return REsList;
	}// �õ��ļ���ԭʼ��������ʽ

	private String simplifyRE(String originalRE) {
		if (originalRE == null) {
			return null;
		}
		for (int i = 0; i < originalRE.length(); i++) {
			if (originalRE.charAt(i) == Util.ExpandOperator.pos_closure) {
				originalRE = this.replace_pos_closure(originalRE, i);
			}
		}// �滻���հ�+
		for (int i = 0; i < originalRE.length(); i++) {
			if (originalRE.charAt(i) == Util.ExpandOperator.is_exist) {
				originalRE = this.replace_is_exist(originalRE, i);
			}
		}// �滻���ڷ�?
		originalRE = this.addConnectOperator(originalRE);// ������������
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
			}// �����Ż��߱հ����Ż��߲������������Ż��߲�����֮������������
		}
		return originalRE;
	}// ������������

	private String insertConnectOperator(String str, int position) {
		String before = str.substring(0, position + 1);
		String after = str.substring(position + 1);
		String res = before + Util.BasicOperator.connect + after;
		return res;
	}// ���ַ���ĳһλ����һ�����ַ�

	private String replace_is_exist(String originalRE, int position) {
		int start = this.getStartOfOperand(originalRE, position);
		String before = originalRE.substring(0, start);
		String target = originalRE.substring(start, position);
		target = Util.Pair.left + target + Util.BasicOperator.select + Util.SpecialOperand.�� + Util.Pair.right;// r?=(r|$)
		String after = originalRE.substring(position + 1);
		String newRE = before + target + after;// �����ڲ�����ȥ�����������ʽ
		return newRE;
	}// �滻���ڲ�����

	private String replace_pos_closure(String originalRE, int position) {
		int start = this.getStartOfOperand(originalRE, position);
		String before = originalRE.substring(0, start);
		String target = originalRE.substring(start, position);// ���հ�������
		target = Util.Pair.left + target + Util.BasicOperator.connect + target + Util.BasicOperator.closure + Util.Pair.right;// r+=(rr*)
		String after = originalRE.substring(position + 1);
		String newRE = before + target + after;// �����հ�ȥ�����������ʽ
		return newRE;
	}// �滻���հ�+

	private int getStartOfOperand(String str, int operatorPosition) {
		int pairNum = 0;// ���е����Ŷ���
		boolean isSingle = true;// �Ƿ��ǵ����ַ�
		int k = 0;
		for (k = operatorPosition - 1; k >= 0; k--) {
			if (str.charAt(k) == Util.Pair.right) {
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
				if (str.charAt(start) == Util.Pair.left) {
					pairNum--;
					if (pairNum == 0) {
						break;
					}
				}
			}
		}// +ǰ�Ǹ��ϲ��������������������������Ϊ������
		return start;
	}// ���㵥Ŀ������(+,?)�Ĳ�������ʼλ��������Ϊ�ֽ�
}