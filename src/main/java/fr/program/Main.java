package fr.program;

import fr.program.utils.CommandsManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    @Override
    public void onEnable() {
        System.out.println("[ModUtils] Ready");
        CommandsManager.init(this);
        saveDefaultConfig();
    }

    @Override
    public void onDisable() {
        System.out.println("[ModUtils] Stopped");
    }
}