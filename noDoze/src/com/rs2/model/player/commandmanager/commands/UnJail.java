package com.rs2.model.player.commandmanager.commands;

import com.rs2.content.JailSystem;
import com.rs2.content.controllers.Location;
import com.rs2.model.player.Client;
import com.rs2.model.player.Player;
import com.rs2.model.player.commandmanager.Command;
import com.rs2.world.PlayerManager;

public class UnJail implements Command {

	@Override
	public void execute(Client client, String command) {
		if (client.getPrivileges() >= 2) {
			if (command.length() > 7) {
				String name = command.substring(7);
				
				client.getActionSender().sendMessage("You have released "+ name +" from jail.");
				
				for (Player p : PlayerManager.getSingleton().getPlayers()) {
					if (p == null)
						continue;
					
					if (p.getUsername() == null)
						continue;
					
					if (p.getUsername().equalsIgnoreCase(name)) {
						JailSystem.removeFromJail((Client) p);
						break;
					}	
				}
				Location.addNewRequest(client, 3228, 3410, 0, 0);
			} else {
				client.getActionSender().sendMessage("Syntax is ::jail <name>.");
			}
		}
	}

}