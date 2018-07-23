package com.github.pocketkid2.survivalgames.commands;

import java.util.Arrays;

import org.bukkit.command.CommandSender;

import com.github.pocketkid2.survivalgames.Game;
import com.github.pocketkid2.survivalgames.Messages;
import com.github.pocketkid2.survivalgames.SurvivalGamesPlugin;

public class InfoCommand extends SubCommand {

	public InfoCommand(SurvivalGamesPlugin pl) {
		super(pl, 1, 1, Arrays.asList("info"), "<name>", "Shows information about a map", "info");
	}

	@Override
	public boolean execute(CommandSender sender, String[] arguments) {
		Game g = plugin.getGM().byName(arguments[1]);
		if (g == null) {
			sender.sendMessage(Messages.MAP_DOESNT_EXIST);
		} else {
			sender.sendMessage(Messages.MAP_INFO(g));
		}
		return true;
	}

}
