package com.github.pocketkid2.survivalgames.commands;

import java.util.Arrays;
import java.util.Set;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.pocketkid2.survivalgames.Game;
import com.github.pocketkid2.survivalgames.Game.Status;
import com.github.pocketkid2.survivalgames.Messages;
import com.github.pocketkid2.survivalgames.SurvivalGamesPlugin;

public class ListCommand extends SubCommand {

	public ListCommand(SurvivalGamesPlugin pl) {
		super(pl, 0, 1, Arrays.asList("list", "maps"), "[map]", "List all maps or players in a map", "list");
	}

	@Override
	public boolean execute(CommandSender sender, String[] arguments) {

		if (arguments.length == 2) {
			Game game = plugin.getGM().byName(arguments[1]);
			if (game == null) {
				sender.sendMessage(Messages.MAP_DOESNT_EXIST);
			} else {
				displayGameInfo(game, sender);
			}
		} else {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				if (plugin.getGM().isInGame(player)) {
					Game game = plugin.getGM().byPlayer(player);
					displayGameInfo(game, player);
					return true;
				}
			}
			int count = plugin.getGM().allGames().size();
			sender.sendMessage(Messages.LIST_NUM_GAMES(count));
			for (Game game : plugin.getGM().allGames()) {
				sender.sendMessage(Messages.LIST_GAME_NAME(game));
			}
		}

		return true;
	}

	private void displayGameInfo(Game game, CommandSender sender) {
		Set<Player> players = game.getAlive();
		sender.sendMessage(Messages.MAP_HAS_PLAYERS(game.getMap(), players.size()));
		if ((game.getStatus() == Status.IN_GAME) || (game.getStatus() == Status.WAITING) || (game.getStatus() == Status.STARTING)) {
			sender.sendMessage(Messages.ALIVE_PLAYERS(game.getAlive()));
			sender.sendMessage(Messages.DEAD_PLAYERS(game.getDead()));
		}
	}

}
