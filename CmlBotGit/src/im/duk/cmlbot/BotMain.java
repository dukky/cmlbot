package im.duk.cmlbot;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import org.pircbotx.PircBotX;
import org.pircbotx.exception.IrcException;

public class BotMain {

	public static void main(String[] args) {
		PircBotX bot = new PircBotX();
		Set<String> channels = new HashSet<>();
		File f = new File("channels.txt");
		Scanner s;
		try {
			s = new Scanner(f);
			while (s.hasNext()) {
				String channel = s.nextLine();
				channels.add(channel);
			}
			s.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}

		CommandListener mainListener = new CommandListener(channels);
		bot.getListenerManager().addListener(mainListener);
		bot.setLogin("cmlbot");
		bot.setName("cmlbot_test");
		//bot.identify("UvNE^6CyfRu8R5K");
		bot.setVersion("Crystal Math Labs bot v1.0");
		bot.setVerbose(true);

		try {
			bot.connect("irc.swiftirc.net");
			for (String channel : channels) {
				bot.joinChannel(channel);
			}
			System.out.println("Connected to network.");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error in connecting to network.");
		} catch (IrcException e) {
			e.printStackTrace();
			System.out.println("Error in connecting to network.");
		}
	}

}
