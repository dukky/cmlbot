package im.duk.cmlbot.commands;

import im.duk.cmlbot.Command;

import org.pircbotx.PircBotX;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.events.PrivateMessageEvent;
import org.pircbotx.hooks.types.GenericMessageEvent;

public class CommandsCommand implements Command {

	public <T extends GenericMessageEvent<PircBotX>> void process(T event, String command, String commandVisibility) {
		String[] responses = { "All commands can use either <.> <!> or <@>. <.> and <!> will send a notice, <@> will send channel message.",
				".ttm <username> - Tells you the number of hours to max for the user assuming they play efficiently gaining close to the max xp rates.",
				".cmltrack <username> <@time(optional)> - Tracks the user, updating their CrystalMathLabs profile and showing you their gains in the last week (default)" +
				" or the specified time period. Time periods are specified using for example \"@20d\" for 20 days or \"@3w\" for 3 weeks.", 
				".cmlstats <username> - Tells you the stats of the user specified as well as updating their CrystalMathLabs profile. ",
				//"If a skill is provided, more information will be given for that skill including rank, xp and virtual level.",
				".commands - Displays a list of commands and how to use them (what you're reading now).",
				".players - Gives you the total number of players currently online on Oldschool Runescape.",
				".save <1/0> - Sets the bot to remain in the channel after disconnecting. 1 to stay, 0 to not stay." };
		if (event instanceof MessageEvent) {
			String channel = ((MessageEvent<PircBotX>) event).getChannel().getName();
			switch(commandVisibility) {
			case ".":
				for (String response : responses) {					
					event.getBot().sendNotice(event.getUser(), response);
				}
				break;
			case "!":
				for (String response : responses) {
					event.getBot().sendNotice(event.getUser(), response);					
				}
				break;
			case "@": 
				for (String response : responses) {
					event.getBot().sendMessage(channel, response);					
				}
			}
		} else if (event instanceof PrivateMessageEvent) {
			for (String response : responses) {
				event.respond(response);
			}
		}
	}
}
