package nz.laspruca.tcplugin.commands

import net.md_5.bungee.api.ChatColor.*
import net.md_5.bungee.api.chat.ClickEvent
import net.md_5.bungee.api.chat.HoverEvent
import net.md_5.bungee.api.chat.TextComponent
import net.md_5.bungee.api.chat.hover.content.Text
import org.bukkit.command.Command
import org.bukkit.command.CommandSender

import org.bukkit.command.CommandExecutor

fun generateText(): TextComponent = TextComponent(BLUE.toString() + BOLD.toString() + "Join the Technocraft Discord Server!").apply {
    clickEvent = ClickEvent(ClickEvent.Action.OPEN_URL, "https://discord.gg/8F7RpsG");
    hoverEvent = HoverEvent(HoverEvent.Action.SHOW_TEXT, Text("Click to join discord\n"));
}


class DiscordCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        sender.spigot().sendMessage(generateText())
        return true
    }
}