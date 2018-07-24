package com.github.pocketkid2.survivalgames.tasks;

import org.bukkit.scheduler.BukkitRunnable;

import com.github.pocketkid2.survivalgames.Game;
import com.github.pocketkid2.survivalgames.Messages;

/**
 * Represents the countdown timer, which broadcasts every second
 *
 * @author Adam
 *
 */
public class CountdownTask extends BukkitRunnable {

	private int seconds;
	private Game game;

	/**
	 * @param seconds
	 * @param game
	 */
	public CountdownTask(int seconds, Game game) {
		this.seconds = seconds;
		this.game = game;
	}

	@Override
	public void run() {
		if (seconds > 0) {
			if ((seconds < 10) || ((seconds % 10) == 0)) {
				game.broadcast(Messages.GAME_STARTING_IN(seconds));
			}
			seconds--;
		} else {
			game.start();
			cancel();
		}
	}

}