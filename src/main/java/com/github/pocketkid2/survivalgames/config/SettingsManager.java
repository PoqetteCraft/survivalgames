package com.github.pocketkid2.survivalgames.config;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.Material;

import com.github.pocketkid2.survivalgames.SurvivalGamesPlugin;
import com.github.pocketkid2.survivalgames.Values;

public class SettingsManager {

	private SurvivalGamesPlugin plugin;

	private ConfigAccessor map_config;
	private ConfigAccessor item_config;
	private ConfigAccessor lobby_config;

	private List<Material> allowedBlocks;
	private double autoStartThreshold;
	private int autoStartTimer;

	private boolean chestRefreshEnabled;
	private int chestRefreshTimer;

	private boolean gracePeriodEnabled;
	private int gracePeriodTimer;

	private int victoryTimer;

	public SettingsManager(SurvivalGamesPlugin plugin) {
		this.plugin = plugin;
		plugin.saveDefaultConfig();

		map_config = new ConfigAccessor(plugin, "maps.yml");
		map_config.saveDefaultConfig();

		item_config = new ConfigAccessor(plugin, "items.yml");
		item_config.saveDefaultConfig();

		lobby_config = new ConfigAccessor(plugin, "lobby.yml");
		lobby_config.saveDefaultConfig();

		loadGlobalSettings();
	}

	@SuppressWarnings("deprecation")
	private void loadGlobalSettings() {
		// Populate allowed blocks list
		List<Integer> ids = plugin.getConfig().getIntegerList("global.allowed-blocks");
		allowedBlocks = ids.stream().map(id -> Material.getMaterial(id)).collect(Collectors.toList());
		autoStartThreshold = plugin.getConfig().getDouble("global.auto-start.threshold");
		autoStartTimer = plugin.getConfig().getInt("global.auto-start.timer");
		// Populate chest refresh settings
		chestRefreshEnabled = plugin.getConfig().getBoolean("global.chest-refresh.enabled");
		chestRefreshTimer = plugin.getConfig().getInt("global.chest-refresh.timer");
		// Populate grace period settings
		gracePeriodEnabled = plugin.getConfig().getBoolean("global.grace-period.enabled");
		gracePeriodTimer = plugin.getConfig().getInt("global.grace-period.timer");
		// Grab victory timer
		victoryTimer = plugin.getConfig().getInt("global.victory-timer");
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

	public List<Material> allowedBlocks() {
		return allowedBlocks;
	}

	public double getAutoStartThreshold() {
		return autoStartThreshold;
	}

	public int getAutoStartTimer() {
		return autoStartTimer;
	}

	/**
	 * @return the chestRefreshEnabled
	 */
	public boolean isChestRefreshEnabled() {
		return chestRefreshEnabled;
	}

	/**
	 * @return the chestRefreshTimer
	 */
	public int getChestRefreshTimer() {
		return chestRefreshTimer;
	}

	/**
	 * @return the gracePeriodEnabled
	 */
	public boolean isGracePeriodEnabled() {
		return gracePeriodEnabled;
	}

	/**
	 * @return the gracePeriodTimer
	 */
	public int getGracePeriodTimer() {
		return gracePeriodTimer;
	}

	/**
	 * @return the victoryTimer
	 */
	public int getVictoryTimer() {
		return victoryTimer;
	}

	/**
	 * @return the map_config
	 */
	public ConfigAccessor getMapConfig() {
		return map_config;
	}

	/**
	 * @return the item_config
	 */
	public ConfigAccessor getItemConfig() {
		return item_config;
	}

	/**
	 * @return the lobby_config
	 */
	public ConfigAccessor getLobbyConfig() {
		return lobby_config;
	}

}
