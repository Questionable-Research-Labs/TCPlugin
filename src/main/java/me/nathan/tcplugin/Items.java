package me.nathan.tcplugin;

import org.bukkit.Material;

public class Items {
    boolean empty = false;
    Material material;
    int amount;

    public Items() {
        this.material = Material.AIR;
        this.amount = 0;
        this.empty = true;
    }

    public Items(Material type, int amount) {
        this.material = type;
        this.amount = amount;
    }

    public boolean isEmpty() {
        return empty;
    }

    public Material getMaterial() {
        return material;
    }

    public int getAmount() {
        return amount;
    }
}
