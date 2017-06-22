/**
 * 
 *//*

package org.loocsij.web.tags;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.loocsij.util.StringUtil;
import org.loocsij.web.tags.ASimpleTag;

*/
/**
 * Add cookie. Currently, support name, value, maxAge, path, and secure.
 * @author wengm
 *
 *//*

public class AddCookieTag extends ASimpleTag {
	*
	 * cookie name
	private String name;
	*/
/**
	 * cookie value
	 *//*

	private String value;
	*/
/**
	 * maximum age of the cookie
	 *//*

	private String maxAge;
	
	*/
/**
	 * path available for cookie
	 *//*

	private String path;
	
	*/
/**
	 * secure
	 *//*

	private boolean secure;
	
	*/
/**
	 * @return String
	 * @author wengm
	 * @since 2008-12-6
	 *//*

	public String getMaxAge() {
		return maxAge;
	}


	*/
/**
	 * @return void
	 * @author wengm
	 * @since 2008-12-6
	 *//*

	public void setMaxAge(String maxAgeString) {
		this.maxAge = maxAgeString;
	}


	*/
/**
	 * @return String
	 * @author wengm
	 * @since 2008-12-6
	 *//*

	public String getName() {
		return name;
	}


	*/
/**
	 * @return void
	 * @author wengm
	 * @since 2008-12-6
	 *//*

	public void setName(String name) {
		this.name = name;
	}


	*/
/**
	 * @return String
	 * @author wengm
	 * @since 2008-12-6
	 *//*

	public String getValue() {
		return value;
	}


	*/
/**
	 * @return void
	 * @author wengm
	 * @since 2008-12-6
	 *//*

	public void setValue(String value) {
		this.value = value;
	}


	*/
/**
	 * 
	 *//*

	public AddCookieTag() {
		// TODO Auto-generated constructor stub
	}

	public void doTag() throws JspException, IOException{
		super.doTag();
		int iMaxAge = -1;
		if(!StringUtil.isEmpty(this.maxAge) && StringUtil.isNumber(this.maxAge)){
			iMaxAge = Integer.valueOf(this.maxAge).intValue();
		}
		PageContext cxt = (PageContext)this.getJspContext();
		HttpServletResponse res = (HttpServletResponse) cxt.getResponse();
		CookieUtil.sendCookie(name,value,iMaxAge,this.path,this.secure, res);
	}


	public String getPath() {
		return path;
	}


	public void setPath(String path) {
		this.path = path;
	}


	public boolean isSecure() {
		return secure;
	}


	public void setSecure(boolean secure) {
		this.secure = secure;
	}
}
*/
