package com.rs2.model.player.commandmanager.commands;

import com.rs2.market.Market;
import com.rs2.model.player.Client;
import com.rs2.model.player.commandmanager.Command;

public class MyMarket implements Command {

	@Override
	public void execute(Client client, String command) {
		Market.MyMarket(client);
	}

}
