package me.nathan.tcPlugin.player_logger;

import me.nathan.tcPlugin.commands.Discord;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerJoin implements Listener {
    @EventHandler
    public static void onPlayerJoin(PlayerJoinEvent e) {
        PlayerLogger.createLogFile(e.getPlayer().getUniqueId());
        PlayerLogger.logData(e.getPlayer().getUniqueId(), "Joined the game");
        e.getPlayer().spigot().sendMessage(Discord.generateText());
    }

    @EventHandler
    public static void onPlayerLeave(PlayerQuitEvent e){
        PlayerLogger.logData(e.getPlayer().getUniqueId(), "Left the game");
    }
}
