package com.rs2.util;

import java.sql.Connection;
import java.sql.Statement;

import com.rs2.mysql.Mysql;

/**
 * Rights processing.
 * 
 * @author Blake
 * @author Graham
 */
public class RightsProcessor {

	private RightsProcessor() {
	}

	public static void createModerator(String name) {
		try {
			Connection con = Mysql.getConnection();
			Statement s;
			s = con.createStatement();
			s.executeQuery ("update characters set rights = 1 where username = '" + name + "'");
			s.close();
			con.close();
			Mysql.release();
			} catch (Exception e) {
			}
	}

	public static void createAdministrator(String name) {
		try {
			Connection con = Mysql.getConnection();
			Statement s;
			s = con.createStatement();
			s.executeQuery ("update characters set rights = 2 where username = '" + name + "'");
			s.close();
			con.close();
			Mysql.release();
			} catch (Exception e) {
			}
	}
}