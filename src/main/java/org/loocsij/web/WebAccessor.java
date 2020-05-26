package org.loocsij.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.HttpURLConnection;
import java.util.Hashtable;
import org.apache.logging.log4j.Logger;

import org.loocsij.SystemProperties;
import org.loocsij.logger.*;
import org.loocsij.util.FileUtil;

/**
 * Web Utility - Get information of specified url.
 * 
 * @author wengm
 * 
 */
public class WebAccessor {
	class FileNameMap implements java.net.FileNameMap {
		String[] extensions = { "abs", "ai", "aif", "aifc", "aiff", "aim",
				"art", "asf", "asx", "au", "avi", "avx", "bcpio", "bin",
				"bmp", "body", "cdf", "cer", "class", "cpio", "csh", "css",
				"dib", "doc", "dtd", "dv", "dvi", "eps", "etx", "exe",
				"gif", "gtar", "gz", "hdf", "hqx", "htc", "htm", "html",
				"hqx", "ief", "jad", "jar", "java", "jnlp", "jpe", "jpeg",
				"jpg", "js", "jsf", "jspf", "kar", "latex", "m3u", "mac",
				"man", "me", "mid", "midi", "mif", "mov", "movie", "mp1",
				"mp2", "mp3", "mpa", "mpe", "mpeg", "mpega", "mpg", "mpv2",
				"ms", "nc", "oda", "pbm", "pct", "pdf", "pgm", "pic",
				"pict", "pls", "png", "pnm", "pnt", "ppm", "ppt", "ps",
				"psd", "qt", "qti", "qtif", "ras", "rgb", "rm", "roff",
				"rtf", "rtx", "sh", "shar", "smf", "sit", "snd", "src",
				"sv4cpio", "sv4crc", "swf", "t", "tar", "tcl", "tex",
				"texi", "texinfo", "tif", "tiff", "tr", "tsv", "txt",
				"ulw", "ustar", "xbm", "xht", "xhtml", "xml", "xpm", "xsl",
				"xwd", "wav", "svg", "svgz", "vsd", "wbmp", "wml", "wmlc",
				"wmls", "wmlscriptc", "wrl", "Z", "z", "zip", };
	
		String[] miniTypes = { "audio/x-mpeg", "application/postscript",
				"audio/x-aiff", "audio/x-aiff", "audio/x-aiff",
				"application/x-aim", "image/x-jg", "video/x-ms-asf",
				"video/x-ms-asf", "audio/basic", "video/x-msvideo",
				"video/x-rad-screenplay", "application/x-bcpio",
				"application/octet-stream", "image/bmp", "text/html",
				"application/x-cdf", "application/x-x509-ca-cert",
				"application/java", "application/x-cpio",
				"application/x-csh", "text/css", "image/bmp",
				"application/msword", "text/plain", "video/x-dv",
				"application/x-dvi", "application/postscript",
				"text/x-setext", 
//				"application/octet-stream",
				"application/x-sdlc",
				"image/gif",
				"application/x-gtar", "application/x-gzip",
				"application/x-hdf", "application/mac-binhex40",
				"text/x-component", "text/html", "text/html",
				"application/mac-binhex40", "image/ief",
				"text/vnd.sun.j2me.app-descriptor",
				"application/java-archive", "text/plain",
				"application/x-java-jnlp-file", "image/jpeg", "image/jpeg",
				"image/jpeg", "text/javascript", "text/plain",
				"text/plain", "audio/x-midi", "application/x-latex",
				"audio/x-mpegurl", "image/x-macpaint",
				"application/x-troff-man", "application/x-troff-me",
				"audio/x-midi", "audio/x-midi", "application/x-mif",
				"video/quicktime", "video/x-sgi-movie", "audio/x-mpeg",
				"audio/x-mpeg", "audio/x-mpeg", "audio/x-mpeg",
				"video/mpeg", "video/mpeg", "audio/x-mpeg", "video/mpeg",
				"video/mpeg2", "application/x-wais-source",
				"application/x-netcdf", "application/oda",
				"image/x-portable-bitmap", "image/pict", "application/pdf",
				"image/x-portable-graymap", "image/pict", "image/pict",
				"audio/x-scpls", "image/png", "image/x-portable-anymap",
				"image/x-macpaint", "image/x-portable-pixmap",
				"application/powerpoint", "application/postscript",
				"image/x-photoshop", "video/quicktime",
				"image/x-quicktime", "image/x-quicktime",
				"image/x-cmu-raster", "image/x-rgb",
				"application/vnd.rn-realmedia", "application/x-troff",
				"application/rtf", "text/richtext", "application/x-sh",
				"application/x-shar", "audio/x-midi",
				"application/x-stuffit", "audio/basic",
				"application/x-wais-source", "application/x-sv4cpio",
				"application/x-sv4crc", "application/x-shockwave-flash",
				"application/x-troff", "application/x-tar",
				"application/x-tcl", "application/x-tex",
				"application/x-texinfo", "application/x-texinfo",
				"image/tiff", "image/tiff", "application/x-troff",
				"text/tab-separated-values", "text/plain", "audio/basic",
				"application/x-ustar", "image/x-xbitmap",
				"application/xhtml+xml", "application/xhtml+xml",
				"text/xml", "image/x-xpixmap", "text/xml",
				"image/x-xwindowdump", "audio/x-wav", "image/svg+xml",
				"image/svg+xml", "application/x-visio",
				"image/vnd.wap.wbmp", "text/vnd.wap.wml",
				"application/vnd.wap.wmlc", "text/vnd.wap.wmlscript",
				"application/vnd.wap.wmlscriptc", "x-world/x-vrml",
				"application/x-compress", "application/x-compress",
				"application/zip", };
	
