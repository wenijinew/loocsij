package org.loocsij.logger;

public interface SimpleLogger {
	void error(Exception e);
	void info(String verbose);
	void error(Object errorDes, Exception e);
}
