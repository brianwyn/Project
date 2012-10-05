package com.rs2.content.skills.crafting;

import com.rs2.GameEngine;
import com.rs2.content.actions.ActionManager;
import com.rs2.model.Entity;
import com.rs2.model.player.Client;

/**
 * 
 * @author killamess
 *
 */

public class HideCrafting {
	
	public static final int[][] dragonhide = {
		{ 1065, 1099, 1135 }, //green
		{ 2487, 2493, 2499 }, //blue
		{ 2489, 2495, 2501 }, //red
		{ 2491, 2497, 2503 }, //black
	};
	 
	public static void createDragonHideInterface(Entity entity, int set) {
		System.out.println(1 >> 3);
		if (set > 3 || set < 0)
			return;
		
		if (!(entity instanceof Client))
			return;
		
		Client client = (Client) entity;
		
		String[] hideNames = new String[3];
		
		hideNames[0] = GameEngine.getItemManager().getItemDefinition(dragonhide[set][0]).getName();
		hideNames[1] = GameEngine.getItemManager().getItemDefinition(dragonhide[set][1]).getName();
		hideNames[2] = GameEngine.getItemManager().getItemDefinition(dragonhide[set][2]).getName();
		
		client.getActionSender().sendFrame164(8880); //8880
		client.getActionSender().sendQuest(hideNames[0], 8897); //8897
		client.getActionSender().sendQuest(hideNames[1], 8893); //8893
		client.getActionSender().sendQuest(hideNames[2], 8889); //8889
		client.getActionSender().sendFrame246(8883 , 250, dragonhide[set][0]);
		client.getActionSender().sendFrame246(8884 , 200, dragonhide[set][1]);
		client.getActionSender().sendFrame246(8885 , 200, dragonhide[set][2]);
	}	
	
	public static void craftLeather(Client client, int item, int amount) {
		client.craftingItem = item;
		client.craftingAmount = amount;
		ActionManager.addNewRequest(client, 8, 2);	
	}
}
