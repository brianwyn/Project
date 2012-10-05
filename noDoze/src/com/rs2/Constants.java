package com.rs2;


public class Constants {

	//serverRegistration
	public static boolean runningSRViaCM2D = true;
	
	//server GUI (Control Panel)
	public static boolean runControlPanel = false;
	
	public static int SAVE_TIMER = 30000;
	
	public static boolean showPlayerOnlineCount = false;
	public static boolean showCpuUsage = false; //will display every 100 cycles
	public static boolean showSaveMessage = false; 
	public static boolean showXMLMessage = true;//will display all xml loadings
	
	public static final int PET_TYPE = 98; //DOG
    
	/**
	 * On rs when you eat, and get hit at the same time
	 * it can delay the hit for 1 or 2 cycles.
	 */
	public static boolean FOOD_DELAY_HITS = false;
	
	public static final int BUFFER_SIZE = 1024;

	public static final int CYCLE_TIME = 600;

	/**
	 * Increase this when needed.
	 */
	public static final int MAX_PLAYERS = 50;

	public static final int INGAME_TIMEOUT = 5;

	public static final int MAX_CONNECTIONS_PER_IP = 5;

	public static final int PACKET_SIZES[] = {
		0, 0, 0, 1, -1, 0, 0, 0, 0, 0, // 0
		0, 0, 0, 0, 8, 0, 6, 2, 2, 0, // 10
		0, 2, 0, 6, 0, 12, 0, 0, 0, 0, // 20
		0, 0, 0, 0, 0, 8, 4, 0, 0, 2, // 30
		2, 6, 0, 6, 0, -1, 0, 0, 0, 0, // 40
		0, 0, 0, 12, 0, 0, 0, 0, 8, 0, // 50
		0, 8, 0, 0, 0, 0, 0, 0, 0, 0, // 60
		6, 0, 2, 2, 8, 6, 0, -1, 0, 6, // 70
		0, 0, 0, 0, 0, 1, 4, 6, 0, 0, // 80
		0, 0, 0, 0, 0, 3, 0, 0, -1, 0, // 90
		0, 13, 0, -1, 0, 0, 0, 0, 0, 0,// 100
		0, 0, 0, 0, 0, 0, 0, 6, 0, 0, // 110
		1, 0, 6, 0, 0, 0, -1, 0, 2, 6, // 120
		0, 4, 6, 8, 0, 6, 0, 0, 0, 2, // 130
		0, 0, 0, 0, 0, 6, 0, 0, 0, 0, // 140
		0, 0, 1, 2, 0, 2, 6, 0, 0, 0, // 150
		0, 0, 0, 0, -1, -1, 0, 0, 0, 0,// 160
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0, // 170
		0, 8, 0, 3, 0, 2, 0, 0, 8, 1, // 180
		0, 0, 12, 0, 0, 0, 0, 0, 0, 0, // 190
		2, 0, 0, 0, 0, 0, 0, 0, 4, 0, // 200
		4, 0, 0, 0, 7, 8, 0, 0, 10, 0, // 210
		0, 0, 0, 0, 0, 0, -1, 0, 6, 0, // 220
		1, 0, 0, 0, 6, 0, 6, 8, 1, 0, // 230
		0, 4, 0, 0, 0, 0, -1, 0, -1, 4,// 240
		0, 0, 6, 6, 0, 0, 0 // 250
	};

}