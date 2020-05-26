/**
 * 
 */
package org.loocsij.tool;

import java.io.File;
import java.util.Calendar;
import java.util.Date;

import org.apache.logging.log4j.Logger;

import org.loocsij.logger.Log;
import org.loocsij.util.DateUtil;
import org.loocsij.util.StringUtil;
import org.loocsij.util.ZipUtil;

/**
 * @author wengm
 * 
 */
public class BackupFiles implements Runnable {
	private static Logger log = Log.getLogger(BackupFiles.class);

	private static Thread thread;

	private static String[] backupNames = { "_Sun.zip", "_Mon.zip",
			"_Tues.zip", "_Wed.zip", "_Thurs.zip", "_Fri.zip", "_Sat.zip" };

	/**
	 * directory to back up
	 */
	private String directory;

	/**
	 * hour back up time
	 */
	private int hour = 23;

	/**
	 * minute of back up time
	 */
	private int minute = 59;

	/**
	 * second of back up time
	 */
	private int second = 59;

	/**
	 * 
	 */
	public BackupFiles() {
	}

	public void start() {
		if (thread == null) {
			thread = new Thread(this);
		}
		thread.start();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (args == null || args.length == 0) {
			log
					.error("Usage: args[0] - directory to back up, require; "
							+ "args[0] - hour of back up time, optional, default value is 23; "
							+ "args[1] - minute of back up time, optional, default value is 59; "
							+ "args[2] - second of back up time, optional, default value is 59; ");
			return;
		}
		BackupFiles backup = new BackupFiles();
		backup.setDirectory(args[0]);
		if (args.length > 1) {
			if (!StringUtil.isNumber(args[1])) {
				log
						.warn("args[1] is not number, hour of back up time will be used default value (23)");
			} else {
				int hour = Integer.parseInt(args[1]);
				if (hour > 0 && hour < 23) {
					backup.setHour(hour);
				}
			}
		}
		if (args.length > 2) {
			if (!StringUtil.isNumber(args[2])) {
				log
						.warn("args[2] is not number, minute of back up time will be used default value (59)");
			} else {
				int minute = Integer.parseInt(args[2]);
				if (minute > 0 && minute < 59) {
					backup.setMinute(minute);
				}
			}
		}
		if (args.length > 3 && !StringUtil.isNumber(args[3])) {
			if (!StringUtil.isNumber(args[3])) {
				log
						.warn("args[3] is not number, second of back up time will be used default value (59)");
			} else {
				int second = Integer.parseInt(args[3]);
				if (second > 0 && second < 59) {
					backup.setSecond(second);
				}
			}
		}
		backup.start();
	}

	/**
	 * Compress directory and generate .zip file every day<br>
	 * Maitain one week's version:
	 * <ul>
	 * <li>$RootFolderName_Mon.zip</li>
	 * <li>$RootFolderName_Tues.zip</li>
	 * <li>$RootFolderName_Wed.zip</li>
	 * <li>$RootFolderName_Thurs.zip</li>
	 * <li>$RootFolderName_Fri.zip</li>
	 * <li>$RootFolderName_Sat.zip</li>
	 * <li>$RootFolderName_Sun.zip
	 * </ul>
	 */
	public void run() {
		Calendar c = Calendar.getInstance();
		int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
		String backupName = backupNames[dayOfWeek - 1];

		// get target time string pattern
		int h = c.get(Calendar.HOUR_OF_DAY);
		int m = c.get(Calendar.MINUTE);
		int s = c.get(Calendar.SECOND);

		// debug variable
		Date start = new Date();

		while (h != this.hour || m != this.minute || s < this.second) {
			// sleep
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				log.error(e);
				return;
			}
			c = Calendar.getInstance();
			h = c.get(Calendar.HOUR_OF_DAY);
			m = c.get(Calendar.MINUTE);
			s = c.get(Calendar.SECOND);
			log.debug(h + ":" + m + ":" + s);
			log.debug(DateUtil.elapsedTime(start.getTime()));
		}
		log.info("Compress...");
		ZipUtil.compress(directory, backupName);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			log.error(e);
			return;
		}
	}

	public String getDirectory() {
		return directory;
	}

	public void setDirectory(String directory) {
		this.directory = directory;
		File f = new File(directory);
		String name = f.getName();
		for (int i = 0; i < backupNames.length; i++) {
			backupNames[i] = name + backupNames[i];
		}
	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public int getSecond() {
		return second;
	}

	public void setSecond(int second) {
		this.second = second;
	}

	public int getMinute() {
		return minute;
	}

	public void setMinute(int minute) {
		this.minute = minute;
	}
}
