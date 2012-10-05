package com.rs2.content.skills.crafting;

import com.rs2.model.player.Client;

/**
 * 
 * @author killamess
 *
 */

public class Pottery {
	
	public static void makeSoftClay(Client client) {
		if (client.getActionAssistant().isItemInBag(434) && client.getActionAssistant().isItemInBag(1937)) {
			client.getActionAssistant().deleteItem(1937, 1);
			client.getActionSender().sendInventoryItem(1935, 1);
			client.getActionAssistant().deleteItem(434, 1);
			client.getActionSender().sendInventoryItem(1761, 1);
			client.getActionSender().sendMessage("You mix the clay and the water.");
			client.getActionSender().sendMessage("You now have some soft, workable clay.");
		}
	}


}
