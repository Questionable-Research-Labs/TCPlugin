package nz.laspruca.tcplugin.commands

import org.bukkit.command.Command
import org.bukkit.command.CommandSender

import org.bukkit.command.CommandExecutor


class Bee : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        sender.sendMessage("According to all known laws of aviation, there is no way that a bee should be able to fly.")
        return true
    }
}
