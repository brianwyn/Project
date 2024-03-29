package com.rs2.model.combat.magic;

import com.rs2.model.Entity;
import com.rs2.model.player.Client;
import com.rs2.model.player.Language;
import com.rs2.model.player.PlayerConstants;

/**
 * 
 * @author killamess
 *
 */
public class MagicChecker {

	public static boolean canFreeze(int id) {
		if (Magic.spell(id) == null) 
			return false;
		
		return Magic.spell(id).getFreezeDelay() > 0;
	}
	
	public static boolean hasRequiredLevel(Entity ent, int id) {
		if (Magic.spell(id) == null) 
			return false;
		
		if (ent instanceof Client) {
			if (Magic.spell(id).getMagicLevel() > ((Client)ent).playerLevel[PlayerConstants.MAGIC]) {
				((Client)ent).getActionSender().sendMessage("You need a magic level of "+ Magic.spell(id).getMagicLevel() +" to cast this spell"); 
				return false;
			}
		}
		return true;
	}
	
	public static boolean hasRunes(Entity ent, int id) {
		if (Magic.spell(id) == null) 
			return false;
		
		int[] runes = Magic.spell(id).getRunes();
		int[] amount = Magic.spell(id).getAmounts();
		
		if (ent instanceof Client) {
			
			for (int i = 0; i <  runes.length; i++) {
				if (runes[i] > 0) {
					if (!((Client)ent).getActionAssistant().playerHasItem(runes[i], amount[i]) && !((Client)ent).getActionAssistant().staffType(runes[i])) {
						((Client)ent).getActionSender().sendMessage(Language.NO_RUNES);
						return false;
					}
				}	
			}
		}
		return true;
	}
	
	public static void deleteRunes(Entity ent, int id) {
		
		if (Magic.spell(id) == null) 
			return;
		
		int[] runes = Magic.spell(id).getRunes();
		int[] amount = Magic.spell(id).getAmounts();
		
		if (ent instanceof Client) {
			
			for (int i = 0; i < 4; i++) {
				if (runes[i] > 0) {
					if (!((Client)ent).getActionAssistant().staffType(runes[i])) {
						((Client)ent).getActionAssistant().deleteItem(runes[i], amount[i]); 
						
					}
				}	
			}
		}
	}	
}
