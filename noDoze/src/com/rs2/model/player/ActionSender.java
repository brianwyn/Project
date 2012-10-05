package com.rs2.model.player;

/**
 * Client actions
 * @author Ultimate
 */

import com.rs2.model.Item;
import com.rs2.model.Shop;
import com.rs2.model.npc.NPC;

public class ActionSender {

	private Client client;

	public ActionSender(Client client) {
		this.setClient(client);
	}

	public void sendCookOption(int i) {
		sendFrame164(1743);
		sendFrame246(13716, 250, i);
		sendQuest("How many would you like to cook?", 13721);
		sendQuest("", 13717);
		sendQuest("", 13718);
		sendQuest("", 13719);
		sendQuest("", 13720);
	}
	
	public void sound(int songid, int vol, int delay) {
		getClient().outStream.createFrame(174);
		getClient().outStream.writeWord(songid);
		getClient().outStream.writeByte(vol);
		getClient().outStream.writeWord(delay);
	}

	public void selectOption(String question, String s1, String s2, String s3, String s4, String s5) {
		sendFrame171(1, 2465);
		sendFrame171(0, 2468);
		sendQuest(question, 2493);
		sendQuest(s1, 2494);
		sendQuest(s2, 2495);
		sendQuest(s3, 2496);
		sendQuest(s4, 2497);
		sendQuest(s5, 2498);
		sendFrame164(2492);
	}
	
	public void selectOption(String question, String s1, String s2, String s3) {
		sendFrame171(1, 2461);
		sendFrame171(0, 2462);
		sendQuest(question, 2493);
		sendQuest(s1, 2471);
		sendQuest(s2, 2472);
		sendQuest(s3, 2473);
		sendFrame164(2469);
	}
	public void selectOption(String question, String s1, String s2) {
		sendFrame171(1, 2461);
		sendFrame171(0, 2462);
		sendQuest(question, 2493);
		sendQuest(s1, 2461);
		sendQuest(s2, 2462);
		sendFrame164(2459);
	}

	public void sendFrame71(int a, int b){
		synchronized(getClient()) {
			getClient().outStream.createFrame(71);
			getClient().outStream.writeWord(a);
			getClient().outStream.writeByteA(b);
		}
    }
	public void sendFrame106(int a){
		synchronized(getClient()) {
			getClient().outStream.createFrame(106);
			getClient().outStream.writeByteC(a);
		}
	}
	public void sendFrame87(int id, int state) {
		synchronized(getClient()) {
			if(getClient().outStream != null && getClient() != null) {
				getClient().outStream.createFrame(87);
				getClient().outStream.writeWordBigEndian_dup(id);	
				getClient().outStream.writeDWord_v1(state);
				getClient().flushOutStream();
			}
		}
	}
	public void sendFrame99(int a){
		synchronized(getClient()) {
			getClient().outStream.createFrame(99);
			getClient().outStream.writeByte(a);
		}
	}
	public void sendMultiInterface(int i1) {
		synchronized(getClient()) {
			getClient().outStream.createFrame(61);//61
			getClient().outStream.writeByte(i1);
			getClient().updateRequired = true;
			getClient().appearanceUpdateRequired = true;
		}
	}

	public void sendConfig(int id, int state) {
		synchronized(getClient()) {
			if(getClient().outStream != null && getClient() != null) {
				getClient().outStream.createFrame(36);
				getClient().outStream.writeWordBigEndian(id);
				getClient().outStream.writeByte(state);
				getClient().flushOutStream();
			}		
		}
	}

	public void sendPkLocation() {
		synchronized(getClient()) {
			if(getClient().outStream != null && getClient() != null) {
				clearQuestInterface();
				sendQuest("Home", 2494);
				sendQuest("Town Center", 2495);
				sendQuest("Edge @red@Wildy@red@@bla@", 2496);
				sendQuest("Varrock @red@Wildy@red@@bla@", 2497);
				sendQuest("Mage Bank @red@Wildy@red@@bla@", 2498);
				sendFrame164(2492);
				getClient().flushOutStream();
			}		
		}
	}
	public void sendClientConfig(int id, int state) {
		synchronized(getClient()) {
			if(getClient().outStream == null || getClient() == null){
				return;
			}
			getClient().outStream.createFrame(36);
			getClient().outStream.writeWordBigEndian(id);
			getClient().outStream.writeByte(state);
			getClient().flushOutStream();
		}
	}

