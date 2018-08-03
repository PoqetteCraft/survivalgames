package com.github.pocketkid2.survivalgames.commands;

import java.util.Arrays;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.pocketkid2.survivalgames.Game;
import com.github.pocketkid2.survivalgames.Messages;
import com.github.pocketkid2.survivalgames.SurvivalGamesPlugin;
import com.github.pocketkid2.survivalgames.Values;

public class AddSpawnCommand extends SubCommand {

	public AddSpawnCommand(SurvivalGamesPlugin pl) {
		super(pl, 1, 1, Arrays.asList("addspawn"), "<name>", "Adds your current location to the spawn list of the given map", "addspawn");
	}

	@Override
	public boolean execute(CommandSender sender, String[] arguments) {
		if (sender instanceof Player) {
			Player player = (Player) sender;

			Game game = plugin.getGM().byName(arguments[1]);

			if (game == null) {
				player.sendMessage(Messages.MAP_DOESNT_EXIST);
			} else {
				if (game.getMap().getSpawns().size() < Values.MAX_SPAWNS) {
					if (game.getMap().contains(player.getLocation())) {
						game.getMap().addSpawn(player.getLocation());
						player.sendMessage(Messages.SPAWN_ADDED(game.maxCount(), game.getMap()));
					} else {
						player.sendMessage(Messages.SPAWN_OUTSIDE_ARENA);
					}
				} else {
					player.sendMessage(Messages.MAX_SPAWNS_REACHED);
				}
			}
		} else {
			sender.sendMessage(Messages.MUST_BE_PLAYER);
		}
		return true;
	}

}
