package com.github.pocketkid2.survivalgames;

import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.github.pocketkid2.survivalgames.commands.BaseCommand;
import com.github.pocketkid2.survivalgames.config.SettingsManager;
import com.github.pocketkid2.survivalgames.listeners.BlockListener;
import com.github.pocketkid2.survivalgames.listeners.DamageListener;
import com.github.pocketkid2.survivalgames.listeners.MoveListener;
import com.github.pocketkid2.survivalgames.listeners.PlayerListener;

public class SurvivalGamesPlugin extends JavaPlugin {

	private SettingsManager sm;
	private GameManager gm;

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
		gm.shutdown(sm);

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
			plugin.registerListeners();
		}

	}

	private void loadMaps() {
		// Initialize game manager
		gm = new GameManager(this, sm);

		// Notify how many arenas were loaded from file
		getLogger().info("Loaded " + gm.allGames().size() + " maps");
	}

	private void registerListeners() {
		getServer().getPluginManager().registerEvents(new MoveListener(this), this);
		getServer().getPluginManager().registerEvents(new BlockListener(this), this);
		getServer().getPluginManager().registerEvents(new DamageListener(this), this);
		getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
	}

	// Getter
	public SettingsManager getSM() {
		return sm;
	}

	// Getter
	public GameManager getGM() {
		return gm;
	}
}