	public void sendFollowing(int followID, boolean npc, int distance) {
		synchronized(getClient()) {
			if(getClient().outStream != null && getClient() != null) {
				getClient().outStream.createFrame(174);
				getClient().outStream.writeWord(followID);
				getClient().outStream.writeByte(npc ? 0 : 1); // 0 - NPC's, 1 - Players.
				getClient().outStream.writeWord(distance);
				getClient().flushOutStream();
			}		
		}
	}

	public void sendConfig2(int id, int state) {
		synchronized(getClient()) {
			if(getClient().outStream != null && getClient() != null) {
				getClient().outStream.createFrame(87);
				getClient().outStream.writeWordBigEndian(id);
				getClient().outStream.writeDWord_v1(state);
				getClient().flushOutStream();
			}		
		}
	}

	public void sendWalkableInterface(int i) {
		synchronized(getClient()) {
			if(getClient().outStream != null && getClient() != null) {
				getClient().outStream.createFrame(208);
				getClient().outStream.writeWordBigEndian_dup(i);
			}		
		}
	}

	public void sendOption(String s, int pos) {
		synchronized(getClient()) {
			if(getClient().outStream != null && getClient() != null) {
				getClient().outStream.createFrameVarSize(104);
				getClient().outStream.writeByteC(pos);
				getClient().outStream.writeByteA(0);
				getClient().outStream.writeString(s);
				getClient().outStream.endFrameVarSize();
				getClient().flushOutStream();
			}		
		}
	}

	public void sendMessage(String s) {
		synchronized(getClient()) {
			if(getClient().outStream != null && getClient() != null) {
				getClient().outStream.createFrameVarSize(253);
				getClient().outStream.writeString(s);
				getClient().outStream.endFrameVarSize();
				getClient().flushOutStream();
			}		
		}
	}

	public void sendQuest(String s, int id) {
		synchronized(getClient()) {
			if(getClient().outStream != null && getClient() != null) {
				getClient().outStream.createFrameVarSizeWord(126);
				getClient().outStream.writeString(s);
				getClient().outStream.writeWordA(id);
				getClient().outStream.endFrameVarSizeWord();
				getClient().flushOutStream();
			}		
		}
	}

	public int[] textID = { 8145, 8147, 8148, 8149, 8150, 8151, 8152, 8153,
			8154, 8155, 8156, 8157, 8158, 8159, 8160, 8161, 8162, 8163, 8164,
			8165, 8166, 8167, 8168, 8169, 8170, 8171, 8172, 8173, 8174, 8175,
			8176, 8177, 8178, 8179, 8180, 8181, 8182, 8183, 8184, 8185, 8186,
			8187, 8188, 8189, 8190, 8191, 8192, 8193, 8194, 8195, 12174, 12175,
			12176, 12177, 12178, 12179, 12180, 12181, 12182, 12183, 12184,
			12185, 12186, 12187, 12188, 12189, 12190, 12191, 12192, 12193,
			12194, 12195, 12196, 12197, 12198, 12199, 12200, 12201, 12202,
			12203, 12204, 12205, 12206, 12207, 12208, 12209, 12210, 12211,
			12212, 12213, 12214, 12215, 12216, 12217, 12218, 12219, 12220,
			12221, 12222, 12223 };

	public void clearQuestInterface() {
		for (int i : textID) {
			sendQuest("", i);
		}
	}

	public void sendAnimationReset() {
		synchronized(getClient()) {
			if(getClient().outStream != null && getClient() != null) {
				getClient().outStream.createFrame(1);
				getClient().flushOutStream();
			}		
		}
	}

