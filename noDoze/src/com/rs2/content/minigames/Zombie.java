package com.rs2.content.minigames;

import java.util.ArrayList;
import java.util.List;

import com.rs2.model.player.Client;

public class Zombie {
	
	public int waveCount = 0;
	public int nextWave = 10; //after killing them all 5 sec count down?
	
	private static List<String> playersInRoom = new ArrayList<String>();
	
	public static void add(Client client) {
		if (client == null)
				return;
		if (playersInRoom.contains(client.getUsername()))
			return;
		playersInRoom.add(client.getUsername());
	}
	
	public static void remove(Client client) {
		if (client == null)
			return;
	if (playersInRoom.contains(client.getUsername()))
		playersInRoom.remove(client.getUsername());
	}
	
	

}
