package com.github.pocketkid2.survivalgames.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.github.pocketkid2.survivalgames.Game;

public class PlayerJoinGameEvent extends Event {

	private Game g;
	private Player p;

	/**
	 * @param g
	 * @param p
	 */
	public PlayerJoinGameEvent(Game g, Player p) {
		this.g = g;
		this.p = p;
	}

	/**
	 * @return the g
	 */
	public Game getG() {
		return g;
	}

	/**
	 * @return the p
	 */
	public Player getP() {
		return p;
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
