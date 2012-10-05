package com.rs2.model.player.commandmanager.commands;

import com.rs2.model.player.Client;
import com.rs2.model.player.Player;
import com.rs2.model.player.commandmanager.Command;
import com.rs2.world.PlayerManager;

public class TeleportTo implements Command {

	@Override
	public void execute(Client client, String command) {
		if (client.getPrivileges() >= 1) {
			if (command.length() > 7) {
				String name = command.substring(7);
				for (Player p : PlayerManager.getSingleton().getPlayers()) {
					if (p == null)
						continue;
					if (!p.isActive || p.disconnected)
						continue;
					if (p.getUsername().equalsIgnoreCase(name)) {
						client.teleportToZ = p.getHeightLevel();
						client.teleportToX = p.getAbsX();
						client.teleportToY = p.getAbsY();
					}
				}
			} else {
				client.getActionSender().sendMessage(
						"Syntax is ::teleto <name>.");
			}
		} 
	}

}
