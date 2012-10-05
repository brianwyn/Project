package com.rs2.content.minigames;

import com.rs2.content.controllers.Location;
import com.rs2.model.player.Client;
import com.rs2.tiles.TileManager;
import com.rs2.util.Misc;

/**
 * 
 * @author killamess
 * Jesus christ, this is gonna be big.
 */
public class Duelling {
	
	public static boolean inDuelArea(Client client) {
		int[] myLocation = TileManager.currentLocation(client);
		
		return 
			myLocation[0] >= 3328 && myLocation[0] <= 3392 &&
			myLocation[1] >= 3197 && myLocation[1] <= 3286;
	}
	
	public static void requestDuel(Client client, Client opponent) {
		client.duelRequest = opponent;
		
		if (opponent.duelRequest == client && client.duelRequest == opponent) {
			openDuelScreen(client);
			openDuelScreen(opponent);
			return;
		}
		client.getActionSender().sendMessage("Sending duel request...");
		opponent.getActionSender().sendMessage(client.getUsername()+":duelreq:");
	}
	
	public static void openDuelScreen(Client client) {
		if (client == null || client.duelRequest == null) {
			declineDuel(client);
			return;
		}
		client.getActionSender().sendQuest("Dueling with: " + client.duelRequest.getUsername() + " (level-" + client.duelRequest.combatLevel + ")", 6671);
		client.getActionSender().sendQuest("", 6684);
		client.getActionSender().sendFrame248(6575, 3321);
		client.getActionSender().sendItemReset(3322);
		for (int i = 0; i < client.duelRule.length; i++) {
			if (client.duelRule[i]) {
				client.duelRule[i] = false;
				client.duelOption -= client.DUEL_RULE_ID[i];
			}
		}
		client.duelAccepted[0] = false;
		client.duelAccepted[1] = false;
		client.getActionSender().sendFrame87(286, client.duelOption);
	}

	public static void declineDuel(Client client) {
		client.getActionSender().sendWindowsRemoval();
		resetAcceptStatus(client);
		client.duelRequest = null;
		client.duelSpaceReq = 0;
		client.duelAccepted[0] = false;
		client.duelAccepted[1] = false;
		for (int i = 0; i < client.duelRule.length; i++) {
			if (client.duelRule[i]) {
				client.duelRule[i] = false;
				client.duelOption -= client.DUEL_RULE_ID[i];
			}
		}
	}
	
	public static void selectRule(Client client, int rule) {
		if (client == null || client.duelRequest == null) {
			declineDuel(client);
			return;
		}
		client.getActionSender().sendQuest("", 6684);
		client.duelRequest.getActionSender().sendQuest("", 6684);
		client.duelRequest.duelSlot = client.duelSlot;
		
		if (rule >= 11 && client.duelSlot > -1) {
			if (client.playerEquipment[client.duelSlot] > 0) {
				if(!client.duelRule[rule]) {
					client.duelSpaceReq++;	
				} else {
					client.duelSpaceReq--;
				}
			}	
			if (client.duelRequest.playerEquipment[client.duelRequest.duelSlot] > 0) {
				if (!client.duelRequest.duelRule[rule]) {
					client.duelRequest.duelSpaceReq++;
				} else {
					client.duelRequest.duelSpaceReq--;
				}
			}
		}
		if(!client.duelRule[rule]) {
			client.duelRule[rule] = true;
			client.duelOption += client.DUEL_RULE_ID[rule];
		} else {	
			client.duelRule[rule] = false;
			client.duelOption -= client.DUEL_RULE_ID[rule];
		}
		client.getActionSender().sendFrame87(286, client.duelOption);
		client.duelRequest.duelOption = client.duelOption;
		client.duelRequest.duelRule[rule] = client.duelRule[rule];
		client.duelRequest.getActionSender().sendFrame87(286, client.duelRequest.duelOption);
		resetAcceptStatus(client);
	}
	
