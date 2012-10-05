package com.rs2.model.player.commandmanager.commands;

import java.io.IOException;

import com.rs2.model.player.Client;
import com.rs2.model.player.Player;
import com.rs2.model.player.commandmanager.Command;
import com.rs2.util.BanProcessor;
import com.rs2.util.LogHandler;
import com.rs2.world.PlayerManager;

public class IpMute  implements Command {

		@Override
		public void execute(Client client, String command) {
			if (client.getPrivileges() >= 2) {
				if (command.length() > 5) {
					String name = command.substring(5);
					for (Player p : PlayerManager.getSingleton().getPlayers()) {
						if (p == null)
							continue;
						if (!p.isActive || p.disconnected)
							continue;
						Client player = (Client) p;
						if (p.getUsername().equalsIgnoreCase(name)) {
							player.setMuted(true);
						}
					}
					try {
						BanProcessor.Ban(name, 0, name, 3);
						LogHandler.logMute(client.getUsername(), name);
						client.getActionSender().sendMessage("You have ip-muted "+ name +".");
					} catch (IOException e) {
						client.getActionSender().sendMessage("Error processing ip-mute for "+ name +".");
					}
				} else {
					client.getActionSender()
							.sendMessage("Syntax is ::mute <name>.");
				}
			}
		}
}
