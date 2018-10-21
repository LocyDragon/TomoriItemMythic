package com.locydragon.tim;

import com.locydragon.tim.commands.CommandBus;
import com.locydragon.tim.commands.SubCommandBasic;
import com.locydragon.tim.commands.sub.CommandModelLoad;
import com.locydragon.tim.commands.sub.CommandMonsterDrop;
import com.locydragon.tim.commands.sub.CommandShowVersion;
import com.locydragon.tim.commands.sub.CommandUseModel;
import com.locydragon.tim.io.FileConstantURLs;
import com.locydragon.tim.io.listener.IOItemListener;
import com.locydragon.tim.listener.LoreRunnerListener;
import com.locydragon.tim.listener.drop.MonsterDropListener;
import com.locydragon.tim.model.ModelMainFile;
import com.locydragon.tim.model.script.CompileBasic;
import com.locydragon.tim.model.script.ScriptLoader;
import com.locydragon.tim.model.script.compile.*;
import com.locydragon.tim.util.DonotLookAtMe;
import javassist.*;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author LocyDragon
 * github: https://github.com/LocyDragon/TomoriItemMythic
 */
public class TomoriItemMythic extends JavaPlugin {
	public static TomoriItemMythic PLUGIN_INSTANCE;
	public static FileConfiguration config;
	public static final String PLUGIN_CMD = "tim";
	public static FileConfiguration monsterData;
	public static File monsterDataFile;

	@Override
	public void onEnable() {
		DonotLookAtMe.init();
		makeSupporter();
		registerCompilers();
		infoTask();
		loadConfig();
		FileConstantURLs.init();
		Bukkit.getPluginCommand(PLUGIN_CMD).setExecutor(new CommandBus());
		Bukkit.getPluginManager().registerEvents(new LoreRunnerListener(), this);
		Bukkit.getPluginManager().registerEvents(new MonsterDropListener(), this);
		registerEvents();
		loadDefaultModel();
		loadModels();
		loadScripts();
		registerSubCmd();
		new Metrics(this);
	}

	public void infoTask() {
		Bukkit.getScheduler().runTaskLaterAsynchronously(this, () -> {
			Bukkit.getLogger().info("===========TomoriItemMythic==========");
			Bukkit.getLogger().info("欢迎使用 TIM 物品编辑器 v" + this.getDescription().getVersion());
			Bukkit.getLogger().info("您使用的是Locy系列插件: TomoriItemMythic 作者： LocyDragon ; QQ 2424441676");
			Bukkit.getLogger().info("小组: SpicyChicken/PluginCDTribe ; 欢迎加入QQ群: 546818810");
			Bukkit.getLogger().info("===========TomoriItemMythic==========");
		}, 20 * 10);
	}

