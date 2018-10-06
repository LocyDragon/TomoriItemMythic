package com.locydragon.tim.commands.sub;

import com.locydragon.tim.commands.SubCmdRunner;
import com.locydragon.tim.commands.SubCommandBasic;
import com.locydragon.tim.commands.SubCommandInfo;
import com.locydragon.tim.io.FileConstantURLs;
import com.locydragon.tim.io.item.IOItemMaker;
import com.locydragon.tim.model.ModelCar;
import com.locydragon.tim.model.ModelMainFile;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * @author Administrator
 */
public class CommandUseModel implements SubCmdRunner {
	/**
	 * 指令前缀
	 */
	public static final String CMD_PREFIX = "model";
	/**
	 * 期望指令长度
	 */
	public static final Integer LENGTH_EXPECT = 2;

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
				ModelMainFile model = ModelCar.modelHash.get(modelName);
				if (model == null) {
					info.getSender().sendMessage("§3[TomoriItemMythic] §e你输入的模板不存在或模板文件不正确或损坏.");
					return;
				}
				IOItemMaker maker = new IOItemMaker(model.getLoreNeedFormat(),
						model.getMessageFormat(), info.getSender(), info.getSender().getItemInHand());
				info.getSender().sendMessage(ChatColor.GREEN+"开始使用模板了...在此过程中请不要变更手上的物品,也要§c正确地§a回答模板的问题!");
				maker.start();
			} else {
				info.getSender().sendMessage("§3[TomoriItemMythic] §e请使用/tim model [模板名称] ——给你手上的物品增加一个模板!");
			}
		}
	}
}
