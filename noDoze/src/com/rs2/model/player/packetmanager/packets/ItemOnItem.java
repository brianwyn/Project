package com.rs2.model.player.packetmanager.packets;
 
import com.rs2.content.actions.ActionManager;
import com.rs2.content.skills.crafting.GemCrafting;
import com.rs2.content.skills.crafting.HideCrafting;
import com.rs2.content.skills.crafting.Pottery;
import com.rs2.content.skills.fletching.Fletching;
import com.rs2.content.skills.herblore.PotionMixing;
import com.rs2.model.player.Client;
import com.rs2.model.player.packetmanager.Packet;
 
/**
 * Item on Item
 *
 * @author Killamess
 */
 
public class ItemOnItem implements Packet {
       
	public static final int BALL_OF_WALL = 1759;
 	public static final int CLAY = 434;
  	public static final int JUG_OF_WATER = 1937;
 	public static final int CHISEL = 1755;
 	public static final int NEEDLE = 1733, THREAD = 1734;
               
 	@Override
	public void handlePacket(Client client, int packetType, int packetSize) {
 		int usedWithSlot = client.inStream.readUnsignedWord() ;
 		int itemUsedSlot = client.inStream.readUnsignedWordA() ;
               
 		int useWith = client.playerItems[usedWithSlot]-1;
 		int itemUsed = client.playerItems[itemUsedSlot]-1;
 		
		if (Fletching.isFletchable(client, itemUsed, useWith)) {
			return;
		}
             
 		if (itemUsed == NEEDLE || useWith == NEEDLE) {
 			if (useWith == 1745 && itemUsed == NEEDLE || itemUsed == 1745 && useWith == NEEDLE) {
 				client.crafting = 1745;
 				HideCrafting.createDragonHideInterface(client, 0);
 				return;
 			} else if (useWith == 2505 && itemUsed == NEEDLE || itemUsed == 2505 && useWith == NEEDLE) {
 				client.crafting = 2505;
 				HideCrafting.createDragonHideInterface(client, 1);
 				return;
 			} else if (useWith == 2507 && itemUsed == NEEDLE || itemUsed == 2507 && useWith == NEEDLE) {
 				client.crafting = 2507;
 				HideCrafting.createDragonHideInterface(client, 2);
 				return;
 			} else if (useWith == 2509 && itemUsed == NEEDLE || itemUsed == 2509 && useWith == NEEDLE) {
 				client.crafting = 2509;
 				HideCrafting.createDragonHideInterface(client, 3);
 				return;
 			}
 		}
 		if (itemUsed == PotionMixing.VIAL || useWith == PotionMixing.VIAL) {
 			if (itemUsed == PotionMixing.VIAL) {
 				PotionMixing.makeUnfinished(client, PotionMixing.VIAL, useWith);
 			} else {
 				PotionMixing.makeUnfinished(client, PotionMixing.VIAL, itemUsed);
 			}
 		}
 		if (PotionMixing.isUnFinishedPotion(client, itemUsed) || PotionMixing.isUnFinishedPotion(client, useWith)) {
 			if (PotionMixing.isExtraMixture(client, itemUsed)) {
 				PotionMixing.makeFinished(client, useWith);
 			} else  if (PotionMixing.isExtraMixture(client, useWith)) {
 				PotionMixing.makeFinished(client, itemUsed);
 			}
 		}
 		if (CHISEL == useWith || CHISEL == itemUsed) {
 			for (int i = 0; i < GemCrafting.craftGem.length; i++) {
 				if (CHISEL != useWith) {
 					if (useWith == GemCrafting.craftGem[i][0]) {
 						GemCrafting.craftGem(client, useWith);
 						return;
 					}
 				} else if (CHISEL != itemUsed) {
 					if (itemUsed == GemCrafting.craftGem[i][0]) {
 						GemCrafting.craftGem(client, itemUsed);
 						return;
 					}
 				}
 			}
 		}
 		ActionManager.destructActions(client.getUsername());
 		if (CLAY == useWith && JUG_OF_WATER == itemUsed || CLAY == itemUsed && JUG_OF_WATER == useWith) {
 			Pottery.makeSoftClay(client);
 			return;
 		}
 		if (BALL_OF_WALL == useWith || BALL_OF_WALL == itemUsed) {
 			for (int i = 0; i < GemCrafting.mendItems.length; i++) {
 				if (GemCrafting.mendItems[i][0] == useWith || GemCrafting.mendItems[i][0] == itemUsed) {
 					GemCrafting.string(client, i);
 					break;
 				}
 			}
 		}
 	}
}