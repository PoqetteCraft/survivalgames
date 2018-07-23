package com.github.pocketkid2.survivalgames.commands;

import java.util.Arrays;
import java.util.List;

import org.bukkit.command.CommandSender;

import com.github.pocketkid2.survivalgames.Messages;
import com.github.pocketkid2.survivalgames.SurvivalGamesPlugin;

import net.md_5.bungee.api.ChatColor;

public class HelpCommand extends SubCommand {

	private BaseCommand bc;

	public HelpCommand(SurvivalGamesPlugin pl, BaseCommand bc) {
		super(pl,
				0,
				1,
				Arrays.asList("help"),
				"[command]",
				"Show a list of commands or help for a specific command",
				"help");
		this.bc = bc;
	}

	@Override
	public boolean execute(CommandSender sender, String[] arguments) {
		if (arguments.length == 2) {
			String sub = arguments[1];
			// Specific command help
			for (SubCommand sc : bc.getSubCommands()) {
				for (String alias : sc.getAliases()) {
					if (alias.equalsIgnoreCase(sub)) {
						sender.sendMessage(Messages.COMMAND_HELP_FOR(alias));
						sender.sendMessage(Messages.USAGE("sg", alias, sc.getUsage()));
						sender.sendMessage(ChatColor.AQUA + sc.getDesc());
						return true;
					}
				}
			}
			sender.sendMessage(Messages.COMMAND_NOT_FOUND);
		} else {
			// General command help
			List<SubCommand> subs = bc.getSubCommands();
			sender.sendMessage(Messages.COMMAND_LIST(subs.size()));
			for (SubCommand sc : subs) {
				sender.sendMessage(Messages.USAGE("sg", sc.getAliases().get(0), sc.getUsage()));
			}
		}
		return true;
	}

}
