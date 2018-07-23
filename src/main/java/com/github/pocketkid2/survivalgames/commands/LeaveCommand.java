package com.github.pocketkid2.survivalgames.commands;

import java.util.Arrays;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.pocketkid2.survivalgames.Game;
import com.github.pocketkid2.survivalgames.Messages;
import com.github.pocketkid2.survivalgames.SurvivalGamesPlugin;

public class LeaveCommand extends SubCommand {

	public LeaveCommand(SurvivalGamesPlugin pl) {
		super(pl, 0, 0, Arrays.asList("leave"), "", "Leaves the current game", "leave");
	}

	@Override
	public boolean execute(CommandSender sender, String[] arguments) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			// Check that the player is in a game
			Game g = plugin.getGM().byPlayer(player);
			if (g != null) {
				g.leave(player, true, "");
			} else {
				player.sendMessage(Messages.NOT_IN_GAME);
			}
		} else {
			sender.sendMessage(Messages.MUST_BE_PLAYER);
		}
		return true;
	}

}
