package com.locydragon.tim.model.script.compile;

import com.locydragon.tim.model.script.Compiler;
import com.locydragon.tim.model.script.Result;

public class EventCompiler implements Compiler {

	@Override
	public Result onInput(String line) {
		Result result = new Result();
		boolean canAsync = true;
		if (line.startsWith("设置伤害")) {
			String codeFormat = "e.setDamage(";
			line = line.replace("设置伤害", codeFormat);
			line += ");";
			canAsync = false;
		}
		if (line.contains("攻击伤害")) {
			String codeFormat = "e.getDamage()";
			line = line.replace("攻击伤害", codeFormat);
			canAsync = true;
		}
		if (line.equalsIgnoreCase("无伤害")) {
			line = "e.setCancelled(true);";
			canAsync = false;
		}
		result.code = line;
		result.canAsync = canAsync;
		return result;
	}
}
