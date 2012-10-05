package com.rs2.content;

import java.util.ArrayList;

import com.rs2.content.controllers.Location;
import com.rs2.model.player.Client;
import com.rs2.util.Misc;

public class JailSystem {
	
	private static ArrayList<String> jailedPlayers = new ArrayList<String>();
	
	private static final int[] jailCells = {
		3230,
		3228,
		3226
	};

	public static void addToJail(Client client) {
		client.getActionSender().sendMessage("You have been accused of rule breaking, An admin will now deal with you.");
		Location.addNewRequest(client, jailCells[Misc.random(2)], 3407, 0, 0);
		add(client.getUsername());
	}
	public static void removeFromJail(Client client) {
		remove(client.getUsername());
		client.getActionSender().sendMessage("You have been released from jail, Let this be a warning.");
		client.forceMove = true;
		client.forceMovement[0] = client.getAbsX();
		client.forceMovement[1] = client.getAbsY() + 1;
		client.forceMovement[2] = client.getHeightLevel();

	}
	
	public static void add(String name) {
		if (!jailedPlayers.contains(name))
			jailedPlayers.add(name);
	}
	
	public static void remove(String name) {
		if (jailedPlayers.contains(name))
			jailedPlayers.remove(name);
	}
	
	public static boolean inJail(Client client) {
		return jailedPlayers.contains(client.getUsername());
	}
}
