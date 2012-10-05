package com.rs2.model.player;

import com.rs2.Constants;
import com.rs2.GameEngine;
import com.rs2.content.ForceWalker;
import com.rs2.content.JailSystem;
import com.rs2.content.minigames.FightPits;
import com.rs2.model.Item;
import com.rs2.model.Entity;
import com.rs2.model.npc.NPC;
import com.rs2.net.Stream;
import com.rs2.tiles.FollowEngine;
import com.rs2.util.Misc;
import com.rs2.world.NPCManager;
import com.rs2.world.PlayerManager;

public abstract class Player extends Entity {
	
	public String teleOther;

	public int teleToSpell;
	
	public boolean inMiniGame() {
		if (FightPits.inFightPits((Client)this) || FightPits.inFightArea((Client)this)) {
			return true;
		}
		if (FightPits.inWaitingRoom((Client)this) || FightPits.inWaitingArea((Client)this)) {
			return true;
		}
		return false;
	}
	
	public int clickedNPCID = 0, destX = 0, destY = 0, npcSize = 0, npcTask = 0, npcSlot = 0;
	
	public NPC npc = null;
	public boolean[] teleport = new boolean[6];
	public int teleportConfig = -1;
	public int projectileDelay;
	
	/**
	 * poisoning
	 */
	public boolean poisonHit = false;
	public int[] poisonHits = new int[3];
	public int poisonDamage;
	public int poisonDelay;
	
	/** mining **/
	public int objectID = 0, objectX = 0, objectY = 0, objectSize = 0, setPlayerX = 0, setPlayerY = 0;
	
	public int[] objectStorage = new int[6];

	public int chargeTimer;
	public int runConfig;
	public int retaliate;
	public int ID;
	/**
	 * @return the ID
	 */
	public int getID() {
		return ID;
	}
	
	/**
	 * Duelling features.
	 */
	public boolean[] duelRule = new boolean[22];
	public boolean[] duelAccepted = new boolean[2]; //1 being first screen, etc.
	public Client duelRequest = null;
	public int duelOption = 0;
	public int duelSpaceReq = 0;
	public int duelSlot = 0;
	public int[] DUEL_RULE_ID = {
		1, 2, 16, 32, 64, 128, 256, 512, 
		1024, 4096, 8192, 16384, 32768, 
		65536, 131072, 262144, 524288, 
		2097152, 8388608, 16777216, 
		67108864, 134217728
	};
	
	public int[][] array; 
	public int gem = 0;
	public int crafting = 0;
	public int craftingItem = 0;
	public int craftingType = 0;
	public int craftingAmount = 0;
	public int craftingThreadCount = 0;
	
	/**
	 * smelting
	 */
	public int oreId = 0;
	public int smeltAmount = 0;
	
	public int jewleryId = 0;
	public int lastMeleeMode = -1;
	public int lastRangeMode = -1;
	public int lastMagicMode = -1;
	public boolean forceMove;
	public int[] forceMovement = new int[3];
	
	public int foodDelay;
	public int potionDelay;
	public Client skulledOn;
	public long potionTimer;
	public int[] pickup = new int[3];
	public int starter;
	public Client whoKilledYa;
	public String[] lastKilled = {"", "", ""};
	public String MBTC = "", MBBC = "",  MBHT = "", MBID = "";
	public boolean wildSignWarning = false, SplitChat;
	public int testnum = 0;
	public int combatMode = 0; //default - attack.
	public boolean usingMagicDefence = false;
	public boolean turnOffSpell = false;
	public int spellId = 0;
	public boolean autoRetaliate = false;
	
	public void println_debug(String s) {
		if (GameEngine.isDebugEnabled())
			System.out.println("[Client] " + getUsername() + " - " + s);
	}
	public String[] multiList = new String[9];
	public long actionTimer;
	public String[] getMulti() {
		return multiList;
	}
	public void setMulti(String name, int slot) {
		this.multiList[slot] = name;
	}
	public int tanningInterfaceID;
	
	public int getTanningInterfaceID() {
		return tanningInterfaceID;
	}
	public void setTanningInterfaceID(int newInterface) {
		this.tanningInterfaceID = newInterface;
	}
	
	public int wildernessLevel;
	
	public int getWildernessLevel() {
		return wildernessLevel;
	}
	public void setWildernessLevel(int level) {
		this.wildernessLevel = level;
	}
	
	public int autoCastId;
	
	public int getAutoCastId() {
		return autoCastId;
	}
	public void setAutoCastId(int id) {
		this.autoCastId = id;
	}
	
	public boolean autoCasting;
	
	public boolean isAutoCasting() {
		return autoCasting;
	}
	public void setAutoCasting(boolean type) {
		this.autoCasting = type;
	}
	
	public boolean isDeadWaiting;
	
	public boolean isDeadWaiting() {
		return isDeadWaiting;
	}
	public void setDeadWaiting(boolean type) {
		this.isDeadWaiting = type;
	}
	
	public int deadTimer;
	
	public int getDeadTimer() {
		return deadTimer;
	}
	public void setDeadTimer(int time) {
		this.deadTimer = time;
	}
	public void deductDeadTimer() {
		this.deadTimer--;
	}
	
	public int stunnedTimer;
	
	public int getStunnedTimer() {
		return stunnedTimer;
	}
	public void setStunnedTimer(int time) {
		this.stunnedTimer = time;
	}
	public void deductStunnedTimer() {
		this.stunnedTimer--;
	}
	
	public int recoil;
	public int getRecoilCount() {
		return recoil;
	}
	public void setRecoilCount(int count) {
		this.recoil = count;
	}
	/*
	 * spellbook 0 = reg, 1 = anc, 2 = lunar
	 */
	public int dharokDamage = 0;
	public int spellBook = 0;
	public String username = "";
	public String password = "";
	public String connectedFrom = "";
	public int privileges = 0;
	public int userID = -1;
	public boolean appearanceSet = false;

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username
	 *            the username to be set
	 */
	
	public void setUsername(String username) {
		this.username = username;
	}
	private long lastAction;

	public long getLastAction() {
		return lastAction;
	}

	public void setLastAction(long currentTimeMillis) {
		this.lastAction = currentTimeMillis;
		
	}
	
	/**
	 * lunar
	 */
	private boolean vengenceCasted;
	
