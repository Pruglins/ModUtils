package fr.program.utils;

import fr.program.Main;
import fr.program.cmds.ModTool;

public class CommandsManager {
    public static void init(Main main) {
        main.getCommand("modt").setExecutor(new ModTool(main));
    }
}
