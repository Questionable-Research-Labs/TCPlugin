package nz.laspruca.tcplugin.commands

import net.kyori.adventure.audience.MessageType
import net.kyori.adventure.identity.Identity
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextComponent
import net.kyori.adventure.text.event.ClickEvent
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import net.kyori.adventure.text.event.HoverEvent

import org.bukkit.command.CommandExecutor

fun generateText(): TextComponent {
    return Component.text("${ChatColor.BLUE}${ChatColor.BOLD}Join the Technocraft Discord Server!")
        .hoverEvent(HoverEvent.showText(Component.text("${ChatColor.BLUE}${ChatColor.BOLD}Click to join discord")))
        .clickEvent(ClickEvent.openUrl("https://discord.gg/8F7RpsG"))

}

class DiscordCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        sender.sendMessage(Identity.nil(), generateText(), MessageType.CHAT)
        return true
    }
}