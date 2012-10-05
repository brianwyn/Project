package com.rs2.model.player.commandmanager.commands;

import com.rs2.model.player.Client;
import com.rs2.model.player.commandmanager.Command;
import com.rs2.world.PlayerManager;

/**
 * Yell command.
 * 
 * @author Graham
 */
public class Yell implements Command {

	@Override
	public void execute(Client client, String command) {
		if (client.getPrivileges() >= 1) {
			if (command.length() > 5) {
				String words = command.substring(5);
				if (client.isYellMuted() || client.isMuted()) {
					client.getActionSender().sendMessage("You are muted.");
					return;
				}
				String prefix = "";
				if (client.getPrivileges() == 1)
					prefix = "[MODERATOR] ";
				else if (client.getPrivileges() == 2)
					prefix = "[ADMINISTRATOR] ";
				else if (client.getPrivileges() == 3)
					prefix = "[THE CREATOR] ";

				PlayerManager.getSingleton().sendGlobalMessage(
						prefix + client.getUsername() + ": " + words);
			} else {
				client.getActionSender().sendMessage(
						"Syntax is ::yell <message>.");
			}
		} else {
			client.getActionSender().sendMessage(
					"You do not have the correct rights to use this command.");
		}
	}

}
