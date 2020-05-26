/*
 * Author: wengm@loocsij.com
 * Refer to:HTML4.01 Specification - 17.13.4 Form content types/multipart/form-data
 * Important: As with all MIME transmissions, "CR LF" (i.e., ??%0D%0A??) is used to separate
 lines of data. (From HTML4.01 Specification)
 * FileUpload related RFC documents:http://www.ietf.org/rfc/rfc2388.txt,http://www.ietf.org/rfc/rfc1867.txt.
 * All rights are reserved.
 *//*

package org.loocsij.web.tags;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.apache.logging.log4j.Logger;

import org.loocsij.util.StringUtil;

*/
/**
 * Upload file tag. Preparation for uploading files with web form:<br>
 * <ul>
 * <li>Read 17.13.4 of <a
 * href="http://www.w3.org/TR/1999/REC-html401-19991224/">HTML 4.01
 * Specification</a> to understand Form content types.</li>
 * <li>If file name contain non-ASCII character, set charset attributes:
 * <ul>
 * <li>charset item of contentType attribute of page directive, eg:
 * 
 * <pre>
 *                  &lt;%@ page language=&quot;java&quot; contentType=&quot;text/html; charset=UTF-8&quot; %&gt;
 * </pre>
 * 
 * </li>
 * <li>pageEncoding attribute of page directive, eg:
 * 
 * <pre>
 *                  &lt;%@ page language=&quot;java&quot; contentType=&quot;text/html; charset=UTF-8&quot;
 *                   pageEncoding=&quot;UTF-8&quot;%&gt;
 * </pre>
 * 
 * </li>
 * <li>charset item of content attribute of meta tag, eg:
 * 
 * <pre>
 *                  &lt;meta http-equiv=&quot;Content-Type&quot; content=&quot;text/html; charset=UTF-8&quot;&gt;
 * </pre>
 * 
 * </li>
 * <li>set "encoding" attribute of this tag the same as that of attributes
 * above.</li>
 * </ul>
 * </li>
 * <li>decide target path in web application. set relative path to web
 * application root as value of attribute "storePath" of this tag. For example,
 * "/img/upload"</li>
 * <li>determain rule set: file extension, file size, file number, file name
 * length, total data length</li>
 * </ul>
 * 
 * Usage:<br>
 * TO ADD
 * 
 * @author wengm
 * @since 2008/12/25
 *//*

public class FileUploadTag extends SimpleTagSupport {

	*/
/**
	 * logger
	 *//*

	private static Logger log = LogManager.getLogger(FileUploadTag.class);

	*/
/**
	 * where to save uploaded file - relative path to the web application root,
	 * such as "/img/upload/". This value will be transfered to physical path
	 * {@link #realStorePath}. For example, If $webroot is "c:/web/", then
	 * "/img/upload" will be transfered to "c:/web/img/upload".
	 *//*

	private String storePath;

	*/
/**
	 * if file name contain non-ASCII character, encoding should be set. "UTF-8"
	 * is strongly recommended. in submit page, all attributes related with
	 * encoding should be set. Typically, should set following items:<br>
	 * <ul>
	 * <li>
	 * 
	 * <pre>
	 *                       &lt;%@ page language=&quot;java&quot; contentType=&quot;text/html; charset=UTF-8&quot;
	 *                        pageEncoding=&quot;UTF-8&quot; isELIgnored=&quot;false&quot;%&gt;
	 * </pre>
	 * 
	 * </li>
	 * <li><meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></li>
	 * 
	 * </ul>
	 *//*

	private String encoding;

	// /////////////////////////////////////////////////////////
	// Restrictions:
	// maximum file size, file extension, maximum file number, maximum file name
	// length
	// total data length
	// /////////////////////////////////////////////////////////
	private int maxFileSize;

	*/
/**
	 * allowed file extensions, separated by ";"
	 *//*

	private String allowFileExtensions;

	private int maxFileNumber;

	private int maxFileNameLength;

	private int maxDataLength;

	// /////////////////////////////////////////////////////////
	// CONSTANTS:
	// fail attribute name, file extension separator
	// /////////////////////////////////////////////////////////
	public static final String FILEUPLOAD_FAIL_REASON = "fileuploadfailreason";

	public static final String FILEUPLOAD_FAIL_FILE = "fileuploadfailfiles";

	private static final String FILEEXTENSION_SEPARATOR = ";";

	*/
