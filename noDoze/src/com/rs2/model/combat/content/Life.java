package com.rs2.model.combat.content;

import com.rs2.model.Entity;
import com.rs2.model.npc.NPC;
import com.rs2.model.player.Client;

/**
 * 
 * @author killamess
 *
 */
public class Life {
	
	public static boolean isAlive(Entity attacker) {
		
		if (attacker instanceof Client) {
			if (((Client)attacker).hitpoints > 0)
				return true;	
			
		} else if (attacker instanceof NPC) {
			if (((NPC)attacker).getHP() > 0)
				return true;
		}
		
		return false;
	}
	public static boolean isAlive(Entity attacker, Entity victim) {
		
		if (attacker instanceof Client) {
			if (((Client)attacker).hitpoints < 1)
				return false;	
			
		} else if (attacker instanceof NPC) {
			if (((NPC)attacker).getHP() < 1)
				return false;
		}
		if (victim instanceof Client) {
			if (((Client)victim).hitpoints < 1)
				return false;	
			
		} else if (victim instanceof NPC) {
			if (((NPC)victim).getHP() < 1)
				return false;
		}
		return true;
	}

}
