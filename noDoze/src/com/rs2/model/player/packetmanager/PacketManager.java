package com.rs2.model.player.packetmanager;

import com.rs2.model.player.Client;
import com.rs2.model.player.packetmanager.packets.ActionButtons;
import com.rs2.model.player.packetmanager.packets.Attack;
import com.rs2.model.player.packetmanager.packets.BankAll;
import com.rs2.model.player.packetmanager.packets.BankFive;
import com.rs2.model.player.packetmanager.packets.BankTen;
import com.rs2.model.player.packetmanager.packets.BankX;
import com.rs2.model.player.packetmanager.packets.CharacterDesign;
import com.rs2.model.player.packetmanager.packets.Chat;
import com.rs2.model.player.packetmanager.packets.ClickNPC;
import com.rs2.model.player.packetmanager.packets.Clicking;
import com.rs2.model.player.packetmanager.packets.CustomCommand;
import com.rs2.model.player.packetmanager.packets.DialogueAction;
import com.rs2.model.player.packetmanager.packets.DropItem;
import com.rs2.model.player.packetmanager.packets.FollowPlayer;
import com.rs2.model.player.packetmanager.packets.IdleLogout;
import com.rs2.model.player.packetmanager.packets.ItemOnItem;
import com.rs2.model.player.packetmanager.packets.ItemOnNpc;
import com.rs2.model.player.packetmanager.packets.ItemOnObject;
import com.rs2.model.player.packetmanager.packets.LoadingComplete;
import com.rs2.model.player.packetmanager.packets.MagicOnInventoryItem;
import com.rs2.model.player.packetmanager.packets.MagicOnPlayer;
import com.rs2.model.player.packetmanager.packets.MagicOnNPC;
import com.rs2.model.player.packetmanager.packets.MoveItems;
import com.rs2.model.player.packetmanager.packets.ObjectClick;
import com.rs2.model.player.packetmanager.packets.PickupItem;
import com.rs2.model.player.packetmanager.packets.Remove;
import com.rs2.model.player.packetmanager.packets.ItemOption;
import com.rs2.model.player.packetmanager.packets.TradeAction;
import com.rs2.model.player.packetmanager.packets.Unused;
import com.rs2.model.player.packetmanager.packets.UseItem;
import com.rs2.model.player.packetmanager.packets.Walk;
import com.rs2.model.player.packetmanager.packets.Wear;

/**
 * PacketsProcessor
 * 
 * @author Java'
 * @author Steven
 */
public class PacketManager {

	public PacketManager() {
	}

	public static final int MAX_PACKETS = 256;
	public static Packet[] packet = new Packet[MAX_PACKETS];

	public static void loadAllPackets() {
		int unhandledPackets = 0;
		       /*
		        * Packets
		        */
				packet[237] = new MagicOnInventoryItem();
				packet[153] = new FollowPlayer();
				packet[202] = new IdleLogout();
				packet[101] = new CharacterDesign();
				packet[121] = new LoadingComplete();
				packet[210] = new LoadingComplete();
				packet[122] = new UseItem();
				packet[248] = new Walk();
				packet[164] = new Walk();
				packet[98] = new Walk();
				packet[57] = new ItemOnNpc();
				packet[4] = new Chat();
				packet[95] = new Chat();
				packet[188] = new Chat();
				packet[215] = new Chat();
				packet[133] = new Chat();
				packet[74] = new Chat();
				packet[126] = new Chat();
				packet[132] = new ObjectClick();
				packet[252] = new ObjectClick();
				packet[70] = new ObjectClick();
				packet[236] = new PickupItem();
				packet[73] = new TradeAction();
				packet[139] = new TradeAction();
				packet[103] = new CustomCommand();
				packet[214] = new MoveItems();
				packet[41] = new Wear();
				packet[145] = new Remove();
				packet[117] = new BankFive();
				packet[43] = new BankTen();
				packet[129] = new BankAll();
				packet[135] = new BankX();
				packet[208] = new BankX();
				packet[87] = new DropItem();
				packet[185] = new ActionButtons();
				packet[130] = new Clicking();
				packet[155] = new ClickNPC();
				packet[230] = new ClickNPC();
				packet[17] = new ClickNPC();
				packet[21] = new ClickNPC();
				packet[40] = new DialogueAction();
				packet[53] = new ItemOnItem();//
				packet[128] = new Attack();
				packet[72] = new Attack();
				packet[249] = new MagicOnPlayer();
				packet[131] = new MagicOnNPC();
				packet[192] = new ItemOnObject();
				packet[75] = new ItemOption();
				
				/*
				 * Loaded Packets Count
				 */
				for(int i = 0; i < MAX_PACKETS; i++) {
					if(packet[i] == null)
						packet[i] = new Unused();
				}
				
		int loadedPackets = MAX_PACKETS - unhandledPackets;
		System.out.println("Loaded " + loadedPackets + " packets.");
	}

	public static void handlePacket(Client c, int packetType, int packetSize) {
		try {
			//c.getActionSender().sendMessage(packetType+"");
			if(packet[packetType] != null && packetType <= MAX_PACKETS && packetType >= 0) {
			packet[packetType].handlePacket(c, packetType, packetSize);
			} else {
				System.out.println("Unhandled Packet: " + packetType);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}