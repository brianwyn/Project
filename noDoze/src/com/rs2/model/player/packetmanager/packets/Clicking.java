package com.rs2.model.player.packetmanager.packets;

import com.rs2.model.player.Client;
import com.rs2.model.player.packetmanager.Packet;

/**
 * Clicking
 * 
 * @author Ultimate
 */

public class Clicking implements Packet {

	public void handlePacket(Client client, int packetType, int packetSize) {
		int interfaceId = client.inStream.readUnsignedWordA();
		switch(interfaceId) {}
		client.cancelTasks();
	}

}
