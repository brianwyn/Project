package com.rs2.model.player.packetmanager.packets;

import com.rs2.content.EmotionTablet;
import com.rs2.content.ItemDestroy;
import com.rs2.content.actions.ActionManager;
import com.rs2.content.controllers.Location;
import com.rs2.content.minigames.Duelling;
import com.rs2.content.minigames.FightPits;
import com.rs2.content.skills.crafting.HideCrafting;
import com.rs2.content.skills.crafting.Tanning;
import com.rs2.content.skills.fletching.Fletching;
import com.rs2.content.skills.smithing.SmithingSmelt;
import com.rs2.content.teleporting.JewleryTeleporting;
import com.rs2.content.teleporting.MenuTeleports;
import com.rs2.model.combat.content.CombatMode;
import com.rs2.model.combat.content.Specials;
import com.rs2.model.combat.magic.AutoCast;
import com.rs2.model.combat.magic.GodSpells;
import com.rs2.model.combat.magic.Lunar;
import com.rs2.model.combat.magic.TeleOther;
import com.rs2.model.player.Client;
import com.rs2.model.player.packetmanager.Packet;
import com.rs2.util.Misc;
import com.rs2.world.PlayerManager;

/**
 * @author Killamess
 */

public class ActionButtons implements Packet {

	public void handlePacket(Client client, int packetType, int packetSize) {

		int actionButtonId = Misc.HexToInt(client.inStream.buffer, 0, packetSize);
		
		//client.getActionSender().sendWindowsRemoval();
		//System.out.println(actionButtonId+"");
		if (client.isDead())
			return;
		
		switch (actionButtonId) {

		case 49022: //teleother accept;
			client.getActionSender().sendWindowsRemoval();
			TeleOther.acceptTeleport(client, (Client) PlayerManager.getSingleton().getPlayerByName(client.teleOther), client.teleToSpell);
			break;
			
		case 49024: //teleOther decline
			client.getActionSender().sendWindowsRemoval();
			break;
			
		case 15147://Smelt Bronze 1x
			if(client.getActionAssistant().playerHasItem(436,1) && client.getActionAssistant().playerHasItem(438,1)){
				new SmithingSmelt(client, 1, 436);
			} else {
				client.getActionSender().sendMessage("You don't have enough ores.");
				client.getActionSender().sendWindowsRemoval();
			}
			break;
			case 15146://Smelt Bronze 5x
			if(client.getActionAssistant().playerHasItem(436,1) && client.getActionAssistant().playerHasItem(438,1)){
				new SmithingSmelt(client, 5, 436);
			} else {
				client.getActionSender().sendMessage("You don't have enough ores.");
				client.getActionSender().sendWindowsRemoval();
			}
			break;
			case 10247://Smelt Bronze 10x
			if(client.getActionAssistant().playerHasItem(436,1) && client.getActionAssistant().playerHasItem(438,1)){
				new SmithingSmelt(client, 10, 436);
			} else {
				client.getActionSender().sendMessage("You don't have enough ores.");
				client.getActionSender().sendWindowsRemoval();
			}
			break;
			case 9110://Smelt Bronze x
			if(client.getActionAssistant().playerHasItem(436,1) && client.getActionAssistant().playerHasItem(438,1)){
				new SmithingSmelt(client, 20, 436);
			} else {
				client.getActionSender().sendMessage("You don't have enough ores.");
				client.getActionSender().sendWindowsRemoval();
			}
			break;
			case 15151: // Iron
			if(client.getActionAssistant().playerHasItem(440,1)){
				new SmithingSmelt(client, 1, 440);
			} else {
				client.getActionSender().sendMessage("You don't have enough ores.");
				client.getActionSender().sendWindowsRemoval();
			}
			break;
			case 15150:
			if(client.getActionAssistant().playerHasItem(440,1)){
				new SmithingSmelt(client, 5, 440);
			} else {
				client.getActionSender().sendMessage("You don't have enough ores.");
				client.getActionSender().sendWindowsRemoval();
			}
			break;
			case 15149:
			if(client.getActionAssistant().playerHasItem(440,1)){
				new SmithingSmelt(client, 10, 440);
			} else {
				client.getActionSender().sendMessage("You don't have enough ores.");
				client.getActionSender().sendWindowsRemoval();
			}
			break;
			case 15148:
			if(client.getActionAssistant().playerHasItem(440,1)){
				new SmithingSmelt(client, 20, 440);
			} else {
				client.getActionSender().sendMessage("You don't have enough ores.");
				client.getActionSender().sendWindowsRemoval();
			}
			break;
			case 15155: // Silver
			if(client.getActionAssistant().playerHasItem(442,1)){
				new SmithingSmelt(client, 1, 442);
			} else {
				client.getActionSender().sendMessage("You don't have enough ores.");
				client.getActionSender().sendWindowsRemoval();
			}
			break;
			case 15154:
			if(client.getActionAssistant().playerHasItem(442,1)){
				new SmithingSmelt(client, 5, 442);
			} else {
				client.getActionSender().sendMessage("You don't have enough ores.");
				client.getActionSender().sendWindowsRemoval();
			}
			break;
			case 15153:
			if(client.getActionAssistant().playerHasItem(442,1)){
				new SmithingSmelt(client, 10, 442);
			} else {
				client.getActionSender().sendMessage("You don't have enough ores.");
				client.getActionSender().sendWindowsRemoval();
			}
			break;
			case 15152:
			if(client.getActionAssistant().playerHasItem(442,1)){
				new SmithingSmelt(client, 20, 442);
			} else {
				client.getActionSender().sendMessage("You don't have enough ores.");
				client.getActionSender().sendWindowsRemoval();
			}
			break;
			case 15163: // Gold
			if(client.getActionAssistant().playerHasItem(444,1)){
				new SmithingSmelt(client, 1, 444);
			} else {
				client.getActionSender().sendMessage("You don't have enough ores.");
				client.getActionSender().sendWindowsRemoval();
			}
			break;
			case 15162:
			if(client.getActionAssistant().playerHasItem(444,1)){
				new SmithingSmelt(client, 5, 444);
			} else {
				client.getActionSender().sendMessage("You don't have enough ores.");
				client.getActionSender().sendWindowsRemoval();
			}
			break;
			case 15161:
			if(client.getActionAssistant().playerHasItem(444,1)){
				new SmithingSmelt(client, 10, 444);
			} else {
				client.getActionSender().sendMessage("You don't have enough ores.");
				client.getActionSender().sendWindowsRemoval();
			}
			break;
			case 15160:
			if(client.getActionAssistant().playerHasItem(444,1)){
				new SmithingSmelt(client, 20, 444);
			} else {
				client.getActionSender().sendMessage("You don't have enough ores.");
				client.getActionSender().sendWindowsRemoval();
			}
			break;
			case 15159: // Steel
			if(client.getActionAssistant().playerHasItem(440,1) && client.getActionAssistant().playerHasItem(453,1)){
				new SmithingSmelt(client, 1, 443);
			} else {
				client.getActionSender().sendMessage("You don't have enough ores.");
				client.getActionSender().sendWindowsRemoval();
			}
			break;
			case 15158:
			if(client.getActionAssistant().playerHasItem(440,1) && client.getActionAssistant().playerHasItem(453,1)){
				new SmithingSmelt(client, 5, 443);
			} else {
				client.getActionSender().sendMessage("You don't have enough ores.");
				client.getActionSender().sendWindowsRemoval();
			}
			break;
			case 15157:
			if(client.getActionAssistant().playerHasItem(440,1) && client.getActionAssistant().playerHasItem(453,1)){
				new SmithingSmelt(client, 10, 443);
			} else {
				client.getActionSender().sendMessage("You don't have enough ores.");
				client.getActionSender().sendWindowsRemoval();
			}
			break;
			case 15156:
			if(client.getActionAssistant().playerHasItem(440,1) && client.getActionAssistant().playerHasItem(453,1)){
				new SmithingSmelt(client, 20, 443);
			} else {
				client.getActionSender().sendMessage("You don't have enough ores.");
				client.getActionSender().sendWindowsRemoval();
			}
			break;
			case 29017: // Mithril
			if(client.getActionAssistant().playerHasItem(447,1) && client.getActionAssistant().playerHasItem(453,1)){
				new SmithingSmelt(client, 1, 447);
			} else {
				client.getActionSender().sendMessage("You don't have enough ores.");
				client.getActionSender().sendWindowsRemoval();
			}
			break;
			case 29016:
			if(client.getActionAssistant().playerHasItem(447,1) && client.getActionAssistant().playerHasItem(453,1)){
				new SmithingSmelt(client, 5, 447);
			} else {
				client.getActionSender().sendMessage("You don't have enough ores.");
				client.getActionSender().sendWindowsRemoval();
			}
			break;
			case 24253:
			if(client.getActionAssistant().playerHasItem(447,1) && client.getActionAssistant().playerHasItem(453,1)){
				new SmithingSmelt(client, 10, 447);
			} else {
				client.getActionSender().sendMessage("You don't have enough ores.");
				client.getActionSender().sendWindowsRemoval();
			}
			break;
			case 16062:
			if(client.getActionAssistant().playerHasItem(447,1) && client.getActionAssistant().playerHasItem(453,1)){
				new SmithingSmelt(client, 20, 447);
			} else {
				client.getActionSender().sendMessage("You don't have enough ores.");
				client.getActionSender().sendWindowsRemoval();
			}
			break;
			case 29022: // Addy
			if(client.getActionAssistant().playerHasItem(449,1) && client.getActionAssistant().playerHasItem(453,1)){
				new SmithingSmelt(client, 1, 449);
			} else {
				client.getActionSender().sendMessage("You don't have enough ores.");
				client.getActionSender().sendWindowsRemoval();
			}
			break;
			case 29020:
			if(client.getActionAssistant().playerHasItem(449,1) && client.getActionAssistant().playerHasItem(453,1)){
				new SmithingSmelt(client, 5, 449);
			} else {
				client.getActionSender().sendMessage("You don't have enough ores.");
				client.getActionSender().sendWindowsRemoval();
			}
			break;
			case 29019:
			if(client.getActionAssistant().playerHasItem(449,1) && client.getActionAssistant().playerHasItem(453,1)){
				new SmithingSmelt(client, 10, 449);
			} else {
				client.getActionSender().sendMessage("You don't have enough ores.");
				client.getActionSender().sendWindowsRemoval();
			}
			break;
			case 29018:
			if(client.getActionAssistant().playerHasItem(449,1) && client.getActionAssistant().playerHasItem(453,1)){
				new SmithingSmelt(client, 20, 449);
			} else {
				client.getActionSender().sendMessage("You don't have enough ores.");
				client.getActionSender().sendWindowsRemoval();
			}
			break;			
			case 29026: // Rune
			if(client.getActionAssistant().playerHasItem(451,1) && client.getActionAssistant().playerHasItem(453,1)){
				new SmithingSmelt(client, 1, 451);
			} else {
				client.getActionSender().sendMessage("You don't have enough ores.");
				client.getActionSender().sendWindowsRemoval();
			}
			break;
			case 29025:
			if(client.getActionAssistant().playerHasItem(451,1) && client.getActionAssistant().playerHasItem(453,1)){
				new SmithingSmelt(client, 5, 451);
			} else {
				client.getActionSender().sendMessage("You don't have enough ores.");
				client.getActionSender().sendWindowsRemoval();
			}
			break;
			case 29024:
			if(client.getActionAssistant().playerHasItem(451,1) && client.getActionAssistant().playerHasItem(453,1)){
				new SmithingSmelt(client, 10, 451);
			} else {
				client.getActionSender().sendMessage("You don't have enough ores.");
				client.getActionSender().sendWindowsRemoval();
			}
			break;
			case 29023:
			if(client.getActionAssistant().playerHasItem(451,1) && client.getActionAssistant().playerHasItem(453,1)){
				new SmithingSmelt(client, 20, 451);
			} else {
				client.getActionSender().sendMessage("You don't have enough ores.");
				client.getActionSender().sendWindowsRemoval();
			}
			break;
		case 55095:
			ItemDestroy.destroyItem(client);
		case 55096:
			client.getActionSender().sendWindowsRemoval();
			break;
		case 53152: //cook 1
			client.cookingAmount = 1;
			ActionManager.addNewRequest(client, 14, 4);
			break;
		case 53151: //cook 5
			client.cookingAmount = 5;
			ActionManager.addNewRequest(client, 14, 4);
			break;
		case 53150: //cook x
			client.cookingAmount = 28;
			ActionManager.addNewRequest(client, 14, 4);
			break;
		case 53149: //cook all
			client.cookingAmount = 28;
			ActionManager.addNewRequest(client, 14, 4);
			break;
		
		case 34185: //vambraces x1
			switch(client.crafting) {
			case 1745:
				HideCrafting.craftLeather(client, 1065, 1);
				break;
			case 2505:
				HideCrafting.craftLeather(client, 2487, 1);
				break;
			case 2507:
				HideCrafting.craftLeather(client, 2489, 1);
				break;
			case 2509:
				HideCrafting.craftLeather(client, 2491, 1);
				break;
			}
			client.getActionSender().sendWindowsRemoval();
			Fletching.startFletching(client, 1, "shortbow");
			break;
		case 34184: //vambraces x5
			switch(client.crafting) {
			case 1745:
				HideCrafting.craftLeather(client, 1065, 5);
				break;
			case 2505:
				HideCrafting.craftLeather(client, 2487, 5);
				break;
			case 2507:
				HideCrafting.craftLeather(client, 2489, 5);
				break;
			case 2509:
				HideCrafting.craftLeather(client, 2491, 5);
				break;
			}
			client.getActionSender().sendWindowsRemoval();
			Fletching.startFletching(client, 5, "shortbow");
			break;
		case 34183: //vambraces x10
			switch(client.crafting) {
			case 1745:
				HideCrafting.craftLeather(client, 1065, 10);
				break;
			case 2505:
				HideCrafting.craftLeather(client, 2487, 10);
				break;
			case 2507:
				HideCrafting.craftLeather(client, 2489, 10);
				break;
			case 2509:
				HideCrafting.craftLeather(client, 2491, 10);
				break;
			}
			Fletching.startFletching(client, 10, "shortbow");
			break;	
		case 34189: //chaps x1
			switch(client.crafting) {
			case 1745:
				HideCrafting.craftLeather(client, 1099, 1);
				break;
			case 2505:
				HideCrafting.craftLeather(client, 2493, 1);
				break;
			case 2507:
				HideCrafting.craftLeather(client, 2495, 1);
				break;
			case 2509:
				HideCrafting.craftLeather(client, 2497, 1);
				break;
			}
			client.getActionSender().sendWindowsRemoval();
			Fletching.startFletching(client, 1, "shaft");
			Fletching.setLogId(client, 52);
			break;
		case 34188: //chaps x5
			switch(client.crafting) {
			case 1745:
				HideCrafting.craftLeather(client, 1099, 5);
				break;
			case 2505:
				HideCrafting.craftLeather(client, 2493, 5);
				break;
			case 2507:
				HideCrafting.craftLeather(client, 2495, 5);
				break;
			case 2509:
				HideCrafting.craftLeather(client, 2497, 5);
				break;
			}
			client.getActionSender().sendWindowsRemoval();
			Fletching.startFletching(client, 5, "shaft");
			Fletching.setLogId(client, 52);
			break;
		case 34187: //chaps x10
			switch(client.crafting) {
			case 1745:
				HideCrafting.craftLeather(client, 1099, 10);
				break;
			case 2505:
				HideCrafting.craftLeather(client, 2493, 10);
				break;
			case 2507:
				HideCrafting.craftLeather(client, 2495, 10);
				break;
			case 2509:
				HideCrafting.craftLeather(client, 2497, 10);
				break;
			}
			client.getActionSender().sendWindowsRemoval();
			Fletching.startFletching(client, 10, "shaft");
			Fletching.setLogId(client, 52);
			break;
		case 34193: //body x1
			switch(client.crafting) {
			case 1745:
				HideCrafting.craftLeather(client, 1135, 1);
				break;
			case 2505:
				HideCrafting.craftLeather(client, 2499, 1);
				break;
			case 2507:
				HideCrafting.craftLeather(client, 2501, 1);
				break;
			case 2509:
				HideCrafting.craftLeather(client, 2503, 1);
				break;
			}
			client.getActionSender().sendWindowsRemoval();
			Fletching.startFletching(client, 1, "longbow");
			break;
		case 34192: //body x5
			switch(client.crafting) {
			case 1745:
				HideCrafting.craftLeather(client, 1135, 5);
				break;
			case 2505:
				HideCrafting.craftLeather(client, 2499, 5);
				break;
			case 2507:
				HideCrafting.craftLeather(client, 2501, 5);
				break;
			case 2509:
				HideCrafting.craftLeather(client, 2503, 5);
				break;
			}
			client.getActionSender().sendWindowsRemoval();
			Fletching.startFletching(client, 5, "longbow");
			break;
		case 34191: //body x10
			switch(client.crafting) {
			case 1745:
				HideCrafting.craftLeather(client, 1135, 10);
				break;
			case 2505:
				HideCrafting.craftLeather(client, 2499, 10);
				break;
			case 2507:
				HideCrafting.craftLeather(client, 2501, 10);
				break;
			case 2509:
				HideCrafting.craftLeather(client, 2503, 10);
				break;
			}
			client.getActionSender().sendWindowsRemoval();
			Fletching.startFletching(client, 10, "longbow");
			break;
			case 34186:
				Fletching.startFletching(client, 28, "shaft");
				Fletching.setLogId(client, 52);
				client.getActionSender().sendWindowsRemoval();
			break;
			case 34182:
				Fletching.startFletching(client, 28, "shortbow");
				client.getActionSender().sendWindowsRemoval();
			break;
			case 34190:
				Fletching.startFletching(client, 28, "longbow");
				client.getActionSender().sendWindowsRemoval();
			break;			
		
		/**Dueling**/	
		
		case 26065: // no forfeit
			client.duelSlot = -1;
			Duelling.selectRule(client, 0);
			break;
		
		case 26066: // no movement
		case 26048:
			client.duelSlot = -1;
			Duelling.selectRule(client, 1);
			break;
		
		case 26069: // no range
		case 26042:
			client.duelSlot = -1;
			Duelling.selectRule(client, 2);
			break;
		
		case 26070: // no melee
		case 26043:
			client.duelSlot = -1;
			Duelling.selectRule(client, 3);
			break;			
		
		case 26071: // no mage
		case 26041:
			client.duelSlot = -1;
			Duelling.selectRule(client, 4);
			break;
			
		case 26072: // no drinks
		case 26045:
			client.duelSlot = -1;
			Duelling.selectRule(client, 5);
			break;
		
		case 26073: // no food
		case 26046:
			client.duelSlot = -1;
			Duelling.selectRule(client, 6);
			break;
		
		case 26074: // no prayer
		case 26047:	
			client.duelSlot = -1;
			Duelling.selectRule(client, 7);
			break;
		
		case 26076: // obsticals
		case 26075:
			client.duelSlot = -1;
			Duelling.selectRule(client, 8);
			break;
		
		case 2158: // fun weapons
		case 2157:
			client.duelSlot = -1;
			Duelling.selectRule(client, 9);
			break;
		
		case 30136: // sp attack
		case 30137:
			client.duelSlot = -1;
			Duelling.selectRule(client, 10);
			break;
			
		case 53245: //no helm
			client.duelSlot = 0;
			Duelling.selectRule(client, 11);
			break;
			
		case 53246: // no cape
			client.duelSlot = 1;
			Duelling.selectRule(client, 12);
			break;
			
		case 53247: // no ammy
			client.duelSlot = 2;
			Duelling.selectRule(client, 13);
			break;
			
		case 53249: // no weapon.
			client.duelSlot = 3;
			Duelling.selectRule(client, 14);
			break;
			
		case 53250: // no body
			client.duelSlot = 4;
			Duelling.selectRule(client, 15);
			break;
			
		case 53251: // no shield
			client.duelSlot = 5;
			Duelling.selectRule(client, 16);
			break;
			
		case 53252: // no legs
			client.duelSlot = 7;
			Duelling.selectRule(client, 17);
			break;
			
		case 53255: // no gloves
			client.duelSlot = 9;
			Duelling.selectRule(client, 18);
			break;
			
		case 53254: // no boots
			client.duelSlot = 10;
			Duelling.selectRule(client, 19);
			break;
			
		case 53253: // no rings
			client.duelSlot = 12;
			Duelling.selectRule(client, 20);
			break;
			
		case 53248: // no arrows
			client.duelSlot = 13;
			Duelling.selectRule(client, 21);
			break;
		case 26018:
			Duelling.checkSetupOnAccept(client);
			break;
		case 25120:
			Duelling.confirmSecondDuel(client);
			break;
				
		/**
		 * Attack mode selections.
		 */
		case 1080:
		case 1177:
		case 6168:
		case 8234:
		case 9125:
		case 14218:
		case 18077:
		case 21200:
		case 33020:
		case 48010:
		case 22230:
			client.combatMode = CombatMode.setCombatMode(client, 0); 
			break;
		
		/**
		 * Defence mode selections.
		 */
		case 1078:
		case 6169:
		case 8235:
		case 9126:	
		case 14219:
		case 21201:
		case 33018:
		case 48008:
		case 18078:
		case 1175:
		case 22228:
			client.combatMode = CombatMode.setCombatMode(client, 1);
			break;
			
		/** 
		 * Strength mode selections.	
		 */
		case 8236:
		case 1079:
		case 1176:
		case 6170:
		case 6171:
		case 8237:
		case 9128:
		case 14221:
		case 21203:
		case 22229:
			client.combatMode = CombatMode.setCombatMode(client, 2); 
			break;	
			
		/**
		 * controlled mode.	
		 */
		case 9127:
		case 14220:
		case 21202:
		case 33019:
		case 48009:
		case 18079:
		case 18080:
			client.combatMode = CombatMode.setCombatMode(client, 3);
			break;
			
		/**
		 * Accurate ranged.
		 */
		case 6236:
		case 17102:
			client.combatMode = CombatMode.setCombatMode(client, 4);
			break;	
			
		/**
		 * Rapid ranged.
		 */
		case 6235:
		case 17101:
			client.combatMode = CombatMode.setCombatMode(client, 5);
			break;	
			
		/**
		 * longranged.
		 */
		case 6234:
		case 17100:
			client.combatMode = CombatMode.setCombatMode(client, 6);
			break;	

		case 150:
			client.autoRetaliate = !client.autoRetaliate;

			break;
			

		/**
		 * Vengence
		 */
		case 118098:
			Lunar.castVeng(client);
			break;
		/**
		 * Prayer
		 */
		case 97168:
		case 21233: // Thick Skin
			client.getPrayerHandler().activatePrayer(0);
			break;
		case 97170:
		case 21234: // Burst of Strength
			client.getPrayerHandler().activatePrayer(1);
			break;	
		case 97172:
		case 21235: // Clarity of though
			client.getPrayerHandler().activatePrayer(2);
			break;
		case 97178:	
		case 21236: // Rock skin
			client.getPrayerHandler().activatePrayer(3);
			break;	
		case 97180:
		case 21237: // Superhuman Strength
			client.getPrayerHandler().activatePrayer(4);
			break;	
		case 97182:
		case 21238: // Improved reflexes
			client.getPrayerHandler().activatePrayer(5);
			break;
		case 97184:			
		case 21239: // Rapid Restore
			client.getPrayerHandler().activatePrayer(6);
			break;
		case 97186:	
		case 21240: // Rapid Heal
			client.getPrayerHandler().activatePrayer(7);
			break;
		case 97188:
		case 21241: // Protect Item
			client.getPrayerHandler().activatePrayer(8);
			break;
		case 97194:
		case 21242: // Steel Skin
			client.getPrayerHandler().activatePrayer(9);
			break;
		case 97196:
		case 21243: // Ultimate Strength
			client.getPrayerHandler().activatePrayer(10);
			break;			
		case 97198:
		case 21244: // Incredible Reflexes
			client.getPrayerHandler().activatePrayer(11);
			break;		
		case 97200:
		case 21245: // Protect from Magic
			client.getPrayerHandler().activatePrayer(12);
			break;			
		case 97202:
		case 21246: // Protect from Missiles
			client.getPrayerHandler().activatePrayer(13);
			break;			
		case 97204:
		case 21247: // Protect from Melee
			client.getPrayerHandler().activatePrayer(14);
			break;			
		case 97210:
		case 2171: // Retribution
			client.getPrayerHandler().activatePrayer(15);
			break;			
		case 97212:
		case 2172: // Redemption
			client.getPrayerHandler().activatePrayer(16);
			break;			
		case 97214:
		case 2173: // Smite
			client.getPrayerHandler().activatePrayer(17);
			break;			
		case 77113:
		case 70092://chivalry
			client.getPrayerHandler().activatePrayer(18);
			break;			
		case 77115:
		case 70094://piety
			client.getPrayerHandler().activatePrayer(19);
			break;			
		case 77100: //sharp eye
			client.getPrayerHandler().activatePrayer(20);
			break;				
		case 77104: //hawk eye
			client.getPrayerHandler().activatePrayer(21);
			break;				
		case 77109: //Eagle eye
			client.getPrayerHandler().activatePrayer(22);
			break;	
		case 77102: //Mystic will
			client.getPrayerHandler().activatePrayer(23);
			break;	
		case 77106: //Mystic lore
			client.getPrayerHandler().activatePrayer(24);
			break;	
		case 77111: //Mystic might
			client.getPrayerHandler().activatePrayer(25);
			break;
		/**
		 * AutoCasting selections
		 */
		case 7038: // wind strike
			AutoCast.newSpell(client, 1152);
			break;
		case 7039: // water strike
			AutoCast.newSpell(client, 1154);
			break;
		case 7040: // earth strike
			AutoCast.newSpell(client, 1156);
			break;
		case 7041: // fire strike
			AutoCast.newSpell(client, 1158);
			break;
		case 7042: // wind bolt
			AutoCast.newSpell(client, 1160);
			break;
		case 7043: // water bolt
			AutoCast.newSpell(client,  1163);
			break;
		case 7044: // earth bolt 
			AutoCast.newSpell(client, 1166);
			break;
		case 7045: // fire bolt 
			AutoCast.newSpell(client, 1169);
			break;
		case 7046: // wind blast 
			AutoCast.newSpell(client, 1172);
			break;
		case 7047: // water blast  
			AutoCast.newSpell(client, 1175);
			break;
		case 7048: // earth blast  
			AutoCast.newSpell(client, 1177);
			break;
		case 7049: // fire blast 
			AutoCast.newSpell(client, 1181);
			break;
		case 7050:  // wind wave 
			AutoCast.newSpell(client, 1183);
			break;
		case 7051: // water wave
			AutoCast.newSpell(client, 1185);
			break;
		case 7052:  // earth blast 
			AutoCast.newSpell(client, 1188);
			break;
		case 7053:  // fire blast 
			AutoCast.newSpell(client, 1189);
			break;	
		case 51133: // smoke rush
			AutoCast.newSpell(client, 12939);
			break;		
		case 51185: // shadow rush
			AutoCast.newSpell(client, 12987);
			break;
		case 51091: // blood rush
			AutoCast.newSpell(client, 12901);
			break;
		case 24018: // ice rush
			AutoCast.newSpell(client, 12861);
			break;
		case 51159: // smoke burst
			AutoCast.newSpell(client, 12963);
			break;
		case 51211: // shadow burst
			AutoCast.newSpell(client, 13011);
			break;
		case 51111: // blood burst
			AutoCast.newSpell(client, 12919);
			break;		
		case 51069: //ice burst
			AutoCast.newSpell(client, 12881);
			break;
		case 51146: // smoke blitz
			AutoCast.newSpell(client, 12951);
			break;
		case 51198: // shadow blitz
			AutoCast.newSpell(client, 12999);
			break;
		case 51102: // blood blitz
			AutoCast.newSpell(client, 12911);
			break;		
		case 51058: //ice blitz
			AutoCast.newSpell(client, 12871);
			break;
		case 51172: //smoke barrage
			AutoCast.newSpell(client, 12975);
			break;
		case 51224: //shadow barrage
			AutoCast.newSpell(client, 13023);
			break;
		case 51122: //blood barrage
			AutoCast.newSpell(client, 12929);
			break;
		case 51080: //ice barrage
			AutoCast.newSpell(client, 12891);
			break;
		
		/**
		 * AutoCasting toggle button.
		 */
		case 1093:
			client.usingMagicDefence = !client.usingMagicDefence;
			break;

		case 1094:	
		case 1097:
			if (!AutoCast.checkForCorrectStaff(client)) {
				AutoCast.turnOff(client);
				break;
			}
			if (client.getSpellBook() == 2)
				client.getActionSender().sendSidebar(0, 1689);
			else if (client.getSpellBook() == 1)
				client.getActionSender().sendSidebar(0, 1829);	
			break;
			
		/**
		 * AutoCast cancel spell selection.	
		 */
		case 7212:
		case 24017:
			client.setAutoCastId(0);
			client.getActionSender().sendSidebar(0, 328);
			AutoCast.turnOff(client);
			break;

		/**
		 * Special attack bars.
		 */
		case 30108:
		case 33033:
		case 29038:
		case 29063:
		case 29188:
		case 29138:
		case 48023:
		case 29238:
		case 29113:
		case 29163:
			Specials.activateSpecial(client);
			break;
			
			
		/**
		* trading
		*/
		case 13092:
			client.getTradeHandler().acceptStage1();
			break;

		case 13218:
			client.getTradeHandler().acceptStage2();
			break;

		case 21011:
			client.takeAsNote = false;
			break;

		case 21010:
			client.takeAsNote = true;
			break;

		/**
		 * Teleports
		 */
		case 75010: //home tele
			Location.to(client, 0, "magicbook");
			break;
		case 4140: //varrock teleport
			Location.to(client, 1, "magicbook");
			break;
		case 4143: //lumbridge teleport
			Location.to(client, 2, "magicbook");
			break;
		case 4146: //Falador
			Location.to(client, 3, "magicbook");
			break;
		case 4150: //city teleports
			MenuTeleports.createTeleportMenu(client, 2);
			break;
		case 6004: //skill teleports
			MenuTeleports.createTeleportMenu(client, 3);
			break;
		case 6005: //wilderness teleports
			MenuTeleports.createTeleportMenu(client, 0);
			break;
		case 29031: //training Teleports
			MenuTeleports.createTeleportMenu(client, 1);
			break;
		case 50235: //paddewwa teleport
			MenuTeleports.createTeleportMenu(client, 0);//pking
			break;
		case 50245: //senntisten teleport
			MenuTeleports.createTeleportMenu(client, 3);//skilling
			break;
		case 50253: //kharyrll teleport
			MenuTeleports.createTeleportMenu(client, 1);//training
			
			break;
		case 51005: //lassar teleport
			Location.to(client, 11, "magicbook");
			break;
		case 51013: //dareeyak teleport
			Location.to(client, 12, "magicbook");
			break;
		case 51023: //carrallangar teleport
			MenuTeleports.createTeleportMenu(client, 2);//city
			break;
		case 51031: //duel arena
			Location.to(client, 14, "magicbook");
			break;
		case 51039: //home
			Location.to(client, 0, "magicbook");
			break;
		
		/**
		 * Run/Walk button toggling.
		 */
		case 152:
		case 74214:
			client.setRunning(!client.isRunning);
			client.getActionSender().sendConfig(429, client.isRunning ? 1 : 0);
			break;

		/**
		 * Logout button.
		 */
		case 9154:
			client.getActionSender().sendLogout();
			break;

		/**
		 * Close window button.
		 */
		case 130:
			client.getActionSender().sendWindowsRemoval();
			break;
			
		case 58253:
			client.getActionSender().sendInterface(15106);
			break;
		
		/**
		 * Cow hide to leather tanning
		 */
		case 57225:
			Tanning.tanHide(client, 1739, 1741, 1);
			break;
		case 57217:
			Tanning.tanHide(client, 1739, 1741, 5);
			break;
		case 57209:
			break;
		case 57201:
			Tanning.tanHide(client, 1739, 1741, 28);
			break;
			
		/** 
		 * cow hide to hard leather tanning	
		 */	
		case 57229:
			Tanning.tanHide(client, 1739, 1743, 1);
			break;
		case 57221:
			Tanning.tanHide(client, 1739, 1743, 5);
			break;
		case 57213:
			break;
		case 57205:
			Tanning.tanHide(client, 1739, 1743, 28);
			break;
			
		/** 
		 * Green dragon hide tanning	
		 */	
		case 57219:
			Tanning.tanHide(client, 1753, 1745, 5);
			break;
			
		case 57227:
			Tanning.tanHide(client, 1753, 1745, 1);
			
			break;
		case 57211:
			break;
		case 57203:
			Tanning.tanHide(client, 1753, 1745, 28);
			break;
			
		/** 
		* Blue dragon hide tanning	
		 */			
		case 57220:
			Tanning.tanHide(client, 1751, 2505, 5);
			break;
		case 57228:
			Tanning.tanHide(client, 1751, 2505, 1);
			break;
		case 57212:
			break;
		case 57204:
			Tanning.tanHide(client, 1751, 2505, 28);
			break;
			
		/** 
		 * Red dragon hide tanning	
		 */
		case 57223:
			Tanning.tanHide(client, 1749, 2507, 5);
			break;
		case 57231:
			Tanning.tanHide(client, 1749, 2507, 1);
			break;
		case 57215:
			break;
		case 57207:
			Tanning.tanHide(client, 1749, 2507, 28);
			break;
			
		/** 
		 * Black dragon hide tanning	
		 */
		case 57224:
			Tanning.tanHide(client, 1747, 2509, 5);
			break;
		case 57232:
			Tanning.tanHide(client, 1747, 2509, 1);
			break;
		case 57216:
			break;
		case 57208:
			Tanning.tanHide(client, 1747, 2509, 28);
		break;
		
		/**
		 * Charge
		 */
		case 4169: 
			GodSpells.charge(client);
			break;
			
		/**
		 * All of our emotions
		 */
		case 168: 
		case 169: 
		case 164: 
		case 165: 
		case 162:
		case 163:
		case 52058:
		case 171:
		case 167:
		case 170:
		case 52054:
		case 52056:
		case 166:
		case 52051:
		case 52052:
		case 52053:
		case 161:
		case 43092:
		case 52050:
		case 52055:
		case 172:
		case 52057:
		case 52071:
		case 52072:
		case 2155:
		case 25103:
		case 25106:
		case 2154:
		case 72032:
		case 72033:
		case 59062:
		case 72254:
		case 74108:
			EmotionTablet.doEmote(client, actionButtonId);
			break;
			
		case 59135://center
			FightPits.fightPitsOrb("Centre", 15239, client);
			client.teleportToX = 2398;
			client.teleportToY = 5150;
			client.setHeightLevel(0);
			FightPits.hidePlayer(client);
			break;

		case 59136://north-west
			FightPits.fightPitsOrb("North-West",15240, client);
			client.teleportToX = 2384;
			client.teleportToY = 5157;
			client.setHeightLevel(0);
			FightPits.hidePlayer(client);
			break;

		case 59137://north-east
			FightPits.fightPitsOrb("North-East",15241, client);
			client.teleportToX = 2409;
			client.teleportToY = 5158;
			client.setHeightLevel(0);
			FightPits.hidePlayer(client);
			break;

		case 59138://south-east
			FightPits.fightPitsOrb("South-East",15242, client);
			client.teleportToX = 2411;
			client.teleportToY = 5137;
			client.setHeightLevel(0);
			FightPits.hidePlayer(client);
			break;

		case 59139://south-west
			FightPits.fightPitsOrb("South-West",15243, client);
			client.teleportToX = 2388;
			client.teleportToY = 5138;
			client.setHeightLevel(0);
			FightPits.hidePlayer(client);
			break;
				
		case 17111:// exit fight pits viewing orb
			FightPits.fightPitsOrb("Centre",15239, client);
			client.setViewingOrb(false);
			client.getActionSender().sendWindowsRemoval();
			client.getActionSender().sendFrame99(0);
			client.getActionSender().sendSidebar(10, 2449);
			client.teleportToX = 2399;
			client.teleportToY = 5171;
			client.setHeightLevel(0);
			client.isNPC = false;
			client.updateRequired = true;
			client.appearanceUpdateRequired = true;
			break;
			
		case 9190:
		case 9191:
		case 9192:
		case 9193:
		case 9194:
			if (client.teleportConfig >= 0) {
				MenuTeleports.runTeleport(client, actionButtonId);
				break;
			}
			if (client.jewleryId != 1337) {
				JewleryTeleporting.useGlory(client, actionButtonId);
			}
			client.getActionSender().sendWindowsRemoval();
			break;
			
		case 9168:
		case 9167:
			JewleryTeleporting.useRingOfDueling(client, actionButtonId);
			client.getActionSender().sendWindowsRemoval();
			break;
			
		/**
		 * No where.
		 */
		
		case 9169:
			client.getActionSender().sendWindowsRemoval();
			break;
			
			
		default:
			client.println_debug("Unhandled Action Button: " + actionButtonId);
			break;
		}
	}

}