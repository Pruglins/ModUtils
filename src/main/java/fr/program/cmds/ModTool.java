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
                if (config.getString("modt." + player.getUniqueId()) == "on") {
                    config.set("modt." + player.getUniqueId(), "off");
                } else {
                    config.set("modt." + player.getUniqueId(), "on");

                    ItemCustom vanish = new ItemCustom("§l§9Vanish", Material.IRON_HOE, 1, true);
                    ItemCustom stick_kb_1 = new ItemCustom("§l§9KB 1", Material.STICK, 1, Enchantment.KNOCKBACK, 1);
                    ItemCustom stick_kb_2 = new ItemCustom("§l§9KB 2", Material.STICK, 1, Enchantment.KNOCKBACK, 2);
                    ItemCustom cps = new ItemCustom("§l§9CPS", Material.CLOCK, 1, true);
                    ItemCustom info_plr = new ItemCustom("§l§9Informations Joueurs", Material.PLAYER_HEAD, 1, true);

                    playerInv.setItem(1, vanish);
                    playerInv.setItem(3, stick_kb_1);
                    playerInv.setItem(4, stick_kb_2);
                    playerInv.setItem(6, cps);
                    playerInv.setItem(7, info_plr);

                    player.updateInventory();
                }
                plugin.saveConfig();
            }
        }
        return false;
    }
}
