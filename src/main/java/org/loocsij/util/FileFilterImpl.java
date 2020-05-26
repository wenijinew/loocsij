package org.loocsij.util;

import java.io.File;
import java.io.FileFilter;

public class FileFilterImpl implements FileFilter{
	private String extension;
	public FileFilterImpl(String extension) {
		this.extension = extension;
	}
	public FileFilterImpl() {
	}
	public boolean accept(File pathname) {
		if(this.extension==null||this.extension.equals("")) {
			return true;
		}
		return pathname.getName().endsWith(this.extension);
	}
}
