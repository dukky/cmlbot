package im.duk.cmlbot.commands;

import im.duk.cmlbot.Command;

import org.pircbotx.PircBotX;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.events.PrivateMessageEvent;
import org.pircbotx.hooks.types.GenericMessageEvent;

public class CommandsCommand implements Command {

	public <T extends GenericMessageEvent<PircBotX>> void process(T event, String command, String commandVisibility) {
		System.out.println(event.getClass().getSimpleName());
		if (event instanceof MessageEvent) {
			System.out.println("kok");
			event.getBot().sendMessage(((MessageEvent<PircBotX>) event).getChannel(), "kok");
		} else if (event instanceof PrivateMessageEvent) {
			event.getBot().sendMessage(((MessageEvent<PircBotX>) event).getUser(), "dik");
		}
	}
}
