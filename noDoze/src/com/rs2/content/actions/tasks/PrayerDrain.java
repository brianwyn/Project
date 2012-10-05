package com.rs2.content.actions.tasks;

import com.rs2.content.actions.Action;
import com.rs2.content.actions.ActionManager;
import com.rs2.content.actions.Task;
import com.rs2.model.player.Client;
import com.rs2.model.player.Player;

/**
 * 
 * @author Killamess
 *
 */

public class PrayerDrain implements Task {
	
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
				Client client = (Client) player;
				client.getPrayerHandler().prayerEvent();
			}
		}
	}

	@Override
	public void stop(Action currentAction) {
		currentAction.setActionType(Action.type.TRASHING);
	}
}
