package com.github.pocketkid2.survivalgames.commands;

import java.util.Arrays;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.pocketkid2.survivalgames.Messages;
import com.github.pocketkid2.survivalgames.SurvivalGamesPlugin;

public class SetLobbySpawnCommand extends SubCommand {

	protected SetLobbySpawnCommand(SurvivalGamesPlugin pl) {
		super(pl, 0, 0, Arrays.asList("setlobbyspawn"), "", "Sets the lobby spawn to your current location", "setlobbyspawn");
	}

	@Override
	public boolean execute(CommandSender sender, String[] arguments) {
		if (sender instanceof Player) {
			plugin.getLM().setSpawn(((Player) sender).getLocation());
			sender.sendMessage(Messages.LOBBY_SPAWN_SET);
		} else {
			sender.sendMessage(Messages.MUST_BE_PLAYER);
		}
		return true;
	}

}
