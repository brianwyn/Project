package com.rs2.model.player.packetmanager.packets;

import com.rs2.model.player.Client;
import com.rs2.model.player.packetmanager.Packet;
import com.rs2.util.Misc;

/**
 * Chat packets
 * 
 * @author Graham
 */
public class Chat implements Packet {

	public static final int REGULAR_CHAT = 4, UPDATE_CHAT_OPTIONS = 95,
			FRIEND_ADD = 188, FRIEND_REMOVE = 215, IGNORE_ADD = 133,
			IGNORE_REMOVE = 74, PRIVATE_MESSAGE = 126;


	
	@Override
	public void handlePacket(Client client, int packetType, int packetSize) {
		switch (packetType) {
		case REGULAR_CHAT:
			if (client.isMuted()) {
				client.getActionSender().sendMessage("You are muted.");
				return;
			}
			client.MBTC = Misc.textUnpack(client.chatText, packetSize - 2);
			client.MBBC = Misc.textUnpack(client.chatText, packetSize - 2);
			client.MBHT = Misc.textUnpack(client.chatText, packetSize - 2);
			client.MBID = Misc.textUnpack(client.chatText, packetSize - 2);
			client.chatTextEffects = client.inStream.readUnsignedByteS();
			client.chatTextColor = client.inStream.readUnsignedByteS();
			client.chatTextSize = (byte) (packetSize - 2);
			client.inStream.readBytes_reverseA(client.chatText, client.chatTextSize, 0);
			client.chatTextUpdateRequired = true;
			break;

		case FRIEND_ADD:
			client.getFriendsHandler().addFriend(client.inStream.readQWord());
			break;
		case FRIEND_REMOVE:
			client.getFriendsHandler()
					.removeFriend(client.inStream.readQWord());
			break;
		case IGNORE_ADD:
			client.getFriendsHandler().addIgnore(client.inStream.readQWord());
			break;
		case IGNORE_REMOVE:
			client.getFriendsHandler()
					.removeIgnore(client.inStream.readQWord());
			break;
		case PRIVATE_MESSAGE:
			long to = client.inStream.readQWord();
			int chatTextSize = (byte) (packetSize - 8);
			byte[] chatText = new byte[256];
			client.inStream.readBytes(chatText, chatTextSize, 0);
			client.getFriendsHandler().sendMessage(to, chatText, chatTextSize);
			break;
		}
	}

}
