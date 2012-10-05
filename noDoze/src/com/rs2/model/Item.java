package com.rs2.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import com.rs2.model.player.PlayerConstants;

/**
 * A collection of item methods.
 * 
 * @author Daiki
 * @author Graham
 */
public class Item {

	private int id;
	private int amount;

	public int getId() {
		return id;
	}

	public int getAmount() {
		return amount;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public Item(int id, int amount) {
		this.id = id;
		this.amount = amount;
	}

	// Few item types for equipping
	public static int capes[] = { 9948, 9949, 10499, 10498, 9747,9748,9750,9751,9753,9754,9756,9757,9759,9760,9762,9763,9765,9766,9768,9769,9771,9772,9774,9775,9777,9778,9780,9781,9783,9784,9786,9787,9789,9790,9792,9793,9795,9796,9798,9799,9801,9802,9804,9805,9807,9808,9810,9811,9813
		,9747,3781, 3783, 3785, 3787, 3789, 3777, 3779,
			3759, 3761, 3763, 3765, 6111, 6570, 6568, 1007, 1019, 1021, 1023,
			1027, 1029, 1031, 1052, 2412, 2413, 2414, 4304, 4315, 4317, 4319,
			4321, 4323, 4325, 4327, 4329, 4331, 4333, 4335, 4337, 4339, 4341,
			4343, 4345, 4347, 4349, 4351, 4353, 4355, 4357, 4359, 4361, 4363,
			4365, 4367, 4369, 4371, 4373, 4375, 4377, 4379, 4381, 4383, 4385,
			4387, 4389, 4391, 4393, 4395, 4397, 4399, 4401, 4403, 4405, 4407,
			4409, 4411, 4413, 4514, 4516, 6070, 6568, 6570 };

	public static int boots[] = { 11728, 11732,7596, 6619, 7159, 7991, 6666, 6061, 6106, 88,
			89, 626, 628, 630, 632, 634, 1061, 1837, 1846, 2577, 2579, 2894,
			2904, 2914, 2924, 2934, 3061, 3105, 3107, 3791, 4097, 4107, 4117,
			4119, 4121, 4123, 4125, 4127, 4129, 4131, 4310, 5064, 5345, 5557,
			6069, 6106, 6143, 6145, 6147, 6328, 6920, 6349, 6357, 3393 };

	public static int gloves[] = { 10336, 8842, 7595, 6629, 2491, 1065, 2487, 2489, 3060,
			1495, 775, 777, 778, 6708, 1059, 1063, 1065, 1580, 2487, 2489,
			2491, 2902, 2912, 2922, 2932, 2942, 3060, 3799, 4095, 4105, 4115,
			4308, 5556, 6068, 6110, 6149, 6151, 6153, 6922, 7454, 7455, 7456,
			7457, 7458, 7459, 7460, 7461, 7462, 6330, 3391 };

	public static int shields[] = { 10352, 8850, 8849, 8848, 8847, 8846, 8845, 8844,
			11284, 7342, 7348, 7354, 7360, 7334, 7340, 7347, 7352, 7358, 7356, 
			7350, 7344, 7332, 7338, 7336, 7360, 1171, 1173, 1175, 1177, 1179, 
			1181, 1183, 1185, 1187, 1189, 1191, 1193, 1195,
			1197, 1199, 1201, 1540, 2589, 2597, 2603, 2611, 2621, 2629, 2659,
			2667, 2675, 2890, 3122, 3488, 3758, 3839, 3840, 3841, 3842, 3843,
			3844, 4072, 4156, 4224, 4225, 4226, 4227, 4228, 4229, 4230, 4231,
			4232, 4233, 4234, 4302, 4507, 4512, 6215, 6217, 6219, 6221, 6223,
			6225, 6227, 6229, 6231, 6233, 6235, 6237, 6239, 6241, 6243, 6245,
			6247, 6249, 6251, 6253, 6255, 6257, 6259, 6261, 6263, 6265, 6267,
			6269, 6271, 6273, 6275, 6277, 6279, 6524, 6889 };
	//melee, 11664, mage 11663
	
	public static int hats[] = { 10547,10548,10549,10550,10334, 10342, 10350, 1038,11665, 11664, 11663, 9814,9749,9752,9755,9758,9761,9764,9767,9770,
			9773,9776,9779,9782,9785,9788,9791,9794,9797,9800,9803,9806,9809,
			9812,11718, 10828,4041, 4042, 4502, 7319, 7321, 7323, 7325,
			7327, 1167, 8074, 4168, 1169, 6665, 6665, 7321, 6886, 6547, 6548,
			2645, 2647, 2649, 4856, 4857, 4858, 4859, 4880, 4881, 4882, 4883,
			4904, 4905, 4906, 4907, 4928, 4929, 4930, 4931, 4952, 4953, 4954,
			4955, 4976, 4977, 4978, 4979, 4732, 4753, 4611, 6188, 6182, 4511,
			4056, 4071, 4724, 2639, 2641, 2643, 2665, 6109, 5525, 5527, 5529,
			5531, 5533, 5535, 5537, 5539, 5541, 5543, 5545, 5547, 5549, 5551,
			74, 579, 656, 658, 660, 662, 664, 740, 1017, 1040, 1042,
			1044, 1046, 1048, 1050, 1053, 1055, 1057, 1137, 1139, 1141, 1143,
			1145, 1147, 1149, 1151, 1153, 1155, 1157, 1159, 1161, 1163, 1165,
			1506, 1949, 2422, 2581, 2587, 2595, 2605, 2613, 2619, 2627, 2631,
			2633, 2635, 2637, 2651, 2657, 2673, 2900, 2910, 2920, 2930, 2940,
			2978, 2979, 2980, 2981, 2982, 2983, 2984, 2985, 2986, 2987, 2988,
			2989, 2990, 2991, 2992, 2993, 2994, 2995, 3057, 3385, 3486, 3748,
			3749, 3751, 3753, 3797, 4071, 4089, 3755, 4099, 4109, 4164, 4302,
			4506, 4511, 4513, 4515, 4551, 4567, 4708, 4716, 4724, 4745, 4753,
			4856, 4857, 4858, 4859, 4880, 4881, 4882, 4883, 4904, 4905, 4906,
			4907, 4952, 4953, 4954, 4955, 4976, 4977, 4978, 4979, 5013, 5014,
			5554, 5574, 6109, 6128, 6131, 6137, 6182, 6188, 6335, 6337, 6339,
			6345, 6355, 6365, 6375, 6382, 6392, 6400, 6918, 6656, 2581, 7539,
			7394, 7396, 7534, 5574, 6885, 6858, 6860, 6862, 6856, 6326, 6128,
			6137, 7400, 7323, 7325, 7327, 7003 };

	public static int amulets[] = { 10344, 11128,1654, 1656, 1658, 1660, 1662, 1664, 8081,
			8033, 7968, 6585, 86, 87, 295, 421, 552, 589, 1478, 1692, 1694,
			1696, 1698, 1700, 1702, 1704, 1706, 1708, 1710, 1712, 1725, 1727,
			1729, 1731, 4021, 4081, 4250, 4677, 6040, 6041, 6208, 1718, 1722,
			6859, 6863, 6857 };

	public static int arrows[] = { 8052, 8065, 7919, 7906, 78, 598, 877, 878,
			879, 880, 881, 882, 883, 884, 885, 886, 887, 888, 889, 890, 891,
			892, 893, 942, 2532, 2533, 2534, 2535, 2536, 2537, 2538, 2539,
			2540, 2541, 2866, 4160, 4172, 4173, 4174, 4175, 4740, 5616, 5617,
			5618, 5619, 5620, 5621, 5622, 5623, 5624, 5625, 5626, 5627, 6061,
			606, 9241, 9242, 9243, 9244, 9245, 11212 };

	public static int rings[] = { 773, 1635, 1637, 1639, 1641, 1643, 1645,
			2550, 2552, 2554, 2556, 2558, 2560, 2562, 2564, 2566, 2568, 2570,
			2572, 4202, 4657, 6465, 6737, 6731, 6735, 6735, 6583, 6733 };

	public static int body[] = {  581,10330, 10338, 10348, 8839, 10551, 11724, 11720, 7362, 7364, 636, 638, 640, 642, 644, 426,
			1005, 1757, 7592, 6617, 7376, 544, 7372, 7370, 577, 3793, 3775,
			3773, 3771, 3769, 3767, 6139, 1135, 2499, 2501, 1035, 540, 5553,
			4757, 1833, 6388, 6384, 4111, 4101, 4091, 6186, 6184, 6180, 3058,
			4509, 4504, 4069, 4728, 4736, 4712, 6107, 2661, 3140, 1101, 1103,
			1105, 1107, 1109, 1111, 1113, 1115, 1117, 1119, 1121, 1123, 1125,
			1127, 1129, 1131, 1133, 2583, 2591, 2599, 2607, 2615, 2623, 2653,
			2669, 3481, 4712, 4720, 4728, 4749, 4892, 4893, 4894, 4895, 4916,
			4917, 4918, 4919, 4964, 4965, 4966, 4967, 6107, 6133, 6322, 6322,
			6129, 75, 6916, 6916, 4111, 6654, 6654, 75, 7399, 7390, 7374, 5575,
			2503, 6341, 6351, 3387, 5030, 5032, 5034, 5030, 5032, 5034, 7392,
			546 };

	public static int legs[] = { 10332, 10340, 10346, 8840, 11722, 11726,7378, 7380, 7382, 7368, 7366, 7388, 646, 648,
			650, 652, 654, 428, 1097, 1095, 7593, 6625, 8020, 8015, 7384, 6141,
			1835, 538, 1033, 5555, 4759, 6386, 6390, 2497, 2495, 2493, 1099,
			4113, 4103, 4093, 6924, 6187, 6185, 6181, 3059, 4510, 4505, 4070,
			6108, 538, 542, 548, 1011, 1013, 1015, 1067, 1069, 1071, 1073,
			1075, 1077, 1079, 1081, 1083, 1085, 1087, 1089, 1091, 1093, 2585,
			2593, 2601, 2609, 2617, 2625, 2655, 2663, 2671, 3059, 3389, 3472,
			3473, 3474, 3475, 3476, 3477, 3478, 3479, 3480, 3483, 3485, 3795,
			4087, 4585, 4712, 4714, 4722, 4730, 4738, 4751, 4759, 4874, 4875,
			4876, 4877, 4898, 4899, 4900, 4901, 4922, 4923, 4924, 4925, 4946,
			4947, 4948, 4949, 4970, 4971, 4972, 4973, 4994, 4995, 4996, 4997,
			5048, 5050, 5052, 5576, 6107, 6130, 6187, 6390, 6386, 6390, 6396,
			6404, 6135, 6809, 6916, 4091, 4111, 6655, 6654, 7398, 7398, 7386,
			6324, 6343, 6353, 3387, 5036, 5038, 5040 };

	public static int platebody[] = {  581,10330, 10338, 10348, 8839, 10551, 11724, 11720, 636, 638, 640, 642, 644, 426, 8031, 8027,
			6617, 544, 577, 3793, 3773, 3775, 3771, 3769, 3767, 6139, 1035,
			540, 5553, 4757, 1833, 1835, 6388, 6384, 4111, 4101, 4868, 4869,
			4870, 4871, 4892, 4893, 4894, 4895, 4916, 4917, 4918, 4919, 4940,
			4941, 4942, 4943, 4964, 4965, 4966, 4967, 4988, 4989, 4990,
			0x2f9a0eb, 6186, 6184, 6180, 3058, 4509, 4504, 4069, 4728, 4736,
			4712, 6107, 2661, 3140, 1115, 1117, 1119, 1121, 1123, 1125, 1127,
			2583, 2591, 2599, 2607, 2615, 6322, 2623, 2653, 2669, 3481, 4720,
			4728, 4749, 2661, 6129, 6916, 4091, 6654, 6133, 75, 7399, 7390,
			5575, 6341, 6351, 3387, 5030, 5032, 5034, 7392 };

	public static int fullHelm[] = {  10547,10548,10549,10550,10334, 10342, 10350, 9814,9749,9752,9755,9758,9761,
			9764,9767,9770,9773,9776,9779,9782,9785,9788,9791,9794,9797,9800,9803,9806,9809,9812,	
			11718, 10828, 4041, 4042, 1147, 3748, 6137, 6128, 3753,
			3755, 3749, 3751, 1149, 3751, 7594, 4708, 4716, 4745, 4732, 5554,
			4753, 4732, 4753, 6188, 4511, 4056, 4071, 4724, 6109, 2665, 1153,
			1155, 1157, 1159, 1161, 1163, 1165, 2587, 2595, 2605, 2613, 2619,
			2627, 2657, 2673, 3486, 6402, 6394, 6131, 74, 7539, 7539, 7534,
			5574, 6326 };

	public static int fullMask[] = { 4502, 6623, 7990, 7594, 1153, 1155, 1157,
			1159, 1161, 1163, 1165, 4732, 5554, 4753, 4611, 6188, 3507, 4511,
			4056, 4071, 4724, 2665, 1053, 1055, 1057 };

	// All other IDs are weapons (I hope)

	public static boolean[] itemStackable = new boolean[PlayerConstants.MAX_ITEMS];
	public static boolean[] itemIsNote = new boolean[PlayerConstants.MAX_ITEMS];

	static {
		int counter = 0;
		int c;

		try {
			FileInputStream dataIn = new FileInputStream(new File("./data/item/stackable.dat"));
			while ((c = dataIn.read()) != -1) {
				if (c == 0)
					itemStackable[counter] = false;
				else
					itemStackable[counter] = true;
				counter++;
			}
			dataIn.close();
			for (int i = 6568; i < 6800; i++) {
				itemStackable[i] = false;
			}
			int[] stackID = { 6569, 6572, 6574, 6576, 6578, 6580, 6582, 6584,
					6586, 6588, 6590, 6592, 6594, 6596, 6598, 6600, 6602, 6604,
					6606, 6608, 6610, 6612, 6614, 6616, 6618, 6620, 6622, 6624,
					6626, 6628, 6630, 6632, 6634, 6676, 6684, 6686, 6688, 6690,
					6692, 6694, 6698, 6700, 6702, 6704, 6706, 6725, 6732, 6734,
					6736, 6738, 6740, 6742, 6744, 6761, 6763, 6765, 6795, 6815,
					6890, 6892, 6909, 6911, 6913, 6915, 6917, 6919, 6921, 6923,
					6925, 6960, 6972, 6974, 6976, 6978, 6980, 6982, 6984, 7052,
					7055, 7057, 7059, 7061, 7063, 7065, 7067, 7069, 7071, 7073,
					7075, 7077, 7079, 7081, 7083, 7085, 7087, 7089, 7091, 7093,
					7095, 7113, 7115, 7117, 7123, 7125, 7127, 7129, 7131, 7133,
					7135, 7137, 7139, 7161, 7163, 7165, 7167, 7169, 7171, 7173,
					7175, 7177, 7179, 7181, 7183, 7185, 7187, 7189, 7191, 7193,
					7195, 7197, 7199, 7201, 7203, 7205, 7207, 7209, 7211, 7213,
					7215, 7217, 7219, 7221, 7223, 7227, 7229, 7320, 7322, 7324,
					7326, 7328, 7333, 7335, 7337, 7339, 7341, 7343, 7345, 7347,
					7349, 7351, 7353, 7355, 7357, 7359, 7361, 7363, 7365, 7367,
					7369, 7371, 7373, 7375, 7377, 7379, 7381, 7383, 7385, 7387,
					7389, 7391, 7393, 7395, 7397, 7414, 7417, 7419, 7434, 7436,
					7438, 7440, 7442, 7444, 7446, 7448, 7450, 7452, 7467, 7469,
					7522, 7548, 7549, 7550, 7551, 7552, 7553, 7554, 7555, 7556,
					7557, 7558, 7559, 7560, 7561, 7562, 7563, 7937 };
			for (int i = 0; i < stackID.length; i++) {
				itemStackable[stackID[i]] = true;
				counter++;
			}
			System.out.println("Loaded " + counter + " stackable data.");
		} catch (IOException e) {
			System.out
					.println("Critical error while loading stackabledata! Trace:");
			e.printStackTrace();
		}

		counter = 0;

		try {
			FileInputStream dataIn = new FileInputStream(new File("./data/item/notes.dat"));
			while ((c = dataIn.read()) != -1) {
				if (c == 0)
					itemIsNote[counter] = true;
				else
					itemIsNote[counter] = false;
				counter++;
			}
			dataIn.close();
			int[] noteID = { 6569, 6572, 6574, 6576, 6578, 6580, 6582, 6584,
					6586, 6588, 6590, 6592, 6594, 6596, 6598, 6600, 6602, 6604,
					6606, 6608, 6610, 6612, 6614, 6616, 6618, 6620, 6622, 6624,
					6626, 6628, 6630, 6632, 6634, 6676, 6684, 6686, 6688, 6690,
					6692, 6694, 6698, 6700, 6702, 6704, 6706, 6725, 6732, 6734,
					6736, 6738, 6740, 6742, 6744, 6761, 6763, 6765, 6795, 6815,
					6890, 6892, 6909, 6911, 6913, 6915, 6917, 6919, 6921, 6923,
					6925, 6960, 6972, 6974, 6976, 6978, 6980, 6982, 6984, 7052,
					7055, 7057, 7059, 7061, 7063, 7065, 7067, 7069, 7071, 7073,
					7075, 7077, 7079, 7081, 7083, 7085, 7087, 7089, 7091, 7093,
					7095, 7113, 7115, 7117, 7123, 7125, 7127, 7129, 7131, 7133,
					7135, 7137, 7139, 7161, 7163, 7165, 7167, 7169, 7171, 7173,
					7175, 7177, 7179, 7181, 7183, 7185, 7187, 7189, 7191, 7193,
					7195, 7197, 7199, 7201, 7203, 7205, 7207, 7209, 7211, 7213,
					7215, 7217, 7219, 7221, 7223, 7227, 7229, 7320, 7322, 7324,
					7326, 7328, 7333, 7335, 7337, 7339, 7341, 7343, 7345, 7347,
					7349, 7351, 7353, 7355, 7357, 7359, 7361, 7363, 7365, 7367,
					7369, 7371, 7373, 7375, 7377, 7379, 7381, 7383, 7385, 7387,
					7389, 7391, 7393, 7395, 7397, 7414, 7417, 7419, 7434, 7436,
					7438, 7440, 7442, 7444, 7446, 7448, 7450, 7452, 7467, 7469,
					7522, 7549, 7551, 7553, 7555, 7557, 7559, 7561, 7563, 7567,
					7569, 7571, 7937, };
			for (int i = 0; i < noteID.length; i++) {
				itemIsNote[noteID[i]] = true;
				counter++;
			}
			System.out.println("Loaded " + counter + " note data.");
		} catch (IOException e) {
			System.out.println("Critical error while loading notedata! Trace:");
			e.printStackTrace();
		}
	}

	public static int randomCape() {
		return capes[(int) (Math.random() * capes.length)];
	}

	public static int randomBoots() {
		return boots[(int) (Math.random() * boots.length)];
	}

	public static int randomGloves() {
		return gloves[(int) (Math.random() * gloves.length)];
	}

	public static int randomShield() {
		return shields[(int) (Math.random() * shields.length)];
	}

	public static int randomHat() {
		return hats[(int) (Math.random() * hats.length)];
	}

	public static int randomAmulet() {
		return amulets[(int) (Math.random() * amulets.length)];
	}

	public static int randomArrows() {
		return arrows[(int) (Math.random() * arrows.length)];
	}

	public static int randomRing() {
		return rings[(int) (Math.random() * rings.length)];
	}

	public static int randomBody() {
		return body[(int) (Math.random() * body.length)];
	}

	public static int randomLegs() {
		return legs[(int) (Math.random() * legs.length)];
	}

	public static boolean isPlate(int itemID) {
		for (int i = 0; i < platebody.length; i++)
			if (platebody[i] == itemID)
				return true;
		return false;
	}

	public static boolean isFullHelm(int itemID) {
		for (int i = 0; i < fullHelm.length; i++)
			if (fullHelm[i] == itemID)
				return true;
		return false;
	}

	public static boolean isFullMask(int itemID) {
		for (int i = 0; i < fullMask.length; i++)
			if (fullMask[i] == itemID)
				return true;
		return false;
	}
}