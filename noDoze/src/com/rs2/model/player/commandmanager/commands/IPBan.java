package com.rs2.model.player.commandmanager.commands;

import java.io.IOException;

import com.rs2.model.player.Client;
import com.rs2.model.player.Player;
import com.rs2.model.player.commandmanager.Command;
import com.rs2.util.BanProcessor;
import com.rs2.util.LogHandler;
import com.rs2.world.PlayerManager;

public class IPBan implements Command {

	@Override
	public void execute(Client client, String command) {
		if (client.getPrivileges() > 3) {
			if (command.length() > 6) {
				String name = command.substring(6);
				String ip = null;
				for (Player p : PlayerManager.getSingleton().getPlayers()) {
					if (p == null)
						continue;
					if (!p.isActive || p.disconnected)
						continue;
					if (p.getUsername().equalsIgnoreCase(name)) {
						ip = p.connectedFrom;
						p.kick();
						p.disconnected = true;
					}
				}
				try {
					if (ip == null)
						throw new IOException("temp quickfix lol");
					BanProcessor.Ban(name, 0, ip, 1);
					LogHandler.logIPBan(client.getUsername(), name, ip);
					client.getActionSender().sendMessage("You have ip-banned "+ name +".");
				} catch (IOException e) {
					client.getActionSender().sendMessage("Error processing ban for "+ name +".");
				}
			} else {
				client.getActionSender().sendMessage("Syntax is ::ban <name>.");
			}
		}
	}

}
