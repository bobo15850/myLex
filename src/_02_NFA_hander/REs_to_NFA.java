package _02_NFA_hander;

import common.NotFoundREsException;

public class REs_to_NFA {
	private final String RE;// ���յ�������ʽ��ֻ����(),|,*����������

	public REs_to_NFA(String RE) throws NotFoundREsException {
		this.RE = RE;
		if (RE == null) {
			throw new NotFoundREsException();
		}
	}
}
