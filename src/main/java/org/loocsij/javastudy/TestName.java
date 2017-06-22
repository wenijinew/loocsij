/**
 * 
 */
package org.loocsij.javastudy;

interface A{}
/**
 * @author dev
 *
 */
public class TestName {

	int i=110;
	
	class TestInner{
		public void test(){
			System.out.println(i);
			class Inner{
				public void test(){
					System.out.println(i);
				}
			}
			new Inner().test();
		}
	}
	
	/**
	 * 
	 */
	public TestName() {
		// TODO Auto-generated constructor stub
	}
	
	public void test1(){
		int i=0;
		i:for(;i<2;i++){
			System.out.println("A");
			break i;
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TestName tn = new TestName();
		TestInner tin = tn.new TestInner();
		tn.test1();
		tin.test();
	}

}
