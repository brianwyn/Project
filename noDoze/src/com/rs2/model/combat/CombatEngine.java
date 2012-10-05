package com.rs2.model.combat;

import com.rs2.Constants;
import com.rs2.GameEngine;
import com.rs2.content.actions.tasks.Skulling;
import com.rs2.content.controllers.Animation;
import com.rs2.content.controllers.Damage;
import com.rs2.gameevents.BusyEvent;
import com.rs2.model.Entity;
import com.rs2.model.Entity.CombatType;
import com.rs2.model.combat.content.Distances;
import com.rs2.model.combat.content.Hits;
import com.rs2.model.combat.content.Life;
import com.rs2.model.combat.content.Wilderness;
import com.rs2.model.combat.magic.AutoCast;
import com.rs2.model.combat.magic.GodSpells;
import com.rs2.model.combat.magic.Magic;
import com.rs2.model.combat.magic.MagicChecker;
import com.rs2.model.combat.magic.MagicHandler;
import com.rs2.model.combat.magic.MagicType;
import com.rs2.model.combat.magic.Multi;
import com.rs2.model.combat.ranged.Ranged;
import com.rs2.model.npc.NPC;
import com.rs2.model.npc.NPCAttacks;
import com.rs2.model.npc.NPCFollow;
import com.rs2.model.player.Client;
import com.rs2.model.player.Equipment;
import com.rs2.model.player.PlayerConstants;
import com.rs2.tiles.FollowEngine;
import com.rs2.tiles.TileManager;
import com.rs2.util.Misc;
import com.rs2.world.NPCManager;

/**
 * 
 * @author killamess
 *
 */
public class CombatEngine {
	
	public static void addEvent(Entity attacker, Entity target) {
		attacker.setTarget(target);
	}
	
