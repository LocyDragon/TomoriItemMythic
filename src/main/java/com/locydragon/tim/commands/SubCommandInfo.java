package com.locydragon.tim.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author Administrator
 */
public class SubCommandInfo {
	public String[] args;
	public Player sender;
	public Command command;
	public String s;

	public String[] getArgs() {
		return args;
	}

	public Command getCommand() {
		return command;
	}

	public Player getSender() {
		return sender;
	}

	private String getS() {
		return s;
	}
}