/**
 * 
 */
package org.loocsij.javastudy;

/**
 * @author dev
 * 
 */
public class TestConversion extends TestParentObject {

	/**
	 * 
	 */
	public TestConversion() {
	}
	
	public void test1(){
		float f = 1.345454f;
		int i = (int) f;
		double d = f;
		double dd = Math.sin(f);
		f = (float)dd;
		d("f-i=" + (f - i));
		d("f>i=" + (f > i));
		d("d-f=" + (d - f));
		d("d>f=" + (d > f));
		d("sin(f)="+dd);
		d("(float)dd="+f);
	}
	
	public void test2(){
		int i = 1234567890;
		float f = i;
		d("f="+f);
		d("i-f="+(i-(int)f));
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TestConversion tc = new TestConversion();
		tc.test1();
		tc.test2();
	}

}
