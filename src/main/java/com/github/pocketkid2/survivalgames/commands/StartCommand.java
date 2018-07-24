package com.github.pocketkid2.survivalgames.commands;

import java.util.Arrays;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.pocketkid2.survivalgames.Game;
import com.github.pocketkid2.survivalgames.Messages;
import com.github.pocketkid2.survivalgames.SurvivalGamesPlugin;

public class StartCommand extends SubCommand {

	protected StartCommand(SurvivalGamesPlugin pl) {
		super(pl, 0, 1, Arrays.asList("start"), "[name]", "Starts either your game or the game specified", "start");
	}

	@Override
	public boolean execute(CommandSender sender, String[] arguments) {
		Game game;
		if (arguments.length == 1) {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				game = plugin.getGM().byPlayer(player);
				if (game == null) {
					player.sendMessage(Messages.NOT_IN_GAME);
					return true;
				}
			} else {
				return false;
			}
		} else {
			game = plugin.getGM().byName(arguments[1]);
			if (game == null) {
				sender.sendMessage(Messages.MAP_DOESNT_EXIST);
				return true;
			}
		}
		if (game.canStart()) {
			game.countdown(10);
			sender.sendMessage(Messages.GAME_STARTED(game.getMap().getName()));
		} else {
			sender.sendMessage(Messages.GAME_CANNOT_START);
		}
		return true;
	}

}
