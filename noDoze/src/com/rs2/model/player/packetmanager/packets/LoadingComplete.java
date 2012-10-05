package com.rs2.model.player.packetmanager.packets;

import com.rs2.model.player.Client;
import com.rs2.model.player.packetmanager.Packet;

/**
 * Loading complete packet.
 * 
 * @author Graham
 */
public class LoadingComplete implements Packet {

	public static final int GAME_LOAD = 121, AREA_LOAD = 210;

	@Override
	public void handlePacket(Client client, int packetType, int packetSize) {
		if (packetType == AREA_LOAD) {
			client.doZoning();
		}
	}

}
