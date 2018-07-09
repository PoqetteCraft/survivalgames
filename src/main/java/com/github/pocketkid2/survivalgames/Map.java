package com.github.pocketkid2.survivalgames;

import java.util.List;

import org.bukkit.Location;

// Represents a physical map region for a game
public class Map {

	// The center of the map (should be player-accessible, this will be the default
	// spectator spawnpoint)
	private Location center;

	// The square radius of the map from the center
	private int radius;

	// A list of all the player spawn-points
	private List<Location> spawns;

	// Base constructor
	public Map(Location center, int radius) {
		this.center = center;
		this.radius = radius;
	}

	/**
	 * Checks if this map contains a location
	 *
	 * @param loc The location to check
	 * @return true if the location is inside this map region
	 */
	public boolean contains(Location loc) {

		// If the worlds differ immediately exit
		if (loc.getWorld() != center.getWorld()) {
			return false;
		}

		// We should only check the horizontal coordinates because regions are
		// top-to-bottom
		if (Math.abs(loc.getBlockX() - center.getBlockX()) > radius) {
			return false;
		} else if (Math.abs(loc.getBlockZ() - center.getBlockZ()) > radius) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Add this spawn to the list
	 * 
	 * @param loc The spawn location to add
	 */
	public void addSpawn(Location loc) {
		if (contains(loc)) {
			spawns.add(loc);
		}
	}

	/**
	 * @return the center
	 */
	public Location getCenter() {
		return center;
	}

	/**
	 * @return the radius
	 */
	public int getRadius() {
		return radius;
	}

	/**
	 * @return the spawns
	 */
	public List<Location> getSpawns() {
		return spawns;
	}

	/**
	 * Get a specific spawn location
	 *
	 * @param index Range is 0...size-1
	 * @return The location or null if index is invalid
	 */
	public Location getSpawnByIndex(int index) {
		if ((index < 0) || (index >= spawns.size())) {
			return null;
		} else {
			return spawns.get(index);
		}
	}

}