/**
	 * {@link #storePath} is relative path. To save file, it should be converted
	 * to real physical path.
	 * 
	 * @see #storePath
	 *//*

	private String realStorePath;

	private PageContext cxt;

	*/
/**
	 * Do tag.
	 *//*

	public void doTag() throws JspException, IOException {
		super.doTag();
		// page context
		cxt = (PageContext) this.getJspContext();

		// real path of store path
		realStorePath = cxt.getServletConfig().getServletContext().getRealPath(
				this.storePath);

		// http request and servlet input stream
		HttpServletRequest req = (HttpServletRequest) cxt.getRequest();

		// data length
		int length = req.getContentLength();
		if (this.getMaxDataLength() > 0 && length > this.getMaxDataLength()) {
			cxt.setAttribute(FILEUPLOAD_FAIL_REASON, "Total data length("
					+ toHumanReadable(length) + ") is larger than maximum acceptable value("
					+ toHumanReadable(this.getMaxDataLength()) + ").");
			return;
		}

		// get boundary - if contentType is null, that mean there is not file
		// uploaded(TO CHECK)
		String contentType = req.getContentType();
		if (contentType == null) {
			cxt.setAttribute("error", "no data.");
			return;
		}
		String boundary = contentType.substring(contentType.indexOf("=") + 1);

		// data byte array
		ServletInputStream in = req.getInputStream();
		byte[] reqdata = this.getDataByteArray(in);

		// all form data wrappers
		FormData[] formDatas = this.getFormDatas(reqdata, boundary);

		// files
		FormData[] files = this.getFiles(formDatas);

		// files that is failed in saving - CAN NOT BE SAVED, DOES NOT ACCORD
		// THE RULE
		FormData[] failSaveFiles = saveAllFiles(files);
		this.setAttribute(FILEUPLOAD_FAIL_FILE, failSaveFiles);
	}

	*/
/**
	 * Save all files
	 * 
	 * @return FormData[] - failed ones
	 * @author wengm
	 * @since 2008-12-26
	 *//*

	private FormData[] saveAllFiles(FormData[] files) throws IOException {
		FormData formData = null;
		ArrayList failFiles = new ArrayList();
		for (int i = 0; i < files.length; i++) {
			formData = files[i];
			if (canSave(formData)) {
				formData.save(this.getRealStorePath());
			} else {
				log.info(formData.getFileName() + " can not be saved, because "
						+ formData.getNowAllowedReason());
				failFiles.add(formData);
			}
		}

		if (failFiles.size() == 0) {
			return new FormData[0];
		}

		FormData[] fds = new FormData[failFiles.size()];
		failFiles.toArray(fds);
		return fds;
	}

	*/
/**
	 * Restrict save action.<br>
	 * <ul>
	 * <li>File total can not be larger than allowed maximum value, if the
	 * maximum value is set.</li>
	 * <li>File extension is contained in allowed file extension, if allowed
	 * file extensions is set.</li>
	 * <li>File short name length is not longer than allowed maximum file short
	 * name length, if the allowed maximum file short name length value is set.</li>
	 * <li>File size is not larger than allowed maximum file size, if the
	 * maximum file size is set.</li>
	 * </ul>
	 * 
	 * @return boolean
	 * @author wengm
	 * @since 2008-12-26
	 *//*

	private boolean canSave(FormData file) {
		// if file is not allowed, return false;
		if (!file.isAllowed()
				&& !StringUtil.isEmpty(file.getNowAllowedReason())) {
			return false;
		}

		String fileExtension = file.getFileExtension();
		String fileShortName = file.getFileShortName();
		int fileSize = file.getDataLength();

		// file extension check
		String allowedFileExts = this.getAllowFileExtensions();
		if (!StringUtil.isEmpty(allowedFileExts)) {
			String[] exts = allowedFileExts.split(FILEEXTENSION_SEPARATOR);
			for (int i = 0; i < exts.length; i++) {
				if (exts[i].equalsIgnoreCase(fileExtension)) {
					break;
				}
			}
			file
					.setNowAllowedReason(fileExtension
							+ " is not supported. Currently, supported extensions contain ("
							+ allowedFileExts + ").");
			return false;
		}

		// file name length check
		if (fileShortName.length() == 0
				|| (this.getMaxFileNameLength() > 0 && fileShortName.length() > this
						.getMaxFileNameLength())) {
			file
					.setNowAllowedReason("file name '"
							+ fileShortName
							+ "' is not supported. file name length should be longer than 0 and shorter than "
							+ this.getMaxFileNameLength() + ".");
			return false;
		}

		// file size check
		if (this.getMaxFileSize() > 0 && fileSize > this.getMaxFileSize()) {
			file.setNowAllowedReason("file size '" + toHumanReadable(fileSize)
					+ "' is not supported. Maximum acceptable file size is "
					+ toHumanReadable(this.getMaxFileSize()) + ".");
			return false;
		}

		return true;
	}

	private String toHumanReadable(int size) {
		double oneKilo = 1024;
		double oneMeg = 1024 * 1024;
		double k = 0.0;
		double m = 0.0;
		String strK = null;
		String strM = null;
		if (size >= oneMeg) {
			m = size / oneMeg;
			strM = String.valueOf(m);
			if(strM.length()>3){
				strM = strM.substring(0,3);
			}
			return strM + " M";
		} else if (size >= oneKilo) {
			k = size / oneKilo;
			strK = String.valueOf(k);
			if(strK.length()>3){
				strK = strK.substring(0,3);
			}
			return strK + " K";
		} else {
			return size + " Bytes";
		}
	}

	*/
