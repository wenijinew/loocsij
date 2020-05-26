package org.loocsij.web.bean;


public class AdminConfBean extends OptionBean{
	public AdminConfBean(){
	}
	private String name;
	private String path; 
	public String getName() { 
		return name;
	}
	public void setName(String name) {
		this.name = name;
	} 
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getValue() {
		return path;
	}
	public void setValue(String path) {
		this.path = path;
	}
}
