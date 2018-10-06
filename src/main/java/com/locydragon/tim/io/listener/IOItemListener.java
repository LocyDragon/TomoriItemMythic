package com.locydragon.tim.io.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class IOItemListener implements Listener {
	public static ConcurrentHashMap<String,String> langMap = new ConcurrentHashMap<>();
	public static ConcurrentLinkedQueue<String> playerJob = new ConcurrentLinkedQueue<>();

	@EventHandler
	public void onPlayerIOChat(AsyncPlayerChatEvent e) {
		if (playerJob.contains(e.getPlayer().getName())) {
			langMap.put(e.getPlayer().getName(), e.getMessage());
		}
	}

	/**
	 * 堵塞地获取玩家聊天的返回值(切记异步调用)
	 * 可能返回null因为超时而玩家没有输入
	 * @param waitSeconds 等待的秒数
	 * @param player 玩家对象
	 * @return 玩家输入聊天框的信息
	 */
	public static String blockedGetReturn(int waitSeconds, String player) {
		playerJob.add(player);
		int counter = 0;
		while (true) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			counter++;
			if (counter == waitSeconds) {
				playerJob.remove(player);
				if (langMap.get(player) != null) {
					//再试一次
					playerJob.remove(player);
					langMap.remove(player);
					return langMap.get(player);
				}
				return null;
			}
			if (langMap.get(player) != null) {
				playerJob.remove(player);
				langMap.remove(player);
				return langMap.get(player);
			}
		}
	}
}
