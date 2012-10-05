package com.rs2.model.player.commandmanager.commands;

import java.util.Map;

import com.rs2.GameEngine;
import com.rs2.model.npc.NPC;
import com.rs2.model.player.Client;
import com.rs2.model.player.commandmanager.Command;

public class ResetNPCDrops implements Command {

	@Override
	public void execute(Client client, String command) {
		
		if (client.getPrivileges() >= 2) {
			
			for (Map.Entry<Integer, NPC> entry : GameEngine.getNpcManager().npcMap.entrySet()) {
				
				NPC n = entry.getValue();

				n.getDefinition().getDrops().clear();	
			}
			GameEngine.getNpcManager().loadDrops();
			GameEngine.getGlobalActions().sendMessage("NPC drops have been updated");
		}
	}

}
