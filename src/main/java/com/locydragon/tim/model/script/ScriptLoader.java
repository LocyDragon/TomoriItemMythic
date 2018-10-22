package com.locydragon.tim.model.script;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.List;

public class ScriptLoader {
	public static Integer asyncScriptNum = 0;
	public static Integer syncScriptNum = 0;
	public static TomoriScript load(File file) {
		if (!file.getName().endsWith(".tos")) {
			throw new IllegalArgumentException("Not a legal file!");
		}
		FileConfiguration config = YamlConfiguration.loadConfiguration(file);
		Result scriptResult = CompileBasic.compileAndFindCanAsync( config.getStringList("script"));
		List<String> codeList = scriptResult.codeList;
		boolean canAsync = scriptResult.canAsync;
		TomoriScript scriptObject = new TomoriScript(config.getString("pattern"), codeList);
		scriptObject.setListenerDependOn(config.getString("type", null));
		if (canAsync) {
			ScriptCar.carAsync.put(scriptObject.getPattern(), scriptObject);
			asyncScriptNum++;
		} else {
			ScriptCar.carSync.put(scriptObject.getPattern(), scriptObject);
			syncScriptNum++;
		}
		return scriptObject;
	}
}
