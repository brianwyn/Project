package com.rs2.model.player.commandmanager.commands;

import com.rs2.model.player.Client;
import com.rs2.model.player.PlayerConstants;
import com.rs2.model.player.commandmanager.Command;

public class ResetAll implements Command {

	@Override
	public void execute(Client client, String command) {
		if (client.getPrivileges() >= 3) {
			for (int skill = 0; skill < PlayerConstants.MAX_SKILLS; skill++) {
				client.playerLevel[skill] = 1;
				client.playerXP[skill] = 0;
				client.getActionAssistant().refreshSkill(skill);
			}
		}	
	}
}
