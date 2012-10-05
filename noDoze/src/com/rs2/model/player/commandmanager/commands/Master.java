package com.rs2.model.player.commandmanager.commands;

import com.rs2.model.player.Client;
import com.rs2.model.player.PlayerConstants;
import com.rs2.model.player.commandmanager.Command;

/** 
 * 
 * @author Killamess
 *  Just a max stat command.
 *
 */
public class Master implements Command {

	@Override
	public void execute(Client client, String command) {
		if (client.getPrivileges() >= 2) {
			for (int skill = 0; skill < PlayerConstants.MAX_SKILLS; skill++) {
				client.playerLevel[skill] = client.getLevelForXP(14000000);
				client.playerXP[skill] = 14000000;
				client.getActionAssistant().refreshSkill(skill);
			}
		}
	}

}
