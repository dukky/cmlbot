package im.duk.cmlbot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.pircbotx.PircBotX;
import org.pircbotx.exception.IrcException;
import org.pircbotx.exception.NickAlreadyInUseException;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.InviteEvent;
import org.pircbotx.hooks.events.MessageEvent;

@SuppressWarnings("rawtypes")
public class BotTest extends ListenerAdapter {

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		PircBotX bot = new PircBotX();
		bot.getListenerManager().addListener(new BotTest());
		bot.setLogin("cmlbot");
		
		bot.setName("cmlbot");
		bot.setVersion("Crystal Math Labs bot v0.1");
		bot.setVerbose(true);
		try {
			bot.connect("irc.swiftirc.net");
			bot.joinChannel("#duksandfish");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error in connecting to network.");
		} catch (NickAlreadyInUseException e) {
			e.printStackTrace();
			System.out.println("The nick is already in use.");
		} catch (IrcException e) {
			e.printStackTrace();
			System.out.println("Error in connecting to network.");
		}
		System.out.println("Connected to network.");
	}

	@Override
	public void onInvite(InviteEvent event) throws Exception {
		System.out.println(event.getChannel());
	}

	public void onMessage(MessageEvent event) {
		String message = event.getMessage();
		System.out.println(message);

		if (message.equals("?quit")) {
			System.out.println("quitting");
			event.getBot().quitServer("Quit command issued.");
			System.exit(0);
		} else if (message.startsWith(".")) {
			Pair<String, ArrayList<String>> pair = parseMessage(message);
			String command = pair.first;
			ArrayList<String> args = pair.second;
			try {
				switch (command) {
				case ".ttm":
					apiReq(event, args, ReqType.TTM);
					break;
				case ".ttmrank":
					apiReq(event, args, ReqType.TTMRANK);
					break;
				/*
				 * case ".cmltrack": int updated = update(args); if (updated ==
				 * 1) { apiReq(event, args, ReqType.TRACK); } else {
				 * event.respond("Error updating user: " + args.get(0) + "."); }
				 * break;
				 */
				case ".cmlstats":
					int updated2 = update(args);
					if (updated2 == 1) {
						apiReq(event, args, ReqType.STATS);
					} else {
						event.respond("Error updating user: " + args.get(0)
								+ ".");
					}
					break;
				case ".fkfut":
					event.getBot().sendMessage(event.getChannel(),
							"FK FUT!!!!!!!!!!!!!!!!!!!!");
					// event.getBot().sendMessage(event.getUser(), "ok");
					event.respond("u");
					event.getBot().sendNotice(event.getUser(), "notice??");
					break;
				case ".invite":
					event.getBot().joinChannel(args.get(0));
					event.getBot()
							.sendMessage(
									args.get(0),
									"Hello, users of "
											+ args.get(0)
											+ "! I am cmlbot, a bot to provide access to the CrystalMathLabs Oldschool Runescape tracker. For a list of commands, type '.commands'."
											+ " I was invited by "
											+ event.getUser().getNick() + ".");

				default:
					break;
				}
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("IO Exception while making api request.");
			}

		}

	}

	private Pair<String, ArrayList<String>> parseMessage(String message) {
		String[] messageArr = message.split(" ");
		String command = messageArr[0];
		ArrayList<String> args = new ArrayList<>();
		if (messageArr.length > 1) {
			for (int i = 1; i < messageArr.length; ++i) {
				args.add(messageArr[i]);
			}
		}
		return new Pair<String, ArrayList<String>>(command, args);
	}

	private int update(ArrayList<String> args) throws MalformedURLException,
			IOException {
		URL url = new URL(
				"http://crystalmathlabs.com/tracker/api.php?type=update&player="
						+ args.get(0));
		URLConnection conn = url.openConnection();
		BufferedReader read = new BufferedReader(new InputStreamReader(
				conn.getInputStream()));
		String inputString;
		int res = 0;
		while ((inputString = read.readLine()) != null) {
			res = Integer.parseInt(inputString);
		}
		return res;
	}

	private void apiReq(MessageEvent event, ArrayList<String> args, ReqType type)
			throws IOException {
		if (args.size() > 0) {
			String typeString = null;
			switch (type) {

			case STATS:
				typeString = "stats";
				break;
			case TRACK:
				typeString = "track";
				break;
			case TTM:
				typeString = "ttm";
				break;
			case TTMRANK:
				typeString = "ttmrank";
				break;
			default:
				break;
			}

			URL apiUrl = null;
			try {
				apiUrl = new URL(
						"http://crystalmathlabs.com/tracker/api.php?type="
								+ typeString + "&player=" + args.get(0));
			} catch (MalformedURLException e) {
				System.out
						.println("Malformed url, probably invalid characters in username.");
			}
			if (type == ReqType.TRACK) {
				long time = 604800;
				if (args.size() > 1) {
					if (args.get(1).equals("day")) {
						time = 86400;
					} else if (args.get(1).equals("week")) {
						time = 604800;
					} else if (args.get(1).equals("month")) {
						time = 2678400;
					} else if (args.get(1).equals("year")) {
						time = 31536000;
					} else {
						event.respond("Invalid time period: "
								+ args.get(1)
								+ ". Please use day/week/month/year. "
								+ "Note: if this detected part of your name as the date, please use underscores instead of spaces in your name.");
						return;
					}
				}
				apiUrl = new URL(
						"http://crystalmathlabs.com/tracker/api.php?type=track&player="
								+ args.get(0) + "&time=" + time);
			}

			URLConnection apiConnection = apiUrl.openConnection();
			BufferedReader reader;
			reader = new BufferedReader(new InputStreamReader(
					apiConnection.getInputStream()));
			String inputLine;
			if (type == ReqType.TRACK || type == ReqType.STATS) {
				ArrayList<String> data = new ArrayList<String>();
				while ((inputLine = reader.readLine()) != null) {
					data.add(inputLine);
					System.out.println(inputLine);
				}
				System.out.println(data.size());
				if (data.size() != 1) {
					String res = "";
					int total = 0;
					for (int i = 2; i < data.size(); ++i) {
						System.out.println("lel" + i);
						res += getSkillName(i - 1) + ": ";
						String thisData = data.get(i);
						String[] thisArr = thisData.split(",");
						int level = getLevel(Integer.parseInt(thisArr[0]));

						res += level + ", ";
						int xp = Integer.parseInt(thisArr[0]);
						res += xp + ", ";

						int rank = Integer.parseInt(thisArr[1].trim());
						res += rank;
						if (i != (data.size() - 1))
							res += ". ";
						else
							res += ".";
						total += level;
					}
					res = "Player "
							+ args.get(0)
							+ " Lvl, xp, rank: Overall: "
							+ total
							+ ", "
							+ Integer.parseInt(data.get(1).split(",")[0])
							+ ", "
							+ Integer
									.parseInt(data.get(1).split(",")[1].trim())
							+ ". " + res;
					String res1 = res.substring(0, res.length() / 2);
					String res2 = res.substring(res.length() / 2);
					event.respond(res1);
					event.respond(res2);
				}
			} else {
				int res = 0;
				while ((inputLine = reader.readLine()) != null) {
					System.out.println(inputLine);
					res = Integer.valueOf(inputLine);
				}
				if (res == -1) {
					event.respond("The username: "
							+ args.get(0)
							+ " is not in the database, please add it with \".cmlstats "
							+ args.get(0)
							+ "\". Note: spaces in usernames must be replaced with underscores.");
				} else if (res == -2) {
					event.respond("Invalid username: "
							+ args.get(0)
							+ ". Note: Spaces in usernames must be replaced with underscores.");
				} else if (res == -3) {
					event.respond("UH-OH!");
				} else {
					switch (type) {
					case TTM:
						event.respond(args.get(0) + "'s Time to max is " + res
								+ " hours.");
						break;
					case TTMRANK:
						event.respond(args.get(0) + "'s Time to max rank is " + res + ".");
						break;
					default:
						break;
					}

				}
				reader.close();
			}
		} else {
			event.respond("No username specified.");
		}
	}

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
