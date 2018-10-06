package com.locydragon.tim.io.item;

import com.locydragon.tim.TomoriItemMythic;
import com.locydragon.tim.io.listener.IOItemListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class IOItemMaker {
	public static ExecutorService pool = Executors.newCachedThreadPool();
	private List<String> arg1;
	private List<String> arg2;
	private List<String> lore;
	private Player who;
	private ItemStack compare;
	private int counter;
	public IOItemMaker(List<String> arg1, List<String> arg2, Player who, ItemStack compare) {
		this.arg1 = arg1;
		this.arg2 = arg2;
		this.lore = new ArrayList<>();
		this.who = who;
		this.compare = compare;
		this.counter = 0;
	}

	public void start() {
		pool.execute(new Runnable() {
			@Override
			public void run() {
				for (int i = 0;i < arg1.size();i++) {
					if (arg1.get(i).contains("<input>")) {
						counter++;
						if (counter - 1 >= arg2.size()) {
							who.sendMessage(ChatColor.GREEN+"请输入...");
						} else {
							who.sendMessage(arg2.get(counter - 1));
						}
						String returnInput = IOItemListener.blockedGetReturn(10, who.getName());
						if (returnInput == null) {
							who.sendMessage(ChatColor.RED+"你取消了使用模板...");
							return;
						}
						String loreNow = arg1.get(i);
						loreNow = loreNow.replace("<input>", returnInput);
						lore.add(loreNow);
					} else {
						lore.add(arg1.get(i));
					}
				}
				Bukkit.getScheduler().runTask(TomoriItemMythic.PLUGIN_INSTANCE, new Runnable() {
					@Override
					public void run() {
						if (!who.getItemInHand().isSimilar(compare)) {
							who.sendMessage(ChatColor.RED+"你取消了使用模板...");
							return;
						} else {
							ItemStack inHand = who.getItemInHand();
							ItemMeta meta = inHand.getItemMeta();
							meta.setLore(lore);
							inHand.setItemMeta(meta);
							who.setItemInHand(inHand);
							who.updateInventory();
						}
					}
				});
			}
		});
	}
}
