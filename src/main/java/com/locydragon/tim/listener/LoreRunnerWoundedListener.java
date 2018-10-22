package com.locydragon.tim.listener;

import com.locydragon.tim.model.script.ScriptCar;
import com.locydragon.tim.model.script.TomoriScript;
import com.locydragon.tim.model.script.enums.ScriptListenerTypeEnum;
import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LoreRunnerWoundedListener implements Listener {
	private ExecutorService asyncPool = Executors.newCachedThreadPool();
	@EventHandler
	public void onWounded(EntityDamageByEntityEvent e) {
		if (e.getEntity() instanceof Player && e.getDamager() instanceof LivingEntity) {
			ItemStack[] armors = ((Player) e.getEntity()).getEquipment().getArmorContents();
			for (ItemStack armor : armors) {
				if (armor == null || !armor.hasItemMeta() || !armor.getItemMeta().hasLore()) {
					continue;
				}
				{
					Father:
					for (String loreEach : armor.getItemMeta().getLore()) {
						for (Map.Entry<String, TomoriScript> entry : ScriptCar.carSync.entrySet()) {
							if (entry.getValue().type == ScriptListenerTypeEnum.WOUNDED) {
								if (entry.getValue().match(ChatColor.stripColor(loreEach).trim())) {
									if (!entry.getValue().run(((Player) e.getEntity()), e.getDamager()
											, e, entry.getValue().valueIn(ChatColor.stripColor(loreEach)))) {
										break Father;
									}
								}
							}
						}
					}
				}
				{
					asyncPool.execute(() -> {
						Father:
						for (String loreEach : armor.getItemMeta().getLore()) {
							for (Map.Entry<String, TomoriScript> entry : ScriptCar.carAsync.entrySet()) {
								if (entry.getValue().type == ScriptListenerTypeEnum.WOUNDED) {
									if (entry.getValue().match(ChatColor.stripColor(loreEach).trim())) {
										if (!entry.getValue().run(((Player) e.getEntity()), e.getDamager()
												, e, entry.getValue().valueIn(ChatColor.stripColor(loreEach)))) {
											break Father;
										}
									}
								}
							}
						}
					});
				}
			}
		}
	}
}
