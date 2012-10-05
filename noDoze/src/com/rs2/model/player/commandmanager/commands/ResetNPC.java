package com.rs2.model.player.commandmanager.commands;

import java.util.Map;

import com.rs2.Constants;
import com.rs2.GameEngine;
import com.rs2.model.combat.CombatEngine;
import com.rs2.model.npc.NPC;
import com.rs2.model.player.Client;
import com.rs2.model.player.commandmanager.Command;
import com.rs2.world.PlayerManager;

public class ResetNPC implements Command {

	@Override
	public void execute(Client client, String command) {
		if (client.getPrivileges() >= 2) {
			
			for (Map.Entry<Integer, NPC> entry : GameEngine.getNpcManager().npcMap.entrySet()) {
				NPC n = entry.getValue();
				n.setHidden(true);
			}
			GameEngine.getNpcManager().npcMap.clear();
			GameEngine.getNpcManager().reloadSpawns();
			GameEngine.getGlobalActions().sendMessage("NPC Reset");
			
			for (int i2 = 0; i2 < Constants.MAX_PLAYERS; i2++) {
				
				Client client2 = (Client) PlayerManager.getSingleton().getPlayers()[i2];
				
				if (client2 == null || client2.isDead())
					continue;
				
				if (client2.getTarget() != null) {
					if (client2.getTarget() instanceof NPC) {
						CombatEngine.resetAttack(client2, true);
					}
				}
			}
		}
	}

}