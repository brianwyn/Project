package com.rs2.content.skills.cooking;

import com.rs2.content.actions.Action;
import com.rs2.content.actions.Action.type;
import com.rs2.content.actions.Task;
import com.rs2.content.controllers.Animation;
import com.rs2.content.objects.ObjectStorage;
import com.rs2.model.player.Client;
import com.rs2.model.player.PlayerConstants;
import com.rs2.util.Misc;
import com.rs2.world.XMLManager;
import com.rs2.world.XMLManager.Cooking;

/**
 * 
 * @author killamess
 *
 */
public class Cooker implements Task {
	
	@Override
	public void execute(Action currentAction) {
		
		Client client = currentAction.getClient();
			
		if (client == null) {
			stop(currentAction);
			return;
		}
		if (client.cooking <= 0 || client.cookingAmount <= 0 || client.cookingAnimation <= 0) {
			stop(currentAction);
			return;
		}
		if (rawInput(client.cooking) != null) {
			if (rawInput(client.cooking).getLevel() > client.playerLevel[PlayerConstants.COOKING]) {
				client.getActionSender().sendMessage("You need a cooking level of "+ rawInput(client.cooking).getLevel() +" to cook this.");
				stop(currentAction);
				return;
			}
		}
		int[] object = ObjectStorage.getDetails(currentAction.getClient());
		client.objectID = object[0];
		client.objectX = object[2];			
		client.objectY = object[3];
		client.objectSize = object[1];
		client.setPlayerX = client.getAbsX();
		client.setPlayerY = client.getAbsY();
		if (client.objectSize > 1) {
			client.getActionAssistant().turnTo(client.objectX + client.objectSize / 2 , client.objectY + client.objectSize / 2);
		} else {
			client.getActionAssistant().turnTo(client.objectX, client.objectY);
		}
		Animation.addNewRequest(client, client.cookingAnimation, 0);
		currentAction.setActionType(Action.type.LOOPING);
	}

	@Override
	public void loop(Action currentAction) {
		
		currentAction.setCurrentTick(currentAction.getCurrentTick() - 1);
		
		if (currentAction.getCurrentTick() > 1)
			return;
			
		Client client = currentAction.getClient();
		
		if (client == null) {
			stop(currentAction);
			return;
		}
		if (client.cookingAmount > 0) {
			client.cookingAmount--;
		} else {
			stop(currentAction);
			return;
		}		
		int[] object = ObjectStorage.getDetails(client);
		client.objectID = object[0];
		client.objectX = object[2];			
		client.objectY = object[3];
		client.objectSize = object[1];
		client.setPlayerX = client.getAbsX();
		client.setPlayerY = client.getAbsY();
		
		if (client.objectSize > 1)
			client.getActionAssistant().turnTo(client.objectX + client.objectSize / 2 , client.objectY + client.objectSize / 2);
		else
			client.getActionAssistant().turnTo(client.objectX, client.objectY);
		
		if (rawInput(client.cooking) != null) {
			if (client.getActionAssistant().isItemInBag(rawInput(client.cooking).getRawType())) {
				client.getActionAssistant().deleteItem(rawInput(client.cooking).getRawType(), 1);
				if (generateCookSuccess(client.playerLevel[PlayerConstants.COOKING], rawInput(client.cooking).getLevel())) {
					client.getActionSender().sendInventoryItem(rawInput(client.cooking).getCookedType(), 1);
					client.getActionAssistant().addSkillXP(rawInput(client.cooking).getXp() * PlayerConstants.SKILL_EXPERIENCE_MULTIPLIER, PlayerConstants.COOKING);
				} else {
					client.getActionSender().sendInventoryItem(rawInput(client.cooking).getBurntType(), 1);
				}
			} else {
				stop(currentAction);
				return;
			}	
		}
		Animation.addNewRequest(client, client.cookingAnimation, 0);
		currentAction.setCurrentTick(4);
	}

	@Override
	public void stop(Action currentAction) {
		if (currentAction.getClient() != null) {
			currentAction.getClient().cooking = 0;
			currentAction.getClient().cookingAmount = 0;
			currentAction.getClient().cookingAnimation = 0;	
		}
		currentAction.setActionType(type.TRASHING); 
	}
	
	public Cooking rawInput(int id) {
		for (Cooking c : XMLManager.ingredients) {
			if (c.getRawType() == id) 
				return c;
		}
		return null;
	}
	
	public boolean generateCookSuccess(int level, int levelReq) {
		if (level - 20 > levelReq)
			return true;
		if (level - 15 > levelReq)
			return Misc.random(5) != 0;
		if (level - 10 > levelReq)
			return Misc.random(4) != 0;
		if (level - 5 > levelReq)
			return Misc.random(3) != 0;
		return Misc.random(2) != 0;
	}
}
