package com.rs2.model.player.commandmanager.commands;

import com.rs2.model.player.Client;
import com.rs2.model.player.Player;
import com.rs2.model.player.commandmanager.Command;
import com.rs2.world.PlayerManager;

/**
 * Yell command.
 * 
 * @author Graham
 */
public class SaveAll implements Command {

	@Override
	public void execute(Client client, String command) {
		if (client.getPrivileges() >= 2) {
			int saved = 0;
			for (Player p : PlayerManager.getSingleton().getPlayers()) {
				if (p == null)
					continue;
				saved++;
				PlayerManager.getSingleton().saveGame(p, false);
				client.getActionSender().sendMessage(
						"Game saved from " + p.getUsername() + ".");
			}
			client.getActionSender().sendMessage(
					"A total of " + saved + " characters were saved.");
		} else {
			client.getActionSender().sendMessage(
					"You do not have the correct rights to use this command.");
		}
	}

}
