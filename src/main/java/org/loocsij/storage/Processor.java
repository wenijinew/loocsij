package org.loocsij.storage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.StringTokenizer;
/**
 * 
 * @author wengm
 *
 */
public class Processor {
	public static final int CACHE=0;
	public static final int MEMORY=1;
	public static final int DISK=2;
	private CapacityInitor initor;
	private static Cache cache;
	private static Memory memory;
	private static Disk disk;
	public Processor(CapacityInitor initor){
		if(!initor.isInit()){
			throw new RuntimeException("Invalid initor!");
		}
		this.initor=initor;
		init(initor);
	}
	private void init(CapacityInitor initor){
		cache = new Cache(initor.getCacheCapacity(),initor.getBlockCapacity());
		memory = new Memory(initor.getMemoryCapacity(),initor.getMemoryBlockCapacity());
		disk = new Disk(initor.getDiskCapacity(),initor.getDiskBlockCapacity());
	} 
	/**
	 * <p>
	 * accept new accessing ip address(ip).
	 * find ip address(ip).
	 * add ip address or replace ip addre ss(ip).
	 * export ip data(ip).
	 * </p>
	 * @param ip String
	 */
	public void process(String ip) {
		long lip=getLongIP(ip);
		FindResult result=contain(lip);
		boolean hasAccessed=result.isAccessed();
		if(!hasAccessed){
			add(lip);
		}
	}
	public void process(long lip) {
		FindResult result=contain(lip);
		boolean hasAccessed=result.isAccessed();
		if(hasAccessed){
			//System.out.println("		has accessed:"+lip+"	container:"+result.getContainer()+"	position:"+result.getUnitIndex());
		}
		if(!hasAccessed){
//			addLip(lip);
			add(lip);
			//System.out.println("new accessed:"+lip+",");
			//System.out.print(lip+",");
		}
	}
	/**
	 * @return Returns the cache.
	 */
	public static Cache getCache() {
		return cache;
	}
	/**
	 * @param cache The cache to set.
	 */
	public static void setCache(Cache cache) {
		Processor.cache = cache;
	}
	/**
	 * @return Returns the disk.
	 */
	public static Disk getDisk() {
		return disk;
	}
	/**
	 * @param disk The disk to set.
	 */
	public static void setDisk(Disk disk) {
		Processor.disk = disk;
	}
	/**
	 * @return Returns the memory.
	 */
	public static Memory getMemory() {
		return memory;
	}
	/**
	 * @param memory The memory to set.
	 */
	public static void setMemory(Memory memory) {
		Processor.memory = memory;
	}
	/**
	 * @return Returns the initor.
	 */
	public CapacityInitor getInitor() {
		return initor;
	}
	/**
	 * @param initor The initor to set.
	 */
	public void setInitor(CapacityInitor initor) {
		this.initor = initor;
	}
	/**
	 * <p>
	 * judge if the storage system contains the specified ip.
	 *  ip
	 * </p> 
	 * @param ip
	 * @return
	 */
	public FindResult contain(long lip){
		FindResult result=new FindResult();
		result.setAccessed(false);
		if(cache.size()==0 && cache.get(0).size()==0){
			return result;
		}
		int unitIndex=-1;
		//cache 
		unitIndex=cache.findPosition(lip);
		if(unitIndex>=0){
			result.setAccessed(true);
			result.setContainer(CACHE);
			result.setUnitIndex(unitIndex);
			return result;
		}
		//memory
		if(memory.size()==0 && memory.get(0).size()==0){
			return result;
		}
		unitIndex=memory.findPosition(lip);
		if(unitIndex>=0){
			result.setAccessed(true);
			result.setContainer(MEMORY);
			result.setUnitIndex(unitIndex);
			return result;
		}
		//disk
		if(disk.size()==0 && disk.get(0).size()==0){
			return result;
		}
		unitIndex=disk.findPosition(lip);
		if(unitIndex>=0){
			result.setAccessed(true);
			result.setContainer(DISK);
			result.setUnitIndex(unitIndex);
			return result;
		}
		return result;
	}
	/**
	 * add specified ip into storage system.
	 * @param lip
	 * @deprecated
	 */
	public void add(long lip){		
		if(cache.haveSpace()){
			//System.out.println("cache processing...cache size:"+cache.size+"	cache capacity:"+cache.capacity());
			cache.add(lip);
		}else if(memory.haveSpace()){
			//System.out.println("memory processing...memory size:"+memory.size+"	memory capacity:"+memory.capacity());
			cache.export(memory);
		}else if(disk.haveSpace()){
			//System.out.println("disk processing...disk size:"+disk.size+"	disk capacity:"+disk.capacity());
			memory.export(disk);
		}else{
			//System.out.println("disk.size="+disk.size+"file system processing...");
			File parent=new File(initor.getExportFilePath());
			File file=null;
			if(!parent.exists()){
				parent.mkdir();
			}
			if(parent.exists()){
				file=new File(parent,logFileName());
			}
			try{
				disk.export(file);
			}catch(IOException ioe){
				System.out.println("some error:"+ioe.toString());
			}
		}
	}
	public void addLip(long lip){		
		if(cache.haveSpace()){
			//System.out.println("cache processing...cache size:"+cache.size+"	cache capacity:"+cache.capacity());
			cache.add(lip);
		}else if(memory.haveSpace()){
			//System.out.println("memory processing...memory size:"+memory.size+"	memory capacity:"+memory.capacity());
			cache.export(memory);
		}else if(disk.haveSpace()){
			//System.out.println("disk processing...disk size:"+disk.size+"	disk capacity:"+disk.capacity());
			memory.export(disk);
		}else{
			disk.reset();
		}
	}
	/**
	 * reset storage system
	 *
	 */
	public void reset(){
		cache.reset();
		memory.reset();
		disk.reset();
	}
	/**
	 * transmit String ip into long ip
	 * @param ip
	 * @return
	 */
	public long getLongIP(String ip){
		StringBuffer bf=new StringBuffer();
		StringTokenizer tool=new StringTokenizer(ip,".");
		while(tool.hasMoreTokens()){
			bf.append(tool.nextToken());
		}
		return Long.parseLong(bf.toString());
	}
	/**
	 * generate the log file name
	 * @return String
	 */
	private String logFileName(){
		StringBuffer name=new StringBuffer("ipLog_");
		String strNow=getStrDate("yyyyMMdd");
		name.append(strNow).append(".log");
		return name.toString();
	}
	/**
	 * get current date's string 
	 * @param pattern
	 * @return String
	 */
	private String getStrDate(String pattern){
		SimpleDateFormat parser=new SimpleDateFormat(pattern);
		return parser.format(new Date());
	}
	/**
	 * test the function of the processor
	 * @param strs
	 */
	public static void main(String[] strs){
		CapacityInitor initor=new CapacityInitor();
		initor.setBlockCapacity(128);
		initor.setCacheCapacity(4);
		initor.setMemoryBlockCapacity(512);
		initor.setMemoryCapacity(4);
		initor.setDiskBlockCapacity(2048);
		initor.setDiskCapacity(32);
		initor.setExportFilePath("e:\\popupLog");
		Processor p=new Processor(initor);
		//String ip="192.168.1.198";
		Random r = new Random(new Date().getTime());
		int count = 0;
		long start=System.currentTimeMillis();
		while (count < 1000*200) {
			//1000*100--59766
			long a = r.nextLong();
			if (a > 0) {
				p.process(a);
				count++;
			}
		}
		System.out.println(System.currentTimeMillis()-start);
		/*
		System.out.println();
		for(int i=0;i<cache.capacity();i++){
			for(int index=0;index<cache.get(i).capacity();index++){
				System.out.print("["+i+"]"+"["+index+"]"+((CacheBlock)cache.get(i)).getElements()[index]+"	");
			}
			System.out.println();
		}
		System.out.println();
		for(int i=0;i<memory.capacity();i++){
			for(int index=0;index<memory.get(i).capacity();index++){
				System.out.print("["+i+"]"+"["+index+"]"+((MemoryBlock)memory.get(i)).getElements()[index]+"	");
			}
			System.out.println();
		}
		System.out.println(); 
		for(int i=0;i<disk.capacity();i++){
			for(int index=0;index<disk.get(i).capacity();index++){
				System.out.print("["+i+"]"+"["+index+"]"+((DiskBlock)disk.get(i)).getElements()[index]+"	");
			}
			System.out.println();
		} 
		System.out.println("count="+count);
		*/
	}
	public long[] get(){
		Random r = new Random(new Date().getTime());
		long la[] = new long[10*10];
		int count = 0;
		while (count < la.length) {
			int a = r.nextInt();
			if (a > 0) {
				la[count] = r.nextInt();
				count++;
			}
		}
		return la;
	}
}
