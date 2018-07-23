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
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import com.github.pocketkid2.survivalgames.random.BinaryRandomCount;
import com.github.pocketkid2.survivalgames.random.RandomIntSet;

/**
 * Represents an automated game given a map loaded from disk (arena)
 *
 * @author Adam
 *
 */
public class Game {

	/**
	 * Represents the current state of the game (includes human readable form, for
	 * chat/console)
	 *
	 * @author Adam
	 *
	 */
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

	/*
	 * Used for representing all player data that needs to be restored after a match
	 */
	private class SaveData {

		private ItemStack[] contents;
		private Location loc;
		private GameMode gm;
		private boolean isFlying;

		// Constructs the save snapshot of the player at the current time
		public SaveData(Player player) {
			contents = player.getInventory().getContents();
			loc = player.getLocation();
			gm = player.getGameMode();
			isFlying = player.isFlying();
		}

		// Restores the player's data to the data held in this object
		public void restore(Player player) {
			player.teleport(loc);
			player.setGameMode(gm);
			player.getInventory().setContents(contents);
			player.setFlying(isFlying);
		}

	}

	// Required references and stuff
	private SurvivalGamesPlugin plugin;
	private GameManager gm;
	private Status status;
	private Arena arena;
	private Map<Player, SaveData> activePlayers;
	private List<OfflinePlayer> inactivePlayers;
	private List<BlockState> toReset;
	private List<Block> chests;
	private BinaryRandomCount brc;
	private RandomIntSet ris;

	// Initialization constructor
	public Game(SurvivalGamesPlugin plugin, GameManager gameManager, Arena m) {
		this.plugin = plugin;
		gm = gameManager;
		status = Status.WAITING;
		arena = m;
		activePlayers = new HashMap<Player, SaveData>();
		inactivePlayers = new ArrayList<OfflinePlayer>();
		toReset = new ArrayList<BlockState>();
		chests = new ArrayList<Block>();
		brc = new BinaryRandomCount();
		ris = new RandomIntSet();
		log("Loaded!");
	}

	// Private logger
	private void log(String message) {
		plugin.getLogger().info(String.format("[%s] %s", getMap().getName(), message));
	}

	// Getter
	public Set<Player> getAlive() {
		return activePlayers.keySet();
	}

	// Getter
	public Status getStatus() {
		return status;
	}

	// Getter
	public Arena getMap() {
		return arena;
	}

	/**
	 * Checks if the player is in this game
	 *
	 * @param player
	 * @return true if player is in-game
	 */
	public boolean isInGame(Player player) {
		return activePlayers.containsKey(player);
	}

	/**
	 * Attempts to put the player in the game. Sends message on success/failure.
	 *
	 * @param player
	 */
	public void join(Player player) {
		// First determine if the player is open
		if (!gm.isInGame(player)) {
			// Next make sure the arena is available to join
			switch (status) {
			case DISABLED:
				player.sendMessage(Messages.GAME_UNAVAILABLE);
				break;
			case IN_GAME:
				player.sendMessage(Messages.GAME_ALREADY_STARTED);
				break;
			case RESETTING:
				player.sendMessage(Messages.GAME_RESETTING);
				break;
			case STARTING:
			case WAITING:
				// Check for room
				if (activePlayers.size() < arena.getSpawns().size()) {

					// Find an available spawn
					Location spawn = arena.getSpawnByIndex(activePlayers.size());

					// Save their data
					activePlayers.put(player, new SaveData(player));

					// Prepare them for the game
					player.teleport(spawn);
					prepare(player);
					player.sendMessage(Messages.JOINED_GAME(arena.getName()));

					// If we have enough players, start the game
					int current = activePlayers.size();
					int max = arena.getSpawns().size();
					double percentFull = (double) current / (double) max;
					if (percentFull >= plugin.getSM().getAutoStartThreshold()) {
						countdown(plugin.getSM().getAutoStartTimer());
					}
				} else {
					player.sendMessage(Messages.GAME_FULL);
				}
				break;
			}
		} else {
			player.sendMessage(Messages.ALREADY_IN_GAME);
		}
	}

	@SuppressWarnings("deprecation")
	private void prepare(Player player) {
		player.getInventory().clear();
		player.setHealth(player.getMaxHealth());
		player.setFoodLevel(20);
		player.setExhaustion(0.0f);
		player.setExp(0.0f);
		player.setGameMode(GameMode.SURVIVAL);
		// Probably much more
	}

