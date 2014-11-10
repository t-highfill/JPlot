package main;

public class AliasRecursionException extends Exception {
	private static final long serialVersionUID = 7273629540913191780L;

	public AliasRecursionException() {
	}

	public AliasRecursionException(String message) {
		super(message);
	}

	public AliasRecursionException(Throwable cause) {
		super(cause);
	}

	public AliasRecursionException(String message, Throwable cause) {
		super(message, cause);
	}

	public AliasRecursionException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
