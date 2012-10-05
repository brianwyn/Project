package com.rs2.model.player.commandmanager.commands;

import java.io.IOException;

import com.rs2.model.player.Client;
import com.rs2.model.player.Player;
import com.rs2.model.player.commandmanager.Command;
import com.rs2.util.BanProcessor;
import com.rs2.util.LogHandler;
import com.rs2.world.PlayerManager;

public class Ban implements Command {

	@Override
	public void execute(Client client, String command) {
		if (client.getPrivileges() >= 2) {
			if (command.length() > 4) {
				String name = command.substring(4);
				for (Player p : PlayerManager.getSingleton().getPlayers()) {
					if (p == null)
						continue;
					if (!p.isActive || p.disconnected)
						continue;
					if (p.getUsername().equalsIgnoreCase(name)) {
						p.kick();
						p.disconnected = true;
					}
				}
				try {
					BanProcessor.Ban(name, 0, name, 0);
					LogHandler.logBan(client.getUsername(), name);
					client.getActionSender().sendMessage("You have banned "+ name +".");
				} catch (IOException e) {
					client.getActionSender().sendMessage("Error processing ban for "+ name +".");
				}
			} else {
				client.getActionSender().sendMessage("Syntax is ::ban <name>.");
			}
		}
	}

}
