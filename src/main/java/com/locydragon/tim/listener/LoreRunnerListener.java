package com.locydragon.tim.listener;

import com.locydragon.tim.model.script.ScriptCar;
import com.locydragon.tim.model.script.TomoriScript;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LoreRunnerListener implements Listener {
	ExecutorService asyncPool = Executors.newCachedThreadPool();
	@EventHandler
	public void onDamage(EntityDamageByEntityEvent e) {
		if (e.getDamager() instanceof Player && e.getEntity() instanceof LivingEntity) {
			ItemStack inPlayerHand = ((Player)e.getDamager()).getItemInHand();
			if (inPlayerHand != null && inPlayerHand.getType() != Material.AIR) {
				if (inPlayerHand.hasItemMeta() && inPlayerHand.getItemMeta().hasLore()) {
					{
						// /SyncModel
						for (String loreEach : inPlayerHand.getItemMeta().getLore()) {
							for (Map.Entry<String,TomoriScript> entry : ScriptCar.carSync.entrySet()) {
								if (entry.getValue().match(ChatColor.stripColor(loreEach))) {
									entry.getValue().run(((Player)e.getDamager()), e.getEntity(), e
											, entry.getValue().valueIn(ChatColor.stripColor(loreEach)));
								}
							}
						}
					}
					{
						//AsncM
						// odel
						asyncPool.execute(() -> {
							for (String loreEach : inPlayerHand.getItemMeta().getLore()) {
								for (Map.Entry<String,TomoriScript> entry : ScriptCar.carAsync.entrySet()) {
									if (entry.getValue().match(ChatColor.stripColor(loreEach))) {
										entry.getValue().run(((Player)e.getDamager()), e.getEntity()
												, e, entry.getValue().valueIn(ChatColor.stripColor(loreEach)));
									}
								}
							}
						});
					}
				}
			}
		}
	}
}
