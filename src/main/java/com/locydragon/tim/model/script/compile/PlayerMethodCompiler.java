package com.locydragon.tim.model.script.compile;

import com.locydragon.tim.TomoriItemMythic;
import com.locydragon.tim.model.script.Compiler;
import com.locydragon.tim.model.script.Result;
import org.bukkit.util.StringUtil;

public class PlayerMethodCompiler implements Compiler {

	@Override
	public Result onInput(String line) {
		Result result = new Result();
		boolean canAsync = true;
		if (StringUtil.startsWithIgnoreCase(line, "print") || line.startsWith("输出")) {
			String codeSource = "p.sendMessage(";
			line = line.replaceAll("(?i)print", codeSource).replace("输出", codeSource);
			line += ");";
		} if (line.startsWith("玩家说")) {
			String codeSource = "p.chat(";
			line = line.replace("玩家说", codeSource);
			line += ");";
		} if (line.contains("玩家的地址") || line.contains("玩家地址") && !line.contains("\"")) {
			String codeSource = "p.getAddress().toString()";
			line = line.replace("玩家的地址", codeSource).replace("玩家地址", codeSource);
		} if (line.contains("可以飞行") && !line.contains("\"")) {
			String codeSource = "p.getAllowFlight()";
			line = line.replace("可以飞行", codeSource);
		} if (line.contains("玩家显示名字") || line.contains("玩家的显示名字") && !line.contains("\"")) {
			String codeSource = "p.getDisplayName()";
			line = line.replace("玩家显示名字", codeSource).replace("玩家的显示名字", codeSource);
		} if (line.contains("玩家飞行速度") || line.contains("玩家的飞行速度") && !line.contains("\"")) {
			String codeSource = "p.getFlySpeed()";
			line = line.replace("玩家飞行速度", codeSource).replace("玩家的飞行速度", codeSource);
		} if (line.contains("玩家饱食度") || line.contains("玩家的饱食度") && !line.contains("\"")) {
			String codeSource = "p.getFoodLevel()";
			line = line.replace("玩家饱食度", codeSource).replace("玩家的饱食度", codeSource);
		} if (line.contains("玩家等级") || line.contains("玩家的等级") && !line.contains("\"")) {
			String codeSource = "p.getLevel()";
			line = line.replace("玩家等级", codeSource).replace("玩家的等级", codeSource);
		} if (line.contains("玩家地点") || line.contains("玩家的地点") && !line.contains("\"")) {
			String codeSource = "p.getLocale()";
			line = line.replace("玩家地点", codeSource).replace("玩家的地点", codeSource);
		} if (line.contains("玩家经验") || line.contains("玩家的经验") && !line.contains("\"")) {
			String codeSource = "p.getTotalExperience()";
			line = line.replace("玩家经验", codeSource).replace("玩家的经验", codeSource);
		} if (line.contains("玩家速度") || line.contains("玩家的速度") && !line.contains("\"")) {
			String codeSource = "p.getWalkSpeed()";
			line = line.replace("玩家速度", codeSource).replace("玩家的速度", codeSource);
		} if (line.contains("玩家在飞行") && !line.contains("\"")) {
			line = line.replace("玩家在飞行", "p.isFlying()");
		} if (line.contains("玩家在潜行") && !line.contains("\"")) {
			line = line.replace("玩家在潜行", "p.isSneaking()");
		} if (line.contains("玩家在冲刺") && !line.contains("\"")) {
			line = line.replace("玩家在冲刺", "p.isSprinting()");
		} if (line.contains("踢出玩家") && !line.contains("\"")) {
			line = line.replace("踢出玩家", "p.kickPlayer(\"\");");
		} if (line.startsWith("设置等级") && !line.contains("\"")) {
			line = line.replace("设置等级", "p.setLevel(");
			line += ");";
			result.canAsync = true;
			result.code = line;
			return result;
		} if (line.contains("玩家名字") || line.contains("玩家的名字")) {
			String codeSource = "p.getName()";
			line = line.replace("玩家名字", codeSource).replace("玩家的名字", codeSource);
		} if (line.contains("有管理员权限")) {
			String codeSource = "p.isOp()";
			line = line.replace("有管理员权限", codeSource);
		} if (line.startsWith("扣血")) {
			String codeSource = "p.damage(";
			line = line.replace("扣血", codeSource);
			line += ");";
			canAsync = false;
		} if (line.contains("玩家血量") || line.contains("玩家的血量")) {
			String codeSource = "p.getHealth()";
			line = line.replace("玩家血量", codeSource).replace("玩家的血量", codeSource);
		} if (line.contains("玩家最大血量") || line.contains("玩家的最大血量")) {
			String codeSource = "p.getMaxHealth()";
			line = line.replace("玩家最大血量", codeSource).replace("玩家的最大血量", codeSource);
		} if (line.contains("设置血量")) {
			String codeSource = "p.setHealth((double)";
			line = line.replace("设置血量", codeSource);
			line += ");";
			canAsync = false;
		} if (line.contains("设置最大血量")) {
			String codeSource = "p.setMaxHealth((double)";
			line = line.replace("设置最大血量", codeSource);
			line += ");";
			canAsync = false;
		} if (line.startsWith("给自己药效")) {
			//How to use: 给自己药效,药效ID,持续时间,等级
			try {
				String obj = line;
				String[] spliter = obj.split(",");
				line = "p.addPotionEffect(new PotionEffect(PotionEffectType.getById("+spliter[1]+"), getNumber("+spliter[2]+") * 20, getNumber("+spliter[3]+")));";
			} catch (Exception exc) {
				TomoriItemMythic.PLUGIN_INSTANCE.getLogger().info("请使用 \"给自己药效,药效ID,持续时间,等级\" 来修改这行代码.");
			}
			result.canAsync = false;
			result.code = line;
			return result;
		}
		result.canAsync = canAsync;
		result.code = line;
		return result;
	}
}
