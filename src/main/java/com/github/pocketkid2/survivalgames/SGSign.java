package com.github.pocketkid2.survivalgames;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;

public class SGSign {

	Block block;
	Sign sign;

	public SGSign(Block block) {
		if (block.getType() == Material.WALL_SIGN) {
			this.block = block;
			sign = (Sign) block.getState();
		}
	}
}
