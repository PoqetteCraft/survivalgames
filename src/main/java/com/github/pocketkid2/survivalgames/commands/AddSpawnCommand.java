package com.github.pocketkid2.survivalgames.commands;

import java.util.Arrays;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.pocketkid2.survivalgames.Game;
import com.github.pocketkid2.survivalgames.Messages;
import com.github.pocketkid2.survivalgames.SurvivalGamesPlugin;

public class AddSpawnCommand extends SubCommand {

	public AddSpawnCommand(SurvivalGamesPlugin pl) {
		super(pl,
				1,
				1,
				Arrays.asList("addspawn"),
				"<name>",
				"Adds your current location to the spawn list of the given map",
				"survivalgames.addspawn");
	}

	@Override
	public boolean execute(CommandSender sender, String[] arguments) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			Location loc = player.getLocation();

			Game g = plugin.getGM().byName(arguments[1]);
			if (g == null) {
				player.sendMessage(Messages.MAP_DOESNT_EXIST);
			} else {
				g.getMap().addSpawn(loc);
				player.sendMessage(Messages.SPAWN_ADDED(g.getMap().getSpawns().size()));
			}
		} else {
			sender.sendMessage(Messages.MUST_BE_PLAYER);
		}
		return true;
	}

}
