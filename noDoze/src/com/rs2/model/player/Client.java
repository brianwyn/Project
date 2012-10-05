package com.rs2.model.player;

import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import com.rs2.Constants;
import com.rs2.GameEngine;
import com.rs2.content.FriendsHandler;
import com.rs2.content.PrayerHandler;
import com.rs2.content.TradeHandler;
import com.rs2.content.minigames.FightPits;
import com.rs2.content.teleporting.MenuTeleports;
import com.rs2.model.combat.content.CombatMode;
import com.rs2.model.combat.content.Specials;
import com.rs2.model.combat.magic.AutoCast;
import com.rs2.model.player.packetmanager.PacketManager;
import com.rs2.net.Cryption;
import com.rs2.net.Stream;
import com.rs2.util.Misc;
import com.rs2.world.PlayerManager;
import com.rs2.world.XMLManager;
import com.rs2.mysql.HiscoresHandler;

public class Client extends Player {
	
	public static final String playerName = null;
	public static final String playerRights = null;
	public Stream inStream = null, outStream = null;
	public Cryption inStreamDecryption = null, outStreamDecryption = null;
	private Socket mySock;

	public int timeOutCounter = 0;
	public int lowMemoryVersion = 0;
	public int packetType = -1, packetSize = 0;

	private boolean muted = false;
	private boolean yellMuted = false;

	public void setInStream(Stream inStream) {
		this.inStream = inStream;
	}

	public void setOutStream(Stream outStream) {
		this.outStream = outStream;
	}

	public void setInStreamDecryption(Cryption inStreamDecryption) {
		this.inStreamDecryption = inStreamDecryption;
	}

	public Client(Socket s, int slot) {
		super(slot);
		mySock = s;
		outStream = new Stream(new byte[Constants.BUFFER_SIZE]);
		outStream.currentOffset = 0;
		inStream = new Stream(new byte[Constants.BUFFER_SIZE]);
		inStream.currentOffset = 0;
	}

	@Override
	public void destruct() {
		if (mySock == null) {
			return;
		}
		
		System.out.println("" + getUsername() + " logged out [idx="
				+ getUserID() + ", online="
				+ (PlayerManager.getSingleton().getPlayerCount() - 1) + "]");
		
		
		friendsHandler.destruct();

		if (extraData.containsKey("shop")) {
			extraData.remove("shop");
			actionSender.sendWindowsRemoval();
		}
		if (tradeHandler.getCurrentTrade() != null) {
			if (tradeHandler.getCurrentTrade().isOpen()) {
				tradeHandler.decline();
			}
		}
		try {
			disconnected = true;
			mySock = null;
			inStream = null;
			outStream = null;
			isActive = false;
			GameEngine.getIOThread().destroySocket(
					GameEngine.getIOThread().socketFor(this), connectedFrom,
					true);
		} catch (Exception e) {
		}
		super.destruct();
	}

	public void flushOutStream() {
		if (disconnected || outStream.currentOffset == 0) {
			return;
		}
		synchronized(this) {
			if(outStream != null && this != null) {
				ByteBuffer buf = ByteBuffer.allocate(outStream.buffer.length);
				buf.put(outStream.buffer, 0, outStream.currentOffset);
				buf.flip();
				try {
					GameEngine.getIOThread().writeReq(
						GameEngine.getIOThread().socketFor(this), buf);
				} catch (Exception e) {
					return; // it can try again later.
				}
				outStream.currentOffset = 0;
				outStream.reset();
			}
		}
	}

