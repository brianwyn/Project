package com.rs2.model.player.packetmanager.packets;

import com.rs2.model.player.Client;
import com.rs2.model.player.packetmanager.Packet;

	/**
	 * Bank all items packet
	 * 
	 * @author Graham
	 */
public class ItemOnNpc implements Packet {

	@Override
	public void handlePacket(Client client, int packetType, int packetSize) {
		///int itemId = client.inStream.readSignedWordA();
		//int npcId = client.inStream.readSignedWordA();
		//int slot = client.inStream.readSignedWordBigEndian();
		client.getActionSender().sendMessage("Items on npcs is not finished yet.");
		//System.out.println(itemId +" "+ npcId +" "+ slot +".");	
		return;
		
	}
}
