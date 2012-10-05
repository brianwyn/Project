package com.rs2.model.combat.magic;

import com.rs2.content.controllers.Graphics;
import com.rs2.model.player.Client;
import com.rs2.model.player.Language;
import com.rs2.model.player.PlayerConstants;
import com.rs2.world.XMLManager;
import com.rs2.world.XMLManager.Enchants;

/**
 * 
 * @author Killamess
 *
 */
public class Enchant {

	public static Enchants enchant(int id) {
		for (Enchants e : XMLManager.enchants) {
			if (e.getSpellId() == id) {
				return e;
			}
		}
		return null;
	}

	public static void item(Client client, int id, int itemId, int slot) {	
		
		for (Enchants e : XMLManager.enchants) {
			
			if (e.getSpellId() == id && e.getOriginalId() == itemId) {
		
				if (client.playerLevel[6] < e.getLevel()) {
					client.getActionSender().sendMessage(Language.MAGE_TOO_LOW);
					return;
				}
				if (!client.getActionAssistant().playerHasItem(e.getRunes()[0], e.getAmounts()[0]) || !client.getActionAssistant().playerHasItem(e.getRunes()[1], e.getAmounts()[1]) || !client.getActionAssistant().playerHasItem(e.getRunes()[2], e.getAmounts()[2])) {
					client.getActionSender().sendMessage(Language.NO_RUNES);
					return;
				}
				Graphics.addNewRequest(client, e.getGfx(), 100, 0);
				client.getActionAssistant().deleteItem(e.getRunes()[0], e.getAmounts()[0]);
				client.getActionAssistant().deleteItem(e.getRunes()[1], e.getAmounts()[1]);
				client.getActionAssistant().deleteItem(e.getRunes()[2], e.getAmounts()[2]);
				client.getActionAssistant().deleteItem(e.getOriginalId(), slot, 1);
				client.getActionSender().sendInventoryItem(e.getNewId(), 1, slot);	
				client.getActionAssistant().addSkillXP(e.getXp() * PlayerConstants.SKILL_EXPERIENCE_MULTIPLIER, PlayerConstants.MAGIC);
			}	
		}
	}
}
