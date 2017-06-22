package org.loocsij.storage;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
public class FileExport {
	private File file;
	public FileExport(File file) {
		super();
		if(file==null){
			throw new RuntimeException("Usage:NewLineWriter(File file),file can not be NULL!");
		}
		this.file=file;
	}
	public void write(String content) throws IOException{
		RandomAccessFile writer=new RandomAccessFile(file,"rws");
		byte[] bts=content.getBytes();
		/*
		 * specify the file pointer
		 * the parameter is not "file.length()" but "file.length-2"
		 * this purpose is make no "linear white space"
		 */
		writer.seek(file.length());
		for(int i=0,l=bts.length;i<l;i++){
			writer.writeByte(bts[i]);
		}
		/*
		 * first:Carriage Return
		 * second:line feed 
		 */
		writer.writeByte(13);
		writer.write(10);
		writer.close();
	}
	public void write(long content) throws IOException{
		String c=new Long(content).toString();
		write(c);
	}
}
