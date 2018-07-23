package com.github.pocketkid2.survivalgames.commands;

import java.util.Arrays;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.pocketkid2.survivalgames.Game;
import com.github.pocketkid2.survivalgames.Messages;
import com.github.pocketkid2.survivalgames.SurvivalGamesPlugin;

public class TeleportCommand extends SubCommand {

	protected TeleportCommand(SurvivalGamesPlugin pl) {
		super(pl,
				1,
				1,
				Arrays.asList("teleport", "tp"),
				"<name>",
				"Teleport to the center of an arena",
				"teleport");
	}

	@Override
	public boolean execute(CommandSender sender, String[] arguments) {
		// Grab the game
		Game game = plugin.getGM().byName(arguments[1]);

		// Make sure it exists
		if (game == null) {
			sender.sendMessage(Messages.MAP_DOESNT_EXIST);
			return true;
		} else {
			// Make sure it's a player
			if (sender instanceof Player) {
				// Teleport them to the center
				Player player = (Player) sender;
				player.teleport(game.getMap().getCenter());
				return true;
			} else {
				sender.sendMessage(Messages.MUST_BE_PLAYER);
				return true;
			}
		}
	}

}
