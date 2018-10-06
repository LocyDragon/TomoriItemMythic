package com.locydragon.tim.commands.sub;

import com.locydragon.tim.TomoriItemMythic;
import com.locydragon.tim.commands.SubCmdRunner;
import com.locydragon.tim.commands.SubCommandBasic;
import com.locydragon.tim.commands.SubCommandInfo;
import org.bukkit.ChatColor;

/**
 * @author Administrator
 */
public class CommandShowVersion implements SubCmdRunner {
	/**
	 * 指令前缀
	 */
	public static final String CMD_PREFIX = "version";
	/**
	 * 期望指令长度
	 */
	public static final Integer LENGTH_EXPECT = 3;

	static {
		SubCommandBasic.addListener(new CommandShowVersion());
	}

	@Override
	public void onSubCommand(SubCommandInfo info) {
		if (info.args[0].equalsIgnoreCase(CMD_PREFIX)) {
			info.getSender().sendMessage("§3[TomoriItemMythic] §e当前插件版本号: " +
					""+ TomoriItemMythic.PLUGIN_INSTANCE.getDescription().getVersion());
		}
	}
}