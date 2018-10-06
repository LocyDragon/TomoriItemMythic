package com.locydragon.tim;

import com.locydragon.tim.commands.CommandBus;
import com.locydragon.tim.io.FileConstantURLs;
import com.locydragon.tim.io.listener.IOItemListener;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

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
		infoTask();
		loadConfig();
		FileConstantURLs.init();
		Bukkit.getPluginCommand(PLUGIN_CMD).setExecutor(new CommandBus());
		registerEvents();
	}

	public void infoTask() {
		Bukkit.getScheduler().runTaskLater(this, () -> {
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
}
