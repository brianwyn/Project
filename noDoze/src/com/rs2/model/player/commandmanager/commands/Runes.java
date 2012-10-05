package com.rs2.model.player.commandmanager.commands;

import com.rs2.model.player.Client;
import com.rs2.model.player.commandmanager.Command;

public class Runes implements Command {

	int[] set = {554,555,556,557,558,559,560,561,562,563,564,565,566};
	
	@Override
	public void execute(Client client, String command) {
		if (client.getPrivileges() >= 3) {
			for (int rune : set)
				client.getActionSender().sendInventoryItem(rune, 999);
		}
	}
}