	public void sendDialogue(String s) {
		sendQuest(s, 357);
		sendFrame164(356);
	}

	public void sendFrame185(int i) {
		synchronized(getClient()) {
			if(getClient().outStream != null && getClient() != null) {
				getClient().outStream.createFrame(185);
				getClient().outStream.writeWordBigEndianA(i);
				getClient().flushOutStream();
			}		
		}
	}

	public void sendFrame36(int id, int state) {
		synchronized(getClient()) {
			if(getClient().outStream != null && getClient() != null) {
				getClient().outStream.createFrame(36);
				getClient().outStream.writeWordBigEndian(id);
				getClient().outStream.writeByte(state);
				getClient().flushOutStream();
			}		
		}
	}

	public void sendFrame200(int i, int j) {
		synchronized(getClient()) {
			if(getClient().outStream != null && getClient() != null) {
				getClient().outStream.createFrame(200);
				getClient().outStream.writeWord(i);
				getClient().outStream.writeWord(j);
				getClient().flushOutStream();
			}		
		}
	}

	public void sendFrame75(int npc, int i) {
		synchronized(getClient()) {
			if(getClient().outStream != null && getClient() != null) {
				getClient().outStream.createFrame(75);
				getClient().outStream.writeWordBigEndianA(npc);
				getClient().outStream.writeWordBigEndianA(i);
				getClient().flushOutStream();
			}		
		}
	}

	public void sendFrame164(int frame) {
		synchronized(getClient()) {
			if(getClient().outStream != null && getClient() != null) {
				getClient().outStream.createFrame(164);
				getClient().outStream.writeWordBigEndian_dup(frame);
				getClient().flushOutStream();
			}		
		}
	}

	public void sendFrame171(int id, int visible) {
		synchronized(getClient()) {
			if(getClient().outStream != null && getClient() != null) {
				getClient().outStream.createFrame(171);
				getClient().outStream.writeByte(visible);
				getClient().outStream.writeWord(id);
				getClient().flushOutStream();
			}		
		}
	}

	public void sendSidebar(int menuId, int form) {
		synchronized(getClient()) {
			if(getClient().outStream != null && getClient() != null) {
				getClient().outStream.createFrame(71);
				getClient().outStream.writeWord(form);
				getClient().outStream.writeByteA(menuId);
				getClient().flushOutStream();
			}		
		}
	}

	public void sendChatOptions(int publicChat, int privateChat, int tradeBlock) {
		synchronized(getClient()) {
			if(getClient().outStream != null && getClient() != null) {
				getClient().outStream.createFrame(206);
				getClient().outStream.writeByte(publicChat);
				getClient().outStream.writeByte(privateChat);
				getClient().outStream.writeByte(tradeBlock);
				getClient().flushOutStream();
			}		
		}
	}

	public void sendInterface(int interfaceID) {
		synchronized(getClient()) {
			if(getClient().outStream != null && getClient() != null) {
				getClient().outStream.createFrame(97);
				getClient().outStream.writeWord(interfaceID);
				getClient().flushOutStream();
			}		
		}
	}

	public void sendWindowsRemoval() {
		synchronized(getClient()) {
			if(getClient().outStream != null && getClient() != null) {
				getClient().outStream.createFrame(219);
				getClient().flushOutStream();
			}		
		}
	}
	public void sendFlagRemoval() {
		synchronized(getClient()) {
			if(getClient().outStream != null && getClient() != null) {
				getClient().outStream.createFrame(78);
				getClient().flushOutStream();
			}		
		}
	}
	public void sendBankInterface() {
		synchronized(getClient()) {
			if(getClient().outStream != null && getClient() != null) {
				//if (!GameEngine.getConsole().valid)
				//	return;
				getClient().outStream.createFrame(248);
				getClient().outStream.writeWordA(5292);
				getClient().outStream.writeWord(5063);
				getClient().flushOutStream();
				sendReplacementOfTempItem();
			}		
		}
	}

