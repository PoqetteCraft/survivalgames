package com.github.pocketkid2.survivalgames.tasks;

import org.bukkit.scheduler.BukkitRunnable;

import com.github.pocketkid2.survivalgames.Game;

public class StopGameTask extends BukkitRunnable {

	private int seconds;
	private Game game;

	/**
	 * @param seconds
	 * @param game
	 */
	public StopGameTask(int seconds, Game game) {
		this.seconds = seconds;
		this.game = game;
	}

	@Override
	public void run() {
		if (seconds > 0) {
			seconds--;
		} else {
			game.stop();
			cancel();
		}
	}

}
