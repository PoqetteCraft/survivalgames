package com.github.pocketkid2.survivalgames.commands;

import java.util.Arrays;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.pocketkid2.survivalgames.Game;
import com.github.pocketkid2.survivalgames.Messages;
import com.github.pocketkid2.survivalgames.SurvivalGamesPlugin;

public class InfoCommand extends SubCommand {

	public InfoCommand(SurvivalGamesPlugin pl) {
		super(pl, 0, 1, Arrays.asList("info", "show"), "[map]", "Shows information about a map", "info");
	}

	@Override
	public boolean execute(CommandSender sender, String[] arguments) {

		if (arguments.length == 1) {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				if (plugin.getGM().isInGame(player)) {
					Game game = plugin.getGM().byPlayer(player);
					displayGameInfo(game, player);
					return true;
				}
			}
			return false;
		}

		Game game = plugin.getGM().byName(arguments[1]);
		if (game == null) {
			sender.sendMessage(Messages.MAP_DOESNT_EXIST);
		} else {
			displayGameInfo(game, sender);
		}

		return true;
	}

	private void displayGameInfo(Game game, CommandSender sender) {
		sender.sendMessage(Messages.MAP_INFO(game));
	}

}
