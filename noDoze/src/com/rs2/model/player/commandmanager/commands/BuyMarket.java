package com.rs2.model.player.commandmanager.commands;

import com.rs2.market.Market;
import com.rs2.model.player.Client;
import com.rs2.model.player.commandmanager.Command;

public class BuyMarket implements Command {

	@Override
	public void execute(Client client, String command) {
		String market[] = command.replace("buymarket", "").split(" ");
		if(command.length() > 2) {
		Market.BuyMarket(client, Integer.parseInt(market[0]), Integer.parseInt(market[1]));
		} else {
			client.getActionSender().sendMessage("Syntax Error Example: ::buymarket whip");
		}
	}

}
