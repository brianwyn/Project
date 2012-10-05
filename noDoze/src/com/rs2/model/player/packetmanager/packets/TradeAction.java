package com.rs2.model.player.packetmanager.packets;

import com.rs2.Constants;
import com.rs2.content.actions.ActionManager;
import com.rs2.model.player.Client;
import com.rs2.model.player.packetmanager.Packet;
import com.rs2.world.PlayerManager;

/**
 * Trade actions
 * 
 * @author Graham
 */
public class TradeAction implements Packet {

	public static final int REQUEST = 73, ANSWER = 139;

	@Override
	public void handlePacket(Client client, int packetType, int packetSize) {
		ActionManager.destructActions(client.getUsername());
		if (packetType == REQUEST) {
			int trade = client.inStream.readSignedWordBigEndian();
			if (trade < 0 || trade >= Constants.MAX_PLAYERS)
				return;
			if (PlayerManager.getSingleton().getPlayers()[trade] != null) {
				Client c = (Client) PlayerManager.getSingleton().getPlayers()[trade];
				client.getTradeHandler().requestTrade(c);
			}
			client.println_debug("Trade Request to: " + trade);
		} else if (packetType == ANSWER) {
			int trade = client.inStream.readSignedWordBigEndian();
			if (trade < 0 || trade >= Constants.MAX_PLAYERS)
				return;
			if (PlayerManager.getSingleton().getPlayers()[trade] != null) {
				Client c = (Client) PlayerManager.getSingleton().getPlayers()[trade];
				client.getTradeHandler().answerTrade(c);
			}
			client.println_debug("Trade Answer to: " + trade);
		}
	}

}
