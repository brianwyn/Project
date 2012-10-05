package com.rs2.model.player.packetmanager.packets;

import com.rs2.content.actions.ActionManager;
import com.rs2.content.objects.ObjectController;
import com.rs2.content.objects.ObjectStorage;
import com.rs2.content.skills.mining.Mining;
import com.rs2.model.combat.CombatEngine;
import com.rs2.model.player.Client;
import com.rs2.model.player.packetmanager.Packet;

/**
 * Object clicking
 *
 * @author killamess 
 */

public class ObjectClick implements Packet {

	public static final int FIRST_CLICK = 132, SECOND_CLICK = 252;

	public void handlePacket(Client client, int packetType, int packetSize) {
		int objectY = 0;
		int objectID = 0;
		int objectX = 0;

		switch (packetType) {

		case FIRST_CLICK:
			objectX = client.inStream.readSignedWordBigEndianA();
			objectID = client.inStream.readUnsignedWord();
			objectY = client.inStream.readUnsignedWordA();
			if (objectID == 11666) {
				break;
			}
			ActionManager.destructActions(client.getUsername());
			if (client.getFreezeDelay() > 0) {
	    		client.getActionSender().sendMessage("A magical force holds you from moving!");
	    		return;
	    	}
			CombatEngine.resetAttack(client, false);
			ObjectController.run(client, ObjectStorage.compress(objectID, objectX, objectY));
			break;

		case SECOND_CLICK:
			objectID = client.inStream.readUnsignedWordBigEndianA();
			objectY = client.inStream.readSignedWordBigEndian();
			objectX = client.inStream.readUnsignedWordA();
			if (objectID == 11666) {
				break;
			}
			ActionManager.destructActions(client.getUsername());
			if (client.getFreezeDelay() > 0) {
	    		client.getActionSender().sendMessage("A magical force holds you from moving!");
	    		return;
	    	}
			CombatEngine.resetAttack(client, false);
			int c = 0;
			for (int i : Mining.RockID) {
				if (i == objectID) {
					client.getActionSender().sendMessage("This rock contains "+Mining.getName[c]+".");
					return;
				}
				c++;
			}
			ObjectController.run(client, ObjectStorage.compress(objectID, objectX, objectY));
			break;
		}
	}
	
	

}