	@Override
	public void initialize() {
		outStream.createFrame(249);
		outStream.writeByteA(1);
		outStream.writeWordBigEndianA(getUserID());

		actionSender.sendChatOptions(0, 0, 0);
		getActionAssistant().setSplitChat(SplitChat ? 0 : 1);
		for (int i = 0; i < PlayerConstants.MAX_SKILLS; i++) {
			actionAssistant.refreshSkill(i);
		}
		if (starter == 0) {
			actionSender.sendMessage("Welcome to "+ XMLManager.settings().getServerName() +"."); 
			actionSender.sendMessage("You have received a greetings package.");
			starter = 1;
			actionSender.sendInventoryItem(995, 1250000);
			actionSender.sendInventoryItem(841, 1);
			actionSender.sendInventoryItem(882, 1000);
			actionSender.sendInventoryItem(1381, 1);
			
			actionSender.sendInventoryItem(1725, 1);
			actionSender.sendInventoryItem(1727, 1);
			actionSender.sendInventoryItem(1729, 1);
			actionSender.sendInventoryItem(1731, 1);
			
			actionSender.sendInventoryItem(558, 200);
			actionSender.sendInventoryItem(556, 100);
			actionSender.sendInventoryItem(554, 100);
			actionSender.sendInventoryItem(555, 100);
			
			actionSender.sendInventoryItem(557, 100);
			actionSender.sendInventoryItem(562, 100);
			actionSender.sendInventoryItem(560, 50);	
			actionSender.sendInventoryItem(563, 20);
			
			actionSender.sendInventoryItem(173, 1);
			actionSender.sendInventoryItem(137, 1);
			actionSender.sendInventoryItem(125, 1);
			actionSender.sendInventoryItem(119, 1);
			actionSender.sendInventoryItem(1323, 1);
			actionSender.sendInventoryItem(1309, 1);
			actionSender.sendInventoryItem(380, 1000);
			
		} else {
			actionSender.sendMessage("Welcome back to "+  XMLManager.settings().getServerName() +"."); 
		}
		outStream.createFrame(107);
		outStream.createFrame(68);
		actionSender.sendSidebar(1, 3917);
		actionSender.sendSidebar(2, 638);
		actionSender.sendSidebar(3, 3213);
		actionSender.sendSidebar(4, 1644);
		actionSender.sendSidebar(5, 5608);
		switch(spellBook) {
		case 2:
			actionSender.sendSidebar(6, 12855);
			break;
		case 3:
			actionSender.sendSidebar(6, 430); // 29999
			break;
		default:
			actionSender.sendSidebar(6, 1151);
			break;
		}
		actionSender.sendSidebar(7, -1);//18128
		actionSender.sendSidebar(8, 5065);
		actionSender.sendSidebar(9, 5715);
		actionSender.sendSidebar(10, 2449);
		actionSender.sendSidebar(11, 904);
		actionSender.sendSidebar(12, 147);
		actionSender.sendSidebar(13, -1); //harp (music tab)
		actionSender.sendSidebar(0, 2423);
		actionSender.sendOption("Trade With", 3);
		actionSender.sendOption("Follow", 2);
		setFriendsSize(200);
		setIgnoresSize(100);
		setPlayerBankSize(352); // full bank (352) PND = 144
		if (skullTimer > 0) 
			skullIcon = -1;
		
		if (FightPits.inWaitingArea(this)) 
			FightPits.addToWaitingRoom(this);
		if (FightPits.inFightArea(this))
			FightPits.addToFightPits(this);
		
		updateEnergy();

		if (!appearanceSet) {
			actionSender.sendInterface(3559);
		} else {
		}
		GameEngine.getItemManager().reloadDrops(this);		
		actionSender.sendQuest("Defence XP", 353);
		MenuTeleports.sendTeleportNames(this);
		update();
		AutoCast.turnOff(this); 
		if (runConfig > 0) {
			setRunning(true);
			isRunning2 = true;
			actionSender.sendConfig(429, 1); 
		} else {
			setRunning(false);
			isRunning2 = false;
			actionSender.sendConfig(429, 0); 
		}
		if (autoRetaliate) {
			actionSender.sendConfig(172, 1); 
		} else {
			actionSender.sendConfig(172, 0); 
		}
		CombatMode.setCombatMode(this, this.combatMode);
		actionSender.sendConfig(301, 0); //reset special
		Specials.updateSpecialBar(this);
		prayerHandler.resetAllPrayers(); //reset prayers
		actionSender.sendItemReset();
		actionSender.sendBankReset();

		equipment.setEquipment(playerEquipment[PlayerConstants.HELM], 1,
				PlayerConstants.HELM);
		equipment.setEquipment(playerEquipment[PlayerConstants.CAPE], 1,
				PlayerConstants.CAPE);
		equipment.setEquipment(playerEquipment[PlayerConstants.AMULET], 1,
				PlayerConstants.AMULET);
		equipment.setEquipment(playerEquipment[PlayerConstants.ARROWS],
				playerEquipmentN[PlayerConstants.ARROWS],
				PlayerConstants.ARROWS);
		equipment.setEquipment(playerEquipment[PlayerConstants.CHEST], 1,
				PlayerConstants.CHEST);
		equipment.setEquipment(playerEquipment[PlayerConstants.SHIELD], 1,
				PlayerConstants.SHIELD);
		equipment.setEquipment(playerEquipment[PlayerConstants.BOTTOMS], 1,
				PlayerConstants.BOTTOMS);
		equipment.setEquipment(playerEquipment[PlayerConstants.GLOVES], 1,
				PlayerConstants.GLOVES);
		equipment.setEquipment(playerEquipment[PlayerConstants.BOOTS], 1,
				PlayerConstants.BOOTS);
		equipment.setEquipment(playerEquipment[PlayerConstants.RING], 1,
				PlayerConstants.RING);
		equipment.setEquipment(playerEquipment[PlayerConstants.WEAPON], 1,
				PlayerConstants.WEAPON);

		equipment.sendWeapon();
		bonuses.calculateBonus();

		friendsHandler.initialize();
		flushOutStream();
	}	

	public void convertMagic() {
		if (getSpellBook() <= 1) {
			setSpellBook(2);
			actionSender.sendSidebar(6, 12855);
			actionSender.sendMessage("You convert to ancient magic.");
		} else if (getSpellBook() == 2) {
			setSpellBook(1);
			actionSender.sendSidebar(6, 1151);
			actionSender.sendMessage("You convert to regular magic.");
		}
	}

	@Override
	public void update() {
		manager.updatePlayer(this, outStream);
		manager.updateNPC(this, outStream);

		if (zoneRequired) {
			Client c = this;
			c.doZoning();
			zoneRequired = false;
		}
		flushOutStream();
	}

