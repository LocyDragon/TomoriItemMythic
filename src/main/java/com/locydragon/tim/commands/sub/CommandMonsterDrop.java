package com.locydragon.tim.commands.sub;

import com.locydragon.tim.commands.SubCmdRunner;
import com.locydragon.tim.commands.SubCommandInfo;

public class CommandMonsterDrop implements SubCmdRunner {
	/**
	 * 指令前缀
	 */
	public static final String CMD_PREFIX = "drop";
	/**
	 * 期望指令长度
	 */
	public static final Integer LENGTH_EXPECT = 2;
	@Override
	public void onSubCommand(SubCommandInfo info) {
		if (info.args[0].equalsIgnoreCase(CMD_PREFIX)) {

		}
	}
}
