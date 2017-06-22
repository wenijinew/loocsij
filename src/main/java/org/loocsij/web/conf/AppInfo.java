package org.loocsij.web.conf;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class AppInfo extends HttpServlet {
	
	private static final long serialVersionUID = -624941345556609995L;

	private static String confPath;

	private static String imgPath;

	private static String jsPath;

	private static String cssPath;

	private static String logPath;

	private static void setConfPath(String confPath) {
		AppInfo.confPath = confPath;
	}

	private static void setCssPath(String cssPath) {
		AppInfo.cssPath = cssPath;
	}

	private static void setImgPath(String imgPath) {
		AppInfo.imgPath = imgPath;
	}

	private static void setJsPath(String jsPath) {
		AppInfo.jsPath = jsPath;
	}

	public static String getConfPath() {
		return confPath;
	}

	public static String getCssPath() {
		return cssPath;
	}

	public static String getImgPath() {
		return imgPath;
	}

	public static String getJsPath() {
		return jsPath;
	}

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		ServletContext app = config.getServletContext();
		AppInfo.setConfPath(app.getRealPath("conf"));
		AppInfo.setCssPath(app.getRealPath("css"));
		AppInfo.setImgPath(app.getRealPath("img"));
		AppInfo.setJsPath(app.getRealPath("js"));
		AppInfo.setLogPath(app.getRealPath("log"));
	}

	public static String getLogPath() {
		return logPath;
	}

	private static void setLogPath(String logPath) {
		AppInfo.logPath = logPath;
	}
}
