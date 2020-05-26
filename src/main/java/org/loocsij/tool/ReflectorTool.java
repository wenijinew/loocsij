/**
 * 
 */
package org.loocsij.tool;

import java.lang.reflect.Method;

import org.apache.logging.log4j.Logger;

import org.loocsij.logger.Log;

/**
 * @author wengm
 *
 */
public class ReflectorTool {
	private static Logger log = Log.getLogger(CopyFile.class);
	/**
	 * 
	 */
	public ReflectorTool() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Method[] m = ReflectorTool.class.getMethods();
			for(int i=0;i<m.length;i++){
				log.info("Method Name - "+m[i].getName());
				log.info("Modifiers - "+m[i].getModifiers());
				log.info("Class - "+m[i].getClass());
				log.info("Declaring Class - "+m[i].getDeclaringClass());
				log.info("ExceptionTypes - "+m[i].getExceptionTypes());
				log.info("ParameterTypes - "+m[i].getParameterTypes());
				log.info("ReturnType - "+m[i].getReturnType());
			}
		} catch (Exception e) {
			log.error("", e);
		}
	}

}
