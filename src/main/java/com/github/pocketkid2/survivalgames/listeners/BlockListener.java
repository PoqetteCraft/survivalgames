package com.github.pocketkid2.survivalgames.listeners;

import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import com.github.pocketkid2.survivalgames.Game;
import com.github.pocketkid2.survivalgames.SurvivalGamesPlugin;

public class BlockListener extends BaseListener {

	public BlockListener(SurvivalGamesPlugin sgp) {
		super(sgp);
	}

	/*
	 * Listen for when a player damages a block
	 */
	@EventHandler
	public void onBlockDamage(BlockDamageEvent event) {
		if (plugin.getGM().isInGame(event.getPlayer())) {
			if (!plugin.getSM().allowedBlocks().contains(event.getBlock().getType())) {
				event.setCancelled(true);
			}
		}
	}

	/*
	 * Listen for when a player breaks a block
	 */
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		if (plugin.getGM().isInGame(event.getPlayer())) {
			// If this block isn't allowed
			if (!plugin.getSM().allowedBlocks().contains(event.getBlock().getType())) {
				// Cancel the event
				event.setCancelled(true);
			} else {
				// Otherwise mark this for replacement
				plugin.getGM().byPlayer(event.getPlayer()).queueBlock(event.getBlock());
			}
		}
	}

	/*
	 * Listen for when a player places a block
	 */
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		if (plugin.getGM().isInGame(event.getPlayer())) {
			event.setCancelled(true);
		}
	}

	/*
	 * Listen for when a block explodes
	 */
	@EventHandler
	public void onBlockExplode(BlockExplodeEvent event) {
		if (plugin.getGM().isInGame(event.getBlock())) {
			// Queue all blocks for reset
			Game game = plugin.getGM().byBlock(event.getBlock());
			game.queueBlock(event.getBlock());
			for (Block block : event.blockList()) {
				game.queueBlock(block);
			}
		}
	}

	/*
	 * Listen for when a block burns
	 */
	@EventHandler
	public void onBlockBurn(BlockBurnEvent event) {
		if (plugin.getGM().isInGame(event.getBlock())) {
			// Queue block for reset
			plugin.getGM().byBlock(event.getBlock()).queueBlock(event.getBlock());
		}
	}

}