	public void doZoning() {
		GameEngine.getItemManager().reload(this);
		GameEngine.getObjectManager().reload(this);
	}

	private int vengTimer;
	
	public void setVengTimer() {
		this.vengTimer = 60;
	}

	public int getVengTimer() {
		return vengTimer;
	}
	
	public void deductVengTimer() {
		this.vengTimer--;
	}
	
	private boolean loggedOut = false;

	public void setLoggedOut(boolean loggedOut) {
		this.loggedOut = loggedOut;
		HiscoresHandler.save(this);
		HiscoresHandler.hiscoresHandler(this);
	}

	public boolean isLoggedOut() {
		return loggedOut;
	}

	@Override
	public void process() {
		timeOutCounter++;

		if (isKicked || isLoggedOut()) {
			disconnected = true;
		}

		if (timeOutCounter > Constants.INGAME_TIMEOUT * 2) {
			actionSender.sendLogout();
		}

		if (logoutDelay > 0) {
			logoutDelay--;
		}

	}

	private ByteBuffer oldBuffer = null;

	public void read(ByteBuffer buffer) {
		if (disconnected || !isActive || outStream == null || inStream == null) {
			Misc
					.print_debug("Dropped packet due to null outstream/instream/inactive or disconnected client.");
			return;
		}
		buffer.flip();
		if (oldBuffer != null) {
			ByteBuffer newBuffer = ByteBuffer.allocate(buffer.limit()
					+ oldBuffer.limit());
			newBuffer.put(oldBuffer);
			newBuffer.put(buffer);
			oldBuffer = null;
			newBuffer.flip();
			buffer = newBuffer;
			newBuffer = null;
		}
		int avail = buffer.limit();
		while (true) {
			if (avail <= 0) {
				break;
			}
			if (packetType == -1) {
				if (avail > 0) {
					packetType = buffer.get() & 0xFF;
					packetType = (packetType - inStreamDecryption.getNextKey()) & 0xFF;
					packetSize = Constants.PACKET_SIZES[packetType];
					avail--;
				} else {
					break;
				}
			}
			if (packetSize == -1) {
				if (avail > 0) {
					packetSize = buffer.get() & 0xFF;
					avail--;
				} else {
					break;
				}
			}
			if (avail >= packetSize) {
				avail -= packetSize;
				buffer.get(inStream.buffer, 0, packetSize);
				inStream.currentOffset = 0;
				PacketManager.handlePacket(this, packetType, packetSize);
				timeOutCounter = 0;
				inStream.reset();
				packetType = -1;
			} else {
				break;
			}
		}
		if (avail > 0) {
			oldBuffer = ByteBuffer.allocate(avail);
			byte[] tmp = new byte[avail];
			buffer.get(tmp, 0, avail);
			oldBuffer.put(tmp);
			oldBuffer.flip();
			tmp = null;
		}
		buffer.clear();
	}

	public void cancelTasks() {
		if (extraData.containsKey("shop")) {
			extraData.remove("shop");
			actionSender.sendWindowsRemoval();
		}
		if (getTradeHandler().getCurrentTrade() != null) {
			if (getTradeHandler().getCurrentTrade().isOpen()) {
				getTradeHandler().decline();
			}
		}
		actionSender.sendWindowsRemoval();
		
	}

	public void setYellMuted(boolean yellMuted) {
		this.yellMuted = yellMuted;
	}

	public boolean isYellMuted() {
		return yellMuted;
	}

	public void setMuted(boolean muted) {
		this.muted = muted;
	}

	public boolean isMuted() {
		return muted;
	}

	private Map<String, Object> extraData = new HashMap<String, Object>();

	public Map<String, Object> getExtraData() {
		return extraData;
	}

	private ActionSender actionSender = new ActionSender(this);

	public ActionSender getActionSender() {
		return actionSender;
	}

	private ActionAssistant actionAssistant = new ActionAssistant(this);

	public ActionAssistant getActionAssistant() {
		return actionAssistant;
	}

	private ContainerAssistant containerAssistant = new ContainerAssistant(this);

	public ContainerAssistant getContainerAssistant() {
		return containerAssistant;
	}

	private Equipment equipment = new Equipment(this);

	public Equipment getEquipment() {
		return equipment;
	}

	private Bonuses bonuses = new Bonuses(this);

	public Bonuses getBonuses() {
		return bonuses;
	}

	private TradeHandler tradeHandler = new TradeHandler(this);

	public TradeHandler getTradeHandler() {
		return tradeHandler;
	}

	private FriendsHandler friendsHandler = new FriendsHandler(this);

	public FriendsHandler getFriendsHandler() {
		return friendsHandler;
	}

	private PrayerHandler prayerHandler = new PrayerHandler(this);

	public PrayerHandler getPrayerHandler() {
		return prayerHandler;
	}

	private boolean isViewingOrb;
	

	public void setViewingOrb(boolean isViewingOrb) {
		this.isViewingOrb = isViewingOrb;
	}

	public boolean isViewingOrb() {
		return isViewingOrb;
	}
	


	

}