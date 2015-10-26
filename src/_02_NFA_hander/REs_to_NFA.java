package _02_NFA_hander;

import common.NotFoundREsException;

public class REs_to_NFA {
	private final String RE;// 最终的正则表达式，只存在(),|,*和连接运算

	public REs_to_NFA(String RE) throws NotFoundREsException {
		this.RE = RE;
		if (RE == null) {
			throw new NotFoundREsException();
		}
	}
}
