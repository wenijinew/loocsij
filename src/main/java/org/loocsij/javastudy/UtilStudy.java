package org.loocsij.javastudy;

import java.text.ChoiceFormat;
import java.text.CollationElementIterator;
import java.text.Collator;
import java.text.ParsePosition;
import java.text.RuleBasedCollator;
import java.util.Arrays;
import java.util.TimeZone;

public class UtilStudy {
	public static void showAllAvailableTimeZoneIDs(){
		String[] ids = TimeZone.getAvailableIDs();
		for(int i=0;i<ids.length;i++){
			System.out.println(ids[i]);
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		showAllAvailableTimeZoneIDs();
//		String[] strs = {"a", "b", "ac"};
//		Arrays.sort(strs);
//		for(int i=0;i<strs.length;i++){
//			System.out.println(strs[i]);
//		}
//		
//		double[] limits = { 1, 2, 3, 4, 5, 6, 7 };
//		String[] monthNames = { "Sun", "Mon", "Tue", "Wed", "Thur", "Fri",
//				"Sat" };
//		ChoiceFormat form = new ChoiceFormat(limits, monthNames);
//		ParsePosition status = new ParsePosition(0);
//		for (double i = 0.0; i <= 8.0; ++i) {
//			status.setIndex(0);
//			System.out.println(i + " -> " + form.format(i) + " -> "
//					+ form.parse(form.format(i), status));
//		}

		String testString = "This is a test";
		RuleBasedCollator ruleBasedCollator = (RuleBasedCollator) Collator
				.getInstance();
		CollationElementIterator collationElementIterator = ruleBasedCollator
				.getCollationElementIterator(testString);
		int primaryOrder = CollationElementIterator.primaryOrder(collationElementIterator.next());
		System.out.println(primaryOrder);
		
		RuleBasedCollator c = (RuleBasedCollator)Collator.getInstance();
	    CollationElementIterator iter = c.getCollationElementIterator("Foo");

	    int element;
	    while ((element = iter.next()) != CollationElementIterator.NULLORDER) {
		System.out.println("Collation element is: " + 
	        	              Integer.toString(element,16) );
		System.out.println(" primary:   " + Integer.toString(
                CollationElementIterator.primaryOrder(element), 16) );
        System.out.println(" secondary: " + Integer.toString(
                CollationElementIterator.secondaryOrder(element), 16) );
        System.out.println(" tertiary:  " + Integer.toString(
                CollationElementIterator.tertiaryOrder(element), 16) );

	    }

	}

}
