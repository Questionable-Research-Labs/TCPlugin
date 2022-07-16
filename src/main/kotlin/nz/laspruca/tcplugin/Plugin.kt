package nz.laspruca.tcplugin

import nz.laspruca.tcplugin.commands.Bee
import nz.laspruca.tcplugin.commands.DiscordCommand
import nz.laspruca.tcplugin.commands.GiveBaton
import nz.laspruca.tcplugin.eventListeners.PlayerJoinLeave
import nz.laspruca.tcplugin.eventListeners.loadBlocks
import nz.laspruca.tcplugin.eventListeners.loadCrops
import nz.laspruca.tcplugin.logger.init
import org.qrl.tcplugin.TCPlugin.Companion.config
import org.qrl.tcplugin.TCPlugin.Companion.plugin
import org.qrl.tcplugin.TCPluginComponent
import java.io.IOException

class Plugin : TCPluginComponent {
    companion object {
//        var discord: Discord? = null
        var baton = false
        var hardcore = false
        var announceDeath = false
    }

    override fun onEnable() {
        init()
//        discord = Discord()

        with(plugin) {
            // Register event listeners
            registerEvent(PlayerJoinLeave)
            registerEvent(nz.laspruca.tcplugin.eventListeners.InventoryInteract)
            registerEvent(nz.laspruca.tcplugin.eventListeners.BlockPlaceBreak)
            registerEvent(nz.laspruca.tcplugin.eventListeners.PlayerDie)
            registerEvent(nz.laspruca.tcplugin.eventListeners.YouDoNotRecogniseTheBodiesInTheWater)
            registerEvent(nz.laspruca.tcplugin.eventListeners.CropHarvestEvent)

            // Register command executors
            getCommand("givebaton")?.setExecutor(GiveBaton())
            getCommand("bee")?.setExecutor(Bee())
            getCommand("discord")?.setExecutor(DiscordCommand())
        }

        // Load config stuff
        with(config) {
            baton = getBoolean("givebaton")
            hardcore = getBoolean("hardcore")
            announceDeath = getBoolean("announceDeath")
        }

        loadBlocks()
        loadCrops()

        // Create event to notify people that they can join the discord if they are not already

        // Create event to notify people that they can join the discord if they are not already
//        object : BukkitRunnable() {
//            override fun run() {
//                try {
//
//                    val discordUsers = discord!!.getMembers()
//                    for (player in plugin.server.onlinePlayers) {
//                        if (!discordUsers.contains(player.name)) {
//                            player.sendMessage(Identity.nil(), generateText(), MessageType.CHAT)
//                        }
//                    }
//                } catch (ex: IllegalStateException) {
//                    // This happens if discord failed to init
//                    ex.printStackTrace()
//                }
//            }
//        }.runTaskTimerAsynchronously(plugin, 36000, 36000)
    }

    override fun onDisable() {
        // update and save config
        config["givebaton"] = baton
        config["hardcore"] = hardcore
        config["announceDeath"] = announceDeath

        try {
            config.save("${plugin.dataFolder}/config.yml")
        } catch (e: IOException) {
            e.printStackTrace()
        }

        // Shutdown discord
//        try {
//            discord?.exitDiscord()
//        } catch (ex: InterruptedException) {
//            logger.severe("Could not shutdown discord")
//        }
    }
}