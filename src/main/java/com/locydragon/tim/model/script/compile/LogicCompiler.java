package com.locydragon.tim.model.script.compile;

import com.locydragon.tim.model.script.Compiler;
import com.locydragon.tim.model.script.Result;

public class LogicCompiler implements Compiler {
	@Override
	public Result onInput(String line) {
		Result result = new Result();
		if (line.contains("数据")) {
			line = line.replace("数据", "Integer.valueOf");
		}
		result.canAsync = true;
		result.code = line;
		return result;
	}
}
