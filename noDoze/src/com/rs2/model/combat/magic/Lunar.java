package com.rs2.model.combat.magic;

import com.rs2.content.controllers.Animation;
import com.rs2.content.controllers.Damage;
import com.rs2.model.Entity;
import com.rs2.model.Entity.CombatType;
import com.rs2.model.combat.content.Life;
import com.rs2.model.npc.NPC;
import com.rs2.model.player.Client;
import com.rs2.model.player.Language;
import com.rs2.model.player.PlayerConstants;
import com.rs2.util.Misc;

/**
 * 
 * @author killamess
 * contains some lunar spells.
 */

public class Lunar {
	
	public static void castVeng(Entity ent) {
		if (ent == null) 
			return;
		if (ent instanceof Client) {
			if (((Client)ent).isBusy() || !Life.isAlive(ent))
				return;
			if (((Client)ent).isVengOn()) {
				((Client)ent).getActionSender().sendMessage(Language.VENG_ALREADY_CASTED);
				return;
			}
			if (ent.getVengTimer() > 0) {
				((Client)ent).getActionSender().sendMessage(Language.VENG_TIMER);
				return;
			}		
			if (((Client)ent).playerLevel[PlayerConstants.MAGIC] < 94) { 
				((Client)ent).getActionSender().sendMessage(Language.MAGE_TOO_LOW);
				return;
			}
			if(!((Client)ent).getActionAssistant().playerHasItem(9075, 4) || !((Client)ent).getActionAssistant().playerHasItem(557, 10) && !((Client)ent).getActionAssistant().staffType(557) || !((Client)ent).getActionAssistant().playerHasItem(560, 2)) {
				((Client)ent).getActionSender().sendMessage(Language.NO_RUNES);
				return;
			}
			Animation.createAnimation(ent, 4410);
			Animation.createGraphic(ent, 726, 0, true);
			((Client)ent).getActionAssistant().deleteItem(9075, 4);
			if (!((Client)ent).getActionAssistant().staffType(557)) {
				((Client)ent).getActionAssistant().deleteItem(557, 10);
			}
			((Client)ent).getActionAssistant().deleteItem(560, 2);
			((Client)ent).getActionAssistant().addSkillXP(112* PlayerConstants.COMBAT_EXPERIENCE_MULTIPLIER, 6);
			((Client)ent).getActionSender().sendMessage("You cast vengence.");
			((Client)ent).setVeng(true);
			ent.setVengTimer();
		}
	}
	
	public static void vengenceEffect(Entity attacker, Entity target, int damage) {
		
		int totalDamage = damage / 2 + Misc.random(damage / 3) + 1;
		
		if (attacker instanceof Client) {
			
			if (((Client)attacker).hitpoints - (totalDamage) <= 0) 
				totalDamage = ((Client)attacker).hitpoints;
			
			((Client)attacker).setVeng(false);
			((Client)attacker).getActionAssistant().forceText("Taste vengence!");
			
		} else if (attacker instanceof NPC) {
			
			if (((NPC)attacker).getHP() - (totalDamage) <= 0) 
				totalDamage = ((NPC)attacker).getHP();
			
		}
		Damage.addNewHit(attacker, target, CombatType.RECOIL, totalDamage, 0);	
	}
}
