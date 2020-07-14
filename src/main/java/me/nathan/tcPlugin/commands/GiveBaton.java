package me.nathan.tcPlugin.commands;

import me.nathan.tcPlugin.TCPlugin;

import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.enchantments.Enchantment;

import java.util.ArrayList;
import java.util.Objects;

public class GiveBaton implements CommandExecutor {
    static TCPlugin plugin = TCPlugin.getPlugin(TCPlugin.class);
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        int argslen = 0;
        for (String str : args) {
            argslen++;
        }

        if (argslen < 1) {
            Objects.requireNonNull(plugin.getServer().getPlayer(commandSender.getName())).getInventory().addItem(makeBonkyBoi());
        } else {
            for (String str : args) {
                if (str.equals("@a")) {
                    for (Player player : plugin.getServer().getOnlinePlayers()) {
                        player.getInventory().addItem(makeBonkyBoi());
                    }
                } else if (str.equals("@p")) {
                    Objects.requireNonNull(plugin.getServer().getPlayer(commandSender.getName())).getInventory().addItem(makeBonkyBoi());
                } else {
                    try {
                        Objects.requireNonNull(plugin.getServer().getPlayer(str)).getInventory().addItem(makeBonkyBoi());
                    } catch (Exception ignored) {
                        commandSender.sendMessage("Unable to find player " + str);
                    }
                }
            }
        }

        return true;
    }

    private ItemStack makeBonkyBoi() {
        ItemStack is = new ItemStack(Material.STICK, 1);

        ItemMeta im = is.getItemMeta();
        ArrayList<String> lore = new ArrayList<String>();

        lore.add("THIS IS YOUR OFFICIAL TECHNOCRAFT POLICE DPT KNOCKBACK STICK");
        lore.add("HAVE FUN");
        im.setLore(lore);
        im.addEnchant(Enchantment.KNOCKBACK, 1000, true);
        im.setDisplayName(ChatColor.BLUE.toString() + ChatColor.BOLD.toString() + "TCPD Batten");

        is.setItemMeta(im);

        return is;
    }
}
