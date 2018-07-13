package com.github.pocketkid2.survivalgames;

import java.util.ArrayList;
import java.util.List;

public class GameManager {

	private List<Game> games;

	public GameManager(SurvivalGamesPlugin plugin, SettingsManager sm) {
		games = new ArrayList<Game>();

		List<Map> arenas = sm.getAllMaps();

		for (Map m : arenas) {
			games.add(new Game(m));
		}
	}

}
