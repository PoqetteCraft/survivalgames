package com.github.pocketkid2.survivalgames;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
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
import org.bukkit.scheduler.BukkitTask;

import com.github.pocketkid2.survivalgames.random.BinaryRandomCount;
import com.github.pocketkid2.survivalgames.random.RandomIntSet;
import com.github.pocketkid2.survivalgames.tasks.ChestRefreshTask;
import com.github.pocketkid2.survivalgames.tasks.CountdownTask;
import com.github.pocketkid2.survivalgames.tasks.GracePeriodTask;

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
		@SuppressWarnings("deprecation")
		public void restore(Player player) {
			for (ItemStack is : player.getInventory().getContents()) {
				player.getWorld().dropItemNaturally(player.getLocation(), is);
			}
			player.teleport(loc);
			player.setGameMode(gm);
			player.getInventory().setContents(contents);
			player.setFlying(isFlying);
			player.setHealth(player.getMaxHealth());
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
	private List<BukkitTask> tasks;
	private boolean gracePeriod;

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
		tasks = new LinkedList<BukkitTask>();
		gracePeriod = false;
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

	public List<OfflinePlayer> getDeadOrLeft() {
		return inactivePlayers;
	}

	// Getter
	public Status getStatus() {
		return status;
	}

	// Getter
	public Arena getMap() {
		return arena;
	}

	public boolean canStart() {
		return (activePlayers.size() >= Values.MIN_PLAYERS) && (getStatus() == Status.WAITING);
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
			resetChests();
			// Clear both player lists
			activePlayers.clear();
			inactivePlayers.clear();
			// Reset to default status
			status = Status.WAITING;
			// Reset all tasks
			for (BukkitTask bt : tasks) {
				bt.cancel();
			}
			tasks.clear();
			// Reset grace period flag
			gracePeriod = false;
			log("Stopped game");
			break;
		}
	}

	/**
	 * Reset the chests
	 */
	public void resetChests() {
		chests.clear();
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
	public void broadcast(String message) {
		for (Player player : activePlayers.keySet()) {
			player.sendMessage(message);
		}
	}

	/**
	 * Starts a game countdown. Changes state from WAITING to STARTING and
	 * broadcasts the countdown to all joined players. When the countdown reaches 0,
	 * the game starts.
	 *
	 * @param seconds
	 */
	public boolean countdown(int seconds) {
		if (status == Status.WAITING) {
			if (activePlayers.size() < Values.MIN_PLAYERS) {
				log("Game could not start - Not enough players!");
				broadcast(Messages.NOT_ENOUGH_PLAYERS);
				return false;
			}
			status = Status.STARTING;
			tasks.add(new CountdownTask(seconds, this).runTaskTimer(plugin, 0, 20));
			log("Started countdown of " + seconds + " seconds");
			return true;
		} else {
			log("Game could not start - Not available to start!");
			return false;
		}
	}

	/**
	 * Starts the game
	 */
	public void start() {
		if (status == Status.STARTING) {
			if (activePlayers.size() < Values.MIN_PLAYERS) {
				log("Game could not start - Not enough players!");
				broadcast(Messages.NOT_ENOUGH_PLAYERS);
				stop();
				return;
			}
			status = Status.IN_GAME;
			// Check if grace period is enabled
			if (plugin.getSM().isGracePeriodEnabled()) {
				tasks.add(new GracePeriodTask(plugin.getSM().getGracePeriodTimer(), this).runTaskTimer(plugin, 0, 20));
			}
			// Check if chest refresh is enabled
			if (plugin.getSM().isChestRefreshEnabled()) {
				tasks.add(new ChestRefreshTask(plugin.getSM().getChestRefreshTimer(), this).runTaskTimer(plugin, 0, 20));
			}
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

	public boolean isGracePeriod() {
		return gracePeriod;
	}

	public void setGracePeriod(boolean gracePeriod) {
		this.gracePeriod = gracePeriod;
	}
}
