package com.locydragon.tim.model.script.compile;

import com.locydragon.tim.model.script.Compiler;
import com.locydragon.tim.model.script.Result;

public class FlowControlCompiler implements Compiler {
	@Override
	public Result onInput(String line) {
		Result result = new Result();
		if (line.startsWith("如果") && !line.contains("\"")) {
			line = line.replace("如果", "if (");
			line += ") {\n";
		} else if (line.startsWith("循环") && !line.contains("\"")) {
			line = line.replace("循环", "while (");
			line += ") {\n";
		} else if ((line.startsWith("结束") || line.equalsIgnoreCase("END")) && !line.contains("\"")) {
			line = "}\n";
		}
		if (line.contains("且") && !line.contains("\"")) {
			line = line.replace("且", "&&");
		}
		if (line.contains("或") && !line.contains("\"")) {
			line = line.replace("或", "||");
		}
		result.canAsync = true;
		result.code = line;
		return result;
	}
}
