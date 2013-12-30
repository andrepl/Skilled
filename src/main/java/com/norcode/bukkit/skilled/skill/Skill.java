package com.norcode.bukkit.skilled.skill;

import com.norcode.bukkit.playerid.PlayerID;
import com.norcode.bukkit.skilled.Skilled;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.metadata.LazyMetadataValue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public abstract class Skill {

	String name;
	String description;
	String[] longDescription;

	public static Skill FISHING = new FishingSkill();
	public static Skill MINING = new MiningSkill();

	private static List<Skill> registered = new ArrayList<Skill>();
	static {
		registered.add(FISHING);
		registered.add(MINING);
	}

	public static List<Skill> values() {
		return new ArrayList<Skill>(registered);
	}

	Skill(String name, String description, String[] longDescription) {
		this.name = name;
		this.description = description;
		this.longDescription = longDescription;
	}

	public String getMetaKey() {
		return "skilled-skill-" + this.name.toLowerCase().replace(" ","-");
	}

	public int getPlayerLevel(Player p) {
		return p.getMetadata(getMetaKey()).get(0).asInt();
	}

	public void setPlayerLevel(Player p, int level) {
		ConfigurationSection cfg = PlayerID.getPlayerData("Skilled", p);
		cfg.set(getMetaKey(), level);
		p.getMetadata(getMetaKey()).get(0).invalidate();
	}

	public static void initializePlayer(final Skilled plugin, Player player) {
		final String playerName = player.getName();
		for (final Skill s: registered) {
			if (!player.hasMetadata(s.getMetaKey())) {
				player.setMetadata(s.getMetaKey(), new LazyMetadataValue(plugin, new Callable<Object>() {
					@Override
					public Object call() throws Exception {
						ConfigurationSection cfg = PlayerID.getPlayerData("Skilled", plugin.getServer().getPlayerExact(playerName));
						return cfg.getInt(s.getMetaKey(), 0);
					}
				}));
			}
		}
	};

	public String getName() {
		return name;
	}

	public static Skill valueOf(String name) {
		for (Skill s: registered) {
			if (s.name.equalsIgnoreCase(name)) {
				return s;
			}
		}
		return null;
	}
}
