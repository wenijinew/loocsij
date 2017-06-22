package org.loocsij.web.tags;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.loocsij.web.bean.ASiteBean;

public class ASimpleTag extends SimpleTagSupport {
	public static final String DBRESULT_OK = "dbresult_ok";

	public static final String DBRESULT_FAIL = "dbresult_fail";

	public static final String DBRESULT_EXCEPTION = "dbresult_exception";

	public static final String SEARCH_RESULT = "search_result";
	
	public static final String SEARCH_RESULTS = "search_results";

	public static final int SAVE = 1;

	public static final int EDIT = 2;

	public static final int SEARCH = 3;

	public static final int DELETE = 4;

	public ASimpleTag() {
	}

	/**
	 * set attribute in Page context
	 * 
	 * @param name -
	 *            attribute name
	 * @param value -
	 *            attribute value
	 */
	public void setAttribute(String name, Object value) {
		JspContext context = this.getJspContext();
		context.setAttribute(name, value);
	}

	/**
	 * get message to describe the result of database operation.
	 * 
	 * @param operation -
	 *            operation type:SAVE, EDIT, SEARCH and DELETE.
	 * @param isOk -
	 *            if the operation is successful or not.
	 * @param appender -
	 *            the associate information.
	 * @return
	 */
	public String getMessage(int operation, boolean isOk, String appender) {
		StringBuffer message = null;
		switch (operation) {
		case SAVE:
			if (isOk) {
				message = new StringBuffer(appender)
						.append(" has been saved successfully!");
			} else {
				message = new StringBuffer("Error occurred in saving ").append(
						appender).append("!");
			}
			break;
		case EDIT:
			if (isOk) {
				message = new StringBuffer(appender)
						.append(" has been updated successfully!");
			} else {
				message = new StringBuffer("Error occurred in updating ")
						.append(appender).append("!");
			}
			break;
		case SEARCH:
			if (isOk) {
				message = new StringBuffer();
			} else {
				message = new StringBuffer("There is no record can be found!");
			}
			break;
		case DELETE:
			if (isOk) {
				message = new StringBuffer(appender)
						.append(" has been deleted successfully!");
			} else {
				message = new StringBuffer("Error occurred in deleting ")
						.append(appender).append("!");
			}
			break;
		default:
			throw new RuntimeException("Unsupported Database Operation - "
					+ operation);
		}
		return message.toString();
	}

	/**
	 * set result information in Page context.
	 * 
	 * @param operation -
	 *            operation type:SAVE, EDIT, SEARCH and DELETE.
	 * @param isOk -
	 *            if the operation is successful or not.
	 * @param appender -
	 *            the associate information.
	 * @param bean -
	 *            Site Bean instance.
	 */
	public void setResultInformation(int operation, boolean isOk,
			String appender, ASiteBean bean) {
		String msg = getMessage(operation, isOk, appender);
		if (isOk) {
			this.setAttribute(DBRESULT_OK, msg);
			return;
		}
		if (bean.getExceptionStack() != null) {
			this.setAttribute(DBRESULT_EXCEPTION, bean.getExceptionStack());
		}
		this.setAttribute(DBRESULT_FAIL, msg);
	}
}
