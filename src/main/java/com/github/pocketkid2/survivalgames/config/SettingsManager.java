package com.github.pocketkid2.survivalgames.config;

import java.util.ArrayList;
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

	public List<Map> loadAllMaps() {
		List<String> names = main_config.getStringList("all-maps");

		List<Map> arenas = new ArrayList<Map>();
		for (String s : names) {
			arenas.add(map_config.getConfig().getSerializable(s, Map.class));
		}
		return arenas;
	}

	public void saveAllMaps(List<Map> arenas) {
		map_config.getConfig().set("all-maps", arenas);
		map_config.saveConfig();
	}

}
