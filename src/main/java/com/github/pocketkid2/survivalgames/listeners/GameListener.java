package com.github.pocketkid2.survivalgames.listeners;

import org.bukkit.event.EventHandler;

import com.github.pocketkid2.survivalgames.SurvivalGamesPlugin;
import com.github.pocketkid2.survivalgames.events.GameChangeStatusEvent;
import com.github.pocketkid2.survivalgames.events.PlayerJoinGameEvent;
import com.github.pocketkid2.survivalgames.events.PlayerLeaveGameEvent;

public class GameListener extends BaseListener {

	public GameListener(SurvivalGamesPlugin sgp) {
		super(sgp);
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinGameEvent event) {
		plugin.getLM().update();
	}

	@EventHandler
	public void onPlayerLeave(PlayerLeaveGameEvent event) {
		plugin.getLM().update();
	}

	@EventHandler
	public void onGameStatusChange(GameChangeStatusEvent event) {
		plugin.getLM().update();
	}
}
