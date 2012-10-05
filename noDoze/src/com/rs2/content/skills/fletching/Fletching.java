package com.rs2.content.skills.fletching;

import com.rs2.content.actions.Action;
import com.rs2.content.actions.ActionManager;
import com.rs2.content.actions.Task;
import com.rs2.content.controllers.Animation;
import com.rs2.model.player.Client;
import com.rs2.model.player.PlayerConstants;

public class Fletching implements Task {

	public static int getLogId(Client client) {
		if (client.getExtraData().get("logId") == null) {
			return -1;
		}
		int log = (Integer) client.getExtraData().get("logId");
		return log;
	}

	public static void setLogId(Client client, int id) {
		client.getExtraData().put("logId", (Integer) id);
	}

	public static int getIntArray(int[] array1, int[] array2, int bow) {
		int a = 0;
		for (int object : array1) {
			if (object == bow) {
				return array2[a];
			}
			a++;
		}
		return -1;
	}

	public static String getStringArray(int[] array1, String[] array2, int bow) {
		int a = 0;
		for (int object : array1) {
			if (object == bow) {
				return array2[a];
			}
			a++;
		}
		return "";
	}

	public static int contains(int[] array, int value) {
		for (int i : array) {
			if (i == value)
				return value;
		}
		return 0;
	}

	public static boolean isFletchable(Client client, int useItem, int usedItem) {
		if (useItem == 946 && usedItem == contains(client.LOGS, usedItem) || useItem == contains(client.LOGS, useItem) && usedItem == 946) {
			return chooseItem(client, (useItem == contains(client.LOGS, useItem) ? useItem : usedItem));
		} else if (useItem == 1777 && (usedItem == contains(client.UNSTRUNG_BOWS, usedItem) || useItem == contains(client.UNSTRUNG_BOWS, useItem))){
			return stringBow(client, (useItem == contains(client.UNSTRUNG_BOWS, useItem) ? useItem : usedItem));
		} else if (useItem == 52 && usedItem == 314 || useItem == 314 && usedItem == 52) {
			return createHeadlessArrows(client, 52);
		} else if (useItem == 53 && usedItem == contains(client.ARROW_HEADS, usedItem) || useItem == contains(client.ARROW_HEADS, useItem) && usedItem == 53) {
			return createArrows(client, (useItem == contains(client.ARROW_HEADS, useItem) ? useItem : usedItem));
		} else if (useItem == 314 && usedItem == contains(client.DART_TIPS, usedItem) || useItem == contains(client.DART_TIPS, useItem) && usedItem == 314) {
			return createDarts(client, (useItem == contains(client.DART_TIPS, useItem) ? useItem : usedItem));
		} else
			return false;
	}

	public static boolean createHeadlessArrows(Client client, int item) {
		if (client.isBusy()) {
			return true;
		}
		int amount = client.getActionAssistant().getItemAmount(item);
		int otherAmount = client.getActionAssistant().getItemAmount(314);

		if (amount < 15 || otherAmount < 15) {
			return true;
		}
		if (client.getActionAssistant().freeSlots() >= 1) {
			client.getActionAssistant().deleteItem(314, amount > 15 ? 15 : amount);
			client.getActionAssistant().deleteItem(item, amount > 15 ? 15 : amount);
			client.getActionSender().sendInventoryItem(53, amount > 15 ? 15 : amount);
			client.getActionAssistant().addSkillXP(100, PlayerConstants.FLETCHING);
			client.getActionSender().sendMessage("You make some headless arrows.");
		} else {
			client.getActionSender().sendMessage("You have no space in your inventory");
		}
		return true;
	}

	public static boolean createDarts(Client client, int item) {

		if (client.isBusy()) {
			return true;
		}
		int amount = client.getActionAssistant().getItemAmount(item);
		int otherAmount = client.getActionAssistant().getItemAmount(314);

		if (amount < 15 || otherAmount < 15) {
			return true;
		}
		if (client.getActionAssistant().freeSlots() >= 1) {
			if (client.playerLevel[PlayerConstants.FLETCHING] >= getIntArray(
					client.DART_TIPS, client.DART_LEVELS, item)) {
				client.getActionAssistant().deleteItem(314,
						amount > 15 ? 15 : amount);
				client.getActionAssistant().deleteItem(item,
						amount > 15 ? 15 : amount);
				client.getActionSender().sendInventoryItem(
						getIntArray(client.DART_TIPS, client.DARTS, item),
						amount > 15 ? 15 : amount);
				client.getActionAssistant().addSkillXP(
						getIntArray(client.DART_TIPS, client.DART_EXPERIENCE, item),
						PlayerConstants.FLETCHING);
				client.getActionSender().sendMessage("You make some darts.");
			} else {
				client.getActionSender().sendMessage("You need a fletching level of" + " " + getIntArray(client.DART_TIPS, client.DART_LEVELS, item) + " to make these darts.");
			}
		} else {
			client.getActionSender().sendMessage(
					"You have no space in your inventory");
		}
		return true;
	}

