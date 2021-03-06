package com.locydragon.tim.model.script;

import com.esotericsoftware.reflectasm.MethodAccess;
import com.locydragon.tim.model.script.enums.ScriptListenerTypeEnum;
import com.locydragon.tim.util.DonotLookAtMe;
import javassist.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;
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
	public ScriptListenerTypeEnum type = ScriptListenerTypeEnum.EMPTY;

	static {
		longAdder.reset();
	}

	protected TomoriScript(String pattern, List<String> code) {
		this.pattern = pattern;
		this.code = code;
		longAdder.increment();
		ClassPool defaultPool = ClassPool.getDefault();
		/**
		 Path path = Paths.get(Bukkit.class.getProtectionDomain().getCodeSource().getLocation().toString().substring(6));
		 String pathThis = null;
		 try {
		 pathThis = URLDecoder.decode(new String(path.toFile().getAbsolutePath().getBytes("utf-8"), "GBK"), "GBK");
		 try {
		 defaultPool.insertClassPath(pathThis);
		 } catch (NotFoundException e) {
		 e.printStackTrace();
		 }
		 } catch (UnsupportedEncodingException e) {
		 e.printStackTrace();
		 }
		 **/
		defaultPool.importPackage("com.locy.Helper");
		DonotLookAtMe.performance.forEach(x -> defaultPool.importPackage(x));
		defaultPool.importPackage("java.util.Random");
		scriptClass = defaultPool.makeClass(fatherClass + longAdder.intValue());
		try {
			CtMethod methodToNumber = CtMethod.make("public int getNumber(String x) {" +
					"StringBuilder builder = new StringBuilder();" +
					"char[] charSet = x.toCharArray();" +
					"for (int i = 0;i < charSet.length;i++) {" +
					"char each = charSet[i];" +
					"if (Character.isDigit(each)) {" +
					"builder.append(each);" +
					"}" +
					"}" +
					"if (builder.length() == 0) {" +
					"builder.append(\"0\");" +
					"}" +
					"return Integer.valueOf(builder.toString()).intValue();" +
					"}", scriptClass);
			scriptClass.addMethod(methodToNumber);
			CtMethod methodToNumberTwo = CtMethod.make("public int getNumber(int x) { return x; }", scriptClass);
			scriptClass.addMethod(methodToNumberTwo);
			CtMethod methodToNumberThird = CtMethod.make("public int getNumber(Integer x) { return x.intValue(); }", scriptClass);
			scriptClass.addMethod(methodToNumberThird);
			CtMethod methodToNumberForth = CtMethod.make("public int getNumber(Double x) { return x.intValue(); }", scriptClass);
			scriptClass.addMethod(methodToNumberForth);
			CtMethod methodToNumberFifth = CtMethod.make("public int getNumber(double x) { return (int)x; }", scriptClass);
			scriptClass.addMethod(methodToNumberFifth);
		} catch (CannotCompileException e) {
			e.printStackTrace();
		}
		try {
			CtMethod methodToNumber = CtMethod.make("public boolean odds(int chance) {" +
					"if (chance + 1 > (int) (Math.random() * 101)) {" +
					"return true;" +
					"}" +
					"return false;" +
					"}", scriptClass);
			scriptClass.addMethod(methodToNumber);
		} catch (CannotCompileException e) {
			e.printStackTrace();
		}
		try {
			CtMethod mainMethod = CtMethod.make("public Integer toInt(int obj) { return Integer.valueOf(obj);}", scriptClass);
			scriptClass.addMethod(mainMethod);
			CtMethod mainMethodSecond = CtMethod.make("public Integer toInt(Integer obj) { return obj;}", scriptClass);
			scriptClass.addMethod(mainMethodSecond);
		} catch (CannotCompileException e) {
			e.printStackTrace();
		}
		try {
			CtMethod methodSpawnRandom = CtMethod.make("public int randomInArea(int max, int min) {" +
					"return (int)(Math.random() * (min + 1) + max);" +
					"}", scriptClass);
			scriptClass.addMethod(methodSpawnRandom);
		} catch (CannotCompileException e) {
			e.printStackTrace();
		}
		StringBuilder builderSource = new StringBuilder();
		builderSource.append("public boolean run(Player p, LivingEntity target, EntityDamageByEntityEvent e, String x) {");
		for (String obj : code) {
			builderSource.append(obj).append("\n");
		}
		builderSource.append("return true;\n");
		builderSource.append("}");
		try {
			CtMethod mainMethod = CtMethod.make(builderSource.toString(), scriptClass);
			scriptClass.addMethod(mainMethod);
		} catch (CannotCompileException e) {
			Bukkit.getLogger().info("无法加载该脚本: " + this.pattern);
			Bukkit.getLogger().info("原因：脚本内代码不正确或编码不对.");
			Bukkit.getLogger().info("报错输出: [" + e.getMessage() + "][" + e.getReason() + "]");
			Bukkit.getLogger().info("报错输出: "+builderSource.toString());
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
		if (pattern.startsWith("+")) {
			pattern = new StringBuilder().append("\\").append(pattern).toString();
		}
		this.pattern = pattern.replace("<input>", "+\\S+");
	}

	public void setListenerDependOn(String input) {
		if (input == null || input.trim().length() == 0) {
			this.type = ScriptListenerTypeEnum.EMPTY;
			return;
		}
		if (input.equalsIgnoreCase("ignore")) {
			this.type = ScriptListenerTypeEnum.IGNORE;
			return;
		}
		if (input.equalsIgnoreCase("wound")) {
			this.type = ScriptListenerTypeEnum.WOUND;
			return;
		}
		if (input.equalsIgnoreCase("wounded")) {
			this.type = ScriptListenerTypeEnum.WOUNDED;
			return;
		}
		this.type = ScriptListenerTypeEnum.EMPTY;
	}

	public boolean run(Player who, LivingEntity target, EntityDamageByEntityEvent e, String x) {
		boolean returnType = (boolean)this.methodAccess.invoke(this.newInstance, "run", who, target, e, getMagic(x));
		return returnType;
	}

	public boolean match(String lore) {
		return lore.matches(this.pattern);
	}

	public String valueIn(String loreInput) {
		String lore = ChatColor.stripColor(loreInput);
		String newPrefix = this.pattern.replace("+\\S+", " ");
		for (String split : newPrefix.split(" ")) {
			split = split.trim();
			lore = lore.replace(split, "");
		}
		return lore.trim();
	}

	private String getMagic(String obj) {
		StringBuilder defaultBuilder = new StringBuilder();
		StringBuilder numberBuilder = new StringBuilder();
		StringBuilder numberSecondBuilder = new StringBuilder();
		boolean firstOrSecondLock = false;
		boolean foundFirst = false;
		boolean foundSecond = false;
		boolean shut = false;
		for (char c : obj.toCharArray()) {
			defaultBuilder.append(c);
			if (!shut) {
				if (Character.isDigit(c) && !firstOrSecondLock && !foundFirst) {
					numberBuilder.append(c);
					foundFirst = true;
				} else if (!Character.isDigit(c) && foundFirst) {
					if (firstOrSecondLock) {
						shut = true;
					}
					firstOrSecondLock = true;
				} else if (Character.isDigit(c) && firstOrSecondLock) {
					numberSecondBuilder.append(c);
					foundSecond = true;
				} else if (!Character.isDigit(c) && foundSecond) {
					shut = true;
				}
			}
		}
		if (numberBuilder.length() == 0 || numberSecondBuilder.length() == 0) {
			return defaultBuilder.toString();
		} else {
			int firstNum = Integer.valueOf(numberBuilder.toString());
			int secondNum = Integer.valueOf(numberSecondBuilder.toString());
			if (firstNum == secondNum) {
				return defaultBuilder.toString();
			} else if (firstNum > secondNum) {
				return String.valueOf(getRandom(secondNum, firstNum));
			} else {
				return String.valueOf(getRandom(firstNum, secondNum));
			}
		}
	}

	public static String getRandom(int min, int max){
		Random random = new Random();
		int s = random.nextInt(max - min + 1) + min;
		return String.valueOf(s);
	}

	public String getPattern() {
		return this.pattern;
	}
}
