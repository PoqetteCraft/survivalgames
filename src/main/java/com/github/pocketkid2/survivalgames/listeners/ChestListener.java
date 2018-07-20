package com.github.pocketkid2.survivalgames.listeners;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.github.pocketkid2.survivalgames.Game;
import com.github.pocketkid2.survivalgames.SurvivalGamesPlugin;

public class ChestListener extends BaseListener {

	public ChestListener(SurvivalGamesPlugin sgp) {
		super(sgp);
	}

	@EventHandler
	public void onPlayerClickChest(PlayerInteractEvent event) {
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			Block block = event.getClickedBlock();
			BlockState state = block.getState();
			if (state instanceof Chest) {
				Game game = plugin.getGM().byBlock(block);
				if (game != null) {
					if (!game.isChest(block)) {
						// Populate chest
						Inventory inv = ((Chest) state).getBlockInventory();
						inv.clear();
						inv.addItem(new ItemStack(Material.IRON_SWORD));
						// state.update(true, true);
						game.addChest(block);
					}
				}
			}
		}
	}

}
