package me.nathan.tcplugin;

import me.nathan.tcplugin.eventlistners.PlayerJoinLeave;
import me.nathan.tcplugin.util.Discord;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class TCPlugin extends JavaPlugin {
	public static final TCPlugin PLUGIN = TCPlugin.getPlugin(TCPlugin.class);
	public static final Logger LOGGER = PLUGIN.getLogger();
	public static Discord discord;

	@Override
	public void onEnable() {
		FileConfiguration config = this.getConfig();
		registerEvent(new PlayerJoinLeave());
		discord = new Discord(config.getString("discordToken"));
	}

	@Override
	public void onDisable() {

	}

	private void registerEvent(Listener e) {
		getServer().getPluginManager().registerEvents(e, this);
	}
}
