package com.rs2.model.player.commandmanager.commands;

import com.rs2.GameEngine;
import com.rs2.model.player.Client;
import com.rs2.model.player.commandmanager.Command;

public class SetNPC implements Command {

	@Override
	public void execute(Client client, String command) {	
		if (client.getPrivileges() >= 1) {	
			if (command.length() > 4) {
				int npcId = Integer.valueOf(command.substring(7));
				if (client.isNPC && npcId == -1) {
					client.isNPC = false;
					client.getActionSender().sendMessage("You have changed back to human.");
					return;
				} else {
					client.npcID = npcId;
					client.isNPC = true;
					client.getActionSender().sendMessage("You changed into an "+ GameEngine.getNPCManager().getNPCDefinition(npcId).getName().toLowerCase() +".");
					client.getActionSender().sendMessage("To change back to human type ::setnpc -1.");
				}
			} else
				client.getActionSender().sendMessage("Wrong syntax use ::setnpc #id.");
		}
	}
}