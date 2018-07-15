package com.github.pocketkid2.survivalgames.commands;

import java.util.Arrays;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.pocketkid2.survivalgames.Game;
import com.github.pocketkid2.survivalgames.Messages;
import com.github.pocketkid2.survivalgames.SurvivalGamesPlugin;

public class StartCommand extends SubCommand {

	protected StartCommand(SurvivalGamesPlugin pl) {
		super(
				pl,
				0,
				1,
				Arrays.asList("start"),
				"[name]",
				"Starts either your game or the game specified",
				"survivalgames.start");
	}

	@Override
	public boolean execute(CommandSender sender, String[] arguments) {
		Game g;
		if (arguments.length == 1) {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				g = plugin.getGM().byPlayer(player);
				if (g == null) {
					player.sendMessage(Messages.NOT_IN_GAME);
					return true;
				}
			} else {
				return false;
			}
		} else {
			g = plugin.getGM().byName(arguments[1]);
			if (g == null) {
				sender.sendMessage(Messages.MAP_DOESNT_EXIST);
				return true;
			}
		}
		g.start();
		sender.sendMessage(Messages.GAME_STARTED(g.getMap().getName()));
		return true;
	}

}
