package com.github.pocketkid2.survivalgames;

import org.bukkit.ChatColor;

import com.github.pocketkid2.survivalgames.Game.Status;

public interface Messages {

	String MUST_BE_PLAYER = ChatColor.RED + "You must be a player!";
	String NO_PERM = ChatColor.RED + "You don't have permission for that!";
	String PLACEHOLDER = ChatColor.AQUA + "Placeholder message";
	String RADIUS_TOO_SMALL = ChatColor.RED + "That radius is too small!";
	String RADIUS_TOO_LARGE = ChatColor.RED + "That radius is too large!";
	String MAP_ALREADY_EXISTS = ChatColor.RED + "A map already exists with that name!";
	String MAP_DOESNT_EXIST = ChatColor.RED + "That map does not exist!";
	String ALREADY_IN_GAME = ChatColor.RED + "You are already in a game!";
	String GAME_UNAVAILABLE = ChatColor.RED + "Sorry, that game is currently unavailable!";
	String GAME_ALREADY_STARTED = ChatColor.RED + "Sorry, that game has already started!";
	String GAME_RESETTING = ChatColor.RED + "Please wait a moment while the arena resets!";
	String GAME_FULL = ChatColor.RED + "Sorry, that game is full!";
	String NOT_IN_GAME = ChatColor.RED + "You are not in a game!";
	String LEFT_GAME = ChatColor.AQUA + "You have left the game!";
	String SPAWN_ADDED = ChatColor.AQUA + "Spawn added!";

	static String USAGE(String label, String sub, String args) {
		return String.format(ChatColor.AQUA + "/%s %s %s", label, sub, args);
	}

	static String CREATED_MAP(String name, int radius) {
		return String.format(ChatColor.AQUA + "Created map " + ChatColor.GREEN + "%s" + ChatColor.AQUA + " with radius "
				+ ChatColor.BLUE + "%d" + ChatColor.AQUA + " from your current location", name, radius);
	}

	static String MAP_REMOVED(String name) {
		return String.format(ChatColor.AQUA + "Arena " + ChatColor.GREEN + "%s" + ChatColor.AQUA + " deleted", name);
	}

	static String LIST_NUM_GAMES(int count) {
		return String.format(ChatColor.AQUA + "There are " + ChatColor.GOLD + "%s" + ChatColor.AQUA + " maps", count);
	}

	static String LIST_GAME_NAME(String name, Status status) {
		return String.format(ChatColor.AQUA + "Name: " + ChatColor.BLUE + "%s" + ChatColor.AQUA + " Status: %s", name,
				status.getReadable());
	}

	static String JOINED_GAME(String name) {
		return String.format(ChatColor.AQUA + "Joined game " + ChatColor.GOLD + "%s", name);
	}

	static String[] MAP_INFO(Game g) {
		return new String[] { String.format(ChatColor.AQUA + "Name: " + ChatColor.BLUE + "%s", g.getMap().getName()),
				String.format(ChatColor.AQUA + "Radius: " + ChatColor.BLUE + "%d", g.getMap().getRadius()),
				String.format(ChatColor.AQUA + "Status: %s", g.getStatus().getReadable()),
				String.format(ChatColor.AQUA + "Players: " + ChatColor.LIGHT_PURPLE + "%d/%d", g.getAlive().size(),
						g.getMap().getSpawns().size()) };
	}
}
