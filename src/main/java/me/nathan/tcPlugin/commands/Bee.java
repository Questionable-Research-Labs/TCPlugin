package me.nathan.tcPlugin.commands;

import org.bukkit.command.*;

public class Bee implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        commandSender.sendMessage("According to all known laws of aviation, there is no way that a bee should be able to fly.");
        return true;
    }
}
