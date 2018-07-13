package com.github.pocketkid2.survivalgames;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

// Represents an automated game
public class Game {

	public enum Status {

		DISABLED(ChatColor.DARK_RED + "Disabled"),
		RESETTING(ChatColor.AQUA + "Resetting map"),
		WAITING(ChatColor.GREEN + "Waiting for players"),
		STARTING(ChatColor.GOLD + "Counting down"),
		IN_GAME(ChatColor.RED + "In-game");

		private String readable;

		public String getReadable() {
			return readable;
		}

		Status(String s) {
			readable = s;
		}
	}

	// Each game has a status
	private Status status;

	// Each game has a map
	private Map map;

	// Each game has a list of alive/active players
	private List<Player> activePlayers;

	// Each game has a list of dead/offline players
	private List<OfflinePlayer> inactivePlayers;

	// Initialization constructor
	public Game(Map m) {
		map = m;
		activePlayers = new ArrayList<Player>();
		inactivePlayers = new ArrayList<OfflinePlayer>();
	}

	public Map getMap() {
		return map;
	}

	/**
	 * Stops the game peacefully and resets the arena
	 */
	public void stop() {
		status = Status.RESETTING;
		// TODO reset players
		// TODO reset blocks
		status = Status.WAITING;
	}
}
