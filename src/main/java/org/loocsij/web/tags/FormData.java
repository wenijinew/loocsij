/**
 * 
 */
package org.loocsij.web.tags;

import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.logging.log4j.Logger;

import org.loocsij.SystemProperties;

/**
 * Form data wrapper - header portions and data body bytes array. (TO ADD)
 * 
 * @author wengm
 * @since 2008/12/25
 */
class FormData {
	private static Logger log = LogManager.getLogger(FormData.class);

	private static final String LINE_SEPARATOR = "\r\n";

	/**
	 * Content-Disposition data
	 */
	private String contentDisposition;

	/**
	 * parameter name in upload form, such as "picture"
	 */
	private String name;

	/**
	 * uploaded file name - absolute file path in upload machine such as
	 * "C:\Users\wengm\Pictures\01.jpg" which was displayed in file text field
	 * in file explorer during uploading.
	 */
	private String fileName;

	/**
	 * file short name - eg:01.jpg
	 */
	private String fileShortName;

	/**
	 * file extension such as ".jpg".
	 */
	private String fileExtension;

	/**
	 * uploaded file content type. for example, if uploaded file is 01.jpg, the
	 * content type should be "image/pjpeg".<br>
	 * Refer to <a>http://www.iana.org/assignments/media-types/</a> for more
	 * detail information about media type.
	 */
	private String contentType;

	private String boundary;

	private String contentTransferEncoding;

	/**
	 * data body bytes
	 */
	private byte[] data;

	/**
	 * form data header
	 */
	private String header;

	/**
	 * if this form data is file, if it is allowed to be uploaded.
	 */
	private boolean isAllowed;

	/**
	 * if this form data is file, why it is not allowed to be uploaded
	 */
	private String nowAllowedReason;

	private void setBoundary(String boundary) {
		this.boundary = boundary;
	}

	private void setContentDisposition(String contentDisposition) {
		this.contentDisposition = contentDisposition;
	}

	private void setContentTransferEncoding(String contentTransferEncoding) {
		this.contentTransferEncoding = contentTransferEncoding;
	}

	private void setContentType(String contentType) {
		this.contentType = contentType;
	}

	private void setFileName(String fileName) {
		this.fileName = fileName;
	}

	private void setName(String name) {
		this.name = name;
	}

	public String getHeader() {
		return header;
	}

	/**
	 * Store file data into specified real path.
	 * 
	 * @param -
	 *            realStorepath - physical file parent directory.
	 * @return void
	 * @author wengm
	 * @since 2008-12-26
	 */
	public void save(String realStorepath) throws IOException {
		String targetPath = realStorepath + SystemProperties.sFileSeparator
				+ this.getFileShortName();
		FileOutputStream fileout = new FileOutputStream(targetPath);
		fileout.write(this.getData());
		fileout.flush();
		fileout.close();
	}

	/**
	 * Set header. Meanwhile, parse header data.(TO ADD DETAIL)
	 * 
	 * @return void
	 * @author wengm
	 * @since 2008-12-26
	 */
	public void setHeader(String header) {

		this.header = header;
		String[] lines = header.split(LINE_SEPARATOR);
		String line = null;
		for (int k = 0; k < lines.length; k++) {
			line = lines[k];
			/*
			 * set each fields(Content-Disposition, name, filename) for current
			 * form data
			 */
			String[] portions = line.split("; ");
			String portion = null;

			// set data:Content-Disposition,name,filename
			if (line.startsWith("Content-Disposition")) {
				for (int i = 0; i < portions.length; i++) {
					portion = portions[i].trim();
					if (portion.startsWith("Content-Disposition")) {
						this.setContentDisposition(portion.substring(
								portion.indexOf(':') + 1).trim());
					}
					if (portion.startsWith("name")
							&& portion.indexOf("\"") >= 0) {
						this.setName(portion.substring(
								portion.indexOf("\"") + 1, portion
										.lastIndexOf("\"")));
					}
					if (portion.startsWith("filename")) {
						this.setFileName(portion.substring(portion
								.indexOf("\"") + 1, portion.lastIndexOf("\"")));
						this.setFileShortName(this.getFileName().substring(
								this.getFileName().lastIndexOf(
										SystemProperties.sFileSeparator) + 1));
						log.debug(this.getFileShortName());
						if (this.getFileShortName().lastIndexOf(".") == -1) {
							log.debug("file name" + this.getFileShortName()
									+ " has no extension");
						} else {
							this.setFileExtension(this.getFileShortName()
									.substring(
											this.getFileShortName()
													.lastIndexOf(".")));
						}
						log.info("filename is set" + this.getFileName());
					}
				}
				log.info("Content-Disposition is set");
			}

			/*
			 * set data: Content-Type, boundary(if contentType is
			 * "multipart/mixed")
			 */
			if (line.startsWith("Content-Type")) {
				for (int i = 0; i < portions.length; i++) {
					portion = portions[i].trim();
					if (portion.startsWith("Content-Type")) {
						this.setContentType(portion.substring(
								portion.indexOf(':') + 1).trim());
					}
					if (portion.startsWith("boundary")) {
						this.setBoundary(portion
								.substring(portion.indexOf("=") + 1));
					}
				}
				log.info("Content-Type is set");
			}

			// set data: Content-Transfer-Encoding
			if (line.startsWith("Content-Transfer-Encoding")) {
				for (int i = 0; i < portions.length; i++) {
					portion = portions[i].trim();
					if (portion.startsWith("Content-Transfer-Encoding")) {
						this.setContentTransferEncoding(portion.substring(
								portion.indexOf(':') + 1).trim());
					}
				}
				log.info("Content-Transfer-Encoding is set");
			}
		}// for end

	}

	public String toString() {
		return this.getHeader()+";"+this.getNowAllowedReason();
	}

	public boolean isFile() {
		return this.fileName != null && this.fileName.trim().length() > 0;
	}

	public String getContentType() {
		return contentType;
	}

	public String getFileName() {
		return fileName;
	}

	public String getName() {
		return name;
	}

	public String getContentDisposition() {
		return contentDisposition;
	}

	public String getBoundary() {
		return boundary;
	}

	public String getContentTransferEncoding() {
		return contentTransferEncoding;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public String getFileShortName() {
		return fileShortName;
	}

	public int getDataLength() {
		return this.getData().length;
	}

	private void setFileShortName(String fileShortName) {
		this.fileShortName = fileShortName;
	}

	public String getFileExtension() {
		return fileExtension;
	}

	private void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}

	public boolean isAllowed() {
		return isAllowed;
	}

	public void setAllowed(boolean isAllowed) {
		this.isAllowed = isAllowed;
	}

	public String getNowAllowedReason() {
		return nowAllowedReason;
	}

	public void setNowAllowedReason(String nowAllowedReason) {
		this.nowAllowedReason = nowAllowedReason;
	}
}