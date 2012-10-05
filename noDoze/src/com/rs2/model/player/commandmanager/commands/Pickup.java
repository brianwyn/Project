package com.rs2.model.player.commandmanager.commands;

import com.rs2.model.Item;
import com.rs2.model.player.Client;
import com.rs2.model.player.commandmanager.Command;

/**
 * Pickup command
 * 
 * @author Graham
 */
public class Pickup implements Command {

	@Override
	public void execute(Client client, String command) {
		if (client.getPrivileges() >= 3) {
			String[] parts = command.split(" ");
			try {
				int item = Integer.valueOf(parts[1]);
				int amount = Integer.valueOf(parts[2]);
				
				if (!Item.itemStackable[item] && amount > 1) {
					
					if (amount > 28) 
						amount = 28;
					
					for (int i = 0; i < amount; i ++) 
						client.getActionSender().sendInventoryItem(item, 1);
				} else 
					client.getActionSender().sendInventoryItem(item, amount);
				
			} catch (Exception e) {
				client.getActionSender().sendMessage(
						"Syntax is ::pickup <id> <amount>.");
			}
		} 
	}

}
