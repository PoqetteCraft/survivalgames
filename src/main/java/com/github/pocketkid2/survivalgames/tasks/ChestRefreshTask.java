package com.github.pocketkid2.survivalgames.tasks;

import org.bukkit.scheduler.BukkitRunnable;

import com.github.pocketkid2.survivalgames.Game;
import com.github.pocketkid2.survivalgames.Messages;

public class ChestRefreshTask extends BukkitRunnable {

	private int seconds;
	private Game game;

	/**
	 * @param seconds
	 * @param game
	 */
	public ChestRefreshTask(int seconds, Game game) {
		this.seconds = seconds;
		this.game = game;
	}

	@Override
	public void run() {
		if (seconds > 0) {
			seconds--;
		} else {
			game.resetChests();
			game.broadcast(Messages.CHESTS_HAVE_REFRESHED);
			cancel();
		}
	}

}
