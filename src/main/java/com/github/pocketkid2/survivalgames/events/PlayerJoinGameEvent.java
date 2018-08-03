package com.github.pocketkid2.survivalgames.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.github.pocketkid2.survivalgames.Game;

public class PlayerJoinGameEvent extends Event {

	private Game game;
	private Player player;

	/**
	 * @param game
	 * @param player
	 */
	public PlayerJoinGameEvent(Game g, Player p) {
		game = g;
		player = p;
	}

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

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	private static final HandlerList handlers = new HandlerList();

	public static HandlerList getHandlerList() {
		return handlers;
	}

}
