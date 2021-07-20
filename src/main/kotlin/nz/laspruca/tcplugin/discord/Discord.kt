package nz.laspruca.tcplugin.discord

import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.events.ReadyEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.requests.GatewayIntent
import net.dv8tion.jda.api.utils.cache.CacheFlag
import org.qrl.tcplugin.TCPlugin.Companion.config
import org.qrl.tcplugin.TCPlugin.Companion.logger
import javax.security.auth.login.LoginException


class Discord : ListenerAdapter() {
    private var jda: JDA?
    private var goBrrr = false
    val loggedIn: Boolean
        get() = goBrrr

    init {
        logger.info("Starting discord")
        if (config.getString("prefix") == null) {
            logger.warning("No prefix set, setting to default")
            config.set("prefix", "setme")
        }
        jda = try {
            JDABuilder.createDefault(config.getString("discordToken"))
                .disableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.VOICE_STATE)
                .enableIntents(GatewayIntent.GUILD_MEMBERS)
                .addEventListeners(this, DiscordMessageListener)
                .build()
        } catch (ex: LoginException) {
            logger.warning("Unable to initialize discord api, invalid login token")
            null
        }
    }

    @Throws(InterruptedException::class)
    fun exitDiscord() {
        if (goBrrr) {
            sendMessage("Shutting down")

            jda?.shutdown()
            goBrrr = false
        }
    }

    @Throws(IllegalStateException::class)
    fun getMembers(): List<String> {
        if (goBrrr) {
            return jda?.getGuildsByName("technocraft", true)?.get(0)!!
                .loadMembers()
                .get()
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
        if (goBrrr && jda != null) {
            jda!!.getGuildsByName("technocraft", true)[0]
                .getTextChannelById(channelID)!!
                .sendMessage(message.setAuthor(config.getString("prefix")).build())
                .queue()
        }
    }

    fun sendMessage(message: String) {
        sendMessage(EmbedBuilder().addField(message, "", true))
    }

    fun sendMessage(message: EmbedBuilder) {
        if (goBrrr && jda != null) {
            jda!!.getGuildsByName("technocraft", true)[0]
                .getTextChannelById("791082936609931264")!!
                .sendMessage(message.setAuthor(config.getString("prefix")).build())
                .queue()
        }
    }
}