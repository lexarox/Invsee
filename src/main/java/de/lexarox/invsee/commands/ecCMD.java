package de.lexarox.invsee.commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ecCMD implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if (!(sender instanceof Player)) return false;

        if (args.length > 1){
            sender.sendMessage("§8[§4✘ §cError§8] §f⌁ Too many Arguments");
            return false;
        }

        if (!(sender.hasPermission("ec.use"))){
            sender.sendMessage("§8[§4✘ §cError§8] §f⌁ You lack the permission 'ec.use'");
            return false;
        }

        Player player = (Player) sender;

        if (args.length == 0){

            player.openInventory(player.getEnderChest());
            player.getOpenInventory().setTitle("§9Your Enderchest");
            return true;
        }

        if (!(sender.hasPermission("ec.others"))){
            sender.sendMessage("§8[§4✘ §cError§8] §f⌁ You lack the permission 'ec.others'");
            return false;
        }

        Player target = Bukkit.getPlayer(args[0]);

        if (target == null){
            sender.sendMessage("§8[§4✘ §cError§8] §f⌁ Player not found");
            return false;
        }

        player.openInventory(target.getEnderChest());
        player.getOpenInventory().setTitle("§9" + target.getName() + "'s Enderchest");

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

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
