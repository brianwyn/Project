package com.rs2.model.combat.content;

import com.rs2.content.minigames.FunPk;
import com.rs2.model.player.Client;
import com.rs2.util.Misc;

/**
 * 
 * @author killamess
 *
 */
public class PkTokens {
	
	public static void rewardPlayer(Client client, Client killed) {
		
		if (client.alreadyInList(killed)) {
			client.getActionSender().sendMessage("You have recently killed "+ killed.getUsername().toLowerCase()+" so you receive no tokens.");
			return;
		} else {
			client.addNewKiller(killed);
		}
		
		int rewardForKill = 100 - (client.getCombatLevel() - killed.getCombatLevel());
		
		int amountReceived = 1 + (Misc.random((rewardForKill) - 25) + 25);
		
		if(FunPk.IsInFunPK(client))
			amountReceived = amountReceived / 3;
		
		if (client.getActionAssistant().freeSlots() > 0) {
			if (amountReceived == 1) {
				client.getActionSender().sendInventoryItem(8851, amountReceived);
				client.getActionSender().sendMessage("You have received "+ amountReceived +" token for killing "+ killed.getUsername().toLowerCase()+".");
			} else if (amountReceived > 0) {
				client.getActionSender().sendInventoryItem(8851, amountReceived);
				client.getActionSender().sendMessage("You have received "+ amountReceived +" tokens for killing "+ killed.getUsername().toLowerCase()+".");
			} else if (amountReceived < 1) {
				client.removeKiller(killed);
				client.getActionSender().sendMessage("You did not receive any tokens for killing "+ killed.getUsername().toLowerCase()+".");
			}
		} else {
			for (int i = 0; i < client.getPlayerBankSize(); i++) {
				if (client.bankItems[i] == 8851 + 1) {
					client.bankItemsN[i] += amountReceived;
					break;
				} else if (client.bankItems[i] < 1) {
					client.bankItems[i] = 8851 + 1;
					client.bankItemsN[i] = amountReceived;	
					break;
				}
			}
			client.getActionSender().sendBankReset();
			client.getActionSender().sendItemReset(5064);
			client.getActionSender().sendMessage("You have received "+ amountReceived +" tokens for killing "+ killed.getUsername().toLowerCase()+".");
			client.getActionSender().sendMessage("These tokens have been automatically added to your bank account.");
		}
	}

}
