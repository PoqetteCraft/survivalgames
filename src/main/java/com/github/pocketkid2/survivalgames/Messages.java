package com.github.pocketkid2.survivalgames;

import net.md_5.bungee.api.ChatColor;

public interface Messages {

	String MUST_BE_PLAYER = ChatColor.RED + "You must be a player!";
	String NO_PERM = ChatColor.RED + "You don't have permission for that!";
	String PLACEHOLDER = ChatColor.AQUA + "Placeholder message";
	String RADIUS_TOO_SMALL = ChatColor.RED + "That radius is too small!";
	String RADIUS_TOO_LARGE = ChatColor.RED + "That radius is too large!";
	String MAP_ALREADY_EXISTS = ChatColor.RED + "A map already exists with that name!";
	String MAP_DOESNT_EXIST = ChatColor.RED + "That map does not exist!";

	static String USAGE(String label, String sub, String args) {
		return String.format(ChatColor.AQUA + "/%s %s %s", label, sub, args);
	}

	static String CREATED_MAP(String name, int radius) {
		return String.format(ChatColor.AQUA + "Created map '%s' with radius %d from your current location", name,
				radius);
	}

	static String MAP_REMOVED(String name) {
		return String.format(ChatColor.AQUA + "Map '%s' deleted", name);
	}

	static String LIST_NUM_GAMES(int count) {
		return String.format(ChatColor.AQUA + "There are " + ChatColor.GOLD + "%s" + ChatColor.AQUA + " maps", count);
	}

	static String LIST_GAME_NAME(String name, int radius) {
		return String.format(
				ChatColor.AQUA + "Name: " + ChatColor.GREEN + "%s " + ChatColor.AQUA + "Size: " + ChatColor.BLUE + "%d",
				name, radius);
	}
}
