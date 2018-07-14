package com.github.pocketkid2.survivalgames;

import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.pocketkid2.survivalgames.commands.BaseCommand;
import com.github.pocketkid2.survivalgames.config.SettingsManager;

public class SurvivalGamesPlugin extends JavaPlugin {

	private SettingsManager sm;
	private GameManager gm;

	@Override
	public void onEnable() {
		// Configuration stuff
		ConfigurationSerialization.registerClass(Map.class, "arena");
		saveDefaultConfig();
		sm = new SettingsManager(this, getConfig());

		// Initialize the manager
		gm = new GameManager(this, sm);

		getLogger().info("Loaded " + gm.allGames().size() + " maps");

		// Command stuff
		getCommand("survivalgames").setExecutor(new BaseCommand(this));

		getLogger().info("Done!");
	}

	@Override
	public void onDisable() {
		// Save configuration stuff
		gm.shutdown(sm);

		getLogger().info("Done!");
	}

	public SettingsManager getSM() {
		return sm;
	}

	public GameManager getGM() {
		return gm;
	}
}