	public void loadConfig() {
		File monsterSave = new File(".//plugins//TomoriItemMythic//Monster.data");
		boolean exist = true;
		if (!monsterSave.exists()) {
			monsterSave.getParentFile().mkdirs();
			exist = false;
			try {
				monsterSave.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		monsterData = YamlConfiguration.loadConfiguration(monsterSave);
		monsterDataFile = monsterSave;
		if (!exist) {
			monsterData.set("Point", 0);
			try {
				monsterData.save(monsterDataFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		PLUGIN_INSTANCE = this;
		saveDefaultConfig();
		config = getConfig();
	}

	public void registerEvents() {
		Bukkit.getPluginManager().registerEvents(new IOItemListener(), this);
	}

	public void loadDefaultModel() {
		File defaultFile = new File(FileConstantURLs.MODEL_LOCATION+"Default//Model.tim");
		if (!defaultFile.exists()) {
			boolean isSuccess = defaultFile.getParentFile().mkdirs();
			try {
				isSuccess = defaultFile.createNewFile() && isSuccess;
				FileConfiguration configDefault = YamlConfiguration.loadConfiguration(defaultFile);
				List<String> lore = new ArrayList<>();
				lore.add("&a&l&m一&b&l&m一&c&l&m一&e&l&m一&f&l&m一&1&l&m一&2&l&m一&3&l&m一");
				lore.add("&b加成攻击 >> &e<input> &b点 <<");
				lore.add("&b致命打击 >> &e<input> &b点 <<");
				lore.add("&7&l武器介绍 >> <input>");
				lore.add("&a&l&m一&b&l&m一&c&l&m一&e&l&m一&f&l&m一&1&l&m一&2&l&m一&3&l&m一");
				configDefault.set("ModelLore", lore);
				List<String> usage = new ArrayList<>();
				usage.add("&7[&a模板&7]&a欢迎使用我们的模板,现在,请在聊天栏输入武器加成的伤害点数.(阿拉伯数字一位)");
				usage.add("&7[&a模板&7]&a现在,请在聊天栏输入武器加成的力量效果时长点数.(阿拉伯数字一位)");
				usage.add("&7[&a模板&7]&a现在,请在聊天栏输入武器的介绍吧!");
				configDefault.set("UsingMessage", usage);
				configDefault.set("ModelName", "Default");
				configDefault.save(defaultFile);
				/**
				Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(defaultFile), "GBK"));
				writer.write(FileContains.DEFAULT_MODEL_CONTENT);
				writer.close();
				 **/
				File scriptDamageMaker = new File(FileConstantURLs.MODEL_LOCATION+"Default//Script//DamageCaller.tos");
				if (!scriptDamageMaker.exists()) {
					scriptDamageMaker.getParentFile().mkdirs();
					scriptDamageMaker.createNewFile();
					FileConfiguration damage = YamlConfiguration.loadConfiguration(scriptDamageMaker);
					damage.set("pattern", "加成攻击 >> <input> 点 <<");
					List<String> scriptDamage = new ArrayList<>();
					scriptDamage.add("设置伤害 数据(x)");
					scriptDamage.add("如果 百分比概率(25)");
					scriptDamage.add("输出 \"&7[&b剑魂&7]&a>> &6你造成了 \"+数据(x)+\" 点伤害!\"");
					scriptDamage.add("END IF");
					damage.set("script", scriptDamage);
					damage.save(scriptDamageMaker);
				}
				File scriptEffect = new File(FileConstantURLs.MODEL_LOCATION+"Default//Script//BlindHit.tos");
				if (!scriptEffect.exists()) {
					scriptEffect.getParentFile().mkdirs();
					scriptEffect.createNewFile();
					FileConfiguration effect = YamlConfiguration.loadConfiguration(scriptEffect);
					effect.set("pattern", "致命打击 >> <input> 点 <<");
					List<String> scriptEffects = new ArrayList<>();
					scriptEffects.add("给自己药效,5,x,2");
					effect.set("script", scriptEffects);
					effect.save(scriptEffect);
				}
				if (isSuccess) {
					Bukkit.getLogger().info("创建加载默认模板成功...");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void registerSubCmd() {
		SubCommandBasic.addListener(new CommandUseModel());
		SubCommandBasic.addListener(new CommandShowVersion());
		SubCommandBasic.addListener(new CommandModelLoad());
		SubCommandBasic.addListener(new CommandMonsterDrop());
	}

	public void loadModels() {
		Bukkit.getLogger().info("===========加载模板==========");
		int foundModel = 0;
		File targetModel = new File(FileConstantURLs.MODEL_LOCATION);
		Father:
		for (File inWhich : targetModel.listFiles()) {
			if (inWhich.isDirectory()) {
				for (File file : inWhich.listFiles()) {
					if (file.getName().trim().equalsIgnoreCase("Model.tim")) {
						new ModelMainFile(file);
						foundModel++;
						continue Father;
					}
				}
			}
		}
		Bukkit.getLogger().info("找到了 "+foundModel+" 个有效模板!");
		Bukkit.getLogger().info("===========完毕==========");
	}

	public void loadScripts() {
		Bukkit.getLogger().info("===========加载脚本==========");
		int foundScript = 0;
		File targetModel = new File(FileConstantURLs.MODEL_LOCATION);
		for (File inWhich : targetModel.listFiles()) {
			try {
				File scriptDir = new File(inWhich.getCanonicalPath()+"//Script");
				if (scriptDir.exists()) {
					for (File inScript : scriptDir.listFiles()) {
						if (inScript.getName().endsWith(".tos")) {
							ScriptLoader.load(inScript);
							foundScript++;
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		Bukkit.getLogger().info("找到了 "+foundScript+" 个有效脚本!");
		Bukkit.getLogger().info("其中 "+ScriptLoader.syncScriptNum+" 是不可异步脚本!");
		Bukkit.getLogger().info("其中 "+ScriptLoader.asyncScriptNum+" 是可异步脚本!");
		Bukkit.getLogger().info("===========完毕==========");
	}

	public void registerCompilers() {
		CompileBasic.addListener(new MathCompiler());
		CompileBasic.addListener(new LogicCompiler());
		CompileBasic.addListener(new EventCompiler());
		CompileBasic.addListener(new InterruptedCompiler());
		CompileBasic.addListener(new TargetMethodCompiler());
		CompileBasic.addListener(new PlayerMethodCompiler());
		CompileBasic.addListener(new FlowControlCompiler());
		//这个应该在最后一个
	}

	public void makeSupporter() {
		ClassPool pool = ClassPool.getDefault();
		pool.appendClassPath(new LoaderClassPath(ClassLoader.getSystemClassLoader()));
		CtClass classCt = pool.makeClass("com.locy.Helper");
		try {
			CtMethod mainMethod = CtMethod.make("public static Integer toInt(int obj) { return Integer.valueOf(obj);}", classCt);
			classCt.addMethod(mainMethod);
			CtMethod mainMethodSecond = CtMethod.make("public static Integer toInt(Integer obj) { return obj;}", classCt);
			classCt.addMethod(mainMethodSecond);
			Class<?> ctClass = classCt.toClass();
			getLogger().info("Create Support class in loader: "+ctClass.getClassLoader().getClass().getName());
		} catch (CannotCompileException e) {
			e.printStackTrace();
		}
	}
}
