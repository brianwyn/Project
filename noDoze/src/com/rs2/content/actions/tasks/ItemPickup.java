package com.rs2.content.actions.tasks;

import com.rs2.content.actions.Action;
import com.rs2.content.actions.ActionManager;
import com.rs2.content.actions.Task;
import com.rs2.content.controllers.Animation;
import com.rs2.model.player.Client;
import com.rs2.model.player.Player;
import com.rs2.tiles.TileManager;

/**
 * 
 * @author killamess
 *
 */

public class ItemPickup implements Task {


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
				
				if (p.pickup[0] < 1)
					continue;
				p.resetFaceDirection();
				if (TileManager.calculateDistance(p.pickup, (Client) p) == 0) {
					((Client) p).getActionAssistant().pickUpItem(p.pickup[0], p.pickup[1], p.pickup[2]);
					for (int pickupReset = 0; pickupReset < p.pickup.length; pickupReset++) {
						p.pickup[pickupReset] = -1;
					}
				} else if (TileManager.calculateDistance(p.pickup, (Client) p) == 1 && ((Client)p).getFreezeDelay() > 0) {
					((Client) p).getActionAssistant().pickUpItem(p.pickup[0], p.pickup[1], p.pickup[2]);
					Animation.addNewRequest((Client) p, 832, 1);
					for (int pickupReset = 0; pickupReset < p.pickup.length; pickupReset++) {
						p.pickup[pickupReset] = -1;
					}
				}
			}
		}
	}

	@Override
	public void stop(Action currentAction) {
		currentAction.setActionType(Action.type.TRASHING);
	}
}
