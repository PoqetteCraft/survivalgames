package com.github.pocketkid2.survivalgames;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import com.github.pocketkid2.survivalgames.config.SettingsManager;

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
	public GameManager(SurvivalGamesPlugin plugin, SettingsManager sm) {
		this.plugin = plugin;

		games = new ArrayList<Game>();

		List<Arena> arenas = sm.loadAllMaps();

		for (Arena arena : arenas) {
			games.add(new Game(plugin, this, arena));
		}
	}

	/**
	 * Stops all maps, saves them to disk
	 *
	 * @param sm
	 */
	public void shutdown(SettingsManager sm) {
		// Stop all games peacefully
		for (Game game : games) {
			game.stop();
		}

		List<Arena> arenas = new ArrayList<Arena>();

		for (Game game : games) {
			arenas.add(game.getMap());
		}

		sm.saveAllMaps(arenas);
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
