package com.github.pocketkid2.survivalgames.listeners;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import com.github.pocketkid2.survivalgames.Game;
import com.github.pocketkid2.survivalgames.SurvivalGamesPlugin;

public class DamageListener implements Listener {

	private SurvivalGamesPlugin plugin;

	public DamageListener(SurvivalGamesPlugin pl) {
		plugin = pl;
	}

	@EventHandler
	public void onEntityDamage(EntityDamageEvent event) {
		if (event.getEntityType() == EntityType.PLAYER) {
			Player player = (Player) event.getEntity();
			if (plugin.getGM().isInGame(player)) {
				if (player.getHealth() <= event.getFinalDamage()) {
					event.setCancelled(true);
					Game g = plugin.getGM().byPlayer(player);
					g.leave(player, false, event.getCause());
				}
			}
		}
	}

}
