package com.rs2.model.player.packetmanager.packets;

/**
 * FollowHandler a player
 *
 * @author Ultimate
 *
 **/

import com.rs2.content.actions.ActionManager;
import com.rs2.content.minigames.FightPits;
import com.rs2.model.combat.CombatEngine;
import com.rs2.model.player.Client;
import com.rs2.model.player.packetmanager.Packet;
import com.rs2.tiles.FollowEngine;
import com.rs2.util.Misc;
import com.rs2.world.PlayerManager;


public class FollowPlayer implements Packet {

	public void handlePacket(Client client, int packetType, int packetSize) {
		CombatEngine.resetAttack(client, false);
		
		int followId = Misc.HexToInt(client.inStream.buffer, 0, packetSize) / 1000;
		
		Client follow = (Client) PlayerManager.getSingleton().getPlayers()[followId];
		ActionManager.destructActions(client.getUsername());
		if (follow != null) {
			if (FightPits.inWaitingArea(follow) || follow.isViewingOrb() || client.isBusy()) {
				return;
			}
			if (FightPits.inWaitingArea(client))
				return;
			client.following = follow;
			FollowEngine.loop(client);
		}
	}
}