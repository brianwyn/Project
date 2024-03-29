package com.rs2.model.player.commandmanager.commands;

import java.sql.Connection;
import java.sql.Statement;
import java.util.Map;

import com.rs2.Constants;
import com.rs2.GameEngine;
import com.rs2.model.combat.CombatEngine;
import com.rs2.model.npc.NPC;
import com.rs2.model.player.Client;
import com.rs2.model.player.commandmanager.Command;
import com.rs2.mysql.Mysql;
import com.rs2.world.PlayerManager;

public class AddNPC implements Command {

	public void execute(Client client, String command) {
		if (client.getPrivileges() >= 2) {
			try {
				
				String[] args = command.split(" ");
				
				if (args.length == 3) {
					
					int npcId = Integer.parseInt(args[1]);
					int distance = Integer.parseInt(args[2]);
					
					int x = client.getAbsX();
					int y = client.getAbsY();
					int z = client.getHeightLevel();
					
					if (npcId > 3782) {
						client.getActionSender().sendMessage("NPCID cannot exceed the limit of 3782.");
						return;
					}
					
					if (distance > 10) {
						client.getActionSender().sendMessage("The distance cannot exceed the limit of 10.");
						return;
					}
					Connection con = Mysql.getConnection();
					Statement s = con.createStatement();
					s.executeUpdate("insert into npcspawns (id, absX, absY, height, rangeX1, rangeX2, rangeY1, rangeY2, walktype, Descrption) value ('" + npcId + "', '" + x + "', '" + y + "', '" + z + "', '"+(x + distance)+"', '"+(y + distance)+"', '"+(x - distance)+"', '"+(y - distance)+"', '1', '')");
					s.close();
					con.close();
					Mysql.release();
					client.getActionSender().sendMessage("npc added.");
					for (Map.Entry<Integer, NPC> entry : GameEngine.getNpcManager().npcMap.entrySet()) {
						NPC n = entry.getValue();
						n.setHidden(true);
						
					}
					GameEngine.getNpcManager().npcMap.clear();
					GameEngine.getNpcManager().reloadSpawns();
					GameEngine.getGlobalActions().sendMessage("NPCs have been reset");
					
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
			} catch (Exception e) {
				client.getActionSender().sendMessage("Incorrect usage,  ::addnpc npcid distance");
			}
		}
	}
}
