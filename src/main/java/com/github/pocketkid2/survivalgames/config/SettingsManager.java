package com.github.pocketkid2.survivalgames.config;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;

import com.github.pocketkid2.survivalgames.Arena;
import com.github.pocketkid2.survivalgames.SurvivalGamesPlugin;

public class SettingsManager {

	private SurvivalGamesPlugin plugin;
	private ConfigAccessor map_config;
	private List<Material> allowedBlocks;

	public SettingsManager(SurvivalGamesPlugin plugin) {
		this.plugin = plugin;
		plugin.saveDefaultConfig();
		map_config = new ConfigAccessor(plugin, "maps.yml");
		map_config.saveDefaultConfig();

		loadGlobalSettings();
	}

	@SuppressWarnings("deprecation")
	private void loadGlobalSettings() {
		// Populate allowed blocks list
		List<Integer> ids = plugin.getConfig().getIntegerList("global.allowed-blocks");
		allowedBlocks = new ArrayList<Material>();
		for (Integer id : ids) {
			allowedBlocks.add(Material.getMaterial(id));
		}
	}

	/**
	 * Retrives all maps from disk
	 *
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Arena> loadAllMaps() {
		// Grab changes from disk
		map_config.reloadConfig();
		// Read list
		return (List<Arena>) map_config.getConfig().getList("all-maps", new ArrayList<Arena>());
	}

	/**
	 * Saves all the given maps to the disk
	 *
	 * @param arenas
	 */
	public void saveAllMaps(List<Arena> arenas) {
		// Save list
		map_config.getConfig().set("all-maps", arenas);
		// Save changes to disk
		map_config.saveConfig();
	}

	public List<Material> allowedBlocks() {
		return allowedBlocks();
	}

}
