package com.norcode.bukkit.skilled.command.skill;

import com.norcode.bukkit.skilled.Skilled;
import com.norcode.bukkit.skilled.command.BaseCommand;
import com.norcode.bukkit.skilled.command.CommandError;
import com.norcode.bukkit.skilled.skill.Skill;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.LinkedList;

public class SkillCommand extends BaseCommand {
	public SkillCommand(Skilled plugin) {
		super(plugin, "skill", new String[] {}, "skilled.command.skill", new String[] {});
		plugin.getServer().getPluginCommand("skill").setExecutor(this);
		// register subcommands
		registerSubcommand(new SkillSetCommand(plugin));
	}

	@Override
	protected void onExecute(CommandSender commandSender, String label, LinkedList<String> args) throws CommandError {
		if (!(commandSender instanceof Player)) {
			throw new CommandError("This command is only available in game.");
		}
		Player player = (Player) commandSender;
		if (args.size() == 0) {
			// list skills.
			ArrayList<String> lines = new ArrayList<String>();
			lines.add(ChatColor.UNDERLINE + "" + ChatColor.GOLD + "Your Skills");
			for (Skill s: Skill.values()) {
				player.sendMessage(s.getName() + ": " + ChatColor.GOLD + Integer.toString(s.getPlayerLevel(player)));
			}
		}
	}
}