	public static void confirmDuel(Client client) {
		if (client == null || client.duelRequest == null) {
			declineDuel(client);
			return;
		}
		String itemId = "";
		
		client.getActionSender().sendQuest(itemId, 6516);
		itemId = "";
		
		client.getActionSender().sendQuest(itemId, 6517);
		client.getActionSender().sendQuest("", 8242);
		for(int i = 8238; i <= 8253; i++) {
			client.getActionSender().sendQuest("", i);
		}
		client.getActionSender().sendQuest("Hitpoints will be restored.", 8250);
		client.getActionSender().sendQuest("Boosted stats will be restored.", 8238);
		if(client.duelRule[8]) {
			client.getActionSender().sendQuest("There will be obstacles in the arena.", 8239);
		} 
		client.getActionSender().sendQuest("", 8240);
		client.getActionSender().sendQuest("", 8241);
		
		boolean itemsRemoved = false;
		
		for(int i = 11; i < 21; i++) {
			if (i == 14)
				continue;
			if(client.duelRule[i]) {
				itemsRemoved = true;
				break;
			}
		}
		String[] rulesOption = {"Players cannot forfeit!", "Players cannot move.", "Players cannot use range.", "Players cannot use melee.", "Players cannot use magic.",  "Players cannot drink pots.",  "Players cannot eat food.", "Players cannot use prayer.", "Obstacles.", "Players cannot use fun weapons.", "Players cannot use special attacks."};
		
		int lineNumber = 8242;
		
		if (itemsRemoved) 
			client.getActionSender().sendQuest("Some armor will not be usable.", lineNumber++);
		
		if (client.duelRule[14] && !client.duelRule[16]) 
			client.getActionSender().sendQuest("Weapons will not be usable.", lineNumber++);
		
		if (client.duelRule[14] && client.duelRule[16]) 
			client.getActionSender().sendQuest("weapons will not be usable.", lineNumber++);
		
		if (client.duelRule[16] && !client.duelRule[14])
			client.getActionSender().sendQuest("Two handed weapons will not be usable.", lineNumber++);
		
		for(int i = 0; i < 11; i++) {
			
			if (i == 8)
				continue;
			
			if(client.duelRule[i]) {
				client.getActionSender().sendQuest(""+rulesOption[i], lineNumber++);
			}
		}
	
		client.getActionSender().sendQuest("", 6571);
		client.getActionSender().sendFrame248(6412, 197);
	}
	
	public static void confirmSecondDuel(Client client) {
		if (client == null || client.duelRequest == null) {
			declineDuel(client);
			return;
		}
		client.duelAccepted[1] = true;
		
		if (client.duelAccepted[1] && client.duelRequest.duelAccepted[1]) {
			
			if (client.duelRule[1] && !client.duelRule[8]) { //no move, reg area
				int randomRoll = Misc.random(DuelAreaLocations.length);
				int randomSpot1 = Misc.random(1);
				int randomSpot2 = randomSpot1 == 1 ? 0 : 1;
				Location.addNewRequest(client, DuelAreaLocations[randomRoll][0], DuelAreaLocations[randomRoll][1], 0, 0);
				Location.addNewRequest(client.duelRequest, DuelAreaLocations[randomRoll][0] + randomSpot1, DuelAreaLocations[randomRoll][1] + randomSpot2, 0, 0);
			}
			if (!client.duelRule[1] && !client.duelRule[8]) { // reg area, can move
				int randomRoll1 = Misc.random(DuelAreaLocations.length);
				int randomRoll2 = Misc.random(DuelAreaLocations.length);
				int randomSpot1 = Misc.random(1);
				int randomSpot2 = randomSpot1 == 1 ? 0 : 1;
				Location.addNewRequest(client, DuelAreaLocations[randomRoll1][0], DuelAreaLocations[randomRoll1][1], 0, 0);
				Location.addNewRequest(client.duelRequest, DuelAreaLocations[randomRoll2][0] + randomSpot1, DuelAreaLocations[randomRoll2][1] + randomSpot2, 0, 0);
			}
			
			return;
		} 
		client.getActionSender().sendQuest("Waiting for other player...", 6571);
		client.duelRequest.getActionSender().sendQuest("Other player has accepted", 6571);
	}
	
