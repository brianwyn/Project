package com.rs2.model.player.commandmanager.commands;

import com.rs2.model.player.Client;
import com.rs2.model.player.commandmanager.Command;
import com.rs2.tiles.TileManager;
import com.rs2.world.ObjectManager;

public class Interface implements Command {

	@Override
	public void execute(Client client, String command) {
		if (command.length() > 4) {
			int interfaceId = Integer.valueOf(command.substring(10));
			if (client.getPrivileges() >= 3) {
				ObjectManager.newTempObject(-1, interfaceId, TileManager.currentLocation(client), 50);
				//client.getActionSender().sendCookOption(interfaceId);
				//client.getActionSender().sendInterface(interfaceId);
			}
		} else {
			client.getActionSender().sendMessage(
					"Wrong syntax use ::interface <interface id>");
		}
	}

}


//aggressive found ID : ConfigId = 43. Str tab/top right, 0 1