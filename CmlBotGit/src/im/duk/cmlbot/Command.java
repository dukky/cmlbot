package im.duk.cmlbot;

import org.pircbotx.PircBotX;
import org.pircbotx.hooks.types.GenericMessageEvent;

public interface Command {
	public <T extends GenericMessageEvent<PircBotX>> void process(T event, String command, String commandVisibility);
}
