package org.loocsij.web.bean;

import org.loocsij.util.StringUtil;

/**
 * Abstract class which used to provide common methods for Beans used on Web
 * page. One especially feature is to provide exception stack attribute which
 * can be obtained on JSP page. This feature can be used for developers to debug
 * and testers to report error.<br>
 * Meanwhile, this class contain Database table design information. Developers
 * can easily maintain these information because they are centralized. 
 * 
 * @author wengm
 * @since 2008-11-09
 */
public abstract class ASiteBean {
	/**
	 * iis user table name: iis_user <br>
	 * "iis_user" table structure:<br>
	 * 
	 * <pre>
	 *      +------------------+-------------+------+-----+---------+-------+
	 *     	 | Field            | Type        | Null | Key | Default | Extra |
	 *     	 +------------------+-------------+------+-----+---------+-------+
	 *     	 | username         | varchar(20) | YES  |     | NULL    |       |
	 *     	 | userpass         | varchar(8)  | YES  |     | NULL    |       |
	 *     	 | userconfirmpass  | varchar(8)  | YES  |     | NULL    |       |
	 *     	 | givenname        | varchar(10) | YES  |     | NULL    |       |
	 *     	 | lastname         | varchar(10) | YES  |     | NULL    |       |
	 *     	 | email            | varchar(30) | YES  |     | NULL    |       |
	 *     	 | preferedlanguage | varchar(20) | YES  |     | NULL    |       |
	 *     	 +------------------+-------------+------+-----+---------+-------+
	 * </pre>
	 */
	public static final String IIS_USER = "IIS_USER";
	
	public static final String IIS_ID_STORE = "IIS_ID_STORE";

	/**
	 * iis resource table name:iis_resource<br>
	 * "iis_resource" table structure:<br>
	 * 
	 */
	public static final String IIS_RESOURCE = "IIS_RESOURCE";

	private String exceptionStack;

	/**
	 * @return the exceptionStack
	 */
	public String getExceptionStack() {
		return exceptionStack;
	}

	/**
	 * @param exceptionStack
	 *            the exceptionStack to set
	 */
	protected void setExceptionStack(String exceptionStack) {
		this.exceptionStack = exceptionStack;
	}

	protected void setExceptionStack(Exception e) {
		this.setExceptionStack(StringUtil.getExceptionStackForHtml(e));
	}
}