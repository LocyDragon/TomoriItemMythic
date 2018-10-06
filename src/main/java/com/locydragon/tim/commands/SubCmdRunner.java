package com.locydragon.tim.commands;

/**
 * @author LocyDragon
 */
public interface SubCmdRunner {
	/**
	 * 当被执行时调用
	 * @param info 传入的参数
	 */
	void onSubCommand(SubCommandInfo info);
}