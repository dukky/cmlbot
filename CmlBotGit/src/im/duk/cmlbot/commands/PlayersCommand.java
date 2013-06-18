package im.duk.cmlbot.commands;

import im.duk.cmlbot.ApiRequest;
import im.duk.cmlbot.Command;
import im.duk.cmlbot.ReqType;

import java.util.Date;

import org.pircbotx.PircBotX;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.events.PrivateMessageEvent;
import org.pircbotx.hooks.types.GenericMessageEvent;

public class PlayersCommand implements Command {

	@SuppressWarnings("deprecation")
	@Override
	public <T extends GenericMessageEvent<PircBotX>> void process(T event, String command, String commandVisibility) {
		String apiData = ApiRequest.request(ReqType.PLAYERS);
		String[] split = apiData.split(",");
		String time = split[0];
		String players = split[1];
		System.out.println(time);
		Date date = new Date(Long.parseLong(time + "000"));
		time = date.toLocaleString();
		String response = players + " players online on Oldschool Runescape as of " + time
				+ ". For graphs go to http://crystalmathlabs.com/tracker/players.php";
		if (event instanceof MessageEvent) {
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
		} else if (event instanceof PrivateMessageEvent) {
			event.respond(response);
		}
	}

}
