package com.rs2.model.player.packetmanager.packets;

import com.rs2.content.actions.ActionManager;
import com.rs2.content.minigames.FightPits;
import com.rs2.content.minigames.Jad;
import com.rs2.model.combat.CombatEngine;
import com.rs2.model.combat.magic.TeleOther;
import com.rs2.model.player.Client;
import com.rs2.model.player.packetmanager.Packet;
import com.rs2.world.PlayerManager;

/**
 * Magic on player
 * 
 * @author Killamess
 */

public class MagicOnPlayer implements Packet {

	@Override
	public void handlePacket(Client client, int packetType, int packetSize) {
		
		int magicOn = client.inStream.readSignedWordA();
		int spellId = client.inStream.readSignedWordBigEndian();
		
		Client enemy = (Client) PlayerManager.getSingleton().getPlayers()[magicOn];
		
		ActionManager.destructActions(client.getUsername());
		
		//System.out.println(spellId);
		
		if (enemy == null)
			return;
	
		if (Jad.inArea(client))
			return;
		
		if (enemy instanceof Client) {
			if (FightPits.inWaitingArea(enemy) || enemy.isViewingOrb()) {
				client.getActionSender().sendMessage("You cannot attack players in the waiting room.");
				return;
			}
		}
		for (int[] s : TeleOther.SPELLS) {
			if (s[0] == spellId) {
				TeleOther.castSpell(client, enemy, spellId);
				client.stopMovement();
				return;
			}
		}
		
		client.setRetaliateDelay(0);
		client.spellId = spellId;
		client.turnOffSpell = false;
		CombatEngine.addEvent(client, enemy);
		client.stopMovement();
	}
}