	public static void checkSetupOnAccept(Client client) {
		if (client == null || client.duelRequest == null) {
			declineDuel(client);
			return;
		}
		
		client.duelAccepted[0] = true;
		boolean spaceChanged = false;
		
		if (client.duelAccepted[0] && client.duelRequest.duelAccepted[0]) {
			
			for(int i = 11; i < 21; i++) {
				if(client.duelRule[i]) {
					spaceChanged = true;
					break;
				}
			}
			if (spaceChanged) {
				if (client.getActionAssistant().freeSlots() < (client.duelSpaceReq ) || client.duelRequest.getActionAssistant().freeSlots() < (client.duelRequest.duelSpaceReq)) {
					resetAcceptStatus(client);
					client.getActionSender().sendQuest("You or your opponent don't have enough room.", 6684);
					client.duelRequest.getActionSender().sendQuest("You or your opponent don't have enough room.", 6684);
					client.duelAccepted[0] = client.duelRequest.duelAccepted[0] = false;
					if (client.playerEquipment[client.duelSlot] > 0) {
						client.duelSpaceReq--;
					}
					if (client.duelRequest.playerEquipment[client.duelRequest.duelSlot] > 0) {
						client.duelRequest.duelSpaceReq--;
					}
					return;
				}
			}
			if (client.duelRule[0] && client.duelRule[1]) {
				resetAcceptStatus(client);
				client.getActionSender().sendQuest("You cannot have 'No Forfeit' and 'No Movement'.", 6684);
				client.duelRequest.getActionSender().sendQuest("You cannot have 'No Forfeit' and 'No Movement'.", 6684);
				return;
			}
			if (client.duelRule[0] && client.duelRule[3]) {
				resetAcceptStatus(client);
				client.getActionSender().sendQuest("You cannot have 'No Forfeit' and 'No Melee'.", 6684);
				client.duelRequest.getActionSender().sendQuest("You cannot have 'No Forfeit' and 'No Melee'.", 6684);
				return;
			}
			if (client.duelRule[2] && client.duelRule[3] && client.duelRule[4]) {
				resetAcceptStatus(client);
				client.getActionSender().sendQuest("You must have 1 type of combat.", 6684);
				client.duelRequest.getActionSender().sendQuest("You must have 1 type of combat.", 6684);
				return;
			}
			if (client.duelRule[1] && client.duelRule[8]) {
				resetAcceptStatus(client);
				client.getActionSender().sendQuest("You cannot have 'No Movement' and 'Obstacles'.", 6684);
				client.duelRequest.getActionSender().sendQuest("You cannot have 'No Movement' and 'Obstacles'.", 6684);
				return;
			}
			confirmDuel(client);
			confirmDuel(client.duelRequest);
			return;
		}
		client.getActionSender().sendQuest("Waiting for other player...", 6684);
		client.duelRequest.getActionSender().sendQuest("Other player has accepted.", 6684);
	}
	
	public static void resetAcceptStatus(Client client) {
		if (client == null || client.duelRequest == null) {
			declineDuel(client);
			return;
		}
		client.duelAccepted[0] = client.duelAccepted[1] = 
		client.duelRequest.duelAccepted[0] = client.duelRequest.duelAccepted[1] = false;
	}
	
	public static final int[][] DuelAreaLocations = {
		{ 3338, 3248 },
		{ 3335, 3253 },
		{ 3340, 3250 },
		{ 3343, 3247 },
		{ 3345, 3250 },
		{ 3345, 3255 },
		{ 3350, 3254 },
		{ 3354, 3254 },
		{ 3354, 3251 },
		{ 3353, 3247 },
		{ 3341, 3257 },
	};
}


