package com.github.pocketkid2.survivalgames.commands;

import java.util.Arrays;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.pocketkid2.survivalgames.Messages;
import com.github.pocketkid2.survivalgames.SurvivalGamesPlugin;

public class LobbyCommand extends SubCommand {

	protected LobbyCommand(SurvivalGamesPlugin pl) {
		super(pl, 0, 0, Arrays.asList("lobby"), "", "Teleports you to the sg lobby if it exists", "lobby");
	}

	@Override
	public boolean execute(CommandSender sender, String[] arguments) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			Location loc = plugin.getLM().getSpawn();
			if (loc == null) {
				player.sendMessage(Messages.LOBBY_SPAWN_NOT_SET);
			} else {
				player.teleport(loc);
				player.sendMessage(Messages.LOBBY_SPAWN_TELEPORTED);
			}
		} else {
			sender.sendMessage(Messages.MUST_BE_PLAYER);
		}
		return true;
	}

}
