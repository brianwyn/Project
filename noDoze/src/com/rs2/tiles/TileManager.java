package com.rs2.tiles;

import com.rs2.tiles.Tile;
import com.rs2.model.Entity;
import com.rs2.model.npc.NPC;


/**
 * 
 * @author Killamess
 *
 */
public class TileManager {
	
	public static Tile generate(Entity entity, int x, int y, int z) {
		return new Tile(entity, new int[] {x, y, z});
	}
	public static Tile generate(Object entity, int x, int y, int z) {
		return new Tile(entity, new int[] {x, y, z});
	}

	public static Tile[] getTiles(Entity entity) {
		
		int size = 1, tileCount = 0;
		
		if (entity instanceof NPC) 
			size = NPC.getNPCSize(((NPC)entity).getDefinition().getType());
		
		Tile[] tiles = new Tile[size * size];
		
		if (tiles.length == 1)
			tiles[0] = generate(entity, entity.getAbsX(), entity.getAbsY(), entity.getHeightLevel());
		else {
			for (int x = 0; x < size; x++) 
				for (int y = 0; y < size; y++) 
					tiles[tileCount++] = generate(entity, entity.getAbsX() + x, entity.getAbsY() + y, entity.getHeightLevel());
		}	
		return tiles;
	}
	
	public static Tile[] getTiles(Entity entity, int[] location) {
		
		int size = 1, tileCount = 0;
		
		if (entity instanceof NPC) 
			size = NPC.getNPCSize(((NPC)entity).getDefinition().getType());
		
		Tile[] tiles = new Tile[size * size];
		
		if (tiles.length == 1)
			tiles[0] = generate(entity, location[0], location[1], location[2]);
		else {
			for (int x = 0; x < size; x++)
				for (int y = 0; y < size; y++)
					tiles[tileCount++] = generate(entity, location[0] + x, location[1] + y, location[2]);
		}	
		return tiles;
	}
	
	public static int calculateDistance(Entity entity, Entity following) {
		
		Tile[] tiles = getTiles(entity);
		
		int[] location = currentLocation(entity);
		int[] pointer = new int[tiles.length > 1 ? tiles.length : 1];
		
		int lowestCount = 20, count = 0;
		
		for (Tile newTiles : tiles) {
			if (newTiles.getTile() == location)
				pointer[count++] = 0;
			else 
				pointer[count++] = TileManager.calculateDistance(newTiles, following);
		}
		for (int i = 0; i < pointer.length; i++)
			if (pointer[i] < lowestCount)
				lowestCount = pointer[i];
		
		return lowestCount;
	}
	
	public static int calculateDistance(Tile location, Entity other) {
		int X = Math.abs(location.getTile()[0] - other.getAbsX());
		int Y = Math.abs(location.getTile()[1] - other.getAbsY());
		return X > Y ? X : Y;
	}
	
	public static int calculateDistance(int[] location, Entity other) {
		int X = Math.abs(location[0] - other.getAbsX());
		int Y = Math.abs(location[1] - other.getAbsY());
		return X > Y ? X : Y;
	}
	
	public static int calculateDistance(int[] location, int[] other) {
		int X = Math.abs(location[0] - other[0]);
		int Y = Math.abs(location[0] - other[1]);
		return X > Y ? X : Y;
	}
	
	public static int[] currentLocation(Entity entity) {
		int[] currentLocation = new int[3];
		if(entity != null) {
			currentLocation[0] = entity.getAbsX();
			currentLocation[1] = entity.getAbsY();
			currentLocation[2] = entity.getHeightLevel();
		}
		return currentLocation;
	}
	
	public static int[] currentLocation(Tile tileLocation) {
		
		int[] currentLocation = new int[3];
		
		if(tileLocation != null) {
			currentLocation[0] = tileLocation.getTile()[0];
			currentLocation[1] = tileLocation.getTile()[1];
			currentLocation[2] = tileLocation.getTile()[2];
		}
		return currentLocation;
	}
	
	public static boolean inAttackablePosition(Entity entity, Entity following) {
		
		Tile[] tiles = getTiles(entity);
		Tile[] followingTiles = getTiles(following);
		
		for (Tile followTile : followingTiles) {
			for (Tile npcTile : tiles) {
				if (npcTile.getTile()[0] - 1 == followTile.getTile()[0] && npcTile.getTile()[1] == followTile.getTile()[1] || 
					npcTile.getTile()[0] + 1 == followTile.getTile()[0] && npcTile.getTile()[1] == followTile.getTile()[1] ||
					npcTile.getTile()[1] - 1 == followTile.getTile()[1] && npcTile.getTile()[0] == followTile.getTile()[0] || 
					npcTile.getTile()[1] + 1 == followTile.getTile()[1] && npcTile.getTile()[0] == followTile.getTile()[0])
					return true;
			}
		}
		return false;
	}
}
