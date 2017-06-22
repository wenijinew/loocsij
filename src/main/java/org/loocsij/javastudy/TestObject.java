package org.loocsij.javastudy;

import java.io.InputStream;
import java.io.OutputStream;

interface TestInheritence {
	String str = "How are you, my friend?";

	void d(Object obj);
}

class TestParentObject implements TestInheritence {
	public TestParentObject() {
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	}

	public void d(Object obj) {
		System.out.println(obj);
	}
}

class TestObject extends TestParentObject {

	public TestObject() {
		d(TestObject.class.getName());
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TestObject tobj = new TestObject();
		Object[] str = { "" };
		tobj.d(str.getClass().getName());
		int[] is = { 1 };
		tobj.d(is.getClass().getName());
		tobj.d("1:" + (new TestObject() instanceof Object));
		tobj.d(TestInheritence.str);
	}

	public void destroy() {
		// TODO Auto-generated method stub
	}

	public int exitValue() {
		// TODO Auto-generated method stub
		return 0;
	}

	public InputStream getErrorStream() {
		// TODO Auto-generated method stub
		return null;
	}

	public InputStream getInputStream() {
		// TODO Auto-generated method stub
		return null;
	}

	public OutputStream getOutputStream() {
		// TODO Auto-generated method stub
		return null;
	}

	public int waitFor() throws InterruptedException {
		// TODO Auto-generated method stub
		return 0;
	}

}
