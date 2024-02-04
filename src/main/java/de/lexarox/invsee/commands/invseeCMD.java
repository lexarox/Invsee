package de.lexarox.invsee.commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
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

public class invseeCMD implements CommandExecutor, TabCompleter {


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if (!(sender instanceof Player)) return false; // /invsee <player>  // §8[§2✔ §aSuccess§8] §f⌁ // §8[§4✘ §cError§8] §f⌁ // §8[§8¡ §7Info§8] §f⌁

        if (args.length != 1) {
            sender.sendMessage("§8[§4✘ §cError§8] §f⌁ Use /invsee <player>");
            return false;
        }

        Player target = Bukkit.getPlayer(args[0]);

        if (target == null){
            sender.sendMessage("§8[§4✘ §cError§8] §f⌁ Player not found");
            return false;
        }

        Player player = (Player) sender;

        if (target == player) {
            sender.sendMessage("§8[§4✘ §cError§8] §f⌁ You cant use Invsee on your own Inventory");
            return false;
        }

        player.openInventory(target.getInventory());
        player.getOpenInventory().setTitle("§9" + target.getName() + "'s Inventory");

        return false;
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
