package com.github.pocketkid2.survivalgames.commands;

import java.util.Arrays;

import org.bukkit.command.CommandSender;

import com.github.pocketkid2.survivalgames.Game;
import com.github.pocketkid2.survivalgames.Messages;
import com.github.pocketkid2.survivalgames.SurvivalGamesPlugin;

public class DeleteCommand extends SubCommand {

	public DeleteCommand(SurvivalGamesPlugin pl) {
		super(pl, 1, 1, Arrays.asList("delete"), "<name>", "Deletes a map with the given name", "delete");
	}

	@Override
	public boolean execute(CommandSender sender, String[] arguments) {
		// Find an arena with the given name and remove it
		Game game = plugin.getGM().byName(arguments[1]);
		if (game != null) {
			plugin.getGM().removeGame(game);
			sender.sendMessage(Messages.MAP_REMOVED(game.getMap()));
		} else {
			sender.sendMessage(Messages.MAP_DOESNT_EXIST);
		}
		return true;
	}

}
