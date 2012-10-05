package com.rs2.model.player.commandmanager.commands;

import com.rs2.model.player.Client;
import com.rs2.model.player.Player;
import com.rs2.model.player.commandmanager.Command;
import com.rs2.world.PlayerManager;

public class TeleportToMe implements Command {

	@Override
	public void execute(Client client, String command) {
		if (client.getPrivileges() >= 2) {
			if (command.length() > 9) {
				String name = command.substring(9);
				for (Player p : PlayerManager.getSingleton().getPlayers()) {
					if (p == null)
						continue;
					if (!p.isActive || p.disconnected)
						continue;
					if (p.getUsername().equalsIgnoreCase(name)) {
						p.teleportToZ = client.getHeightLevel();
						p.teleportToX = client.getAbsX();
						p.teleportToY = client.getAbsY();
					}
				}
			} else {
				client.getActionSender().sendMessage(
						"Syntax is ::teletome <name>.");
			}
		} else {
			client.getActionSender().sendMessage(
					"You do not have the correct rights to use this command.");
		}
	}

}
