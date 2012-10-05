package com.rs2.model.combat.melee;

import com.rs2.model.player.Client;
import com.rs2.model.player.PlayerConstants;

/**
 * 
 * @author Killamess
 *
 */

public class MaxHit {
	
	public static double calculateBaseDamage(Client client, boolean special) {
		
		double base = 0;
		
		double effective = getEffectiveStr(client);
		double specialBonus = getSpecialStr(client);
			
		double strengthBonus = client.getBonuses().bonus[PlayerConstants.STR_BONUS];
			
		base = (13 + effective + (strengthBonus / 8) + ((effective * strengthBonus) / 64)) / 10;
			
		if (special)
			base = (base * specialBonus);
			
		if (hasObsidianEffect(client) || hasVoid(client))
			base = (base * 1.2);
		
		return Math.floor(base);
	}
	
	public static double getEffectiveStr(Client client) {
		return ((client.playerLevel[PlayerConstants.STRENGTH]) * getPrayerStr(client)) + getStyleBonus(client);		
	}
	
	public static int getStyleBonus(Client client) {
		return 
		client.combatMode == 2 ? 3 : 
			client.combatMode == 3 ? 1 : 
				client.combatMode == 4 ? 3 : 0;
	}
	
	public static double getPrayerStr(Client client) {
		if (client.getPrayerHandler().clicked[1])
			return 1.05;
		else if (client.getPrayerHandler().clicked[4]) 
			return 1.1;
		else if (client.getPrayerHandler().clicked[10]) 
			return 1.15;
		else if (client.getPrayerHandler().clicked[18]) 
			return 1.18;
		else if (client.getPrayerHandler().clicked[19]) 
			return 1.23;
		return 1;
	}
	
	public static final double[][] special = {
		{ 5698, 1.1}, { 5680, 1.1}, 
		{ 1231, 1.1}, { 1215, 1.1}, 
		{ 3204, 1.1}, { 1305, 1.15}, 
		{ 1434, 1.45},{ 11694, 1.34375},
		{ 11696, 1.1825}, {11698, 1.075},
		{ 11700, 1.075}, {861, 1.1}
	};
	
	public static double getSpecialStr(Client client) {
		for (double[] slot : special) {
			if (client.playerEquipment[PlayerConstants.WEAPON] == slot[0])
				return slot[1];
		}
		return 1;
	}
	
	public static final int[] obsidianWeapons = {
		746, 747, 6523, 6525, 6526, 6527, 6528
	};
	
	public static boolean hasObsidianEffect(Client client) {		
		if (client.playerEquipment[PlayerConstants.AMULET] != 11128) 
			return false;
			
		for (int weapon : obsidianWeapons) {
			if (client.playerEquipment[PlayerConstants.WEAPON] == weapon) 
				return true;
		}
		return false;
	}
	
	public static boolean hasVoid(Client client) {
		return 
		client.playerEquipment[PlayerConstants.HELM] == 11665 && 
		client.playerEquipment[PlayerConstants.CHEST] == 8839 && 
		client.playerEquipment[PlayerConstants.BOTTOMS] == 8840 && 
		client.playerEquipment[PlayerConstants.GLOVES] == 8842; 
	}
}
