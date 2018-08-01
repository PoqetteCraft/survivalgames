package com.github.pocketkid2.survivalgames.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.github.pocketkid2.survivalgames.Game;

public class GameChangeStatusEvent extends Event {

	private Game game;

	/**
	 * @param game
	 */
	public GameChangeStatusEvent(Game game) {
		this.game = game;
	}

	/**
	 * @return the game
	 */
	public Game getGame() {
		return game;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	private static final HandlerList handlers = new HandlerList();

	public static HandlerList getHandlerList() {
		return handlers;
	}

}
