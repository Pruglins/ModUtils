package fr.program.utils;

import fr.program.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.io.File;
import java.util.Objects;

public class EventsListeners implements Listener {
    private final Main plugin;
    public EventsListeners(Main main) {
        this.plugin = main;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        FileConfiguration config = plugin.getConfig();
        config.set("modt." + player.getUniqueId() + ".enabled", "off");
        config.set("modt." + player.getUniqueId() + ".vanish", "off");
        plugin.saveConfig();
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        FileConfiguration config = plugin.getConfig();
        if (config.getString("modt." + player.getUniqueId() + ".enabled").equals("on")) {
            player.getInventory().clear();
            config.set("modt." + player.getUniqueId() + ".enabled", "off");
            config.set("modt." + player.getUniqueId() + ".vanish", "off");
            plugin.saveConfig();
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();
        ItemStack item = event.getItem();
        FileConfiguration config = this.plugin.getConfig();

        if (item != null && item.hasItemMeta()) {
            ItemMeta meta = item.getItemMeta();
            PersistentDataContainer container = meta.getPersistentDataContainer();

            NamespacedKey key_vanish = new NamespacedKey(this.plugin, "vanish");
            if (item.getType() == Material.IRON_HOE && container.has(key_vanish, PersistentDataType.DOUBLE)) {
                vanish(this.plugin, player, action, config);
            }
        }
    }

    @EventHandler
    public void onInteractEntity(PlayerInteractEntityEvent event) {
        if (event.getHand() != EquipmentSlot.HAND) { // * if the hand used is NOT the main hand:
            return; // do not progress past this point  |
        }

        Player player = event.getPlayer();
        FileConfiguration config = this.plugin.getConfig();
        if (event.getRightClicked() instanceof Player target) {
            player.getInventory().getItemInMainHand();
            if (player.getInventory().getItemInMainHand().hasItemMeta()) {
                ItemStack item = player.getInventory().getItemInMainHand();
                ItemMeta meta = item.getItemMeta();
                PersistentDataContainer container = meta.getPersistentDataContainer();

                NamespacedKey key_cps = new NamespacedKey(this.plugin, "cps");
                NamespacedKey key_playerinfo = new NamespacedKey(this.plugin, "info_plr");

                if (item.getType() == Material.CLOCK && container.has(key_cps, PersistentDataType.DOUBLE)) {
                    cpsPlayer(this.plugin, player, config);
                } else if (item.getType() == Material.PLAYER_HEAD && container.has(key_playerinfo, PersistentDataType.DOUBLE)) {
                    informationPlayer(this.plugin, player, config);
                }
            }
        }
    }

    private void informationPlayer(Main plugin, Player player, FileConfiguration config) {
        player.sendMessage("Yo");
    }

    private void cpsPlayer(Main plugin, Player player, FileConfiguration config) {
        player.sendMessage("Bad boy ?");
    }

    private void vanish(Main plugin, Player player, Action action, FileConfiguration config) {
        String mode = config.getString("modt." + player.getUniqueId() + ".enabled");
        if (action == Action.RIGHT_CLICK_AIR && mode != null) {
            if (Objects.equals(mode, "on")) {
                String enabled = config.getString("modt." + player.getUniqueId() + ".vanish");
                if (Objects.equals(enabled, "off")) {
                    config.set("modt." + player.getUniqueId() + ".vanish", "on");

                    for (Player p : Bukkit.getOnlinePlayers()) {
                        if (p.getUniqueId() != player.getUniqueId()) {
                            p.hidePlayer(plugin, player);
                        }
                    }

                    player.sendMessage("[" + ChatColor.GOLD + "ModUtils" + ChatColor.WHITE + "] "
                            + "Vous êtes maintenant" + ChatColor.RED +  " invisible.");
                } else {
                    config.set("modt." + player.getUniqueId() + ".vanish", "off");
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        if (p.getUniqueId() != player.getUniqueId()) {
                            p.showPlayer(plugin, player);
                        }
                    }

                    player.sendMessage("[" + ChatColor.GOLD + "ModUtils" + ChatColor.WHITE + "] "
                            + "Vous êtes maintenant" + ChatColor.RED +  " visible." );
                }
            }
        }
    }
}
