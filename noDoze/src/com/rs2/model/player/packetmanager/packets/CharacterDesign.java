package com.rs2.model.player.packetmanager.packets;

/**
 * Player design screen         
 *
 * @author Ultimate
 *
 **/

import com.rs2.content.actions.ActionManager;
import com.rs2.model.player.Client;
import com.rs2.model.player.PlayerConstants;
import com.rs2.model.player.packetmanager.Packet;

public class CharacterDesign implements Packet {

	public void handlePacket(Client client, int packetType, int packetSize) {

		int sex = client.inStream.readSignedByte();

		int head = client.inStream.readSignedByte();
		int beard = client.inStream.readSignedByte();
		int body = client.inStream.readSignedByte();
		int arms = client.inStream.readSignedByte();
		int hands = client.inStream.readSignedByte();
		int legs = client.inStream.readSignedByte();
		int feet = client.inStream.readSignedByte();

		int hairColour = client.inStream.readSignedByte();
		int bodyColour = client.inStream.readSignedByte();
		int legColour = client.inStream.readSignedByte();
		int feetColour = client.inStream.readSignedByte();
		int skinColour = client.inStream.readSignedByte();

		client.playerLook[PlayerConstants.SEX] = sex;
		client.playerLook[PlayerConstants.HAIR_COLOUR] = hairColour;
		client.playerLook[PlayerConstants.BODY_COLOUR] = bodyColour;
		client.playerLook[PlayerConstants.LEG_COLOUR] = legColour;
		client.playerLook[PlayerConstants.FEET_COLOUR] = feetColour;
		client.playerLook[PlayerConstants.SKIN_COLOUR] = skinColour;

		client.playerLook[PlayerConstants.HEAD] = head;
		client.playerLook[PlayerConstants.BODY] = body;
		client.playerLook[PlayerConstants.ARMS] = arms;
		client.playerLook[PlayerConstants.HANDS] = hands;
		client.playerLook[PlayerConstants.LEGS] = legs;
		client.playerLook[PlayerConstants.FEET] = feet;
		client.playerLook[PlayerConstants.BEARD] = beard;

		client.appearanceSet = true;
		client.updateRequired = true;
		client.appearanceUpdateRequired = true;

		client.getActionSender().sendWindowsRemoval();

		ActionManager.destructActions(client.getUsername());
	}
}