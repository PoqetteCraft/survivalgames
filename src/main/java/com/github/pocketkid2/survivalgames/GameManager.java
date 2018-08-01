package com.github.pocketkid2.survivalgames;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;

/*
 * Manages all operating and loaded games
 */
public class GameManager {

	private SurvivalGamesPlugin plugin;
	private List<Game> games;

	/**
	 * Initializes the manager, loads all maps, and prepares them for operation
	 *
	 * @param plugin
	 * @param sm
	 */
	public GameManager(SurvivalGamesPlugin plugin) {
		this.plugin = plugin;

		plugin.getSM().getMapConfig().reloadConfig();
		// Read list
		@SuppressWarnings("unchecked")
		List<Arena> arenas = (List<Arena>) plugin.getSM().getMapConfig().getConfig().getList("all-maps", new ArrayList<Arena>());
		games = arenas.stream().map(a -> new Game(plugin, this, a)).collect(Collectors.toList());

		// Notify how many arenas were loaded from file
		plugin.getLogger().info("Loaded " + games.size() + " maps");

	}

	/**
	 * Stops all maps, saves them to disk
	 *
	 * @param sm
	 */
	public void shutdown() {
		// Stop all games peacefully
		for (Game game : games) {
			game.stop();
		}
		plugin.getLogger().info("Stopped " + games.size() + " maps");
		plugin.getSM().getMapConfig().getConfig().set("all-maps", games.stream().map(g -> g.getMap()).collect(Collectors.toList()));
		plugin.getSM().getMapConfig().saveConfig();

	}

	/**
	 * Creates a new game with the given map
	 *
	 * @param arena
	 */
	public void addMap(Arena arena) {
		games.add(new Game(plugin, this, arena));
	}

	/**
	 * Removes a game from operation
	 *
	 * @param game
	 */
	public void removeGame(Game game) {
		if (games.contains(game)) {
			game.stop();
			games.remove(game);
		}
	}

	/**
	 * Retrieves all games
	 *
	 * @return
	 */
	public List<Game> allGames() {
		return games;
	}

	/**
	 * Finds a game (if it exists) by it's name
	 *
	 * @param name
	 * @return
	 */
	public Game byName(String name) {
		for (Game game : games) {
			if (game.getMap().getName().equalsIgnoreCase(name)) {
				return game;
			}
		}
		return null;
	}

	/**
	 * Finds a game (if it exists) by one of it's players
	 *
	 * @param player
	 * @return
	 */
	public Game byPlayer(Player player) {
		for (Game game : games) {
			if (game.isInGame(player)) {
				return game;
			}
		}
		return null;
	}

	/**
	 * Checks whether this player is in any game
	 *
	 * @param player
	 * @return True if the player is in a game, false if not
	 */
	public boolean isInGame(Player player) {
		for (Game game : games) {
			if (game.isInGame(player)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns true if this block is inside an arena
	 *
	 * @param block
	 * @return
	 */
	public boolean isInGame(Block block) {
		for (Game game : games) {
			if (game.getMap().contains(block.getLocation())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns the game that contains this block
	 *
	 * @param block
	 * @return
	 */
	public Game byBlock(Block block) {
		for (Game game : games) {
			if (game.getMap().contains(block.getLocation())) {
				return game;
			}
		}
		return null;
	}

}