		public String getContentTypeFor(String fileName) {
			String extension = FileUtil.getFileExtension(fileName);
			for (int i = 0; i < extensions.length; i++) {
				if (extension != null
						&& (extension.equalsIgnoreCase(extensions[i]) || extension
								.equalsIgnoreCase("." + extensions[i]))) {
					return miniTypes[i];
				}
			}
			return null;
		}
		
		
	
		public String[] getExtensions() {
			return extensions;
		}



		public String[] getMiniTypes() {
			return miniTypes;
		}



		private FileNameMap() {
		}
	}

	private static Logger log = Log.getLogger(WebAccessor.class);

	private String strURL = null;

	private URL url = null;

	private HttpURLConnection con = null;

	private static FileNameMap map = null;

	private static Hashtable webs = new Hashtable();

	public HttpURLConnection getCon() {
		return con;
	}

	public void setCon(HttpURLConnection con) {
		this.con = con;
	}

	public String getStrURL() {
		return strURL;
	}

	public void setStrURL(String strURL) {
		this.strURL = strURL;
	}

	public URL getUrl() {
		return url;
	}

	public void setUrl(URL url) {
		this.url = url;
	}

	/**
	 * Construct instance of org.loocsij.util.WebUtil with given URL string. This
	 * url string will be wrapped with java.net.URL. One connection to this URL
	 * will be opened for getting kinds of information of this URL.
	 * 
	 * @param strURL
	 * @throws IOException
	 */
	private WebAccessor() {
	}

	public static WebAccessor getInstance(String strURL) {
		Object obj = webs.get(strURL);
		if (obj != null) {
			return (WebAccessor) obj;
		}
		URL url = null;
		HttpURLConnection con = null;
		try {
			url = new URL(strURL);
		} catch (java.net.MalformedURLException e) {
			log.error(new WebAccessException(
					"Invalid url ["
							+ strURL + "]", e));
			return null;
		}
		try {
			con = (HttpURLConnection) url.openConnection();
		} catch (IOException e) {
			log.error(new WebAccessException(
					"Can not connect ["
							+ strURL + "]", e));
			return null;
		}
		try {
			if (!con.getDoInput()) {
				con.setDoInput(true);
			}
			if (!con.getDoOutput()) {
				con.setDoOutput(true);
			}
		} catch (IllegalStateException e) {
			log.error(new WebAccessException(
					"Illegal State (Already Connect)!", e));
			return null;
		}
		try {
			if (con.getResponseCode() != 200) {
				return null;
			}
			con.connect();
		}catch(IOException e) {
			log.error(new WebAccessException(
					"Can not get response code from ["+strURL+"]", e));
			return null;
		}
		if (url == null || con == null) {
			return null;
		}
		WebAccessor web = new WebAccessor();
		web.setStrURL(strURL);
		web.setUrl(url);
		web.setCon(con);
		return web;
	}

	public String getContentEncoding() {
		return con.getContentEncoding();
	}

	public String getContentType() {
		return con.getContentType();
	}

	public FileNameMap getFileNameMap() {
		if (map != null) {
			return map;
		}
		map = new FileNameMap();
		return map;
	}

	public void connect() throws IOException {
		con.connect();
	}

	public InputStream getInputStream() throws IOException {
		return con.getInputStream();
	}

	public OutputStream getOutputStream() throws IOException {
		return con.getOutputStream();
	}