	public static void createNewAttack(Entity attacker, Entity target) {
		
		if (attacker instanceof Client && !Life.isAlive(attacker) || !Life.isAlive(target) || !Wilderness.checkPlayers(attacker, target) || TileManager.calculateDistance(attacker, target) >= 20) {
			attacker.setInCombatWith(null);
			attacker.setInCombatTimer(0);
			target.setInCombatWith(null);
			target.setInCombatTimer(0);
			resetAttack(attacker, true);
			if (attacker instanceof Client) {
				((Client)attacker).resetWalkingQueue();
			}
			return;
		}
		if (target instanceof NPC) {
			if (((NPC)target).getDefinition().getType() == Constants.PET_TYPE) {
				attacker.setInCombatWith(null);
				attacker.setInCombatTimer(0);
				target.setInCombatWith(null);
				target.setInCombatTimer(0);
				resetAttack(attacker, true);
				if (attacker instanceof Client) {
					((Client)attacker).resetWalkingQueue();
				}
				return;
			}
		}
		if (attacker instanceof Client) {
			((Client)attacker).logoutDelay = 20;
		}
		if (attacker.following == null || attacker.following != target)
			attacker.following = target;
		
		if (attacker instanceof Client) {
			
			if (attacker.getCombatType() == CombatType.MELEE) {
				if (!((Client)attacker).usingSpecial())
					Animation.createAnimation(attacker);
				Hits.runHitMelee(attacker, target, ((Client)attacker).usingSpecial());
				
			} else if (attacker.getCombatType() == CombatType.RANGE) { 
				
				if (Ranged.hasArrows(((Client)attacker))) {
					if (Ranged.projectile(((Client)attacker).playerEquipment[PlayerConstants.ARROWS]) != null || Ranged.isUsingCrystalBow(((Client)attacker))) {
						if (Ranged.isUsingCrystalBow(((Client)attacker))) {
							Animation.createGraphic(attacker, Ranged.projectile(1337).getPullBackGfx(), 0, true);
						} else {
							Animation.createGraphic(attacker, Ranged.projectile(((Client)attacker).playerEquipment[PlayerConstants.ARROWS]).getPullBackGfx(), 0, true);
						}
					}
					Animation.createAnimation(attacker);
					if (((Client)attacker).usingSpecial()) {
						Ranged.createMSBProjectile(attacker, target);
					} else {
						Ranged.createProjectile(attacker, target);
					
					}
					Hits.runHitMelee(attacker, target, ((Client)attacker).usingSpecial());
				} else {
					resetAttack(attacker, true);
					return;
				}
			} else if (attacker.getCombatType() == CombatType.MAGIC) { 
				
				int theCurrentId = 0;
				
				if (((Client)attacker).spellId > 0) {
					theCurrentId = ((Client)attacker).spellId;
					((Client)attacker).turnOffSpell = true;
				} else if (((Client)attacker).isAutoCasting()) {
					theCurrentId = ((Client)attacker).getAutoCastId();
				}
				if (theCurrentId > 0) {
					
					if (MagicChecker.hasRunes(attacker, theCurrentId)) {
						
						if (MagicChecker.hasRequiredLevel(attacker, theCurrentId)) {
							
							if (GodSpells.godSpell(attacker, theCurrentId)) {
								if (GodSpells.wrongGodCape(attacker, theCurrentId)) {
									((Client)attacker).getActionSender().sendMessage("You cannot cast this spell while supporting a different god.");
									resetAttack(attacker, true);
									return;
								}
							}
							if (MagicType.getMagicType(theCurrentId) == MagicType.type.TELEBLOCK) {
								if (target instanceof NPC) {
									((Client)attacker).getActionSender().sendMessage("You cannot cast this spell on npcs.");
									resetAttack(attacker, true);
									return;
								}
							}	
							MagicChecker.deleteRunes(attacker, theCurrentId);
							attacker.setHasHit(Hits.canHitMagic(attacker, target, theCurrentId));

							/**
							 * check for multi attacking spell.
							 */
							
							Multi.multiAttack(attacker, target, theCurrentId, 1);
							attacker.setCombatDelay(MagicHandler.getSpellDelay(theCurrentId));
							
							if (Magic.spell(theCurrentId).getProjectileDelay() > 0) {
								MagicHandler.startGfx(attacker, target, theCurrentId);
								MagicHandler.startAnimation(attacker, theCurrentId);
								MagicHandler.endGfx(target, attacker, theCurrentId);
								attacker.setHoldingSpellDelay(Magic.spell(theCurrentId).getProjectileDelay());
								attacker.setHoldingSpell(theCurrentId);
								attacker.setHoldingSpellTarget(target);
							} else {
								MagicHandler.startGfx(attacker, target, theCurrentId);
								MagicHandler.startAnimation(attacker, theCurrentId);
								MagicHandler.createProjectile(attacker, target, theCurrentId);
								MagicHandler.endGfx(target, attacker, theCurrentId);
							}
						} else {
							AutoCast.turnOff(((Client)attacker));
							resetAttack(attacker, true);
							return;
						}	
					} else {
						AutoCast.turnOff(((Client)attacker));
						resetAttack(attacker, true);
						return;
					}
				}
			}
			if (((Client)attacker).playerEquipment[PlayerConstants.WEAPON] == Ranged.DARK_BOW)
				Hits.runHitMelee(attacker, target, false);
			
		} else if (attacker instanceof NPC) {
			
			int npcId = ((NPC)attacker).getDefinition().getType();
			
			if (attacker.getCombatType() == CombatType.MAGIC) {
				
				int spell = 0;
				
				for (int i = 0; i < NPCAttacks.NPC_SPELLS.length; i++) {
					if (NPCAttacks.NPC_SPELLS[i][0] == npcId) {
						spell = NPCAttacks.NPC_SPELLS[i][1];
						break;
					}
				}
				attacker.setHasHitTarget(0, Hits.canHitMagic(attacker, target, spell));
				MagicHandler.startGfx(attacker, target, spell);
				MagicHandler.startAnimation(attacker, spell);
				MagicHandler.createProjectile(attacker, target, spell);
				MagicHandler.endGfx(target, attacker, spell);
				attacker.setCombatDelay(6);	
				
			} else {
				int randomGen = Misc.random(5);
				if (randomGen > 4 && npcId == 941 || npcId == 55)  {	
					int spell = 0;
					
					for (int i = 0; i < NPCAttacks.NPC_SPELLS.length; i++) {
						if (NPCAttacks.NPC_SPELLS[i][0] == npcId) {
							spell = NPCAttacks.NPC_SPELLS[i][1];
							break;
						}
					}
					MagicHandler.startGfx(attacker, target, spell);
					MagicHandler.startAnimation(attacker, spell);
					if (target instanceof Client) {
						if (((Client)target).playerEquipment[PlayerConstants.SHIELD] != 1540 && ((Client)target).playerEquipment[PlayerConstants.SHIELD] != 11284) {
							((Client)target).getActionSender().sendMessage("The dragons breath burns you.");
							Damage.addNewHit(attacker, target, CombatType.MAGIC, Misc.random(70), 2);
						} else {
							((Client)target).getActionSender().sendMessage("The shield aborbs the dragons breath.");
							if (Misc.random(5) == 1) {
								Damage.addNewHit(attacker, target, CombatType.MAGIC, Misc.random(8), 2);
							} else {
								Damage.addNewHit(attacker, target, CombatType.MAGIC, 0, 2);
							}
						}
					}
				} else {
					int animation = NPCManager.emotions[npcId][0];
					Animation.createAnimation(attacker, animation);
					Hits.runHitMelee(attacker, target, ((NPC)attacker).usingSpecial());
				}
			}
		}
		if (!(attacker.getCombatType() == CombatType.MAGIC)) { 
			attacker.setCombatDelay((attacker instanceof Client) ? Equipment.getWeaponSpeed((Client)attacker) : 6);	
		}
		if (attacker instanceof Client && target instanceof Client)
			Skulling.setSkulled((Client)attacker,(Client) target);
		
	}
	
