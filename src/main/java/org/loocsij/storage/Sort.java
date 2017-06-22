package org.loocsij.storage;
public class Sort {
	public Sort() {
		super();
		// TODO Auto-generated constructor stub
	}
	protected static void swap(long[] la, int i1, int i2) {
		long temp;
		temp = la[i1];
		la[i1] = la[i2];
		la[i2] = temp;
	} 
}
 