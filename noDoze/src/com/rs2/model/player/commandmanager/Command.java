package com.rs2.model.player.commandmanager;

import com.rs2.model.player.Client;

/**
 * Command interface.
 * 
 * @author Graham
 */
public interface Command {

	public void execute(Client client, String command);

}
