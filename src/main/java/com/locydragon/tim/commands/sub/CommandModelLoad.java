package com.locydragon.tim.commands.sub;

import com.locydragon.tim.commands.SubCmdRunner;
import com.locydragon.tim.commands.SubCommandInfo;
import com.locydragon.tim.io.FileConstantURLs;
import com.locydragon.tim.model.ModelCar;
import com.locydragon.tim.model.ModelMainFile;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class CommandModelLoad implements SubCmdRunner {
	/**
	 * 指令前缀
	 */
	public static final String CMD_PREFIX = "load";
	/**
	 * 期望指令长度
	 */
	public static final Integer LENGTH_EXPECT = 2;
	@Override
	public void onSubCommand(SubCommandInfo info) {
		if (info.args[0].equalsIgnoreCase(CMD_PREFIX)) {
			if (info.args.length == LENGTH_EXPECT) {
				String modelName = info.args[1];
				if (ModelCar.modelHash.keySet().contains(modelName)) {
					ModelCar.modelHash.remove(modelName);
				}
				File foundTarget = null;
				File targetModel = new File(FileConstantURLs.MODEL_LOCATION);
				Father:
				for (File inWhich : targetModel.listFiles()) {
					if (inWhich.isDirectory()) {
						for (File file : inWhich.listFiles()) {
							if (file.getName().trim().equalsIgnoreCase("Model.tim")) {
								FileConfiguration configFormat
										= YamlConfiguration.loadConfiguration(file);
								if (configFormat.getString("ModelName").equals(modelName)) {
									foundTarget = file;
									break Father;
								}
								continue Father;
							}
						}
					}
				}
				if (foundTarget == null) {
					info.getSender().sendMessage("§3[TomoriItemMythic] " +
							"§c无法找到该模板,请确认模板所在的位置是否正确.");
				} else {
					new ModelMainFile(foundTarget);
				}
			} else {
				info.getSender().sendMessage("§3[TomoriItemMythic] " +
						"§e请使用/tim load [模板名称] ——搜索并加载或重新加载一个模板.");
			}
		}
	}
}
