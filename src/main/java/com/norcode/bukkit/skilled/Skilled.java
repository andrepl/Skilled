package com.norcode.bukkit.skilled;

import com.norcode.bukkit.skilled.command.skill.SkillCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class Skilled extends JavaPlugin {

	PlayerListener playerListener;
	SkillCommand skillCommand;
	@Override
	public void onEnable() {
		setupListeners();
		setupCommands();
		getServer().getPluginManager().registerEvents(playerListener, this);
	}

	private void setupCommands() {
		skillCommand = new SkillCommand(this);
	}

	private void setupListeners() {
		playerListener = new PlayerListener(this);
	}


}
