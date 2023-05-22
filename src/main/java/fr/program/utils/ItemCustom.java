package fr.program.utils;

import fr.program.Main;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.security.Key;
import java.util.List;

public class ItemCustom {
    private final ItemStack item;

    public ItemCustom(String name, Material mat, int amount, boolean hideAttributes) {
        this.item = new ItemStack(mat, amount);
        setDisplayName(name);
        if (hideAttributes) {
            disableAttributes();
        } else {
            enableAttributes();
        }
    }

    public ItemCustom(String name, Material mat, int amount, boolean hideAttributes, Main pluginInstance, String keyName) {
        this.item = new ItemStack(mat, amount);
        setDisplayName(name);
        addKey(pluginInstance, keyName);
        if (hideAttributes) {
            disableAttributes();
        } else {
            enableAttributes();
        }
    }

    public ItemCustom(String name, Material mat, int amount, Enchantment enchantment, int level) {
        this.item = new ItemStack(mat, amount);
        setDisplayName(name);
        addEnchantment(enchantment, level);
    }

    public ItemStack getItem() {
        return this.item;
    }

    public ItemMeta getItemMeta() {
        return this.item.getItemMeta();
    }

    public void setItemMeta(ItemMeta meta) {
        this.item.setItemMeta(meta);
    }

    public void addKey(Main pluginInstance, String keyName) {
        ItemMeta meta = this.item.getItemMeta();
        NamespacedKey key = new NamespacedKey(pluginInstance, keyName);
        meta.getPersistentDataContainer().set(key, PersistentDataType.DOUBLE, Math.PI);
        this.item.setItemMeta(meta);
    }

    public void addEnchantment(Enchantment ench, int level) {
        ItemMeta meta = this.item.getItemMeta();
        meta.addEnchant(ench, level, false);
        this.item.setItemMeta(meta);
    }

    public void addEnchantments(List<Enchantment> enchantments, List<Integer> levels) {
        ItemMeta meta = this.item.getItemMeta();
        for (int i = 0; i < enchantments.size(); i++) {
            Enchantment enchantment = enchantments.get(i);
            int level = levels.get(i);
            meta.addEnchant(enchantment, level, true);
        }
        this.item.setItemMeta(meta);
    }

    public void setDisplayName(String name) {
        ItemMeta meta = this.item.getItemMeta();
        meta.setDisplayName(name);
        this.item.setItemMeta(meta);
    }

    public void disableAttributes() {
        ItemMeta meta = this.item.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        this.item.setItemMeta(meta);
    }

    public void enableAttributes() {
        ItemMeta meta = this.item.getItemMeta();
        meta.removeItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        this.item.setItemMeta(meta);
    }
}