	public static void resetAttack(Entity ent, boolean stopMovement) {
		
		ent.setTarget(null);
		FollowEngine.resetFollowing(ent);
		if (ent instanceof Client) {

			((Client)ent).resetFaceDirection();
			
			if (stopMovement) {
				((Client)ent).stopMovement();	
				((Client)ent).getActionSender().sendFlagRemoval();
			}
		}
	} 
	
	public static boolean inCombatWith(Entity entity, Entity victim) {
		return entity.getInCombatWith() == victim;
	}

	public static String canAttack(Entity entity, Entity victim) {
		
		if(entity.getInCombatWith() != null && entity.getInCombatWith() != victim)
			return "You are already under attack.";
		else if(victim.getInCombatWith() != null && victim.getInCombatWith() != entity)
			return "They are already under attack.";
		else if(victim.getTarget() != null && victim.getTarget() != entity && victim.getTarget().getTarget() !=null)
			return "They are already in combat.";
		return "";
	}
	public static CombatType getCombatType(Entity entity) {
		
		if (entity instanceof Client) {
			
			Client client = (Client) entity;
			
			if (client.isAutoCasting() || client.getAutoCastId() > 0 || client.spellId > 0)
				return CombatType.MAGIC;
				
			else if (Ranged.isUsingRange(client))
				return CombatType.RANGE;
		} else if (entity instanceof NPC) {
			
			NPC npc = (NPC) entity;
			int type = npc.getDefinition().getType();
			int combatType = NPCAttacks.getCombatType(type);
			
			switch(combatType) {
			
			case 2:
				return CombatType.RANGE;
			case 3:
				return CombatType.MAGIC;
			case 4:
				if (Misc.random(1) == 0) 
					return CombatType.RANGE;
				else 
					return CombatType.MAGIC;
			case 5:
				if (Misc.random(1) == 0) 
					return CombatType.MELEE;
				else 
					return CombatType.RANGE;
			case 6:
				int random = Misc.random(2);
				if (random == 0) 
					return CombatType.MELEE;
				else if (random == 1) 
					return CombatType.RANGE;
				else
					return CombatType.MAGIC;
			case 7:
				if (Misc.random(1) == 0) 
					return CombatType.MELEE;
				else 
					return CombatType.MAGIC;
			}
		}
		return CombatType.MELEE;
	}
	public static void mainProcessor(Entity entity) {
		
		if (entity == null)
			return;
		
		if (entity.getTarget() != null) {
			if (entity.getTarget() instanceof Client) {
				if (((Client)entity.getTarget()).disconnected) {
					CombatEngine.resetAttack(entity, true);
				}
			}
		}
		
		if (entity.getFreezeDelay() > 0) {
			entity.setFreezeDelay(entity.getFreezeDelay() - 1);
			if (entity.getFreezeDelay() == 0) {
				if (entity instanceof Client)
					((Client)entity).setCanWalk(true);
			}
		}
		if (entity.getInCombatWithTimer() > 0) 
			entity.deductInCombatWithTimer();
		
		if (entity.getInCombatWithTimer() <= 0) 
			entity.setInCombatWith(null);
		
		if (entity.getCombatDelay() > 0) 
			entity.setCombatDelay(entity.getCombatDelay() - 1);	
		
		if (entity.getVengTimer() > 0) 
			entity.deductVengTimer();
		
		if (entity.getHoldingSpellDelay() > 0)
			entity.setHoldingSpellDelay(entity.getHoldingSpellDelay() - 1);	
		
		BusyEvent.busyEvent(entity);
		Wilderness.wildernessEvent(entity);
		
		if (entity.getHoldingSpell() > 0 && entity.getHoldingSpellDelay() == 0 && entity.getHoldingSpellTarget() != null) {
			MagicHandler.createProjectile(entity, entity.getHoldingSpellTarget(), entity.getHoldingSpell());
			entity.setHoldingSpellDelay(0);
			entity.setHoldingSpell(0);
			entity.setHoldingSpellTarget(null);
		}
		entity.setCombatType(getCombatType(entity));
		
		if (entity.getTarget() == null) {
			return;
		}
		if (checkForSpellReset(entity)) {
			FollowEngine.resetFollowing(entity);
			return;
		}
		
		Animation.face(entity, entity.getTarget());			
		
		if (entity.getTarget() != null) {
			if (!Life.isAlive(entity.getTarget())) {
				entity.setInCombatWith(null);
				entity.setInCombatTimer(0);
			}
		}
		if (!Wilderness.checkPlayers(entity, entity.getTarget())) {
			resetAttack(entity, true);
			return;
		}
		if (entity.getRetaliateDelay() > 0) {
			if (entity.getRetaliateDelay() == 1) {
				entity.setRetaliateDelay(0);
			} else {
				entity.setRetaliateDelay(entity.getRetaliateDelay() - 1);
			}
			return;
		}
		if (entity.isBusy())
			return;
		
		if (TileManager.calculateDistance(entity.getTarget(), entity) > 32) {
			resetAttack(entity, true);
			return;
		}

		if (entity instanceof NPC) {
			if (NPCFollow.validSidewardsWalker((NPC)entity) && !NPCFollow.inMeleeDistance(entity, entity.getTarget())) {
				if (((NPC)entity).getCombatType() == CombatType.MELEE)
					return;
			}
		} else {
			if (!TileManager.inAttackablePosition(entity, entity.getTarget()) && entity.combatType == CombatType.MELEE && TileManager.calculateDistance(entity, entity.getTarget()) == 1) {
				return;
			}
		}
		
		if (entity.getCombatDelay() > 0 || !Distances.inAttackableDistance(entity, entity.getTarget()))
			return;
		
		if (canAttack(entity, entity.getTarget()) != "") {
			if (GameEngine.multiValve && !inMultiZone(entity.getTarget()) && !inMultiZone(entity)) {
				if (entity instanceof Client) {
					((Client)entity).getActionSender().sendMessage(canAttack(entity, entity.getTarget())+"");
				}
				resetAttack(entity, true);
				return;
			}
		} else {
			entity.getTarget().setInCombatWith(entity);
			entity.getTarget().setInCombatWithTimer();
			if (entity.getTarget() != null && entity.getTarget().getTarget() != entity) {
				entity.getTarget().setRetaliateDelay(2);
			}
		}
		createNewAttack(entity, entity.getTarget());
	}
	
	public static boolean inMultiZone(Entity entity) {
		return entity.multiZone();
	}
	
	public static boolean checkForSpellReset(Entity entity) {
		
		if (!(entity instanceof Client)) 
			return false;
		
		Client client = (Client) entity;
		
		if (client.spellId > 0 && client.turnOffSpell) {
			client.turnOffSpell = false;
			client.spellId = 0;
			entity.setTarget(null);
			return true;
		}
		return false;
	}
}
