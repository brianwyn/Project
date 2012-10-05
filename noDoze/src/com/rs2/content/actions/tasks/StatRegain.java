package com.rs2.content.actions.tasks;

import com.rs2.content.actions.Action;
import com.rs2.content.actions.ActionManager;
import com.rs2.content.actions.Task;
import com.rs2.model.player.Client;
import com.rs2.model.player.Player;
import com.rs2.model.player.PlayerConstants;

/**
 * 
 * @author Killamess
 *
 */

public class StatRegain implements Task {
	
	@Override
	public void execute(Action currentAction) {
		currentAction.setActionType(Action.type.LOOPING);
	}
	
	@Override
	public void loop(Action currentAction) {
		
		currentAction.setCurrentTick(currentAction.getCurrentTick() - 1);
		
		if (currentAction.getCurrentTick() == 0) {
			
			currentAction.setCurrentTick(currentAction.getActionTick());
		
			for (Player p : ActionManager.players) {
			
				if (p == null)
					continue;
			
				Client client = (Client) p;
			
				for (int i = 0; i < PlayerConstants.MAX_SKILLS; i++) {
					if (client.playerLevel[i] > client.getLevelForXP(client.playerXP[i])) {
						client.playerLevel[i]--;
					}
					if (i == 3) {
						
						if (client.hitpoints < client.getLevelForXP(client.playerXP[i])) {
							if (client.getPrayerHandler().clicked[6]) {
								client.getActionAssistant().addHP(1);
							}
							client.getActionAssistant().addHP(1);
						}
							
					} else if (client.playerLevel[i] < client.getLevelForXP(client.playerXP[i])) {
						if (i == 5) {
							continue;
						}
						client.playerLevel[i]++;
					}
					client.getActionAssistant().refreshSkill(i);
				}
			}
		}
	}
	
	@Override
	public void stop(Action currentAction) {
		currentAction.setActionType(Action.type.TRASHING);
	}
}
