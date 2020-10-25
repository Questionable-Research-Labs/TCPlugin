package org.qrl.tcplugin;

import nz.laspruca.tcplugin.*;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class TCPlugin extends JavaPlugin {
	public static TCPlugin plugin;
	public static Logger logger;

	@Override
	public void onEnable() {
		plugin = TCPlugin.getPlugin(TCPlugin.class);
		logger = plugin.getLogger();
		saveDefaultConfig();
		Plugin.onEnable();
	}

	@Override
	public void onDisable() {
		Plugin.onDisable();
	}

	public void registerEvent(Listener e) {
		getServer().getPluginManager().registerEvents(e, this);
	}
}