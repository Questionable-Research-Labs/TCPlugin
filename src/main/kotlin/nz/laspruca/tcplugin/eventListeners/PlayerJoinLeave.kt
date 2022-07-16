package nz.laspruca.tcplugin.eventListeners

import nz.laspruca.tcplugin.logger.LeaveEvent
import org.bukkit.event.player.PlayerQuitEvent
import nz.laspruca.tcplugin.logger.JoinEvent
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent


object PlayerJoinLeave : Listener {
    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        JoinEvent(event.player.name).log()
//        if (discord?.loggedIn!!) {
//            if (!discord!!.getMembers().contains(event.player.name)) {
//                event.player.sendMessage(generateText())
//            }
//        }
    }

    @EventHandler
    fun onLeaveJoin(event: PlayerQuitEvent) {
        LeaveEvent(event.player.name).log()
    }
}
