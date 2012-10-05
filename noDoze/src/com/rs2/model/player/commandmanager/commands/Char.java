package com.rs2.model.player.commandmanager.commands;

import com.rs2.model.player.Client;
import com.rs2.model.player.commandmanager.Command;

public class Char implements Command {

	@Override
	public void execute(Client client, String command) {
		if (client.getPrivileges() >= 1) {
			client.getActionSender().sendInterface(3559);
		}
	}

}