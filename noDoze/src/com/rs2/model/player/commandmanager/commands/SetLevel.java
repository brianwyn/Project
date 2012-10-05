package com.rs2.model.player.commandmanager.commands;

import com.rs2.model.player.Client;
import com.rs2.model.player.commandmanager.Command;

public class SetLevel implements Command {

	@Override
	public void execute(Client client, String command) {
		if (client.getPrivileges() >= 3) {
			
			if (command.length() > 9) {
				
				int level = Integer.valueOf(command.substring(9));
				
				client.combatLevel = level;
				
			} else {
				
				client.getActionSender().sendMessage("Wrong syntax use ::setLevel <combat level>");
				
			} 
		}
	}
}
