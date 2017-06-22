package org.loocsij.storage;
/**
 * 
 * @author wengm
 *
 */
public class Cache extends StorageImpl{
	private CacheBlock[] units;
	private int capacity = 4;
	public Cache(){
		super();
		this.units = new CacheBlock[capacity];
		for(int index=0,length=this.capacity;index<length;index++){
			this.units[index]=new CacheBlock();
		}
	}
	public Cache(int capacity,int blockCapacity){
		super();
		this.capacity=capacity;
		this.units = new CacheBlock[capacity];
		/*!!Note:if no this operation,java.lang.StackOverflowError will be thrown!*/
		for(int index=0,length=this.capacity;index<length;index++){
			this.units[index]=new CacheBlock(blockCapacity);
		} 
	} 
	/**
	 * @return Returns the units.
	 */
	public StoreUnit[] getUnits() {
		return units;
	}
	/**
	 * @param units The units to set.
	 */
	public void setUnits(CacheBlock[] units) {
		this.units = units;
	}
	/**
	 * @see Storage
	 */
	public void export(Storage destination){
		/*
		int exportIndex=this.getExportIndex();
		StoreUnit unit=this.get(exportIndex);
		destination.importElements(unit);
		this.setUpdateIndex(exportIndex);
		this.setExportIndex(++exportIndex);
		if(this.exportIndex==this.capacity){
			this.exportIndex=0;
		}
		unit.reset();
		this.size--;
		*/
		StoreUnit unit=null;
		for(int index=0;index<this.capacity;index++){
			unit=this.get(index);
			if(destination.haveSpace()){
				destination.importElements(unit);
			}
			unit.reset();
		}
		this.size=0;
	}
	/**
	 * add unit
	 * <p>
	 * first,judge if have more space to add unit and throw RuntimeException if no more space;
	 * second,set unit and increase the size;
	 * third,judge if have more space and increase updateIndex if no more space.
	 * </p>
	 */
	public void add(StoreUnit unit){
		if(!haveSpace()){
			throw new RuntimeException("cache has been full,please export data to memory!");
		}
		if(unit instanceof CacheBlock){
			super.add(unit);
		}
	}
	public void add(long element){
		if(!haveSpace()){
			throw new RuntimeException("cache has no space,please export data to memory!");
		}
		StoreUnit block=get(this.updateIndex);
		block.add(element); 
		if(!block.haveSpace()){
			this.updateIndex++;
			if(this.size<this.capacity){
				this.size++;
			}
			if(this.updateIndex==this.capacity){
				this.updateIndex=this.exportIndex;
			}
		}
	}
	/**
	 * judge if there is more space to add unit;
	 */
	public boolean haveSpace(){
		return this.size<this.capacity;
	}
	/**
	 * @see Storage
	 */
	public int size(){
		return this.size;
	}
	/**
	 * @see Storage
	 */
	public int capacity(){
		return this.capacity;
	}
	public StoreUnit get(int index){
		return this.units[index];
	}
}
