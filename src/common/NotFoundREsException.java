package common;

public class NotFoundREsException extends Exception {
	private static final long serialVersionUID = 1L;

	@Override
	public String getMessage() {
		return "沒有找到可用的正则表达式";
	}

}
