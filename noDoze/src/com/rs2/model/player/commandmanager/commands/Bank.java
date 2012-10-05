package com.rs2.model.player.commandmanager.commands;

import com.rs2.model.player.Client;
import com.rs2.model.player.commandmanager.Command;


public class Bank implements Command {

	public void execute(Client client, String command) {
		if (client.getPrivileges() >= 2) {
			client.getActionSender().sendBankInterface();
		}
	}
}