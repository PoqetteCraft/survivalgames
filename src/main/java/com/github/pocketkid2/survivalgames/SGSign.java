package com.github.pocketkid2.survivalgames;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;

public class SGSign {

	private static final int TITLE_LINE = 0;
	private static final int GAME_LINE = 1;
	private static final int STATUS_LINE = 2;
	private static final int COUNT_LINE = 3;

	Block block;
	Sign sign;

	public SGSign(Block block) {
		if (block.getType() == Material.WALL_SIGN) {
			this.block = block;
			sign = (Sign) block.getState();
		}
	}

	public SGSign(Block block, String[] lines) {
		this(block);
		for (int i = 0; i < 4; i++) {
			sign.setLine(i, lines[i]);
		}
	}

	public Location getLocation() {
		return block.getLocation();
	}

	private void readState() {
		sign = (Sign) block.getState();
	}

	private void saveState() {
		sign.update(true, false);
	}

	public boolean isValid() {
		readState();
		if (block != null) {
			if (block.getType() == Material.WALL_SIGN) {
				if (sign.getLine(0).equalsIgnoreCase(Values.SIGN_TITLE)) {
					return true;
				}
			}
		}
		return false;
	}

	public void update(Game game) {
		if (isValid() && (game != null)) {
			sign.setLine(TITLE_LINE, Values.SIGN_TITLE);
			sign.setLine(GAME_LINE, Values.GAME_FORMAT(game.getMap().getName()));
			sign.setLine(STATUS_LINE, Values.STATUS_FORMAT(game.getStatus()));
			sign.setLine(COUNT_LINE, Values.COUNT_FORMAT(game.currCount(), game.maxCount()));
			saveState();
		}
	}

	public String getGameName() {
		if (isValid()) {
			return sign.getLine(GAME_LINE);
		} else {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		SGSign other = (SGSign) obj;
		if (block == null) {
			if (other.block != null) {
				return false;
			}
		} else if (!blockcheck(block, other.block)) {
			return false;
		}
		return true;
	}

	private boolean blockcheck(Block block0, Block block1) {
		if (block0.getLocation().getBlockX() == block1.getLocation().getBlockX()) {
			if (block0.getLocation().getBlockY() == block1.getLocation().getBlockY()) {
				if (block0.getLocation().getBlockZ() == block1.getLocation().getBlockZ()) {
					return true;
				}
			}
		}
		return false;
	}

}
