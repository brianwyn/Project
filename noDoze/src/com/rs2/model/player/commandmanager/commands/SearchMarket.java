package com.rs2.model.player.commandmanager.commands;

import com.rs2.market.Market;
import com.rs2.model.player.Client;
import com.rs2.model.player.commandmanager.Command;

public class SearchMarket implements Command {

	@Override
	public void execute(Client client, String command) {
		if(command.length() > 13) {
		Market.SearchMarket(client, command.substring(13));
		} else {
			client.getActionSender().sendMessage("Syntax Error Example: ::searchmarket whip");
		}
	}

}
