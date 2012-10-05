package com.rs2;

import com.rs2.content.actions.ActionManager;
import com.rs2.model.player.SaveAllPlayers;
import com.rs2.model.player.commandmanager.CommandManager;
import com.rs2.model.player.packetmanager.PacketManager;
import com.rs2.mysql.Loadable;
import com.rs2.net.ConnectionListener;
import com.rs2.tiles.WorldMap;
import com.rs2.util.log.Log;
import com.rs2.world.GlobalActions;
import com.rs2.world.ItemManager;
import com.rs2.world.NPCManager;
import com.rs2.world.ObjectManager;
import com.rs2.world.PlayerManager;
import com.rs2.world.ShopManager;
import com.rs2.world.XMLManager;

/**
 * GameEngine
 * 
 * @author Ultimate
 */

public class GameEngine extends Process {
	
	public static boolean multiValve = true;
	public static boolean systemValve = false;
	
	private static Object gameLogicLock = new Object();

	public static Object getGameLogicLock() {
		return gameLogicLock;
	}

	private static ConnectionListener ioThread = null;

	public static ConnectionListener getIOThread() {
		return getIoThread();
	}
	
	private static int averageProcessTime = -1;

	public static void main(String[] args) {
		try {
			System.setOut(new Log(System.out));
			System.setErr(new Log(System.err));
			
			start = System.currentTimeMillis();
			XMLManager.loadServerSettings();
			System.out.println();
			System.out.println("Loading "+ XMLManager.settings().getServerName() +"...");
			setShopManager(new ShopManager());
			setNpcManager(new NPCManager());
			setObjectManager(new ObjectManager());
			setItemManager(new ItemManager());
			setGlobalActions(new GlobalActions());
			Process proc = new Process();
			setPlayerManager(PlayerManager.getSingleton());
			PacketManager.loadAllPackets();
			CommandManager.loadAllCommands();
			ActionManager.loadAllActions();
			Loadable.runSQLQuerys();
			XMLManager.load();
			WorldMap.load();
			System.gc();
			new Thread(proc).start();
			setIoThread(new ConnectionListener());
			(new Thread(getIoThread(), "ioThread")).start();
			SaveAllPlayers SaveAllPlayers = new SaveAllPlayers();
			new Thread(SaveAllPlayers).start();

			
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static int garbageCollectionCycle = 0;

	public static long start = 0;

	public static String getUptime() {
		long uptime = (System.currentTimeMillis() - start) / 1000;
		int minutes = (int) (uptime / 60);
		if (minutes == 0 || minutes == 1) {
			return minutes + " min";
		} else {
			return minutes + " mins";
		}
	}
	
	public static int getProcessTime() {
		return averageProcessTime;
	}

	public static int getJVMSize() {
		Runtime runtime = Runtime.getRuntime();
		return (int) ((runtime.totalMemory() - runtime.freeMemory()) / 1024L);
	}

	private static boolean debug = false;

	public static boolean isDebugEnabled() {
		return debug;
	}

	private static boolean shutdownServer = false;

	public static boolean isShutdown() {
		return isShutdownServer();
	}

	public static void setShutdown(boolean shutdown) {
		setShutdownServer(shutdown);
	}

	public static void setShutdownServer(boolean shutdownServer) {
		GameEngine.shutdownServer = shutdownServer;
	}

	public static boolean isShutdownServer() {
		return shutdownServer;
	}

	public static void setIoThread(ConnectionListener ioThread) {
		GameEngine.ioThread = ioThread;
	}

	public static ConnectionListener getIoThread() {
		return ioThread;
	}
}