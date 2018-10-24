package com.locydragon.tim.model.script.compile;

import com.locydragon.tim.model.script.Compiler;
import com.locydragon.tim.model.script.Result;

public class MathCompiler implements Compiler {
	@Override
	public Result onInput(String line) {
		Result result = new Result();
		if (line.contains("百分比概率") ||line.contains("百分比几率")) {
			line = line.replace("百分比概率", "odds").replace("百分比几率", "odds");
		}
		if (line.contains("随机数范围")) {
			line = line.replace("随机数范围", "randomInArea");
		}
		result.canAsync = true;
		result.code = line;
		return result;
	}
}
