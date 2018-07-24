package com.github.pocketkid2.survivalgames.tasks;

import org.bukkit.scheduler.BukkitRunnable;

import com.github.pocketkid2.survivalgames.Game;
import com.github.pocketkid2.survivalgames.Messages;

public class GracePeriodTask extends BukkitRunnable {

	private int seconds;
	private Game game;

	/**
	 * @param seconds
	 * @param game
	 */
	public GracePeriodTask(int seconds, Game game) {
		this.seconds = seconds;
		this.game = game;
	}

	@Override
	public void run() {
		if (seconds > 0) {
			if (!game.isGracePeriod()) {
				game.setGracePeriod(true);
				game.broadcast(Messages.GRACE_PERIOD_STARTED);
			}
			if ((seconds % 10) == 0) {
				game.broadcast(Messages.GRACE_PERIOD_LEFT(seconds));
			}
			seconds--;
		} else {
			game.setGracePeriod(false);
			game.broadcast(Messages.GRACE_PERIOD_ENDED);
			cancel();
		}
	}

}
