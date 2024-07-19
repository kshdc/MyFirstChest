package me.suffren.src.Commands;

import me.suffren.src.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class flyCommand implements CommandExecutor, Listener {
    private Main plugin;
    public flyCommand(Main plugin) {
        this.plugin = plugin;

    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Vous devez être un joueur pour exécuter cette commande.");
            return true;
        }

        Player player = (Player) sender;
        if (player.getAllowFlight()) {
            player.setAllowFlight(false);
            player.sendTitle(player.getName(), "Fly désactiver");


        } else {
            player.setAllowFlight(true);
            player.sendTitle(player.getName(), "Fly activé");
        }

        return true;
    }
}