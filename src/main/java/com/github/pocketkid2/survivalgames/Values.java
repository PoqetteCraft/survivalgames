package com.github.pocketkid2.survivalgames;

import com.github.pocketkid2.survivalgames.Game.Status;

public interface Values {

	int MIN_RADIUS = 50;
	int MAX_RADIUS = 500;

	int MIN_TIER = 1;
	int MAX_TIER = 5;

	int MIN_SPAWNS = 4;
	int MAX_SPAWNS = 50;

	int MIN_PLAYERS = 2;

	String SIGN_TITLE = "[SurvivalGames]";

	static String GAME_FORMAT(String name) {
		return name;
	}

	static String STATUS_FORMAT(Status status) {
		return status.getReadable();
	}

	static String COUNT_FORMAT(int currCount, int maxCount) {
		return String.format("%d/%d", currCount, maxCount);
	}

}
