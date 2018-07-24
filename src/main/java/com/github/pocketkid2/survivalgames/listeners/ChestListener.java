package com.github.pocketkid2.survivalgames.listeners;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

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
import com.github.pocketkid2.survivalgames.Values;

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
						populate(game, inv);
						game.addChest(block);
					}
				}
			}
		}
	}

	/*
	 * Populates the inventory with a new random selection of items
	 */
	private void populate(Game game, Inventory inv) {
		// Reset the chest
		inv.clear();

		// First get a tier
		int tier = game.getBRC().getCount(Values.MAX_TIER);

		// Grab the possible values for that tier
		List<Material> items = plugin.getSM().getItemsInTier(tier);

		// Choose how many items from that list to pick
		int numItems = game.getBRC().getCount(inv.getSize());

		// Choose which indexes to choose
		Set<Integer> choose = game.getRIS().getInts(numItems, items.size());

		// Choose which items to use
		List<Material> reduced = new LinkedList<Material>();
		for (Integer i : choose) {
			reduced.add(items.get(i));
		}

		// Now choose which inventory slots to put them in
		Set<Integer> slots = game.getRIS().getInts(numItems, inv.getSize());

		// Now fill those slots
		Collections.shuffle(reduced);
		for (Integer i : slots) {
			inv.setItem(i, new ItemStack(reduced.get(0)));
			reduced.remove(0);
		}
	}

}
