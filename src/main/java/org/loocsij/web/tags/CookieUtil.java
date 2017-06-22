/**
 * 
 */
package org.loocsij.web.tags;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author wengm
 * 
 */
public class CookieUtil {

	/**
	 * 
	 */
	public CookieUtil() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return void
	 * @author wengm
	 * @since 2008-12-6
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	/**
	 * Remove cookie - set cookie's maxAge as zero.
	 * @return void
	 * @author wengm
	 * @since 2008-12-7
	 */
	public static void removeCookie(String cookieName,HttpServletRequest req, HttpServletResponse res){
		Cookie[] cks = req.getCookies();
		if (cks != null && cks.length > 0) {
			for (int i = 0; i < cks.length; i++) {
				if(cks[i].getName()!=null && cks[i].getName().equals(cookieName)){
					System.out.println(cks[i]);
					cks[i].setMaxAge(0);
					cks[i].setValue(null);
				}
				res.addCookie(cks[i]);
			}
		}
	}

	/**
	 * Send cookie to response.
	 * 
	 * @param String
	 *            name - cookie name
	 * @param String
	 *            value - cookie value
	 * @param maxAge -
	 *            maximum age of cookie
	 * @param HttpServletResponse
	 *            res - response object.
	 * @return void
	 * @author wengm 
	 * @since 2008-12-6
	 */
	public static void sendCookie(String name, String value, int maxAge,String path, boolean secure,
			HttpServletResponse res) {
		Cookie cookie = new Cookie(name, value);
		cookie.setMaxAge(maxAge);
		cookie.setPath(path);
		cookie.setSecure(secure);
		res.addCookie(cookie);
	}
}
