package de.lexarox.invsee;

import de.lexarox.invsee.commands.clearCMD;
import de.lexarox.invsee.commands.dropCMD;
import de.lexarox.invsee.commands.ecCMD;
import de.lexarox.invsee.commands.invseeCMD;
import org.bukkit.plugin.java.JavaPlugin;

public final class Invsee extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic

        getCommand("invsee").setExecutor(new invseeCMD());
        getCommand("ec").setExecutor(new ecCMD());
        getCommand("clear").setExecutor(new clearCMD());
        getCommand("drop").setExecutor(new dropCMD());

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
