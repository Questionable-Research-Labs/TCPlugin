package nz.laspruca.tcplugin.eventListeners

import net.dv8tion.jda.api.EmbedBuilder
import net.md_5.bungee.api.chat.TranslatableComponent
import nz.laspruca.tcplugin.Plugin
import nz.laspruca.tcplugin.Plugin.Companion.announceDeath
import nz.laspruca.tcplugin.Plugin.Companion.discord
import nz.laspruca.tcplugin.Plugin.Companion.hardcore
import nz.laspruca.tcplugin.logger.DeathEvent
import org.bukkit.entity.EntityType
import org.bukkit.entity.HumanEntity
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.PlayerDeathEvent
import org.qrl.tcplugin.TCPlugin.Companion.logger
import org.qrl.tcplugin.TCPlugin.Companion.plugin
import java.awt.Color


object PlayerDie : Listener {
    @EventHandler
    fun onPlayerDie(e: PlayerDeathEvent) {
        val player: HumanEntity = e.entity;

        DeathEvent(player.name, e.deathMessage).log()

        var timePeriod = "15m You died (L)";

        if (hardcore) {
            val playerCords = player.eyeLocation;
            if (player.isOp) {
                logger.info("Player is op, privileges revoked")
                player.isOp = false
            }

            if (player.lastDamageCause is EntityDamageByEntityEvent) {
                val damager = player.killer;
                damager?.let {
                    if (damager.type==EntityType.PLAYER) {
                        // BLOODY MURDER
                        timePeriod = "1h You died to another player? Get vaished (L)";
                    }
                }

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