package com.rs2.model.player;

import com.rs2.GameEngine;
import com.rs2.model.ItemDefinition;

/**
 * @author Ultimate
 */
public class Bonuses {

	private Client client;

	public Bonuses(Client client) {
		this.client = client;
	}

	public int bonus[] = new int[12];

	public void calculateBonus() {
		for (int i = 0; i < bonus.length; i++) {
			bonus[i] = 0;
		}
		for (int i = 0; i < client.playerEquipment.length; i++) {
			if (client.playerEquipment[i] > -1) {
				ItemDefinition def = GameEngine.getItemManager()
						.getItemDefinition(client.playerEquipment[i]);
				for (int k = 0; k < bonus.length; k++) {
					bonus[k] += def.getBonus(k);
				}
			}
		}
		for (int i = 0; i < bonus.length; i++) {
			String text = "";
			int offset = 0;
			if (bonus[i] >= 0) {
				text = PlayerConstants.BONUS_NAME[i] + ": +" + bonus[i];
			} else {
				text = PlayerConstants.BONUS_NAME[i] + ": -"
						+ Math.abs(bonus[i]);
			}
			if (i >= 10) {
				offset = 1;
			}
			int interfaceid = 1675 + i + offset;
			client.getActionSender().sendQuest(text, interfaceid);
		}
	}

}
