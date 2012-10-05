package com.rs2.model.player.packetmanager.packets;

import com.rs2.GameEngine;
import com.rs2.content.WalkToNPC;
import com.rs2.content.actions.ActionManager;
import com.rs2.content.controllers.Animation;
import com.rs2.model.Entity;
import com.rs2.model.npc.NPC;
import com.rs2.model.player.Client;
import com.rs2.model.player.packetmanager.Packet;

/**
 * Click NPC packet.
 * 
 * @author Killamess
 */
public class ClickNPC implements Packet {
	//155 17 14
	public static final int FIRST_CLICK = 155, SECOND_CLICK = 17, THIRD_CLICK = 21;

	@Override
	public void handlePacket(Client client, int packetType, int packetSize) {
		
		Entity npc = null;
		
		int clickType = 0;
		
		switch (packetType) {
		
		case FIRST_CLICK:
			npc = (NPC) GameEngine.getNPCManager().getNPC(client.inStream.readSignedWordBigEndian());
			clickType = 1;
			break;
			
		case SECOND_CLICK:
			npc = (NPC) GameEngine.getNPCManager().getNPC(client.inStream.readUnsignedWordBigEndianA());
			clickType = 2;
			break;
		
		case THIRD_CLICK:
			npc = (NPC) GameEngine.getNPCManager().getNPC(client.inStream.readSignedWord());//client.inStream.readUnsignedWordA()
			clickType = 3;
			break;
			
		default:
			break;
		
		}	
		ActionManager.destructActions(client.getUsername());
		
		if (npc == null) 
			return;
		
		Animation.face(client, npc);
		WalkToNPC.setDestination(client, ((NPC)npc), WalkToNPC.getNpcWalkType(((NPC)npc), clickType));
	} 
	
}
