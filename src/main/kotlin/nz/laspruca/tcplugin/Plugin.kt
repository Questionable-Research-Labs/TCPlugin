package nz.laspruca.tcplugin

import nz.laspruca.tcplugin.commands.Bee
import nz.laspruca.tcplugin.commands.DiscordCommand
import nz.laspruca.tcplugin.commands.GiveBaton
import nz.laspruca.tcplugin.commands.generateText
import nz.laspruca.tcplugin.discord.Discord
import nz.laspruca.tcplugin.eventListeners.PlayerJoinLeave
import nz.laspruca.tcplugin.eventListeners.loadBlocks
import nz.laspruca.tcplugin.eventListeners.loadCrops
import nz.laspruca.tcplugin.logger.init
import org.bukkit.scheduler.BukkitRunnable
import org.qrl.tcplugin.TCPlugin.Companion.config
import org.qrl.tcplugin.TCPlugin.Companion.logger
import org.qrl.tcplugin.TCPlugin.Companion.plugin
import org.qrl.tcplugin.TCPluginComponent
import java.awt.TrayIcon
import java.io.IOException

class Plugin : TCPluginComponent {
    companion object {
        var discord: Discord? = null
        var baton = false
        var hardcore = false
        var announceDeath = false
        var discordBot = false
        var discordServerID: String? = null
        var discordChannelID: String? = null
        var discordPrefix: String? = null
        var discordToken: String? = null
    }

    override fun onEnable() {
        init()

        logger.info("Starting plugin! __!__")


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
            discordBot = getBoolean("discordBot")
            discordServerID = getString("discordServerID")
            discordChannelID = getString("discordChannelID")
            discordPrefix = getString("prefix")
            discordToken = getString("discordToken")
        }

        discord = Discord()


        loadBlocks()
        loadCrops()

        // Create event to notify people that they can join the discord if they are not already

        // Create event to notify people that they can join the discord if they are not already
        object : BukkitRunnable() {
            override fun run() {
                try {

                    val discordUsers = discord!!.getMembers()
                    for (player in plugin.server.onlinePlayers) {
                        if (!discordUsers.contains(player.name)) {
                            player.sendMessage(generateText().toString())
                        }
                    }
                } catch (ex: IllegalStateException) {
                    // This happens if discord failed to init
                    ex.printStackTrace()
                }
            }
        }.runTaskTimerAsynchronously(plugin, 36000, 36000)
    }

    override fun onDisable() {
        // update and save config
        config["givebaton"] = baton
        config["hardcore"] = hardcore
        config["announceDeath"] = announceDeath
        config["discordBot"] = discordBot
        config["discordServerID"] = discordServerID
        config["discordChannelID"] = discordChannelID
        config["prefix"] = discordPrefix
        config["discordToken"] = discordToken

        try {
            config.save("${plugin.dataFolder}/config.yml")
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