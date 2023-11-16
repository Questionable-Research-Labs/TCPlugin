package nz.laspruca.tcplugin.discord

import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.requests.GatewayIntent
import net.dv8tion.jda.api.utils.cache.CacheFlag
import javax.security.auth.login.LoginException

import net.dv8tion.jda.api.events.session.ReadyEvent
import nz.laspruca.tcplugin.Plugin
import nz.laspruca.tcplugin.Plugin.Companion.discordBot
import nz.laspruca.tcplugin.Plugin.Companion.discordChannelID
import nz.laspruca.tcplugin.Plugin.Companion.discordPrefix
import nz.laspruca.tcplugin.Plugin.Companion.discordServerID
import nz.laspruca.tcplugin.Plugin.Companion.discordToken
import org.qrl.tcplugin.TCPlugin.Companion.config
import org.qrl.tcplugin.TCPlugin.Companion.logger
import kotlin.streams.toList


class Discord : ListenerAdapter() {
    private var jda: JDA? = null
    private var goBrrr = false
    val loggedIn: Boolean
        get() = goBrrr

    init {
        if (discordBot) {
            logger?.info("Starting discord")
            if (discordPrefix.isNullOrBlank()) {
                logger?.warning("No prefix set, setting to default")
                discordPrefix = "setme"
            }
            if (discordToken.isNullOrBlank()) {
                logger?.warning("No discord token set, uh oh")
            }

            try {
                jda = JDABuilder.createDefault(discordToken)
                    .disableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.VOICE_STATE)
                    .enableIntents(GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_INVITES)
                    .addEventListeners(this, DiscordMessageListener)
                    .build()
            } catch (ex: LoginException) {
                logger?.warning("Unable to initialize discord api, invalid login token")
            }

        } else {
            logger.info("Discord bot disabled "+ config.getBoolean("discordBot") + " " + discordBot)

        }

    }

    @Throws(InterruptedException::class)
    fun exitDiscord() {
        if (goBrrr && discordBot) {
            sendMessage("Shutting down")

            jda?.shutdown()
            goBrrr = false
        }
    }

    @Throws(IllegalStateException::class)
    fun getMembers(): List<String> {
        if (goBrrr && discordBot) {
            return jda!!.getGuildById(discordServerID!!)!!
                .loadMembers()
                .get()
                .stream()
                .map { a -> if (a.nickname == null) a.user.name else a.nickname!! }
                .toList()
        } else {
            throw IllegalStateException()
        }
    }

    override fun onReady(event: ReadyEvent) {
        super.onReady(event)
        goBrrr = true
        sendMessage("Server started")
    }

    fun sendMessageToChannel(message: String, channelID: String) {
        sendMessageToChannel(EmbedBuilder().addField(message, "", true), channelID)
    }

    fun sendMessageToChannel(message: EmbedBuilder, channelID: String) {
        if (goBrrr && discordBot) {
            jda!!.getGuildById(discordServerID!!)!!
                .getTextChannelById(channelID)!!
                .sendMessageEmbeds(message.setAuthor(discordPrefix).build())
                .queue()
        }
    }

    fun sendMessage(message: String) {
        sendMessage(EmbedBuilder().addField(message, "", true))
    }

    fun sendMessage(message: EmbedBuilder) {
        if (goBrrr && discordBot) {
            jda!!.getGuildById(discordServerID!!)!!
                .getTextChannelById(discordChannelID!!)!!
                .sendMessageEmbeds(message.setAuthor(discordPrefix).build())
                .queue()
        }
    }
}
