package com.rs2.content.minigames;

import com.rs2.model.player.Client;

public class FunPk {
	public static boolean IsInFunPK(Client client) {
		return(client.getAbsX() > 2303 && client.getAbsX() < 2356 && client.getAbsY() > 4571 && client.getAbsY() < 4605);
	}
}
