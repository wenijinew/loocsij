/**
 * 
 */
package org.loocsij.test;

import java.util.GregorianCalendar;

import junit.framework.TestCase;

/**
 * @author wengm
 * 
 */
public class TC_Person extends TestCase {
	protected Person person;

	/**
	 * 
	 */
	public TC_Person() {
	}

	/**
	 * @param arg0
	 */
	public TC_Person(String arg0) {
		super(arg0);
	}

	protected void setUp() throws Exception {
		person = new Person();
	}

	public void testGetDefaultAge() {
		int actual = person.getAge();
		assertEquals(0, actual);
	}

	public void testGetAge() {
		GregorianCalendar calendar = new GregorianCalendar(1971, 3, 23);
		person.setBirthDate(calendar.getTime());
		int actual = person.getAge();
		assertEquals(38, actual);

	}

	protected void tearDown() throws Exception {
	}

}
