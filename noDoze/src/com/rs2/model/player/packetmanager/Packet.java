package com.rs2.model.player.packetmanager;

import com.rs2.model.player.Client;

/**
 * Packet interface.
 * 
 * @author Graham
 */
public interface Packet {

	public void handlePacket(Client client, int packetType, int packetSize);
}
