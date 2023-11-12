package nz.laspruca.tcplugin.eventListeners

import nz.laspruca.tcplugin.Plugin.Companion.hardcore
import nz.laspruca.tcplugin.logger.DeathEvent
import org.bukkit.entity.HumanEntity
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent
import org.qrl.tcplugin.TCPlugin.Companion.logger
import org.qrl.tcplugin.TCPlugin.Companion.plugin

object PlayerDie : Listener {
    @EventHandler
    fun onPlayerDie(e: PlayerDeathEvent) {
        val player: HumanEntity = e.entity;

        DeathEvent(player.name, e.deathMessage).log()


        if (hardcore) {
            if (player.isOp) {
                logger.info("Player is op, privileges revoked")
                player.isOp = false
            }
            plugin.server.dispatchCommand(

                plugin.server.consoleSender,
                "tempban " + player.name + " 1h You died"
            )
        }
//        logger.info("Announce death is $announceDeath")
//        if (announceDeath) {
//            logger.info(player.name + " noped.")
//            val reason = e.deathMessage() as TranslatableComponent
//            discord!!.sendMessageToChannel(
//                EmbedBuilder().setTitle(player.name + " Just died")
//                    .addField("Reason: ", "${reason.args()}", false), "800632332646088714"
//            )
//        }
    }
}