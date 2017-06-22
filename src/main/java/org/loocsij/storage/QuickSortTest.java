package org.loocsij.storage;
import java.util.Date;
import java.util.Random;
public class QuickSortTest {
	public QuickSortTest() {
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
			System.out.println("pivotIndex="+pivotIndex);
			//if(true){return la;}
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
			for (int i = 0, l = la.length; i < l; i++) {
				System.out.print("["+ i + "]" + la[i]);
				System.out.print("	");
			}
			System.out.println();
			System.out.println("first="+first+"	n="+n+"	tooBigIndex="+tooBigIndex+"	tooSmallIndex="+tooSmallIndex);
			/**
			try{
				Thread.sleep(2000);
			}catch(InterruptedException ie){
			}*/
		}
		swap(la,first,tooSmallIndex);
		return tooSmallIndex;
	}
	private static void swap(long[] la, int i1, int i2) {
		long temp;
		temp = la[i1];
		la[i1] = la[i2];
		la[i2] = temp;
	} 
	public static void main(String[] strs) {
		QuickSortTest sb = new QuickSortTest();
//		long[] la={254147,3};
		long[] la=sb.get();
		int first = 0; 
		int n = la.length ;
		//long start = System.currentTimeMillis();
		System.out.println("before sort:");
		for (int i = 0, l = la.length; i < l; i++) {
			System.out.print("["+ i + "]" + la[i]);
			System.out.print("	");
		}
		System.out.println();
		System.out.println("sortting:");
		long lac[] = quickSort(la, first, n);
		System.out.println("after sort:");
		for (int i = 0, l = lac.length; i < l; i++) {
			System.out.println("["+ i + "]" + lac[i]);
			//System.out.print("	");
		}
		// System.out.println(System.currentTimeMillis()-start);
	}
	public long[] get(){
		Random r = new Random(new Date().getTime());
		long la[] = new long[10];
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
