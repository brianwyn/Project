package com.rs2.market;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;

import com.rs2.GameEngine;
import com.rs2.model.player.Client;
import com.rs2.mysql.Mysql;

/**
 * 
 * @author Steven
 *
 */
public class Market {

	static long getuniqueId(String text) throws IOException {
		final String DATE_FORMAT_NOW = "yyyyMMddHHmmss";
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT_NOW);
		text += format.format(cal.getTime());
		byte buffer[] = text.getBytes();
		ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
		CheckedInputStream cis = new CheckedInputStream(bais, new CRC32());
		byte readBuffer[] = new byte[5];
		while (cis.read(readBuffer) >= 0);
			return cis.getChecksum().getValue();
	}
	
	public static void AddItem(Client client, int itemId, int itemQuantity, int itemPrice)  throws IOException {
		if (itemId == 0 || itemQuantity == 0 || itemPrice == 0 ) {
			client.getActionSender().sendMessage("None of the data can be 0");
			return;
		} else if (itemId == 995) {
			client.getActionSender().sendMessage("You cannot sell coins.");
			return;
		} 
		
		String itemName;
		
	    try {
	    	itemName = GameEngine.getItemManager().getItemDefinition(itemId).getName();
	    } catch (Exception e) {
	    	client.getActionSender().sendMessage("There is no item with the id " + itemId);
	    	return;
	    }
		if(client.getActionAssistant().playerHasItem(itemId, itemQuantity)) {
			try {
				Connection con = Mysql.getConnection();
				Statement s = con.createStatement();
				client.getActionAssistant().deleteItem(itemId, itemQuantity);
				s.executeUpdate("INSERT INTO market (id, username, itemName, itemId, itemPrice, itemQuantity) VALUES ('" + getuniqueId(client.getUsername()) + "', '" + client.getUsername() + "', '" + itemName + "', '" + itemId + "', '" + itemPrice + "', '" + itemQuantity + "')");
				if(itemQuantity == 1) {
					client.getActionSender().sendMessage("Sucessfully added 1 " + itemName);
				} else {
					client.getActionSender().sendMessage("Sucessfully added " + itemQuantity + " " + itemName + "s");
				}
				s.close();
				con.close();
				Mysql.release();
			} catch (SQLException e) {
				client.getActionSender().sendInventoryItem(itemId, itemQuantity);
				client.getActionSender().sendMessage("Error adding to market please contact steven");
				e.printStackTrace();
			}
		} else {
			if(itemQuantity == 1) {
				client.getActionSender().sendMessage("You dont have 1 " + itemName);
			} else {
				client.getActionSender().sendMessage("You dont have " + itemQuantity + " " + itemName + "s");
			}
		}
	}
	
	public static void SearchMarket(Client client, String name) {
		for(int i = 0; i < 102; i++)
			client.getActionSender().sendQuest("", 13589+i);
		client.getActionSender().sendQuest("Market Search", 13589);
		client.getActionSender().sendQuest("Buy Example: ::buymarket id amount", 13591);
		client.getActionSender().sendQuest("Searched: " + name, 13593);
		client.getActionSender().sendQuest("Name - Id - Price - Amount", 13595);
		Connection con = Mysql.getConnection();
		Statement s;
		try {
			s = con.createStatement();
			s.executeQuery ("SELECT * FROM market WHERE itemName like '%" + name + "%' AND itemQuantity <> 0 ORDER BY itemPrice ASC LIMIT 93");
			ResultSet rs = s.getResultSet ();
			int i = 0;
		while(rs.next()) {
			client.getActionSender().sendQuest(rs.getString("itemName") + " - " + rs.getString("id") + " - " + rs.getInt("itemPrice") + " - " + rs.getInt("itemQuantity"), 13597+i);
			i++;
		}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		client.getActionSender().sendInterface(13585);
		client.getActionSender().getClient().flushOutStream();

	}
	
	public static void BuyMarket(Client client, int id, int itemQuantity) {
	if(client.getActionAssistant().freeSlots() >= 0 || client.getActionAssistant().freeBankSlots() >= 0) {
		Connection con = Mysql.getConnection();
		Statement s;
		try {
			int itemId, itemPrice;
			String itemName;
			s = con.createStatement();
			s.executeQuery ("SELECT * FROM market WHERE id = '" + id + "' AND itemQuantity > " + (itemQuantity-1));
			ResultSet rs = s.getResultSet ();
		if(rs.next()) {
			if(rs.getString("username") == client.getUsername()){
				rs.close();
				s.close();
				con.close();
				Mysql.release();
				client.getActionSender().sendMessage("You cannot buy your own items");
				return;
			}
			if(!client.getActionAssistant().playerHasItem(995, (rs.getInt("itemPrice") * itemQuantity))) {
				rs.close();
				s.close();
				con.close();
				Mysql.release();
				client.getActionSender().sendMessage("Sorry you do not have the right amount of coins for this purchase");
				return;
			}
			itemId = rs.getInt("itemID");
			itemName = rs.getString("itemName");
			itemPrice = rs.getInt("itemPrice");
		} else {
			client.getActionSender().sendMessage("Sorry this item has not been found or its been sold");
			return;
		}
		rs.close();
		if(client.getActionAssistant().freeSlots() >= itemQuantity || client.getActionAssistant().freeSlots() == itemQuantity ) {
			s.executeUpdate("UPDATE market set itemQuantity = itemQuantity - " + itemQuantity + ", itemTotal = itemTotal + " + itemQuantity + " WHERE id = '" + id + "'");
			client.getActionAssistant().deleteItem(995, (itemPrice * itemQuantity));
			client.getActionSender().sendMessage("You sucessfully bought " + itemQuantity + " " + itemName + "(s)");
			client.getActionSender().sendInventoryItem(itemId, itemQuantity);
		} else if (client.getActionAssistant().freeBankSlots() >= itemQuantity || client.getActionAssistant().freeBankSlots() == itemQuantity) {
			s.executeUpdate("UPDATE market set itemQuantity = itemQuantity - " + itemQuantity + ", itemTotal = itemTotal + " + itemQuantity + " WHERE id = '" + id + "'");
			client.getActionAssistant().deleteItem(995, (itemPrice * itemQuantity));
			client.getActionSender().sendMessage("You sucessfully bought " + itemQuantity + " " + itemName + "(s)");
			client.getActionSender().sendMessage("Added " + itemQuantity + " " + itemName + "(s) to your bank");
			for (int i = 0; i < client.getPlayerBankSize(); i++) {
				if (client.bankItems[i] == itemId + 1) {
					client.bankItemsN[i] += itemQuantity;
					break;
				} else if (client.bankItems[i] < 1) {
					client.bankItems[i] = itemId + 1;
					client.bankItemsN[i] = itemQuantity;	
					break;
				}
			}
			client.getActionSender().sendBankReset();
			client.getActionSender().sendItemReset(5064);
			}
		s.close();
		con.close();
		Mysql.release();
		} catch (SQLException e) {
			client.getActionSender().sendMessage("Error buying from market please contact steven");
			e.printStackTrace();
		}
	} else {
		client.getActionSender().sendMessage("You do not have anoth space in your inventory or bank.");
	}
	}
	
	public static void MyMarket(Client client) {
		for(int i = 0; i < 102; i++)
			client.getActionSender().sendQuest("", 13589+i);
		client.getActionSender().sendQuest("My Market", 13589);
		client.getActionSender().sendQuest("::collectmarket, ::removemarket id", 13591);
		client.getActionSender().sendQuest("Name - Id - Price - Amount - Sold", 13595);
		try {
			Connection con = Mysql.getConnection();
			Statement s;
			s = con.createStatement();
			s.executeQuery ("SELECT * FROM market WHERE username = '" + client.getUsername() + "' ORDER BY itemPrice ASC LIMIT 93");
			ResultSet rs = s.getResultSet ();
			int i = 0, MoneyMade = 0;
		while(rs.next()) {
			client.getActionSender().sendQuest(rs.getString("itemName") + " - " + rs.getString("id") + " - " + rs.getInt("itemPrice") + " - " + rs.getInt("itemQuantity") + " - " +  rs.getInt("itemTotal"), 13597+i);
			MoneyMade += (rs.getInt("itemPrice") * rs.getInt("itemTotal"));
			i++;
		}
		s.close();
		rs.close();
		con.close();
		Mysql.release();
		client.getActionSender().sendQuest("Cash Made: " + MoneyMade, 13593);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		client.getActionSender().sendInterface(13585);
		client.getActionSender().getClient().flushOutStream();
	}
	
	
	public static void CollectMarket(Client client) {
		if(client.getActionAssistant().freeSlots() >= 0) {
		try {
			Connection con = Mysql.getConnection();
			Statement s;
			s = con.createStatement();
			s.executeQuery ("SELECT * FROM market WHERE username = '" + client.getUsername() + "' AND itemTotal <> 0");
			ResultSet rs = s.getResultSet ();
			int MoneyMade = 0;
		while(rs.next()) {
			MoneyMade += (rs.getInt("itemPrice") * rs.getInt("itemTotal"));
		}
		rs.close();
		s.executeQuery("DELETE FROM market WHERE username = '" + client.getUsername() + "' AND itemQuantity = 0");
		s.executeQuery("UPDATE market SET itemTotal = 0 WHERE username = '" + client.getUsername() + "' AND itemTotal <> 0");
		s.close();
		con.close();
		Mysql.release();
		client.getActionSender().sendMessage("Cash Made: " + MoneyMade);
		client.getActionSender().sendInventoryItem(995, MoneyMade);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		} else {
			client.getActionSender().sendMessage("You need atleast 1 space free in your inventory.");
		}
	}
	
	public static void RemoveMarket(Client client, int id) {
		try {
			Connection con = Mysql.getConnection();
			Statement s;
			s = con.createStatement();
			s.executeQuery ("SELECT * FROM market WHERE username = '" + client.getUsername() + "' AND itemTotal <> 0");
			ResultSet rs = s.getResultSet ();
			int MoneyMade = 0, itemQuantity = 0, itemId = 0;
			String itemName = "";
		if (rs.next()) {
			MoneyMade += (rs.getInt("itemPrice") * rs.getInt("itemTotal"));
			itemId = rs.getInt("itemId");
			itemQuantity = rs.getInt("itemQuantity");
			itemName = rs.getString("itemName");
		}
		rs.close();
		s.executeQuery("DELETE FROM market WHERE id = '" + id + "'");
		if(itemQuantity >= 0 && itemId >= 0) {
			for (int i = 0; i < client.getPlayerBankSize(); i++) {
				if (client.bankItems[i] == itemId + 1) {
					client.bankItemsN[i] += itemQuantity;
					break;
				} else if (client.bankItems[i] < 1) {
					client.bankItems[i] = itemId + 1;
					client.bankItemsN[i] = itemQuantity;	
					break;
				}
			}
			client.getActionSender().sendBankReset();
			client.getActionSender().sendItemReset(5064);
			client.getActionSender().sendMessage(itemQuantity + " " + itemName + "(s) added to your bank");
		}
		if(MoneyMade >= 0) {
			for (int i = 0; i < client.getPlayerBankSize(); i++) {
				if (client.bankItems[i] == 995 + 1) {
					client.bankItemsN[i] += MoneyMade;
					break;
				} else if (client.bankItems[i] < 1) {
					client.bankItems[i] = 995 + 1;
					client.bankItemsN[i] = MoneyMade;	
					break;
				}
			}
			client.getActionSender().sendBankReset();
			client.getActionSender().sendItemReset(5064);
			client.getActionSender().sendMessage(MoneyMade + " Coins added to your bank");
		}
		s.close();
		con.close();
		Mysql.release();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	}
