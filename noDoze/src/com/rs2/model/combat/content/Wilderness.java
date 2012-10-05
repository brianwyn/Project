package com.rs2.model.combat.content;
import com.rs2.GameEngine;
import com.rs2.content.minigames.Duelling;
import com.rs2.content.minigames.FightPits;
import com.rs2.content.minigames.FunPk;
import com.rs2.content.minigames.Jad;
import com.rs2.content.minigames.LumbridgePk;
import com.rs2.model.Entity;
import com.rs2.model.combat.CombatEngine;
import com.rs2.model.player.Client;
import com.rs2.model.player.Language;
/**
 * 
 * @author Killamess
 * Wilderness levels and attack options.
 *
 */
public class Wilderness {
	
	public static void wildernessEvent(Entity entity) {
		if (!(entity instanceof Client)) {
			return;
		}
		Client client = (Client) entity;
		
		if (client.multiZone() || GameEngine.multiValve == false) {
			client.getActionSender().sendMultiInterface(1);
		} else {
			client.getActionSender().sendMultiInterface(0);
		}
		if (!client.wildSignWarning && client.getAbsY() >= 3516 && client.getAbsY() < 3519) {
			client.getActionSender().sendInterface(1908);
			CombatEngine.resetAttack(client, false);
			client.resetWalkingQueue();
			client.wildSignWarning = true;
			client.getActionSender().sendFlagRemoval();
		}
		if (client.getAbsY() >= 3520 && client.getAbsY() <= 3980) {
			client.setWildernessLevel((client.getAbsY() - 3520) / 8 + 1);
			client.wildSignWarning = true;
		} else {
			client.setWildernessLevel(-1);
		}
		if (client.getWildernessLevel() != -1 && !FunPk.IsInFunPK(client) && !LumbridgePk.inLumbridgePkArea(client) && !Jad.inArea(client) && !FightPits.inFightArea(client) && !FightPits.inWaitingArea(client)) {
			client.outStream.createFrame(208);
			client.outStream.writeWordBigEndian_dup(197);
			client.getActionSender().sendQuest("Level: " + client.getWildernessLevel(), 199);
			client.getActionSender().sendOption("Attack", 1);
			client.flushOutStream();
		} else if (FunPk.IsInFunPK(client)) {
			client.outStream.createFrame(208);
			client.outStream.writeWordBigEndian_dup(197);
			client.getActionSender().sendQuest("FunPk", 199);
			client.getActionSender().sendOption("Attack", 1);
			client.flushOutStream();
		} else if (FightPits.inFightArea(client)) {
			
			client.setWildernessLevel(126);
			client.getActionSender().sendQuest("", 199);
			client.getActionSender().sendOption("@red@Attack", 1);
			
		} else if (FightPits.inWaitingArea(client)) {
			
			client.setWildernessLevel(126);
			client.getActionSender().sendQuest("", 199);
			client.getActionSender().sendOption("null", 1);
			
		} else if (LumbridgePk.inLumbridgePkArea(client)) {
			if (!LumbridgePk.inSafeArea(entity)) {
				client.outStream.createFrame(208);
				client.outStream.writeWordBigEndian_dup(197);
				client.getActionSender().sendOption("Attack", 1);
				client.flushOutStream();
				int cmb = client.getCombatLevel();
				client.getActionSender().sendQuest("@red@"+ (cmb - 10) +"-"+ (cmb + 10 > 126 ? 126 : cmb+10) +"", 199);
				client.setWildernessLevel(10);
			} else {
				client.outStream.createFrame(208);
				client.outStream.writeWordBigEndian_dup(197);
				client.getActionSender().sendOption("null", 1);
				client.getActionSender().sendQuest("SAFE", 199);
				client.setWildernessLevel(-1);
			}
			client.flushOutStream();
		} else if (Duelling.inDuelArea(client)) {
			client.outStream.createFrame(208);
			client.outStream.writeWordBigEndian_dup(201);
			client.getActionSender().sendOption("Challenge", 1);
			client.flushOutStream();
		} else {
			client.outStream.createFrame(208);
			client.outStream.writeWordBigEndian_dup(-1);
			client.getActionSender().sendOption("null", 1);
			client.flushOutStream();
			client.setWildernessLevel(-1);
		}	
	}
	
	public static boolean checkPlayers(Entity attacker, Entity victim) {
		
		if (attacker instanceof Client && victim instanceof Client) {
			
			int lvldiff = Math.abs(((Client)attacker).getCombatLevel() - ((Client)victim).getCombatLevel());
			
			if (FightPits.inWaitingArea(((Client)victim)) || ((Client)victim).isViewingOrb()) 
				return false;
			
			
			if (((Client)attacker).getWildernessLevel() == -1) {
				((Client)attacker).getActionSender().sendMessage(Language.NOT_IN_WILDY);
				return false;
			}
			if (((Client)victim).getWildernessLevel() == -1) {
				((Client)attacker).getActionSender().sendMessage(Language.NOT_IN_WILDY_OTHER);
				return false;
			}
			if (lvldiff > ((Client)attacker).getWildernessLevel()) {
				((Client)attacker).getActionSender().sendMessage(Language.WILDY_DIFFENCE);
				return false;
			}
		}
		return true;
	}

}
