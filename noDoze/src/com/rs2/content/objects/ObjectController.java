package com.rs2.content.objects;

import com.rs2.GameEngine;
import com.rs2.content.JailSystem;
import com.rs2.content.actions.Action;
import com.rs2.content.actions.ActionManager;
import com.rs2.content.actions.Task;
import com.rs2.content.controllers.Animation;
import com.rs2.content.controllers.Damage;
import com.rs2.content.controllers.Location;
import com.rs2.content.minigames.FightPits;
import com.rs2.content.objects.ObjectConstants;	
import com.rs2.content.objects.ObjectStorage;
import com.rs2.content.skills.crafting.GemCrafting;
import com.rs2.content.skills.runecrafting.RuneCrafting;
import com.rs2.model.Entity.CombatType;
import com.rs2.model.WorldObject;
import com.rs2.model.WorldObject.Face;
import com.rs2.model.player.Client;
import com.rs2.model.player.Language;
import com.rs2.model.player.PlayerConstants;
import com.rs2.util.Misc;

/**
 * Object Controller.
 * 
 * Assigns the clicked object to its action.
 *
 * @author Killamess.
 */

public class ObjectController implements Task {
	
	public static void runAction(Client client) {
		
		if (System.currentTimeMillis() - client.actionTimer < 500 || client.isBusy()) {
			return;
		} else {
			client.actionTimer = System.currentTimeMillis();
		}
		int[] object = ObjectStorage.getDetails(client);
		if (object[1] > 1 && ObjectStorage.getDetail(client, ObjectConstants.OBJECT_TASK) != ObjectConstants.RUNE_CRAFT) {
			client.getActionAssistant().turnTo(object[2] + object[1] / 2, object[3] + object[1] / 2);
		} else {
			client.getActionAssistant().turnTo(object[2], object[3]);
		}
		int task = ObjectStorage.getDetail(client, ObjectConstants.OBJECT_TASK);
		if (GameEngine.getObjectManager().getDefinition(object[0]) != null) {
			if (GameEngine.getObjectManager().getDefinition(object[0]).getName().contains("Door") ||
					GameEngine.getObjectManager().getDefinition(object[0]).getName().contains("Gate")) {
				task = ObjectConstants.DOOR;
			}
		}
		
		switch(task) {
		case ObjectConstants.DOOR:
			
			GameEngine.getObjectManager().addObject(new WorldObject(-1, object[2], object[3], 0, Face.NORTH, 0));
			break;
		case ObjectConstants.TREE:
			ActionManager.addNewRequest(client, 0, 4);			
			break;
			
		case ObjectConstants.NO_ORE:
			client.getActionSender().sendMessage("This rock does not contain any ore.");
			break;
			
		case ObjectConstants.BANK:
			client.getActionSender().sendBankInterface();
			break;
			
		case ObjectConstants.MINE:
			ActionManager.addNewRequest(client, 10, 4);
			break;
			
		case ObjectConstants.GEM_CRAFT:
		case ObjectConstants.ORE_SMELTING:
			if (client.oreId > 0) {
				if (client.getActionAssistant().isItemInBag(444)) {
					ActionManager.addNewRequest(client, 9, 2);
					break;
				}
			}
			if (client.getActionAssistant().isItemInBag(1592) || client.getActionAssistant().isItemInBag(1595) || client.getActionAssistant().isItemInBag(1597)) {
				GemCrafting.openInterface(client);
			}
			break;
			
		case ObjectConstants.RUNE_CRAFT:
			switch(object[0]) {
			case 7139: RuneCrafting.craftRunesOnAltar(client, 1, 5, 556, 30, 45, 60, 1438);//air 2478 
				break;
			case 7137: RuneCrafting.craftRunesOnAltar(client, 5, 6, 555, 30, 45, 60, 1444); 
				break;
			case 7130: RuneCrafting.craftRunesOnAltar(client, 9, 7, 557, 45, 55, 65, 1440); 
				break;
			case 7129: RuneCrafting.craftRunesOnAltar(client, 14, 7, 554, 50, 60, 70, 1442);
				break;
			case 7140: RuneCrafting.craftRunesOnAltar(client, 20, 8, 559, 55, 65, 75, 1454); 
				break;
			case 7134: RuneCrafting.craftRunesOnAltar(client, 35, 9, 562, 60, 70, 80, 1452);
				break;
			case 7133: RuneCrafting.craftRunesOnAltar(client, 44, 9, 561, 60, 74, 91, 1462); 
				break;
			case 7135: RuneCrafting.craftRunesOnAltar(client, 54, 10, 563, 65, 79, 93, 1458); 
				break;
			case 7136: RuneCrafting.craftRunesOnAltar(client, 65, 10, 560, 72, 84, 96, 1456); 
				break;
			case 7141: RuneCrafting.craftRunesOnAltar(client, 77, 11, 565, 89, 94, 99, 1450); //blood 
				break;
			default:
				client.getActionSender().sendMessage("Available at this time.");
				break;
			}
			break;

		case ObjectConstants.COOKING_FURNACE:
			client.cookingAnimation = 883;
			client.getActionSender().sendCookOption(client.cooking);		
			break;
			
		case ObjectConstants.LOG_FIRE:
			client.cookingAnimation = 883;
			client.getActionSender().sendCookOption(client.cooking);		
			break;
			
		case ObjectConstants.STR_DOOR:
		case ObjectConstants.HAY_STACK:
		case ObjectConstants.DUMMY:
			ActionManager.addNewRequest(client, 7, 4);		
			break;
			
		case ObjectConstants.STEAL_CAKE:
			if (client.getActionAssistant().freeSlots() == 0) 
				break;
			int[] food = {1891, 1897, 2309};
			int randomFood = Misc.random(food.length -1);
			client.getActionSender().sendInventoryItem(food[randomFood], 1);
			client.getActionSender().sendMessage("You manage to steal some "+GameEngine.getItemManager().getItemDefinition(food[randomFood]).getName().toLowerCase()+".");
			client.getActionAssistant().addSkillXP(20 * PlayerConstants.SKILL_EXPERIENCE_MULTIPLIER, PlayerConstants.THIEVING);
			client.setBusyTimer(4);
			Animation.addNewRequest(client, 881, 0);
			break;
			
		case ObjectConstants.STEAL_GEM:
			if (client.getActionAssistant().freeSlots() == 0) 
				break;
			if (client.playerLevel[PlayerConstants.THIEVING] < 75) {
				client.getActionSender().sendMessage("You need a Thieving level of 75 to steal from the Gem stall.");
				return;
			}
			int[] gem = {1755,1623,1621,1619,1617};
			int randomGem = Misc.random(gem.length - 1);
			client.getActionSender().sendInventoryItem(gem[randomGem], 1);
			client.getActionSender().sendMessage("You manage to steal a "+GameEngine.getItemManager().getItemDefinition(gem[randomGem]).getName().toLowerCase()+".");
			client.getActionAssistant().addSkillXP(150 * PlayerConstants.SKILL_EXPERIENCE_MULTIPLIER, PlayerConstants.THIEVING);
			client.setBusyTimer(4);
			Animation.addNewRequest(client, 881, 0);
			break;
			
		case ObjectConstants.STEAL_SILVER:
			if (client.getActionAssistant().freeSlots() == 0) 
				break;
			if (client.playerLevel[PlayerConstants.THIEVING] < 45) {
				client.getActionSender().sendMessage("You need a Thieving level of 45 to steal from the Sliver stall.");
				return;
			}
			int[] silver = {442,2355,2355};
			int randomSilver = Misc.random(silver.length - 1);
			client.getActionSender().sendInventoryItem(silver[randomSilver], 1);
			client.getActionSender().sendMessage("You manage to steal a "+GameEngine.getItemManager().getItemDefinition(silver[randomSilver]).getName().toLowerCase()+".");
			client.getActionAssistant().addSkillXP(80 * PlayerConstants.SKILL_EXPERIENCE_MULTIPLIER, PlayerConstants.THIEVING);
			client.setBusyTimer(4);
			Animation.addNewRequest(client, 881, 0);
			break;
			
		case ObjectConstants.STEAL_SILK:
			if (client.getActionAssistant().freeSlots() == 0) 
				break;
			if (client.playerLevel[PlayerConstants.THIEVING] < 25) {
				client.getActionSender().sendMessage("You need a Thieving level of 25 to steal from the Silk stall.");
				return;
			}
			client.getActionSender().sendInventoryItem(950, 1);
			client.getActionSender().sendMessage("You manage to steal some "+GameEngine.getItemManager().getItemDefinition(950).getName().toLowerCase()+".");
			client.getActionAssistant().addSkillXP(40 * PlayerConstants.SKILL_EXPERIENCE_MULTIPLIER, PlayerConstants.THIEVING);
			client.setBusyTimer(4);
			Animation.addNewRequest(client, 881, 0);
			break;
			
		case ObjectConstants.STEAL_FUR:
			int[] fur = {958,948};
			int randomFur = Misc.random(fur.length - 1);
			if (client.getActionAssistant().freeSlots() == 0) 
				break;
			if (client.playerLevel[PlayerConstants.THIEVING] < 30) {
				client.getActionSender().sendMessage("You need a Thieving level of 30 to steal from the Fur stall.");
				return;
			}
			client.getActionSender().sendInventoryItem(fur[randomFur], 1);
			client.getActionSender().sendMessage("You manage to steal some "+GameEngine.getItemManager().getItemDefinition(fur[randomFur]).getName().toLowerCase()+".");
			client.getActionAssistant().addSkillXP(50 * PlayerConstants.SKILL_EXPERIENCE_MULTIPLIER, PlayerConstants.THIEVING);
			client.setBusyTimer(4);
			Animation.addNewRequest(client, 881, 0);
			break;
			
		case ObjectConstants.FLAX:
			if (client.getActionAssistant().freeSlots() == 0) 
				break;
			client.getActionSender().sendInventoryItem(1779, 1);
			client.getActionSender().sendMessage("You pick some flax.");
			Animation.addNewRequest(client, 827, 0);
			break;
			
		case ObjectConstants.MOULD_CRATE:
			if (!client.getActionAssistant().isItemInBag(1592)) {
				client.getActionSender().sendMessage("You found an Ring mould.");
				client.getActionSender().sendInventoryItem(1592, 1);
				break;
			} else if (!client.getActionAssistant().isItemInBag(1597)) {
				client.getActionSender().sendMessage("You found an Necklace mould.");
				client.getActionSender().sendInventoryItem(1597, 1);
				break;
			} else {
				client.getActionSender().sendMessage("You find nothing of interest.");
				break;		
			}
			
		case ObjectConstants.JUG:
			if (!client.getActionAssistant().isItemInBag(ObjectConstants.JUG)) {
				break;
			} else if (client.getActionAssistant().isItemInBag(ObjectConstants.JUG)) {
				
				Animation.addNewRequest(client, 894, 2);
				client.getActionAssistant().deleteItem(ObjectConstants.JUG, 1);
				client.getActionSender().sendInventoryItem(1937, 1);
				client.getActionSender().sendMessage("You fill the jug.");
				break;
			}
			
		case ObjectConstants.DEPOSIT_BOX:
			client.getActionSender().sendMessage("The Deposit system currently is down.");
			break;

		case ObjectConstants.MAGIC_STAIR_CASE_1_DOWN:
			Location.addNewRequest(client, 3366, 3306, 0, 2);
			break;
			
		case ObjectConstants.MAGIC_STAIR_CASE_1_UP:
			if (client.playerLevel[6] < 70) {
				client.getActionSender().sendMessage("You need a Magic level of 90 to go up these stairs.");
				break;
			}
			Location.addNewRequest(client, 3369, 3307, 1, 2);
			break;
			
		case ObjectConstants.MAGIC_STAIR_CASE_2_DOWN:
			Location.addNewRequest(client, 3360, 3306, 0, 2);
			break;
			
		case ObjectConstants.MAGIC_STAIR_CASE_2_UP:
			if (client.playerLevel[6] < 90) {
				client.getActionSender().sendMessage("You need a Magic level of 90 to go up these stairs.");
				break;
			}
			Location.addNewRequest(client, 3357, 3307, 1, 2);
			break;
			
		case ObjectConstants.JAIL_EXIT:
			if (client.getAbsY() == 3407 && !JailSystem.inJail(client)) {
				client.forceMove = true;
				client.forceMovement[0] = client.getAbsX();
				client.forceMovement[1] = client.getAbsY() + 1;
				client.forceMovement[2] = client.getHeightLevel();
			}
			break;
			
		case ObjectConstants.ALTAR:
		case ObjectConstants.CHAOS_ALTAR:
			if(client.playerLevel[5] == client.getLevelForXP(client.playerXP[5])) {
				client.getActionSender().sendMessage("You already have full prayer points.");
			} else {
				Animation.addNewRequest(client, 645, 1);
				client.playerLevel[5] = client.getLevelForXP(client.playerXP[5]);
				client.getActionSender().sendQuest("" + client.playerLevel[PlayerConstants.PRAYER] + "", 4012);
				client.getActionSender().sendMessage("You recharge your prayer points.");
			}  
			break;
			
		case ObjectConstants.ANCIENT_ALTAR:
			Animation.addNewRequest(client, 645, 1);
			client.convertMagic();
			break;
			
		case ObjectConstants.MAGIC_DOOR:
			if (client.getAbsX() == 3363 && client.getAbsY() == 3300) {
				client.setBusyTimer(4);
				client.resetWalkingQueue();
				client.forceMove = true;
				client.forceMovement[0] = 3363;
				client.forceMovement[1] = 3298;
				client.forceMovement[2] = client.getHeightLevel();
			} else if (client.getAbsX() == 3363 && client.getAbsY() == 3298) {
				client.setBusyTimer(4);
				client.resetWalkingQueue();
				client.forceMove = true;
				client.forceMovement[0] = 3363;
				client.forceMovement[1] = 3300;
				client.forceMovement[2] = client.getHeightLevel();
			}
			break;
			
		case ObjectConstants.FIGHT_PIT_WAITING_ROOM:
			if (client.getAbsX() == 2399 && client.getAbsY() == 5177) {
				client.setBusyTimer(3);
				client.resetWalkingQueue();
				client.forceMove = true;
				client.forceMovement[0] = 2399;
				client.forceMovement[1] = 5175;
				client.forceMovement[2] = client.getHeightLevel();
				
			} else if (client.getAbsX() == 2399 && client.getAbsY() == 5175) {
				client.setBusyTimer(3);
				client.resetWalkingQueue();
				client.forceMove = true;
				client.forceMovement[0] = 2399;
				client.forceMovement[1] = 5177;
				client.forceMovement[2] = client.getHeightLevel();
				
			}
			break;
			
		case ObjectConstants.FIGHT_PIT_WAITING_ROOM2:
			if (client.getAbsX() == 2399 && client.getAbsY() == 5169) {
				client.getActionSender().sendMessage("The next match starts in: " + FightPits.nextRoundDelay / 2 +" seconds.");
			} else if (client.getAbsX() == 2399 && client.getAbsY() == 5167) {
				client.setBusyTimer(3);
				client.resetWalkingQueue();
				client.forceMove = true;
				client.forceMovement[0] = 2399;
				client.forceMovement[1] = 5169;
				client.forceMovement[2] = client.getHeightLevel();
				client.getActionSender().sendMessage("You have left the match early, You do not receive any rewards.");
			}
			break;	
			
		case ObjectConstants.KARAMJA_ROPE_DOWN:
			Location.addNewRequest(client, 2856, 9568, 0, 1);
			break;
			
		case ObjectConstants.KARAMJA_ROPE_UP:
			Location.addNewRequest(client, 2857, 3167, 0, 1);
			break;	
			
		case ObjectConstants.TZ_HAAR_ENTRY:
			Location.addNewRequest(client, 2480, 5175, 0, 1);
			break;
			
		case ObjectConstants.TZ_HAAR_EXIT:
			Location.addNewRequest(client, 2862, 9572, 0, 1);
			break;	
			
		case ObjectConstants.VIEWING_ORB:
			client.setBusy(true);
			client.getActionSender().sendSidebar(10, 3209);
			client.getActionSender().sendFrame106(10);
			FightPits.fightPitsOrb("Centre", 15239, client);
			client.teleportToX = 2398;
			client.teleportToY = 5150;
			client.setHeightLevel(0);
			client.npcID = 1666;
			client.isNPC = true;
			client.setViewingOrb(true);
			client.updateRequired = true;
			client.appearanceUpdateRequired = true;
			break;
			
		case ObjectConstants.FIRST_DUNGEON_EXIT: //going up ladder
			Location.addNewRequest(client, 3268, 3401, 0, 2);
			Animation.addNewRequest(client, 828, 0);
			break;
			
		case ObjectConstants.FIRST_DUNGEON_ENTRY: //going down trapdoor.
			Location.addNewRequest(client, 2628, 5072, 0, 3);
			Animation.addNewRequest(client, 770, 1);
			client.getActionSender().sendMessage("You slip and fall in.");
			Animation.addNewRequest(client, 3103, 3);
			Damage.addNewHit(client, client, CombatType.RECOIL, 5, 3);
			break;
			
		case ObjectConstants.TEA_STALL:
			if (client.getActionAssistant().freeSlots() < 1) {
				client.getActionSender().sendMessage(Language.NO_SPACE);
				break;
			}
			client.setBusyTimer(4);
			Animation.addNewRequest(client, 881, 0);
			client.getActionSender().sendMessage("You take some tea from the stall.");
			client.getActionSender().sendInventoryItem(1978, 1);
			client.getActionAssistant().addSkillXP(5 * PlayerConstants.SKILL_EXPERIENCE_MULTIPLIER, PlayerConstants.THIEVING);
			break;
			
		case ObjectConstants.CHICKEN_DOOR1:
		case ObjectConstants.CHICKEN_DOOR2:
			client.setBusyTimer(2);
			client.resetWalkingQueue();
			client.forceMove = true;
			if (client.getAbsX() == 3264) {
				client.forceMovement[0] = client.getAbsX() - 1;
			} else {
				client.forceMovement[0] = client.getAbsX() + 1;
			}
			client.forceMovement[1] = client.getAbsY();
			client.forceMovement[2] = client.getHeightLevel();
			break;

		default:
			client.getActionSender().sendMessage("OBJECT ID: "+ObjectStorage.getDetail(client, ObjectConstants.OBJECT_ID)+".");
			break;
			
		}
	}
	
