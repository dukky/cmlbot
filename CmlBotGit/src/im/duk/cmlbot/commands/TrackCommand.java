package im.duk.cmlbot.commands;

import im.duk.cmlbot.ApiRequest;
import im.duk.cmlbot.Command;
import im.duk.cmlbot.ReqType;
import im.duk.cmlbot.SkillFunc;

import org.pircbotx.PircBotX;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.events.PrivateMessageEvent;
import org.pircbotx.hooks.types.GenericMessageEvent;

public class TrackCommand implements Command {

	@Override
	public <T extends GenericMessageEvent<PircBotX>> void process(T event, String command, String commandVisibility) {
		String message = event.getMessage().substring(1);
		String[] splitat = message.split("@");
		boolean valid = false;
		boolean time = false;
		System.out.println(splitat.length);
		if (splitat.length == 2) {
			valid = true;
			time = true;
		} else if (splitat.length == 1) {
			valid = true;
			time = false;
		} else {
			valid = false;
			time = false;
		}
		String response = "";
		String response1 = "";
		String response2 = "";
		String response3 = "";
		if (valid) {
			String[] split = splitat[0].split(" ");
			int timeS = SkillFunc.SECONDS_IN_WEEK;
			if (time) {
				String timeString = splitat[1];
				try {
					int ntimeS = Integer.parseInt(timeString.substring(0, timeString.length() - 1).trim());
					System.out.println(ntimeS);
					System.out.println(timeString);
					switch (timeString.charAt(timeString.length()-1)) {
					case 'd':
						timeS = ntimeS * SkillFunc.SECONDS_IN_DAY;
						break;
					case 'w':
						timeS = ntimeS * SkillFunc.SECONDS_IN_WEEK;
						break;
					case 'm':
						timeS = ntimeS * SkillFunc.SECONDS_IN_MONTH;
						break;
					}
					System.out.println(timeS);
					System.out.println(SkillFunc.timeToShortString(timeS));
				} catch (Exception e) {
					e.printStackTrace();
					timeS = SkillFunc.SECONDS_IN_WEEK;
				}
			}
			String name = "";
			for (int i = 1; i < split.length; ++i) {
				if (i < split.length - 1) {
					name += split[i];
					name += "_";
				} else {
					name += split[i];
				}
			}
			String apiDataUpdate = ApiRequest.request(ReqType.UPDATE, name);
			String apiDataStats = ApiRequest.request(ReqType.STATS, name);
			String apiDataTrack = ApiRequest.request(ReqType.TRACK, name, timeS);
			System.out.println(apiDataTrack);
			if (apiDataStats.equals("-1")) {
				// TODO: Fix this here or in the api
				response = "Player "
						+ name
						+ " not found in database. They also couldn't be added when attempted, meaning they aren't on the hiscores or the hiscores are down.";
			} else if (apiDataUpdate.equals("-2")) {
				response = name + " is an invalid username.";
			} else {
				String[] statsLines = apiDataStats.split(" ");
				String[] trackLines = apiDataTrack.split(" ");
				int nowTotal = 0;
				int oldTotal = 0;
				int totalGained = 0;
				for (int i = 2; i < statsLines.length; ++i) {
					String[] thisStatsLine = statsLines[i].split(",");
					String[] thisTrackLine = trackLines[i].split(",");
					int nowXP = Integer.parseInt(thisStatsLine[0]);
					int gainedXP = Integer.parseInt(thisTrackLine[0]);
					int oldXP = nowXP - gainedXP;
					response += SkillFunc.getSkillName(i - 1);
					response += "(";
					int oldLevel = SkillFunc.getVirtualLevel(oldXP);
					int nowLevel = SkillFunc.getVirtualLevel(nowXP);
					if (nowLevel > oldLevel) {
						response += oldLevel + "->" + nowLevel;
					} else {
						response += nowLevel;
					}
					response += ") ";
					response += "+" + gainedXP;
					response += " | ";
					nowTotal += SkillFunc.getLevel(nowXP);
					oldTotal += SkillFunc.getLevel(oldXP);
					totalGained += gainedXP;
				}
				response = "Gains for " + name + " in last " + SkillFunc.timeToShortString(timeS) + ":" + " Overall(+"
						+ (nowTotal - oldTotal) + ") +" + totalGained + " | " + response;
				response1 = response.substring(0, response.indexOf("Mining("));
				response2 = response.substring(response.indexOf("Mining("));
				response3 = "Graphs: http://crystalmathlabs.com/tracker/track.php?user=foot";
			}
		} else {
			response = "Invalid arguments. You probably used more than one @.";
		}
		if (event instanceof MessageEvent) {
			String channel = ((MessageEvent<PircBotX>) event).getChannel().getName();
			switch (commandVisibility) {
			case "!":
				if (valid) {
					event.getBot().sendNotice(event.getUser(), response1);
					event.getBot().sendNotice(event.getUser(), response2);
					event.getBot().sendNotice(event.getUser(), response3);
				} else {
					event.getBot().sendNotice(event.getUser(), response);
				}

				break;
			case ".":
				if (valid) {
					event.getBot().sendNotice(event.getUser(), response1);
					event.getBot().sendNotice(event.getUser(), response2);
					event.getBot().sendNotice(event.getUser(), response3);
				} else {
					event.getBot().sendNotice(event.getUser(), response);
				}
				break;
			case "@":
				if (valid) {
					event.getBot().sendMessage(channel, response1);
					event.getBot().sendMessage(channel, response2);
					event.getBot().sendMessage(channel, response3);
				} else {
					event.getBot().sendMessage(channel, response);
				}
				break;
			}
		} else if (event instanceof PrivateMessageEvent) {
			if (valid) {
				event.respond(response1);
				event.respond(response2);
				event.respond(response3);
			} else {
				event.respond(response);
			}
		}
	}
}
