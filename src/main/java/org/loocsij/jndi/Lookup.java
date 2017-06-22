/**
 * 
 */
package org.loocsij.jndi;

import java.io.File;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;

/**
 * @author wengm
 *
 */
public class Lookup {

	/**
	 * 
	 */
	public Lookup() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Hashtable env = new Hashtable();
		env.put(Context.INITIAL_CONTEXT_FACTORY, 
		    "com.sun.jndi.fscontext.RefFSContextFactory");
		env.put(Context.PROVIDER_URL, "file:\\eclipse\\workspace\\ij");
		Context ctx = null; 
		Context obj = null;
		try {
			ctx = new InitialContext(env);
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String name = "";
		try {
			obj = (Context)ctx.lookup(name);
			showSubs(obj);
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void showSubs(Context ctx) throws NamingException {
		System.out.println(ctx.getNameInNamespace());
		NamingEnumeration ne = ctx.list("");
		while(ne.hasMoreElements()) {
			Object o = ne.next();
			String str = o.toString();
			String[] strs = str.split(":");
			if(str.endsWith("javax.naming.Context")) {
				Context ctxsub = (Context) ctx.lookup(strs[0]);
//				Context ctxsubobj = (Context)ctx.lookup("java:comp/env/Cmd");
				showSubs(ctxsub);
			}else if(str.endsWith("java.io.File")){
				File f = (File)ctx.lookup(strs[0]);
				System.out.println(f.getAbsoluteFile());
			}
		}
	}
}
