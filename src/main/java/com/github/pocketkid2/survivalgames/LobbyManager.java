package com.github.pocketkid2.survivalgames;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

public class LobbyManager {

	private SurvivalGamesPlugin plugin;
	private Location spawn;
	private List<SGSign> signs;

	@SuppressWarnings("unchecked")
	public LobbyManager(SurvivalGamesPlugin pl) {
		plugin = pl;

		plugin.getSM().getLobbyConfig().reloadConfig();

		spawn = plugin.getSM().getLobbyConfig().getConfig().getSerializable("spawn", Location.class);

		// signs = ((List<Location>)
		// plugin.getSM().getLobbyConfig().getConfig().getList("signs", new
		// LinkedList<Location>())).stream()
		// .map(l -> l.getBlock()).collect(Collectors.toList());
	}

	public void shutdown() {
		plugin.getSM().getLobbyConfig().getConfig().set("spawn", spawn);
		// plugin.getSM().getLobbyConfig().getConfig().set("signs", signs.stream().map(b
		// -> b.getLocation()).collect(Collectors.toList()));
		plugin.getSM().getLobbyConfig().saveConfig();
	}

	/**
	 * @return the spawn
	 */
	public Location getSpawn() {
		return spawn;
	}

	/**
	 * @return the signs
	 */
	public List<SGSign> getSigns() {
		return signs;
	}

	/**
	 * @param location The new lobby spawn location
	 */
	public void setSpawn(Location location) {
		spawn = location;
	}

	public void createSign(SGSign sgSign) {
		signs.add(sgSign);
	}

	public void removeSign(SGSign sgSign) {
		signs.remove(sgSign);
	}

	public void update() {
		System.out.println("Updating " + signs.size() + " signs");
		for (SGSign sign : signs) {
			// updateSign((Sign) sign.getState());
		}
	}

	private void updateSign(Sign sign) {
		Game game = plugin.getGM().byName(sign.getLine(1));
		if (game != null) {
			sign.setLine(2, String.format("Players: %d/%d", game.getAlive().size(), game.getMap().getSpawns().size()));
			sign.setLine(3, game.getStatus().getReadable());
		} else {
			plugin.getLogger().warning("Could not find game from sign with name " + sign.getLine(1));
		}
	}

	public boolean isGameSign(String[] strings) {
		if (strings[0].equalsIgnoreCase("[SurvivalGames]")) {
			if (plugin.getGM().byName(strings[1]) != null) {
				return true;
			}
		}
		return false;
	}

	public void playerClickedSign(Player player, String[] strings) {
		Game game = plugin.getGM().byName(strings[1]);
		if (game != null) {
			game.join(player);

		}
	}

	public SGSign getSign(Block block) {
		// TODO Auto-generated method stub
		return null;
	}
}
