package com.rs2.model.player.packetmanager.packets;

import com.rs2.model.player.Client;
import com.rs2.model.player.packetmanager.Packet;

/**
 * Idle logout
 * 
 * @author Ultimate
 */

public class IdleLogout implements Packet {

	public void handlePacket(Client client, int packetType, int packetSize) {
		//if (client.logoutDelay == 0) {
		//	client.getActionSender().sendLogout();
		//}
	}

}