	public static boolean createArrows(Client client, int item) {

		if (client.isBusy()) {
			return true;
		}

		int amount = client.getActionAssistant().getItemAmount(item);

		int otherAmount = client.getActionAssistant().getItemAmount(53);

		if (amount < 15 || otherAmount < 15) {
			return true;
		}
		if (client.getActionAssistant().freeSlots() >= 1) {
			if (client.playerLevel[PlayerConstants.FLETCHING] >= getIntArray(
					client.ARROW_HEADS, client.ARROW_LEVELS, item)) {
				client.getActionAssistant().deleteItem(53,
						amount > 15 ? 15 : amount);
				client.getActionAssistant().deleteItem(item,
						amount > 15 ? 15 : amount);
				client.getActionSender().sendInventoryItem(
						getIntArray(client.ARROW_HEADS, client.ARROWSX, item), 15);
				client.getActionAssistant().addSkillXP(
						getIntArray(client.ARROW_HEADS, client.ARROW_EXPERIENCE, item),
						PlayerConstants.FLETCHING);
				client.getActionSender()
						.sendMessage("You make some arrows.");
			} else {
				client.getActionSender().sendMessage(
						"You need a fletching level of" + " "
								+ getIntArray(client.ARROW_HEADS, client.ARROW_LEVELS, item)
								+ " to make these arrows.");
			}
		} else {
			client.getActionSender().sendMessage(
					"You have no space in your inventory");
		}
		return true;
	}

	public static boolean stringBow(Client client, int bow) {

		if (client.isBusy()) {
			return true;
		}

		if (client.playerLevel[PlayerConstants.FLETCHING] >= getIntArray(client.UNSTRUNG_BOWS, client.FLETCHING_LEVELS, bow)) {
			client.getActionAssistant().deleteItem(bow, 1);
			client.getActionAssistant().deleteItem(1777, 1);
			client.getActionAssistant().addSkillXP(5 ,PlayerConstants.FLETCHING);
			client.getActionSender().sendInventoryItem(getIntArray(client.UNSTRUNG_BOWS, client.STRUNG_BOWS, bow), 1);
			client.getActionSender().sendMessage("You attach the bowstring to the bow.");
		} else {
			client.getActionSender().sendMessage(
					"You need a fletching level of" + " "
							+ getIntArray(client.UNSTRUNG_BOWS, client.FLETCHING_LEVELS, bow)
							+ " to string that bow.");
		}
		return true;
	}

	public static boolean chooseItem(Client client, int log) {
		if (client.isBusy()) 
			return true;

		client.getActionSender().sendWindowsRemoval();
		client.getActionSender().sendFrame164(8880);
		client.getActionSender().sendFrame246(8883, 200, getIntArray(client.LOGS, client.LEFT_ITEM, log));
		client.getActionSender().sendFrame246(8884, 200, (log == 1511 ? 52 : -1));
		client.getActionSender().sendFrame246(8885, 200, getIntArray(client.LOGS, client.RIGHT_ITEM, log));
		client.getActionSender().sendQuest(getStringArray(client.LOGS, client.LEFT_ITEM_NAME, log), 8897);
		client.getActionSender().sendQuest(log == 1511 ? "Arrow Shaft" : "", 8893);
		client.getActionSender().sendQuest( getStringArray(client.LOGS, client.RIGHT_ITEM_NAME, log), 8889);
		setLogId(client, log);
		
		return true;
	}

	public static void startFletching(Client client, int amount, String length) {
		if (client.isBusy()) {
			return;
		}
		client.setBusy(true);
		
		client.amountLeft = amount;
		client.log2 = getLogId(client);
		client.unstrungBow = 0;
		client.FLETCHING_DELAY = 4;
		ActionManager.addNewRequest(client, 17, 4);
	}

	@Override
	public void execute(Action currentAction) {
		currentAction.setActionType(Action.type.LOOPING);
	}

	@Override
	public void loop(Action currentAction) {
		if (currentAction.getCurrentTick() > 0) {
			currentAction.setCurrentTick(currentAction.getCurrentTick() - 1);
			return;
		}

		Client client = currentAction.getClient();
			
		if (client == null) {
			stop(currentAction);
			return;
		}
		if (client.amountLeft == 0 || client.log2 == -1) {
			stop(currentAction);
			return;
		}

		if (client.length == "shortbow") {
			client.unstrungBow = getIntArray(client.LOGS, client.LEFT_ITEM, client.log2);
		} else if (client.length == "longbow") {
			client.unstrungBow = getIntArray(client.LOGS, client.RIGHT_ITEM, client.log2);
		} else {
			client.unstrungBow = 52;
		}

		if (client.getActionAssistant().freeSlots() >= 0) {
			if (client.getActionAssistant().getItemAmount(client.log2) > 0) {
				if (client.playerLevel[PlayerConstants.FLETCHING] >= getIntArray(client.UNSTRUNG_BOWS, client.FLETCHING_LEVELS, client.unstrungBow)) {
					Animation.addNewRequest(client, 1248, 1);
					client.getActionAssistant().deleteItem(client.log2, 1);
					client.getActionSender().sendInventoryItem(client.unstrungBow == 52 ? 52 : client.unstrungBow, client.unstrungBow == 52 ? 15 : 1);
					client.getActionAssistant().addSkillXP(client.unstrungBow == 52 ? 5 : getIntArray(client.UNSTRUNG_BOWS, client.EXPERIENCE, client.unstrungBow), PlayerConstants.FLETCHING);
					client.getActionSender().sendMessage("You fletch the bow.");
					client.amountLeft--;
				} else {
					client.getActionSender().sendMessage("You need a fletching level of " + getIntArray(client.UNSTRUNG_BOWS, client.FLETCHING_LEVELS, client.unstrungBow) + " to fletch that bow.");
					stop(currentAction);
					return;
				}
			} else {
				client.getActionSender().sendMessage("You don't have the item to fletch");
				stop(currentAction);
				return;
			}
		} else {
			client.getActionSender().sendMessage("You have no space in your inventory");
			stop(currentAction);
			return;
		}
		currentAction.setCurrentTick(client.FLETCHING_DELAY);
	}

	@Override
	public void stop(Action currentAction) {
		Client client = currentAction.getClient();
		
		if (client != null) {
			client.getActionSender().sendAnimationReset();
			client.setBusy(false);
			client.getExtraData().remove("logId");
		}
		
	}

}
