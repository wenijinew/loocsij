package org.loocsij.storage;
public class HalfFind {
	public HalfFind() {
		super();
	}
	/**
	 * half search arithmetic
	 * @param la
	 * @return boolean
	 */
	public static boolean find(long[] la,long target,int first,int size){
		if(size <=0){
			return false;
		}else{
			int middle=first+size/2;
			if(target==la[middle]){
				return true;
			}else if(target<la[middle]){
				return find(la,target,first,size/2);
			}else{
				return find(la,target,middle+1,(size-1)/2);
			}
		}
	} 
	/**
	 * 
	 * @param la
	 * @param l
	 * @param s0
	 * @param e0
	 * @return
	 */
	public static int findPosition(long[] la,long toBeFound,int start,int end){
		int s=start;
		int e=end;
		int m=(s+e)/2;
		if(toBeFound==la[m]){
			return m;
		}
		while(s!=e){
			if(toBeFound<la[m]){
				return findPosition(la,toBeFound,s,m);
			}else{
				return findPosition(la,toBeFound,m,e);
			}
		}
		return m;
	}
	public static void main(String[] strs){
		int count=0;
		long[] la=new long[1000000];
		while(count<la.length){
			la[count]=count++;
		}
		long[] ia={15,35,86,33,67,27,77,23,41,14,36,81,39,10,28,2,94,98,99,34,97,61,66,76,8,85,53,6,44,58,63,44,37,75,95,1,78,24,47,25,32,93,90,56,21,84,71,4,49,22,66,40,57,91,16,16,43,64,9,20,74,88,69,43};
		long start=System.currentTimeMillis();
		boolean in=false;
		ia=QuickSort.quickSort(ia,0,ia.length);
		for(int index=0;index<ia.length;index++){
			System.out.println(ia[index]);
		}
		int c=0;
		while(c<ia.length){
			in=find(la,ia[c],0,la.length);
			if(in){
				System.out.print(ia[c]+"	");
			}
			c++;
		}
		find(la,100,0,la.length);
		System.out.println(System.currentTimeMillis()-start);
	}
}