	public static void run(Client client, int[] inStream) {

		if (client.isBusy()) {
			return;
		}

		int[] objectData = new int[3];
		
		for (int i = 0; i < ObjectConstants.objectCommander.length; i++) {
			if (ObjectConstants.objectCommander[i][0] == inStream[0]) {
				for (int i2 = 0; i2 < objectData.length; i2++) {
					objectData[i2] = ObjectConstants.objectCommander[i][i2 + 1];
				}
				break;
			}
		}
		for (int i = 0; i < objectData.length; i++) {
			if (objectData[i] == 0) {
				if (client.privileges > 1) {
					client.getActionSender().sendMessage("OBJECT ID: "+inStream[0]+".");
				}
				destruct(client);
				return;
			}
		}	

		ObjectStorage.addDetails(client, ObjectStorage.compress(inStream[0], objectData[1], inStream[1], inStream[2], objectData[0], objectData[2]));
		if (atObject(client)) {		
			runAction(client);	
			return;
		}
		ActionManager.addNewRequest(client, 11, 1);

	}
	
	public static boolean atObject(Client client) {

		switch(ObjectStorage.getDetail(client, ObjectConstants.OBJECT_SIZE)) {

		case 1: 
			return ObjectStorage.getDetail(client, ObjectConstants.OBJECT_X) - 1 == client.getAbsX() && ObjectStorage.getDetail(client, ObjectConstants.OBJECT_Y) == client.getAbsY() || 
					ObjectStorage.getDetail(client, ObjectConstants.OBJECT_X) + 1 == client.getAbsX() && ObjectStorage.getDetail(client, ObjectConstants.OBJECT_Y) == client.getAbsY() ||
					ObjectStorage.getDetail(client, ObjectConstants.OBJECT_Y) - 1 == client.getAbsY() && ObjectStorage.getDetail(client, ObjectConstants.OBJECT_X) == client.getAbsX() || 
					ObjectStorage.getDetail(client, ObjectConstants.OBJECT_Y) + 1 == client.getAbsY() && ObjectStorage.getDetail(client, ObjectConstants.OBJECT_X) == client.getAbsX();
		default:
			return client.getAbsX() >= ObjectStorage.getDetail(client, ObjectConstants.OBJECT_X) - 1 && client.getAbsX() <= ObjectStorage.getDetail(client, ObjectConstants.OBJECT_X) + ObjectStorage.getDetail(client, ObjectConstants.OBJECT_SIZE) && 
			       client.getAbsY() >= ObjectStorage.getDetail(client, ObjectConstants.OBJECT_Y) - 1 && client.getAbsY() <= ObjectStorage.getDetail(client, ObjectConstants.OBJECT_Y) + ObjectStorage.getDetail(client, ObjectConstants.OBJECT_SIZE);
		}
	}
	