	public boolean isVengOn() {
		return vengenceCasted;
	}
	public void setVeng(boolean type) {
		this.vengenceCasted = type;
	}
	public void moveClient() {
		this.forceMove = true;
		int random = Misc.random(1);
		int opposite = random == 1 ? 0 : 1;
		this.forceMovement[0] = this.getAbsX() + random;
		this.forceMovement[1] = this.getAbsY() + opposite;
		this.forceMovement[2] = this.getHeightLevel();
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to be set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the connectedFrom
	 */
	public String getConnectedFrom() {
		return connectedFrom;
	}

	/**
	 * @param connectedFrom
	 *            the connectedFrom to be set
	 */
	public void setConnectedFrom(String connectedFrom) {
		this.connectedFrom = connectedFrom;
	}

	/**
	 * @return the privileges
	 */
	public int getPrivileges() {
		return privileges;
	}

	/**
	 * @param privileges
	 *            the privileges to be set
	 */
	public void setPrivileges(int privileges) {
		this.privileges = privileges;
	}

	/**
	 * @return the userID
	 */
	public int getUserID() {
		return userID;
	}

	/**
	 * @param userID
	 *            the userID to be set
	 */
	public void setUserID(int userID) {
		this.userID = userID;
	}

	private long[] friends = new long[200];
	private long[] ignores = new long[100];
	private int friendsSize;
	private int ignoresSize;

	/**
	 * @param friends
	 *            the friends to set
	 */
	public void setFriends(long[] friends) {
		this.friends = friends;
	}

	/**
	 * @return the friends
	 */
	public long[] getFriends() {
		return friends;
	}

	public int getSpellBook() {
		return spellBook;
	}
	
	public void setSpellBook(int book) {
		this.spellBook = book;
	}
	
	public int getDharokDamage() {
		return dharokDamage;
	}
	
	public void resetDharokDamage() {
		this.dharokDamage = 0;
	}
	
	public void setDharokDamage(int damage) {
		this.dharokDamage = damage;
	}
	/**
	 * @param ignoresSize
	 *            the ignoresSize to set
	 */
	public void setIgnoresSize(int ignoresSize) {
		this.ignoresSize = ignoresSize;
	}

	/**
	 * @return the ignoresSize
	 */
	public int getIgnoresSize() {
		return ignoresSize;
	}

	/**
	 * @param friendsSize
	 *            the friendsSize to set
	 */
	public void setFriendsSize(int friendsSize) {
		this.friendsSize = friendsSize;
	}

	/**
	 * @return the friendsSize
	 */
	public int getFriendsSize() {
		return friendsSize;
	}

	/**
	 * @param ignores
	 *            the ignores to set
	 */
	public void setIgnores(long[] ignores) {
		this.ignores = ignores;
	}

	/**
	 * @return the ignores
	 */
	public long[] getIgnores() {
		return ignores;
	}

	public boolean isBusy = false;

	/**
	 * @return the player's status
	 */
	public boolean isBusy() {
		return isBusy;
	}

	/**
	 * @param isBusy
	 *            the status to be set
	 */
	public void setBusy(boolean isBusy) {
		this.isBusy = isBusy;
	}

	public boolean canWalk = true;

	/*
	 * @return if the player can walk
	 */
	public boolean canWalk() {
		return canWalk;
	}

	/**
	 * @param canWalk
	 *            set the player walking
	 */
	public void setCanWalk(boolean canWalk) {
		this.canWalk = canWalk;
	}

	public int specialAmount = 100;

	/**
	 * @param specialAmount
	 *            the specialAmount to set
	 */
	public void setSpecialAmount(int specialAmount) {
		this.specialAmount = specialAmount;
	}

	/**
	 * @return the specialAmount
	 */
	public int getSpecialAmount() {
		return specialAmount;
	}

	public int lastHit;

	public int skullTimer = 0;

	/**
	 * @return the skullTimer
	 */
	public int getSkullTimer() {
		return skullTimer;
	}

	/**
	 * @param skullTimer
	 *            the skullTimer to be set
	 */
	public void setSkullTimer(int skullTimer) {
		this.skullTimer = skullTimer;
	}

	public boolean ancients;

	/**
	 * @return ancients;
	 */
	public boolean isUsingAncients() {
		return ancients;
	}

	public int pkPoints;

	/**
	 * @return the pk points;
	 */
	public int getPkPoints() {
		return pkPoints;
	}

	public int logoutDelay = 0;

	public boolean playerIsMember = false;

	/**
	 * @return playerIsMember
	 */
	public boolean isPlayerMember() {
		return playerIsMember;
	}

	public int[] killedBy = new int[Constants.MAX_PLAYERS];

	public boolean alreadyInList(Client client) {
		for (int i = 0; i < lastKilled.length; i++) {
			if (lastKilled[i].equalsIgnoreCase(client.getUsername())) {
				return true;
			}
		}
		return false;
	}
	
	
	public void addNewKiller(Client client) {
		if(lastKilled[0] == null) {
			lastKilled[0] = client.getUsername();
		} else if (lastKilled[1] == null) {
			lastKilled[1] = lastKilled[0];
			lastKilled[0] = client.getUsername();
		} else {
			lastKilled[2] = lastKilled[1];
			lastKilled[1] = lastKilled[0];
			lastKilled[0] = client.getUsername();
		}
	}

	
	public void removeKiller(Client client) {
		for (int i = 0; i < lastKilled.length; i++) {
			if (lastKilled[i] == client.getUsername()) {
				lastKilled[i] = null;
			}
		}
	}
	public String[] getLastKilled() {
		return lastKilled;
	}
	
	public int getPlayerKiller() {
		int killerID;
		int killerHits;
		retry: {
			killerID = 0;
			killerHits = 0;
			int l = 1;
			do {
				if (l >= Constants.MAX_PLAYERS)
					break retry;
				if (killerID == 0) {
					killerID = l;
					killerHits = 1;
				} else if (killedBy[l] > killedBy[killerID]) {
					killerID = l;
					killerHits = 1;
				} else if (killedBy[l] == killedBy[killerID])
					killerHits++;
				l++;
			} while (true);
		}
		if (killerHits > 1)
			killerID = userID;
		return killerID;
	}

	public Player(int userID) {
		super();

		setUserID(userID);

		for (int i = 0; i < playerItems.length; i++) {
			playerItems[i] = 0;
		}
		for (int i = 0; i < playerItemsN.length; i++) {
			playerItemsN[i] = 0;
		}

		for (int i = 0; i < PlayerConstants.MAX_SKILLS; i++) {
			if (i == 3) {
				playerLevel[i] = 10;
			} else {
				playerLevel[i] = 1;
			}
		}

		for (int i = 0; i < PlayerConstants.MAX_SKILLS; i++) {
			if (i == 3) {
				playerXP[i] = 1155;
			} else {
				playerXP[i] = 0;
			}
		}

		for (int i = 0; i < playerBankSize; i++) {
			bankItems[i] = 0;
		}
		for (int i = 0; i < playerBankSize; i++) {
			bankItemsN[i] = 0;
		}

		for (int j1 = 0; j1 < killedBy.length; j1++)
			killedBy[j1] = 0;

		for (int i = 0; i < friends.length; i++) {
			friends[i] = 0;
		}
		for (int i = 0; i < ignores.length; i++) {
			ignores[i] = 0;
		}

		playerEquipment[PlayerConstants.HELM] = -1;
		playerEquipment[PlayerConstants.CAPE] = -1;
		playerEquipment[PlayerConstants.AMULET] = -1;
		playerEquipment[PlayerConstants.CHEST] = -1;
		playerEquipment[PlayerConstants.SHIELD] = -1;
		playerEquipment[PlayerConstants.BOTTOMS] = -1;
		playerEquipment[PlayerConstants.GLOVES] = -1;
		playerEquipment[PlayerConstants.BOOTS] = -1;
		playerEquipment[PlayerConstants.RING] = -1;
		playerEquipment[PlayerConstants.ARROWS] = -1;
		playerEquipment[PlayerConstants.WEAPON] = -1;

		playerLook[PlayerConstants.HEAD] = 0;
		playerLook[PlayerConstants.BODY] = 18;
		playerLook[PlayerConstants.ARMS] = 26;
		playerLook[PlayerConstants.HANDS] = 33;
		playerLook[PlayerConstants.LEGS] = 36;
		playerLook[PlayerConstants.FEET] = 42;
		playerLook[PlayerConstants.BEARD] = 10;

		setHeightLevel(0);
		teleportToX = 3251 + (Misc.random(2) - 1);
		teleportToY = 3422 + (Misc.random(2) - 1);

		setAbsX(-1);
		setAbsY(-1);
		mapRegionX = mapRegionY = -1;
		currentX = currentY = 0;
		resetWalkingQueue();

	}

	public void destruct() {
		playerListSize = 0;
		for (int i = 0; i < Constants.MAX_PLAYERS; i++)
			playerList[i] = null;

		npcListSize = 0;
		for (int i = 0; i < NPCManager.MAXIMUM_NPCS; i++)
			npcList[i] = null;

		setAbsX(-1);
		setAbsY(-1);
		mapRegionX = mapRegionY = -1;
		currentX = currentY = 0;
		resetWalkingQueue();
	}

	public PlayerManager manager = null;

	public boolean initialized = false;
	public boolean disconnected = false;
	public boolean isActive = false;

	public abstract void initialize();

	public abstract void update();

	public abstract void process();

	protected static Stream playerProps;

	static {
		playerProps = new Stream(new byte[PlayerConstants.PROPS_SIZE]);
	}
	

	public int getPoisonDelay() {
		return poisonDelay;
	}
	public int[] playerLook = new int[13];

	public int runEmote = 0x338;
	public int standEmote = 0x328;
	public int walkEmote = 0x333;
	public int headIcon = -1;
	public int prayerId = -1;
	public int bountyIcon = -1;
	public int skullIcon = -1;
	public int hintIcon = -1;


	public int npcID = 0;
	public boolean isNPC = false;

	public int[] playerEquipment = new int[14];
	public int[] playerEquipmentN = new int[14];

	public int[] playerLevel = new int[PlayerConstants.MAX_SKILLS];
	public int[] playerXP = new int[PlayerConstants.MAX_SKILLS];

	public int getXPForLevel(int level) {
		int points = 0;
		int output = 0;

		for (int lvl = 1; lvl <= level; lvl++) {
			points += Math.floor(lvl + 300.0 * Math.pow(2.0, lvl / 7.0));
			if (lvl >= level) {
				return output;
			}
			output = (int) Math.floor(points / 4);
		}
		return 0;
	}

	public int getLevelForXP(int exp) {
		int points = 0;
		int output = 0;

		for (int lvl = 1; lvl <= 99; lvl++) {
			points += Math.floor(lvl + 300.0 * Math.pow(2.0, lvl / 7.0));
			output = (int) Math.floor(points / 4);
			if (output >= exp)
				return lvl;
		}
		return 99;
	}

	public int[] playerItems = new int[28];
	public int[] playerItemsN = new int[28];

	private int playerBankSize;
	public int[] bankItems = new int[800];
	public int[] bankItemsN = new int[800];

	private int bankXremoveID = 0;
	private int bankXinterfaceID = 0;
	private int bankXremoveSlot = 0;

	public boolean takeAsNote = false;

	/**
	 * @param bankXinterfaceID
	 *            the bankXinterfaceID to set
	 */
	public void setBankXinterfaceID(int bankXinterfaceID) {
		this.bankXinterfaceID = bankXinterfaceID;
	}

	/**
	 * @return the bankXinterfaceID
	 */
	public int getBankXinterfaceID() {
		return bankXinterfaceID;
	}

	/**
	 * @param bankXremoveSlot
	 *            the bankXremoveSlot to set
	 */
	public void setBankXremoveSlot(int bankXremoveSlot) {
		this.bankXremoveSlot = bankXremoveSlot;
	}

	/**
	 * @return the bankXremoveSlot
	 */
	public int getBankXremoveSlot() {
		return bankXremoveSlot;
	}

	/**
	 * @param bankXremoveID
	 *            the bankXremoveID to set
	 */
	public void setBankXremoveID(int bankXremoveID) {
		this.bankXremoveID = bankXremoveID;
	}

	/**
	 * @return the bankXremoveID
	 */
	public int getBankXremoveID() {
		return bankXremoveID;
	}

	/**
	 * @return the playerBankSize
	 */
	public int getPlayerBankSize() {
		return playerBankSize;
	}

	/**
	 * @param playerBankSize
	 *            the playerBankSize to set
	 */
	public void setPlayerBankSize(int playerBankSize) {
		this.playerBankSize = playerBankSize;
	}
	public void appendPlayerAppearance(Stream str) {
		playerProps.currentOffset = 0;
		playerProps.writeByte(playerLook[PlayerConstants.SEX]);
		playerProps.writeByte(headIcon);
		playerProps.writeByte(skullIcon);
		playerProps.writeByte(hintIcon);
		
		if (!isNPC && playerEquipment[PlayerConstants.RING] != 6583 || getTarget() != null) {
			if (playerEquipment[PlayerConstants.HELM] > 1) {
				playerProps
						.writeWord(0x200 + playerEquipment[PlayerConstants.HELM]);
			} else {
				playerProps.writeByte(0);
			}
			if (playerEquipment[PlayerConstants.CAPE] > 1) {
				playerProps
						.writeWord(0x200 + playerEquipment[PlayerConstants.CAPE]);
			} else {
				playerProps.writeByte(0);
			}
			if (playerEquipment[PlayerConstants.AMULET] > 1) {
				playerProps
						.writeWord(0x200 + playerEquipment[PlayerConstants.AMULET]);
			} else {
				playerProps.writeByte(0);
			}
			if (playerEquipment[PlayerConstants.WEAPON] > 1) {
				playerProps
						.writeWord(0x200 + playerEquipment[PlayerConstants.WEAPON]);
			} else {
				playerProps.writeByte(0);
			}
			if (playerEquipment[PlayerConstants.CHEST] > 1) {
				playerProps
						.writeWord(0x200 + playerEquipment[PlayerConstants.CHEST]);
			} else {
				playerProps.writeWord(0x100 + playerLook[PlayerConstants.BODY]);
			}
			if (playerEquipment[PlayerConstants.SHIELD] > 1) {
				playerProps
						.writeWord(0x200 + playerEquipment[PlayerConstants.SHIELD]);
			} else {
				playerProps.writeByte(0);
			}
			if (!Item.isPlate(playerEquipment[PlayerConstants.CHEST])) {
				playerProps.writeWord(0x100 + playerLook[PlayerConstants.ARMS]);
			} else {
				playerProps.writeByte(0);
			}
			if (playerEquipment[PlayerConstants.BOTTOMS] > 1) {
				playerProps
						.writeWord(0x200 + playerEquipment[PlayerConstants.BOTTOMS]);
			} else {
				playerProps.writeWord(0x100 + playerLook[PlayerConstants.LEGS]);
			}
			if (!Item.isFullHelm(playerEquipment[PlayerConstants.HELM])
					&& !Item.isFullMask(playerEquipment[PlayerConstants.HELM])) {
				playerProps.writeWord(0x100 + playerLook[PlayerConstants.HEAD]);
			} else {
				playerProps.writeByte(0);
			}
			if (playerEquipment[PlayerConstants.GLOVES] > 1) {
				playerProps
						.writeWord(0x200 + playerEquipment[PlayerConstants.GLOVES]);
			} else {
				playerProps
						.writeWord(0x100 + playerLook[PlayerConstants.HANDS]);
			}
			if (playerEquipment[PlayerConstants.BOOTS] > 1) {
				playerProps
						.writeWord(0x200 + playerEquipment[PlayerConstants.BOOTS]);
			} else {
				playerProps.writeWord(0x100 + playerLook[PlayerConstants.FEET]);
			}
			if (!Item.isFullMask(playerEquipment[PlayerConstants.HELM])
					&& (playerLook[PlayerConstants.SEX] != 1)) {
				playerProps
						.writeWord(0x100 + playerLook[PlayerConstants.BEARD]);
			} else {
				playerProps.writeByte(0);
			}
		} else {
			if (playerEquipment[PlayerConstants.RING] == 6583 && getTarget() == null) {
				npcID = 2626;
			} else if  (npcID == 2626 && playerEquipment[PlayerConstants.RING] != 6583) {
				npcID = -1;
			}
			playerProps.writeWord(-1);
			playerProps.writeWord(npcID);
		}

		playerProps.writeByte(playerLook[PlayerConstants.HAIR_COLOUR]);
		playerProps.writeByte(playerLook[PlayerConstants.BODY_COLOUR]);
		playerProps.writeByte(playerLook[PlayerConstants.LEG_COLOUR]);
		playerProps.writeByte(playerLook[PlayerConstants.FEET_COLOUR]);
		playerProps.writeByte(playerLook[PlayerConstants.SKIN_COLOUR]);
		playerProps.writeWord(standEmote);
		playerProps.writeWord(0x337);
		playerProps.writeWord(walkEmote);
		playerProps.writeWord(0x334);
		playerProps.writeWord(0x335);
		playerProps.writeWord(0x336);
		playerProps.writeWord(runEmote);
		playerProps.writeQWord(Misc.playerNameToInt64(getUsername()));
		int combatLevel = getCombatLevel();
		playerProps.writeByte(combatLevel);
		playerProps.writeWord(0);
		str.writeByteC(playerProps.currentOffset);
		str.writeBytes(playerProps.buffer, playerProps.currentOffset, 0);
	}

	public int combatLevel = 3;

	public int getCombatLevel() {

		final int attack = getLevelForXP(playerXP[0]);
		final int defence = getLevelForXP(playerXP[1]);
		final int strength = getLevelForXP(playerXP[2]);
		final int hp = getLevelForXP(playerXP[3]);
		final int prayer = getLevelForXP(playerXP[5]);
		final int ranged = getLevelForXP(playerXP[4]);
		final int magic = getLevelForXP(playerXP[6]);
		int combatLevel = 3;	
		combatLevel = (int) ((defence + hp + Math.floor(prayer / 2)) * 0.2535) + 1;
		final double melee = (attack + strength) * 0.325;
		final double ranger = Math.floor(ranged * 1.5) * 0.325;
		final double mage = Math.floor(magic * 1.5) * 0.325;
		if (melee >= ranger && melee >= mage) {
			combatLevel += melee;
		} else if (ranger >= melee && ranger >= mage) {
			combatLevel += ranger;
		} else if (mage >= melee && mage >= ranger) {
			combatLevel += mage;
		}
		if(combatLevel <= 126) {
			return combatLevel;
		} else {
			return 126;
		}
	}


	public boolean appearanceUpdateRequired = true;
	public boolean updateRequired = true;

	public byte chatText[] = new byte[4096];
	public byte chatTextSize = 0;
	public int chatTextEffects = 0;
	public int chatTextColor = 0;
	public boolean chatTextUpdateRequired = false;

	public void appendplayerChatOptionsText(Stream str) {
		str.writeWordBigEndian(((chatTextColor & 0xFF) << 8)
				+ (chatTextEffects & 0xFF));
		str.writeByte(getPrivileges());
		str.writeByteC(chatTextSize);
		str.writeBytes_reverse(chatText, chatTextSize, 0);
	}

	public int gfxID = 0;
	public int gfxDelay = 0;
	public boolean graphicsUpdateRequired = false;

	public void appendGraphicsUpdate(Stream str) {
		str.writeWordBigEndian(gfxID);
		str.writeDWord(gfxDelay);
	}

	public int animationRequest = -1, animationWaitCycles = 0;
	protected boolean animationUpdateRequired = false;

	public void appendAnimationRequest(Stream str) {
		str.writeWordBigEndian((animationRequest == -1) ? 65535
				: animationRequest);
		str.writeByte(animationWaitCycles);
	}

	public String forcedText = "";
	public boolean forcedTextUpdateRequired = false;

	public void appendForcedText(Stream str) {
		str.writeString(forcedText);
	}

	public int turnPlayerTo = -1;
	public boolean turnPlayerToUpdateRequired = false;

	public void appendTurnPlayerToUpdate(Stream str) {
		str.writeWordBigEndian(turnPlayerTo);
	}

	public void resetFaceDirection() {
		turnPlayerTo = 65535;
		updateRequired = true;
		turnPlayerToUpdateRequired = true;
	}

	public int viewToX = -1;
	public int viewToY = -1;

	public boolean faceUpdateRequired = false;

	public void appendFaceUpdate(Stream str) {
		str.writeWordBigEndianA(viewToX);
		str.writeWordBigEndian(viewToY);
	}

	public int hitpoints = 10;
	public boolean dead = false;

	public boolean isDead() {
		return dead;
	}

	public void setDead(boolean dead) {
		this.dead = dead;
	}

	public int hitDiff = 0;
	public boolean hitUpdateRequired = false;

	protected void appendHitUpdate(Stream str) {
		if (hitpoints < 0) {
			hitpoints = 0;
		}
		str.writeByte(hitDiff);
		if (hitDiff > 0) {
			str.writeByteA(1);
		} else {
			str.writeByteA(0);
		}
		str.writeByteC(hitpoints);
		str.writeByte(getLevelForXP(playerXP[3]));
		if (hitpoints <= 0) {
			setDead(true);
			hitpoints = 0;
		}
	}

	public int hitDiff2 = 0;
	public boolean hitUpdateRequired2 = false;

	public void appendHitUpdate2(Stream str) {
		if (hitpoints < 0) {
			hitpoints = 0;
		}
		str.writeByte(hitDiff2);
		if (!poisonHit) {
			if (hitDiff2 > 0) {
				str.writeByteS(1);
			} else {
				str.writeByteS(0);
			}
		} else {
			str.writeByteS(2);
		}
		str.writeByte(hitpoints);
		str.writeByteC(getLevelForXP(playerXP[3]));
		if (hitpoints <= 0) {
			setDead(true);
			hitpoints = 0;
		}
	}

	public void appendPlayerUpdateBlock(Stream str) {
		if (!updateRequired && !chatTextUpdateRequired)
			return;
		int updateMask = 0;

		if (graphicsUpdateRequired)
			updateMask |= 0x100;	
		if (animationRequest != -1)
			updateMask |= 8;
		if (forcedTextUpdateRequired)
			updateMask |= 4;
		if (chatTextUpdateRequired)
			updateMask |= 0x80;
		if (turnPlayerToUpdateRequired)
			updateMask |= 1;
		if (appearanceUpdateRequired)
			updateMask |= 0x10;
		if (faceUpdateRequired)
			updateMask |= 2;
		if (hitUpdateRequired)
			updateMask |= 0x20;
		if (hitUpdateRequired2)
			updateMask |= 0x200;

		if (updateMask >= 0x100) {
			updateMask |= 0x40;
			str.writeByte(updateMask & 0xFF);
			str.writeByte(updateMask >> 8);
		} else {
			str.writeByte(updateMask);
		}
		if (graphicsUpdateRequired)
			appendGraphicsUpdate(str);
		if (animationRequest != -1)
			appendAnimationRequest(str);
		if (forcedTextUpdateRequired)
			appendForcedText(str);
		if (chatTextUpdateRequired)
			appendplayerChatOptionsText(str);
		if (turnPlayerToUpdateRequired)
			appendTurnPlayerToUpdate(str);
		if (appearanceUpdateRequired)
			appendPlayerAppearance(str);
		if (faceUpdateRequired)
			appendFaceUpdate(str);
		if (hitUpdateRequired)
			appendHitUpdate(str);
		if (hitUpdateRequired2)
			appendHitUpdate2(str);
	}

	public void clearUpdateFlags() {
		updateRequired = false;
		graphicsUpdateRequired = false;
		animationRequest = -1;
		forcedText = null;
		forcedTextUpdateRequired = false;
		chatTextUpdateRequired = false;
		turnPlayerTo = 65535;
		turnPlayerToUpdateRequired = false;
		appearanceUpdateRequired = false;
		faceUpdateRequired = false;
		hitUpdateRequired = false;
		hitUpdateRequired2 = false;
		poisonHit = false;
	}

	public int walkingQueueX[] = new int[PlayerConstants.WALKING_QUEUE_SIZE],
			walkingQueueY[] = new int[PlayerConstants.WALKING_QUEUE_SIZE];

	public int directionCount = 0;
	public int dir1 = -1, dir2 = -1;
	public int currentX, currentY = 0;
	public int mapRegionX, mapRegionY = 0;
	public int wQueueReadPtr = 0;
	public int wQueueWritePtr = 0;

	public int energy = 100;

	/**
	 * @return the energy
	 */
	public int getEnergy() {
		return energy;
	}

	public boolean didTeleport = false;
	public boolean isRunning = false;
	public boolean isRunning2 = false;
	public boolean mapRegionDidChange = false;

	public boolean wi1ldArea() {
		return getAbsX() >= 2940 && getAbsX() <= 3395 && getAbsY() >= 3520
				&& getAbsY() <= 4000;
	}

	public void setRunning(boolean b) {
		isRunning = b;
		isRunning2 = b;
	}

	public int teleportToX = -1, teleportToY = -1, teleportToZ = -1;

	public int newWalkCmdX[] = new int[PlayerConstants.WALKING_QUEUE_SIZE];
	public int newWalkCmdY[] = new int[PlayerConstants.WALKING_QUEUE_SIZE];

	public int newWalkCmdSteps = 0;
	public boolean newWalkCmdIsRunning = false;
	public int travelBackX[] = new int[PlayerConstants.WALKING_QUEUE_SIZE];
	public int travelBackY[] = new int[PlayerConstants.WALKING_QUEUE_SIZE];
	public int numTravelBackSteps = 0;

	public void resetWalkingQueue() {
		wQueueReadPtr = wQueueWritePtr = 0;
		for (int i = 0; i < PlayerConstants.WALKING_QUEUE_SIZE; i++) {
			walkingQueueX[i] = currentX;
			walkingQueueY[i] = currentY;
		}
	}

	public void addToWalkingQueue(int x, int y) {
		int next = (wQueueWritePtr + 1) % PlayerConstants.WALKING_QUEUE_SIZE;
		if (next == wQueueWritePtr) {
			return;
		}
		walkingQueueX[wQueueWritePtr] = x;
		walkingQueueY[wQueueWritePtr] = y;
		wQueueWritePtr = next;
	}

	protected boolean zoneRequired = true;

	public int getNextWalkingDirection() {
		
		if (wQueueReadPtr == wQueueWritePtr)
			return -1;
		int dir;
		do {
			dir = Misc.direction(currentX, currentY,
					walkingQueueX[wQueueReadPtr], walkingQueueY[wQueueReadPtr]);
			if (dir == -1)
				wQueueReadPtr = (wQueueReadPtr + 1)
						% PlayerConstants.WALKING_QUEUE_SIZE;
			else if ((dir & 1) != 0) {
				println_debug("Invalid waypoint in walking queue!");
				resetWalkingQueue();
				return -1;
			}
		} while (dir == -1 && wQueueReadPtr != wQueueWritePtr);
		if (dir == -1)
			return -1;
		
		
		dir >>= 1;
		currentX += Misc.directionDeltaX[dir];
		currentY += Misc.directionDeltaY[dir];
		setAbsX(getAbsX() + Misc.directionDeltaX[dir]);
		setAbsY(getAbsY() + Misc.directionDeltaY[dir]);
		return dir;
	}
	
	
	public void stopMovement() {
		if (teleportToX <= 0 && teleportToY <= 0) {
			teleportToX = getAbsX();
			teleportToY = getAbsY();
		}
		newWalkCmdSteps = 0;
		newWalkCmdX[0] = newWalkCmdY[0] = travelBackX[0] = travelBackY[0] = 0;
		getNextPlayerMovement();
	}

	public void updatePlayerMovement(Stream str) {
		if (dir1 == -1) {
			if (updateRequired || chatTextUpdateRequired) {
				str.writeBits(1, 1);
				str.writeBits(2, 0);
			} else
				str.writeBits(1, 0);
		} else if (dir2 == -1) {
			str.writeBits(1, 1);
			str.writeBits(2, 1);
			str.writeBits(3, Misc.xlateDirectionToClient[dir1]);
			str.writeBits(1, (updateRequired || chatTextUpdateRequired) ? 1 : 0);
		} else {
			str.writeBits(1, 1);
			str.writeBits(2, 2);
			str.writeBits(3, Misc.xlateDirectionToClient[dir1]);
			str.writeBits(3, Misc.xlateDirectionToClient[dir2]);
			str.writeBits(1, (updateRequired || chatTextUpdateRequired) ? 1 : 0);
		}
	}
	
	public Client getFollowPlayer() {
		return followPlayer;
	}
	public Client setFollowPlayer(Client follow) {
		return this.followPlayer = follow;
	}
	public void getNextPlayerMovement() {
		

		mapRegionDidChange = false;
		boolean heightLevelDidChange = false;
		didTeleport = false;
		dir1 = dir2 = -1;

		if (teleportToZ != -1) {
			setHeightLevel(teleportToZ);
			teleportToZ = -1;
			if (teleportToX == -1) {
				teleportToX = getAbsX();
			}
			if (teleportToY == -1) {
				teleportToY = getAbsY();
			}
			heightLevelDidChange = true;
		}
		if (teleportToX != -1 && teleportToY != -1) {
			mapRegionDidChange = true;
			if (mapRegionX != -1 && mapRegionY != -1) {
				int relX = teleportToX - mapRegionX * 8, relY = teleportToY
						- mapRegionY * 8;
				if (relX >= 2 * 8 && relX < 11 * 8 && relY >= 2 * 8
						&& relY < 11 * 8)
					mapRegionDidChange = false;
			}
			if (mapRegionDidChange) {
				mapRegionX = (teleportToX >> 3) - 6;
				mapRegionY = (teleportToY >> 3) - 6;

			}
			
			currentX = teleportToX - 8 * mapRegionX;
			currentY = teleportToY - 8 * mapRegionY;
			setAbsX(teleportToX);
			setAbsY(teleportToY);
			resetWalkingQueue();
			
			teleportToX = teleportToY = -1;
			didTeleport = true;

		} else  {
			if (((Client)this).getFreezeDelay() > 0) {
				return;
			}
			if (following != null){
				if (followPlayerIdle) {	
					followPlayerIdle = false;
					return;
				}
				if (pauseFollow || JailSystem.inJail((Client)this)) 
					return;
				
				dir1 = FollowEngine.getNextFollowingDirection((Client) this, following);
				
				if (dir1 == -1) {
					followPlayerIdle = true;
				}
			} else	 
			
				dir1 = getNextWalkingDirection();
		
			if (forceMove) //overwrite old walking path. 
				dir1 = ForceWalker.getForceMovement((Client)this, forceMovement);
			
			if (dir1 == -1 || JailSystem.inJail((Client)this)) 
				return;

			if (isRunning && following != null) 
			{
				if(following != null)
					dir2 = FollowEngine.getNextFollowingDirection((Client) this, following);
				else
					dir2 = getNextWalkingDirection();
				

			} else if (isRunning) {
				dir2 = getNextWalkingDirection();
			} else {
				setRunning(false);
			}	
			int deltaX = 0, deltaY = 0;
			if (currentX < 2 * 8) {
				deltaX = 4 * 8;
				mapRegionX -= 4;
				mapRegionDidChange = true;
			} else if (currentX >= 11 * 8) {
				deltaX = -4 * 8;
				mapRegionX += 4;
				mapRegionDidChange = true;
			}
			if (currentY < 2 * 8) {
				deltaY = 4 * 8;
				mapRegionY -= 4;
				mapRegionDidChange = true;
			} else if (currentY >= 11 * 8) {
				deltaY = -4 * 8;
				mapRegionY += 4;
				mapRegionDidChange = true;
			}

			if (mapRegionDidChange) {
				currentX += deltaX;
				currentY += deltaY;
				for (int i = 0; i < PlayerConstants.WALKING_QUEUE_SIZE; i++) {
					walkingQueueX[i] += deltaX;
					walkingQueueY[i] += deltaY;
				}
			}
		}
		if (mapRegionDidChange || heightLevelDidChange) {
			zoneRequired = true;
		}
	}
	public boolean pauseFollow = false, followPlayerIdle = false;
	public Client followPlayer = null;

	public void updateThisPlayerMovement(Stream str) {
		if (mapRegionDidChange) {
			str.createFrame(73);
			str.writeWordA(mapRegionX + 6);
			str.writeWord(mapRegionY + 6);
		}
		if (didTeleport) {
			str.createFrameVarSizeWord(81);
			str.initBitAccess();
			str.writeBits(1, 1);
			str.writeBits(2, 3);
			str.writeBits(2, getHeightLevel());
			str.writeBits(1, 1);
			str.writeBits(1, (updateRequired) ? 1 : 0);
			str.writeBits(7, currentY);
			str.writeBits(7, currentX);
			return;
		}
		if (dir1 == -1) {
			str.createFrameVarSizeWord(81);
			str.initBitAccess();
			if (updateRequired) {
				str.writeBits(1, 1);
				str.writeBits(2, 0);
			} else {
				str.writeBits(1, 0);
			}
			if (directionCount < PlayerConstants.WALKING_QUEUE_SIZE) {
				directionCount++;
			}
		} else {
			directionCount = 0;
			str.createFrameVarSizeWord(81);
			str.initBitAccess();
			str.writeBits(1, 1);

			if (dir2 == -1) {
				str.writeBits(2, 1);
				str.writeBits(3, Misc.xlateDirectionToClient[dir1]);
				if (updateRequired) {
					str.writeBits(1, 1);
				} else {
					str.writeBits(1, 0);
				}
			} else {
				str.writeBits(2, 2);
				str.writeBits(3, Misc.xlateDirectionToClient[dir1]);
				str.writeBits(3, Misc.xlateDirectionToClient[dir2]);
				if (updateRequired) {
					str.writeBits(1, 1);
				} else {
					str.writeBits(1, 0);
				}
			}
		}
	}

	public void updateEnergy() {
		Client client = (Client) this;
		client.getActionSender().sendQuest(energy + "%", 149);
	}

	public void postProcessing() {
		if (newWalkCmdSteps > 0) {
			int firstX = newWalkCmdX[0], firstY = newWalkCmdY[0];
			int lastDir = 0;
			boolean found = false;
			numTravelBackSteps = 0;
			int ptr = wQueueReadPtr;
			int dir = Misc.direction(currentX, currentY, firstX, firstY);
			if (dir != -1 && (dir & 1) != 0) {

				do {
					lastDir = dir;
					if (--ptr < 0)
						ptr = PlayerConstants.WALKING_QUEUE_SIZE - 1;
					
					travelBackX[numTravelBackSteps] = walkingQueueX[ptr];
					travelBackY[numTravelBackSteps++] = walkingQueueY[ptr];
					
					dir = Misc.direction(walkingQueueX[ptr],
							walkingQueueY[ptr], firstX, firstY);
					if (lastDir != dir) {
						found = true;
						break;
					}

				} while (ptr != wQueueWritePtr);
			} else
				found = true;

			if (!found)
				println_debug("Fatal: couldn't find connection vertex! Dropping packet.");
			else {
				wQueueWritePtr = wQueueReadPtr;

				addToWalkingQueue(currentX, currentY);

				if (dir != -1 && (dir & 1) != 0) {

					for (int i = 0; i < numTravelBackSteps - 1; i++) {
						addToWalkingQueue(travelBackX[i], travelBackY[i]);
					}
					int wayPointX2 = travelBackX[numTravelBackSteps - 1], wayPointY2 = travelBackY[numTravelBackSteps - 1];
					int wayPointX1, wayPointY1;
					if (numTravelBackSteps == 1) {
						wayPointX1 = currentX;
						wayPointY1 = currentY;
					} else {
						wayPointX1 = travelBackX[numTravelBackSteps - 2];
						wayPointY1 = travelBackY[numTravelBackSteps - 2];
					}

					dir = Misc.direction(wayPointX1, wayPointY1, wayPointX2,
							wayPointY2);
					if (dir == -1 || (dir & 1) != 0) {
						println_debug("Fatal: The walking queue is corrupt! wp1=("
								+ wayPointX1
								+ ", "
								+ wayPointY1
								+ "), "
								+ "wp2=("
								+ wayPointX2
								+ ", "
								+ wayPointY2
								+ ")");
					} else {
						dir >>= 1;
						found = false;
						int x = wayPointX1, y = wayPointY1;
						perviousStepX = currentX;
						perviousStepY = currentY;
						while (x != wayPointX2 || y != wayPointY2) {
							x += Misc.directionDeltaX[dir];
							y += Misc.directionDeltaY[dir];
							if ((Misc.direction(x, y, firstX, firstY) & 1) == 0) {
								found = true;
								break;
							}
						}
						if (!found) {
							println_debug("Fatal: Internal error: unable to determine connection vertex!"
									+ "  wp1=("
									+ wayPointX1
									+ ", "
									+ wayPointY1
									+ "), wp2=("
									+ wayPointX2
									+ ", "
									+ wayPointY2
									+ "), "
									+ "first=("
									+ firstX + ", " + firstY + ")");
						} else
							addToWalkingQueue(wayPointX1, wayPointY1);
					}
				} else {
					for (int i = 0; i < numTravelBackSteps; i++) {
						addToWalkingQueue(travelBackX[i], travelBackY[i]);
					}
				}

				for (int i = 0; i < newWalkCmdSteps; i++) {
					addToWalkingQueue(newWalkCmdX[i], newWalkCmdY[i]);
				}

			}
			isRunning = newWalkCmdIsRunning || isRunning2;
		}
		preProcessing();
	}
	public int perviousStepX = 0, perviousStepY = 0;
	public void preProcessing() {
		newWalkCmdSteps = 0;
	}

	public Player playerList[] = new Player[Constants.MAX_PLAYERS];
	public int playerListSize = 0;
	public byte playerInListBitmap[] = new byte[(Constants.MAX_PLAYERS + 7) >> 3];

	public void addNewPlayer(Player plr, Stream str, Stream updateBlock) {
		int id = plr.getUserID();
		playerInListBitmap[id >> 3] |= 1 << (id & 7);
		playerList[playerListSize++] = plr;

		str.writeBits(11, id);

		str.writeBits(1, 1);
		boolean savedFlag = plr.appearanceUpdateRequired;
		boolean savedUpdateRequired = plr.updateRequired;
		plr.appearanceUpdateRequired = true;
		plr.updateRequired = true;
		plr.appendPlayerUpdateBlock(updateBlock);
		plr.appearanceUpdateRequired = savedFlag;
		plr.updateRequired = savedUpdateRequired;

		str.writeBits(1, 1);

		int z = plr.getAbsY() - getAbsY();
		if (z < 0)
			z += 32;
		str.writeBits(5, z);
		z = plr.getAbsX() - getAbsX();
		if (z < 0)
			z += 32;
		str.writeBits(5, z);
	}

	public boolean withinDistance(int targetX, int targetY, int distance) {
		for (int i = 0; i <= distance; i++) {
			for (int j = 0; j <= distance; j++) {
				if ((targetX + i) == getAbsX()
						&& ((targetY + j) == getAbsY()
								|| (targetY - j) == getAbsY() || targetY == getAbsY())) {
					return true;
				} else if ((targetX - i) == getAbsX()
						&& ((targetY + j) == getAbsY()
								|| (targetY - j) == getAbsY() || targetY == getAbsY())) {
					return true;
				} else if (targetX == getAbsX()
						&& ((targetY + j) == getAbsY()
								|| (targetY - j) == getAbsY() || targetY == getAbsY())) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean withinDistance(Player otherPlr) {
		if (getHeightLevel() != otherPlr.getHeightLevel()) {
			return false;
		}
		int deltaX = otherPlr.getAbsX() - getAbsX(), deltaY = otherPlr
				.getAbsY()
				- getAbsY();
		return deltaX <= 15 && deltaX >= -16 && deltaY <= 15 && deltaY >= -16;
	}

	public boolean withinInteractionDistance(Client otherClient) {
		return withinInteractionDistance(otherClient.getAbsX(), otherClient
				.getAbsY(), otherClient.getHeightLevel());
	}

	public boolean withinInteractionDistance(int x, int y, int z) {
		if (getHeightLevel() != z)
			return false;
		int deltaX = x - getAbsX(), deltaY = y - getAbsY();
		return deltaX <= 2 && deltaX >= -2 && deltaY <= 2 && deltaY >= -2;
	}

	public boolean withinDistance(NPC npc) {
		if (getHeightLevel() != npc.getHeightLevel())
			return false;
		int deltaX = npc.getAbsX() - getAbsX(), deltaY = npc.getAbsY()
				- getAbsY();
		return deltaX <= 15 && deltaX >= -16 && deltaY <= 15 && deltaY >= -16;
	}

	public static final int maxNPCListSize = NPCManager.MAXIMUM_NPCS;
	public NPC[] npcList = new NPC[maxNPCListSize];
	public int npcListSize = 0;

	public byte[] npcInListBitmap = new byte[(NPCManager.MAXIMUM_NPCS + 7) >> 3];
	public boolean rebuildNPCList = false;

	public void addNewNPC(NPC npc, Stream str, Stream updateBlock) {

		int slot = npc.getNpcId();
		npcInListBitmap[slot >> 3] |= 1 << (slot & 7);
		npcList[npcListSize++] = npc;

		str.writeBits(14, slot); //14

		int z = npc.getAbsY() - getAbsY();
		if (z < 0)
			z += 32;
		str.writeBits(5, z);
		z = npc.getAbsX() - getAbsX();
		if (z < 0)
			z += 32;
		str.writeBits(5, z);

		str.writeBits(1, 0);
		str.writeBits(12, npc.getDefinition().getType()); // 12

		boolean savedUpdateRequired = npc.isUpdateRequired();
		npc.setUpdateRequired(true); 
		npc.appendNPCUpdateBlock(updateBlock);
		npc.setUpdateRequired(savedUpdateRequired);
		str.writeBits(1, 1);
	}

	public boolean isKicked = false;

	public void kick() {
		Client client = (Client) this;
		client.getActionSender().sendLogout();
		isKicked = true;
	}
	/**
	 * Smithing
	 */
	
	public int smithxp;
	public int smithremove;
	public int smithmaketimes, smithremoveamount, amountToMakeBars, smithitem;
	public boolean hasfailed2123;
	
	public int smeltBarId;
	public int[] smelt_bars = { 2349, 2351, 2355, 2353, 2357, 2359, 2361, 2363 };
	public int[] smelt_frame = { 2405, 2406, 2407, 2409, 2410, 2411, 2412, 2413 };
	
	public int itemToAdd, barID, toremove, barsRemoved, timestomake, NOTUSED, NOTUSED2, xp = 0;
	
	public int item = -1;
	public int remove = -1;
	public int remove2 = -1;
	public int removeam = -1;
	public int removeam2 = -1;
	/**
	 * Fletching
	 */
	public int amountLeft = 0;
	public int log2 = 0;
	public int unstrungBow = 0;
	public String length = "";

	/**
	 * Fletching
	 */
	
	public int[] LOGS = { 1511, 1521, 1519, 1517, 1515, 1513 };
	public int[] UNSTRUNG_BOWS = { 50, 48, 54, 56, 58, 60, 62, 64,66, 68, 70, 72 };
	public int[] STRUNG_BOWS = { 841, 839, 843, 845, 847, 849,851, 853, 855, 857, 859, 861 };
	public int[] FLETCHING_LEVELS = { 5, 10, 25, 20, 40, 35, 55,50, 70, 65, 85, 80 };
	public int[] EXPERIENCE = { 5, 10, 15, 20, 70, 50, 110, 90, 155, 135, 200, 175 };
	public int[] ARROWSX = { 882, 884, 886, 888, 890, 892 };
	public int[] ARROW_HEADS = { 39, 40, 41, 42, 43, 44 };
	public int[] ARROW_LEVELS = { 1, 15, 30, 45, 60, 75 };
	public int[] ARROW_EXPERIENCE = { 15, 20, 22, 25, 30, 40 };
	public int[] DARTS = { 806, 807, 808, 809, 810, 811 };
	public int[] DART_TIPS = { 819, 820, 821, 822, 823, 824 };
	public int[] DART_LEVELS = { 1, 22, 37, 52, 67, 81 };
	public int[] DART_EXPERIENCE = { 12, 14, 16, 18, 20, 25 };
	public int[] LEFT_ITEM = { 50, 54, 60, 64, 68, 72 };
	public int[] RIGHT_ITEM = { 48, 56, 58, 62, 66, 70 };
	public String[] LEFT_ITEM_NAME = { "Longbow(u)", "Oak Longbow(u)","Willow Longbow(u)", "Maple Longbow(u)", "Yew Longbow(u)","Magic Longbow(u)" };
	public String[] RIGHT_ITEM_NAME = { "Shortbow(u)","Oak Shortbow(u)", "Willow Shortbow(u)", "Maple Shortbow(u)","Yew Shortbow(u)", "Magic Shortbow(u)" };
	public int FLETCHING_DELAY = 4;
	/** 
	 * cooking
	 */
	
	public int cooking;
	public int cookingAmount;
	public int cookingAnimation;
	
	/**
	 * Fishing
	 */
	public int fishing;
	public int fishingX;
	public int fishingY;
	
	public int destroyItem;
	
	public boolean slothSettings[] = new boolean[4];

}