package cz.sysnet.env;

public class EnvException extends Exception {
	private static final long serialVersionUID = 2596039245381545136L;

	public EnvException() {
		super();
	}

	public EnvException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public EnvException(String message, Throwable cause) {
		super(message, cause);
	}

	public EnvException(String message) {
		super(message);
	}

	public EnvException(Throwable cause) {
		super(cause);
	}
}
