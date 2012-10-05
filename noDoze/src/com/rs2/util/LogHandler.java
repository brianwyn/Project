package com.rs2.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogHandler {

	private static SimpleDateFormat date = new SimpleDateFormat("yyyy/MM/dd");
	private static SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss");

	private LogHandler() {
	}

	public static String getTime() {
		return "[" + date.format(new Date()) + "][" + time.format(new Date())
				+ "]:";
	}

	public static void logMute(String name, String victim) throws IOException {
		BufferedWriter bw = new BufferedWriter(new FileWriter(
				"./data/logs/mutes.txt", true));
		bw.write(getTime() + " " + name + " muted: " + victim + ".");
		bw.newLine();
		bw.flush();
		bw.close();
	}

	public static void logBan(String name, String victim) throws IOException {
		BufferedWriter bw = new BufferedWriter(new FileWriter(
				"./data/logs/bans.txt", true));
		bw.write(getTime() + " " + name + " banned: " + victim + ".");
		bw.newLine();
		bw.flush();
		bw.close();
	}

	public static void logIPBan(String name, String victim, String ip)
			throws IOException {
		BufferedWriter bw = new BufferedWriter(new FileWriter(
				"./data/logs/ipbans.txt", true));
		bw.write(getTime() + " " + name + " ipbanned: " + victim + ":" + ip
				+ ".");
		bw.newLine();
		bw.flush();
		bw.close();
	}

}