package common;

public class UnknownCharException extends Exception {

	private static final long serialVersionUID = 1L;

	@Override
	public String getMessage() {
		return "输入流中含有无法识别的字符";
	}

}
