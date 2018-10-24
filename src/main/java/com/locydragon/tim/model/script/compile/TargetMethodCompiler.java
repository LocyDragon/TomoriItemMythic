package com.locydragon.tim.model.script.compile;

import com.locydragon.tim.TomoriItemMythic;
import com.locydragon.tim.model.script.Compiler;
import com.locydragon.tim.model.script.Result;

public class TargetMethodCompiler implements Compiler {
	@Override
	public Result onInput(String line) {
		Result result = new Result();
		boolean canAsync = true;
		if (line.startsWith("给对手药效")) {
			try {
				String obj = line;
				String[] spliter = obj.split(",");
				line = "target.addPotionEffect(new PotionEffect(PotionEffectType.getById("+spliter[1]+"), getNumber("+spliter[2]+") * 20, getNumber("+spliter[3]+")));";
			} catch (Exception exc) {
				TomoriItemMythic.PLUGIN_INSTANCE.getLogger().info("请使用 \"给对手药效,药效ID,持续时间,等级\" 来修改这行代码.");
			}
			result.canAsync = false;
			result.code = line;
			return result;
		}
		if (line.contains("对手名字") || line.contains("对手的名字")) {
			String code = "target.getCustomName()";
			line = line.replace("对手名字", code).replace("对手的名字", code);
		}
		if (line.startsWith("给对手发送信息")) {
			String codeSource = "target.sendMessage(";
			line = line.replace("给对手发送信息", codeSource);
			line += ");";
		}
		if (line.contains("设置对手血量")) {
			String codeSource = "target.setHealth(getNumber(";
			line = line.replace("设置对手血量", codeSource);
			line += "));";
			canAsync = false;
		}
		if (line.contains("设置对手最大血量")) {
			String codeSource = "target.setMaxHealth(getNumber(";
			line = line.replace("设置对手最大血量", codeSource);
			line += "));";
			canAsync = false;
		}
		if (line.contains("对手血量") || line.contains("对手的血量")) {
			String codeSource = "target.getHealth()";
			line = line.replace("对手血量", codeSource).replace("对手的血量", codeSource);
		}
		if (line.contains("对手最大血量") || line.contains("对手的最大血量")) {
			String codeSource = "target.getMaxHealth()";
			line = line.replace("对手最大血量", codeSource).replace("对手的最大血量", codeSource);
		}
		if (line.startsWith("扣对手血")) {
			String codeSource = "target.damage(";
			line = line.replace("扣对手血", codeSource);
			line += ");";
			canAsync = false;
		}
		result.canAsync = canAsync;
		result.code = line;
		return result;
	}
}