/**
	 * 
	 * @return void
	 * @author wengm
	 * @since 2008-12-28
	 *//*

	private void setAttribute(String key, Object value) {
		if (this.cxt == null) {
			throw new RuntimeException("cxt is not initialized yet.");
		}
		this.cxt.setAttribute(key, value);
	}

	*/
/**
	 * Get data in byte array
	 * 
	 * @return byte[]
	 * @author wengm
	 * @since 2008-12-24
	 *//*

	private byte[] getDataByteArray(ServletInputStream in) throws IOException {
		// get data byte array
		ByteArrayOutputStream byteout = new ByteArrayOutputStream();
		int count = 0;
		byte[] bs = new byte[1024];
		while ((count = in.read(bs)) != -1) {
			byteout.write(bs, 0, count);
		}
		byteout.flush();
		return byteout.toByteArray();
	}

	*/
/**
	 * Get file data index wrapper container
	 * 
	 * @return ArrayList
	 * @author wengm
	 * @since 2008-12-24
	 *//*

	private FormData[] getFormDatas(byte[] reqdata, String boundary)
			throws IOException {
		// boundary length
		int boundaryLength = boundary.getBytes().length;

		// data length
		int length = reqdata.length;

		// define file data wrapper variable and container
		ArrayList formDatas = new ArrayList();
		FormData formData = null;
		int fileNumber = 0;

		// reset count
		int lineStartIndex = 0;

		int startHeaderCount = 0;
		int overHeaderCount = 0;
		int dataStartIndex = 0;
		int dataEndIndex = 0;

		int headerStartIndex = 0;
		int headerEndIndex = 0;
		boolean headerStart = false;
		boolean headerEnd = false;
		boolean headerSet = false;
		boolean dataEnd = false;
		boolean dataSet = false;
		for (int i = 0; i < length; i++) {
			// if data end, break;
			if (i == length - 1 || i + 4 >= length) {
				break;
			}

			// carriage return
			if (reqdata[i] == 13 && reqdata[i + 1] == 10) {
				// [lineStartIndex]--{boundary}{\r\n}
				// [header start]{header content}[header end]{\r\n}
				// {\r\n}
				// [data start]{data body content}[data end]{\r\n}
				// [lineStartIndex]--{boundary}{\r\n}
				if ((i - lineStartIndex) == boundaryLength + 2) {
					byte[] bytes = this.getSubByteArray(reqdata,
							lineStartIndex, i - lineStartIndex);
					String line = new String(bytes);
					if (line.equals("--" + boundary)) {
						startHeaderCount++;
						headerStartIndex = i + 2;
						headerStart = true;
						headerEnd = false;
						if (lineStartIndex > 0) {
							overHeaderCount++;
							dataEndIndex = lineStartIndex - 3;
							dataEnd = true;
							dataSet = false;
						}
					}
				} else if (reqdata[i + 2] == 13 && headerStart && !headerEnd) {
					headerEndIndex = i - 1;
					dataStartIndex = i + 4;
					headerEnd = true;
					headerSet = false;
					headerStart = false;
				}
				lineStartIndex = i + 2;

				// header end
				// -----------------------------7d83241040fe0
				// Content-Disposition: form-data; name="nickname"[header end]
				// [blank line]
				// [empty data]
				// -----------------------------7d83241040fe0
				if (!headerSet && headerEnd) {
					// get header bytes
					byte[] headerBytes = this.getSubByteArray(reqdata,
							headerStartIndex, headerEndIndex - headerStartIndex
									+ 1);

					// create new form data and set header
					formData = new FormData();
					String header = new String(headerBytes, this.getEncoding());
					formData.setHeader(header);
					headerSet = true;
					if (formData.isFile()) {
						fileNumber++;
						// if file number is larger than maximum allowed file
						// number, will not set data body
						if (fileNumber > this.getMaxFileNumber()) {
							formData.setAllowed(false);
							formData
									.setNowAllowedReason("File total("
											+ fileNumber
											+ ") is larger than maximum allowed number("
											+ this.getMaxFileNumber() + ").");
							continue;
						}
					}
				}
				if (headerSet && !dataSet && dataEnd) {
					// get data bytes
					byte[] dataBodyBytes = this.getSubByteArray(reqdata,
							dataStartIndex, dataEndIndex - dataStartIndex + 1);

					// set form data body
					formData.setData(dataBodyBytes);
					// add into form data container
					formDatas.add(formData);

					dataSet = true;
				}
			}

		}

		if (formDatas.size() == 0) {
			return new FormData[0];
		}
		FormData[] fds = new FormData[formDatas.size()];
		formDatas.toArray(fds);
		return fds;
	}

	*/
