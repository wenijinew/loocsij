package org.loocsij.storage;
public class FindResult {
	private boolean accessed;
	private int container;
	private int unitIndex;
	public FindResult() {
		super();
	}
	/**
	 * @return Returns the container.
	 */
	public int getContainer() {
		return container;
	}
	/**
	 * @param container The container to set.
	 */
	public void setContainer(int container) {
		this.container = container;
	}
	/** 
	 * @return Returns the accessed.
	 */
	public boolean isAccessed() {
		return accessed;
	}
	/**
	 * @param accessed The accessed to set.
	 */
	public void setAccessed(boolean accessed) {
		this.accessed = accessed;
	}
	/**
	 * @return Returns the unitIndex.
	 */
	public int getUnitIndex() {
		return unitIndex;
	}
	/**
	 * @param unitIndex The unitIndex to set.
	 */
	public void setUnitIndex(int unitIndex) {
		this.unitIndex = unitIndex;
	}
}
