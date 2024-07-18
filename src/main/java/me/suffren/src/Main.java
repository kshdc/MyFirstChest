package me.suffren.src;

import me.suffren.src.Commands.rouletteCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("Plugin MyFirstChest has been enabled!");

        getCommand("roulette").setExecutor(new rouletteCommand(this));
    }

    @Override
    public void onDisable() {
        getLogger().info("Plugin MyFirstChest has been disabled!");
    }
}
