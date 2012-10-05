package com.rs2.model.player.packetmanager.packets;

import com.rs2.content.actions.ActionManager;
import com.rs2.content.skills.smithing.SmithingMakeItem;
import com.rs2.model.player.Client;
import com.rs2.model.player.packetmanager.Packet;

/**
 * Bank x items packets
 * 
 * @author Graham
 */
public class BankX implements Packet {

	public static final int PART1 = 135, PART2 = 208;

	@Override
	public void handlePacket(Client client, int packetType, int packetSize) {
		ActionManager.destructActions(client.getUsername());
		if(packetType == PART1) {	
			client.setBankXremoveSlot(client.inStream.readSignedWordBigEndian());
			client.setBankXinterfaceID(client.inStream.readUnsignedWordA());
			client.setBankXremoveID(client.inStream.readSignedWordBigEndian());
			System.out.println("BankXinterfaceId "+client.getBankXinterfaceID());
			if (client.getBankXinterfaceID() != 3900) {
				client.outStream.createFrame(27);
			} else if (client.getBankXinterfaceID() == 3900) {
				client.getContainerAssistant().buyItem(client.getBankXremoveID(), client.getBankXremoveSlot(), 20);
				client.setBankXremoveSlot(0);
				client.setBankXremoveID(0);
			}
		}
			
		if (packetType == PART2) {
			int bankXamount = client.inStream.readDWord();
			System.out.println("BankX: bankXamount "+bankXamount);

			if (client.getBankXinterfaceID() == 5064) {
				client.getContainerAssistant().bankItem(client.playerItems[client.getBankXremoveSlot()], client.getBankXremoveSlot(), bankXamount);
			} else if (client.getBankXinterfaceID() == 24452 || client.getBankXinterfaceID() == 24708 || client.getBankXinterfaceID() == 24964 || client.getBankXinterfaceID() == 25220 || client.getBankXinterfaceID() == 25476) {
				new SmithingMakeItem(client, client.getBankXremoveID(), bankXamount);
			} else if (client.getBankXinterfaceID() == 5382) {
				client.getContainerAssistant().fromBank(client.bankItems[client.getBankXremoveSlot()], client.getBankXremoveSlot(), bankXamount);
				
			} else if (client.getBankXinterfaceID() == 7423) {
				client.getContainerAssistant().bankItem(client.playerItems[client.getBankXremoveSlot()], client.getBankXremoveSlot(), bankXamount);
				client.getActionSender().sendItemReset(7423);
			} else if (client.getBankXinterfaceID() == 3322) {
				if(!client.getActionAssistant().playerHasItem(client.playerItems[client.getBankXremoveSlot()]-1, bankXamount))
					return;
				client.getTradeHandler().tradeItem(client.playerItems[client.getBankXremoveSlot()] - 1, client.getBankXremoveSlot(), bankXamount);
			} else if (client.getBankXinterfaceID() == 3415) {
				if(!client.getActionAssistant().playerHasItem(client.playerItems[client.getBankXremoveSlot()]-1, bankXamount))
					return;
				client.getTradeHandler().fromTrade(client.getTradeHandler().getOffer()[client.getBankXremoveSlot()] - 1, client.getBankXremoveSlot(), bankXamount);
			}
		}
		
	}
}