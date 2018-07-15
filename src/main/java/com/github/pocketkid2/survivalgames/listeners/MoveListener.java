package com.github.pocketkid2.survivalgames.listeners;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import com.github.pocketkid2.survivalgames.Game;
import com.github.pocketkid2.survivalgames.Game.Status;
import com.github.pocketkid2.survivalgames.SurvivalGamesPlugin;

public class MoveListener implements Listener {

	private SurvivalGamesPlugin plugin;

	public MoveListener(SurvivalGamesPlugin pl) {
		plugin = pl;
	}

	/**
	 * Returns true if the two locations are inside different blocks
	 *
	 * @param loc1
	 * @param loc2
	 * @return
	 */
	private boolean blockLocationDiffer(Location loc1, Location loc2) {
		if (loc1.getBlockX() != loc2.getBlockX()) {
			return true;
		}
		if (loc1.getBlockY() != loc2.getBlockY()) {
			return true;
		}
		if (loc1.getBlockZ() != loc2.getBlockZ()) {
			return true;
		}
		return false;
	}

	/*
	 * Check that a player moved off their square
	 */
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {

		// If the player is in-game
		if (plugin.getGM().isInGame(event.getPlayer())) {

			// Grab the status
			Status status = plugin.getGM().byPlayer(event.getPlayer()).getStatus();

			// If the players are supposed to be staying on the pedestal
			if ((status == Game.Status.WAITING) || (status == Game.Status.STARTING)) {

				// If the player is actually changing block
				if (blockLocationDiffer(event.getTo(), event.getFrom())) {
					event.setCancelled(true);
				}
			}
		}
	}
}
