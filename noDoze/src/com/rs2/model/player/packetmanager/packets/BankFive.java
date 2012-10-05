package com.rs2.model.player.packetmanager.packets;

import com.rs2.content.actions.ActionManager;
import com.rs2.content.skills.crafting.GemCrafting;
import com.rs2.content.skills.smithing.SmithingMakeItem;
import com.rs2.model.player.Client;
import com.rs2.model.player.packetmanager.Packet;

/**
 * Bank ten items
 * 
 * @author Graham
 */
public class BankFive implements Packet {

	@Override
	public void handlePacket(Client client, int packetType, int packetSize) {
		int interfaceID = client.inStream.readSignedWordBigEndianA();
		int removeID = client.inStream.readSignedWordBigEndianA();
		int removeSlot = client.inStream.readSignedWordBigEndian();
		ActionManager.destructActions(client.getUsername());
		System.out.println(interfaceID);
		if (interfaceID == 5064) {
			client.getContainerAssistant().bankItem(removeID, removeSlot, 5);
		} else if (interfaceID == 1119 || interfaceID == 1120 || interfaceID == 1121 || interfaceID == 1122 || interfaceID == 1123) {
			new SmithingMakeItem(client, removeID, 5);
		} else if (interfaceID == 5382) {
			client.getContainerAssistant().fromBank(removeID, removeSlot, 5);
		} else if (interfaceID == 7423) {
			client.getContainerAssistant().bankItem(removeID, removeSlot, 5);
			client.getActionSender().sendItemReset(7423);
		} else if (interfaceID == 3823) {
			client.getContainerAssistant().sellItem(removeID, removeSlot, 1);
		} else if (interfaceID == 3900) {
			client.getContainerAssistant().buyItem(removeID, removeSlot, 1);
		} else if (interfaceID == 3322) {
			client.getTradeHandler().tradeItem(removeID, removeSlot, 5);
		} else if (interfaceID == 3415) {
			client.getTradeHandler().fromTrade(
					client.getTradeHandler().getOffer()[removeSlot] - 1,
					removeSlot, 5);
		} else if (interfaceID == 4233 || interfaceID == 4239 || interfaceID == 4245 ) {
			for (int i = 0; i < GemCrafting.ITEMS.length; i++) {
				if (GemCrafting.ITEMS[i][0] == removeID) {
					GemCrafting.startCrafter(client, GemCrafting.ITEMS[i][1], 5, GemCrafting.ITEMS[i][2]);
				}
			}
		}
		
	}

}
