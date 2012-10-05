package com.rs2.model.player;

import com.rs2.Constants;
import com.rs2.world.PlayerManager;

public class SaveAllPlayers extends Thread {
	public SaveAllPlayers() {
		while(true) {
			try {
				if(PlayerManager.getSingleton().getPlayerCount() > 0) {
					int saved = 0;
					
					if (Constants.showSaveMessage)
						System.out.println("Saving All Players...");
					
					for (Player p : PlayerManager.getSingleton().getPlayers()) {
						if (p == null)
							continue;
						
						if (((Client)p).getTradeHandler().stage > 0)
							continue;
						
						saved++;
						PlayerManager.getSingleton().saveGame(p, false);
					}
					if (Constants.showSaveMessage)
						System.out.println("Saved " + saved +" Player(s)");
				}
				Thread.sleep(Constants.SAVE_TIMER);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
