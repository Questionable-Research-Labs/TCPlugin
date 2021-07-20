package nz.laspruca.tcplugin

import net.kyori.adventure.audience.MessageType
import net.kyori.adventure.identity.Identity
import nz.laspruca.tcplugin.commands.Bee
import nz.laspruca.tcplugin.commands.DiscordCommand
import nz.laspruca.tcplugin.commands.GiveBaton
import nz.laspruca.tcplugin.commands.generateText
import nz.laspruca.tcplugin.discord.Discord
import nz.laspruca.tcplugin.eventListeners.*
import nz.laspruca.tcplugin.logger.init
import org.bukkit.scheduler.BukkitRunnable
import org.qrl.tcplugin.TCPlugin.Companion.config
import org.qrl.tcplugin.TCPlugin.Companion.logger
import org.qrl.tcplugin.TCPlugin.Companion.plugin
import org.qrl.tcplugin.TCPluginComponent
import java.io.IOException

class Plugin : TCPluginComponent {
    companion object {
        var discord: Discord? = null
        var baton = false
        var hardcore = false
        var announceDeath = false
    }

    override fun onEnable() {
        init()
//        discord = Discord()

        plugin.apply {
            plugin.registerEvent(PlayerJoinLeave)
            plugin.registerEvent(InventoryInteract)
            plugin.registerEvent(BlockPlaceBreak)
            plugin.registerEvent(PlayerDie)
        }

        loadBlocks()

        baton = config.getBoolean("givebaton")
        hardcore = config.getBoolean("hardcore")
        announceDeath = config.getBoolean("announceDeath")

        // Create event to notify people that they can join the discord if they are not already

        // Create event to notify people that they can join the discord if they are not already
        object : BukkitRunnable() {
            override fun run() {
                try {
                   if (discord != null) {
                       val discordUsers = discord!!.getMembers()
                       for (player in plugin.server.onlinePlayers) {
                           if (!discordUsers.contains(player.name)) {
                               player.sendMessage(Identity.nil(), generateText(), MessageType.CHAT)
                           }
                       }
                   }
                } catch (ex: IllegalStateException) {
                    // This happens if discord failed to init
                    ex.printStackTrace()
                }
            }
        }.runTaskTimerAsynchronously(plugin, 36000, 36000)
        plugin.getCommand("givebaton")?.setExecutor(GiveBaton())
        plugin.getCommand("bee")?.setExecutor(Bee())
        plugin.getCommand("discord")?.setExecutor(DiscordCommand())
    }

    override fun onDisable() {
        // update and save config
        config["givebaton"] = baton
        config["hardcore"] = hardcore
        config["announceDeath"] = announceDeath

        try {
            config.save("${plugin?.dataFolder}/config.yml")
        } catch (e: IOException) {
            e.printStackTrace()
        }

        // Shutdown discord
        try {
            discord?.exitDiscord()
        } catch (ex: InterruptedException) {
            logger.severe("Could not shutdown discord")
        }
    }
}