package com.github.pocketkid2.survivalgames;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import com.github.pocketkid2.survivalgames.config.SettingsManager;

public class GameManager {

	private SurvivalGamesPlugin plugin;

	private List<Game> games;

	public GameManager(SurvivalGamesPlugin pl, SettingsManager sm) {
		plugin = pl;

		games = new ArrayList<Game>();

		List<Arena> arenas = sm.loadAllMaps();

		for (Arena m : arenas) {
			games.add(new Game(plugin, this, m));
		}
	}

	public void shutdown(SettingsManager sm) {
		// Stop all games peacefully
		for (Game g : games) {
			g.stop();
		}

		List<Arena> arenas = new ArrayList<Arena>();

		for (Game g : games) {
			arenas.add(g.getMap());
		}

		sm.saveAllMaps(arenas);
	}

	public void addMap(Arena m) {
		games.add(new Game(plugin, this, m));
	}

	public void removeGame(Game g) {
		if (games.contains(g)) {
			g.stop();
			games.remove(g);
		}
	}

	public List<Game> allGames() {
		return games;
	}

	public Game byName(String name) {
		for (Game g : games) {
			if (g.getMap().getName().equalsIgnoreCase(name)) {
				return g;
			}
		}
		return null;
	}

	public Game byPlayer(Player player) {
		for (Game g : games) {
			if (g.isInGame(player)) {
				return g;
			}
		}
		return null;
	}

	public boolean isInGame(Player p) {
		for (Game g : games) {
			if (g.isInGame(p)) {
				return true;
			}
		}
		return false;
	}

}
