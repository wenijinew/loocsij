package org.loocsij.storage;
/**
 * 
 * @author wengm
 *
 */
public class StorageImpl implements Storage{
	private StoreUnit[] units;
	protected int updateIndex;
	protected int exportIndex;
	protected int size;
	private int capacity;
	public StorageImpl(){
		super();
		this.units = new StoreUnitImpl[capacity];
		for(int index=0,length=this.capacity;index<length;index++){
			this.units[index]=new StoreUnitImpl();
		}
	}
	public StorageImpl(int capacity,int storeUnitCapacity){
		super();
		this.capacity=capacity;
		this.units = new StoreUnitImpl[capacity];
		/*!!Note:if no this operation,java.lang.StackOverflowError will be thrown!*/
		for(int index=0,length=this.capacity;index<length;index++){
			this.units[index]=new StoreUnitImpl(storeUnitCapacity);
		}
	} 
	/** 
	 * @return Returns the exportIndex.
	 */
	public int getExportIndex() {
		return exportIndex;
	}
	/**
	 * @param exportIndex The exportIndex to set.
	 */
	public void setExportIndex(int exportIndex) {
		if(exportIndex==this.capacity){
			exportIndex=0;
		}
		this.exportIndex = exportIndex;
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
	public void setUnits(StoreUnit[] units) {
		this.units = units;
	}
	/**
	 * @return Returns the updateIndex.
	 */
	public int getUpdateIndex() {
		return updateIndex;
	}
	/**
	 * @param updateIndex The updateIndex to set.
	 */
	public void setUpdateIndex(int updateIndex) {
		this.updateIndex = updateIndex;
	}
	public void importElements(StoreUnit block){}
	/**
	 * @see Storage
	 */
	public int findPosition(long element){
		boolean isContain=false;
		StoreUnit unit=null;
		int index=0;
		if(this.size==0 && this.get(0).size()>0){
			isContain=this.get(0).contain(element);
			return isContain?0:-1;
		}
		while(index<this.size && !isContain){
			unit=this.get(index);
			isContain=unit.contain(element);
			index++;
		}
		if(isContain){
			return index;
		}
		return -1;
	}
	/**
	 * @see Storage
	 */
	public boolean contain(long element){
		return (findPosition(element)>0);
	}
	/**
	 * @see Storage
	 */
	public void export(Storage destination){
		int exportIndex=this.getExportIndex();
		StoreUnit unit=this.get(exportIndex);
		destination.importElements(unit);
		this.setUpdateIndex(exportIndex);
		this.setExportIndex(++exportIndex);
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
	/**
	 * @see Storage
	 */
	public StoreUnit get(int index){
		return this.units[index];
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
		this.units[this.size]=unit;
		if(this.size<this.capacity){
			this.size++;
		}
		if(!unit.haveSpace()){
			this.updateIndex++;
			if(this.updateIndex==this.capacity){
				this.updateIndex=0;
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
	 * add element to store unit
	 * @param element
	 */
	public void add(long element){
		if(!haveSpace()){
			throw new RuntimeException("no space,please export data!");
		}
		StoreUnit block=get(this.updateIndex);
		System.out.println("updateIndex:"+this.updateIndex+"capacity:"+this.capacity);
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
	 * reset container
	 */
	public void reset(){
		if(size==0){
			return;
		}
		for(int index=0;index<size;index++){
			units[index].reset();
		}
	}
}