	public void sendFrame248(int mainFrame, int subFrame) {
		synchronized(getClient()) {
			if(getClient().outStream != null && getClient() != null) {
				getClient().outStream.createFrame(248);
				getClient().outStream.writeWordA(mainFrame);
				getClient().outStream.writeWord(subFrame);
				getClient().flushOutStream();
			}		
		}
	}
	
	public void sendFrame34(int id, int slot, int column, int amount) {
		synchronized(getClient()) {
			if(getClient().outStream != null && getClient() != null) {
				getClient().outStream.createFrameVarSizeWord(34); // init item to smith screen
				getClient().outStream.writeWord(column); // Column Across Smith Screen
				getClient().outStream.writeByte(4); // Total Rows?
				getClient().outStream.writeDWord(slot); // Row Down The Smith Screen
				getClient().outStream.writeWord(id+1); // item
				getClient().outStream.writeByte(amount); // how many there are?
				getClient().outStream.endFrameVarSizeWord();
			}
		}
	}

	public void sendFrame246(int mainFrame, int subFrame, int subFrame2) {
		synchronized(getClient()) {
			if(getClient().outStream != null && getClient() != null) {
				getClient().outStream.createFrame(246);
				getClient().outStream.writeWordBigEndian(mainFrame);
				getClient().outStream.writeWord(subFrame);
				getClient().outStream.writeWord(subFrame2);
				getClient().flushOutStream();
			}		
		}
	}

	public void sendCoords(int x, int y) {
		synchronized(getClient()) {
			if(getClient().outStream != null && getClient() != null) {
				getClient().outStream.createFrame(85);
				getClient().outStream.writeByteC(y - (getClient().mapRegionY * 8));
				getClient().outStream.writeByteC(x - (getClient().mapRegionX * 8));
			}		
		}
	}

	public void sendObject(int objectID, int objectX, int objectY,
			int objectHeight, int objectFace, int objectType) {
		synchronized(getClient()) {
			if(getClient().outStream != null && getClient() != null) {
				sendCoords(objectX, objectY);
				getClient().outStream.createFrame(151);
				getClient().outStream.writeByteA(0);
				getClient().outStream.writeWordBigEndian(objectID);
				getClient().outStream.writeByteS((objectType << 2) + (objectFace & 3));
				getClient().flushOutStream();
			}		
		}
	}

	public void sendObjectAnim(int x, int y, int id, int type, int orientation) {
		synchronized(getClient()) {
			if(getClient().outStream != null && getClient() != null) {
				sendCoords(x, y);
				getClient().outStream.createFrame(160);
				getClient().outStream.writeByteS(((x & 7) << 4) + (y & 7));
				getClient().outStream.writeByteS((type << 2) + (orientation & 3));
				getClient().outStream.writeWordA(id);
				getClient().flushOutStream();
			}		
		}
	}

	public void sendProjectile(int absY, int absX, int offsetY, int offsetX,
			int proID, int startHeight, int endHeight, int speed, int lockon) {
		synchronized(getClient()) {
			if(getClient().outStream != null && getClient() != null) {
				getClient().outStream.createFrame(85);
				getClient().outStream.writeByteC(absY - getClient().mapRegionY * 8 - 2);
				getClient().outStream.writeByteC(absX - getClient().mapRegionX * 8 - 3);
				getClient().outStream.createFrame(117);
				getClient().outStream.writeByte(50);
				getClient().outStream.writeByte(offsetY);
				getClient().outStream.writeByte(offsetX);
				getClient().outStream.writeWord(lockon);
				getClient().outStream.writeWord(proID);
				getClient().outStream.writeByte(startHeight);
				getClient().outStream.writeByte(endHeight);
				getClient().outStream.writeWord(51);
				getClient().outStream.writeWord(speed);
				getClient().outStream.writeByte(16);
				getClient().outStream.writeByte(64);
				getClient().flushOutStream();
			}
		}
	}
	
