package nz.laspruca.tcplugin.eventListeners

import nz.laspruca.tcplugin.Plugin.Companion.hardcore
import org.bukkit.entity.HumanEntity
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent
import org.qrl.tcplugin.TCPlugin.Companion.plugin

object PlayerDie : Listener {
    @EventHandler
    fun onPlayerDie(e: PlayerDeathEvent) {
        val player: HumanEntity = e.entity
        if (hardcore) {
            plugin.server.dispatchCommand(
                plugin.server.consoleSender,
                "tempban " + player.name + " 12h You died"
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