package org.loocsij.storage;
public class CapacityInitor {
	private int blockCapacity;
	private int memoryBlockCapacity;
	private int diskBlockCapacity;
	private int cacheCapacity;
	private int memoryCapacity;
	private int diskCapacity;
	private String exportFilePath;
	/**
	 * @return Returns the blockCapacity.
	 */
	public int getBlockCapacity() {
		return blockCapacity;
	}
	/**
	 * @param blockCapacity
	 *            The blockCapacity to set.
	 */
	public void setBlockCapacity(int blockCapacity) {
		this.blockCapacity = blockCapacity;
	}
	/** 
	 * @return Returns the cacheCapacity.
	 */
	public int getCacheCapacity() {
		return cacheCapacity;
	}
	/**
	 * @param cacheCapacity
	 *            The cacheCapacity to set.
	 */
	public void setCacheCapacity(int cacheCapacity) {
		this.cacheCapacity = cacheCapacity;
	}
	/**
	 * @return Returns the diskBlockCapacity.
	 */
	public int getDiskBlockCapacity() {
		return diskBlockCapacity;
	}
	/**
	 * @param diskBlockCapacity
	 *            The diskBlockCapacity to set.
	 */
	public void setDiskBlockCapacity(int diskBlockCapacity) {
		this.diskBlockCapacity = diskBlockCapacity;
	}
	/**
	 * @return Returns the diskCapacity.
	 */
	public int getDiskCapacity() {
		return diskCapacity;
	}
	/**
	 * @param diskCapacity
	 *            The diskCapacity to set.
	 */
	public void setDiskCapacity(int diskCapacity) {
		this.diskCapacity = diskCapacity;
	}
	/**
	 * @return Returns the memoryBlockCapacity.
	 */
	public int getMemoryBlockCapacity() {
		return memoryBlockCapacity;
	}
	/**
	 * @param memoryBlockCapacity
	 *            The memoryBlockCapacity to set.
	 */
	public void setMemoryBlockCapacity(int memoryBlockCapacity) {
		this.memoryBlockCapacity = memoryBlockCapacity;
	}
	/**
	 * @return Returns the memoryCapacity.
	 */
	public int getMemoryCapacity() {
		return memoryCapacity;
	}
	/**
	 * @param memoryCapacity
	 *            The memoryCapacity to set.
	 */
	public void setMemoryCapacity(int memoryCapacity) {
		this.memoryCapacity = memoryCapacity;
	}
	/**
	 * @return Returns the exportFilePath.
	 */
	public String getExportFilePath() {
		return exportFilePath;
	}
	/**
	 * @param exportFilePath The exportFilePath to set.
	 */
	public void setExportFilePath(String exportFilePath) {
		this.exportFilePath = exportFilePath;
	}
	/**
	 * @return Returns the init.
	 */
	public boolean isInit() {
		return this.blockCapacity > 0 && this.cacheCapacity > 0
				&& this.diskBlockCapacity > 0 && this.diskCapacity > 0
				&& this.memoryBlockCapacity > 0 && this.memoryCapacity > 0
				&& this.exportFilePath!=null && !this.exportFilePath.equals("");
	}
	public CapacityInitor() {
	}
}
