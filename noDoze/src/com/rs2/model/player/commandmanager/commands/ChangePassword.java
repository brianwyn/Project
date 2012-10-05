package com.rs2.model.player.commandmanager.commands;

import com.rs2.model.player.Client;
import com.rs2.model.player.commandmanager.Command;

/**
 * Changing password command.
 * 
 * @author Graham
 */
public class ChangePassword implements Command {

	@Override
	public void execute(Client client, String command) {
		if (command.length() > 5) {
			client.setPassword(command.substring(5));
			client.getActionSender().sendMessage(
					"Your new pass is \"" + command.substring(5) + "\"");
		} else {
			client.getActionSender().sendMessage(
					"Syntax is ::pass <new password>.");
		}
	}

}
