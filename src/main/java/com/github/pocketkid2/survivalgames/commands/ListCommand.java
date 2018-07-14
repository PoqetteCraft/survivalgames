package com.github.pocketkid2.survivalgames.commands;

import java.util.Arrays;

import org.bukkit.command.CommandSender;

import com.github.pocketkid2.survivalgames.Game;
import com.github.pocketkid2.survivalgames.Messages;
import com.github.pocketkid2.survivalgames.SurvivalGamesPlugin;

public class ListCommand extends SubCommand {

	public ListCommand(SurvivalGamesPlugin pl) {
		super(
				pl,
				0,
				0,
				Arrays.asList("list"),
				"",
				"List all maps",
				"survivalgames.list");
	}

	@Override
	public boolean execute(CommandSender sender, String[] arguments) {

		int count = plugin.getGM().allGames().size();

		sender.sendMessage(Messages.LIST_NUM_GAMES(count));

		for (Game g : plugin.getGM().allGames()) {
			sender.sendMessage(Messages.LIST_GAME_NAME(g.getMap().getName(), g.getMap().getRadius()));
		}

		return true;
	}

}
