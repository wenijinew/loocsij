/**
 * 
 */
package org.loocsij.web;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author wsx
 * 
 */
public class WebPageTool {

	/**
	 * 
	 */
	public WebPageTool() {
		// TODO Auto-generated constructor stub
	}

	public static void generate(String uri, String path, String fileName)
			throws IOException {

		URL url = new URL(uri);
		String filePath = path + "//" + fileName;

		File file = new File(filePath);

		String parentPath = file.getParent();
		File parent = new File(parentPath);

		if (!parent.exists()) {
			parent.mkdirs();
		}

		FileOutputStream writer = new FileOutputStream(file);

		DataInputStream reader = new DataInputStream(url.openStream());

		byte[] bs = new byte[1024];

		while (reader.read(bs) > 0) {
			writer.write(bs);
		}
	}

	public static String getResourceContent(String uri) throws IOException{
		URL url = new URL(uri);
		
		StringBuffer buf = new StringBuffer();

		DataInputStream reader = new DataInputStream(url.openStream());
		
		byte[] byteBuf = new byte[1024];

		while (reader.read(byteBuf) > 0) {
			buf.append(new String(byteBuf));
		}
		
		return buf.toString();
	} 

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

        String uri = getUri("Bruce","Wen");

		//String uri = "http://gp.internal.ericsson.com/?x500uid=EGUGWEN";
        System.out.println(uri);
		String s = getResourceContent(uri);
		System.out.println(s);
		/*int index = s.indexOf("BNEP DNEP");
		String sub = s.substring(index);
		index = sub.indexOf("\"");
		System.out.println(sub.substring(0,index));*/
	}

	private static String getUri(String fName, String lName){
	    return "http://internal.ericsson.com/wps/portal/yasper/pf?action=search&srchSN="+fName+"+"+lName+"&short=shortSearch&srchMOD=approx";
    }
}
