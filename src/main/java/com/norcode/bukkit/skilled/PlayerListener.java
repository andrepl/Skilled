package com.norcode.bukkit.skilled;

import com.norcode.bukkit.skilled.skill.Skill;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerListener implements Listener {

	Skilled plugin;

	public PlayerListener(Skilled plugin) {
		this.plugin = plugin;
	}

	@EventHandler(ignoreCancelled=true)
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player p = event.getPlayer();
		Skill.initializePlayer(plugin, p);
	}
}