	public boolean getDoInput() {
		return con.getDoInput();
	}

	public void setDoInput(boolean doinput) {
		con.setDoInput(doinput);
	}

	public boolean getDoOutput() {
		return con.getDoOutput();
	}

	public void setDoOutput(boolean dooutput) {
		con.setDoInput(dooutput);
	}

	public String getFile() {
		return url.getFile();
	}

	public String getPath() {
		return url.getPath();
	}

	public String getHost() {
		return url.getHost();
	}

	public int getPort() {
		return url.getPort();
	}

	public String getProtocol() {
		return url.getProtocol();
	}

	/**
	 * Get response of this URL.
	 * 
	 * @return String - response of the url.
	 */
	public static String getWebResponse(String strURL) {
		WebAccessor web = getInstance(strURL);
		if (web == null) {
			return null;
		}else{
			log.info(web);
		}
		
		String contentEncoding = web.getContentEncoding();
		InputStream in = null;
		InputStreamReader isreader = null;
		BufferedReader breader = null;
		StringBuffer buf = new StringBuffer();
		char[] cs = new char[2048];
		int count = 0;
		try {
			web.connect();
			in = web.getInputStream();
			isreader = new InputStreamReader(in,
					contentEncoding == null ? SystemProperties.sFileEncoding
							: contentEncoding);
			breader = new BufferedReader(isreader);
			while ((count = breader.read(cs, 0, cs.length)) != -1) {
				buf.append(cs, 0, count);
			}
			return buf.toString();
		} catch (IOException e) {
			log.error(new WebAccessException("Fai to get response of ["
					+ strURL + "]", e));
			return null;
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException ioe) {
					log.error(new WebAccessException("Fai to get response of ["
							+ strURL + "]", ioe));
				}
			}
			if (isreader != null) {
				try {
					isreader.close();
				} catch (IOException ioe) {
					log.error(new WebAccessException("Fai to get response of ["
							+ strURL + "]", ioe));
				}
			}
		}
	}

	/**
	 * writer string to web resource
	 * 
	 * @param strURL
	 * @param content
	 */
	public static void writeString(String strURL, String content) {
		WebAccessor web = getInstance(strURL);
		if (web == null) {
			log.info("Fail to access [" + strURL + "]");
			return;
		}
		OutputStream out = null;
		OutputStreamWriter writer = null;
		try {
			out = web.getOutputStream();
			String contentEncoding = web.getContentEncoding();
			writer = new OutputStreamWriter(out,
					contentEncoding == null ? SystemProperties.sFileEncoding
							: contentEncoding);
			writer.write(content);
			writer.flush();
		} catch (IOException e) {
			log.error(new WebAccessException("Fai to write content to ["
					+ strURL + "]", e));
			return;
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException ioe) {
					log.error(new WebAccessException(
							"Fai to write content to [" + strURL + "]", ioe));
				}
			}
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException ioe) {
					log.error(new WebAccessException(
							"Fai to write content to [" + strURL + "]", ioe));
				}
			}
		}
	}

	/**
	 * write bytes to web resource
	 * 
	 * @param strURL
	 * @param bytes
	 */
	public static void writeBytes(String strURL, byte[] bytes) {
		WebAccessor web = getInstance(strURL);
		if (web == null) {
			log.info("Fail to access [" + strURL + "]");
			return;
		}
		web.setDoOutput(true);
		OutputStream out = null;
		try {
			out = web.getOutputStream();
			out.write(bytes);
		} catch (IOException ioe) {
			log.error(new WebAccessException("Fai to write bytes to [" + strURL
					+ "]", ioe));
			return;
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException ioe) {
					log.error(new WebAccessException("Fai to write bytes to ["
							+ strURL + "]", ioe));
				}
			}
		}
	}

	/**
	 * write page response into specified file
	 * 
	 * @param targetFileName
	 */
	public static void storeWebPage(String url, String targetFileName) {
		String response = getWebResponse(url);
		if (response == null) {
			log.info("Fail to get response from [" + url + "]");
			return;
		}
		FileUtil.writeString(response, targetFileName);
	}
	
	public String toString(){
		String dilimiter = "$";
		return this.getProtocol()+dilimiter+this.getHost()+dilimiter+this.getPort()+dilimiter+this.getPath();
	}

	public static void println() {
		System.out.println();
	}

	public static void main(String[] args) {
		//writeString("http://www.baidu.com/home.html", "<b>baidu</b>");
		System.out.println(getWebResponse("http://hibernate.sourceforge.net/hibernate-configuration-3.0.dtd"));
	}
}
