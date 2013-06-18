package im.duk.cmlbot;

import java.util.Set;

import org.pircbotx.PircBotX;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.InviteEvent;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.events.PrivateMessageEvent;

public class CommandListener extends ListenerAdapter<PircBotX> {
	private final CommandParser parser;

	public CommandListener(Set<String> channels) {
		parser = new CommandParser(channels);
	}

	@Override
	public void onMessage(MessageEvent<PircBotX> event) throws Exception {
		getParser().parseMessage(event);
	}

	@Override
	public void onPrivateMessage(PrivateMessageEvent<PircBotX> event) throws Exception {
		getParser().parseMessage(event);
	}

	@Override
	public void onInvite(InviteEvent<PircBotX> event) throws Exception {
		String channel = event.getChannel();
		event.getBot().joinChannel(channel);
		event.getBot()
				.sendMessage(
						channel,
						"Hello, users of "
								+ channel
								+ "! I am cmlbot, a bot to provide access to the CrystalMathLabs Oldschool Runescape tracker. For a list of commands, type '.commands'."
								+ " I was invited by " + event.getUser() + ".");
		event.getBot().sendMessage(channel,
				"If you would like me to autojoin (come back after reconnecting) this channel, use the command \".save 1\"");
		System.out.println(event.getBot().getChannelsNames().toString());
	}

	public CommandParser getParser() {
		return parser;
	}

}
