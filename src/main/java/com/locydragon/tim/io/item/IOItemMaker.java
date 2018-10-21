package com.locydragon.tim.io.item;

import com.locydragon.tim.TomoriItemMythic;
import com.locydragon.tim.io.listener.IOItemListener;
import org.apache.commons.lang.StringUtils;
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
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				for (int i = 0;i < arg1.size();i++) {
					if (arg1.get(i).contains("<input>")) {
						counter++;
						if (counter - 1 >= arg2.size()) {
							who.sendMessage(ChatColor.GREEN+"请输入...");
						} else {
							who.sendMessage(ChatColor.translateAlternateColorCodes('&',
									arg2.get(counter - 1)));
						}
						String returnInput = IOItemListener.blockedGetReturn(30, who.getName());
						if (returnInput == null) {
							who.sendMessage(ChatColor.RED+"你取消了使用模板...");
							return;
						}
						if (returnInput.equals("skip") || returnInput.equals("跳过编辑")) {
							who.sendMessage(ChatColor.LIGHT_PURPLE+"你跳过了一行编辑...");
							continue;
						}
						if (returnInput.equals("cancel") || returnInput.equals("取消编辑")) {
							who.sendMessage(ChatColor.RED+"你取消了使用模板...");
							return;
						}
						if (StringUtils.isBlank(ChatColor.stripColor(returnInput).trim())) {
							who.sendMessage(ChatColor.RED+"你取消了使用模板...原因: 输入为空或只包含颜色代码");
							return;
						}
						String loreNow = arg1.get(i);
						loreNow = ChatColor.translateAlternateColorCodes('&',
								loreNow.replace("<input>", returnInput));
						lore.add(loreNow);
						who.sendMessage(ChatColor.LIGHT_PURPLE+"输入成功...["+returnInput+"]");
					} else {
						lore.add(ChatColor.translateAlternateColorCodes('&', arg1.get(i)));
					}
				}
				Bukkit.getScheduler().runTask(TomoriItemMythic.PLUGIN_INSTANCE, new Runnable() {
					@Override
					public void run() {
						if (!who.getItemInHand().isSimilar(compare)) {
							who.sendMessage(ChatColor.RED+"你取消了使用模板...原因: 你手上拿着的物品和一开始拿的那个不一样!");
							return;
						} else {
							ItemStack inHand = who.getItemInHand();
							ItemMeta meta = inHand.getItemMeta();
							meta.setLore(lore);
							inHand.setItemMeta(meta);
							who.setItemInHand(inHand);
							who.updateInventory();who.sendMessage(ChatColor.BLUE+"模板使用结束...");
							who.updateInventory();who.sendMessage(ChatColor.BLUE+"现在你手上物品的Lore被赋予了新的模板.");
						}
					}
				});
			}
		});
	}
}
