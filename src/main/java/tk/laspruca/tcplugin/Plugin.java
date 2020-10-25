package tk.laspruca.tcplugin;

import tk.laspruca.tcplugin.eventlistners.*;
import tk.laspruca.tcplugin.util.*;
import org.bukkit.configuration.file.*;

import static org.qrl.tcplugin.TCPlugin.*;

public class Plugin {
	public static Discord discord;

	public static void onEnable() {
		FileConfiguration config = PLUGIN.getConfig();
		discord = new Discord(config.getString("discordToken"));
		PLUGIN.registerEvent(new PlayerJoinLeave());
	}

	public static void onDisable() {
		discord.exitDiscord();
	}
}
