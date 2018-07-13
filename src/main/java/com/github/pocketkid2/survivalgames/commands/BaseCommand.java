package com.github.pocketkid2.survivalgames.commands;

import java.util.Arrays;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.github.pocketkid2.survivalgames.SurvivalGamesPlugin;

public class BaseCommand implements CommandExecutor {

	private SurvivalGamesPlugin plugin;

	private List<SubCommand> subCommands;

	public BaseCommand(SurvivalGamesPlugin plugin) {
		this.plugin = plugin;
		subCommands.add(new SubCommand() {

			@Override
			public void init() {
				minArguments = 1;
				maxArguments = 1;
				aliases = Arrays.asList("");
			}

			@Override
			public void execute(String[] arguments) {
				// TODO Auto-generated method stub

			}

		});
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		// TODO Auto-generated method stub
		return false;
	}

}
