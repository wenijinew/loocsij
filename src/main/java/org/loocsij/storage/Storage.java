/*
 * @(#)Storage.java().
 */
package org.loocsij.storage;
/**
 * 1.add store unit().
 * 2.find the store unit index of the specified element().
 * 3.judge if this storage contain the specified element().
 * 4.export specified store unit to specified storage().
 * @author wengm
 * @see StorageImpl
 */
public interface Storage extends Container{
	/**
	 * 
	 * @param element
	 * @return int-the index of 
	 */
	public int findPosition(long element);
	public void export(Storage destination);
	public StoreUnit get(int index);
	public void add(StoreUnit unit);
	public void reset();
}
 