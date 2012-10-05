package com.rs2.model.player.packetmanager.packets;

/**
 * @author Ultimate
 */

import com.rs2.content.UsingItemHandler;
import com.rs2.content.actions.ActionManager;
import com.rs2.content.skills.herblore.Cleaning;
import com.rs2.model.player.Client;
import com.rs2.model.player.packetmanager.Packet;
import com.rs2.util.Misc;

public class UseItem implements Packet {

		@Override
		public void handlePacket(Client client, int packetType, int packetSize) {
			
			@SuppressWarnings("unused")
			int interfaaceID = client.inStream.readSignedWordBigEndianA();
			int itemSlot = client.inStream.readUnsignedWordA();
			int itemID = client.inStream.readUnsignedWordBigEndian();
			ActionManager.destructActions(client.getUsername());
			if (client.playerItems[itemSlot] == itemID + 1) {
				
				if (itemID == 405 && client.getActionAssistant().isItemInBag(itemID)) {
					client.getActionAssistant().deleteItem(itemID, itemSlot, 1);
					client.getActionSender().sendInventoryItem(995, casketRewards[Misc.random(casketRewards.length - 1)]);
				}
				UsingItemHandler.useItem(client, itemID, itemSlot);
				Cleaning.cleanHerb(client, itemID, itemSlot);
			}
		}
		private int[] casketRewards = { 
			1000,1000,1000,1000,1000,1000,1000,1000,
			2500,1000,1000,1000,1000,1000,1000,
			5000,2500,1000,1000,1000,1000,
			7500,5000,2500,1000,1000,
			10000,7500,5000,2500,
			25000,10000,7500,
			50000,25000,
			100000
		};
		
		
}
