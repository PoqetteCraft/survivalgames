package com.github.pocketkid2.survivalgames.commands;

import java.util.Arrays;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.pocketkid2.survivalgames.Arena;
import com.github.pocketkid2.survivalgames.Messages;
import com.github.pocketkid2.survivalgames.SurvivalGamesPlugin;
import com.github.pocketkid2.survivalgames.Values;

public class CreateCommand extends SubCommand {

	public CreateCommand(SurvivalGamesPlugin pl) {
		super(
				pl,
				2,
				2,
				Arrays.asList("create"),
				"<name> <radius>",
				"Creates a new map with the given name and given square radius from your current location",
				"survivalgames.create");
	}

	@Override
	public boolean execute(CommandSender sender, String[] arguments) {
		// Create a new arena at the player's location with the given name
		if (sender instanceof Player) {

			Player player = (Player) sender;
			Location loc = player.getLocation();

			int radius;
			try {
				radius = Integer.parseInt(arguments[2]);
			} catch (Exception e) {
				return false;
			}
			if (radius < Values.MIN_RADIUS) {
				player.sendMessage(Messages.RADIUS_TOO_SMALL);
				return true;
			}
			if (radius > Values.MAX_RADIUS) {
				player.sendMessage(Messages.RADIUS_TOO_LARGE);
				return true;
			}

			String name = arguments[1];
			if (plugin.getGM().byName(name) != null) {
				player.sendMessage(Messages.MAP_ALREADY_EXISTS);
				return true;
			}

			plugin.getGM().addMap(new Arena(name, loc, radius));

			sender.sendMessage(Messages.CREATED_MAP(name, radius));
		} else {
			sender.sendMessage(Messages.MUST_BE_PLAYER);
		}
		return true;
	}

}
