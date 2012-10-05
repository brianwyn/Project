package com.rs2.content.actions.tasks;

import com.rs2.content.actions.Action;
import com.rs2.content.actions.ActionManager;
import com.rs2.content.actions.Task;
import com.rs2.model.combat.content.Specials;
import com.rs2.model.player.Client;
import com.rs2.model.player.Player;
import com.rs2.world.PlayerManager;

/**
 * 
 * @author Killamess
 *
 */

public class SpecialRegain implements Task {

	private static final int REPEAT_DELAY = 40;
	
	@Override
	public void execute(Action currentAction) {
		currentAction.setActionTick(1);
		currentAction.setActionType(Action.type.LOOPING);
	}

	@Override
	public void loop(Action currentAction) {
		
		currentAction.setActionTick(currentAction.getActionTick() - 1);
		
		if (currentAction.getActionTick() == 0) {
		
			currentAction.setActionTick(REPEAT_DELAY);
			
			for (Player p : PlayerManager.getSingleton().getPlayers()) {
				
				if (p == null)
					continue;
				
				Client client = (Client) p;
				
				if (client.specialAmount >= 100)
					continue;
				
				int extraAmount = 0;
				
				switch(client.privileges) {
				case 1:
					extraAmount = 5;
					break;
				case 2: 
					extraAmount = 10;
					break;
				case 3:
					extraAmount = 15;
					break;
				}
				client.specialAmount += (10 + extraAmount);
				
				if (client.getSpecialAmount() > 100)
					client.setSpecialAmount(100);
				
				Specials.updateSpecialBar(client);	
			}
		}
	}

	@Override
	public void stop(Action currentAction) {
		ActionManager.removeAction(currentAction);	
	}

}
