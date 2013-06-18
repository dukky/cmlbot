package im.duk.cmlbot.commands;

import org.pircbotx.PircBotX;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.events.PrivateMessageEvent;
import org.pircbotx.hooks.types.GenericMessageEvent;

import im.duk.cmlbot.ApiRequest;
import im.duk.cmlbot.Command;
import im.duk.cmlbot.ReqType;
import im.duk.cmlbot.SkillFunc;

public class StatsCommand implements Command {

	@Override
	public <T extends GenericMessageEvent<PircBotX>> void process(T event, String command, String commandVisibility) {
		String message = event.getMessage();
		String[] split = message.split(" ");
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
		String response = "";
		if (apiDataStats.equals("-1")) {
			// TODO: Fix this here or in the api
			response = "Player "
					+ name
					+ " not found in database. They also couldn't be added when attempted, meaning they aren't on the hiscores or the hiscores are down.";
		} else if (apiDataUpdate.equals("-2")) {
			response = name + " is an invalid username.";
		} else {
			String[] lines = apiDataStats.split(" ");
			for (String string : lines) {
				System.out.println(string);
			}
			System.out.println(lines.length);
			int total = 0;
			for (int i = 2; i < lines.length; ++i) {
				String[] thisLine = lines[i].split(",");
				response += SkillFunc.getSkillName(i-1);
				response += " ";
				int level = SkillFunc.getLevel(Integer.parseInt(thisLine[0].trim()));
				response += level;
				response += " | ";
				total +=level;
			}
			response = "Stats for " + name + ": Overall " + total + " | " + response;
		}
		if(event instanceof MessageEvent) {
			String channel = ((MessageEvent<PircBotX>) event).getChannel().getName();
			switch(commandVisibility) {
			case ".":
				event.getBot().sendNotice(event.getUser(), response);
				break;
			case "!":
				event.getBot().sendNotice(event.getUser(), response);
				break;
			case "@":
				event.getBot().sendMessage(channel, response);
				break;
			}
		} else if(event instanceof PrivateMessageEvent) {
			event.respond(response);
		}
	}

}
