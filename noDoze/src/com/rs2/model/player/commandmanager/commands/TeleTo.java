package com.rs2.model.player.commandmanager.commands;

import com.rs2.content.controllers.Location;
import com.rs2.model.player.Client;
import com.rs2.model.player.commandmanager.Command;

public class TeleTo implements Command {

		@Override
		public void execute(Client client, String command) {
			
			if (client.getPrivileges() >= 2) {
				
				String[] parts = command.split(" ");
				
				try {
					
					int xCord = Integer.valueOf(parts[1]);
					int yCord = Integer.valueOf(parts[2]);
					
					Location.addNewRequest(client, xCord, yCord, 0, 1);
					
				} catch (Exception e) {}
			}
				
		}

		

}
