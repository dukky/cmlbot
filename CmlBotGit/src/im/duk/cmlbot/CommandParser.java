package im.duk.cmlbot;

import im.duk.cmlbot.commands.CommandsCommand;
import im.duk.cmlbot.commands.PlayersCommand;
import im.duk.cmlbot.commands.SaveCommand;
import im.duk.cmlbot.commands.TtmCommand;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

import org.pircbotx.PircBotX;
import org.pircbotx.hooks.types.GenericMessageEvent;

public class CommandParser {
	private CommandsCommand commandsCommand = new CommandsCommand();
	private SaveCommand saveCommand = new SaveCommand();
	private PlayersCommand playersCommand = new PlayersCommand();
	private TtmCommand ttmCommand = new TtmCommand();
	public CommandParser(Set<String> channels) {
		saveCommand.setChannels(channels);
	}

	public void parseMessage(GenericMessageEvent<PircBotX> event) {
		String message = event.getMessage();
		if (message.startsWith(".") || message.startsWith("!") || message.startsWith("@")) {
			ArrayList<String> messageSplit = new ArrayList<>(Arrays.asList(message.split(" ")));
			String command = messageSplit.get(0);
			System.out.println(messageSplit.get(0));
			String commandVisibility = Character.toString(command.charAt(0));
			if(command.length() > 1) {
				command = command.substring(1);
				System.out.println(command);
				switch (command) {
				case "ttm":
					ttmCommand.process(event, command, commandVisibility);
					break;
				case "cmltrack":
					break;
				case "cmlstats":
					break;
				case "players":
					playersCommand.process(event, command, commandVisibility);
					break;
				case "commands":
					commandsCommand.process(event, command, commandVisibility);
					break;
				case "save":
					saveCommand.process(event, command, commandVisibility);
					break;
				default:
					break;
				}
			}
		}
	}

	public CommandsCommand getCommandsCommand() {
		return commandsCommand;
	}

	public void setCommandsCommand(CommandsCommand commandsCommand) {
		this.commandsCommand = commandsCommand;
	}

	public SaveCommand getSaveCommand() {
		return saveCommand;
	}

	public void setSaveCommand(SaveCommand saveCommand) {
		this.saveCommand = saveCommand;
	}

}
