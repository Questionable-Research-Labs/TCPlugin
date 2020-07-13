package me.nathan.tcplugin.chat;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class FidgetSpinner implements Listener {
	@EventHandler
	public static void onChat(AsyncPlayerChatEvent event) {
		if (event.getMessage().toLowerCase().contains("fidget spinner") || event.getMessage().toLowerCase().contains("fidget spinner")) {

		}
	}
}
