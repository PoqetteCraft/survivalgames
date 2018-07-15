package com.github.pocketkid2.survivalgames.listeners;

import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDamageEvent;

import com.github.pocketkid2.survivalgames.SurvivalGamesPlugin;

public class BlockListener implements Listener {

	private SurvivalGamesPlugin plugin;

	public BlockListener(SurvivalGamesPlugin pl) {
		plugin = pl;
	}

	public void onBlockBreak(BlockDamageEvent event) {

	}

}
