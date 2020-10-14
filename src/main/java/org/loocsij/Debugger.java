package org.loocsij;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Debugger {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws ParseException {
		Date date = new Date(190528150509L);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d1970 = format.parse("1970-01-01 00:00:00");

		System.out.println(d1970.getTime());
		System.out.println(format.format(date));
	}

	private static void output(Object[] objs){
		for(int i=0;i<objs.length;i++){
			System.out.println(objs[i].toString());
		}
	}
}
