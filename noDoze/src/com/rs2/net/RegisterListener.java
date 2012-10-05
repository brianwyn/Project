package com.rs2.net;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import com.rs2.mysql.Mysql;
import com.rs2.world.XMLManager;

/**
 * Handles all client IO through the selector.
 * 
 * @author Steven/killamess
 */
public class RegisterListener implements Runnable {

	public static void main(String[] args) { 
		try {
			new Thread(new RegisterListener()).start();
			
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	@Override
	public void run() {
		try {
			if (XMLManager.settings() == null)
				XMLManager.loadServerSettings();
			
			ServerSocket RegisterSocket = new ServerSocket(XMLManager.settings().getServerRegistrationPort());
			System.out.println("Registration Server is Online on port "+ XMLManager.settings().getServerRegistrationPort() +".");
			while (true) {
				
				Socket ClientSocket = RegisterSocket.accept();
				new Register(ClientSocket).start();
				
				Thread.sleep(15000);
			}
		} catch (Exception e) {
			System.out.println("Error loading Register Server.");
			e.printStackTrace();
		}
		
	}
	
	public class Register extends Thread {
		
		public Socket ClientSocket;
		
		public String[] Bad =  { 
				"'", "@"	
		};
		
		public Register(Socket ClientSocket) {
			this.ClientSocket = ClientSocket;
		}
	
		public void run() {
			try {
				
				BufferedReader in = new BufferedReader(new InputStreamReader(ClientSocket.getInputStream()));
				DataOutputStream out = new DataOutputStream(ClientSocket.getOutputStream());
				
				while (!in.ready()) {
				}
				String User = in.readLine(), Pass = in.readLine();
				
				boolean found = false;

				for(int i = 0; i < Bad.length; i++) {
					if(!User.matches("[a-zA-Z0-9 ]*") || !Pass.matches("[a-zA-Z0-9 ]*")) {
						found = true;
						out.writeBytes("0");
						out.close();
						in.close();
						ClientSocket.close();
						return;
					}
				}
				if (!found) {
					Connection con = Mysql.getConnection();
					Statement s = con.createStatement();
					s.executeQuery ("SELECT null FROM characters WHERE username = '" + User + "' limit 1");
					ResultSet rs = s.getResultSet();
					if (rs.next()) {
						out.writeBytes("1");
						out.close();
						in.close();
						ClientSocket.close();
					} else {
						rs.close();
						s.executeUpdate("insert into characters (username, password) values ('" + User + "', '"  + Pass + "')");
						s.close();
						con.close();
						out.writeBytes("2");
						out.close();
						in.close();
						ClientSocket.close();
						System.out.println("New user created: " + User + ".");
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
