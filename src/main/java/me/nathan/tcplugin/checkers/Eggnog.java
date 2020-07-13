package me.nathan.tcplugin.checkers;

import me.nathan.tcplugin.TCPlugin;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import static org.bukkit.ChatColor.*;

public class Eggnog implements Listener {
	private static TCPlugin plugin = TCPlugin.getPlugin(TCPlugin.class);
	@EventHandler
	public static void onPlayerChat(AsyncPlayerChatEvent e) {
		if (e.getMessage().toLowerCase().contains("eggnog")) {
			Bukkit.getScheduler().runTask(plugin, () -> {
				e.getPlayer().kickPlayer(BOLD.toString() + RED.toString() + "No U");
			});
		}
	}
}
