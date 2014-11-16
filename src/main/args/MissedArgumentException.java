package main.args;

public class MissedArgumentException extends RuntimeException {
	public MissedArgumentException() {
		super();
	}

	public MissedArgumentException(String message) {
		super("Missed mandatory argument:"+message);
	}

	private static final long serialVersionUID = -1698657451695650343L;

}
