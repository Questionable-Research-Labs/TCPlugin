package me.nathan.tcPlugin.chat;

import me.leoko.advancedban.manager.TimeManager;
import me.leoko.advancedban.utils.Punishment;
import me.leoko.advancedban.utils.PunishmentType;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import static me.nathan.tcPlugin.TCPlugin.*;

public class FidgetSpinner implements Listener {
	@EventHandler
	public static void onChat(AsyncPlayerChatEvent event) {
		logger.info("onChat was called");
		if (event.getMessage().toLowerCase().contains("fidget spinner") || event.getMessage().toLowerCase().contains("fidget spinner")) {
			logger.info("Banning " + event.getPlayer().getName());
			new Punishment(event.getPlayer().getName(), event.getPlayer().getUniqueId().toString() + "",
					ChatColor.RED.toString() + ChatColor.BOLD.toString() + "No U",
					"TCPlugin - Ban hotwords", PunishmentType.BAN, TimeManager.getTime(),
					TimeManager.toMilliSec("5M"), "", -1).create();
		}
	}
}
