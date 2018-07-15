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
	String GAMES_BEGIN = ChatColor.AQUA + "Let the games begin!";
	String YOU_HAVE_WON = ChatColor.AQUA + "You have won the Survival Games!";

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

	static String GAME_STARTED(String name) {
		return String.format(ChatColor.AQUA + "Game " + ChatColor.GOLD + "%s" + ChatColor.AQUA + " has been started!",
				name);
	}

	static String GAME_STOPPED(String name) {
		return String.format(ChatColor.AQUA + "Game " + ChatColor.GOLD + "%s" + ChatColor.AQUA + " has been stopped!",
				name);
	}

	static String GAME_STARTING_IN(int seconds) {
		return String.format(
				ChatColor.AQUA + "Game will be starting in " + ChatColor.GOLD + "%d" + ChatColor.AQUA + " seconds!",
				seconds);
	}

	static String PLAYER_LEFT_GAME(String name) {
		return String.format(ChatColor.AQUA + "Player " + ChatColor.YELLOW + "%s" + ChatColor.AQUA + " left the game!",
				name);
	}

	static String KILLED_BY(String name) {
		return String.format(ChatColor.AQUA + "You were killed by " + ChatColor.RED + "%s", name);
	}

	static String PLAYER_KILLED(String name, String name2) {
		return String.format(ChatColor.YELLOW + "%s" + ChatColor.AQUA + " was killed by " + ChatColor.RED + "%s", name,
				name2);
	}

	static String X_PLAYERS_LEFT(int size) {
		return String.format(ChatColor.GOLD + "%d" + ChatColor.AQUA + " players left!", size);
	}

	static String PLAYER_HAS_WON(String name, String name2) {
		return String.format(ChatColor.GOLD + "" + ChatColor.BOLD + "%s" + ChatColor.AQUA
				+ " has won the Survival Games on map " + ChatColor.BLUE + ChatColor.ITALIC + "%s", name, name2);
	}
}
