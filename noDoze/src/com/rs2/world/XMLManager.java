package com.rs2.world;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import com.rs2.model.WorldObject;
import com.thoughtworks.xstream.XStream;

/**
 * 
 * @author Killamess
 * 	Trying different ways to
 *  load all the xml files the
 *  quickest
 */
public class XMLManager {
	
	public static XStream xstream;

	static {
		xstream = new XStream();
		xstream.alias("object", WorldObject.class);
		xstream.alias("settings", Settings.class);
		xstream.alias("npc", NPCSizes.class);
		xstream.alias("spell", Spell.class);
		xstream.alias("teleport", Teleport.class);
		xstream.alias("projectile", Projectile.class);
		xstream.alias("enchant", Enchants.class);
		xstream.alias("tree", Trees.class);
		xstream.alias("axe", Axes.class);
		xstream.alias("food", Cooking.class);
		xstream.alias("catch", Catches.class);
		xstream.alias("fire", FireStarter.class);
	
	}
	
	public static Settings settings;
	public static List<Trees> trees = new ArrayList<Trees>();
	public static List<Axes> axes = new ArrayList<Axes>();
	public static List<Enchants> enchants = new ArrayList<Enchants>();
	public static List<Spell> spells = new ArrayList<Spell>();
	public static List<Teleport> teleports = new ArrayList<Teleport>();
	public static List<Projectile> projectile = new ArrayList<Projectile>();
	public static List<NPCSizes> sizes = new ArrayList<NPCSizes>();
	public static List<WorldObject> objects = new ArrayList<WorldObject>();
	public static List<Cooking> ingredients = new ArrayList<Cooking>();
	public static List<Catches> catches = new ArrayList<Catches>();
	public static List<FireStarter> fires = new ArrayList<FireStarter>();
	
	@SuppressWarnings("unchecked")
	public static void load() {	
		try {
			
			int fileCount = 0;
			long start = System.currentTimeMillis();
			
			System.out.println("[XML] Loading files.");
			
			trees.addAll((List<Trees>) xstream.fromXML(new FileInputStream("./data/world/xml/woodcutting/Trees.xml"))); fileCount++;
			axes.addAll((List<Axes>) xstream.fromXML(new FileInputStream("./data/world/xml/woodcutting/Axes.xml"))); fileCount++;
			enchants.addAll((List<Enchants>) xstream.fromXML(new FileInputStream("./data/world/xml/magic/enchants.xml"))); fileCount++;
			spells.addAll((List<Spell>) xstream.fromXML(new FileInputStream("./data/world/xml/magic/spells.xml"))); fileCount++;
			teleports.addAll((List<Teleport>) xstream.fromXML(new FileInputStream("./data/world/xml/magic/teleports.xml"))); fileCount++;
			projectile.addAll((List<Projectile>) xstream.fromXML(new FileInputStream("./data/world/xml/ranged/ammunition.xml"))); fileCount++;
			sizes.addAll((List<NPCSizes>) xstream.fromXML(new FileInputStream("./data/world/xml/npc/npcsizes.xml"))); fileCount++;
			objects.addAll((List<WorldObject>) xstream.fromXML(new FileInputStream("./data/world/xml/object/objects.xml"))); fileCount++;
			ingredients.addAll((List<Cooking>) xstream.fromXML(new FileInputStream("./data/world/xml/cooking/food.xml"))); fileCount++;
			catches.addAll((List<Catches>) xstream.fromXML(new FileInputStream("./data/world/xml/fishing/fish.xml"))); fileCount++;
			fires.addAll((List<FireStarter>) xstream.fromXML(new FileInputStream("./data/world/xml/firemaking/fires.xml"))); fileCount++;
			
			long time = System.currentTimeMillis() - start;
			System.out.println("[XML] Loaded "+ fileCount +" files in " + time +"ms.");
			
		} catch(FileNotFoundException file) {
			file.printStackTrace();
		}
	}
	
