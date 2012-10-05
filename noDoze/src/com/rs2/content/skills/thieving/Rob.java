package com.rs2.content.skills.thieving;

import java.util.HashMap;

import com.rs2.content.controllers.Animation;
import com.rs2.content.controllers.Damage;
import com.rs2.content.controllers.Graphics;
import com.rs2.model.Entity;
import com.rs2.model.Entity.CombatType;
import com.rs2.model.combat.content.Life;
import com.rs2.model.npc.NPC;
import com.rs2.model.player.Client;
import com.rs2.model.player.PlayerConstants;
import com.rs2.tiles.FollowEngine;
import com.rs2.tiles.TileManager;
import com.rs2.util.Misc;

/**
 *
 * @author Killamess
 * 
 */

public class Rob {
	
	public static HashMap<Integer, thieve> npcs = new HashMap<Integer, thieve>();
	
	public static int Map(int npcType, int location) {
		return npcs(npcType).mapLocation[location];
	}
	public static thieve npcs(int id) {
		return npcs.get(id);
	}
	public enum thieve {
		
		MAN1(1, 1, 422, 1, 995, 150),
		MAN2(2, 1, 422, 1, 995, 150),
		MAN3(3, 1, 422, 1, 995, 150),
		WOMAN1(4, 1, 422, 1, 995, 125),
		WOMAN2(5, 1, 422, 1, 995, 125),
		WOMAN3(6, 1, 422, 1, 995, 125),
		FARMER(7, 15, 412, 2, 995, 250),
		GUARD1(9, 40, 412, 3, 995, 450),
		GUARD2(10, 40, 412, 3, 995, 450),
		WARRIOR_WOMEN(15, 25, 412, 2, 995, 300);
		public int[] mapLocation = new int[6];

		thieve(int id, int level, int animation, int damage, int loot, int lootAmount) {
			mapLocation[0] = id;
			mapLocation[1] = level;
			mapLocation[2] = animation;
			mapLocation[3] = damage;
			mapLocation[4] = loot;
			mapLocation[5] = lootAmount;	
		}
	} static {
		for (thieve pointer : thieve.values())
			npcs.put(pointer.mapLocation[0x0], pointer);
	}
	
	public static void createSituation(Entity robber, Entity victim) {
		
		if (robber instanceof Client && victim instanceof NPC) {
			
			final int npcType = ((NPC)victim).getDefinition().getType();
			
			if (((Client)robber).getStunnedTimer() > 0) {
				return;
			}
			if (robber.isBusy()) {
				return;
			}
			if (!Life.isAlive(victim, robber)) {
				return;
			}
			if (npcs(npcType) == null) {
				return;
			}
			
			if (npcs(npcType).mapLocation[1] > ((Client)robber).playerLevel[PlayerConstants.THIEVING]) {
				((Client)robber).getActionSender().sendMessage("You need a Thieving level of "+ npcs(npcType).mapLocation[1] + " to steal from "+((NPC)victim).getDefinition().getName()+"s.");
				return;
			}
			Animation.face(robber, victim);
			
			if (TileManager.calculateDistance(victim, robber) > 1) {
				robber.following = victim;
				FollowEngine.loop(robber);
			}
			
			int exp = ((NPC)victim).getDefinition().getCombat();
			
			if (Misc.random(7) == 1) {
				
				((Client)robber).setStunnedTimer(17);
				//((NPC)victim).say("What do you think you're doing?!"); //seems to crash the client?
				Animation.face(victim, robber);
				Animation.addNewRequest(victim, Map(npcType, 2), 1);
				Damage.addNewHit(victim, robber, CombatType.THIEF, Map(npcType, 3), 2);
				Graphics.addNewRequest(robber, 80, 1, 2);
				
			} else {
				
				((Client)robber).setStunnedTimer(4);
				Animation.createAnimation(robber, 881);
				((Client)robber).getActionSender().sendInventoryItem(Map(npcType, 4), Map(npcType, 5));
				((Client)robber).getActionAssistant().addSkillXP(exp * PlayerConstants.SKILL_EXPERIENCE_MULTIPLIER, PlayerConstants.THIEVING);
			}	
		}
	}
	
}
