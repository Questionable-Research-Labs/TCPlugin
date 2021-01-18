package nz.laspruca.tcplugin.eventlistners;

import nz.laspruca.tcplugin.commands.DiscordCommand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import nz.laspruca.tcplugin.logger.loggerevent.*;

import static nz.laspruca.tcplugin.Plugin.*;

public class PlayerJoinLeave implements Listener {

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		new JoinEvent(event.getPlayer().getDisplayName()).log();
		if (discord.loggedIn()) {
			if (!discord.getMembers().contains(event.getPlayer().getDisplayName())) {
				event.getPlayer().spigot().sendMessage(DiscordCommand.generateText());
			}
		}
	}

	@EventHandler
	public void onLeaveJoin(PlayerQuitEvent event) {
		new LeaveEvent(event.getPlayer().getDisplayName()).log();
	}
}
