package com.github.pocketkid2.survivalgames;

import java.util.ArrayList;
import java.util.List;

import com.github.pocketkid2.survivalgames.config.SettingsManager;

public class GameManager {

	private List<Game> games;

	public GameManager(SurvivalGamesPlugin plugin, SettingsManager sm) {
		games = new ArrayList<Game>();

		List<Map> arenas = sm.loadAllMaps();

		for (Map m : arenas) {
			games.add(new Game(m));
		}
	}

	public void shutdown(SettingsManager sm) {
		// Stop all games peacefully
		for (Game g : games) {
			g.stop();
		}

		List<Map> arenas = new ArrayList<Map>();

		for (Game g : games) {
			arenas.add(g.getMap());
		}

		sm.saveAllMaps(arenas);
	}

	public void addMap(Map m) {
		games.add(new Game(m));
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

}
