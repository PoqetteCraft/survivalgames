package com.github.pocketkid2.survivalgames.commands;

import java.util.Arrays;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.pocketkid2.survivalgames.Game;
import com.github.pocketkid2.survivalgames.Messages;
import com.github.pocketkid2.survivalgames.SurvivalGamesPlugin;

public class JoinCommand extends SubCommand {

	public JoinCommand(SurvivalGamesPlugin pl) {
		super(
				pl,
				1,
				1,
				Arrays.asList("join"),
				"<name>",
				"Attempts to join the game with the given name",
				"survivalgames.join");
	}

	@Override
	public boolean execute(CommandSender sender, String[] arguments) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			Game g = plugin.getGM().byName(arguments[1]);
			if (g == null) {
				player.sendMessage(Messages.MAP_DOESNT_EXIST);
				return true;
			}
			g.join(player);
		} else {
			sender.sendMessage(Messages.MUST_BE_PLAYER);
		}
		return true;
	}

}
