package com.rs2.model.player.commandmanager.commands;

import com.rs2.model.player.Client;
import com.rs2.model.player.commandmanager.Command;

/**
 * My position command
 * 
 * @author Graham
 */
public class MyPosition implements Command {

	@Override
	public void execute(Client client, String command) {
		client.getActionSender().sendMessage(
				"[SERVER] playerX: " + client.getAbsX() + 
				" playerY: " + client.getAbsY() + 
				" playerHeight: " + client.getHeightLevel() + ".");
	}

}
