package com.rs2.content.actions.tasks;

import com.rs2.GameEngine;
import com.rs2.content.actions.Action;
import com.rs2.content.actions.Task;
import com.rs2.content.controllers.Animation;
import com.rs2.model.player.PlayerConstants;

/**
 * 
 * @author killamess
 *
 */
public class CraftHide implements Task {

	@Override
	public void execute(Action currentAction) {
		currentAction.getClient().getActionSender().sendWindowsRemoval();
		currentAction.setCurrentTick(0);
		currentAction.setActionType(Action.type.LOOPING);
	}

	@Override
	public void loop(Action currentAction) {
		if (currentAction.getClient().crafting < 1 || currentAction.getClient().craftingItem < 1 || currentAction.getClient().craftingAmount < 1) {
			stop(currentAction);
			return;
		}
		if (currentAction.getCurrentTick() > 0) {
			currentAction.setCurrentTick(currentAction.getCurrentTick() - 1);
			return;
		}
		currentAction.getClient().craftingAmount--;
		
		for (int i = 0; i < craft.length; i++) {
			if (craft[i][0] == currentAction.getClient().craftingItem) {
				if (currentAction.getClient().playerLevel[PlayerConstants.CRAFTING] < craft[i][3]) {
					currentAction.getClient().getActionSender().sendMessage("You need a crafting level of "+ craft[i][3]+" to craft "+ GameEngine.getItemManager().getItemDefinition(craft[i][0]).getName().toLowerCase()+".");
					stop(currentAction);
					return;	
				}
				if (currentAction.getClient().getActionAssistant().playerHasItem(craft[i][1], craft[i][2])) {
					if (currentAction.getClient().getActionAssistant().playerHasItem(1733, 1)) {
						if (currentAction.getClient().getActionAssistant().playerHasItem(1734, 1)) {
							for (int delete = 0; delete < craft[i][2]; delete++) {
								currentAction.getClient().getActionAssistant().deleteItem(craft[i][1], 1);
							}
							currentAction.getClient().getActionSender().sendInventoryItem(currentAction.getClient().craftingItem, 1);
							currentAction.getClient().getActionAssistant().addSkillXP(craft[i][4] * PlayerConstants.SKILL_EXPERIENCE_MULTIPLIER, PlayerConstants.CRAFTING);
							
							if (GameEngine.getItemManager().getItemDefinition(craft[i][0]).getName().contains("vamb")) {
								currentAction.getClient().craftingThreadCount = (currentAction.getClient().craftingThreadCount + 1);	
							} else if (GameEngine.getItemManager().getItemDefinition(craft[i][0]).getName().contains("chaps")) {
								currentAction.getClient().craftingThreadCount = (currentAction.getClient().craftingThreadCount + 2);
							} else if (GameEngine.getItemManager().getItemDefinition(craft[i][0]).getName().contains("body")) {
								currentAction.getClient().craftingThreadCount = (currentAction.getClient().craftingThreadCount + 3);
							}
							if (currentAction.getClient().craftingThreadCount >= 3) {
								currentAction.getClient().craftingThreadCount = (currentAction.getClient().craftingThreadCount - 3);
								currentAction.getClient().getActionAssistant().deleteItem(1733, 1);
								currentAction.getClient().getActionAssistant().deleteItem(1734, 1);	

							}
						} else {
							currentAction.getClient().getActionSender().sendMessage("You need more thread to craft this item.");
							stop(currentAction);
							return;
						}
					} else {
						currentAction.getClient().getActionSender().sendMessage("You need a needle to craft this item.");
						stop(currentAction);
						return;
					}
				} else {
					currentAction.getClient().getActionSender().sendMessage("You do not have enough "+ GameEngine.getItemManager().getItemDefinition(craft[i][1]).getName().toLowerCase()+" to craft this item.");
					stop(currentAction);
					return;
				}
			}
		}
		Animation.addNewRequest(currentAction.getClient(), 885, 0);
		currentAction.setCurrentTick(2);	
	}

	@Override
	public void stop(Action currentAction) {
		currentAction.getClient().crafting = 0;
		currentAction.getClient().craftingItem = 0;
		currentAction.getClient().craftingAmount = 0;
		currentAction.setActionType(Action.type.TRASHING);
	}
	
	//item, hide type, hides needed, level, experience
	public static final int[][] craft = {
		/* green hide */
		{ 1065, 1745, 1, 57, 62 },//vambraces
		{ 1099, 1745, 2, 60, 124},//chaps
		{ 1135, 1745, 3, 63, 186},//body
		
		/* blue hide */
		{ 2487, 2505, 1, 66, 70 },//vambraces
		{ 2493, 2505, 2, 68, 140},//chaps
		{ 2499, 2505, 3, 71, 210},//body
		
		/* red hide */
		{ 2489, 2507, 1, 73, 78 },//vambraces
		{ 2495, 2507, 2, 75, 156},//chaps
		{ 2501, 2507, 3, 77, 234},//body
		
		/* black hide */
		{ 2491, 2509, 1, 79, 86 },//vambraces
		{ 2497, 2509, 2, 82, 172},//chaps
		{ 2503, 2509, 3, 84, 258},//body

	};

}
