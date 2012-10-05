package com.rs2.model.npc;

import com.rs2.model.Item;

/**
 * An NPC drop.
 */
public class NPCDrop extends Item {

	private int chance;

	public NPCDrop(int id, int amount, int chance) {
		super(id, amount);
		this.chance = chance;
	}

	public int getChance() {
		return this.chance;
	}

}
