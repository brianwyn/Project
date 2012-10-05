package com.rs2.model.player.commandmanager.commands;


import com.rs2.market.Market;
import com.rs2.model.player.Client;
import com.rs2.model.player.commandmanager.Command;

public class AddMarket implements Command {

	@Override
	public void execute(Client client, String command) {
		String market[] = command.replace("addmarket ", "").split(" ");
		if(market.length == 3) {
			try {
				Market.AddItem(client, Integer.parseInt(market[0]), Integer.parseInt(market[1]), Integer.parseInt(market[2]));
			} catch (Exception e) {
				client.getActionSender().sendMessage("Error adding to market please contact steven");
				e.printStackTrace();
			}
		} else {
			client.getActionSender().sendMessage("Syntax error example(itemid amount price): ::addmarket 4151 1 1200000");
		}
	}

}
