package com.github.pocketkid2.survivalgames.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.github.pocketkid2.survivalgames.SurvivalGamesPlugin;

public class BaseCommand implements CommandExecutor {

	private SurvivalGamesPlugin plugin;

	public BaseCommand(SurvivalGamesPlugin plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		// TODO Auto-generated method stub
		return false;
	}

}
