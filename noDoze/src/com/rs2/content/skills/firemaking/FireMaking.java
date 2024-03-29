package com.rs2.content.skills.firemaking;

import java.util.ArrayList;
import java.util.List;

import com.rs2.GameEngine;
import com.rs2.model.WorldObject;
import com.rs2.model.WorldObject.Face;
import com.rs2.model.player.Client;
import com.rs2.tiles.FlagMap;

/**
 * 
 * @author killamess
 *
 */

public class FireMaking {
	
	public static List<Fires> fires = new ArrayList<Fires>();
	
	public static void makeFire(Client client, int logType) {
		
		for (Fires fire : fires) {
			if (fire.getLocation()[0] == client.getAbsX() && fire.getLocation()[1] == client.getAbsY()) {
				client.getActionSender().sendMessage("You can't lite a fire here.");
				return;
			}
			
		}
		
	
	}
	
	public static void addNewFire(Client client, int[] location, int delay) {
		
		client.forceMovement[0] = location[0] - 1;
		client.forceMovement[1] = location[1];
		client.forceMovement[2] = location[2];
		
		if (!FlagMap.locationOccupied(client.forceMovement, client))
			client.forceMove = true;
		else {
			client.forceMovement[0] = location[0] + 1;
			client.forceMovement[1] = location[1];
			client.forceMovement[2] = location[2];
			
			if (!FlagMap.locationOccupied(client.forceMovement, client))
				client.forceMove = true;
			else {
				client.getActionSender().sendMessage("You can't lite a fire here.");
				return;
			}
		}
		fires.add(new Fires(client.getUsername(), location, 10));
		GameEngine.getObjectManager().addObject(new WorldObject(2732, location[0], location[1], location[2], Face.NORTH, 10));
	}


	
	
	
	
	
	

}
