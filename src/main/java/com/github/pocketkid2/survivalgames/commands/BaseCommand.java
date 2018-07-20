package com.github.pocketkid2.survivalgames.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.github.pocketkid2.survivalgames.Messages;
import com.github.pocketkid2.survivalgames.SurvivalGamesPlugin;

public class BaseCommand implements CommandExecutor {

	private SurvivalGamesPlugin plugin;

	private List<SubCommand> subCommands;

	public BaseCommand(SurvivalGamesPlugin plugin) {
		this.plugin = plugin;
		subCommands = new ArrayList<SubCommand>();

		subCommands.add(new CreateCommand(plugin));
		subCommands.add(new DeleteCommand(plugin));
		subCommands.add(new ListCommand(plugin));
		subCommands.add(new JoinCommand(plugin));
		subCommands.add(new LeaveCommand(plugin));
		subCommands.add(new AddSpawnCommand(plugin));
		subCommands.add(new InfoCommand(plugin));
		subCommands.add(new StartCommand(plugin));
		subCommands.add(new StopCommand(plugin));
		subCommands.add(new TeleportCommand(plugin));
		subCommands.add(new HelpCommand(plugin, this));
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!sender.hasPermission("survivalgames.command")) {
			sender.sendMessage(Messages.NO_PERM);
			return true;
		}

		if (args.length == 0) {
			// Show info
			info(sender);
		} else {
			// Look through subcommands
			for (SubCommand sc : subCommands) {
				// And their aliases
				for (String alias : sc.getAliases()) {
					// If the alias matches the subcommand arg
					if (alias.equalsIgnoreCase(args[0])) {

						// First, run a permission check
						if (!sender.hasPermission(sc.perm)) {
							sender.sendMessage(Messages.NO_PERM);
							return true;
						}

						// Next, check if the argument count is valid
						int tac = args.length - 1;
						boolean valid = (tac >= sc.minArguments) && (tac <= sc.maxArguments);

						// Try the command
						if (!valid || !sc.execute(sender, args)) {
							sender.sendMessage(Messages.INCORRECT_COMMAND_USAGE);
							// If it fails, send the usage message
							sender.sendMessage(String.format(Messages.USAGE(label, args[0], sc.getUsage())));
						}
					}
				}
			}
		}

		return true;

	}

	/*
	 * Send basic plugin info to the player on /sg with no arguments
	 */
	private void info(CommandSender sender) {
		sender.sendMessage(Messages.INFO_PLUGIN_1(plugin.getDescription()));
		sender.sendMessage(Messages.INFO_PLUGIN_2(plugin.getDescription()));
		sender.sendMessage(Messages.INFO_PLUGIN_3(plugin.getGM()));
		sender.sendMessage(Messages.INFO_HELP);
	}

	public List<SubCommand> getSubCommands() {
		return subCommands;
	}

}
