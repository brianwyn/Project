package com.rs2.model.player.commandmanager;

import java.util.HashMap;
import java.util.Map;

import com.rs2.model.player.Client;
import com.rs2.model.player.commandmanager.commands.AddMarket;
import com.rs2.model.player.commandmanager.commands.AllToMe;
import com.rs2.model.player.commandmanager.commands.Ban;
import com.rs2.model.player.commandmanager.commands.Bank;
import com.rs2.model.player.commandmanager.commands.BuyMarket;
import com.rs2.model.player.commandmanager.commands.CastleWars;
import com.rs2.model.player.commandmanager.commands.ChangePassword;
import com.rs2.model.player.commandmanager.commands.Char;
import com.rs2.model.player.commandmanager.commands.IPBan;
import com.rs2.model.player.commandmanager.commands.IpMute;
import com.rs2.model.player.commandmanager.commands.Jail;
import com.rs2.model.player.commandmanager.commands.Kick;
import com.rs2.model.player.commandmanager.commands.Level;
import com.rs2.model.player.commandmanager.commands.Master;
import com.rs2.model.player.commandmanager.commands.Mute;
import com.rs2.model.player.commandmanager.commands.MyMarket;
import com.rs2.model.player.commandmanager.commands.MyPosition;
import com.rs2.model.player.commandmanager.commands.Pickup;
import com.rs2.model.player.commandmanager.commands.ResetNPC;
import com.rs2.model.player.commandmanager.commands.ResetNPCDrops;
import com.rs2.model.player.commandmanager.commands.Runes;
import com.rs2.model.player.commandmanager.commands.SaveAll;
import com.rs2.model.player.commandmanager.commands.SearchMarket;
import com.rs2.model.player.commandmanager.commands.SetEmote;
import com.rs2.model.player.commandmanager.commands.SetLevel;
import com.rs2.model.player.commandmanager.commands.SetNPC;
import com.rs2.model.player.commandmanager.commands.Special;
import com.rs2.model.player.commandmanager.commands.Switch;
import com.rs2.model.player.commandmanager.commands.TeleportTo;
import com.rs2.model.player.commandmanager.commands.TeleportToMe;
import com.rs2.model.player.commandmanager.commands.TempNPC;
import com.rs2.model.player.commandmanager.commands.UnIpMute;
import com.rs2.model.player.commandmanager.commands.UnJail;
import com.rs2.model.player.commandmanager.commands.Unban;
import com.rs2.model.player.commandmanager.commands.Unmute;
import com.rs2.model.player.commandmanager.commands.Yell;
import com.rs2.model.player.commandmanager.commands.Gfx;
import com.rs2.model.player.commandmanager.commands.ResetAll;
import com.rs2.model.player.commandmanager.commands.Interface;
import com.rs2.model.player.commandmanager.commands.MultiSwitch;
import com.rs2.model.player.commandmanager.commands.TeleTo;
import com.rs2.model.player.commandmanager.commands.AddNPC;
/**
 * Manages all commands.
 * 
 * @author Graham
 */
public class CommandManager {

	private static Map<String, Command> commandMap = new HashMap<String, Command>();

	public static void loadAllCommands() {
		commandMap.put("runes", new Runes());
		commandMap.put("alltome", new AllToMe());
		commandMap.put("jail", new Jail());
		commandMap.put("unjail", new UnJail());
		commandMap.put("resetnpcdrops", new ResetNPCDrops());
		commandMap.put("setlevel", new SetLevel());
		commandMap.put("special", new Special());
		commandMap.put("spec", new Special());
		commandMap.put("setnpc", new SetNPC());
		commandMap.put("npcreset", new ResetNPC());
		commandMap.put("resetnpc", new ResetNPC());
		commandMap.put("tempnpc", new TempNPC());
		commandMap.put("yell", new Yell());
		commandMap.put("kick", new Kick());
		commandMap.put("mute", new Mute());
		commandMap.put("ipmute", new IpMute());
		commandMap.put("unipmute", new UnIpMute());
		commandMap.put("unmute", new Unmute());
		commandMap.put("ban", new Ban());
		commandMap.put("bank", new Bank());
		commandMap.put("char", new Char());
		commandMap.put("unban", new Unban());
		commandMap.put("ipban", new IPBan());
		commandMap.put("teleto", new TeleportTo());
		commandMap.put("teletome", new TeleportToMe());
		commandMap.put("saveall", new SaveAll());
		commandMap.put("pickup", new Pickup());
		commandMap.put("item", new Pickup());
		commandMap.put("emote", new SetEmote());
		commandMap.put("castlewars", new CastleWars());
		commandMap.put("pass", new ChangePassword());
		commandMap.put("mypos", new MyPosition());
		commandMap.put("level", new Level());
		commandMap.put("switch", new Switch());
		commandMap.put("gfx", new Gfx());
		commandMap.put("master", new Master());
		commandMap.put("resetall", new ResetAll());
		commandMap.put("interface", new Interface());
		commandMap.put("switchmulti", new MultiSwitch());
		commandMap.put("tele", new TeleTo());
		commandMap.put("addnpc", new AddNPC());
		commandMap.put("addmarket", new AddMarket());
		commandMap.put("searchmarket", new SearchMarket());
		commandMap.put("buymarket", new BuyMarket());
		commandMap.put("mymarket", new MyMarket());
		System.out.println("Loaded " + commandMap.size() + " custom commands.");
	}

	public static void execute(Client client, String command) {
		String name = "";
		if (command.indexOf(' ') > -1) {
			name = command.substring(0, command.indexOf(' '));
		} else {
			name = command;
		}
		name = name.toLowerCase();
		if (commandMap.get(name) != null) {
			commandMap.get(name).execute(client, command);
		} else if (name.length() == 0) {
			client.getActionSender().sendMessage("The command does not exist.");
		} else {
			client.getActionSender().sendMessage(
					"The command " + name + " does not exist.");
		}
	}

}
