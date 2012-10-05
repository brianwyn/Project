package com.rs2.model.player.commandmanager.commands;

import com.rs2.model.player.Client;
import com.rs2.model.player.commandmanager.Command;
import com.rs2.world.PlayerManager;

public class Kick implements Command {

	@Override
	public void execute(Client client, String command) {
		if (client.getPrivileges() > 1) {
			if (command.length() > 5) {
				String name = command.substring(5);
				PlayerManager.getSingleton().kick(name);
				client.getActionSender().sendMessage("You have kicked "+ name +".");
			} else {
				client.getActionSender()
						.sendMessage("Syntax is ::kick <name>.");
			}
		}
	}

}
