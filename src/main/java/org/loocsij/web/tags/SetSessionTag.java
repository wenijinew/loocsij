/*
package org.loocsij.web.tags;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.loocsij.web.tags.ASimpleTag;

*/
/**
 * set session. add attribute and set maximum inactive interval.
 * @author wengm
 * @since 2008/12/07
 *//*

public class SetSessionTag extends ASimpleTag {
	*/
/**
	 * attribute var
	 *//*

	private String var;
	
	*/
/**
	 * attribute value
	 *//*

	private Object value;
	
	*/
/**
	 * maximum inactive interval
	 *//*

	private int maxInActiveInterval;
 
	public SetSessionTag() {
		// TODO Auto-generated constructor stub
	}

	*/
/* (non-Javadoc)
	 * @see javax.servlet.jsp.tagext.SimpleTagSupport#doTag()
	 *//*

	public void doTag() throws JspException, IOException{
		super.doTag();
		PageContext cxt = (PageContext) this.getJspContext();
		HttpServletRequest req = (HttpServletRequest) cxt.getRequest();
		HttpSession session = req.getSession();
		session.setAttribute(var, value);
		session.setMaxInactiveInterval(maxInActiveInterval);
	}

	*/
/**
	 * @return String
	 * @author wengm
	 * @since 2008-12-7
	 *//*

	public String getVar() {
		return var;
	}

	*/
/**
	 * @return void
	 * @author wengm
	 * @since 2008-12-7
	 *//*

	public void setVar(String name) {
		this.var = name;
	}

	*/
/**
	 * @return Object
	 * @author wengm
	 * @since 2008-12-7
	 *//*

	public Object getValue() {
		return value;
	}

	*/
/**
	 * @return void
	 * @author wengm
	 * @since 2008-12-7
	 *//*

	public void setValue(Object value) {
		this.value = value;
	}

	*/
/**
	 * @return int
	 * @author wengm
	 * @since 2008-12-7
	 *//*

	public int getMaxInActiveInterval() {
		return maxInActiveInterval;
	}

	*/
/**
	 * @return void
	 * @author wengm
	 * @since 2008-12-7
	 *//*

	public void setMaxInActiveInterval(int maxInActiveInterval) {
		this.maxInActiveInterval = maxInActiveInterval;
	}
}
*/
