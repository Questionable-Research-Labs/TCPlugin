package org.qrl.tcplugin;

import tk.laspruca.tcplugin.*;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class TCPlugin extends JavaPlugin {
	public static final TCPlugin PLUGIN = TCPlugin.getPlugin(TCPlugin.class);
	public static final Logger LOGGER = PLUGIN.getLogger();

	@Override
	public void onEnable() {
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
