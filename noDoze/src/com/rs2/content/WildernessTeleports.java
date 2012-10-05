package com.rs2.content;

import com.rs2.content.controllers.Location;
import com.rs2.model.player.Client;
import com.rs2.model.player.Player;
import com.rs2.tiles.TileManager;
import com.rs2.util.Misc;
import com.rs2.world.PlayerManager;

/**
 * 
 * @author Killamess
 * unable to continue as no
 * object replacement methods 
 * supported yet.
 *
 */
public class WildernessTeleports {
	
	public static int obelisktimer[] = new int[6];
	
	public static final int[][] obeliskLocations = { //these are dead centre of each one.
		{ 3156, 3620, 0 },
	};
	
	public static String[] getUsersIn(int obeliskId) {
		
		String[] listOfNames = new String[100];
		int count = 0;
		
		for (Player p : PlayerManager.getSingleton().getPlayers()) {
			
			if (p == null)
				continue;
			
			if (p.getUsername() == null)
				continue;
			
			if (inObelisk((Client) p, obeliskId))
				listOfNames[count++] = p.getUsername();		
		}
		return listOfNames;
	}
	
	public static void teleportGroup(int obeliskId) {
		
		String[] teleportPlayers = getUsersIn(obeliskId);
		
		int next = Misc.random(obeliskLocations.length - 1);
		
		for (Player p : PlayerManager.getSingleton().getPlayers()) {
			
			if (p == null)
				continue;
			
			if (p.getUsername() == null)
				continue;
			
			for (int i = 0; i < teleportPlayers.length; i++) {
				if (p.getUsername().equalsIgnoreCase(teleportPlayers[i])) {
					Location.addNewRequest(p, obeliskLocations[next][0], obeliskLocations[next][1], obeliskLocations[next][2], 3);
				}
			}
		}	
	}
	
	public static boolean inObelisk(Client client, int id) {
		return TileManager.calculateDistance(TileManager.currentLocation(client), obeliskLocations[id]) <= 1;
	}
	
	public void resetObelisk(int obelsikId){
		 /*
		if(obelsikId == 0){
			GameEngine.getObjectManager().replaceObject(new WorldObject(3305, 3914, 14831, -1, 10), new WorldObject(3305, 3914, 14831, -1, 10), 10);
			GameEngine.getObjectManager().replaceObject(new WorldObject(3305, 3918, 14831, -1, 10), new WorldObject(3305, 3918, 14831, -1, 10), 10);
			GameEngine.getObjectManager().replaceObject(new WorldObject(3309, 3918, 14831, -1, 10), new WorldObject(3309, 3918, 14831, -1, 10), 10);
			GameEngine.getObjectManager().replaceObject(new WorldObject(3309, 3914, 14831, -1, 10), new WorldObject(3309, 3914, 14831, -1, 10), 10);
		} else if(obelsikId == 1){
			GameEngine.getObjectManager().replaceObject(new WorldObject(3104, 3796, 14828, -1, 10), new WorldObject(3104, 3796, 14828, -1, 10), 10);
			GameEngine.getObjectManager().replaceObject(new WorldObject(3104, 3792, 14828, -1, 10), new WorldObject(3104, 3792, 14828, -1, 10), 10);
			GameEngine.getObjectManager().replaceObject(new WorldObject(3108, 3796, 14828, -1, 10), new WorldObject(3108, 3796, 14828, -1, 10), 10);
			GameEngine.getObjectManager().replaceObject(new WorldObject(3108, 3792, 14828, -1, 10), new WorldObject(3108, 3792, 14828, -1, 10), 10);	
		} else if(obelsikId == 2){
			GameEngine.getObjectManager().replaceObject(new WorldObject(3154, 3618, 14829, -1, 10), new WorldObject(3154, 3618, 14829, -1, 10), 10);
			GameEngine.getObjectManager().replaceObject(new WorldObject(3154, 3622, 14829, -1, 10), new WorldObject(3154, 3622, 14829, -1, 10), 10);
			GameEngine.getObjectManager().replaceObject(new WorldObject(3158, 3618, 14829, -1, 10), new WorldObject(3158, 3618, 14829, -1, 10), 10);
			GameEngine.getObjectManager().replaceObject(new WorldObject(3158, 3622, 14829, -1, 10), new WorldObject(3158, 3622, 14829, -1, 10), 10);	
		} else if(obelsikId == 3){
			GameEngine.getObjectManager().replaceObject(new WorldObject(3225, 3669, 14830, -1, 10), new WorldObject(3225, 3669, 14830, -1, 10), 10);
			GameEngine.getObjectManager().replaceObject(new WorldObject(3229, 3665, 14830, -1, 10), new WorldObject(3229, 3665, 14830, -1, 10), 10);
			GameEngine.getObjectManager().replaceObject(new WorldObject(3225, 3665, 14830, -1, 10), new WorldObject(3225, 3665, 14830, -1, 10), 10);
			GameEngine.getObjectManager().replaceObject(new WorldObject(3229, 3669, 14830, -1, 10), new WorldObject(3229, 3669, 14830, -1, 10), 10);
		} else if(obelsikId == 4){
			GameEngine.getObjectManager().replaceObject(new WorldObject(2978, 3864, 14826, -1, 10), new WorldObject(2978, 3864, 14826, -1, 10), 10);
			GameEngine.getObjectManager().replaceObject(new WorldObject(2982, 3864, 14826, -1, 10), new WorldObject(2982, 3864, 14826, -1, 10), 10);
			GameEngine.getObjectManager().replaceObject(new WorldObject(2978, 3868, 14826, -1, 10), new WorldObject(2978, 3868, 14826, -1, 10), 10);
			GameEngine.getObjectManager().replaceObject(new WorldObject(2982, 3868, 14826, -1, 10), new WorldObject(2982, 3868, 14826, -1, 10), 10);
		} else if (obelsikId == 5){
			GameEngine.getObjectManager().replaceObject(new WorldObject(3033, 3734, 14827, -1, 10), new WorldObject(3033, 3734, 14827, -1, 10), 10);
			GameEngine.getObjectManager().replaceObject(new WorldObject(3037, 3734, 14827, -1, 10), new WorldObject(3037, 3734, 14827, -1, 10), 10);
			GameEngine.getObjectManager().replaceObject(new WorldObject(3033, 3730, 14827, -1, 10), new WorldObject(3033, 3730, 14827, -1, 10), 10);
			GameEngine.getObjectManager().replaceObject(new WorldObject(3037, 3730, 14827, -1, 10), new WorldObject(3037, 3730, 14827, -1, 10), 10);
		}
		*/
	}
}
