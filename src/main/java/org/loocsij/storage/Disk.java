package org.loocsij.storage;
import java.io.File;
import java.io.IOException;
public class Disk extends StorageImpl{
	private DiskBlock[] units;
	private int capacity = 16;
	public Disk() {
		super();
		this.units = new DiskBlock[capacity];
		for(int index=0,length=this.capacity;index<length;index++){
			this.units[index]=new DiskBlock();
		}
	}
	public Disk(int capacity,int blockCapacity){
		super();
		this.capacity=capacity;
		this.units = new DiskBlock[capacity];
		/*!!Note:if no this operation,java.lang.StackOverflowError will be thrown!*/
		for(int index=0,length=this.capacity;index<length;index++){
			this.units[index]=new DiskBlock(blockCapacity);
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
	public void setUnits(DiskBlock[] units) {
		this.units = units;
	}
	public void importElements(StoreUnit unit){
		if(!this.haveSpace()){
			throw new RuntimeException("disk has no space,please export data to file!");
		}
		DiskBlock diskBlock=(DiskBlock)this.get(this.getUpdateIndex());
		diskBlock.importElements((MemoryBlock)unit);
		/**/
		if(!diskBlock.haveSpace()){
			diskBlock.sort();
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
	 * 
	 * 
	public void export(File file) throws IOException{
		StoreUnit block=this.get(this.exportIndex);
		FileExport out=new FileExport(file);
		for(int index=0;index<block.size()-1;index++){
			out.write(block.get(index));
		}
		block.reset();
		if(this.exportIndex<this.capacity-1){
			this.exportIndex++;
		}
		this.size--;
	}
	*/
	/**
	 * 
	 */
	public void export(File file) throws IOException{
		int buffer=5;
		DiskBlock block=(DiskBlock)this.get(this.exportIndex);
		int end=block.size()-1;
		int start=end-buffer;
		int index=end;
		if(start<0){
			start=0;
		}
		if(end>=0){
			for(;index>start;index--){
				block.export(file,block.get(index));
			}
		}else{
			block.reset();
			if(this.exportIndex<this.capacity-1){
				this.exportIndex++;
			}else{
				this.exportIndex=0;
			}
			this.size--;
		}
	}
	/**
	 * 
	 */
	public void reset(){
		DiskBlock block=(DiskBlock)this.get(this.exportIndex);
		block.reset();
		if(this.exportIndex<this.capacity-1){
			this.exportIndex++;
		}else{
			this.exportIndex=0;
		}
		this.size--;
	} 
	/**
	 * 
	 * 
	public void export(File file) throws IOException{
		int buffer=10;
		DiskBlock block=(DiskBlock)this.get(this.exportIndex);
		int end=block.size();
		int start=end-buffer;
		int index=start;
		if(index<0){
			index=0;
		}
		if(end>=0){
			for(;index<end;index++){
				block.export(file,block.get(index));
			}
		}else{
			block.reset();
			if(this.exportIndex<this.capacity-1){
				this.exportIndex++;
			}
			this.size--;
		}
	}
	*/
	/**
	 * 
	 *
	public void export(File file) throws IOException{
		DiskBlock block=(DiskBlock)this.get(this.exportIndex);
		int index=block.size()-1;
		if(index>=0){
			block.export(file,block.get(index));
		}else{
			block.reset();
			if(this.exportIndex<this.capacity-1){
				this.exportIndex++;
			}
			this.size--;
		}
	}
	*/
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
}
