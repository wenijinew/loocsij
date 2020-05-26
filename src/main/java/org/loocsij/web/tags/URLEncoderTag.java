package org.loocsij.web.tags;

import org.loocsij.web.tags.ASimpleTag;

/**
 * URLEncoderTag - encode url including request page and parameters.
 * 
 * @author wengm
 * 
 */
public class URLEncoderTag extends ASimpleTag {
	/**
	 * request page - may be absolute or relative path including extension.
	 */
	private String requestPage;

	/**
	 * parameter pairs, separated by &. For example: name=value&name1=value1...
	 */
	private String parameters;

	/**
	 * @return String
	 * @author wengm
	 * @since 2008-12-6
	 */
	public String getParameters() {
		return parameters;
	}

	
	/**
	 * @return void
	 * @author wengm
	 * @since 2008-12-6
	 */
	public void setParameters(String parameters) {
		this.parameters = parameters;
	}

	/**
	 * @return String
	 * @author wengm
	 * @since 2008-12-6
	 */
	public String getRequestPage() {
		return requestPage;
	}

	/**
	 * @return void
	 * @author wengm
	 * @since 2008-12-6
	 */
	public void setRequestPage(String requestPage) {
		this.requestPage = requestPage;
	}

}