	public int getDetail(Client client, int settingSlot) {
		return ObjectStorage.getDetail(client, settingSlot);
	}

	public static void destruct(Client client) {
		ObjectStorage.destruct(client);
		
	}
	@Override
	public void execute(Action currentAction) {
		int delay = 0;
		if (currentAction.getClient() == null) {
			if (!currentAction.getClient().isRunning)
				delay = 1;	
		}
		currentAction.setCurrentTick(delay);
		currentAction.setActionType(Action.type.LOOPING);
	}

	@Override
	public void loop(Action currentAction) {
		if (currentAction.getClient() == null) {
			stop(currentAction);
			return;
		}
		if (currentAction.getCurrentTick() > 0) {
			currentAction.setCurrentTick(currentAction.getCurrentTick() - 1);
			return;
		}
		for (int i = 0; i < ObjectConstants.objectArraySize; i++) {
			if (ObjectStorage.getDetail(currentAction.getClient(), i) == 0) {
				stop(currentAction);
				break;
			}
		}
		if (atObject(currentAction.getClient())) {
			runAction(currentAction.getClient());	
			stop(currentAction);		
		}
		currentAction.setCurrentTick(1);
	}

	@Override
	public void stop(Action currentAction) {
		currentAction.setActionType(Action.type.TRASHING);
		
	}
}