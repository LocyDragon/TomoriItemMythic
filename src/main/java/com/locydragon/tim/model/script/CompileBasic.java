package com.locydragon.tim.model.script;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public class CompileBasic {
	public static List<Compiler> car = new ArrayList<>();
	public static void addListener(Compiler compiler) {
		car.add(compiler);
	}

	public static Result compileAndFindCanAsync(List<String> obj) {
		Result result = new Result();
		boolean tag = true;
		List<String> codeList = new ArrayList<>();
		for (String line : obj) {
			line = ChatColor.translateAlternateColorCodes('&', line.trim());
			for (Compiler compiler : car) {
				Result resultEach = compiler.onInput(line);
				line = resultEach.code;
				tag = tag && resultEach.canAsync;
			}
			if (!(line.contains("{") || line.contains("}")) && !line.endsWith(";")) {
				line = new StringBuilder().append(line).append(";").toString();
			}
			codeList.add(line);
		}
		result.canAsync = tag;
		result.codeList = codeList;
		return result;
	}
}
