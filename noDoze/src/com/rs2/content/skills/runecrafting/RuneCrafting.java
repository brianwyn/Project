package com.rs2.content.skills.runecrafting;

import com.rs2.GameEngine;
import com.rs2.content.controllers.Animation;
import com.rs2.content.controllers.Graphics;
import com.rs2.model.player.Client;
import com.rs2.model.player.PlayerConstants;
import com.rs2.tiles.TileManager;

public class RuneCrafting {

	public static boolean runeCraftArea(Client client) {
		int[] myLocation = TileManager.currentLocation(client);
		
		return 
			myLocation[0] >= 3024 && myLocation[0] <= 3054 &&
			myLocation[1] >= 4817 && myLocation[1] <= 4846;
	}
	
	public static void craftRunesOnAltar(Client client, int requiredLevel, int exp, int item, int x2, int x3, int x4, int talismen) {
		
		int essamount = 0;
		if (!client.getActionAssistant().isItemInBag(talismen)) {
			client.getActionSender().sendMessage("You need "+ GameEngine.getItemManager().getItemDefinition(talismen).getName().toLowerCase()+" to craft "+ GameEngine.getItemManager().getItemDefinition(item).getName().toLowerCase()+"s.");
			return;
		}
		if (client.playerLevel[PlayerConstants.RUNECRAFTING] < requiredLevel) {
			client.getActionSender().sendMessage("You need a runecrafting level of " +requiredLevel +" .");
			return;
		}
		if (!client.getActionAssistant().isItemInBag(1436)) {
			client.getActionSender().sendMessage("You need "+ GameEngine.getItemManager().getItemDefinition(1436).getName().toLowerCase()+" to craft "+ GameEngine.getItemManager().getItemDefinition(item).getName().toLowerCase()+"s.");
			return;
		}
		Graphics.addNewRequest(client, 186, 100, 0);
		Animation.addNewRequest(client, 791, 0);
		
		if (client.playerLevel[PlayerConstants.RUNECRAFTING] >= 0 && client.playerLevel[PlayerConstants.RUNECRAFTING] < x2) {
			essamount = client.getActionAssistant().getItemCount(1436);
		}
		if (client.playerLevel[PlayerConstants.RUNECRAFTING] >= x2 && client.playerLevel[PlayerConstants.RUNECRAFTING] < x3) {
			essamount = client.getActionAssistant().getItemCount(1436) * 2;
		}
		if (client.playerLevel[PlayerConstants.RUNECRAFTING] >= x4) {
			essamount = client.getActionAssistant().getItemCount(1436) * 3;
		}
 		for (int i = 0; i < 28; i++) {
 			client.getActionAssistant().deleteItem(1436, client.getActionAssistant().getItemSlot(1436), i);
		}
		client.getActionAssistant().addSkillXP((exp * essamount) * PlayerConstants.SKILL_EXPERIENCE_MULTIPLIER, PlayerConstants.RUNECRAFTING);
		client.getActionSender().sendInventoryItem(item, essamount);
		client.getActionSender().sendMessage("You craft " + essamount + " " + GameEngine.getItemManager().getItemDefinition(item).getName() + "s.");
		essamount = -1;
	}


}
