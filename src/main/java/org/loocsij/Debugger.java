package org.loocsij;

public class Debugger {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String str = "D:\\Project\\iis\\src\\com\\iis\\tags";
		String[] cs = org.loocsij.util.FileUtil.removeComments(str);
		output(cs);
	}

	private static void output(Object[] objs){
		for(int i=0;i<objs.length;i++){
			System.out.println(objs[i].toString());
		}
	}
}
