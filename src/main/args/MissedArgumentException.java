package main.args;

public class MissedArgumentException extends RuntimeException {
	public MissedArgumentException() {
		super("Missed mandatory argument");
	}

	public MissedArgumentException(String missingarg) {
		super("Missed mandatory argument:"+missingarg);
	}
	
	public MissedArgumentException(String missingarg, String givenarg){
		this(missingarg+": given:"+givenarg);
	}

	private static final long serialVersionUID = -1698657451695650343L;

}
