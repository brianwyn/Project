package com.rs2.model.combat.content;

import com.rs2.model.player.Client;
import com.rs2.model.player.PlayerConstants;

/**
 * 
 * @author killamess
 *
 */
public class BarrowsEffects {
	
	/**
	 * Dharoks.
	 */
	public static final int DHAROKS = 0;
	
	public static void degradeDharoks(Client client, int damage) {
		
		boolean[] sectionFound = new boolean[4];
		
		client.setDharokDamage(client.getDharokDamage() + damage);
		
		if (client.getDharokDamage() < 1000) {
			if (!hasNewDharoks(client))
				return;
		}
		for (int locator = 0; locator < dharokSets.length; locator++) {
			
			if (locator == 5)
				break;

			if (client.playerEquipment[PlayerConstants.HELM] == dharokSets[locator][0] && !sectionFound[0]) {
				client.getEquipment().setEquipment(dharokSets[locator + 1][0], 1, PlayerConstants.HELM);
				sectionFound[0] = true;
			}
			if (client.playerEquipment[PlayerConstants.CHEST] == dharokSets[locator][1] && !sectionFound[1]) {
				client.getEquipment().setEquipment(dharokSets[locator + 1][1], 1, PlayerConstants.CHEST);
				sectionFound[1] = true;
			}
			if (client.playerEquipment[PlayerConstants.BOTTOMS] == dharokSets[locator][2] && !sectionFound[2]) { 
				client.getEquipment().setEquipment(dharokSets[locator + 1][2], 1, PlayerConstants.BOTTOMS);
				sectionFound[2] = true;
			}
			if (client.playerEquipment[PlayerConstants.WEAPON] == dharokSets[locator][3] && !sectionFound[3]) {
				client.getEquipment().setEquipment(dharokSets[locator + 1][3], 1, PlayerConstants.WEAPON);
				sectionFound[3] = true;
			}		
		}
		client.resetDharokDamage();
		client.getActionSender().sendMessage("Your armor has degraded!");
	}
	
	public static boolean hasNewDharoks(Client client) {
		int count = 0;
		if (client.playerEquipment[PlayerConstants.HELM] == dharokSets[0][0]) 
			count++;
		if (client.playerEquipment[PlayerConstants.CHEST] == dharokSets[0][1]) 
			count++;
		if (client.playerEquipment[PlayerConstants.BOTTOMS] == dharokSets[0][2]) 
			count++;
		if (client.playerEquipment[PlayerConstants.WEAPON] == dharokSets[0][3]) 
			count++;
		return count == 4;
	}
	
	public static int[][] dharokSets = {
		{4716, 4720, 4722, 4718}, // full set
		{4880, 4892, 4898, 4886}, // 100
		{4881, 4893, 4899, 4887}, // 75
		{4882, 4894, 4900, 4888}, // 50
		{4883, 4895, 4901, 4889}, // 25
		{4884, 4896, 4902, 4890}, // 0
	};
	
	public static boolean fullDharoks(Client client) {
		
		int count = 0;
		
		for (int i = 0; i < dharokSets.length; i++) {
			if (client.playerEquipment[PlayerConstants.HELM] == dharokSets[i][0]) 
				count++;
			if (client.playerEquipment[PlayerConstants.CHEST] == dharokSets[i][1]) 
				count++;
			if (client.playerEquipment[PlayerConstants.BOTTOMS] == dharokSets[i][2]) 
				count++;
			if (client.playerEquipment[PlayerConstants.WEAPON] == dharokSets[i][3]) 
				count++;	
		}
		return count == 4;
	}
}
