package com.rs2.model.combat.ranged;

import com.rs2.content.controllers.Graphics;

import com.rs2.model.Entity;
import com.rs2.model.combat.melee.MaxHit;
import com.rs2.model.player.Client;
import com.rs2.model.player.PlayerConstants;
import com.rs2.util.Misc;

/**
 * 
 * @author Killamess
 *
 * Max hit Ranged.
 *
 */

public class MaxHitRanged {
	
	public static double getEffectiveStr(Client client) {
		
		double effectiveStrength = 0;
		
		effectiveStrength = ((client.playerLevel[PlayerConstants.RANGE]) * getPrayerStr(client) + MaxHit.getStyleBonus(client) + getVoidRangeBonus(client));	
		
		return effectiveStrength;
	}
	
	public static double getVoidRangeBonus(Client client) {
		if (hasVoid(client)) {
			int rangedLevel = client.playerLevel[PlayerConstants.RANGE];
			return (rangedLevel / 5) + 1.6;
		}
		return 0;
	}
	
	public static double calculateBaseDamage(Client client, Entity target, boolean special) {
		
		double base = 0;
		
		double eff = getEffectiveStr(client);
		double specialStr = MaxHit.getSpecialStr(client);
			
		double bonus = Ranged.getRangedStr(client);
		
		base = (5 + ((eff + 8) * (bonus + 64) / 64)) / 10;
			
		if (special)
			base = (base * specialStr);
			
		if (Ranged.isUsingEnchantedBolt(client) && client.playerEquipment[PlayerConstants.WEAPON] == Ranged.RUNE_CROSSBOW) {
			if (Misc.random(7) == 1 && Math.floor(base) >= 1) {
				Graphics.addNewRequest(target, Ranged.getEnchantedGfx(client), 0, 1);
				for (int i = 0; i < boltEffect.length; i++) {
					if (client.playerEquipment[PlayerConstants.ARROWS] == boltEffect[i][0])
						base = (base * boltEffect[i][1]);
				}	
			}
		}
		return Math.floor(base);
	}
	
	public static double getPrayerStr(Client client) {
		if (client.getPrayerHandler().clicked[20])
			return 1.05;
		else if (client.getPrayerHandler().clicked[21]) 
			return 1.1;
		else if (client.getPrayerHandler().clicked[22]) 
			return 1.15;
		return 1;
	}
	
	public static boolean hasVoid(Client client) {
		return 
		client.playerEquipment[PlayerConstants.HELM] == 11664 && 
		client.playerEquipment[PlayerConstants.CHEST] == 8839 && 
		client.playerEquipment[PlayerConstants.BOTTOMS] == 8840 && 
		client.playerEquipment[PlayerConstants.GLOVES] == 8842; 
	}
	
	public static final double[][] boltEffect = {
		{9241, 1.05}, {9242, 1.05},
		{9243, 1.15}, {9244, 1.45},
		{9245, 1.15},
	};
}
