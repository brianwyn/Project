package com.rs2.model.player.packetmanager.packets;

import com.rs2.GameEngine;
import com.rs2.content.actions.ActionManager;
import com.rs2.content.minigames.Duelling;
import com.rs2.content.minigames.FightPits;
import com.rs2.model.Entity;
import com.rs2.model.combat.CombatEngine;
import com.rs2.model.combat.ranged.Ranged;
import com.rs2.model.npc.NPC;
import com.rs2.model.player.Client;
import com.rs2.model.player.packetmanager.Packet;
import com.rs2.tiles.FollowEngine;
import com.rs2.world.PlayerManager;

/**
 * Attack packet handler

 * @author Killamess
 */
public class Attack implements Packet {

	public static final int NPC = 72;
	public static final int PLAYER = 128;
	
	public void handlePacket(final Client client, int packetType, int packetSize) {
		
		if (client.getStunnedTimer() > 0 || client.isBusy())
			return;
		
		
		
		
		Entity target = null;
		
		if (Ranged.isUsingRange(client)) {
			if (!Ranged.hasArrows(client)) {
				client.stopMovement();
				return;
			}
		}
		if (packetType == NPC) {
			target = (NPC) GameEngine.getNPCManager().getNPC(client.inStream.readUnsignedWordA());
		} else if (packetType == PLAYER) {
			target = (Client) PlayerManager.getSingleton().getPlayers()[client.inStream.readSignedWord()];
		}
		if (target == null)
			return;
		if (Ranged.isUsingRange(client)) {
			client.stopMovement();
		}
		
		if (target.getOwner() != null && target.getOwner() != client) {
			client.getActionSender().sendMessage("You cannot attack this npc.");
			return;
		}
		if (target instanceof Client) {
			if (Duelling.inDuelArea(client)) {
				Duelling.requestDuel(client, (Client) target);
				return;
			} else {
				//client.getActionSender().sendMessage("attack");
			}
			if (FightPits.inWaitingArea((Client)target) || ((Client)target).isViewingOrb()) {
				client.getActionSender().sendMessage("You cannot attack players in the waiting room.");
				return;
			}
		}

		
		ActionManager.destructActions(client.getUsername());
		client.turnOffSpell = false;
		client.spellId = 0;
		Entity ent = client;
		client.setRetaliateDelay(0);
		CombatEngine.addEvent(client, target);
		ent.following = target;
		FollowEngine.loop(client);
		
	}


}