	/*
	 * This one supports angling for the projectile - killamess.
	 */
	public void sendProjectile(int absY, int absX, int offsetY, int offsetX,
			int proID, int startHeight, int endHeight, int speed, int angle, int lockon) {
		synchronized(getClient()) {
			if(getClient().outStream != null && getClient() != null) {
				getClient().outStream.createFrame(85);
				getClient().outStream.writeByteC(absY - getClient().mapRegionY * 8 - 2);
				getClient().outStream.writeByteC(absX - getClient().mapRegionX * 8 - 3);
				getClient().outStream.createFrame(117);
				getClient().outStream.writeByte(50);
				getClient().outStream.writeByte(offsetY);
				getClient().outStream.writeByte(offsetX);
				getClient().outStream.writeWord(lockon);
				getClient().outStream.writeWord(proID);
				getClient().outStream.writeByte(startHeight);
				getClient().outStream.writeByte(endHeight);
				getClient().outStream.writeWord(51);
				getClient().outStream.writeWord(speed);
				getClient().outStream.writeByte(angle); 
				getClient().outStream.writeByte(64);
				getClient().flushOutStream();
			}
		}
	}
	
	public void sendLogout() {
		synchronized(getClient()) {
			if(getClient().outStream != null && getClient() != null) {
				if (getClient().logoutDelay > 0) {
					sendMessage(Language.LOGOUT_COMBAT);
					return;
				}
				NPC.removeMyNPC(getClient());
				getClient().setLoggedOut(true);
				getClient().outStream.createFrame(109);
				getClient().flushOutStream();
			}		
		}
	}

	public void sendItemReset(int frame) {
		synchronized(getClient()) {
			if(getClient().outStream != null && getClient() != null) {
				getClient().outStream.createFrameVarSizeWord(53);
				getClient().outStream.writeWord(frame);
				getClient().outStream.writeWord(getClient().playerItems.length);
				for (int i = 0; i < getClient().playerItems.length; i++) {
					if (getClient().playerItemsN[i] > 254) {
						getClient().outStream.writeByte(255);
						getClient().outStream.writeDWord_v2(getClient().playerItemsN[i]);
					} else {
						getClient().outStream.writeByte(getClient().playerItemsN[i]);
					}
					getClient().outStream.writeWordBigEndianA(getClient().playerItems[i]);
				}
				getClient().outStream.endFrameVarSizeWord();
				getClient().flushOutStream();
			}
		}
	}

	public void sendItemReset() {
		getClient().outStream.createFrameVarSizeWord(53);
		getClient().outStream.writeWord(3214);
		getClient().outStream.writeWord(getClient().playerItems.length);
		for (int i = 0; i < getClient().playerItems.length; i++) {
			if (getClient().playerItemsN[i] > 254) {
				getClient().outStream.writeByte(255); // item's stack count. if
				getClient().outStream.writeDWord_v2(getClient().playerItemsN[i]); // and
			} else {
				getClient().outStream.writeByte(getClient().playerItemsN[i]);
			}
			if (getClient().playerItems[i] > PlayerConstants.MAX_ITEMS || getClient().playerItems[i] < 0) {
				getClient().playerItems[i] = PlayerConstants.MAX_ITEMS;
			}
			getClient().outStream.writeWordBigEndianA(getClient().playerItems[i]); // item
		}
		getClient().outStream.endFrameVarSizeWord();
		getClient().flushOutStream();
	}

	public void sendShopReset(Shop shop) {
		getClient().outStream.createFrameVarSizeWord(53);
		getClient().outStream.writeWord(3900);
		int count = 0;
		for (int i = 0; i < shop.getContainerSize(); i++) {
			Item si = shop.getItemBySlot(i);
			if (si != null) {
				count++;
			}
		}
		getClient().outStream.writeWord(count);
		for (int i = 0; i < shop.getContainerSize(); i++) {
			Item si = shop.getItemBySlot(i);
			if (si == null) {
				continue;
			}
			if (si.getAmount() > 254) {
				getClient().outStream.writeByte(255);
				getClient().outStream.writeDWord_v2(si.getAmount());
			} else {
				getClient().outStream.writeByte(si.getAmount());
			}
			getClient().outStream.writeWordBigEndianA(si.getId() + 1);
		}
		getClient().outStream.endFrameVarSizeWord();
		getClient().flushOutStream();
	}

