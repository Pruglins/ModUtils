package fr.program.cmds;

import fr.program.Main;
import fr.program.utils.ItemCustom;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.Objects;

public class ModTool implements CommandExecutor {
    private final Main plugin;

    public ModTool(Main main) {
        this.plugin = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("modt")) {
            if (sender instanceof Player player) {
                Inventory playerInv = player.getInventory();
                playerInv.clear();
                FileConfiguration config = plugin.getConfig();

                // Config de base dans le modt :
                config.set("modt." + player.getUniqueId() + ".vanish", "off");

                if (Objects.equals(config.getString("modt." + player.getUniqueId() + ".enabled"), "on")) {
                    config.set("modt." + player.getUniqueId() + ".enabled", "off");
                    plugin.saveConfig();

                    player.sendMessage("[" + ChatColor.GOLD + "ModUtils" + ChatColor.WHITE + "] "
                            + "Vous ne disposez plus des outils de modération.");
                    return true;
                } else {
                    config.set("modt." + player.getUniqueId() + ".enabled", "on");
                    plugin.saveConfig();

                    ItemCustom vanish = new ItemCustom("§l§9Vanish", Material.IRON_HOE, 1, true, plugin, "vanish");
                    //vanish.addKey(plugin, "vanish");
                    ItemCustom stick_kb_1 = new ItemCustom("§l§9KB 1", Material.STICK, 1, Enchantment.KNOCKBACK, 1);
                    ItemCustom stick_kb_2 = new ItemCustom("§l§9KB 2", Material.STICK, 1, Enchantment.KNOCKBACK, 2);

                    ItemCustom cps = new ItemCustom("§l§9CPS", Material.CLOCK, 1, true, plugin, "cps");
                    ItemCustom info_plr = new ItemCustom("§l§9Informations Joueur", Material.PLAYER_HEAD, 1, true, plugin, "info_plr");

                    playerInv.setItem(1, vanish.getItem());
                    playerInv.setItem(3, stick_kb_1.getItem());
                    playerInv.setItem(4, stick_kb_2.getItem());
                    playerInv.setItem(6, cps.getItem());
                    playerInv.setItem(7, info_plr.getItem());

                    player.updateInventory();

                    player.sendMessage("[" + ChatColor.GOLD + "ModUtils" + ChatColor.WHITE + "] "
                            + "Vous disposez des outils de modération.");
                }
                return true;
            }
        }
        return false;
    }
}
