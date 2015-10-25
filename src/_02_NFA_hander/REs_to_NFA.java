package _02_NFA_hander;

import java.util.List;

import common.NotFoundREsException;

public class REs_to_NFA {
	private final List<String> REs;// 正则表达式，只存在(),|,*和连接运算

	public REs_to_NFA(List<String> REs) throws NotFoundREsException {
		this.REs = REs;
		if (REs == null) {
			throw new NotFoundREsException();
		}
	}
	
	
	
}
