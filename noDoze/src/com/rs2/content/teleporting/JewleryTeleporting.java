package com.rs2.content.teleporting;

import com.rs2.content.JailSystem;
import com.rs2.content.controllers.Animation;
import com.rs2.content.controllers.Graphics;
import com.rs2.content.controllers.Location;
import com.rs2.model.combat.content.Rings;
import com.rs2.model.player.Client;

/**
 * 
 * @author killamess
 *
 */
public class JewleryTeleporting {
	
	public static final int[] glorys = {
		1712, 1710, 1708, 1706, 1704
	};
	
	/**
	 * (8) - (1), then crumble to dust
	 */
	public static final int[] duelingRings = {
		2552, 2554, 2556, 2558, 2560, 2562, 2564, 2566
	};
	
	public static void gloryTeleport(Client client, int gloryState) {
		
		if (client.isBusy())
			return;
		
		if (client.inMiniGame()) {
			client.getActionSender().sendWindowsRemoval();
			client.getActionSender().sendMessage("You cannot teleport out of a minigame!");
			return;
		}
		
		if (gloryState == glorys[4]) {
			client.getActionSender().sendMessage("This amulet has no more charges left.");
			return;
		}
		client.getActionSender().selectOption("Teleport to?", "Edgeville.", "Karamja.", "Draynor Village.", "Al Kharid.", "No where.");
		client.jewleryId = gloryState;
			
	}
	
	public static void duelingRingTeleport(Client client, int ringState) {
		
		if (client.isBusy())
			return;
		
		client.getActionSender().selectOption("Teleport to", "Al Kharid Duel Arena.", "Castle Wars Arena.", "No where.");
		client.jewleryId = ringState;
			
	}
	
	public static void useRingOfDueling(Client client, int actionButton) {
		
		if (client.isBusy() || JailSystem.inJail(client))
			return;
		
		if (client.getActionAssistant().isItemInBag(client.jewleryId)) {
			for (int rings : duelingRings) {
				if (client.jewleryId == rings) { 
					client.getActionAssistant().deleteItem(client.jewleryId, 1);
					if (rings == 2566) {
						Rings.breakRing(client);
					} else
						client.getActionSender().sendInventoryItem(client.jewleryId + 2, 1);
				}
			}
		}
		client.resetFaceDirection();
		client.resetWalkingQueue();
		client.setBusyTimer(8);
		Graphics.addNewRequest(client, 308, 100, 3);
		Animation.addNewRequest(client, 714, 0);
		Animation.addNewRequest(client, 715, 5);

		
		switch(actionButton) {
		case 9167: //Al Kharid Duel Arena
			Location.addNewRequest(client, 3317, 3235, 0, 4);
			break;
		case 9168: //castlewars
			Location.addNewRequest(client, 2442, 3089, 0, 4);
			break;
		}
	}
	
	public static void useGlory(Client client, int actionButton) {
		
		if (client.isBusy() || JailSystem.inJail(client))
			return;
		
		if (client.getActionAssistant().isItemInBag(client.jewleryId)) {
			for (int glory : glorys) {
				if (client.jewleryId == glory) { 
					client.getActionAssistant().deleteItem(client.jewleryId, 1);
					client.getActionSender().sendInventoryItem(client.jewleryId - 2, 1);
				}
			}
		}
		client.resetFaceDirection();
		client.resetWalkingQueue();
		client.setBusyTimer(8);
		Graphics.addNewRequest(client, 308, 100, 3);
		Animation.addNewRequest(client, 714, 0);
		Animation.addNewRequest(client, 715, 5);
		
		switch(actionButton) {
		case 9190: //Edgeville
			Location.addNewRequest(client, 3088, 3502, 0, 4);
			break;
		case 9191: //Karamja
			Location.addNewRequest(client, 2918, 3176, 0, 4);
			break;
		case 9192: //Draynor Village
			Location.addNewRequest(client, 3104, 3249, 0, 4);
			break;
		case 9193: //Al Kharid 3293, 3177, now tzhaar
			Location.addNewRequest(client, 3293, 3177, 0, 4);
			break;
		}
	}
}
