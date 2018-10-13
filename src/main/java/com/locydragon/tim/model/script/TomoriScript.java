package com.locydragon.tim.model.script;

import com.esotericsoftware.reflectasm.MethodAccess;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.List;
import java.util.concurrent.atomic.LongAdder;

public class TomoriScript {
	public static LongAdder longAdder = new LongAdder();
	public static final String fatherClass = "com.locydragon.tim.TomoriScript_";
	private String pattern;
	private List<String> code;
	private CtClass scriptClass;
	public Class<?> target;
	public Object newInstance;
	public MethodAccess methodAccess;

	static {
		longAdder.reset();
	}

	protected TomoriScript(String pattern, List<String> code) {
		this.pattern = pattern;
		this.code = code;
		longAdder.increment();
		scriptClass = ClassPool.getDefault().makeClass(fatherClass+longAdder.intValue());
		StringBuilder builderSource = new StringBuilder();
		builderSource.append("public void run(Player p, Entity target, EntityDamageByEntityEvent e, String x) {");
		for (String obj : code) {
			builderSource.append(obj).append("\n");
		}
		builderSource.append("}");
		try {
			CtMethod mainMethod = CtMethod.make(builderSource.toString(), scriptClass);
			scriptClass.addMethod(mainMethod);
		} catch (CannotCompileException e) {
			Bukkit.getLogger().info("无法加载该脚本: "+this.pattern);
			Bukkit.getLogger().info("原因：脚本内代码不正确或编码不对.");
			return;
		}
		try {
			this.target = this.scriptClass.toClass();
		} catch (CannotCompileException e) {
			e.printStackTrace();
		}
		try {
			this.newInstance = this.target.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		this.methodAccess = MethodAccess.get(this.target);
		this.pattern = pattern.replace("<input>", "+\\S+");
	}

	public boolean run(Player who, Entity target, EntityDamageByEntityEvent e, String x) {
		this.methodAccess.invoke(this.newInstance, "run", who, target, e, x);
		return true;
	}

	public boolean match(String lore) {
		return lore.matches(this.pattern);
	}

	public String valueIn(String loreInput) {
		String lore = ChatColor.stripColor(loreInput);
		if (!match(lore)) {
			return null;
		}
		String newPrefix = this.pattern.replace("+\\S+", " ");
		for (String split : newPrefix.split(" ")) {
			split = split.trim();
			lore = lore.replace(split, "");
		}
		return lore.trim();
	}

	public String getPattern() {
		return this.pattern;
	}
}
