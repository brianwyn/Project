package com.rs2.model.player.packetmanager.packets;

import com.rs2.content.ItemDestroy;
import com.rs2.content.actions.ActionManager;
import com.rs2.model.combat.CombatEngine;
import com.rs2.model.player.Client;
import com.rs2.model.player.packetmanager.Packet;

/**
 * Drop item packet
 * 
 * @author Graham
 */
public class DropItem implements Packet {

	@Override
	public void handlePacket(Client client, int packetType, int packetSize) {
		int droppedItem = client.inStream.readUnsignedWordA();
		client.inStream.readUnsignedByte();
		client.inStream.readUnsignedByte();
		int slot = client.inStream.readUnsignedWordA();
		if (client.isBusy()) {
			return;
		}
		client.getActionSender().sendWindowsRemoval();
		
		if (client.playerItemsN[slot] != 0 && droppedItem != -1 && client.playerItems[slot] == droppedItem + 1) {
			
			for (int z : DESTROYABLES) {
				if (z == droppedItem) {
					ItemDestroy.option(client, droppedItem);
					return;
				}
			}
			 client.getActionAssistant().dropItem(droppedItem, slot);
		 }
		ActionManager.destructActions(client.getUsername());
		CombatEngine.resetAttack(client, false);
	}
	public static final int[] DESTROYABLES = {6570,7462,8850,8845,8846,8847,8848,8849,8844}; // Destroyable items
	
}
