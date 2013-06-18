package im.duk.cmlbot.commands;

import im.duk.cmlbot.Command;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.pircbotx.PircBotX;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.types.GenericMessageEvent;

public class SaveCommand implements Command {

	private Set<String> channels = new HashSet<>();

	@Override
	public <T extends GenericMessageEvent<PircBotX>> void process(T event, String command, String commandVisibility) {
		if (event instanceof MessageEvent) {
			String channel = ((MessageEvent<PircBotX>) event).getChannel().getName();
			String message = event.getMessage();
			ArrayList<String> args = new ArrayList<>(Arrays.asList(message.split(" ")));
			if (args.size() > 1) {
				switch (args.get(1)) {
				case "1":
					event.getBot().sendMessage(channel, "Now autojoining " + channel);
					getChannels().add(channel);
					saveChannels();
					break;
				case "0":
					event.getBot().sendMessage(channel, "No longer autojoining " + channel);
					getChannels().remove(channel);
					saveChannels();
					break;
				default:
					event.getBot()
							.sendMessage(
									channel,
									"Invalid argument. Please use \".save 1\" to get the bot to autojoin your channel or \".save 0\" to stop it from autojoining.");
					break;
				}
			} else {
				event.getBot()
						.sendMessage(
								channel,
								"No argument supplied. Please use \".save 1\" to get the bot to autojoin your channel or \".save 0\" to stop it from autojoining.");
			}
		}
	}

	private void saveChannels() {
		File f = new File("channels.txt");
		f.delete();
		try {
			BufferedWriter channelWriter = new BufferedWriter(new FileWriter(f, false));
			for (String channel : getChannels()) {
				channelWriter.write(channel + "\n");
			}
			channelWriter.close();
			System.out.println("Saved to " + f.getAbsolutePath());
		} catch (IOException e) {
			System.out.println("Unable to write to file " + f.getAbsolutePath());
			e.printStackTrace();
		}
	}

	public Set<String> getChannels() {
		return channels;
	}

	public void setChannels(Set<String> channels) {
		this.channels = channels;
	}

}
