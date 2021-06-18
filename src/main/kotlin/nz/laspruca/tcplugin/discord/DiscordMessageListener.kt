package nz.laspruca.tcplugin.discord

import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.EmbedBuilder
import net.kyori.adventure.text.Component
import nz.laspruca.tcplugin.Plugin.Companion.discord
import nz.laspruca.tcplugin.Plugin.Companion.baton
import nz.laspruca.tcplugin.Plugin.Companion.announceDeath
import nz.laspruca.tcplugin.Plugin.Companion.hardcore
import java.util.stream.Collectors
import org.bukkit.entity.Player
import org.qrl.tcplugin.TCPlugin.Companion.config
import org.qrl.tcplugin.TCPlugin.Companion.plugin
import java.io.IOException


object DiscordMessageListener : ListenerAdapter() {
    override fun onMessageReceived(event: MessageReceivedEvent) {
        super.onMessageReceived(event)
        val message = event.message.contentStripped

        if (!event.author.isBot && message.startsWith("\\") && event.channel.id == "791082936609931264") {
            val splits = listOf(*message.split(" ").toTypedArray())

            if (splits.size > 1 && splits[0] == "\\" + config!!.getString("prefix")) {
                val command = splits[1]
                val args = splits.stream().skip(2).collect(Collectors.toList())
                when (command) {
                    "say" -> if (args.size > 0) {
                        plugin!!.server.sendMessage(Component.text(args.joinToString(" ")))
                        discord?.sendMessage("Saying: ")
                    } else {
                        discord?.sendMessage("Expected at least one argument, message")
                    }
                    "prefix" -> if (args.size != 1) {
                        discord?.sendMessage("Expected at one argument, name")
                    } else {
                        config!!["prefix"] = args[0]
                        try {
                            config?.save(plugin!!.dataFolder.toString() + "/config.yml")
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }
                        discord?.sendMessage("Setting prefix to " + args[0])
                    }
                    "shutdown" -> plugin!!.server.shutdown()
                    "online" -> {
                        val onlinePlayers = plugin!!.server.onlinePlayers
                        discord?.sendMessage(
                            EmbedBuilder()
                                .setTitle(plugin!!.server.onlinePlayers.size.toString() + " Online")
                                .addField("Players ",
                                    onlinePlayers.stream().map { obj: Player -> obj.name }
                                        .collect(Collectors.joining(", ")),
                                    false
                                )
                        )
                    }
                    "info" -> discord?.sendMessage(
                        EmbedBuilder()
                            .setTitle("Info for " + config!!.getString("prefix"))
                            .addField("/givebaton", if (baton) "Enabled" else "Disabled", true)
                            .addField("pseudo-hardcore mode", if (hardcore) "Enabled" else "Disabled", true)
                            .addField("Death announcements", if (announceDeath) "Enabled" else "Disabled", true)
                    )
                    "t_givebaton" -> {
                        baton = !baton
                        discord?.sendMessage(if (baton) "Enable /givebaton" else "Disabled /givebaton")
                    }
                    "t_hardcore" -> {
                        hardcore = !hardcore
                        discord?.sendMessage(if (hardcore) "Server is in Hardcore mode" else "Server is in normal mode")
                    }
                    "t_deathmsg" -> {
                        announceDeath = !announceDeath
                        discord?.sendMessage(if (announceDeath) "Enable death messages" else "Disabled death messages")
                    }
                    "help" -> discord?.sendMessage(
                        EmbedBuilder()
                            .setTitle("Help for TCPlugin discord commands")
                            .addField("Syntax", "\\[server_prefix] [command] <args>", false)
                            .addBlankField(false)
                            .addField("shutdown", "Shutdown server", false)
                            .addField("prefix", "Sets the servers prefix\n\t**- 1)** the new prefix", false)
                            .addField("say", "Sends a message to the server\n\t**- 1..∞)** the message", false)
                            .addField("online", "Shows all the online players", false)
                            .addField("info", "Shows info about the server", false)
                            .addField("t_givebaton", "Enables/disables /givebaton", false)
                            .addField("t_hardcore", "Enables/disables pseudo-hardcore mode", false)
                            .addField("t_deathmsg", "Enables/disables announcements on death", false)
                            .addField("help", "Shows this", false)
                    )
                    else -> discord?.sendMessage("Invalid commands")
                }
            }
        }
    }

}