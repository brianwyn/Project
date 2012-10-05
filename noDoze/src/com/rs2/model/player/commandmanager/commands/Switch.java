package com.rs2.model.player.commandmanager.commands;

import com.rs2.model.combat.magic.AutoCast;
import com.rs2.model.player.Client;
import com.rs2.model.player.commandmanager.Command;

/**
 * Switch command.
 * 
 * @author Anthony
 */

public class Switch implements Command {

	@Override
	public void execute(Client client, String command) {
		if (client.getPrivileges() >= 3) {
			client.convertMagic();
			AutoCast.turnOff(client);
		} else {
			client.getActionSender().sendMessage(
					"You do not have the correct rights to use this command.");
		}
	}

}
