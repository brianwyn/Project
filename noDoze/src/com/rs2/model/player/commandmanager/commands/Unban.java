package com.rs2.model.player.commandmanager.commands;

import java.sql.Connection;
import java.sql.Statement;

import com.rs2.model.player.Client;
import com.rs2.model.player.commandmanager.Command;
import com.rs2.mysql.Mysql;

public class Unban implements Command {

	@Override
	public void execute(Client client, String command) {
		if (client.getPrivileges() >= 2) {
			if (command.length() > 6) {
				String name = command.substring(6);
				try {
					Connection con = Mysql.getConnection();
					Statement s;
					s = con.createStatement();
					s.executeQuery ("delete from badplayers where username = '" + name + "' and type = 0");
					s.close();
					con.close();
					Mysql.release();
					client.getActionSender().sendMessage("You have unbanned "+ name +".");
				} catch (Exception e) {
					client.getActionSender().sendMessage("Error processing release of ban for "+ name +".");
				}
			} else {
				client.getActionSender().sendMessage(
						"Syntax is ::unban <name>.");
			}
		} 
	}

}
