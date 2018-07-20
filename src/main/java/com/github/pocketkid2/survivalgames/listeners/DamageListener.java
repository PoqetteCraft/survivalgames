package com.github.pocketkid2.survivalgames.listeners;

import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import com.github.pocketkid2.survivalgames.Game;
import com.github.pocketkid2.survivalgames.SurvivalGamesPlugin;

public class DamageListener extends BaseListener {

	public DamageListener(SurvivalGamesPlugin sgp) {
		super(sgp);
	}

	/*
	 * Listen for when a player is damaged and then dies
	 *
	 */
	@EventHandler
	public void onEntityDamage(EntityDamageEvent event) {
		// Check that it's actually a player
		if (event.getEntityType() == EntityType.PLAYER) {

			// Grab that player
			Player player = (Player) event.getEntity();

			// Check that they're in-game
			if (plugin.getGM().isInGame(player)) {

				// If the player is going to die
				if (player.getHealth() <= event.getFinalDamage()) {

					// Cancel the death
					event.setCancelled(true);

					// Grab the game
					Game g = plugin.getGM().byPlayer(player);

					// We need a killer name
					String killer = "";

					// If they were killed by an entity
					if (event instanceof EntityDamageByEntityEvent) {

						// Grab that entity
						EntityDamageByEntityEvent newEvent = ((EntityDamageByEntityEvent) event);
						Entity entity = newEvent.getDamager();

						// If it's a player
						if (entity.getType() == EntityType.PLAYER) {
							// Get the player name
							killer = ((Player) entity).getName();
						} else {
							// Otherwise get the entity type
							killer = entity.getType().toString();
						}
					} else if (event instanceof EntityDamageByBlockEvent) {
						// Grab that block
						Block block = ((EntityDamageByBlockEvent) event).getDamager();

						// Get the block type name
						killer = block.getType().toString();
					} else {
						// Use the regular damage cause name
						killer = event.getCause().toString();
					}

					// Pull them out involuntarily
					g.leave(player, false, killer);
				}
			}
		}
	}

}
