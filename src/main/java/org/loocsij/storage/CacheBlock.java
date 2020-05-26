package org.loocsij.storage;
/**
 * <p>
 * CacheBlock.java
 * The unit of the Storage system.Block is the unit of Cache.
 * </p>
 * @author wengm
 *
 */
public class CacheBlock extends StoreUnitImpl{
	public CacheBlock() {
		super();
		this.capacity=32;
		this.elements=new long[this.capacity];
	}
	public CacheBlock(int capacity){
		super();
		this.capacity=capacity;
		this.elements=new long[this.capacity];
	}
	public void add(long element){
		if(!haveSpace()){
			throw new RuntimeException("this block has been full,please select next block or export data to memory!");
		} 
		super.add(element);
	}
	public void reset(){
		for(int index=0;index<this.size;index++){
			this.elements[index]=0L;
		}
		this.size=0;
	}
}
