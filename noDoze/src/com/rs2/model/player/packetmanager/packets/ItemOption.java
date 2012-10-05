package com.rs2.model.player.packetmanager.packets;

import com.rs2.content.teleporting.JewleryTeleporting;
import com.rs2.model.player.Client;
import com.rs2.model.player.packetmanager.Packet;

/**
 * Killamess
 * @author killamess
 *
 */
public class ItemOption  implements Packet {

	@Override @SuppressWarnings("unused")
	public void handlePacket(Client client, int packetType, int packetSize) {
		int junk = client.inStream.readSignedWordA();
		int amount = client.inStream.readSignedWordBigEndian();
		int itemId = client.inStream.readSignedWordA();
		int moreJunk = client.inStream.readUnsignedWord();
		
		for (int glory : JewleryTeleporting.glorys) {
			if (itemId == glory) 
				JewleryTeleporting.gloryTeleport(client, itemId);
		}
		for (int ringOfDueling : JewleryTeleporting.duelingRings) {
			if (itemId == ringOfDueling) 
				JewleryTeleporting.duelingRingTeleport(client, itemId);
		}
	}

}