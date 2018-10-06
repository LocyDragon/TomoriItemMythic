package com.locydragon.tim.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandBus implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
		if (args.length <= 0) {
			sender.sendMessage("§3[TomoriItemMythic] §e请输入正确的指令!");
			return false;
		}
		SubCommandInfo info = new SubCommandInfo();
		info.args = args;
		info.command = command;
		info.sender = sender;
		info.s = s;
		SubCommandBasic.invoke(info);
		return false;
	}
}
