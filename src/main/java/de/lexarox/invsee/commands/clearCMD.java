package de.lexarox.invsee.commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class clearCMD implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {


        if (args.length > 1){
            sender.sendMessage("§8[§4✘ §cError§8] §f⌁ Too many Arguments");
            return false;
        }



        if (args.length == 0){
            if (!(sender instanceof Player)) return false;

            if (!(sender.hasPermission("clear.use"))){
                sender.sendMessage("§8[§4✘ §cError§8] §f⌁ You lack the permission 'clear.use'");
                return false;
            }

            Player player = (Player) sender;
            Inventory inventory = player.getInventory();

            inventory.clear();
            sender.sendMessage("§8[§2✔ §aSuccess§8] §f⌁ Cleared your Inventory");
            return true;
        }

        if (!(sender.hasPermission("clear.others"))){
            sender.sendMessage("§8[§4✘ §cError§8] §f⌁ You lack the permission 'clear.others'");
            return false;
        }

        Player target = Bukkit.getPlayer(args[0]);

        if (target == null){
            sender.sendMessage("§8[§4✘ §cError§8] §f⌁ Player not found");
            return false;
        }

        target.getInventory().clear();
        sender.sendMessage("§8[§2✔ §aSuccess§8] §f⌁ Cleared " + target.getName() + "'s Inventory");
        target.sendMessage("§8[§8¡ §7Info§8] §f⌁ Your Inventory was cleared");

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        ArrayList<String> completions = new ArrayList<>();
        ArrayList<String> players = new ArrayList<>();

        if (args.length == 1){

            for (OfflinePlayer player : Bukkit.getOnlinePlayers()){

                players.add(player.getName());

            }

            StringUtil.copyPartialMatches(args[0], players, completions);

            Collections.sort(completions);

        }



        return completions;
    }
}
