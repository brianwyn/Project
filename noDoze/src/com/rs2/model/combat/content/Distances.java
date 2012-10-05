package com.rs2.model.combat.content;


import com.rs2.model.Entity;
import com.rs2.model.Entity.CombatType;
import com.rs2.model.npc.NPC;
import com.rs2.model.player.Client;
import com.rs2.tiles.TileManager;
/**
 * 
 * @author Killamess
 * helps us keep control of
 * our players & npcs distances.
 *
 */
public class Distances {
	
	public static boolean inAttackableDistance(Entity attacker, Entity target) {
		
		if (attacker == null || target == null)
			return false;
		
		int actualDistance = 1;
		
		if (attacker instanceof Client && target instanceof NPC) 
			actualDistance = TileManager.calculateDistance(target, attacker);
		else if (attacker instanceof NPC && target instanceof Client) 
			actualDistance = TileManager.calculateDistance(attacker, target);
		else if (attacker instanceof Client && target instanceof Client) 
			actualDistance = TileManager.calculateDistance(attacker, target);
		
		if (attacker.combatType == CombatType.MELEE) {
			if (attacker instanceof Client) {
				if (target instanceof Client) {
					if (actualDistance > 3)
						return false;
				} else if (actualDistance > 1)
					return false;
			} else if (attacker instanceof NPC) {
				if (actualDistance > 1)
					return false;
			}
			if (actualDistance <= 0) 
				return false;
			if (attacker instanceof Client) {
				if (actualDistance > 1 && attacker.getFreezeDelay() > 0)
					return false;
			}
		} else if (attacker.combatType == CombatType.RANGE || attacker.combatType == CombatType.MAGIC) {
			if (attacker instanceof Client) {
				if (((Client)attacker).combatMode == 6) {
					if (actualDistance > 8)
						return false;	
				}
			}
			if (actualDistance > 32) 
				return false;
			if (actualDistance <= 0) 
				return false;
		} else if (attacker.combatType == CombatType.NOTHING)
			return false;
		
		return true;
	}
	
}