package com.github.pocketkid2.survivalgames;

import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;

public class SurvivalGamesPlugin extends JavaPlugin {

	private SettingsManager sm;
	private GameManager gm;

	@Override
	public void onEnable() {
		ConfigurationSerialization.registerClass(Map.class, "arena");
		saveDefaultConfig();
		sm = new SettingsManager(this, getConfig());
		gm = new GameManager(this, sm);
		getLogger().info("Done!");
	}

	@Override
	public void onDisable() {
		getLogger().info("Done!");
	}
}
