package fr.program;

import fr.program.cmds.ModTool;
import fr.program.utils.EventsListeners;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    @Override
    public void onEnable() {
        System.out.println("[ModUtils] Ready");

        this.getCommand("modt").setExecutor(new ModTool(this));

        this.getServer().getPluginManager().registerEvents(new EventsListeners(this), this);
        saveDefaultConfig();
    }

    @Override
    public void onDisable() {
        System.out.println("[ModUtils] Stopped");
    }
}