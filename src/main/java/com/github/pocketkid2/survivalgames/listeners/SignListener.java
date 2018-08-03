package com.github.pocketkid2.survivalgames.listeners;

import org.bukkit.Material;
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
				SGSign sign = new SGSign(event.getClickedBlock());
				if (plugin.getLM().isGameSign(sign)) {
					plugin.getLM().playerClickedSign(event.getPlayer(), sign);
				}
			}
		}
	}

	@EventHandler
	public void onSignChange(SignChangeEvent event) {
		SGSign sign = new SGSign(event.getBlock(), event.getLines());
		if (plugin.getLM().isGameSign(sign)) {
			plugin.getLM().createSign(sign);
			event.getPlayer().sendMessage(Messages.SIGN_CREATED);
			for (int i = 0; i < 4; i++) {
				event.setLine(i, sign.getLines()[i]);
			}
		}
	}

	@EventHandler
	public void onSignBreak(BlockBreakEvent event) {
		if (event.getBlock().getType() == Material.WALL_SIGN) {
			SGSign sign = new SGSign(event.getBlock());
			if (plugin.getLM().isGameSign(sign)) {
				plugin.getLM().removeSign(sign);
				event.getPlayer().sendMessage(Messages.SIGN_REMOVED);
			}
		}
	}

}
