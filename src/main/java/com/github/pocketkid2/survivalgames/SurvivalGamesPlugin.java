package com.github.pocketkid2.survivalgames;

import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.github.pocketkid2.survivalgames.commands.BaseCommand;
import com.github.pocketkid2.survivalgames.config.SettingsManager;
import com.github.pocketkid2.survivalgames.listeners.BlockListener;
import com.github.pocketkid2.survivalgames.listeners.ChestListener;
import com.github.pocketkid2.survivalgames.listeners.DamageListener;
import com.github.pocketkid2.survivalgames.listeners.GameListener;
import com.github.pocketkid2.survivalgames.listeners.MoveListener;
import com.github.pocketkid2.survivalgames.listeners.PlayerListener;
import com.github.pocketkid2.survivalgames.listeners.SignListener;

public class SurvivalGamesPlugin extends JavaPlugin {

	private SettingsManager sm;
	private GameManager gm;
	private LobbyManager lm;

	@Override
	public void onEnable() {

		// Register serializable object
		ConfigurationSerialization.registerClass(Arena.class, "arena");

		// Initialize config manager
		sm = new SettingsManager(this);

		// Register base command
		getCommand("survivalgames").setExecutor(new BaseCommand(this));

		// Prepare loading task
		new LoadTask(this).runTaskLater(this, 20);

		getLogger().info("Done!");
	}

	@Override
	public void onDisable() {
		// Stop all games and save to file
		gm.shutdown();
		lm.shutdown();

		getLogger().info("Done!");
	}

	private class LoadTask extends BukkitRunnable {

		private SurvivalGamesPlugin plugin;

		public LoadTask(SurvivalGamesPlugin plugin) {
			this.plugin = plugin;
		}

		@Override
		public void run() {
			plugin.loadMaps();
			plugin.loadLobby();
			plugin.registerListeners();
			plugin.getLM().updateAllSigns();
		}

	}

	private void loadMaps() {
		// Initialize game manager
		gm = new GameManager(this);

	}

	private void registerListeners() {
		getServer().getPluginManager().registerEvents(new MoveListener(this), this);
		getServer().getPluginManager().registerEvents(new BlockListener(this), this);
		getServer().getPluginManager().registerEvents(new DamageListener(this), this);
		getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
		getServer().getPluginManager().registerEvents(new ChestListener(this), this);
		getServer().getPluginManager().registerEvents(new SignListener(this), this);
		getServer().getPluginManager().registerEvents(new GameListener(this), this);
	}

	private void loadLobby() {
		// Initialize lobby manager
		lm = new LobbyManager(this);

		getLogger().info(lm.getSpawn() == null ? "Did not find lobby spawn" : "Found lobby spawn");
		getLogger().info("Loaded " + lm.getSigns().size() + " signs");
	}

	public SettingsManager getSM() {
		return sm;
	}

	public GameManager getGM() {
		return gm;
	}

	public LobbyManager getLM() {
		return lm;
	}
}
