package org.qrl.tcplugin

import nz.laspruca.tcplugin.Plugin
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.event.Listener
import java.util.logging.Logger

class TCPlugin : org.bukkit.plugin.java.JavaPlugin() {
    var compoents: List<TCPluginComponent> = listOf(
        Plugin()
    )

    companion object {
        lateinit var plugin: TCPlugin
        lateinit var config: FileConfiguration
        lateinit var logger: Logger
    }

    override fun onEnable() {
        plugin = getPlugin(this.javaClass)
        TCPlugin.config = config
        TCPlugin.logger = logger

        saveDefaultConfig()

        for (component in compoents) {
            component.onEnable()
        }
    }

    override fun onDisable() {
        for (component in compoents) {
            component.onDisable()
        }
    }

    fun registerEvent(e: Listener) {
        server.pluginManager.registerEvents(e, this)
    }
}
