package me.suffren.src;

import me.suffren.src.Commands.craftCommand;
import me.suffren.src.Commands.flyCommand;
import me.suffren.src.Commands.rouletteCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("Plugin MyFirstChest has been enabled!");

        getCommand("roulette").setExecutor(new rouletteCommand(this));
        getCommand("fly").setExecutor(new flyCommand(this));
        getCommand("craft").setExecutor(new craftCommand(this));
    }

    @Override
    public void onDisable() {
        getLogger().info("Plugin MyFirstChest has been disabled!");
    }
}
