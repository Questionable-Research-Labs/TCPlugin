package nz.laspruca.tcplugin.eventListeners

import net.dv8tion.jda.api.EmbedBuilder
import nz.laspruca.tcplugin.Plugin.Companion.announceDeath
import nz.laspruca.tcplugin.Plugin.Companion.discord
import nz.laspruca.tcplugin.Plugin.Companion.hardcore
import nz.laspruca.tcplugin.logger.DeathEvent
import org.bukkit.Bukkit
import org.bukkit.entity.EntityType
import org.bukkit.entity.HumanEntity
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.scoreboard.Scoreboard
import org.qrl.tcplugin.TCPlugin.Companion.logger
import org.qrl.tcplugin.TCPlugin.Companion.plugin
import java.awt.Color
import kotlin.math.pow


object PlayerDie : Listener {
    @EventHandler
    fun onPlayerDie(e: PlayerDeathEvent) {
        val player: HumanEntity = e.entity

        DeathEvent(player.name, e.deathMessage).log()

        var timePeriod = "15m You died (L)";

        if (hardcore) {
            val playerCords = player.eyeLocation;
            if (player.isOp) {
                logger.info("Player is op, privileges revoked")
                player.isOp = false
            }

            if (player.lastDamageCause is EntityDamageByEntityEvent) {
                val damager = player.killer
                damager?.let {
                    if (damager.type==EntityType.PLAYER) {
                        // BLOODY MURDER
                        timePeriod = "1h You died to another player? Get vaished (L)"
                    }
                }

            }

            // --------------- dr storm cringe stuff --------------- (this is garbage code, delete this next version lmao)
            if (player.name == "mrdr_storm"){
                timePeriod = try {
                    val manager = Bukkit.getScoreboardManager()
                    val board: Scoreboard = manager!!.mainScoreboard
                    val objective = board.getObjective("Deaths")
                    val deathCount = objective!!.getScore(player.name).score

                    val banTime = 2.0.pow(deathCount - 16.0)

                    banTime.toString() + "m Skill issue"
                } catch (e: NullPointerException) {
                    // what
                    "1h the plugin broke somehow"
                }

                logger.info(timePeriod)
            }


            logger.info("Announce death is $announceDeath")
            if (announceDeath) {
                logger.info(player.name + " noped.")
                val reason = e.deathMessage ?: "Unknown";
                discord!!.sendMessage(
                    EmbedBuilder().setTitle(player.name + " just died")
                        .addField("Reason: ", reason, false)
                        .addField("Banned for: ", timePeriod, false)
                        .setColor(Color.RED)
                )
            }

            plugin.server.dispatchCommand(

                plugin.server.consoleSender,
                "tempban " + player.name + " "+timePeriod + " (X: "+ playerCords.blockX +", Z: "+playerCords.blockZ+")"
            )
        }
    }

}