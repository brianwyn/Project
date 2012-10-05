package com.rs2.util;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import com.rs2.mysql.Mysql;

/**
 * Bans processing.
 * 
 * @author Blake
 * @author Graham
 * @author Killamess
 * @auther Steven
 */
public class BanProcessor {

	private BanProcessor() {
	}
	public static void Ban(String name, int uid, String ip, int type) {
		try {
			Connection con = Mysql.getConnection();
			Statement s;
			s = con.createStatement();
			s.executeQuery ("insert into badplayers (username, ip, type) values ('" + name + "', '" + ip + "', '" + type + "')");
			s.close();
			con.close();
			Mysql.release();	
		} catch (Exception e) {
				
			}			
	}

	public static boolean checkUser(String name) {
		try {
			Connection con = Mysql.getConnection();
			Statement s;
			s = con.createStatement();
			s.executeQuery ("SELECT null FROM badplayers where username = '" + name + "' and type = 0");
			ResultSet rs = s.getResultSet ();
			if (rs.next()) {
				s.close();
				rs.close();
				con.close();
				Mysql.release();
				return true;
			} else {
				s.close();
				rs.close();
				con.close();
				Mysql.release();
				return false;
			}
			} catch (Exception e) {
				return false;
			}
	}

	public static boolean checkMuted(String name) {
		try {
			Connection con = Mysql.getConnection();
			Statement s;
			s = con.createStatement();
			s.executeQuery ("SELECT null FROM badplayers where username = '" + name + "' and type = 2");
			ResultSet rs = s.getResultSet ();
			if (rs.next()) {
				s.close();
				rs.close();
				con.close();
				Mysql.release();
				return true;
			} else {
				s.close();
				rs.close();
				con.close();
				Mysql.release();
				return false;
			}
			} catch (Exception e) {
				return false;
			}
	}
	
	public static boolean checkIpMuted(String name) {
		try {
			Connection con = Mysql.getConnection();
			Statement s;
			s = con.createStatement();
			s.executeQuery ("SELECT null FROM badplayers where username = '" + name + "' and type = 3");
			ResultSet rs = s.getResultSet ();
			if (rs.next()) {
				s.close();
				rs.close();
				con.close();
				Mysql.release();
				return true;
			} else {
				s.close();
				rs.close();
				con.close();
				Mysql.release();
				return false;
			}
			} catch (Exception e) {
				return false;
			}
	}

	public static boolean checkIP(String ip) {
		try {
		Connection con = Mysql.getConnection();
		Statement s;
		s = con.createStatement();
		s.executeQuery ("SELECT null FROM badplayers where ip = '" + ip + "' and type = 1");
		ResultSet rs = s.getResultSet ();
		if (rs.next()) {
			s.close();
			rs.close();
			con.close();
			Mysql.release();
			return true;
		} else {			
		s.close();
		rs.close();
		con.close();
		Mysql.release();
			return false;
		}
		} catch (Exception e) {
			return false;
		}
	}
}