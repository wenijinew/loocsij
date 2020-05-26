package org.loocsij.web.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;

import org.loocsij.web.tags.ASimpleTag;

public class ResourceReaderTag extends ASimpleTag {
	private String preferredLanguage;

	private String key;

	private String browserLanguage;

	public String getBrowserLanguage() {
		return browserLanguage;
	}

	public void setBrowserLanguage(String browserLanguage) {
		this.browserLanguage = browserLanguage;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getPreferredLanguage() {
		return preferredLanguage;
	}

	public void setPreferredLanguage(String preferredLanguage) {
		this.preferredLanguage = preferredLanguage;
	}

	public ResourceReaderTag() {
	}

	public void doTag() throws JspException, IOException {
		super.doTag();
		
	}
}
