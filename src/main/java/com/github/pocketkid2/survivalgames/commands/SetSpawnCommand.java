package com.github.pocketkid2.survivalgames.commands;

import java.util.Arrays;

import org.bukkit.command.CommandSender;

import com.github.pocketkid2.survivalgames.SurvivalGamesPlugin;

public class SetSpawnCommand extends SubCommand {

	public SetSpawnCommand(SurvivalGamesPlugin pl) {
		super(pl,
				2,
				2,
				Arrays.asList("setspawn"),
				"<name> <id>",
				"Override a specific spawn location",
				"setspawn");
	}

	@Override
	public boolean execute(CommandSender sender, String[] arguments) {
		// TODO Auto-generated method stub
		return false;
	}

}
