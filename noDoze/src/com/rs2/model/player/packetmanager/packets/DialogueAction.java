package com.rs2.model.player.packetmanager.packets;

import com.rs2.model.player.Client;
import com.rs2.model.player.packetmanager.Packet;

/**
 * DialogueAction clicks
 * 
 * @author Ultimate
 */
public class DialogueAction implements Packet {

	public void handlePacket(Client client, int packetType, int packetSize) {
		client.getActionSender().sendWindowsRemoval();
	}

}