	/**
	 * Stops the game peacefully and resets the arena
	 */
	public void stop() {
		switch (status) {
		case DISABLED:
		case RESETTING:
			break;
		case WAITING:
		case IN_GAME:
		case STARTING:
			// Reset players
			for (Player p : activePlayers.keySet()) {
				leave(p, true, "");
			}
			// Reset blocks
			for (BlockState state : toReset) {
				state.update(true, false);
			}
			// Reset chests
			chests.clear();
			// Clear both player lists
			activePlayers.clear();
			inactivePlayers.clear();
			status = Status.WAITING;
			log("Stopped game");
			break;
		}
	}

	/**
	 * Player leaves the game, voluntary or not
	 *
	 * @param player    The player
	 * @param voluntary True if they were not killed, false if they were killed
	 * @param killer    If killed, who did it
	 */
	public void leave(Player player, boolean voluntary, String killer) {
		// Player must actually be playing
		if (activePlayers.containsKey(player)) {

			// Grab his save data and restore it
			SaveData sd = activePlayers.get(player);
			sd.restore(player);

			// Move from active to inactive players
			activePlayers.remove(player);

			// Do all this stuff if the game was actually started
			if (getStatus() == Status.IN_GAME) {
				inactivePlayers.add(player);

				// If he died or left
				if (voluntary) {
					if (activePlayers.size() > 1) {
						player.sendMessage(Messages.LEFT_GAME);
						broadcast(Messages.PLAYER_LEFT_GAME(player.getName()));
					}
				} else {
					player.sendMessage(Messages.KILLED_BY(killer));
					broadcast(Messages.PLAYER_KILLED(player.getName(), killer));
				}

				// If the game is still going
				if (activePlayers.size() > 1) {
					// Broadcast current count
					broadcast(Messages.X_PLAYERS_LEFT(activePlayers.size()));
				} else {
					// The game is over
					endGame();
				}
			}
		}
	}

	private void endGame() {
		// Only end if there is one player left
		if (activePlayers.size() == 1) {

			// Grab that one player
			Player player = (Player) activePlayers.keySet().toArray()[0];

			// Grab his save data and restore
			SaveData sd = activePlayers.get(player);
			sd.restore(player);

			// Tell the player he won
			player.sendMessage(Messages.YOU_HAVE_WON);

			// Broadcast the win to the entire server
			plugin.getServer().broadcastMessage(Messages.PLAYER_HAS_WON(player.getName(), arena.getName()));

			// Reset the arena
			stop();
		}
	}

	/**
	 * Broadcast a message to all alive players
	 *
	 * @param message
	 */
	private void broadcast(String message) {
		for (Player player : activePlayers.keySet()) {
			player.sendMessage(message);
		}
	}

	/**
	 * Represents the countdown timer, which broadcasts every second
	 *
	 * @author Adam
	 *
	 */
	private class CountdownTask extends BukkitRunnable {

		private int seconds;
		private Game game;

		CountdownTask(Game game, int seconds) {
			this.game = game;
			this.seconds = seconds;
		}

		@Override
		public void run() {
			// TODO more advanced timer logic for when to broadcast etc
			if (seconds > 0) {
				game.broadcast(Messages.GAME_STARTING_IN(seconds));
				seconds--;
			} else {
				game.start();
				cancel();
			}
		}

	}

	/**
	 * Starts a game countdown. Changes state from WAITING to STARTING and
	 * broadcasts the countdown to all joined players. When the countdown reaches 0,
	 * the game starts.
	 *
	 * @param seconds
	 */
	public void countdown(int seconds) {
		if (status == Status.WAITING) {
			status = Status.STARTING;
			new CountdownTask(this, seconds).runTaskTimer(plugin, 0, 20);
			log("Started countdown of " + seconds + " seconds");
		}
	}

	/**
	 * Starts the game
	 */
	public void start() {
		if (status == Status.STARTING) {
			status = Status.IN_GAME;
			broadcast(Messages.GAMES_BEGIN);
			log("Game started");
		}
	}

	/**
	 * Adds this current block state to the list of blocks that need to be reset
	 *
	 * @param block
	 */
	public void queueBlock(Block block) {
		toReset.add(block.getState());
	}

	public List<Block> getChests() {
		return chests;
	}

	public void addChest(Block chest) {
		chests.add(chest);
	}

	public boolean isChest(Block chest) {
		return chests.contains(chest);
	}

	public void clearChests() {
		chests.clear();
	}

	/**
	 * @return the brc
	 */
	public BinaryRandomCount getBRC() {
		return brc;
	}

	/**
	 * @return the ris
	 */
	public RandomIntSet getRIS() {
		return ris;
	}
}