	public void sendBankReset() {
		getClient().outStream.createFrameVarSizeWord(53);
		getClient().outStream.writeWord(5382); // bank
		getClient().outStream.writeWord(getClient().getPlayerBankSize());
		for (int i = 0; i < getClient().getPlayerBankSize(); i++) {
			if (getClient().bankItemsN[i] > 254) {
				getClient().outStream.writeByte(255);
				getClient().outStream.writeDWord_v2(getClient().bankItemsN[i]);
			} else {
				getClient().outStream.writeByte(getClient().bankItemsN[i]); // amount
			}
			if (getClient().bankItemsN[i] < 1)
				getClient().bankItems[i] = 0;
			if (getClient().bankItems[i] > PlayerConstants.MAX_ITEMS || getClient().bankItems[i] < 0) {
				getClient().bankItems[i] = PlayerConstants.MAX_ITEMS;
			}
			getClient().outStream.writeWordBigEndianA(getClient().bankItems[i]); // itemID
		}
		getClient().outStream.endFrameVarSizeWord();
		getClient().flushOutStream();
	}

	public void sendReplacementOfTempItem() {
		int itemCount = 0;
		for (int i = 0; i < getClient().playerItems.length; i++) {
			if (getClient().playerItems[i] > -1) {
				itemCount = i;
			}
		}
		getClient().outStream.createFrameVarSizeWord(53);
		getClient().outStream.writeWord(5064); // inventory
		getClient().outStream.writeWord(itemCount + 1); // number of items
		for (int i = 0; i < itemCount + 1; i++) {
			if (getClient().playerItemsN[i] > 254) {
				getClient().outStream.writeByte(255);
				getClient().outStream.writeDWord_v2(getClient().playerItemsN[i]);
			} else {
				getClient().outStream.writeByte(getClient().playerItemsN[i]);
			}
			if (getClient().playerItems[i] > PlayerConstants.MAX_ITEMS || getClient().playerItems[i] < 0) {
				getClient().playerItems[i] = PlayerConstants.MAX_ITEMS;
			}
			getClient().outStream.writeWordBigEndianA(getClient().playerItems[i]); // item
		}

		getClient().outStream.endFrameVarSizeWord();
		getClient().flushOutStream();
	}
	
