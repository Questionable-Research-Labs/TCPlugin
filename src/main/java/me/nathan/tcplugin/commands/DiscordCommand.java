package me.nathan.tcplugin.commands;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

import static org.bukkit.ChatColor.*;

public class DiscordCommand {
	public static TextComponent generateText() {
		TextComponent text = new TextComponent(BLUE.toString() + BOLD.toString() + "Join the Technocraft Discord Server!");
		text.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://discord.gg/8F7RpsG"));
		text.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click to join discord\nhttps://discord.gg/8F7RpsG").create()));
		return text;
	}
}
