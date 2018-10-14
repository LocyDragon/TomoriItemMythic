package com.locydragon.tim;

import com.locydragon.tim.commands.CommandBus;
import com.locydragon.tim.commands.SubCommandBasic;
import com.locydragon.tim.commands.sub.CommandModelLoad;
import com.locydragon.tim.commands.sub.CommandShowVersion;
import com.locydragon.tim.commands.sub.CommandUseModel;
import com.locydragon.tim.io.FileConstantURLs;
import com.locydragon.tim.io.FileContains;
import com.locydragon.tim.io.listener.IOItemListener;
import com.locydragon.tim.listener.LoreRunnerListener;
import com.locydragon.tim.model.ModelMainFile;
import com.locydragon.tim.model.script.CompileBasic;
import com.locydragon.tim.model.script.ScriptLoader;
import com.locydragon.tim.model.script.compile.InterruptedCompiler;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;

/**
 * @author LocyDragon
 * github: https://github.com/LocyDragon/TomoriItemMythic
 */
public class TomoriItemMythic extends JavaPlugin {
	public static TomoriItemMythic PLUGIN_INSTANCE;
	public static FileConfiguration config;
	public static final String PLUGIN_CMD = "tim";

	@Override
	public void onEnable() {
		registerCompilers();
		infoTask();
		loadConfig();
		FileConstantURLs.init();
		Bukkit.getPluginCommand(PLUGIN_CMD).setExecutor(new CommandBus());
		Bukkit.getPluginManager().registerEvents(new LoreRunnerListener(), this);
		registerEvents();
		loadDefaultModel();
		loadModels();
		loadScripts();
		registerSubCmd();
	}

	public void infoTask() {
		Bukkit.getScheduler().runTaskLaterAsynchronously(this, () -> {
			Bukkit.getLogger().info("===========TomoriItemMythic==========");
			Bukkit.getLogger().info("欢迎使用 TIM 物品编辑器 v" + this.getDescription().getVersion());
			Bukkit.getLogger().info("您使用的是Locy系列插件: TomoriItemMythic 作者： LocyDragon ; QQ 2424441676");
			Bukkit.getLogger().info("小组: PluginCDTribe ; 欢迎加入QQ群: 546818810");
			Bukkit.getLogger().info("===========TomoriItemMythic==========");
		}, 20 * 10);
	}

	public void loadConfig() {
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
				Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(defaultFile), "GBK"));
				writer.write(FileContains.DEFAULT_MODEL_CONTENT);
				writer.close();
				if (isSuccess) {
					Bukkit.getLogger().info("加载默认模板成功...");
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
	}

	public void loadModels() {
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
	}

	public void loadScripts() {
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
	}

	public void registerCompilers() {
		CompileBasic.addListener(new InterruptedCompiler());
	}
}
