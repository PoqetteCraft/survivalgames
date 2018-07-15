package com.github.pocketkid2.survivalgames.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.github.pocketkid2.survivalgames.Game;

public class PlayerLeaveGameEvent extends Event {

	private Game g;
	private Player p;

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

	/**
	 * @param g
	 * @param p
	 */
	public PlayerLeaveGameEvent(Game g, Player p) {
		this.g = g;
		this.p = p;
	}

	@Override
	public HandlerList getHandlers() {
		// TODO Auto-generated method stub
		return null;
	}

}
