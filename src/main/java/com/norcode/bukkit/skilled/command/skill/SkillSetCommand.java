package com.norcode.bukkit.skilled.command.skill;

import com.norcode.bukkit.skilled.Skilled;
import com.norcode.bukkit.skilled.command.BaseCommand;
import com.norcode.bukkit.skilled.command.CommandError;
import com.norcode.bukkit.skilled.skill.Skill;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.LinkedList;
import java.util.List;

public class SkillSetCommand extends BaseCommand {
	public SkillSetCommand(Skilled plugin) {
		super(plugin, "set", new String[]{}, "skilled.command.skill.set", new String[] {});
	}

	public boolean isNumber(String s) {
		try {
			Integer.parseInt(s);
			return true;
		} catch (IllegalArgumentException ex) {
			return false;
		}
	}

	@Override
	protected void onExecute(CommandSender commandSender, String label, LinkedList<String> args) throws CommandError {
		if (args.size() <= 1) {
			showHelp(commandSender, label, args);
			return;
		}

		if (!isNumber(args.peekLast())) {
			showHelp(commandSender, label, args);
		}

		int level = Integer.parseInt(args.peekLast());
		args.pollLast();
		Player target = null;

		if (args.size() == 2) {
			List<Player> players = plugin.getServer().matchPlayer(args.peek());
			if (players.size() > 2) {
				throw new CommandError(args.peek() + " matched more than 1 player!");
			} else if (players.size() == 0) {
				throw new CommandError("Unknown Player: " + args.peek());
			}
			target = players.get(0);
			args.pop();
		} else if (commandSender instanceof Player) {
			target = (Player) commandSender;
		}

		if (target == null) {
			throw new CommandError("You cannot target yourself from the console.");
		}

		Skill skill = Skill.valueOf(args.peek());

		if (skill == null) {
			throw new CommandError("Unknown Skill: " + args.peek());
		}
		args.pop();
		skill.setPlayerLevel(target, level);
		commandSender.sendMessage(target.getName() + "'s " + skill.getName() + " skill is now level " + level);
	}
}
