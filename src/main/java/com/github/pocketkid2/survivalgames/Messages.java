package com.github.pocketkid2.survivalgames;

import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginDescriptionFile;

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
	String INFO_HELP = ChatColor.AQUA + "Type " + ChatColor.DARK_AQUA + "/sg help" + ChatColor.AQUA
			+ " to see a list of commands";
	String COMMAND_NOT_FOUND = ChatColor.RED + "That command name is invalid!";
	String INCORRECT_COMMAND_USAGE = ChatColor.RED + "Incorrect command usage!";

	/*
	 * Number chat formatting
	 */
	static String NUMBER(String name) {
		return ChatColor.GOLD + name + ChatColor.AQUA;
	}

	/*
	 * Arena name chat formatting
	 */
	static String MAP(String name) {
		return ChatColor.BLUE + "" + ChatColor.ITALIC + name + ChatColor.AQUA;
	}

	/*
	 * Command chat formatting
	 */
	static String COMMAND(String name) {
		return ChatColor.DARK_AQUA + name + ChatColor.AQUA;
	}

	/*
	 * Player killed/leaves chat formatting
	 */
	static String KILLED(String name) {
		return ChatColor.YELLOW + name + ChatColor.AQUA;
	}

	/*
	 * Killer chat formatting
	 */
	static String KILLER(String name) {
		return ChatColor.GRAY + name + ChatColor.AQUA;
	}

	/*
	 * Winning player chat formatting
	 */
	static String WON(String name) {
		return ChatColor.GOLD + "" + ChatColor.BOLD + name + ChatColor.AQUA;
	}

	static String USAGE(String label, String sub, String args) {
		return String.format(COMMAND("/%s %s %s"), label, sub, args);
	}

	static String CREATED_MAP(String name, int radius) {
		return String.format(ChatColor.AQUA + "Created arena " + MAP("%s") + " with radius " + NUMBER("%d")
				+ " from your current location", name, radius);
	}

	static String MAP_REMOVED(String name) {
		return String.format(ChatColor.AQUA + "Arena " + MAP("%s") + " deleted", name);
	}

	static String LIST_NUM_GAMES(int count) {
		return String.format(ChatColor.AQUA + "There are " + NUMBER("%d") + " maps", count);
	}

	static String LIST_GAME_NAME(String name, Status status) {
		return String.format(ChatColor.AQUA + "Name: " + MAP("%s") + " Status: %s", name, status.getReadable());
	}

	static String JOINED_GAME(String name) {
		return String.format(ChatColor.AQUA + "Joined game " + MAP("%s"), name);
	}

	static String[] MAP_INFO(Game g) {
		return new String[] { String.format(ChatColor.AQUA + "Name: " + MAP("%s"), g.getMap().getName()),
				String.format(ChatColor.AQUA + "Radius: " + NUMBER("%d"), g.getMap().getRadius()),
				String.format(ChatColor.AQUA + "Status: %s", g.getStatus().getReadable()),
				String.format(ChatColor.AQUA + "Players: " + ChatColor.LIGHT_PURPLE + "%d/%d", g.getAlive().size(),
						g.getMap().getSpawns().size()) };
	}

	static String GAME_STARTED(String name) {
		return String.format(ChatColor.AQUA + "Game " + MAP("%s") + " has been started!", name);
	}

	static String GAME_STOPPED(String name) {
		return String.format(ChatColor.AQUA + "Game " + MAP("%s") + ChatColor.AQUA + " has been stopped!", name);
	}

	static String GAME_STARTING_IN(int seconds) {
		return String.format(ChatColor.AQUA + "Game will be starting in " + NUMBER("%d") + " seconds!", seconds);
	}

	static String PLAYER_LEFT_GAME(String name) {
		return String.format(ChatColor.AQUA + "Player " + KILLED("%s") + " left the game!", name);
	}

	static String KILLED_BY(String name) {
		return String.format(ChatColor.AQUA + "You were killed by " + KILLER("%s"), name);
	}

	static String PLAYER_KILLED(String name, String name2) {
		return String.format(KILLED("%s") + " was killed by " + KILLER("%s"), name, name2);
	}

	static String X_PLAYERS_LEFT(int size) {
		return String.format("There are " + NUMBER("%d") + " players left!", size);
	}

	static String PLAYER_HAS_WON(String name, String name2) {
		return String.format(WON("%s") + " has won the Survival Games on map " + MAP("%s"), name, name2);
	}

	static String INFO_PLUGIN_1(PluginDescriptionFile description) {
		return String.format(ChatColor.AQUA + "%s version %s", description.getName(), description.getVersion());
	}

	static String INFO_PLUGIN_2(PluginDescriptionFile description) {
		return String.format(ChatColor.AQUA + "Author: %s", description.getAuthors().get(0));
	}

	static String INFO_PLUGIN_3(GameManager gm) {
		return String.format(ChatColor.AQUA + "There are " + NUMBER("%d") + " maps loaded", gm.allGames().size());
	}

	static String COMMAND_LIST(int size) {
		return String.format(ChatColor.AQUA + "There are " + NUMBER("%d") + " commands", size);
	}

	static String COMMAND_HELP_FOR(String alias) {
		return String.format(ChatColor.AQUA + "Command help for " + COMMAND("/sg %s"), alias);
	}

	static String MAP_HAS_PLAYERS(String name, int size) {
		return String.format(ChatColor.AQUA + "Map " + MAP("%s") + " currently has " + NUMBER("%d") + " players", name,
				size);
	}
}
