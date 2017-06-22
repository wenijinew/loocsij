package org.loocsij.tool;

import org.loocsij.util.FileUtil;

public class DeleteFiles {
	/**
	 * Delete directory or file. User can restrict the extension of files to
	 * delete.
	 * 
	 * @param args -
	 *            <br>
	 *            args[0]: directory of file's absolute path, required<br>
	 *            args[1]: extension of files to delete, optional
	 */
	public static void main(String[] args) {
		if (args == null || args.length == 0) {
			System.err
					.println("Usage: args[0] - directory of file's absolute path to delete; args[1] - extension of files to delete");
			return;
		}
		if (args.length < 2 || args[1].trim().length() == 0) {
			FileUtil.deleteFiles(args[0], null);
		} else {
			FileUtil.deleteFiles(args[0], args[1]);
		}
	}
}
