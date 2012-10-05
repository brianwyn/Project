package com.rs2.model.player.packetmanager.packets;

import com.rs2.GameEngine;
import com.rs2.content.actions.ActionManager;
import com.rs2.model.Entity;
import com.rs2.model.combat.CombatEngine;
import com.rs2.model.npc.NPC;
import com.rs2.model.player.Client;
import com.rs2.model.player.packetmanager.Packet;
import com.rs2.tiles.FollowEngine;

public class MagicOnNPC implements Packet {

		@Override
		public void handlePacket(Client client, int packetType, int packetSize) {
			int npcId = client.inStream.readSignedWordBigEndianA();
			int spellId = client.inStream.readSignedWordA();
			
			Entity target = null;
			
			target = (NPC) GameEngine.getNPCManager().getNPC(npcId);
			
			ActionManager.destructActions(client.getUsername());
			if (target == null)
				return;
			if (target.getOwner() != null && target.getOwner() != client) {
				client.getActionSender().sendMessage("You cannot attack this npc.");
				return;
			}
			client.spellId = spellId;
			client.turnOffSpell = false;
			client.stopMovement();
			client.setRetaliateDelay(0);
			FollowEngine.resetFollowing(client);
			CombatEngine.addEvent(client, target);	
	
		}
	}

