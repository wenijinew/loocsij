package org.loocsij.storage;
/**
 * Container.java
 * <p></p>
 * @author wengm wsxspring@yahoo.com.cn
 * @see Storage
 * @see StoreUnit
 * @see StorageImpl
 * @see StoreUnitImpl
 */
public interface Container {
	/**
	 * 
	 * @return int-Container capacity
	 */
	public int capacity();
	/**
	 * 
	 * @return int-Container's elements' count
	 */
	public int size();
	/** 
	 *
	 * @return boolean-the result of judging
	 */
	public boolean haveSpace();
	/**
	 * 
	 * @param element
	 * @return boolean-the result of judging
	 */
	public boolean contain(long element);
	/**
	 * 
	 * @param unit-
	 */
	public void importElements(StoreUnit unit);
}
