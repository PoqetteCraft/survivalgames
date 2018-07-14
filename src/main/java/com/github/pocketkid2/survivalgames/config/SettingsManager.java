package com.github.pocketkid2.survivalgames.config;

import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;

import com.github.pocketkid2.survivalgames.Map;
import com.github.pocketkid2.survivalgames.SurvivalGamesPlugin;

public class SettingsManager {

	private ConfigAccessor map_config;
	private FileConfiguration main_config;

	public SettingsManager(SurvivalGamesPlugin plugin, FileConfiguration config) {
		main_config = config;
		map_config = new ConfigAccessor(plugin, "maps.yml");
		map_config.saveDefaultConfig();
	}

	@SuppressWarnings("unchecked")
	public List<Map> loadAllMaps() {
		// Grab changes from disk
		map_config.reloadConfig();
		// Read list
		return (List<Map>) map_config.getConfig().getList("all-maps");
	}

	public void saveAllMaps(List<Map> arenas) {
		// Save list
		map_config.getConfig().set("all-maps", arenas);
		// Save changes to disk
		map_config.saveConfig();
	}

}
