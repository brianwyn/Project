package com.rs2.model.player.commandmanager.commands;

import com.rs2.content.JailSystem;
import com.rs2.content.controllers.Location;
import com.rs2.model.player.Client;
import com.rs2.model.player.Player;
import com.rs2.model.player.commandmanager.Command;
import com.rs2.world.PlayerManager;

public class Jail implements Command {

	@Override
	public void execute(Client client, String command) {
		if (client.getPrivileges() >= 2) {
			if (command.length() > 5) {
				String name = command.substring(5);

				if (client.getUsername() == name) {
					client.getActionSender().sendMessage("You cannot jail yourself.");
					return;
				}
				client.getActionSender().sendMessage("You have jailed "+ name +".");
				client.getActionSender().sendMessage("You must now decided the severity of his 'crime'.");
				client.getActionSender().sendMessage("You must let the player express themselves before they receive punishment.");
				Location.addNewRequest(client, 3228, 3410, 0, 0);
				
				for (Player p : PlayerManager.getSingleton().getPlayers()) {
					
					if (p == null)
						continue;
					
					if (p.getUsername() == null)
						continue;
					
					if (p.getUsername().equalsIgnoreCase(name)) {
						JailSystem.addToJail((Client) p);
						break;
					}	
				}
				
			} else {
				client.getActionSender().sendMessage("Syntax is ::jail <name>.");
			}
		}
	}

}
