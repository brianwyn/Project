package com.rs2.content.actions.tasks;

import com.rs2.content.actions.Action;
import com.rs2.content.actions.ActionManager;
import com.rs2.content.actions.Task;
import com.rs2.content.minigames.FightPits;
import com.rs2.content.minigames.LumbridgePk;
import com.rs2.model.player.Client;
import com.rs2.model.player.Player;

/**
 * 
 * @author killamess
 *
 */
public class Skulling implements Task {

	@Override
	public void execute(Action currentAction) {
		currentAction.setActionType(Action.type.LOOPING);
	}

	@Override
	public void loop(Action currentAction) {
		
		currentAction.setCurrentTick(currentAction.getCurrentTick() - 1);
		
		if (currentAction.getCurrentTick() == 0) {
			
			currentAction.setCurrentTick(currentAction.getActionTick());
			
			for (Player player : ActionManager.players) {
				
				if (player == null)
					continue;
				
				Client client = (Client) player;
				if (client.getSkullTimer() < 1) {
					client.skullIcon = -1;
				} else {
					client.setSkullTimer(client.getSkullTimer() - 1);
					client.skullIcon = LumbridgePk.inLumbridgePkArea(client) ? 1 : 0;
				}
				if (FightPits.inFightArea(client) || FightPits.inWaitingArea(client)) {
					client.skullIcon = 1;
				}
				if (client.isViewingOrb())
					client.skullIcon = -1;
			}
		}
	}

	@Override
	public void stop(Action currentAction) {
		currentAction.setActionType(Action.type.TRASHING);	
	}
	
	public static void setSkulled(Client client, Client attacked) {
		if (attacked.skulledOn != client) {
			client.setSkullTimer(1200);
			client.skulledOn = attacked;
			if (LumbridgePk.inLumbridgePkArea(client) || FightPits.inFightArea(client) || FightPits.inWaitingArea(client)) {
				client.skullIcon = 1;
			} else 
				client.skullIcon = 0;
		}
	} 

}
