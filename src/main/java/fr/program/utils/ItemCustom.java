package fr.program.utils;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ItemCustom extends ItemStack {
    public ItemCustom(String name, Material mat, int amount) {
        super(mat, amount);
        setDisplayName(name);
    }

    public ItemCustom(String name, Material mat, int amount, boolean hideAtttributes) {
        super(mat, amount);
        setDisplayName(name);
        if (hideAtttributes) {
            disableAttributes();
        } else {
            enableAttributes();
        }
    }

    public ItemCustom(String name, Material mat, int amount, Enchantment enchantment, int level) {
        super(mat, amount);
        setDisplayName(name);
        addEnchantment(enchantment, level);
    }

    public ItemCustom(String name, Material mat, int amount, Enchantment enchantment, int level, boolean hideAtttributes) {
        super(mat, amount);
        setDisplayName(name);
        addEnchantment(enchantment, level);
        if (hideAtttributes) {
            disableAttributes();
        } else {
            enableAttributes();
        }
    }

    public void addEnchantment(Enchantment ench, int level) {
        ItemMeta meta = this.getItemMeta();
        meta.addEnchant(ench, level, false);
        this.setItemMeta(meta);
    }

    public void addEnchantments(List<Enchantment> enchantments, List<Integer> levels) {
        ItemMeta meta = this.getItemMeta();
        for (int i = 0; i < enchantments.size(); i++) {
            Enchantment enchantment = enchantments.get(i);
            int level = levels.get(i);
            meta.addEnchant(enchantment, level, true);
        }
        this.setItemMeta(meta);
    }

    public void setDisplayName(String name) {
        ItemMeta meta = this.getItemMeta();
        meta.setDisplayName(name);
        this.setItemMeta(meta);
    }

    public void disableAttributes() {
        ItemMeta meta = this.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        this.setItemMeta(meta);
    }

    public void enableAttributes() {
        ItemMeta meta = this.getItemMeta();
        meta.removeItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        this.setItemMeta(meta);
    }
}
