package nz.laspruca.tcplugin.commands

import net.md_5.bungee.api.ChatColor.*
import nz.laspruca.tcplugin.Plugin.Companion.baton
import org.bukkit.ChatColor
import org.bukkit.enchantments.Enchantment
import java.util.ArrayList
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.inventory.ItemStack
import org.qrl.tcplugin.TCPlugin.Companion.plugin
import org.bukkit.entity.Player
import org.bukkit.command.CommandSender
import org.bukkit.command.CommandExecutor
import java.lang.Exception


class GiveBaton() : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (baton) {
            if (args.isEmpty()) {
                plugin.server.getPlayer(sender.name)!!.inventory
                    .addItem(makeBonkyBoi())
            } else {
                for (str: String in args) {
                    when (str) {
                        "@a" -> {
                            for (player: Player in plugin.server.onlinePlayers) {
                                player.inventory.addItem(makeBonkyBoi())
                            }
                        }
                        "@p" -> {
                            plugin.server.getPlayer(sender.name)?.inventory?.addItem(makeBonkyBoi())
                        }
                        else -> {
                            try {
                                plugin.server.getPlayer(str)?.inventory?.addItem(makeBonkyBoi())
                            } catch (_: Exception) {
                                sender.sendMessage("Unable to find player $str")
                            }
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
}

private fun makeBonkyBoi(): ItemStack {
    val itemStack = ItemStack(Material.STICK, 1)
    itemStack.itemMeta = itemStack.itemMeta?.apply  {
        this.lore = ArrayList<String>().apply {
            add("THIS IS YOUR OFFICIAL TECHNOCRAFT POLICE DPT BATON")
            add("HAVE FUN")
        }
        addEnchant(Enchantment.KNOCKBACK, 1000, true)
        setDisplayName("$BLUE${BOLD}TCPD Batton")
    }

    return itemStack
}
