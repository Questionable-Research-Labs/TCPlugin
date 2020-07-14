package me.nathan.tcPlugin.checkers;

import me.nathan.tcPlugin.Items;
import me.nathan.tcPlugin.player_logger.PlayerLogger;

import org.bukkit.event.*;
import org.bukkit.Material;
import org.bukkit.event.inventory.*;
import org.bukkit.event.block.BlockBreakEvent;

import org.bukkit.Location;
import org.bukkit.inventory.*;
import org.bukkit.entity.HumanEntity;

import java.util.*;

import static me.nathan.tcPlugin.TCPlugin.logger;


public class Chests implements Listener {

    static HashMap<UUID, ArrayList<Items>> chestInventories = new HashMap<>();

    @EventHandler
    public static void onInventoryOpen(InventoryOpenEvent e) {
        Inventory inventory = e.getInventory();
        HumanEntity player = e.getPlayer();
        ArrayList<Items> items = toArrayList(inventory.getContents());
        chestInventories.put(player.getUniqueId(), items);
    }

    @EventHandler
    public static void onInventory(InventoryCloseEvent e) {
        Inventory inventory = e.getInventory();
        HumanEntity player = e.getPlayer();
        if (inventory.getType().equals(InventoryType.CHEST) || inventory.getType().equals(InventoryType.BARREL) || inventory.getType().equals(InventoryType.SHULKER_BOX)) {
            StringBuilder sb = new StringBuilder();
            StringBuilder sb2 = new StringBuilder();
            Location location = e.getInventory().getLocation();
            if (inventory.getType().equals(InventoryType.CHEST)){
                sb.append(e.getPlayer().getName()).append(" edited chest at ").append(location).append("\n");
            } else if (inventory.getType().equals(InventoryType.SHULKER_BOX)){
                sb.append(e.getPlayer().getName()).append(" edited shulker box at ").append(location).append("\n");
            } else {
                sb.append(e.getPlayer().getName()).append(" edited barrel at ").append(location).append("\n");
            }
            ArrayList<Items> opened = chestInventories.get(e.getPlayer().getUniqueId());
            ArrayList<Items> closed = toArrayList(e.getInventory().getContents());
            for (int i = 0; i < opened.size(); i++) {
                Items prev = opened.get(i);
                Items curr = closed.get(i);
                if (!prev.isEmpty() && !curr.isEmpty()) {
                    if (prev.getMaterial() == curr.getMaterial()) {
                        if (prev.getAmount() > curr.getAmount()) {
                            sb2.append("    Removed ").append(prev.getMaterial().name()).append(", ").append(prev.getAmount() - curr.getAmount()).append("\n");
                        } else if (prev.getAmount() < curr.getAmount()) {
                            sb2.append("    Added ").append(prev.getMaterial().name()).append(", ").append(curr.getAmount() - prev.getAmount()).append("\n");
                        }
                    }
                }
                else if (!prev.isEmpty() &&  curr.isEmpty()) {
                    sb2.append("    Removed ").append(prev.getMaterial().name()).append(", ").append(prev.getAmount()).append("\n");
                } else if ( prev.isEmpty() && !curr.isEmpty()) {
                    sb2.append("    Added ").append(curr.getMaterial().name()).append(", ").append(curr.getAmount()).append("\n");
                }
            }
            sb.append(sb2.toString());
            PlayerLogger.logChestModify(player.getUniqueId(), e.getInventory().getType(), e.getInventory().getLocation(), sb2.toString());
            logger.info(sb.toString());
        }
    }

    @EventHandler
    public static void onBlockBreak(BlockBreakEvent e) {
        if (e.getBlock().getType().equals(Material.CHEST)) {
            logger.info(e.getPlayer().getName() + " broke chest at " + e.getBlock().getLocation() + "\n");
        }
        if (e.getBlock().getType().equals(Material.BARREL)) {
            logger.info(e.getPlayer().getName() + " broke barrel at " + e.getBlock().getLocation() + "\n");
        }
        if (e.getBlock().getType().equals(Material.SHULKER_BOX)) {
            logger.info(e.getPlayer().getName() + " broke shulker box at " + e.getBlock().getLocation() + "\n");
        }
    }

    public static ArrayList<Items> toArrayList(ItemStack[] list) {
        ArrayList<Items> items = new ArrayList<>();
        for (ItemStack itemStack : list) {
            try {
                items.add(new Items(itemStack.getType(), itemStack.getAmount()));
            } catch (Exception ignored) {
                items.add(new Items());
            }
        }
        return items;
    }
}
