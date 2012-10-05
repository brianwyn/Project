package com.rs2.gameevents;

import com.rs2.GameEngine;
import com.rs2.content.minigames.FightPits;
import com.rs2.content.minigames.FunPk;
import com.rs2.model.combat.CombatEngine;
import com.rs2.model.combat.content.ItemProtect;
import com.rs2.model.combat.content.PkTokens;
import com.rs2.model.combat.content.Specials;
import com.rs2.model.player.Client;
import com.rs2.model.player.Player;
import com.rs2.model.player.PlayerConstants;
import com.rs2.util.Misc;
import com.rs2.world.PlayerManager;

/**
 * A player dies.
 * @author Ultimate/killamess
 */

public class DeathEvent {

	public static void process() {
		
		for (Player p : PlayerManager.getSingleton().getPlayers()) {
			
			if (p == null)
				continue;
			
			Client client = (Client) p;
			
			if (client.hitpoints <= 0 && client.getStunnedTimer() < 0) {
				client.setDead(true);
				client.setCanWalk(false);
				client.stopMovement();
				client.setStunnedTimer(4);
			}
			if (client.isDead()) {
				
				if (!client.isDeadWaiting() && client.getStunnedTimer() <= 0) {
					
					client.setDeadWaiting(true);
					client.setDeadTimer(5);
					client.poisonDelay = -1;
					client.poisonDamage = -1;
					client.setBusy(true);
					client.setCanWalk(false);
					client.stopMovement();
					CombatEngine.resetAttack(client, true);
					client.getActionAssistant().startAnimation(2304, 0);
					client.getActionSender().sendMessage("Oh dear, you died!");

				} else {
					if (client.getDeadTimer() == 1) {
						
						if (client.whoKilledYa != null) 
							PkTokens.rewardPlayer(client.whoKilledYa, client);
						
						if (FightPits.inFightArea(client) || FightPits.inWaitingArea(client)) {
							
							client.teleportToX = 2399;
							client.teleportToY = 5175 - Misc.random(2);
							
						} else if (FunPk.IsInFunPK(client)) {
							
							client.teleportToX = PlayerConstants.SPAWN_X + Misc.random(1);
							client.teleportToY = PlayerConstants.SPAWN_Y + Misc.random(1);
							
						} else {
							
							client.teleportToX = PlayerConstants.SPAWN_X + Misc.random(1);
							client.teleportToY = PlayerConstants.SPAWN_Y + Misc.random(1);
							
							ItemProtect.dropItems(client, true);
						}
						client.setWildernessLevel(-1);
						client.setFreezeDelay(0);
						client.energy = 100;
						client.updateEnergy();
						client.getEquipment().sendWeapon();	
						client.setVeng(false);
						client.setHeightLevel(0);
						client.skullTimer = 0;
						client.skulledOn = null;
						client.getEquipment().setWeaponEmotes();
						client.hitpoints = client.getLevelForXP(client.playerXP[PlayerConstants.HITPOINTS]);
						client.getPrayerHandler().resetAllPrayers();
						Specials.deathEvent(client);
						for (int i = 0; i < PlayerConstants.MAX_SKILLS; i++) {
							client.playerLevel[i] = client.getLevelForXP(client.playerXP[i]);
							client.getActionAssistant().refreshSkill(i);
						}

					}
					if (client.getDeadTimer() == 0) {
						client.setDead(false);
						client.setDeadWaiting(false);
						client.logoutDelay = 20;
						client.setBusy(false);
						client.setCanWalk(true);
						GameEngine.getGlobalActions().sendAnimationReset(client);
					}
					client.deductDeadTimer();
				}
			}
			client.deductStunnedTimer();
		}
	}
}
