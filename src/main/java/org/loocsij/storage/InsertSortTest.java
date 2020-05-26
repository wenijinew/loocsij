package org.loocsij.storage;
public class InsertSortTest extends Sort{
	public InsertSortTest() {
		super();
	}
	public static long[] insertSort(long[] la,int count,long element){
		if(la==null){
			return la;
		}
		int length=la.length;
		if(count>=length){
			return la;
		}
		int index=(count-1);
		for(;index>=0;index--){
			if(la[index]>element){
				la[index+1]=la[index];
				break;
			}
		}
		la[index]=element;
		return la;
	}
	public static void main(String[] strs){
		long[] la=new long[5];
		long[] lac=null;
		la[0]=1;
		la[1]=3;
		lac=insertSort(la,2,2);
		for (int i = 0, l = lac.length; i < l; i++) {
			System.out.println("["+ i + "]" + lac[i]);
			//System.out.print("	");
		}
	}
}
