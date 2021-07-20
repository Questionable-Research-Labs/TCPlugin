package nz.laspruca.tcplugin.commands

import net.kyori.adventure.text.Component
import nz.laspruca.tcplugin.Plugin.Companion.baton
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.qrl.tcplugin.TCPlugin.Companion.plugin


class GiveBaton : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (baton) {
            var argslen = 0
            for (str: String? in args) {
                argslen++
            }
            if (argslen < 1) {
                plugin.server.getPlayer(sender.name)!!.inventory
                    .addItem(makeBonkyBoi())
            } else {
                for (str: String in args) {
                    when (str) {
                        "@a" -> for (player: Player in plugin.server.onlinePlayers) {
                            player.inventory.addItem(makeBonkyBoi())
                        }
                        "@p" -> plugin.server.getPlayer(sender.name)!!.inventory
                            .addItem(makeBonkyBoi())
                        else -> try {
                            plugin.server.getPlayer(str)!!.inventory.addItem(makeBonkyBoi())
                        } catch (ignored: Exception) {
                            sender.sendMessage("Unable to find player $str")
                        }
                    }
                }
            }
        } else {
            sender.sendMessage(
                "${ChatColor.RED}${ChatColor.BOLD} GIVE BATON HAD BEEN DISABLED BY ORDER OF THE 05 COUNCIL"
            )
        }
        return true
    }

    private fun makeBonkyBoi(): ItemStack {
        val `is` = ItemStack(Material.STICK, 1)
        val im = `is`.itemMeta
        im.lore(listOf<Component>(
            Component.text("THIS IS YOUR OFFICIAL TECHNOCRAFT POLICE DPT BATON"),
            Component.text("HAVE FUN")
        ))
        im.addEnchant(Enchantment.KNOCKBACK, 1000, true)
        im.displayName(Component.text(ChatColor.BLUE.toString() + ChatColor.BOLD.toString() + "TCPD Baton"))
        `is`.itemMeta = im
        return `is`
    }
}
