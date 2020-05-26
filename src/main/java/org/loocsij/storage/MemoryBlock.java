package org.loocsij.storage;
public class MemoryBlock extends StoreUnitImpl{
	private int replaceIndex;
	/**
	 * @return Returns the replaceIndex.
	 */
	public int getReplaceIndex() {
		return replaceIndex;
	}
	/**
	 * @param replaceIndex The replaceIndex to set.
	 */
	public void setReplaceIndex(int replaceIndex) {
		this.replaceIndex = replaceIndex;
	}
	public MemoryBlock() {
		super();
	}
	public MemoryBlock(int capacity){
		super(capacity);
	}
	/**
	 * <p>
	 * replace elements using block's from replaceIndex to replaceIndex adding block's capacity.
	 *  
	 * </p>
	 * @param block 
	 */
	public void importElements(CacheBlock block){
		int start=this.getReplaceIndex();
		int count=block.capacity();
		int end=start+count;
		if(end>this.capacity){
			end=this.capacity;			
		}
		this.size=end;
		long[] elements=this.getElements();
		long[] replaceElements=block.getElements();
		for(int index=start;index<end;index++){
			elements[index]=replaceElements[index-start];
		}
		this.setReplaceIndex(end);
	}
	public void reset(){
		for(int index=0;index<this.size;index++){
			this.elements[index]=0L;
		}
		this.size--;
		this.replaceIndex=0;
	}
}
