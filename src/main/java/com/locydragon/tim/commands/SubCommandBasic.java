package com.locydragon.tim.commands;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 */
public class SubCommandBasic {
	private static List<SubCmdRunner> runners = new ArrayList<>();

	public static void addListener(SubCmdRunner runner) {
		runners.add(runner);
	}

	public static void invoke(SubCommandInfo info) {
		runners.forEach(x -> x.onSubCommand(info));
	}
}