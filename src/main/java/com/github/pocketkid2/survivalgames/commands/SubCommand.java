package com.github.pocketkid2.survivalgames.commands;

import java.util.List;

import org.bukkit.command.CommandSender;

import com.github.pocketkid2.survivalgames.SurvivalGamesPlugin;

public abstract class SubCommand {

	protected final SurvivalGamesPlugin plugin;
	protected final int minArguments;
	protected final int maxArguments;
	protected final List<String> aliases;
	protected final String usage;
	protected final String desc;
	protected final String perm;

	protected SubCommand(SurvivalGamesPlugin pl, int min, int max, List<String> a, String u, String d, String p) {
		plugin = pl;
		minArguments = min;
		maxArguments = max;
		aliases = a;
		usage = u;
		desc = d;
		perm = p;
	}

	public abstract boolean execute(CommandSender sender, String[] arguments);

	public int getMinArguments() {
		return minArguments;
	}

	public int getMaxArguments() {
		return maxArguments;
	}

	public List<String> getAliases() {
		return aliases;
	}

	public String getUsage() {
		return usage;
	}

	public String getDesc() {
		return desc;
	}

	public String getPermission() {
		return perm;
	}
}
