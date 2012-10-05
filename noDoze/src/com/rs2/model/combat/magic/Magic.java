package com.rs2.model.combat.magic;

import java.util.ArrayList;
import java.util.List;

import com.rs2.model.player.Client;
import com.rs2.world.XMLManager;
import com.rs2.world.XMLManager.Spell;
import com.rs2.world.XMLManager.Teleport;

/**
 * 
 * @author Killamess
 * 
 */

public class Magic {

	public static Teleport teleport(int id) {
		for (Teleport t : XMLManager.teleports) {
			if (t.getId() == id) 
				return t;
		}
		return null;
	}

	public static Spell spell(int id) {
		for (Spell s : XMLManager.spells) {
			if (s.getId() == id) 
				return s;
		}
		return null;
	}

	public static List<Client> playersProcessing = new ArrayList<Client>();
	
	public static boolean inQueue(Client client) {
		return playersProcessing.contains(client);
	}
}
