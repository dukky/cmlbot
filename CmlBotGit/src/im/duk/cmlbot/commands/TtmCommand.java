package im.duk.cmlbot.commands;

import im.duk.cmlbot.ApiRequest;
import im.duk.cmlbot.Command;
import im.duk.cmlbot.ReqType;

import org.pircbotx.PircBotX;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.events.PrivateMessageEvent;
import org.pircbotx.hooks.types.GenericMessageEvent;

public class TtmCommand implements Command {

	@Override
	public <T extends GenericMessageEvent<PircBotX>> void process(T event, String command, String commandVisibility) {
		String message = event.getMessage();
		String[] split = message.split(" ");
		String name = "";
		for(int i = 1; i < split.length; ++i) {
			if (i < split.length-1) {
				name += split[i];
				name += "_";
			} else {
				name += split[i];
			}
		}
		String apiDataTtm = ApiRequest.request(ReqType.TTM, name);
		String apiDataTtmRank = ApiRequest.request(ReqType.TTMRANK, name);
		String response = "";
		if(apiDataTtm.equals("-1")) {
			response = "Player " + name + " not found in database. To add it, use \".cmltrack name\".";
		} else if(apiDataTtm.equals("-2")) {
			response = name + " is an invalid username.";
		} else {
			response = name + " is ranked " + apiDataTtmRank + " for Time to Max, with " + apiDataTtm + " hours to go.";
		}
		if(event instanceof MessageEvent) {
			String channel = ((MessageEvent<PircBotX>) event).getChannel().getName();
			switch (commandVisibility) {
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
