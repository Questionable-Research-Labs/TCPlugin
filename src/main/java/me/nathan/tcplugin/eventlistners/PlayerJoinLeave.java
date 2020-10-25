package me.nathan.tcplugin.eventlistners;

import me.nathan.tcplugin.commands.DiscordCommand;
import me.nathan.tcplugin.logger.loggerevent.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import static me.nathan.tcplugin.TCPlugin.*;

public class PlayerJoinLeave implements Listener {

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		new JoinEvent(event.getPlayer().getDisplayName()).log();
		if (!discord.getMembers().contains(event.getPlayer().getDisplayName())) {
			event.getPlayer().spigot().sendMessage(DiscordCommand.generateText());
		}
	}

	@EventHandler
	public void onLeaveJoin(PlayerQuitEvent event) {
		new LeaveEvent(event.getPlayer().getDisplayName()).log();
	}
}
