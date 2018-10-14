package com.locydragon.tim.model.script.compile;

import com.locydragon.tim.model.script.Compiler;
import com.locydragon.tim.model.script.Result;

public class InterruptedCompiler implements Compiler{
	@Override
	public Result onInput(String line) {
		Result result = new Result();
		if (line.trim().equalsIgnoreCase("stopAll") || line.trim().equals("终止所有") && !line.contains("\"")) {
			result.code = "return false;";
		} else if (line.trim().equalsIgnoreCase("stop") || line.trim().equals("终止") && !line.contains("\"")) {
			result.code = "return true;";
		}
		result.canAsync = true;
		return result;
	}
}
