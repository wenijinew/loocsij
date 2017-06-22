package org.loocsij.storage;
import java.io.File;
import java.io.IOException;
public class DiskBlock extends MemoryBlock{
	private int exportIndex;
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
		this.exportIndex = exportIndex;
	}
	public DiskBlock() {
		super();
	}
	public DiskBlock(int capacity){
		super(capacity);
	}
	/**
	 * <p> 
	 * replace elements using block's from replaceIndex to replaceIndex adding block's capacity.
	 * 
	 * </p>
	 * @param block
	 */
	public void importElements(MemoryBlock block){
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
	public void export(File file,long element) throws IOException{
		FileExport out=new FileExport(file);
		out.write(element);
		if(this.size>0){
			this.size--;
		}
	}
}
