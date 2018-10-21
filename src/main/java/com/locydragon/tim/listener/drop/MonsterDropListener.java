package com.locydragon.tim.listener.drop;

import com.locydragon.tim.TomoriItemMythic;
import com.locydragon.tim.util.InScriptUtils;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public class MonsterDropListener implements Listener {
	@EventHandler
	public void onEntityDeath(EntityDeathEvent e) {
		if (e.getEntity().getCustomName() == null || e.getEntity().getCustomName().trim().length() == 0) {
			return;
		}
		String name = e.getEntity().getCustomName();
		for (String keyData : TomoriItemMythic.monsterData.getKeys(false)) {
			if (ChatColor.translateAlternateColorCodes('&', TomoriItemMythic.monsterData.getString(keyData+".monster"))
					.equals(name)) {
				int chance = TomoriItemMythic.monsterData.getInt(keyData+".chance");
				if (InScriptUtils.odds(chance)) {
					ItemStack dropItem = TomoriItemMythic.monsterData.getItemStack(keyData+".item");
					e.getEntity().getLocation()
							.getWorld().dropItemNaturally(e.getEntity().getLocation(), dropItem);
				}
			}
		}
	}
}