	public boolean sendInventoryItem(int item, int amount, int slot) {
		
		if (item < 1 || item > PlayerConstants.MAX_ITEM_AMOUNT)
			return false;
		
		if (!Item.itemStackable[item] && getClient().getActionAssistant().freeSlots() < 1) { 
			sendMessage(Language.NO_SPACE);
			return false;
		}
		
		/**
		 * Add stackable item.
		 */
		if (Item.itemStackable[item]) {
			if (getClient().getActionAssistant().freeSlots() > 1 || getClient().getActionAssistant().playerHasItem(item, 1)) {
				for (int i = 0; i < getClient().playerItems.length; i++) {
					if (getClient().playerItems[i] == (item + 1)) {
						if ((getClient().playerItemsN[i] + amount) < PlayerConstants.MAX_ITEM_AMOUNT && (getClient().playerItemsN[i] + amount) > -1) {
							getClient().playerItemsN[i] += amount;
						} else {
							sendMessage("I don't think i'll be able to carry all that!");
							return false;
						}
						getClient().outStream.createFrameVarSizeWord(34);
						getClient().outStream.writeWord(3214);
						getClient().outStream.writeByte(i);
						getClient().outStream.writeWord(getClient().playerItems[i]);
						if (getClient().playerItemsN[i] > 254) {
							getClient().outStream.writeByte(255);
							getClient().outStream.writeDWord(getClient().playerItemsN[i]);
						} else {
							getClient().outStream.writeByte(getClient().playerItemsN[i]); // amount
						}
						getClient().outStream.endFrameVarSizeWord();
						return true;
					}
				}
			}
		}
		/**
		 * Add single slot item.
		 */
		boolean canAddToSlot = true;
			
		for (int i = 0; i < getClient().playerItems.length; i++) {
			if (i == slot) {
				if (getClient().playerItems[i] > 0) {
					canAddToSlot = false;
				}
			}
		}
		if (canAddToSlot) {
			for (int i = 0; i < getClient().playerItems.length; i++) {
				if (i == slot) {
					if ((getClient().playerItemsN[i] + amount) < PlayerConstants.MAX_ITEM_AMOUNT && (getClient().playerItemsN[i] + amount) > -1) {
						getClient().playerItems[i] = item + 1;
						getClient().playerItemsN[i] += amount;
					} else {
						sendMessage("I don't think i'll be able to carry all that!");
						return false;
					}
					getClient().outStream.createFrameVarSizeWord(34);
					getClient().outStream.writeWord(3214);
					getClient().outStream.writeByte(i);
					getClient().outStream.writeWord(getClient().playerItems[i]);
					if (getClient().playerItemsN[i] > 254) {
						getClient().outStream.writeByte(255);
						getClient().outStream.writeDWord(getClient().playerItemsN[i]);
					} else {
						getClient().outStream.writeByte(getClient().playerItemsN[i]); // amount						}
						getClient().outStream.endFrameVarSizeWord();
						getClient().flushOutStream();
						return true;
					}
				}
			}
		} else {
			for (int i = 0; i < getClient().playerItems.length; i++) {
				if (getClient().playerItems[i] < 1) {
					if ((getClient().playerItemsN[i] + amount) < PlayerConstants.MAX_ITEM_AMOUNT && (getClient().playerItemsN[i] + amount) > -1) {
						getClient().playerItems[i] = item + 1;
						getClient().playerItemsN[i] += amount;		
					} else {
						sendMessage("I don't think i'll be able to carry all that!");
						return false;
					}
					getClient().outStream.createFrameVarSizeWord(34);
					getClient().outStream.writeWord(3214);
					getClient().outStream.writeByte(i);
					getClient().outStream.writeWord(getClient().playerItems[i]);
					if (getClient().playerItemsN[i] > 254) {
						getClient().outStream.writeByte(255);
						getClient().outStream.writeDWord(getClient().playerItemsN[i]);
					} else {
						getClient().outStream.writeByte(getClient().playerItemsN[i]); // amount
					}
					getClient().outStream.endFrameVarSizeWord();
					getClient().flushOutStream();
					return true;
				}
			}
		}
		sendMessage(Language.NO_SPACE);
		return false;
	}
	
