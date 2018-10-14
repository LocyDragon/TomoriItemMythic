package com.locydragon.tim.model.script.compile;

import com.locydragon.tim.model.script.Compiler;
import com.locydragon.tim.model.script.Result;
import org.bukkit.util.StringUtil;

public class PlayerMethodCompiler implements Compiler {

	@Override
	public Result onInput(String line) {
		Result result = new Result();
		if (StringUtil.startsWithIgnoreCase(line.trim(), "printf")
				|| StringUtil.startsWithIgnoreCase(line.trim(), "print") ||
				StringUtil.startsWithIgnoreCase(line.trim(), "输出")) {
			String codeSource = "p.sendMessage(";
			line = line.replaceAll("(?i)printf", codeSource).replaceAll("(?i)print", codeSource)
					.replaceAll("输出", codeSource);
			line += ");";
		} if (line.startsWith("玩家说")) {
			String codeSource = "p.chat(";
			line = line.replaceAll("玩家说", codeSource);
			line += ");";
		} if (line.contains("玩家的地址") || line.contains("玩家地址")) {
			String codeSource = "p.getAddress().toString()";
			line = line.replaceAll("玩家的地址", codeSource).replaceAll("玩家地址", codeSource);
		} if (line.contains("可以飞行")) {
			String codeSource = "p.getAllowFlight()";
			line = line.replaceAll("可以飞行", codeSource);
		} if (line.contains("玩家显示名字") || line.contains("玩家的显示名字")) {
			String codeSource = "p.getDisplayName()";
			line = line.replaceAll("玩家显示名字", codeSource).replaceAll("玩家的显示名字", codeSource);
		} if (line.contains("玩家飞行速度") || line.contains("玩家的飞行速度")) {
			String codeSource = "p.getFlySpeed()";
			line = line.replaceAll("玩家飞行速度", codeSource).replaceAll("玩家的飞行速度", codeSource);
		} if (line.contains("玩家饱食度") || line.contains("玩家的饱食度")) {
			String codeSource = "p.getFoodLevel()";
			line = line.replaceAll("玩家饱食度", codeSource).replaceAll("玩家的饱食度", codeSource);
		} if (line.contains("玩家等级") || line.contains("玩家的等级")) {
			String codeSource = "p.getLevel()";
			line = line.replaceAll("玩家等级", codeSource).replaceAll("玩家的等级", codeSource);
		} if (line.contains("玩家地点") || line.contains("玩家的地点")) {
			String codeSource = "p.getLocale()";
			line = line.replaceAll("玩家地点", codeSource).replaceAll("玩家的地点", codeSource);
		} if (line.contains("玩家经验") || line.contains("玩家的经验")) {
			String codeSource = "p.getTotalExperience()";
			line = line.replaceAll("玩家经验", codeSource).replaceAll("玩家的经验", codeSource);
		} if (line.contains("玩家速度") || line.contains("玩家的速度")) {
			String codeSource = "p.getWalkSpeed()";
			line = line.replaceAll("玩家速度", codeSource).replaceAll("玩家的速度", codeSource);
		} if (line.contains("玩家在飞行")) {
			line = line.replaceAll("玩家在飞行", "p.isFlying()");
		} if (line.contains("玩家在潜行")) {
			line = line.replaceAll("玩家在潜行", "p.isSneaking()");
		} if (line.contains("玩家在冲刺")) {
			line = line.replaceAll("玩家在冲刺", "p.isSprinting()");
		} if (line.contains("踢出玩家")) {
			line = line.replaceAll("踢出玩家", "p.kickPlayer(\"\");");
		} if (line.startsWith("设置等级")) {
			line = line.replaceAll("设置等级", "p.setLevel(");
			line += ");";
		}
		result.canAsync = true;
		result.code = line;
		return result;
	}
}
