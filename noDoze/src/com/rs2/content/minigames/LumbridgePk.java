package com.rs2.content.minigames;

import com.rs2.model.Entity;
import com.rs2.tiles.TileManager;

/**
 * 
 * @author Killamess
 *
 */

public class LumbridgePk {
	
	public static boolean inLumbridgePkArea(Entity entity) {
		int[] myLocation = TileManager.currentLocation(entity);
		
		return 
			myLocation[0] >= 3135 && myLocation[0] <= 3266 &&
			myLocation[1] >= 3146 && myLocation[1] <= 3329;
	}
	
	public static boolean inSafeArea(Entity entity) {
		int[] myLocation = TileManager.currentLocation(entity);
		
		return 
			myLocation[0] >= 3238 && myLocation[0] <= 3251 &&
			myLocation[1] >= 3191 && myLocation[1] <= 3203 ||
			
			myLocation[0] >= 3247 && myLocation[0] <= 3252 &&
			myLocation[1] >= 3190 && myLocation[1] <= 3195;
	}
	

}