	public static void loadServerSettings() {
		try {
			Object config = xstream.fromXML(new FileInputStream("./data/world/xml/settings.xml"));
			settings = (Settings) config;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static Settings settings() {
		return settings;
	}
	/**
	 * The Class files
	 */
	public class Axes {
		
		private int axeId;
		private int axeAnimation;
		private int axeStrength;
		
		public int getAxeId() {
			return axeId;
		}
		public int getAxeAnimation() {
			return axeAnimation;
		}
		public int getAxeStrength() {
			return axeStrength;
		}
	}
	
	/**
	 * Still missing animation
	 *
	 */
	public class Enchants {
		
		private int spellId;
		private int level;
		private int[] runes;
		private int[] amounts;
		private int gfx;
		private int originalId;
		private int newId;
		private int xp;
		
		public int getSpellId() {
			return spellId;
		}
		public int getLevel() {
			return level;
		}
		public int[] getRunes() {
			return runes;
		}
		public int[] getAmounts() {
			return amounts;
		}
		public int getGfx() {
			return gfx;
		}
		public int getOriginalId() {
			return originalId;
		}
		public int getNewId() {
			return newId;
		}
		public int getXp() {
			return xp;
		}
	}
	
	public class NPCSizes {
		
		private int type;
		private int size;
			
		public NPCSizes(int type, int size) {
			this.type = type;
			this.size = size;
		}
		
		public int getType() {
			return type;
		}
			
		public int getSize() {
			return size;	
		}
	}
	
	public class Projectile {
		
		private int id;
		private int pullBackGfx;
		private int airGfx;
		private int projectileStrength;

		public int getId() {
			return id;
		}
		public int getPullBackGfx() {
			return pullBackGfx;
		}
		public int getAirGfx() {
			return airGfx;
		}
		public int getStrength() {
			return projectileStrength;
		}	
	}
	
	public class Spell {
		
		private String name;
		private int id;
		private int magicLevel;
		private int damage;
		private int freezeDelay;
		private int startGfxDelay;
		private int projectileDelay;
		private int endGfxDelay;
		private int endHitDelay;
		private int animationId;
		private int endGfxHeight;
		private int projectileStartHeight;	
		private int projectileEndHeight;
		private int handGfx;
		private int airGfx; 
		private int endGfx;
		private int[] runes;
		private int[] amounts;
		private int xp;

		public int getId() {
			return id;
		}

		public int getMagicLevel() {
			return magicLevel;
		}

		public int getDamage() {
			return damage;
		}

		public int getFreezeDelay() {
			return freezeDelay;
		}

		public int getStartGfxDelay() {
			return startGfxDelay;
		}

		public int getProjectileDelay() {
			return projectileDelay;
		}

		public int getEndGfxDelay() {
			return endGfxDelay;
		}

		public int getEndHitDelay() {
			return endHitDelay;
		}

		public int getAnimationId() {
			return animationId;
		}

		public int getEndGfxHeight() {
			return endGfxHeight;
		}

		public int getProjectileStartHeight() {
			return projectileStartHeight;
		}

		public int getProjectileEndHeight() {
			return projectileEndHeight;
		}
		
		public int getHandGfx() {
			return handGfx;
		}

		public int getAirGfx() {
			return airGfx;
		}

		public int getEndGfx() {
			return endGfx;
		}

		public int[] getRunes() {
			return runes;
		}

		public int[] getAmounts() {
			return amounts;
		}
		
		public String getName() {
			return name;
		}
		
		public int getXp() {
			return xp;
		}
	}
	
	public class Teleport {
		
		private String name;
		private int id;
		private int[] location;
		private int level;
		private int xp;
		private int[] runes;
		private int[] amounts;
		
		public String getName() {
			return name;
		}
		public int getId() {
			return id;
		}
		public int getAbsX() {
			return location[0];
		}
		public int getAbsY() {
			return location[1];
		}
		public int getHeight() {
			return location[2];
		}
		public int getLevel() {
			return level;
		}
		public int getXp() {
			return xp;
		}
		public int[] getRunes() {
			return runes;
		}
		public int[] getAmounts() {
			return amounts;
		}
	}
	
	public class Trees {
		
		private int treeId;
		private int level;
		private int log;
		private int minimumAxe;
		private int xp;
		private int stumpId;
		private int logAmount;
		private int respawnTime;
		
		public int getTreeId() {
			return treeId;
		}
		public int getLevel() {
			return level;
		}
		public int getLog() {
			return log;
		}
		public int getMinimumAxe() {
			return minimumAxe;
		}
		public int getXp() {
			return xp;
		}
		public int getStumpId() {
			return stumpId;
		}
		public int getLogAmount() {
			return logAmount;
		}
		public int getRespawnTime() {
			return respawnTime;
		}
	}

	public class Settings {

		private String serverName;
		private int serverPort;
		private int serverRegistrationPort;
		
		public String getServerName() {
			return serverName;
		}

		public int getServerPort() {
			return serverPort;
		}
		
		public int getServerRegistrationPort() {
			return serverRegistrationPort;
		}
	}
	
	public class Cooking {
		
		private int rawType;
		private int cookedType;
		private int burntType;
		private int level;
		private int xp;
		
		public int getRawType() {
			return rawType;
		}
		public int getCookedType() {
			return cookedType;
		}
		public int getBurntType() {
			return burntType;
		}
		public int getLevel() {
			return level;
		}
		public int getXp() {
			return xp;
		}
	}

	public class Catches {
		
		private int fishingSpot;
		private int animation;
		private int[] catches = new int[3];
		private int[] tools = new int[2];
		private int[] catchLevel = new int[3];
		private int[] toolAmounts = new int[2];
		
		public int getFishingSpot() {
			return fishingSpot;
		}
		public int getAnimation() {
			return animation;
		}
		public int[] getCatches() {
			return catches;
		}
		public int[] getCatchLevels() {
			return catchLevel;
		}
		public int[] getTools() {
			return tools;
		}
		public int[] getToolAmounts() {
			return toolAmounts;
		}
	}
	
	public class FireStarter {
		
		private int logType;
		private int animationType;
		private int groundObject;
		private int burnTime;
		private int levelRequired;
		private int experience;
		
		public int getLogType() {
			return logType;
		}
		public int getAnimation() {
			return animationType;
		}
		public int getGroundObject() {
			return groundObject;
		}
		public int getBurnTime() {
			return burnTime;
		}
		public int getLevel() {
			return levelRequired;
		}	
		public int getExperience() {
			return experience;
		}
	}
}
