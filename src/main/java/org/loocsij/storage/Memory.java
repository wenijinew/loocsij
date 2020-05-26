package org.loocsij.storage;
public class Memory extends StorageImpl{
	private MemoryBlock[] units;
	private int capacity = 8;
	public Memory() {
		super();
		this.units = new MemoryBlock[capacity];
		for(int index=0,length=this.capacity;index<length;index++){
			this.units[index]=new MemoryBlock();
		}
	}
	public Memory(int capacity,int blockCapacity){
		super();
		this.capacity=capacity;
		this.units = new MemoryBlock[capacity];
		/*!!Note:if no this operation,java.lang.StackOverflowError will be thrown!*/
		for(int index=0,length=this.capacity;index<length;index++){
			this.units[index]=new MemoryBlock(blockCapacity);
		}
	}
	/**
	 * @return Returns the memoryBlocks.
	 */
	public StoreUnit[] getUnits() {
		return units;
	}
	/**
	 * @param memoryBlocks The memoryBlocks to set.
	 */
	public void setUnits(MemoryBlock[] memoryBlocks) {
		this.units = memoryBlocks;
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
			throw new RuntimeException("memory has been full,please export data to disk!");
		}
		CacheBlock block=(CacheBlock)unit;
		MemoryBlock memoryBlock=(MemoryBlock)this.get(this.updateIndex);
		long[] elements=memoryBlock.getElements();
		for(int index=memoryBlock.size();index<this.size+block.capacity();index++){
			elements[index]=block.get(index);
		}
		if(!memoryBlock.haveSpace()){
			if(this.size<this.capacity-1){
				this.size++;
			}
			if(this.updateIndex<this.capacity-1){
				this.updateIndex++;
			}
		}
	}
	public void importElements(StoreUnit unit){
		if(!this.haveSpace()){
			throw new RuntimeException("memory has no space,please export data to disk!");
		}
		MemoryBlock memoryBlock=(MemoryBlock)this.get(this.getUpdateIndex());
		memoryBlock.importElements((CacheBlock)unit);
		/**/
		if(!memoryBlock.haveSpace()){
			memoryBlock.sort();
			if(this.size<this.capacity){
				this.size++;
			}
			this.updateIndex++;
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
		return this.getUnits()[index];
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
		this.size--;
		*/
		StoreUnit unit=null;
		for(int index=0;index<this.size;index++){
			unit=this.get(index);
			if(destination.haveSpace()){
				destination.importElements(unit);
			}
			unit.reset();
		}
		this.size=0;
	}
}
