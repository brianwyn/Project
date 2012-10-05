package com.rs2.util.log;

import java.io.PrintStream;
import java.text.DecimalFormat;

/**
 * Logging class.
 * 
 * @author Graham
 */
public class Log extends PrintStream {

	private final DecimalFormat format = new DecimalFormat("00");
	private final long startTime = System.currentTimeMillis();

	public Log(PrintStream out) {
		super(out);
	}

	@Override
	public void print(String s) {
		String f = prefix() + s;
		super.print(f);
	}

	private String prefix() {
		return "[" + timeSince(startTime) + "]: ";
	}

	public final String timeSince(long time) {
		int seconds = (int) ((System.currentTimeMillis() - time) / 1000);
		int minutes = (seconds / 60);
		int hours = (minutes / 60);
		int days = (hours / 24);
		String dayStr = "";
		if (days > 0)
			dayStr = days + " days, ";
		String s = null;
		synchronized (format) {
			s = dayStr + format.format(hours % 24) + ":"
					+ format.format(minutes % 60) + ":"
					+ format.format(seconds % 60);
		}
		return s;
	}

}
