package com.locydragon.tim.model.script.compile;

import com.locydragon.tim.model.script.Compiler;
import com.locydragon.tim.model.script.Result;

public class InterruptedCompiler implements Compiler{
	@Override
	public Result onInput(String line) {
		Result result = new Result();
		if (line.trim().equalsIgnoreCase("stop") || line.trim().equals("终止")) {
			result.code = "return false;";
		}
		result.canAsync = true;
		return result;
	}
}
