package com.rs2.tiles;

import com.rs2.content.controllers.Animation;
import com.rs2.model.Entity;
import com.rs2.model.Entity.CombatType;
import com.rs2.model.combat.content.Life;
import com.rs2.model.npc.NPC;
import com.rs2.model.npc.NPCFollow;
import com.rs2.model.player.Client;
import com.rs2.util.Misc;


/**
 * 
 * @author Killamess
 *
 */

public class FollowEngine {
	
	public static void loop(Entity entity) {
		if (entity == null || !Life.isAlive(entity) || entity.following == null)
			return;
		
		Entity following = entity.following;
		
		if (following == null || !Life.isAlive(following) || TileManager.calculateDistance(entity, entity.following) > 32 ) {
			resetFollowing(entity);
			return;
		}
		Animation.face(entity, following);
		if (entity instanceof NPC && following instanceof Client) {
			NPCFollow.runFollow((NPC)entity, (Client)following);
		} else if (entity instanceof NPC && following instanceof NPC) {
			NPCFollow.runFollow((NPC)entity, (NPC)following);
		}
	}
	
	public static int getNextFollowingDirection(Entity entity, Entity following) {
		if (!(entity instanceof Client))
			return -1;
		
		Client client = (Client) entity;
		
		if (following == null || !Life.isAlive(following) || TileManager.calculateDistance(entity, entity.following) > 18 ) {
			resetFollowing(entity);
			return -1;
		}
		if (following instanceof Client) {
			if (((Client)following).disconnected) {
				resetFollowing(entity);
				return -1;
			}
		}
		
		client.resetWalkingQueue();
		
		int dir = -1;
		
		if (entity.getFreezeDelay() > 0) 
			return dir;
		
		int[] location = TileManager.currentLocation(entity);
		int[] location2 = TileManager.currentLocation(following);
		
		boolean[] direction = new boolean[4];
		
		Tile[] npcTiles = TileManager.getTiles(following);
		Tile[] followingTiles = TileManager.getTiles(entity);
		
		int actualDistance = TileManager.calculateDistance(following, entity);
		if (actualDistance == 0) {
			direction[Misc.random(3)] = true;
		} else if (actualDistance == 1 && !TileManager.inAttackablePosition(entity, following) && entity.combatType == CombatType.MELEE) {
			for (int i = 0; i < 4; i ++)
				direction[i] = false;
			if (location[0] > location2[0]) {
				direction[3] = true;	
			} else if (location[0] < location2[0]) {
				direction[2] = true;
			} 
		} else {
			if (actualDistance == 1 && entity.combatType != CombatType.MELEE) {
				return -1;
			}
			boolean[] movement = new boolean[4];
			final int noNorth = 0, noEast = 1, noSouth = 2, noWest = 3; 
			for (Tile followTile : followingTiles) {
				for (Tile tiles : npcTiles) {
					if (tiles.getTile()[0] == followTile.getTile()[0]) { //same x line
						movement[noEast] = true;
						movement[noWest] = true;
					}
					if (tiles.getTile()[1] == followTile.getTile()[1]) { //same y line
						movement[noNorth] = true;
						movement[noSouth] = true;	
					}
				}
			}
			if (location2[0] > location[0]) {
				if (!movement[noWest])
					direction[2] = true; //west
			} else if (location2[0] < location[0]) {
				if (!movement[noEast])
					direction[3] = true; //east
			}
			if (location2[1] > location[1]) {
				if (!movement[noNorth])
					direction[0] = true; //north
			} else if (location2[1] < location[1]) {
				if (!movement[noSouth])
					direction[1] = true; //south	
			}
		}
		if (needsToStop(entity, following) && TileManager.inAttackablePosition(entity, following))
			return -1;
		
		if (!direction[0] && !direction[1] && !direction[2] && !direction[3] || !client.canWalk() || needsToStop(entity, following) && actualDistance > 1) 
			return -1;
	
		if (actualDistance > 32) {
			resetFollowing(entity);
			return -1;
		}
		if(direction[0] && direction[2]) dir = 2; //north east
		else if(direction[0] && direction[3]) dir = 14; //north west
		else if(direction[1] && direction[2]) dir = 6;//south east
		else if(direction[1] && direction[3]) dir = 10;//south west
		else if(direction[0]) dir = 0; //north
		else if(direction[2]) dir = 4;//east
		else if(direction[3]) dir = 12;//west
		else if(direction[1]) dir = 8;//south

		dir >>= 1;	
		
		client.currentX += Misc.directionDeltaX[dir];
		client.currentY += Misc.directionDeltaY[dir];
		client.setAbsX(client.getAbsX() + Misc.directionDeltaX[dir]);
		client.setAbsY(client.getAbsY() + Misc.directionDeltaY[dir]);
		return dir;
	}
	

	public static int getDistance(Entity entity) {
		if (!(entity instanceof NPC))
			return 0;
		
		int X = Math.abs(entity.getAbsX() - ((NPC)entity).spawnAbsX);
		int Y = Math.abs(entity.getAbsY() - ((NPC)entity).spawnAbsY);

		return X > Y ? X : Y;
	}
	
	public static boolean needsToStop(Entity entity, Entity following) {
		if (!(entity instanceof Client))
			return true;
		
		Client client = (Client) entity;
		
		if (client.isDead())
			return true;
		
		int[] location2 = TileManager.currentLocation(following);
		
		Tile[] npcTiles = TileManager.getTiles(following);
		
		int[] tileDistance = new int[npcTiles.length];
		
		int lowestCount = 20;
		
		int count = 0;
		
		for (Tile tiles : npcTiles) {
			if (tiles.getTile() != location2) {
				tileDistance[count++] = TileManager.calculateDistance(tiles, entity);
			} else {
				tileDistance[count++] = 0;	
			}
		}
		for (int i = 0; i < tileDistance.length; i++)	{
			if (tileDistance[i] < lowestCount)
				lowestCount = tileDistance[i];
		}
		int actualDistance = lowestCount;
		
		if (entity.getTarget() == null || entity.getCombatType() == CombatType.MELEE)
			return actualDistance == 1;
		
		else if (entity.getCombatType() == CombatType.MAGIC || entity.getCombatType() == CombatType.RANGE)
			return actualDistance <= 8;
		
		return false;
	}
	
	public static void resetFollowing(Entity entity) {
		entity.following = null;
	}
}
