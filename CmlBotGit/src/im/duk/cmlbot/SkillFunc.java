package im.duk.cmlbot;

public class SkillFunc {
	public static int getVirtualLevel(int xp) {
		double points = 0;
		int lvlXP = 0;
		for (int lvl = 1; lvl < 127; lvl++) {
			points += Math.floor(lvl + 300.0 * Math.pow(2.0, lvl / 7.0));
			lvlXP = (int) Math.floor(points / 4);

			if (lvlXP > xp) {
				return lvl;
			}
		}
		return 126;
	}

	public static int getLevel(int xp) {
		return Math.min(99, getVirtualLevel(xp));
	}

	public static String timeToShortString(long time) {
		if (time == 0)
			return "0s";

		int days = (int) (time / 86400);
		time %= 86400;
		int hours = (int) (time / 3600);
		time %= 3600;
		int minutes = (int) (time / 60);
		time %= 60;

		String ret = "";
		if (days > 0)
			ret = ret + days + "d";
		if (hours > 0)
			ret = ret + hours + "h";
		if (minutes > 0)
			ret = ret + minutes + "m";
		if (time > 0)
			ret = ret + time + "s";
		return ret;
	}

	public static String getSkillName(int i) {
		switch (i) {
		case 0:
			return "Overall";
		case 1:
			return "Attack";
		case 2:
			return "Defence";
		case 3:
			return "Strength";
		case 4:
			return "Hitpoints";
		case 5:
			return "Ranged";
		case 6:
			return "Prayer";
		case 7:
			return "Magic";
		case 8:
			return "Cooking";
		case 9:
			return "Woodcutting";
		case 10:
			return "Fletching";
		case 11:
			return "Fishing";
		case 12:
			return "Firemaking";
		case 13:
			return "Crafting";
		case 14:
			return "Smithing";
		case 15:
			return "Mining";
		case 16:
			return "Herblore";
		case 17:
			return "Agility";
		case 18:
			return "Thieving";
		case 19:
			return "Slayer";
		case 20:
			return "Farming";
		case 21:
			return "Runecrafting";
		case 22:
			return "Hunter";
		case 23:
			return "Construction";
		}
		return null;
	}
}
