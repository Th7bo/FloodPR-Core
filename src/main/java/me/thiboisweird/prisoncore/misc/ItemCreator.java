package me.thiboisweird.prisoncore.misc;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class ItemCreator {
    private Material material;
    private int amount = 1;
    private String name;
    private String[] lore;


    public ItemCreator(Material material) {
        this(material, 1);
    }

    public ItemCreator(Material material, int amount) {
        this.material = material;
        this.amount = amount;
    }

    public ItemCreator setMaterial(Material material) {
        this.material = material;
        return this;
    }

    public Material getMaterial() {
        return material;
    }

    public int getAmount() {
        return amount;
    }

    public ItemCreator setName(String name) {
        this.name = Misc.Color(name);
        return this;
    }

    public ItemCreator setAmount(int amount) {
        this.amount = amount;
        return this;
    }

    public ItemCreator setLore(String... lore) {
        this.lore = Misc.Color(lore);
        return this;
    }

    public ItemStack create() {
        ItemStack item = new ItemStack(this.material, this.amount);
        ItemMeta meta = item.getItemMeta();
        if(name != null) {
            meta.setDisplayName(this.name);
        }
        if(lore != null) {
            meta.setLore(Arrays.asList(this.lore));
        }
        item.setItemMeta(meta);

        return item;
    }
}
