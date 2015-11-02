package common;

public class NotEndException extends Exception {
	private static final long serialVersionUID = 1L;

	@Override
	public String getMessage() {
		return "输入流结束时未达到自动机终止状态";
	}

}
