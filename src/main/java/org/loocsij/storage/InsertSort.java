package org.loocsij.storage;
public class InsertSort extends Sort{
	public InsertSort() {
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
}
 