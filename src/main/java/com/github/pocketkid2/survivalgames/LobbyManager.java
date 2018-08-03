package com.github.pocketkid2.survivalgames;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class LobbyManager {

	private SurvivalGamesPlugin plugin;
	private Location spawn;
	private Map<SGSign, Game> signs;

	@SuppressWarnings("unchecked")
	public LobbyManager(SurvivalGamesPlugin pl) {
		plugin = pl;

		// Load config from disk
		plugin.getSM().getLobbyConfig().reloadConfig();

		// Load spawn location
		spawn = plugin.getSM().getLobbyConfig().getConfig().getSerializable("spawn", Location.class);

		// Load all sign locations
		List<Location> signLocations = (List<Location>) plugin.getSM().getLobbyConfig().getConfig().getList("signs",
				new LinkedList<Location>());

		// Initialize map
		signs = new HashMap<SGSign, Game>();

		// Turn them into actual signs
		List<SGSign> converted = signLocations.stream().map(l -> new SGSign(l.getBlock())).collect(Collectors.toList());
		for (SGSign sign : converted) {
			Game game = plugin.getGM().byName(sign.getGameName());
			if (sign.isValid() && (game != null)) {
				signs.put(sign, game);
			}
		}

	}

	public void shutdown() {
		// Save spawn location
		plugin.getSM().getLobbyConfig().getConfig().set("spawn", spawn);

		// Grab all sign locations
		List<Location> signLocations = signs.keySet().stream().map(s -> s.getLocation()).collect(Collectors.toList());

		// Save them to the config
		plugin.getSM().getLobbyConfig().getConfig().set("signs", signLocations);

		// Save config to disk
		plugin.getSM().getLobbyConfig().saveConfig();
	}

	public Location getSpawn() {
		return spawn;
	}

	public void setSpawn(Location location) {
		spawn = location;
	}

	public Set<SGSign> getSigns() {
		return signs.keySet();
	}

	public boolean isGameSign(SGSign sign) {
		return sign.isValid() && (plugin.getGM().byName(sign.getGameName()) != null);
	}

	public void createSign(SGSign sign) {
		plugin.getLogger().info("Added sign");
		Game game = plugin.getGM().byName(sign.getGameName());
		signs.put(sign, game);
		sign.update(game);
	}

	public void removeSign(SGSign sign) {
		plugin.getLogger().info("Removed sign");
		signs.remove(sign);
	}

	public void playerClickedSign(Player player, SGSign sign) {
		Game game = plugin.getGM().byName(sign.getGameName());
		if (game != null) {
			game.join(player);
		}
	}

	public void updateAllSigns() {
		for (SGSign sign : signs.keySet()) {
			sign.update(signs.get(sign));
		}
	}

	public void updateGameSigns(Game game) {
		for (Map.Entry<SGSign, Game> entry : signs.entrySet()) {
			if (entry.getValue() == game) {
				entry.getKey().update(game);
			}
		}
	}

}
