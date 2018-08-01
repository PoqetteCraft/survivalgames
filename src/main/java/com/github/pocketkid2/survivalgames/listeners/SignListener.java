package com.github.pocketkid2.survivalgames.listeners;

import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import com.github.pocketkid2.survivalgames.Messages;
import com.github.pocketkid2.survivalgames.SGSign;
import com.github.pocketkid2.survivalgames.SurvivalGamesPlugin;

public class SignListener extends BaseListener {

	public SignListener(SurvivalGamesPlugin sgp) {
		super(sgp);
	}

	@EventHandler
	public void onPlayerClickSign(PlayerInteractEvent event) {
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (event.getClickedBlock().getType() == Material.WALL_SIGN) {
				if (plugin.getLM().isGameSign(((Sign) event.getClickedBlock().getState()).getLines())) {
					plugin.getLM().playerClickedSign(event.getPlayer(), ((Sign) event.getClickedBlock().getState()).getLines());
				}
			}
		}
	}

	@EventHandler
	public void onSignChange(SignChangeEvent event) {
		if (plugin.getLM().isGameSign(event.getLines())) {
			plugin.getLM().createSign(new SGSign(event.getBlock()));
			event.getPlayer().sendMessage(Messages.SIGN_CREATED);
		}
	}

	@EventHandler
	public void onSignBreak(BlockBreakEvent event) {
		if (event.getBlock().getType() == Material.WALL_SIGN) {
			if (plugin.getLM().isGameSign(((Sign) event.getBlock().getState()).getLines())) {
				plugin.getLM().removeSign(plugin.getLM().getSign(event.getBlock()));
				event.getPlayer().sendMessage(Messages.SIGN_REMOVED);
			}
		}
	}

}
