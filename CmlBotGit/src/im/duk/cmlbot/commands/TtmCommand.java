package im.duk.cmlbot.commands;

import org.pircbotx.PircBotX;
import org.pircbotx.hooks.types.GenericMessageEvent;

import im.duk.cmlbot.Command;

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
		
	}

}
