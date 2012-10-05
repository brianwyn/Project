package com.rs2.model.player.commandmanager.commands;

import com.rs2.GameEngine;
import com.rs2.model.player.Client;
import com.rs2.model.player.commandmanager.Command;

public class MultiSwitch  implements Command {

	@Override
	public void execute(Client client, String command) {
		
		if (client.getPrivileges() >= 3) {
			if (GameEngine.multiValve) {
				client.getActionSender().sendMessage("[SERVER] Multi-Zone (ALL): true.");
				GameEngine.multiValve = false;
			} else {
				client.getActionSender().sendMessage("[SERVER] Multi-Zone (ALL): false.");
				GameEngine.multiValve = true;
			}
		}
	}

}
