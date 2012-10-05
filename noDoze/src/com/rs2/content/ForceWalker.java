package com.rs2.content;

import com.rs2.model.player.Client;
import com.rs2.tiles.TileManager;
import com.rs2.util.Misc;

/**
 * 
 * @author killamess
 *
 */
public class ForceWalker {
	
	public static final int NORTH = 0, EAST = 1, SOUTH = 2, WEST = 3;

	public static int getForceMovement(Client client, int[] toLocation) {
		
		int[] myLocation = TileManager.currentLocation(client);
		
		boolean[] requestedMovement = new boolean[4];
		
		if (myLocation[0] < toLocation[0])
			requestedMovement[EAST] = true;
		else if (myLocation[0] > toLocation[0]) 
			requestedMovement[WEST] = true;
		
		if (myLocation[1] < toLocation[1])
			requestedMovement[NORTH] = true;
		else if (myLocation[1] > toLocation[1])
			requestedMovement[SOUTH] = true;
		
		int dir = -1;
		
		if (requestedMovement[NORTH] && requestedMovement[EAST]) dir = 2; //north east
		else if(requestedMovement[NORTH] && requestedMovement[WEST]) dir = 14; //north west
		else if(requestedMovement[SOUTH] && requestedMovement[EAST]) dir = 6;//south east
		else if(requestedMovement[SOUTH] && requestedMovement[WEST]) dir = 10;//south west
		else if(requestedMovement[NORTH]) dir = 0; //north
		else if(requestedMovement[EAST]) dir = 4;//east
		else if(requestedMovement[WEST]) dir = 12;//west
		else if(requestedMovement[SOUTH]) dir = 8;//south
		
		if (dir == -1) {
			client.forceMove = false;
			return -1;
		}
		dir >>= 1;		

		client.currentX += Misc.directionDeltaX[dir];
		client.currentY += Misc.directionDeltaY[dir];
		client.setAbsX(client.getAbsX() + Misc.directionDeltaX[dir]);
		client.setAbsY(client.getAbsY() + Misc.directionDeltaY[dir]);
		return dir;
	}
	

}
