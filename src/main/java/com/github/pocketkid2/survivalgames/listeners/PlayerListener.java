package com.github.pocketkid2.survivalgames.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;

import com.github.pocketkid2.survivalgames.SurvivalGamesPlugin;

public class PlayerListener extends BaseListener {

	public PlayerListener(SurvivalGamesPlugin sgp) {
		super(sgp);
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		if (plugin.getGM().isInGame(event.getPlayer())) {
			plugin.getGM().byPlayer(event.getPlayer()).leave(event.getPlayer(), true, "", false, "");
		}
	}
}
