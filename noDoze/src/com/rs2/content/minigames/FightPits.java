package com.rs2.content.minigames;

import java.util.ArrayList;

import com.rs2.Constants;
import com.rs2.content.controllers.Animation;
import com.rs2.content.controllers.Location;
import com.rs2.model.combat.CombatEngine;
import com.rs2.model.combat.content.Specials;
import com.rs2.model.player.Client;
import com.rs2.tiles.TileManager;
import com.rs2.util.Misc;
import com.rs2.world.PlayerManager;

/**
 * 
 * @author killamess
 * FightPits
 */

public class FightPits {
	
	private static String previousWinner = "No one";
	private static int REWARD = 6571;
	private static int LIMIT = 120;
	
	public static int nextRoundDelay = LIMIT;
	public static boolean matchActive = false;
	
	private static ArrayList<Client> inWaitingRoom = new ArrayList<Client>();
	private static ArrayList<Client> inFightPits = new ArrayList<Client>();
	
	public static void addToWaitingRoom(Client client) {
		if (!inWaitingRoom.contains(client))
			inWaitingRoom.add(client);
	}
	public static boolean inWaitingRoom(Client client) {
		return inWaitingRoom.contains(client);
	}
	public static void removeFromWaitingRoom(Client client) {
		if (inWaitingRoom.contains(client)) 
			inWaitingRoom.remove(client);
	}
	
	public static void addToFightPits(Client client) {
		if (!inFightPits.contains(client))
			inFightPits.add(client);
	}
	public static boolean inFightPits(Client client) {
		return inFightPits.contains(client);
	}
	public static void removeFromFightPits(Client client) {
		if (inFightPits.contains(client)) 
			inFightPits.remove(client);
	}
	
	public static void process() {
		
		ArrayList<Client> inWaitingRoomCopy = new ArrayList<Client>();
		inWaitingRoomCopy = inWaitingRoom;
		
		ArrayList<Client> inFightPitCopy = new ArrayList<Client>();
		inFightPitCopy = inFightPits;
		
		for (int i2 = 0; i2 < Constants.MAX_PLAYERS; i2++) {
			
			Client client = (Client) PlayerManager.getSingleton().getPlayers()[i2];
			
			if (client == null || client.isDead())
				continue;
			
			if (inFightArea(client))
				if (!inFightPits(client) && !client.isViewingOrb()) 
					addToFightPits(client);	
			
			if (inWaitingArea(client))
				if (!inWaitingRoom(client)) 
					addToWaitingRoom(client);
			if (client.isViewingOrb())
				addToWaitingRoom(client);
		}
		
		
		for (Client client : inWaitingRoomCopy) {
			if (!inWaitingArea(client) && !client.isViewingOrb()) {
				inWaitingRoom.remove(client);
			}
		}
		
		for (Client client : inFightPitCopy) {
			if (!matchActive) 
				Location.addNewRequest(client, 2399, 5169, client.getHeightLevel(), 0);
			
			if (!inFightArea(client) && !client.isViewingOrb()) {
				removeFromFightPits(client);	
			}
		}
		
		if (inWaitingRoomCopy.size() == 0 && inFightPitCopy.size() == 0) 
			return;
		
		for (Client client : inWaitingRoomCopy) {
			if (previousWinner == "No one")
				client.getActionSender().sendQuest("Current Champion: No one", 2805);
			else 
				client.getActionSender().sendQuest("Current Champion: JalYt-Ket-"+previousWinner, 2805);
			
			client.getActionSender().sendQuest("@whi@Players Waiting: "+inWaitingRoomCopy.size(), 2806);
			client.getActionSender().sendFrame36(560, 1);
			client.getActionSender().sendWalkableInterface(2804);
		}
		
		for (Client client : inFightPitCopy) {
			if (previousWinner == "No one")
				client.getActionSender().sendQuest("Current Champion: No one", 2805);
			else 
				client.getActionSender().sendQuest("Current Champion: JalYt-Ket-"+previousWinner, 2805);
			
			client.getActionSender().sendQuest("Foes: "+(inFightPitCopy.size() - 1), 2806);
			client.getActionSender().sendFrame36(560, 1);
			client.getActionSender().sendWalkableInterface(2804);
		}
		if (inFightPitCopy.size() == 1 && matchActive) {
			
			for (Client client : inFightPitCopy) {
				if (matchActive) {
					matchActive = false;
					client.setBusyTimer(9);
					CombatEngine.resetAttack(client, true);
					client.getActionSender().sendMessage("You have won the fight pit match!. You have been awarded an Uncut Onyx.");
					previousWinner = client.getUsername();
					Animation.addNewRequest(client, 862, 4);
					Location.addNewRequest(client, 2399, 5169, client.getHeightLevel(), 9);
					if (client.getActionAssistant().freeSlots() >= 1) {
						client.getActionSender().sendInventoryItem(REWARD, 1);
					} else {
						for (int i = 0; i < client.getPlayerBankSize(); i++) {
							if (client.bankItems[i] == REWARD + 1) {
								client.bankItemsN[i] += 1;
								break;
							} else if (client.bankItems[i] < 1) {
								client.bankItems[i] = REWARD + 1;
								client.bankItemsN[i] = 1;	
								break;
							}
						}
						client.getActionSender().sendMessage("You had no room for this item, So it was automatically added to your bank account.");
					}
					removeFromFightPits(client);
					break;
				}	
			}	
		}
		if (nextRoundDelay == 10) {
			for (Client client : inWaitingRoomCopy) {
				client.getActionSender().sendMessage("The match will begin in: 5 seconds, Prepare for battle!.");
				if (client.isViewingOrb()) {
					fightPitsOrb("Centre", 15239, client);
					client.setViewingOrb(false);
					client.getActionSender().sendWindowsRemoval();
					client.getActionSender().sendFrame99(0);
					client.getActionSender().sendSidebar(10, 2449);
					client.teleportToX = 2399;
					client.teleportToY = 5171;
					client.setHeightLevel(0);
					client.isNPC = false;
					client.updateRequired = true;
					client.appearanceUpdateRequired = true;
				}
			}
		}
		if (nextRoundDelay == 0) {
			if (inWaitingRoomCopy.size() < 2) {
				
				System.out.println(inWaitingRoomCopy.size());
				
				for (Client client : inWaitingRoomCopy) {
					
					if (client == null || client.isDead() || client.disconnected)
						continue;
					
					client.getActionSender().sendMessage("You need atleast 2 players to start the match, The Match will begin in: 60 seconds.");
				}
				nextRoundDelay = LIMIT;
				return;
			}
			if (matchActive) {
				for (Client client : inWaitingRoomCopy) {
					
					if (client == null || client.isDead() || client.disconnected)
						continue;
					
					client.getActionSender().sendMessage("The current game is still running, The Match will begin in: 60 seconds.");
				}
				nextRoundDelay = LIMIT;
				return;
			}
			for (Client client : inWaitingRoomCopy) {
				
				if (client == null || client.isDead() || client.disconnected)
					continue;
				
				if (inWaitingArea(client) && !client.isViewingOrb())
					Location.addNewRequest(client, 2385 + Misc.random(20), 5151 + Misc.random(9), client.getHeightLevel(), 1);
				
				client.setFreezeDelay(0);
				client.skullTimer = 0;
				client.energy = 100;
				client.updateEnergy();
				Specials.deathEvent(client);
				
			}
			matchActive = true;
			
		}
		if (nextRoundDelay > 0) 
			nextRoundDelay -= 1;
		else
			resetRoundDelay();
		
		inFightPitCopy.clear();
		inWaitingRoomCopy.clear();
		inFightPits.clear();
		inWaitingRoom.clear();
		
	}

