package com.github.pocketkid2.survivalgames;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

/**
 * Used to represent the physical map region and anything specific to a map that
 * must be saved to disk.
 * 
 * @author Adam
 *
 */
public class Arena implements ConfigurationSerializable {

	// The essential details that define an arena
	private String name;
	private Location center;
	private int radius;
	private List<Location> spawns;

	// Base constructor
	public Arena(String name, Location center, int radius) {
		this.name = name;
		this.center = center;
		this.radius = radius;
		spawns = new ArrayList<Location>();
	}

	// Serialization constructor
	@SuppressWarnings("unchecked")
	public Arena(java.util.Map<String, Object> objects) {
		name = (String) objects.get("name");
		center = (Location) objects.get("center");
		radius = (int) objects.get("radius");
		spawns = (List<Location>) objects.get("spawns");
	}

	/**
	 * Checks if this map contains a location
	 *
	 * @param loc The location to check
	 * @return true if the location is inside this arena
	 */
	public boolean contains(Location loc) {

		// If the worlds differ immediately exit
		if (loc.getWorld() != center.getWorld()) {
			return false;
		}

		// We should only check the horizontal block coordinates because regions are
		// top-to-bottom
		if (Math.abs(loc.getBlockX() - center.getBlockX()) > radius) {
			return false;
		} else if (Math.abs(loc.getBlockZ() - center.getBlockZ()) > radius) {
			return false;
		}

		// If we get down here we know it must be inside
		return true;
	}

	// Add a new location to the spawn list
	public void addSpawn(Location loc) {
		if (contains(loc)) {
			spawns.add(loc);
		}
	}

	// Getter
	public String getName() {
		return name;
	}

	// Getter
	public Location getCenter() {
		return center;
	}

	// Getter
	public int getRadius() {
		return radius;
	}

	// Getter
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
		// Do some bounds checking
		if ((index < 0) || (index >= spawns.size())) {
			return null;
		} else {
			return spawns.get(index);
		}
	}

	// Required as part of the ConfigurationSerializable interface
	@Override
	public java.util.Map<String, Object> serialize() {
		java.util.Map<String, Object> objects = new HashMap<String, Object>();
		objects.put("name", name);
		objects.put("center", center);
		objects.put("radius", radius);
		objects.put("spawns", spawns);
		return objects;
	}

}
