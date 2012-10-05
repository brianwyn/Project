package com.rs2.model.player.commandmanager.commands;

import com.rs2.model.combat.content.Specials;
import com.rs2.model.player.Client;
import com.rs2.model.player.commandmanager.Command;


public class Special implements Command {

	public void execute(Client client, String command) {
		if (client.getPrivileges() >= 3) {
			client.setSpecialAmount(100);
			Specials.updateSpecialBar(client);
		}
	}
}
