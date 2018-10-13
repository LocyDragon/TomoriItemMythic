package com.locydragon.tim.model.script;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class ScriptLoader {
	public static TomoriScript load(File file) {
		if (!file.getName().endsWith(".tos")) {
			throw new IllegalArgumentException("Not a legal file!");
		}
		FileConfiguration config = YamlConfiguration.loadConfiguration(file);
		boolean canAsync = true;
		for (String line : config.getStringList("script")) {

		}
		TomoriScript scriptObject = new TomoriScript(config.getString("pattern"), config.getStringList("script"));
		if (canAsync) {
			ScriptCar.carAsync.put(config.getString("pattern"), scriptObject);
		} else {
			ScriptCar.carSync.put(config.getString("pattern"), scriptObject);
		}
		return scriptObject;
	}
}
