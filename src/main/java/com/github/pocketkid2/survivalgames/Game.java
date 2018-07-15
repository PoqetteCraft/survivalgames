package com.github.pocketkid2.survivalgames;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

// Represents an automated game
public class Game {

	public enum Status {

		DISABLED(ChatColor.DARK_RED + "Disabled"),
		RESETTING(ChatColor.AQUA + "Resetting arena"),
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

	private class SaveData {

		private ItemStack[] contents;
		private Location loc;

		public SaveData(PlayerInventory inv, Location loc) {
			contents = inv.getContents();
			this.loc = loc;
		}

		public ItemStack[] getInvContents() {
			return contents;
		}

		public Location getLoc() {
			return loc;
		}

	}

	// Reference to manager
	private GameManager gm;

	// Each game has a status
	private Status status;

	// Each game has a arena
	private Arena arena;

	// Each game has a list of alive/active players
	private Map<Player, SaveData> activePlayers;

	// Each game has a list of dead/offline players
	private List<OfflinePlayer> inactivePlayers;

	// Initialization constructor
	public Game(GameManager gameManager, Arena m) {
		gm = gameManager;
		status = Status.WAITING;
		arena = m;
		activePlayers = new HashMap<Player, SaveData>();
		inactivePlayers = new ArrayList<OfflinePlayer>();
	}

	public Set<Player> getAlive() {
		return activePlayers.keySet();
	}

	public Status getStatus() {
		return status;
	}

	public Arena getMap() {
		return arena;
	}

	public boolean isInGame(Player p) {
		return activePlayers.containsKey(p);
	}

	public void join(Player p) {
		// First determine if the player is open
		if (!gm.isInGame(p)) {
			// Next make sure the arena is available to join
			switch (status) {
			case DISABLED:
				p.sendMessage(Messages.GAME_UNAVAILABLE);
				break;
			case IN_GAME:
				p.sendMessage(Messages.GAME_ALREADY_STARTED);
				break;
			case RESETTING:
				p.sendMessage(Messages.GAME_RESETTING);
				break;
			case STARTING:
			case WAITING:
				// Check for room
				if (activePlayers.size() < arena.getSpawns().size()) {
					Location spawn = arena.getSpawnByIndex(activePlayers.size());
					// Grab them and their previous location
					activePlayers.put(p, new SaveData(p.getInventory(), p.getLocation()));
					// Find that spawn
					p.teleport(spawn);
					// Tell the player
					p.sendMessage(Messages.JOINED_GAME(arena.getName()));
				} else {
					p.sendMessage(Messages.GAME_FULL);
				}
				break;
			}
		} else {
			p.sendMessage(Messages.ALREADY_IN_GAME);
		}
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

	/**
	 * A player voluntarily exiting the match
	 *
	 * @param player
	 */
	public void leave(Player player) {
		if (activePlayers.containsKey(player)) {
			SaveData sd = activePlayers.get(player);
			activePlayers.remove(player);
			player.teleport(sd.getLoc());
			player.getInventory().setContents(sd.getInvContents());
			inactivePlayers.add(player);
			player.sendMessage(Messages.LEFT_GAME);
		}
	}
}
