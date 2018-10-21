package com.locydragon.tim.commands.sub;

import com.locydragon.tim.commands.SubCmdRunner;
import com.locydragon.tim.commands.SubCommandInfo;
import com.locydragon.tim.util.NumberUtil;
import org.bukkit.ChatColor;

public class CommandMonsterDrop implements SubCmdRunner {
	/**
	 * 指令前缀
	 */
	public static final String CMD_PREFIX = "drop";
	/**
	 * 期望指令长度
	 */
	public static final Integer LENGTH_EXPECT = 3;
	@Override
	public void onSubCommand(SubCommandInfo info) {
		if (info.args[0].equalsIgnoreCase(CMD_PREFIX)) {
			if (info.args.length == LENGTH_EXPECT) {
				String chanceString = info.args[1];
				String monsterName = ChatColor.stripColor(info.args[2]);
				if (!NumberUtil.isNumber(chanceString)) {
					info.getSender().sendMessage("§3[TomoriItemMythic] §c请输入阿拉伯数字(0-100).");
					return;
				}
				int change = Integer.valueOf(chanceString);
				if (!(change >= 0 && change <= 100)) {
					info.getSender().sendMessage("§3[TomoriItemMythic] §c请输入阿拉伯数字(0-100).");
					return;
				}
			} else {
				info.getSender().sendMessage("§3[TomoriItemMythic] §e请使用/tim drop [概率(0-100)] [怪物名称(包含&颜色代码)]——给某个怪物增加一个掉落为你手上的物品!");
			}
		}
	}
}
