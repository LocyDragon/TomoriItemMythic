package com.locydragon.tim.commands.sub;

import com.locydragon.tim.commands.SubCmdRunner;
import com.locydragon.tim.commands.SubCommandBasic;
import com.locydragon.tim.commands.SubCommandInfo;
import com.locydragon.tim.io.FileConstantURLs;
import com.locydragon.tim.io.item.IOItemMaker;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;

/**
 * @author Administrator
 */
public class CommandUseModel implements SubCmdRunner {
	public static final String MODEL_FILE = "//Model.tim";
	public static final String pathOne = "ModelLore";
	public static final String pathTwo = "UsingMessage";
	/**
	 * 指令前缀
	 */
	public static final String CMD_PREFIX = "model";
	/**
	 * 期望指令长度
	 */
	public static final Integer LENGTH_EXPECT = 2;

	static {
		SubCommandBasic.addListener(new CommandUseModel());
	}

	@Override
	public void onSubCommand(SubCommandInfo info) {
		if (info.args[0].equalsIgnoreCase(CMD_PREFIX)) {
			if (info.args.length == LENGTH_EXPECT) {
				String modelName = info.args[1];
				ItemStack itemInHand = info.getSender().getItemInHand();
				if (itemInHand == null || itemInHand.getType() == Material.AIR) {
					info.getSender().sendMessage("§3[TomoriItemMythic] §e你总不能手握空气使用模板吧!");
					return;
				}
				File modelTarget = new File(FileConstantURLs.MODEL_LOCATION+modelName+MODEL_FILE);
				if (!modelTarget.exists()) {
					info.getSender().sendMessage("§3[TomoriItemMythic] §e你输入的模板不存在或模板文件不正确或损坏.");
					return;
				}
				FileConfiguration target = YamlConfiguration.loadConfiguration(modelTarget);
				IOItemMaker maker = new IOItemMaker(target.getStringList(pathOne),
						target.getStringList(pathTwo), info.getSender(), info.getSender().getItemInHand());
				info.getSender().sendMessage(ChatColor.GREEN+"开始使用模板了...在此过程中请不要变更手上的物品,也要§c正确地§a回答模板的问题!");
				maker.start();
			} else {
				info.getSender().sendMessage("§3[TomoriItemMythic] §e请使用/tim model [模板名称] ——给你手上的物品增加一个模板!");
			}
		}
	}
}
