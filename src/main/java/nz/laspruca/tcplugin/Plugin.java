package nz.laspruca.tcplugin;

import nz.laspruca.tcplugin.eventlistners.*;
import nz.laspruca.tcplugin.util.*;
import org.bukkit.configuration.file.*;

import static org.qrl.tcplugin.TCPlugin.*;

public class Plugin {
	public static Discord discord;

	public static void onEnable() {
		FileConfiguration config = plugin.getConfig();
		discord = new Discord(config.getString("discordToken"));
		plugin.registerEvent(new PlayerJoinLeave());
	}

	public static void onDisable() {
		discord.exitDiscord();
	}
}
