package org.loocsij.web;

public class WebAccessException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6501341173921982248L;

	public WebAccessException() {
		super();
	}

	public WebAccessException(String message) {
		super(message);
	}

	public WebAccessException(Throwable cause) {
		super(cause);
	}

	public WebAccessException(String message, Throwable cause) {
		super(message, cause);
	}

	public String toString() {
		StringBuffer str = new StringBuffer(super.toString());
		Throwable cause = this.getCause();
		while(cause!=null) {
			str.append('\n');
			str.append(cause.toString());
			cause = cause.getCause();
		}
		StackTraceElement[] stes = this.getStackTrace();
		if(stes!=null) {
			int length = stes.length>10?10:stes.length;
			for(int i=0;i<length;i++) {
				str.append("\n\t");
				str.append(stes[i].toString());
			}
		}
		return str.toString();
	}
}
