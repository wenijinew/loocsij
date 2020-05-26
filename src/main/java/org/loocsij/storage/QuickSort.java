package org.loocsij.storage;
/**
 * quick sort algorithm
 * @author wengm
 *
 */
public class QuickSort  extends Sort{
	public QuickSort() {
	}
	/**
	 * sort elements in the specified array by ascending
	 * 
	 * @param la
	 *            long[]
	 * @return long[]
	 */
	public static long[] quickSort(long[] la, int first, int n) {
		int pivotIndex;
		int n1;
		int n2;
		if(n > 1){
			//divide the array and get the pivotIndex
			pivotIndex = partition(la,first,n);
			//calculate the size of two division array
			n1 = pivotIndex - first;
			n2 = n - n1 -1;
			//recursion invoke the quick sort function
			quickSort(la,first,n1);
			quickSort(la,pivotIndex+1,n2);
		}
		return la;
	}
	/** 
	 * get the pivot index()
	 * @param la
	 * @param first
	 * @param n
	 */
	private static int partition(long[] la,int first,int n){
		long pivot=la[first];
		int tooBigIndex=first+1;
		int tooSmallIndex=first + n -1;
		int start=first;
		int end=start+n;
		while(tooBigIndex<=tooSmallIndex){
			if(tooBigIndex<end && la[tooBigIndex]<=pivot){
				tooBigIndex++;
			}
			if(tooSmallIndex>start && la[tooSmallIndex]>pivot){
				tooSmallIndex--;
			}
			if(tooBigIndex<tooSmallIndex){
				if(la[tooBigIndex]>pivot && la[tooSmallIndex]<=pivot){
					swap(la,tooBigIndex,tooSmallIndex);
				}
			}
		}
		swap(la,first,tooSmallIndex);
		return tooSmallIndex;
	}
}
