package com.rs2.content.minigames;

import com.rs2.model.player.Client;

public class StayAlive {
	
	public static int[] rewards = {0, 1, 2};
	public static int[][] npcSpawns = {{11,89,3121,3121,2000}};
	public static int round = 0, limit = 0;

	public static void Round(Client client) {
		if(limit >= 0) {
			limit--;
			return;
		}
		client.getActionSender().sendInventoryItem(995, rewards[round]);
		if(rewards[round] >= 0) {
			client.getActionSender().sendMessage("Congratulations you completed round " + round + ".");
			client.getActionSender().sendMessage("You recieved " + rewards[round] + " Coints.");
		}
		round++;
		limit = npcSpawns[round].length;
		for(int i = 0; i < npcSpawns[round].length; i++)	{
		}
	}

	public static boolean IsInStayAlive(Client client) {
		return(client.getAbsX() > 2242 && client.getAbsX() < 2444 && client.getAbsY() > 2442 && client.getAbsY() < 2444);
	}
}
