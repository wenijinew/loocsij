package org.loocsij.storage;
/**
 * <p>
 * StoreUnitImpl.java
 * </p>
 * @author wengm
 *
 */
public class StoreUnitImpl implements StoreUnit{
	protected long[] elements;
	protected int size; 
	protected int capacity;
	public StoreUnitImpl() {
		super();
		this.elements=new long[this.capacity];
	}
	public StoreUnitImpl(int capacity){
		super();
		this.capacity=capacity;
		this.elements=new long[this.capacity];
	}
	/** 
	 * @return Returns the elements.
	 */
	public long[] getElements() {
		return elements;
	}
	/**
	 * @param elements The elements to set.
	 */
	public void setElements(long[] elements) {
		this.elements = elements;
	}
	/**
	 * @param capacity The capacity to set.
	 */
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	public int size() {
		return size;
	}
	/**
	 * @return Returns the capacity.
	 */
	public int capacity() {
		return capacity;
	}
	/**
	 * sort the elements using quick sort arithmetic
	 * @param elements
	 */
	public void sort(){		
		this.elements=QuickSort.quickSort(elements,0,elements.length);		
	} 
	/**
	 * add element by insert sort algorithm().
	 * @param ip
	 * @return
	 */
	public void add(long ip){	
		if(!this.haveSpace()){
			throw new RuntimeException("no space,please export data!");
		}
		boolean addResult = insert(this.elements,this.size,ip);
		if(addResult){
			this.size++;
		}
	}
	/**
	 * insert specified element into specified array which have count
	 * @param la
	 * @param count
	 * @param element
	 * @return
	 */
	public static boolean insert(long[] la,int count,long element){
		if(la==null || la.length==0){
			return false;
		}
		if(count==0){
			la[0]=element;
			return true;
		}
		int length=la.length;
		if(count>=length){
			return false;
		}
		int index=(count-1);
		while(index>=0 && la[index]>=element){
			la[index+1]=la[index];
			la[index]=element;
			index--;
		}
		la[index+1]=element;
		return true;
	}
	/**
	 * @see StoreUnit
	 */
	public boolean contain(long ip){
		return HalfFind.find(this.elements,ip,0,this.size);
	}
	public void importElements(StoreUnit unit){	}
	public boolean haveSpace(){
		return this.size<this.capacity;
	}
	public long get(int index){
		return this.elements[index];
	}
	public void set(int index,long element){
		this.elements[index]=element;
	}
	public void reset(){
		for(int index=0;index<this.size;index++){
			this.elements[index]=0L;
		}
		this.size--;
	}
}
