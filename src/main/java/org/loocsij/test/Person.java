package org.loocsij.test;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Person {
	protected Date birthdate;

	public int getAge() {
		if (birthdate == null)
			return 0;
		else {
			int yearToday = Calendar.getInstance().get(Calendar.YEAR);

			Calendar calendar = new GregorianCalendar();
			calendar.setTime(birthdate);
			int birthYear = calendar.get(Calendar.YEAR);

			return yearToday - birthYear;
		}
	}

	public void setBirthDate(Date aBirthDate) {
		this.birthdate = aBirthDate;
	}
}