	public boolean sendInventoryItem2(int item, int amount, int slot) {
		if (item == -1)
			return false;
		if (!Item.itemStackable[item] || amount < 1) {
			amount = 1;
		}
		if ((getClient().getActionAssistant().freeSlots() >= amount && !Item.itemStackable[item])
				|| getClient().getActionAssistant().freeSlots() > 0) {
			for (int i = 0; i < getClient().playerItems.length; i++) {
				if (getClient().playerItems[i] == (item + 1)
						&& Item.itemStackable[item]
						&& getClient().playerItems[i] > 0) {
					getClient().playerItems[i] = (item + 1);
					if ((getClient().playerItemsN[i] + amount) < PlayerConstants.MAX_ITEM_AMOUNT
							&& (getClient().playerItemsN[i] + amount) > -1) {
						getClient().playerItemsN[i] += amount;
					} else {
						getClient().playerItemsN[i] = PlayerConstants.MAX_ITEM_AMOUNT;
					}
					getClient().outStream.createFrameVarSizeWord(34);
					getClient().outStream.writeWord(3214);
					getClient().outStream.writeByte(i);
					getClient().outStream.writeWord(getClient().playerItems[i]);
					if (getClient().playerItemsN[i] > 254) {
						getClient().outStream.writeByte(255);
						getClient().outStream.writeDWord(getClient().playerItemsN[i]);
					} else {
						getClient().outStream.writeByte(getClient().playerItemsN[i]); // amount
					}
					getClient().outStream.endFrameVarSizeWord();
					i = 30;
					return true;
				}
			}
			for (int i = 0; i < getClient().playerItems.length; i++) {
				if (getClient().playerItems[i] <= 0) {
					getClient().playerItems[i] = item + 1;
					if (amount < PlayerConstants.MAX_ITEM_AMOUNT && amount > -1) {
						getClient().playerItemsN[i] = amount;
					} else {
						getClient().playerItemsN[i] = PlayerConstants.MAX_ITEM_AMOUNT;
					}
					getClient().outStream.createFrameVarSizeWord(34);
					getClient().outStream.writeWord(3214);
					getClient().outStream.writeByte(i);
					getClient().outStream.writeWord(getClient().playerItems[i]);
					if (getClient().playerItemsN[i] > 254) {
						getClient().outStream.writeByte(255);
						getClient().outStream.writeDWord(getClient().playerItemsN[i]);
					} else {
						getClient().outStream.writeByte(getClient().playerItemsN[i]); // amount
					}
					getClient().outStream.endFrameVarSizeWord();
					getClient().flushOutStream();
					i = 30;
					return true;
				}
			}
			return false;
		} else {
			sendMessage(Language.NO_SPACE);
			return false;
		}
	}
	public boolean sendInventoryItem(int item, int amount) {
		if (item == -1)
			return false;
		if (!Item.itemStackable[item] || amount < 1) {
			amount = 1;
		}
		if ((getClient().getActionAssistant().freeSlots() >= amount && !Item.itemStackable[item])
				|| getClient().getActionAssistant().freeSlots() > 0) {
			for (int i = 0; i < getClient().playerItems.length; i++) {
				if (getClient().playerItems[i] == (item + 1)
						&& Item.itemStackable[item]
						&& getClient().playerItems[i] > 0) {
					getClient().playerItems[i] = (item + 1);
					if ((getClient().playerItemsN[i] + amount) < PlayerConstants.MAX_ITEM_AMOUNT
							&& (getClient().playerItemsN[i] + amount) > -1) {
						getClient().playerItemsN[i] += amount;
					} else {
						getClient().playerItemsN[i] = PlayerConstants.MAX_ITEM_AMOUNT;
					}
					getClient().outStream.createFrameVarSizeWord(34);
					getClient().outStream.writeWord(3214);
					getClient().outStream.writeByte(i);
					getClient().outStream.writeWord(getClient().playerItems[i]);
					if (getClient().playerItemsN[i] > 254) {
						getClient().outStream.writeByte(255);
						getClient().outStream.writeDWord(getClient().playerItemsN[i]);
					} else {
						getClient().outStream.writeByte(getClient().playerItemsN[i]); // amount
					}
					getClient().outStream.endFrameVarSizeWord();
					i = 30;
					return true;
				}
			}
			for (int i = 0; i < getClient().playerItems.length; i++) {
				if (getClient().playerItems[i] <= 0) {
					getClient().playerItems[i] = item + 1;
					if (amount < PlayerConstants.MAX_ITEM_AMOUNT && amount > -1) {
						getClient().playerItemsN[i] = amount;
					} else {
						getClient().playerItemsN[i] = PlayerConstants.MAX_ITEM_AMOUNT;
					}
					getClient().outStream.createFrameVarSizeWord(34);
					getClient().outStream.writeWord(3214);
					getClient().outStream.writeByte(i);
					getClient().outStream.writeWord(getClient().playerItems[i]);
					if (getClient().playerItemsN[i] > 254) {
						getClient().outStream.writeByte(255);
						getClient().outStream.writeDWord(getClient().playerItemsN[i]);
					} else {
						getClient().outStream.writeByte(getClient().playerItemsN[i]); // amount
					}
					getClient().outStream.endFrameVarSizeWord();
					getClient().flushOutStream();
					i = 30;
					return true;
				}
			}
			return false;
		} else {
			sendMessage(Language.NO_SPACE);
			return false;
		}
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Client getClient() {
		return client;
	}

}