package org.loocsij.storage;
/** 
 * StoreUnit.java().
 * <p>
 * 1.add element().
 * 2.sort elements().
 * 3.find the specified element's position().
 * 4.judge if contains specified element().
 * </p>
 * @author wengm
 *	
 */
public interface StoreUnit extends Container{
	public void add(long element);
	public void sort();
	public boolean contain(long element);
	public long get(int index);
	public void set(int index, long element);
	public void reset();
}
 