/**
 * 
 *//*

package org.loocsij.web.tags;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.loocsij.web.tags.ASimpleTag;

*/
/**
 * @author wengm
 * 
 *//*

public class RemoveCookieTag extends ASimpleTag {
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	*/
/**
	 * 
	 *//*

	public RemoveCookieTag() {
		// TODO Auto-generated constructor stub
	}

	public void doTag() throws JspException, IOException {
		super.doTag();
		PageContext cxt = (PageContext) this.getJspContext();
		HttpServletRequest req = (HttpServletRequest) cxt.getRequest();
		HttpServletResponse res = (HttpServletResponse) cxt.getResponse();
		CookieUtil.removeCookie(this.name, req, res);
	}
}
*/
