package com.rs2.model.player.packetmanager.packets;

/**
 * Wear item
 *
 * @author Ultimate
 *
 **/

import com.rs2.content.actions.ActionManager;
import com.rs2.model.player.Client;
import com.rs2.model.player.packetmanager.Packet;

public class Wear implements Packet {

	public void handlePacket(Client client, int packetType, int packetSize) {
		int wearID = client.inStream.readUnsignedWord();
		int wearSlot = client.inStream.readUnsignedWordA();
		@SuppressWarnings("unused")
		int interfaceID = client.inStream.readUnsignedWordA();

		
		if (client.hitpoints > 0 && !client.isBusy()) {
			if (client.getTradeHandler().getStage() == 0) {
				client.getEquipment().wearItem(wearID, wearSlot);
			}
		}
		ActionManager.destructActions(client.getUsername());
	}
}