	public static void resetRoundDelay() {
		nextRoundDelay = LIMIT;
	}
	
	public static boolean inWaitingArea(Client client) {
		int[] myLocation = TileManager.currentLocation(client);
		
		return 
			myLocation[0] >= 2395 && myLocation[0] <= 2403 &&
			myLocation[1] >= 5169 && myLocation[1] <= 5175;
	}
	
	public static boolean inFightArea(Client client) {
		int[] myLocation = TileManager.currentLocation(client);
		
		return 
			myLocation[0] >= 2375 && myLocation[0] <= 2418 &&
			myLocation[1] >= 5129 && myLocation[1] <= 5167;
	}
	
	public static void fightPitsOrb(String setting, int frame, Client client){
		client.getActionSender().sendQuest("@yel@Centre",15239);
		client.getActionSender().sendQuest("@yel@North-West",15240);
		client.getActionSender().sendQuest("@yel@North-East",15241);
		client.getActionSender().sendQuest("@yel@South-East",15242);
		client.getActionSender().sendQuest("@yel@South-West",15243);
		client.getActionSender().sendQuest("@whi@"+setting,frame);
	}
	public static void hidePlayer(Client client){
		if(isInViewingOrb(client)){
			if(client.getHeightLevel() != 0){
				client.setHeightLevel(0);
			} else if(client.getHeightLevel() == 0){
				client.npcID = 1666;
				client.isNPC = true;
				client.setViewingOrb(true);
				client.updateRequired = true;
				client.appearanceUpdateRequired = true;
			}
		}
	}
	public static boolean isInViewingOrb(Client client){
		int absX = client.getAbsX();
		int absY = client.getAbsY();
		if(absX == 2398 && absY == 5150) return true;//center
		if(absX == 2384 && absY == 5157) return true;//north - west
		if(absX == 2409 && absY == 5158) return true;//north - east
		if(absX == 2411 && absY == 5137) return true;//south - east
		if(absX == 2388 && absY == 5138) return true;//south - west
	return false;
	}
}
