package nz.laspruca.tcplugin.eventListeners

import org.bukkit.Bukkit
import org.bukkit.Sound
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.AsyncPlayerChatEvent
import org.qrl.tcplugin.TCPlugin.Companion.logger
import org.qrl.tcplugin.TCPlugin.Companion.plugin

object YouDoNotRecogniseTheBodiesInTheWater : Listener {
    @EventHandler
    fun iRecogniseTheBodiesInTheWater(e: AsyncPlayerChatEvent) {
        with(e) {
            val message = message.toLowerCase()
            if ("recognise" in message
                && "the water" in message
                && "in" in message
                && "bodies" in message
                && "don't" !in message
                && "do not" !in message
            ) {
                Bukkit.getScheduler().runTask(plugin, Runnable {
                    player.playSound(player.location, Sound.BLOCK_BELL_USE, 200.0f, -100f)
                    player.damage(420.69)
                })
            }
        }
    }

    @EventHandler
    fun funnyMessage(e: PlayerDeathEvent) {
        with(e) {
            logger.info(entity.lastDamage.toString())
            if (420.70 > entity.lastDamage && entity.lastDamage > 420.69) {
                deathMessage =
                    "Verification incomplete. User CRV is not within acceptable limits. User CRV influenced by active cognitohazards. Please stay still, a member of your site's medical staff will be with you shortly."
            }
        }
    }
}