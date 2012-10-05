package com.rs2.content.skills.herblore;

import com.rs2.model.player.Client;
import com.rs2.model.player.PlayerConstants;
/**
 * 
 * @author Killamess
 *
 */
public class Cleaning {

	public static int[][] clean = {
		{ 199, 249, 1, (int) 2.5}, 
		{ 201, 251, 5, (int) 3.8}, 
		{ 203, 253, 11, 5}, 
		{ 205, 255, 20, (int) 6.3}, 
		{ 207, 257, 25, (int) 7.5}, 
		{ 209, 259, 30, (int) 8.8}, 
		{ 211, 261, 35, 10}, 
		{ 213, 263, 40, (int) 11.3}, 
		{ 215, 265, 48, (int) 12.5},
		{ 217, 267, 54, (int) 13.8}, 
		{ 219, 269, 75, 15},
	};
	
	public static void cleanHerb(Client client, int itemId, int slot) {
		
		if (!client.getActionAssistant().isItemInBag(itemId)) 
			return;
		
		for (int i = 0; i < clean.length; i++) {
			
			if (clean[i][0] == itemId) { 
				
				if (client.playerLevel[PlayerConstants.HERBLORE] < clean[i][2]) {
 					client.getActionSender().sendMessage("You need a herblore level of "+ clean[i][2] +" to clean this.");
 					return;
 				}
				client.getActionAssistant().deleteItem(clean[i][0], slot, 1);
				client.getActionSender().sendInventoryItem(clean[i][1], 1);
				client.getActionSender().sendMessage("You clean the herb.");
				client.getActionAssistant().addSkillXP(clean[i][3] * PlayerConstants.SKILL_EXPERIENCE_MULTIPLIER, PlayerConstants.HERBLORE);
				break;
			}
		}	
	}
}