/**
	 * Extract subarray from specified byte array.
	 * 
	 * @param bytes -
	 *            byte array
	 * @param offset -
	 *            start index of subarray in original byte array
	 * @param length -
	 *            length of subarray
	 * @return byte[] - subarray of byte array
	 * @author wengm
	 * @since 2008-12-26
	 *//*

	private byte[] getSubByteArray(byte[] bytes, int offset, int length)
			throws IOException {
		byte[] subBytes = new byte[length];
		for (int i = 0; i < length; i++) {
			subBytes[i] = bytes[offset + i];
		}
		return subBytes;
	}

	*/
/**
	 * Get form datas that is file.
	 * 
	 * @param formDatas -
	 *            all form data wrappers
	 * @return int
	 * @author wengm
	 * @since 2008-12-23
	 *//*

	private FormData[] getFiles(FormData[] formDatas) throws IOException {
		// extract file form datas
		int length = formDatas.length;
		FormData formData = null;
		ArrayList fileDatas = new ArrayList();
		for (int i = 0; i < length; i++) {
			formData = formDatas[i];
			if (formData.isFile()) {
				fileDatas.add(formData);
			}
		}

		// if no file, return empty array
		if (fileDatas.size() == 0) {
			return new FormData[0];
		}

		// convert data into array
		FormData[] fds = new FormData[fileDatas.size()];
		fileDatas.toArray(fds);

		// return
		return fds;
	}

	public String getStorePath() {
		return storePath;
	}

	public void setStorePath(String storePath) {
		this.storePath = storePath;
	}

	public static void main(String[] strs) throws IOException {
	}

	public String getRealStorePath() {
		return realStorePath;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public String getAllowFileExtensions() {
		return allowFileExtensions;
	}

	public void setAllowFileExtensions(String allowFileExtensions) {
		this.allowFileExtensions = allowFileExtensions;
	}

	public int getMaxFileNameLength() {
		return maxFileNameLength;
	}

	public void setMaxFileNameLength(int maxFileNameLength) {
		this.maxFileNameLength = maxFileNameLength;
	}

	public int getMaxFileNumber() {
		return maxFileNumber;
	}

	public void setMaxFileNumber(int maxFileNumber) {
		this.maxFileNumber = maxFileNumber;
	}

	public int getMaxFileSize() {
		return maxFileSize;
	}

	public void setMaxFileSize(int maxFileSize) {
		this.maxFileSize = maxFileSize;
	}

	public int getMaxDataLength() {
		return maxDataLength;
	}

	public void setMaxDataLength(int maxDataLength) {
		this.maxDataLength = maxDataLength;
	}
}
*/
