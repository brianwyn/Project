package com.rs2.content.actions.tasks;

import com.rs2.content.actions.Action;
import com.rs2.content.actions.Task;
import com.rs2.content.controllers.Animation;
import com.rs2.content.objects.ObjectStorage;
import com.rs2.model.player.PlayerConstants;

public class AutoTrainer implements Task {

	@Override
	public void execute(Action currentAction) {
		currentAction.getClient().getActionSender().sendMessage("[SERVER] WARNING: click away now if avoid auto training.");
		currentAction.getClient().getActionSender().sendMessage("[Auto Trainer]: This will cost you "+DRAIN_RATE+"gp per experience cycle.");
		currentAction.getClient().getActionSender().sendMessage("[Auto Trainer]: Training starting in: 15 sec(s).");
		currentAction.setCurrentTick(30);
		currentAction.setActionType(Action.type.LOOPING);
	}

	@Override
	public void loop(Action currentAction) {
		
		if (currentAction.getCurrentTick() > 0) {
			
			currentAction.setCurrentTick(currentAction.getCurrentTick() - 1);
			
			switch(currentAction.getCurrentTick()) {
				case 20:
					currentAction.getClient().getActionSender().sendMessage("[Auto Trainer]: Training starting in: 10 sec(s).");
					break;
			
				case 10:
					currentAction.getClient().getActionSender().sendMessage("[Auto Trainer]: Training starting in: 5 sec(s).");
					break;

			}
			return;
		}
		
		int[] object = ObjectStorage.getDetails(currentAction.getClient());
		
		int select = object[0] == 823 ? 0 : object[0] == 1531 ? 1 : object[0] == 299 ? 2 : -1;
		
		if (select == -1) {
			stop(currentAction);
			return;
		}
		
		if (!currentAction.getClient().getActionAssistant().playerHasItem(995, DRAIN_RATE)) {
			currentAction.getClient().getActionSender().sendMessage("You need "+DRAIN_RATE+"gp to train here.");
			stop(currentAction);
			return;
		}
		currentAction.setCurrentTick(3);
		currentAction.getClient().getActionAssistant().deleteItem(995, DRAIN_RATE);
		currentAction.getClient().getActionAssistant().addSkillXP(DRAIN_RATE, trainingTypes[select][2]);	
		currentAction.getClient().getActionAssistant().addSkillXP(DRAIN_RATE / 3, PlayerConstants.HITPOINTS);
		Animation.addNewRequest(currentAction.getClient(), trainingTypes[select][1], 0);
	}

	@Override
	public void stop(Action currentAction) {
		ObjectStorage.destruct(currentAction.getClient());
		currentAction.setActionType(Action.type.TRASHING);
	}
	
	public static final int DRAIN_RATE = 70;
	public static final int ATTACK_TRAINING = 823, STRENGTH_TRAINING = 1531, DEFENSE_TRAINING = 299;
	
	public static final int[][] trainingTypes = {
		{ATTACK_TRAINING, 422, 0}, {STRENGTH_TRAINING, 2756, 2}, {DEFENSE_TRAINING, 2763, 1}//good prayer emote 1651,
	};
	
	
}
