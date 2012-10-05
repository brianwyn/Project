package com.rs2.model.player.packetmanager.packets;

import com.rs2.content.actions.ActionManager;
import com.rs2.model.combat.CombatEngine;
import com.rs2.model.player.Client;
import com.rs2.model.player.packetmanager.Packet;

/**
 * Pickup item packet
 * 
 * @author Graham
 */
public class PickupItem implements Packet {

	@Override
	public void handlePacket(Client client, int packetType, int packetSize) {
		int itemY = client.inStream.readSignedWordBigEndian();
		int itemID = client.inStream.readUnsignedWord();
		int itemX = client.inStream.readSignedWordBigEndian();
		ActionManager.destructActions(client.getUsername());
		client.println_debug("pickupItem: " + itemX + "," + itemY + " itemID: "
				+ itemID);
		client.pickup[0] = itemX;
		client.pickup[1] = itemY;
		client.pickup[2] = itemID;
		//client.getActionAssistant().pickUpItem(itemX, itemY, itemID);
		CombatEngine.resetAttack(client, false);
	}

}
