package com.github.pocketkid2.survivalgames.config;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;

import com.github.pocketkid2.survivalgames.Arena;
import com.github.pocketkid2.survivalgames.SurvivalGamesPlugin;
import com.github.pocketkid2.survivalgames.Values;

public class SettingsManager {

	private SurvivalGamesPlugin plugin;
	private ConfigAccessor map_config;
	private ConfigAccessor item_config;
	private List<Material> allowedBlocks;
	private double autoStartThreshold;
	private int autoStartTimer;

	public SettingsManager(SurvivalGamesPlugin plugin) {
		this.plugin = plugin;
		plugin.saveDefaultConfig();

		map_config = new ConfigAccessor(plugin, "maps.yml");
		map_config.saveDefaultConfig();

		item_config = new ConfigAccessor(plugin, "items.yml");
		item_config.saveDefaultConfig();

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
		autoStartThreshold = plugin.getConfig().getDouble("global.auto-start.threshold");
		autoStartTimer = plugin.getConfig().getInt("global.auto-start.timer");
	}

	@SuppressWarnings("deprecation")
	public List<Material> getItemsInTier(int tier) {
		if ((tier >= Values.MIN_TIER) && (tier <= Values.MAX_TIER)) {
			List<Integer> ints = item_config.getConfig().getIntegerList(Integer.toString(tier));
			List<Material> mats = new ArrayList<Material>();
			for (Integer i : ints) {
				mats.add(Material.getMaterial(i));
			}
			return mats;
		} else {
			return null;
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
		return allowedBlocks;
	}

	public double getAutoStartThreshold() {
		return autoStartThreshold;
	}

	public int getAutoStartTimer() {
		return autoStartTimer;
	}

}
