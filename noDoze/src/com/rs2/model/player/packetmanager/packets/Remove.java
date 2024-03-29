package com.rs2.model.player.packetmanager.packets;

/**
 * Remove items     
 *
 * @author Ultimate
 *
 **/

import com.rs2.GameEngine;

//import com.rs2.content.actions.ActionManager;
import com.rs2.content.skills.crafting.GemCrafting;
import com.rs2.content.skills.smithing.SmithingMakeItem;
import com.rs2.model.Shop;
import com.rs2.model.player.Client;
import com.rs2.model.player.packetmanager.Packet;
import com.rs2.util.Misc;

public class Remove implements Packet {
	

	
	public void handlePacket(Client client, int packetType, int packetSize) {
		int interfaceID = client.inStream.readUnsignedWordA();
		int removeSlot = client.inStream.readUnsignedWordA();
		int removeID = client.inStream.readUnsignedWordA();
		System.out.println(interfaceID);
		//ActionManager.destructActions(client);
		if (interfaceID == 1688) {
			if (client.playerEquipment[removeSlot] > 0)
				client.getEquipment().removeItem(removeID, removeSlot);
		} else if (interfaceID == 5064) {
			client.getContainerAssistant().bankItem(removeID, removeSlot, 1);
		} else if (interfaceID == 1119 || interfaceID == 1120 || interfaceID == 1121 || interfaceID == 1122 || interfaceID == 1123) {
			new SmithingMakeItem(client, removeID, 1);
		} else if (interfaceID == 5382) {
			client.getContainerAssistant().fromBank(removeID, removeSlot, 1);
		} else if (interfaceID == 7423) {
			client.getContainerAssistant().bankItem(removeID, removeSlot, 1);
			client.getActionSender().sendItemReset(7423);
		} else if (interfaceID == 3823) {
			
			String name = GameEngine.getItemManager().getItemDefinition(
					removeID).getName();
			
			if (!client.getExtraData().containsKey("shop"))
				return;
			
			int shop = (Integer) client.getExtraData().get("shop");
			
			Shop s = GameEngine.getShopManager().getShops().get(shop);
			
			if (s == null)
				return;
			
			String currency = GameEngine.getItemManager().getItemDefinition(
					s.getCurrency()).getName();
			int value = s.getItemBuyValue(removeID);
			client.getActionSender().sendMessage(
					name + ": shop will buy for: " + value + " "+currency+"s"
							+ Misc.formatAmount(value));

		} else if (interfaceID == 3900) {
			String name = GameEngine.getItemManager().getItemDefinition(
					removeID).getName();
			if (!client.getExtraData().containsKey("shop"))
				return;
			int shop = (Integer) client.getExtraData().get("shop");
			Shop s = GameEngine.getShopManager().getShops().get(shop);
			if (s == null)
				return;
			int value = s.getItemSellValue(removeID);
			if (value == 0) 
				value = 1;
			String currency = GameEngine.getItemManager().getItemDefinition(
					s.getCurrency()).getName();
			client.getActionSender().sendMessage(
					name + ": currently costs " + value + " "+currency+"s"
							+ Misc.formatAmount(value));
		} else if (interfaceID == 3322) {
			client.getTradeHandler().tradeItem(removeID, removeSlot, 1);
		} else if (interfaceID == 3415) {
			client.getTradeHandler().fromTrade(removeID, removeSlot, 1);
		} else if (interfaceID == 4233 || interfaceID == 4239 || interfaceID == 4245 ) {
			for (int i = 0; i < GemCrafting.ITEMS.length; i++) {
				if (GemCrafting.ITEMS[i][0] == removeID) {
					GemCrafting.startCrafter(client, GemCrafting.ITEMS[i][1], 1, GemCrafting.ITEMS[i][2]);
				}
			}
		}
	}
}