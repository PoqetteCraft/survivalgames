package com.github.pocketkid2.survivalgames;

import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginDescriptionFile;

import com.github.pocketkid2.survivalgames.Game.Status;

public interface Messages {

	String MUST_BE_PLAYER = ERROR() + "You must be a player!";
	String NO_PERM = ERROR() + "You don't have permission for that!";
	String PLACEHOLDER = INFO() + "Placeholder message";
	String RADIUS_TOO_SMALL = ERROR() + "That radius is too small!";
	String RADIUS_TOO_LARGE = ERROR() + "That radius is too large!";
	String MAP_ALREADY_EXISTS = ERROR() + "A map already exists with that name!";
	String MAP_DOESNT_EXIST = ERROR() + "That map does not exist!";
	String ALREADY_IN_GAME = ERROR() + "You are already in a game!";
	String GAME_UNAVAILABLE = ERROR() + "Sorry, that game is currently unavailable!";
	String GAME_ALREADY_STARTED = ERROR() + "Sorry, that game has already started!";
	String GAME_RESETTING = ERROR() + "Please wait a moment while the arena resets!";
	String GAME_FULL = ERROR() + "Sorry, that game is full!";
	String NOT_IN_GAME = ERROR() + "You are not in a game!";
	String LEFT_GAME = INFO() + "You have left the game!";
	String GAMES_BEGIN = INFO() + "Let the games begin!";
	String YOU_HAVE_WON = INFO() + "You have won the Survival Games!";
	String INFO_HELP = INFO() + "Type " + COMMAND("/sg help") + " to see a list of commands";
	String COMMAND_NOT_FOUND = ERROR() + "That command name is invalid!";
	String INCORRECT_COMMAND_USAGE = ERROR() + "Incorrect command usage!";

	/*
	 * Number chat formatting
	 */
	static String NUMBER(int n) {
		return ChatColor.GOLD + "" + n + INFO();
	}

	/*
	 * Arena name chat formatting
	 */
	static String MAP(String name) {
		return ChatColor.BLUE + "" + ChatColor.ITALIC + name + INFO();
	}

	/*
	 * Command chat formatting
	 */
	static String COMMAND(String name) {
		return ChatColor.DARK_AQUA + name + INFO();
	}

	/*
	 * Player killed/leaves chat formatting
	 */
	static String KILLED(String name) {
		return ChatColor.YELLOW + name + INFO();
	}

	/*
	 * Killer chat formatting
	 */
	static String KILLER(String name) {
		return ChatColor.GRAY + name + INFO();
	}

	/*
	 * Winning player chat formatting
	 */
	static String WON(String name) {
		return ChatColor.GOLD + "" + ChatColor.BOLD + name + INFO();
	}

	/*
	 * Standard command color
	 */
	static String INFO() {
		return ChatColor.AQUA + "";
	}

	/*
	 * Standard error color
	 */
	static String ERROR() {
		return ChatColor.RED + "";
	}

	static String USAGE(String label, String sub, String args) {
		return String.format(COMMAND("/%s %s %s"), label, sub, args);
	}

	static String CREATED_MAP(String name, int radius) {
		return INFO() + "Created arena " + MAP(name) + " with radius " + NUMBER(radius) + " from your current location";
	}

	static String MAP_REMOVED(String name) {
		return INFO() + "Arena " + MAP(name) + " deleted";
	}

	static String LIST_NUM_GAMES(int count) {
		return INFO() + "There are " + NUMBER(count) + " maps";
	}

	static String LIST_GAME_NAME(String name, Status status) {
		return INFO() + "Name: " + MAP(name) + " Status: " + status.getReadable();
	}

	static String JOINED_GAME(String name) {
		return INFO() + "Joined game " + MAP(name);
	}

	static String[] MAP_INFO(Game g) {
		return new String[] { INFO() + "Name: " + MAP(g.getMap().getName()),
				INFO() + "Radius: " + NUMBER(g.getMap().getRadius()), INFO() + "Status: " + g.getStatus().getReadable(),
				INFO() + "Players: " + ChatColor.LIGHT_PURPLE + g.getAlive().size() + "/"
						+ g.getMap().getSpawns().size() };
	}

	static String GAME_STARTED(String name) {
		return INFO() + "Game " + MAP(name) + " has been started!";
	}

	static String GAME_STOPPED(String name) {
		return INFO() + "Game " + MAP(name) + " has been stopped!";
	}

	static String GAME_STARTING_IN(int seconds) {
		return INFO() + "Game will be starting in " + NUMBER(seconds) + " seconds!";
	}

	static String PLAYER_LEFT_GAME(String name) {
		return INFO() + "Player " + KILLED(name) + " left the game!";
	}

	static String KILLED_BY(String name) {
		return INFO() + "You were killed by " + KILLER(name);
	}

	static String PLAYER_KILLED(String killed, String killer) {
		return KILLED(killed) + " was killed by " + KILLER(killer);
	}

	static String X_PLAYERS_LEFT(int size) {
		return "There are " + NUMBER(size) + " players left!";
	}

	static String PLAYER_HAS_WON(String player, String map) {
		return WON(player) + " has won the Survival Games on map " + MAP(map);
	}

	static String INFO_PLUGIN_1(PluginDescriptionFile description) {
		return INFO() + description.getName() + " version " + description.getVersion();
	}

	static String INFO_PLUGIN_2(PluginDescriptionFile description) {
		return INFO() + "Author: " + description.getAuthors().get(0);
	}

	static String INFO_PLUGIN_3(GameManager gm) {
		return INFO() + "There are " + NUMBER(gm.allGames().size()) + " maps loaded";
	}

	static String COMMAND_LIST(int size) {
		return INFO() + "There are " + NUMBER(size) + " commands";
	}

	static String COMMAND_HELP_FOR(String alias) {
		return INFO() + "Command help for " + COMMAND("/sg " + alias);
	}

	static String MAP_HAS_PLAYERS(String name, int size) {
		return INFO() + "Map " + MAP(name) + " currently has " + NUMBER(size) + " players";
	}

	static String SPAWN_ADDED(int size) {
		return INFO() + "Added spawn " + NUMBER(size);
	}
}
