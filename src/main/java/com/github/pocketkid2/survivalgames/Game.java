package com.github.pocketkid2.survivalgames;

import java.util.List;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

// Represents an automated game
public class Game {

	// Each game has a map
	private Map map;

	// Each game has a name
	private String name;

	// Each game has a list of alive/active players
	private List<Player> activePlayers;

	// Each game has a list of dead/offline players
	private List<OfflinePlayer> inactivePlayers;
}
