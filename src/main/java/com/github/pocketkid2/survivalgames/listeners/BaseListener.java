package com.github.pocketkid2.survivalgames.listeners;

import org.bukkit.event.Listener;

import com.github.pocketkid2.survivalgames.SurvivalGamesPlugin;

public class BaseListener implements Listener {

	protected SurvivalGamesPlugin plugin;

	public BaseListener() {
		// Do nothing
	}

	public BaseListener(SurvivalGamesPlugin plugin) {
		this.plugin = plugin;
	}
}
