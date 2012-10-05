package com.rs2.content.minigames;

import com.rs2.model.player.Client;
import com.rs2.tiles.TileManager;

public class Jad {
	
	public static boolean inArea(Client client) {
		int[] myLocation = TileManager.currentLocation(client);
			
		return 
			myLocation[0] >= 2897 && myLocation[0] <= 2924 &&
			myLocation[1] >= 3598 && myLocation[1] <= 3628;
	}

}
