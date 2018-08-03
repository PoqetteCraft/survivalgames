package com.github.pocketkid2.survivalgames.commands;

import java.util.Arrays;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.pocketkid2.survivalgames.Game;
import com.github.pocketkid2.survivalgames.Messages;
import com.github.pocketkid2.survivalgames.SurvivalGamesPlugin;

public class SetSpawnCommand extends SubCommand {

	public SetSpawnCommand(SurvivalGamesPlugin pl) {
		super(pl, 2, 2, Arrays.asList("setspawn"), "<name> <id>", "Override a specific spawn location", "setspawn");
	}

	@Override
	public boolean execute(CommandSender sender, String[] arguments) {
		if (sender instanceof Player) {
			Player player = (Player) sender;

			Game game = plugin.getGM().byName(arguments[1]);

			if (game == null) {
				player.sendMessage(Messages.MAP_DOESNT_EXIST);
			} else {
				int index;
				try {
					index = Integer.parseInt(arguments[2]) - 1;
				} catch (NumberFormatException e) {
					player.sendMessage(Messages.INCORRECT_COMMAND_USAGE);
					return false;
				}
				if ((index < 0) || (index >= game.getMap().getSpawns().size())) {
					player.sendMessage(Messages.INVALID_SPAWN_INDEX);
				} else {
					if (game.getMap().contains(player.getLocation())) {
						game.getMap().setSpawn(index, player.getLocation());
						player.sendMessage(Messages.SPAWN_UPDATED(index, game.getMap()));
					} else {
						player.sendMessage(Messages.SPAWN_OUTSIDE_ARENA);
					}
				}
			}
		} else {
			sender.sendMessage(Messages.MUST_BE_PLAYER);
		}
		return true;
	}

}
