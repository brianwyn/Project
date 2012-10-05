package com.rs2.model.player.commandmanager.commands;

import com.rs2.model.player.Client;
import com.rs2.model.player.commandmanager.Command;

/**
 * Castle wars command.
 * 
 * @author Ultimate
 */
public class CastleWars implements Command {

	@Override
	public void execute(Client client, String command) {
		if (client.getPrivileges() >= 2) {
			client.teleportToX = 2440;
			client.teleportToY = 3089;
		}
	}

}
