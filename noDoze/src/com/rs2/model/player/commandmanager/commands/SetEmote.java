package com.rs2.model.player.commandmanager.commands;

import com.rs2.model.player.Client;
import com.rs2.model.player.commandmanager.Command;

/**
 * Set emote command
 * 
 * @author Ventrillo
 */
public class SetEmote implements Command {

	@Override
	public void execute(Client client, String command) {
		if (client.getPrivileges() >= 3) {
			if (command.length() > 6) {
				int emote = Integer.valueOf(command.substring(6));
				if (client.getPrivileges() >= 2) {
					client.getActionAssistant().startAnimation(emote, 0);
				} else {
					client.getActionSender().sendMessage("You do not have the correct rights to use this command.");
				}
			} else {
				client.getActionSender().sendMessage("Wrong syntax use ::emote <emote id>");
			}
		}
	}

}