/**
 * 
 */
package org.loocsij.tool;

import org.loocsij.util.ZipUtil;

/**
 * @author wengm
 *
 */
public class DecompressZip {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ZipUtil.decompress(args[0], args[1]);
	}
}
