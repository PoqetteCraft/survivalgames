package com.github.pocketkid2.survivalgames;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

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
		private GameMode gm;

		public SaveData(Player player) {
			contents = player.getInventory().getContents();
			loc = player.getLocation();
			gm = player.getGameMode();
		}

		public void restore(Player player) {
			player.teleport(loc);
			player.setGameMode(gm);
			player.getInventory().setContents(contents);
		}

		public ItemStack[] getInvContents() {
			return contents;
		}

		public Location getLoc() {
			return loc;
		}

		public GameMode getGameMode() {
			return gm;
		}

	}

	private SurvivalGamesPlugin plugin;

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
	public Game(SurvivalGamesPlugin plugin, GameManager gameManager, Arena m) {
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
					activePlayers.put(p, new SaveData(p));
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
		// Reset players
		for (Player p : activePlayers.keySet()) {
			leave(p, true, null);
		}
		// TODO reset blocks
		status = Status.WAITING;
	}

	/**
	 * Player leaves the game, voluntary or not
	 *
	 * @param player    The player
	 * @param voluntary True if they were not killed, false if they were killed
	 * @param killer    If killed, who did it
	 */
	public void leave(Player player, boolean voluntary, Player killer) {
		if (activePlayers.containsKey(player)) {
			SaveData sd = activePlayers.get(player);
			sd.restore(player);
			activePlayers.remove(player);
			inactivePlayers.add(player);

			if (voluntary) {
				player.sendMessage(Messages.LEFT_GAME);
				broadcast(Messages.PLAYER_LEFT_GAME(player.getName()));
			} else {
				player.sendMessage(Messages.KILLED_BY(killer.getName()));
				broadcast(Messages.PLAYER_KILLED(player.getName(), killer.getName()));
			}

			if (activePlayers.size() > 1) {
				// Broadcast current count
				broadcast(Messages.X_PLAYERS_LEFT(activePlayers.size()));
			} else {
				// The game is over
				endGame();
			}

		}
	}

	private void endGame() {
		if (activePlayers.size() == 1) {
			Player player = (Player) activePlayers.keySet().toArray()[0];
			SaveData sd = activePlayers.get(player);
			sd.restore(player);
			activePlayers.clear();
			inactivePlayers.clear();
			player.sendMessage(Messages.YOU_HAVE_WON);
			plugin.getServer().broadcastMessage(Messages.PLAYER_HAS_WON(player.getName(), arena.getName()));
			stop();
		}
	}

	private void broadcast(String message) {
		for (Player p : activePlayers.keySet()) {
			p.sendMessage(message);
		}
	}

	private class CountdownTask extends BukkitRunnable {

		private int seconds;
		private Game g;

		CountdownTask(Game g, int seconds) {
			this.seconds = seconds;
		}

		@Override
		public void run() {
			if (seconds > 0) {
				g.broadcast(Messages.GAME_STARTING_IN(seconds));
				seconds--;
			} else {
				g.broadcast(Messages.GAMES_BEGIN);
				g.start();
				cancel();
			}
		}

	}

	public void countdown(int seconds) {
		if (status == Status.WAITING) {
			status = Status.STARTING;
			new CountdownTask(this, seconds).runTaskTimer(plugin, 0, 20);
		}
	}

	/**
	 * Starts the game
	 */
	public void start() {
		if (status == Status.STARTING) {
			status = Status.IN_GAME;
		}
	}
}
