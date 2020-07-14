package me.nathan.tcPlugin.commands;

import org.bukkit.command.*;
import net.md_5.bungee.api.chat.*;
import me.nathan.tcPlugin.TCPlugin;

import static org.bukkit.ChatColor.*;

public class Discord implements CommandExecutor {
    static TCPlugin plugin = TCPlugin.getPlugin(TCPlugin.class);
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        commandSender.spigot().sendMessage(generateText());
        return true;
    }

    public static TextComponent generateText() {
        TextComponent text = new TextComponent(BLUE.toString() + BOLD.toString() + "Join the Technocraft Discord Server!");
        text.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://discord.gg/8F7RpsG"));
        text.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click to join discord\nhttps://discord.gg/8F7RpsG").create()));
        return text;
    }
}