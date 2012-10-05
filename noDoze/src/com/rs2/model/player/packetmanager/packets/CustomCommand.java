package com.rs2.model.player.packetmanager.packets;

import com.rs2.model.player.Client;
import com.rs2.model.player.commandmanager.CommandManager;
import com.rs2.model.player.packetmanager.Packet;

/**
 * Handles custom commands
 * 
 * @author Graham
 */
public class CustomCommand implements Packet {

	@Override
	public void handlePacket(Client client, int packetType, int packetSize) {
		String playerCommand = client.inStream.readString();
		client.println_debug("playerCommand: " + playerCommand);
		CommandManager.execute(client, playerCommand);
	}

}
