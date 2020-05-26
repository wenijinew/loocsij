package org.loocsij.util;

public class SortUtil {

	/**
	 * swap location of given element in array
	 * 
	 * @param la -
	 *            long array
	 * @param i1 -
	 * @param i2
	 */
	public static void swap(Object[] objs, int i1, int i2) {
		Object temp = objs[i1];
		objs[i1] = objs[i2];
		objs[i2] = temp;
	}

	/**
	 * insert sort arithmetic implementation
	 * 
	 * @param la
	 * @param targetIndex
	 * @param element
	 * @return long[] - sorted long array
	 */
	public static long[] insert(long[] la, int targetIndex, long element) {
		if (la == null) {
			return la;
		}
		int length = la.length;
		if (targetIndex >= length) {
			return la;
		}
		int idx = (targetIndex - 1);
		for (; idx >= 0; idx--) {
			if (la[idx] > element) {
				la[idx + 1] = la[idx];
				break;
			}
		}
		la[idx] = element;
		return la;
	}

	/**
	 * Insertion sort is a simple sorting algorithm, a comparison sort in which
	 * the sorted array (or list) is built one entry at a time. It is much less
	 * efficient on large lists than the more advanced algorithms such as
	 * quicksort, heapsort, or merge sort, but it has various advantages:
	 * 
	 * Simple to implement Efficient on (quite) small data sets Efficient on
	 * data sets which are already substantially sorted More efficient in
	 * practice than most other simple O(n2) algorithms such as selection sort
	 * or bubble sort: the average time is n2/4 and it is linear in the best
	 * case Stable (does not change the relative order of elements with equal
	 * keys) In-place (only requires a constant amount O(1) of extra memory
	 * space) It is an online algorithm, in that it can sort a list as it
	 * receives it. In abstract terms, each iteration of an insertion sort
	 * removes an element from the input data, inserting it at the correct
	 * position in the already sorted list, until no elements are left in the
	 * input. The choice of which element to remove from the input is arbitrary
	 * and can be made using almost any choice algorithm.
	 * 
	 * @param cs
	 *            an array of Comparable items.
	 */
	public static Comparable[] insertionSort(Comparable[] cs) {
		if (cs == null) {
			return null;
		}
		int l = cs.length;
		if (l <= 1) {
			return cs;
		}
		for (int p = 1; p < l; p++) {
			Comparable tmp = cs[p];
			int j = p;

			for (; j > 0 && tmp.compareTo(cs[j - 1]) < 0; j--)
				cs[j] = cs[j - 1];
			cs[j] = tmp;
		}
		return cs;
	}

	public static double[] insertionSort(double[] ds) {
		Comparable[] cs = insertionSort(NumberUtil.convert(ds));
		return NumberUtil.doubleArray(convert(cs));
	}

	public static long[] insertionSort(long[] ls) {
		Comparable[] cs = insertionSort(NumberUtil.convert(ls));
		return NumberUtil.longArray(convert(cs));
	}

	public static float[] insertionSort(float[] fs) {
		Comparable[] cs = insertionSort(NumberUtil.convert(fs));
		return NumberUtil.floatArray(convert(cs));
	}

	public static int[] insertionSort(int[] is) {
		Comparable[] cs = insertionSort(NumberUtil.convert(is));
		return NumberUtil.intArray(convert(cs));
	}

	public static short[] insertionSort(short[] ss) {
		Comparable[] cs = insertionSort(NumberUtil.convert(ss));
		return NumberUtil.shortArray(convert(cs));
	}

	public static byte[] insertionSort(byte[] bs) {
		Comparable[] cs = insertionSort(NumberUtil.convert(bs));
		return NumberUtil.byteArray(convert(cs));
	}

	private static NumberUtil[] convert(Comparable[] cs) {
		NumberUtil[] ns = new NumberUtil[cs.length];
		for (int i = 0; i < cs.length; i++) {
			ns[i] = (NumberUtil) cs[i];
		}
		return ns;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		  
	}
}
