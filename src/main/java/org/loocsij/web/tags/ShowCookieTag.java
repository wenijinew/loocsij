/**
 * 
 *//*

package org.loocsij.web.tags;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

import org.loocsij.web.tags.ASimpleTag;

*/
/**
 * @author wengm
 *
 *//*

public class ShowCookieTag extends ASimpleTag {
	*/
/**
	 * 
	 *//*

	public ShowCookieTag() {
		// TODO Auto-generated constructor stub
	}

	public void doTag() throws JspException, IOException{
		super.doTag();
		PageContext cxt = (PageContext) this.getJspContext();
		HttpServletRequest req = (HttpServletRequest) cxt.getRequest();
		Cookie[] cks = req.getCookies();
		JspWriter out = cxt.getOut();
		if(cks!=null && cks.length>0){
			out.println("<table border='1px' cellspacing='0px' cellpadding='2px'>");
			for(int i=0;i<cks.length;i++){
				out.print("<tr style='background:#7878ff;'><td colspan='2' style='font-weight:bold;'>Cookie["+i+"]</td></tr>");
				out.print("<tr><td style='font-weight:bold;'>name</td><td>"+cks[i].getName()+"</td></tr>");
				out.print("<tr><td style='font-weight:bold;'>value</td><td>"+cks[i].getValue()+"</td></tr>");
				out.print("<tr><td style='font-weight:bold;'>maxAge</td><td>"+cks[i].getMaxAge()+"</td></tr>");
				out.print("<tr><td style='font-weight:bold;'>comment</td><td>"+cks[i].getComment()+"</td></tr>");
				out.print("<tr><td style='font-weight:bold;'>domain</td><td>"+cks[i].getDomain()+"</td></tr>");
				out.print("<tr><td style='font-weight:bold;'>path</td><td>"+cks[i].getPath()+"</td></tr>");
				out.print("<tr><td style='font-weight:bold;'>secure</td><td>"+cks[i].getSecure()+"</td></tr>");
				out.print("<tr><td style='font-weight:bold;'>version</td><td>"+cks[i].getVersion()+"</td></tr>");
				out.print("<tr><td style='font-weight:bold;'>class</td><td>"+cks[i].getClass()+","+cks[i]+"</td></tr>");
			}
			out.println("</table>");
		}
	}
}
*/
