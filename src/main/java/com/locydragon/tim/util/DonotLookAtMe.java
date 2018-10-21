package com.locydragon.tim.util;

import java.util.ArrayList;
import java.util.List;

public class DonotLookAtMe {
	public static List<String> performance = new ArrayList<>();
	static {
		//这段代码太骚了 别看 丢人
		String info =
				"    org.bukkit\n" +
						"    org.bukkit.block\n" +
						"    org.bukkit.command\n" +
						"    org.bukkit.command.defaults\n" +
						"    org.bukkit.configuration\n" +
						"    org.bukkit.configuration.file\n" +
						"    org.bukkit.configuration.serialization\n" +
						"    org.bukkit.conversations\n" +
						"    org.bukkit.enchantments\n" +
						"    org.bukkit.entity\n" +
						"    org.bukkit.entity.minecart\n" +
						"    org.bukkit.event\n" +
						"    org.bukkit.event.block\n" +
						"    org.bukkit.event.enchantment\n" +
						"    org.bukkit.event.entity\n" +
						"    org.bukkit.event.hanging\n" +
						"    org.bukkit.event.inventory\n" +
						"    org.bukkit.event.painting\n" +
						"    org.bukkit.event.player\n" +
						"    org.bukkit.event.server\n" +
						"    org.bukkit.event.vehicle\n" +
						"    org.bukkit.event.weather\n" +
						"    org.bukkit.event.world\n" +
						"    org.bukkit.generator\n" +
						"    org.bukkit.help\n" +
						"    org.bukkit.inventory\n" +
						"    org.bukkit.inventory.meta\n" +
						"    org.bukkit.map\n" +
						"    org.bukkit.material\n" +
						"    org.bukkit.metadata\n" +
						"    org.bukkit.permissions\n" +
						"    org.bukkit.plugin\n" +
						"    org.bukkit.plugin.java\n" +
						"    org.bukkit.plugin.messaging\n" +
						"    org.bukkit.potion\n" +
						"    org.bukkit.projectiles\n" +
						"    org.bukkit.scheduler\n" +
						"    org.bukkit.scoreboard\n" +
						"    org.bukkit.util\n" +
						"    org.bukkit.util.io\n" +
						"    org.bukkit.util.noise\n" +
						"    org.bukkit.util.permissions\n";
		for (String s : info.split("\n")) {
			performance.add(s.trim());
		}
	}
	public static void init() {}
}
