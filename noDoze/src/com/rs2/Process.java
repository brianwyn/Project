package com.rs2;

import com.rs2.content.actions.ActionManager;
import com.rs2.content.controllers.ControllerManager;
import com.rs2.world.GlobalActions;
import com.rs2.world.ItemManager;
import com.rs2.world.NPCManager;
import com.rs2.world.ObjectManager;
import com.rs2.world.PlayerManager;
import com.rs2.world.ShopManager;

/**
 * 
 * @author Killamess
 *
 */

public class Process extends Thread {
	
	public static final int cycleTime = 500;

	private static PlayerManager playerManager = null;

	public static PlayerManager getPlayerManager() {
		return playerManager;
	}

	private static NPCManager npcManager = null;

	public static NPCManager getNPCManager() {
		return getNpcManager();
	}

	private static ObjectManager objectManager = null;

	public static ObjectManager getObjectManager() {
		return objectManager;
	}
	private static ItemManager itemManager = null;

	public static ItemManager getItemManager() {
		return itemManager;
	}
	
	private static ShopManager shopManager = null;

	public static ShopManager getShopManager() {
		return shopManager;
	}
	private static GlobalActions globalActions = null;

	public static GlobalActions getGlobalActions() {
		return globalActions;
	}
	
	@Override
	public void run() {
		
		long lastTicks = System.currentTimeMillis();
		long totalTimeSpentProcessing = 0;
		int cycle = 0;
		
		while (!GameEngine.isShutdown()) {
			try {
				ControllerManager.processRequests();
				getItemManager().process();
				getNpcManager().process();
				getPlayerManager().process();
				ActionManager.processActions();
				getShopManager().process();
				
				long timeSpent = System.currentTimeMillis() - lastTicks;
				
				totalTimeSpentProcessing += timeSpent;
				
				if (timeSpent >= cycleTime)
					timeSpent = cycleTime;
					
				try {
					Thread.sleep(cycleTime - timeSpent);
				} catch (java.lang.Exception _ex) {
				}
				
				lastTicks = System.currentTimeMillis();
				cycle++;
				
				if (cycle % 100 == 0) {
					
					float time = ((float) totalTimeSpentProcessing) / cycle;
					
					if (Constants.showCpuUsage)
						System.out.println("[CPU-USAGE]: "+(time*100/cycleTime)+"%, Running garbage cleaner.");
					
					System.gc();
					if (Constants.showPlayerOnlineCount)
						System.out.println("There are currently "+ GameEngine.getPlayerManager().getPlayerCount() +" players online.");
				}
				if (GameEngine.isShutdown()) {
					GameEngine.getIoThread().shutdown();
					getPlayerManager().shutdown();
					System.exit(0);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void setPlayerManager(PlayerManager playerManager) {
		Process.playerManager = playerManager;
	}

	public static void setNpcManager(NPCManager npcManager) {
		Process.npcManager = npcManager;
	}

	public static NPCManager getNpcManager() {
		return npcManager;
	}

	public static void setObjectManager(ObjectManager objectManager) {
		Process.objectManager = objectManager;
	}
	
	public static void setShopManager(ShopManager shopManager) {
		Process.shopManager = shopManager;
	}

	public static void setGlobalActions(GlobalActions globalActions) {
		Process.globalActions = globalActions;
	}

	public static void setItemManager(ItemManager itemManager) {
		Process.itemManager = itemManager;
	}
}