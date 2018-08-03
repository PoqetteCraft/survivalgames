package com.github.pocketkid2.survivalgames.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.github.pocketkid2.survivalgames.Game;

public class PlayerLeaveGameEvent extends Event {

	private Game game;
	private Player player;

	/**
	 * @return the game
	 */
	public Game getGame() {
		return game;
	}

	/**
	 * @return the player
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * @param game
	 * @param player
	 */
	public PlayerLeaveGameEvent(Game g, Player p) {
		game = g;
		player = p;
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
