package com.github.pocketkid2.survivalgames;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;

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
	String MAX_SPAWNS_REACHED = ERROR() + "Maximum spawn count reached for this arena!";
	String INVALID_SPAWN_INDEX = ERROR() + "Invalid spawn index!";
	String GAME_CANNOT_START = ERROR() + "That game cannot start now! Please check current status and player count";
	String CHESTS_HAVE_REFRESHED = INFO() + "Loot chests have been refreshed!";
	String GRACE_PERIOD_STARTED = INFO() + "Grace period has been started!";
	String GRACE_PERIOD_ENDED = INFO() + "Grace period has ended!";
	String NOT_ENOUGH_PLAYERS = ERROR() + "There are not enough players to start the game!";
	String LOBBY_SPAWN_SET = INFO() + "Lobby spawn has been set!";
	String SIGN_CREATED = INFO() + "Game sign created!";
	String SIGN_REMOVED = INFO() + "Game sign removed!";
	String LOBBY_SPAWN_NOT_SET = ERROR() + "Lobby spawn has not been set!";
	String LOBBY_SPAWN_TELEPORTED = INFO() + "Teleported you to the lobby!";
	String SPAWN_OUTSIDE_ARENA = ERROR() + "The spawn location must be inside the arena!";

	/*
	 * Number chat formatting
	 */
	static String NUMBER(int n) {
		return ChatColor.GOLD + "" + n + INFO();
	}

	/*
	 * Arena name chat formatting
	 */
	static String MAP(Arena arena) {
		return ChatColor.BLUE + "" + ChatColor.ITALIC + arena.getName() + INFO();
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

	static String ITEM(String name) {
		return ChatColor.LIGHT_PURPLE + name + INFO();
	}

	/*
	 * Winning player chat formatting
	 */
	static String WON(String name) {
		return ChatColor.GOLD + "" + ChatColor.BOLD + name + INFO();
	}

	static String ALIVE(String name) {
		return ChatColor.GREEN + name + INFO();
	}

	static String DEAD(String name) {
		return ChatColor.RED + name + INFO();
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

	static String CREATED_MAP(Arena arena) {
		return INFO() + "Created arena " + MAP(arena) + " with radius " + NUMBER(arena.getRadius()) + " from your current location";
	}

	static String MAP_REMOVED(Arena arena) {
		return INFO() + "Arena " + MAP(arena) + " deleted";
	}

	static String LIST_NUM_GAMES(int count) {
		return INFO() + "There are " + NUMBER(count) + " map" + ((count == 1) ? "" : "s");
	}

	static String JOINED_GAME(Arena arena) {
		return INFO() + "Joined game " + MAP(arena);
	}

	static String[] MAP_INFO(Game game) {
		return new String[] { INFO() + "Name: " + MAP(game.getMap()), INFO() + "Radius: " + NUMBER(game.getMap().getRadius()),
				INFO() + "Status: " + game.getStatus().getReadable(),
				INFO() + "Players: " + ChatColor.LIGHT_PURPLE + game.currCount() + "/" + game.maxCount() };
	}

	static String LIST_GAME_NAME(Game game) {
		return INFO() + "Name: " + MAP(game.getMap()) + " Status: " + game.getStatus().getReadable();
	}

	static String GAME_STARTED(Arena arena) {
		return INFO() + "Game " + MAP(arena) + " has been started!";
	}

	static String GAME_STOPPED(Arena arena) {
		return INFO() + "Game " + MAP(arena) + " has been stopped!";
	}

	static String GAME_STARTING_IN(int seconds) {
		return INFO() + "Game will be starting in " + NUMBER(seconds) + " second" + ((seconds == 1) ? ("") : ("s")) + "!";
	}

	static String PLAYER_LEFT_GAME(String name) {
		return INFO() + "Player " + KILLED(name) + " left the game!";
	}

	static String KILLED_BY(String name) {
		return INFO() + "You were killed by " + KILLER(name);
	}

	static String KILLED_WITH(String name, String item) {
		return KILLED_BY(name) + " with " + ITEM(item);
	}

	static String PLAYER_KILLED(String killed, String killer) {
		return KILLED(killed) + " was killed by " + KILLER(killer);
	}

	static String PLAYER_KILLED_WITH(String killed, String killer, String item) {
		return PLAYER_KILLED(killed, killer) + " with " + ITEM(item);
	}

	static String X_PLAYERS_LEFT(int size) {
		return "There are " + NUMBER(size) + " players left!";
	}

	static String PLAYER_HAS_WON(String player, Arena arena) {
		return WON(player) + " has won the Survival Games on map " + MAP(arena);
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

	static String MAP_HAS_PLAYERS(Arena arena, int size) {
		return INFO() + "Map " + MAP(arena) + " currently has " + NUMBER(size) + " players";
	}

	static String SPAWN_ADDED(int size, Arena arena) {
		return INFO() + "Added spawn " + NUMBER(size) + " to map " + MAP(arena);
	}

	static String SPAWN_UPDATED(int index, Arena arena) {
		return INFO() + "Update spawn " + NUMBER(index + 1);
	}

	static String ALIVE_PLAYERS(Set<Player> alive) {
		Set<String> names = alive.stream().map(p -> ALIVE(p.getName())).collect(Collectors.toSet());
		return INFO() + "Alive (" + NUMBER(alive.size()) + "): " + String.join(", ", names);
	}

	static String DEAD_PLAYERS(List<OfflinePlayer> deadOrLeft) {
		List<String> names = deadOrLeft.stream().map(p -> DEAD(p.getName())).collect(Collectors.toList());
		return INFO() + "Dead/Left (" + NUMBER(deadOrLeft.size()) + "): " + String.join(", ", names);
	}

	static String GRACE_PERIOD_LEFT(int seconds) {
		String message = "";
		if (seconds > 1) {
			message = INFO() + "Grace period will end in " + NUMBER(seconds) + " seconds!";
		} else if (seconds == 1) {
			message = INFO() + "Grace period will end in " + NUMBER(seconds) + " second!";
		}
		return message;
	}

}
