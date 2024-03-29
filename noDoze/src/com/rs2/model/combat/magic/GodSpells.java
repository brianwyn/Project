package com.rs2.model.combat.magic;

import com.rs2.content.controllers.Animation;
import com.rs2.content.controllers.Graphics;
import com.rs2.model.Entity;
import com.rs2.model.player.Client;
import com.rs2.model.player.Language;
import com.rs2.model.player.PlayerConstants;

/**
 * 
 * @author Killamess
 * 
 * God spell effects.
 * 
 */
public class GodSpells {
	
	public static int GOD_BOOK[][] = {
		
		{1190, 2415, 2412, 2413, 2414},	
		{1191, 2416, 2413, 2414, 2412},	
		{1192, 2417, 2414, 2412, 2413}

	};
	
	public static boolean godSpell(Entity ent, int spell) {
		
		if (Magic.spell(spell) == null) 
			return false;
		
		if (ent instanceof Client) {
			for (int i = 0; i < GOD_BOOK.length; i++) {
				if (spell == GOD_BOOK[i][0]) {
					if (((Client)ent).playerEquipment[PlayerConstants.WEAPON] == GOD_BOOK[i][1]) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public static boolean hasGodCape(Entity ent, int spell) {
		
		if (Magic.spell(spell) == null) 
			return false;
		
		if (ent instanceof Client) {
			for (int i = 0; i < GOD_BOOK.length; i++) {
				if (spell == GOD_BOOK[i][0])
					if (((Client)ent).playerEquipment[PlayerConstants.WEAPON] == GOD_BOOK[i][1])
						if (((Client)ent).playerEquipment[PlayerConstants.CAPE] == GOD_BOOK[i][2])
							return true;
			}
		}
		return false;
	}	
	
	public static boolean wrongGodCape(Entity ent, int spell) {
		if (Magic.spell(spell) == null) 
			return false;
		
		if (ent instanceof Client) {
			for (int i = 0; i < GOD_BOOK.length; i++) {
				if (spell == GOD_BOOK[i][0]) {
					if (((Client)ent).playerEquipment[PlayerConstants.WEAPON] == GOD_BOOK[i][1]) {
						if (((Client)ent).playerEquipment[PlayerConstants.CAPE] == GOD_BOOK[i][3] ||
								((Client)ent).playerEquipment[PlayerConstants.CAPE] == GOD_BOOK[i][4]) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}	
	
	public static void charge(Client client) {
		
		if (client == null)
			return;
		
		if (client.isCharged() || client.chargeTimer > 0) {
			client.getActionSender().sendMessage("You are already charged.");
			return;
		}
		
		if (client.getActionAssistant().playerHasItem(554,3) || client.getActionAssistant().staffType(554)) {
			
			if (client.getActionAssistant().playerHasItem(556,3) || client.getActionAssistant().staffType(556)) {
				
				if (client.getActionAssistant().playerHasItem(565,3)) {
					
					Animation.createAnimation(client, 811);
					Graphics.addNewRequest(client, 111, 100, 1);
					client.setCharged(true);
					client.chargeTimer = 1200; // 10 minutes
					
					if (!client.getActionAssistant().staffType(554))
						client.getActionAssistant().deleteItem(554, 3);
						
					client.getActionAssistant().deleteItem(565, 3);
					
					if (!client.getActionAssistant().staffType(556))
						client.getActionAssistant().deleteItem(556, 3);
					
					client.getActionAssistant().addSkillXP(180 * PlayerConstants.COMBAT_EXPERIENCE_MULTIPLIER, PlayerConstants.MAGIC);
					return;
				}
			}
		} 
		client.getActionSender().sendMessage(Language.NO_RUNES);
	}
}
