package org.loocsij.tool;

import org.apache.logging.log4j.Logger;

import org.loocsij.logger.Log;
import org.loocsij.util.FileUtil;

/**
 * This class synchronizes files by invoking method of com.util.FileUtil. There
 * are 4 arguments can be set. First two arguments are required and another two
 * ones are optional. The required arguments are "SourceDirectory" and
 * "DestinationDirectory". The optional arugments are "FilterFolderNames" and
 * "MinSynchronizationBreak"."FilterFolderNames" are folder names separated by
 * "," and "MinSynchronizationBreak" is the minimal break of synchronization, if
 * the difference of last modiried time of source file and destination file is
 * smaller than "MinSynchronizationBreak", the file will not be synchronized.
 * 
 * 
 * @author wengm
 * 
 */
public class SynchronizeFiles {
	private static Logger log = Log.getLogger(FileUtil.class);

	/**
	 * 
	 * @param args -
	 *            <br>
	 *            <ul>
	 *            <li>args[0]-Source Directory
	 *            <li>args[1]-Destination Directory
	 *            <li>args[2]-Filter file or directory
	 *            <li>args[3]-Minimum milliseconds used to judge if the
	 *            destination file is newer than the source file
	 *            <li>args[4]-synchronization type:<br>
	 *            0, full synchronization, update existent files, copy new
	 *            files, delete excrescent destination files<br>
	 *            1, basic synchronization, update existent files and copy new
	 *            files <br>
	 *            2, minimum synchronization, just copy new files
	 *            </ul>
	 */
	public static void main(String[] args) {
		String filterFolderNames = null;
		long min_synchronization_break = -1L;
		int synchType = 0;
		if (args.length > 2 && args[2].trim().length() > 0) {
			filterFolderNames = args[2];
		}
		if (args.length > 3 && args[3].trim().length() > 0) {
			min_synchronization_break = Long.valueOf(args[3]).longValue();
		} else {
			min_synchronization_break = 1000L * 60L * 5L;
		}
		if (args.length > 4 && args[4].trim().length() > 0) {
			synchType = Integer.valueOf(args[4]).intValue();
		}

		log.info("[" + args[0] + "] >>>>>> [" + args[1] + "]");
		FileUtil.synchronize(args[0], args[1], filterFolderNames,
				min_synchronization_break, synchType);
	}
}
