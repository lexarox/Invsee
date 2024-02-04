package de.lexarox.invsee.commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class dropCMD implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        // in console ausführbar /drop <player> oder /drop player all    // §8[§2✔ §aSuccess§8] §f⌁ // §8[§4✘ §cError§8] §f⌁ // §8[§8¡ §7Info§8] §f⌁

        Player player = (Player) sender; // beim drop eher player.dropItem() verwenden


        if (args.length == 0) {

            if (!(player.hasPermission("drop.use"))){
                sender.sendMessage("§8[§4✘ §cError§8] §f⌁ You lack the permission 'drop.use'");
                return false;
            }

            ItemStack[] inventoryContents = player.getInventory().getContents();

            for (ItemStack item : inventoryContents) {
                if (item != null && !item.getType().isAir()) {
                    player.getWorld().dropItemNaturally(player.getLocation(), item).setPickupDelay(20);
                }
            }

            player.getInventory().clear();
            player.sendMessage("§8[§2✔ §aSuccess§8] §f⌁ Your Inventory has been dropped");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);

        if (target == null){
            sender.sendMessage("§8[§4✘ §cError§8] §f⌁ Player not found");
            return false;
        }

        if (!(player.hasPermission("drop.others"))){
            sender.sendMessage("§8[§4✘ §cError§8] §f⌁ You lack the permission 'drop.others'");
            return false;
        }

        if (args.length == 1){
            dropAll(target, player);
            return true;
        }

        if (args.length == 2){
            if (args[1].equalsIgnoreCase("hand")){
                dropHand(target, player);
                return true;
            } else if (args[1].equalsIgnoreCase("all")){
                dropAll(target, player);
                return true;
            } else {
                sender.sendMessage("§8[§4✘ §cError§8] §f⌁ Invalid Argument (hand/all)");
                return false;
            }


        }

        return false;
    }


    private void dropAll(Player target, Player sender){

        ItemStack[] inventoryContents = target.getInventory().getContents();

        for (ItemStack item : inventoryContents) {
            if (item != null && !item.getType().isAir()) {
                target.getWorld().dropItemNaturally(target.getLocation(), item).setPickupDelay(20);
            }
        }

        // Inventar leeren
        target.getInventory().clear();

        sender.sendMessage("§8[§2✔ §aSuccess§8] §f⌁ " + target.getName() +"'s Inventory has been dropped");
        target.sendMessage("§8[§8¡ §7Info§8] §f⌁ Your Inventory has been dropped");

    }





    private void dropHand(Player target, Player sender){

        ItemStack stack = target.getInventory().getItemInMainHand();

        if (stack.getType().isAir()){
            sender.sendMessage("§8[§4✘ §cError§8] §f⌁ This player doesn't hold anything");
            return;
        }

        target.dropItem(true);
        sender.sendMessage("§8[§2✔ §aSuccess§8] §f⌁ " + target.getName() +"'s Hand has been dropped");
        target.sendMessage("§8[§8¡ §7Info§8] §f⌁ Your Items have been dropped");


    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        ArrayList<String> completions = new ArrayList<>();
        ArrayList<String> commands = new ArrayList<>();
        ArrayList<String> players = new ArrayList<>();


        if (args.length == 1){
            for (OfflinePlayer player : Bukkit.getOnlinePlayers()){

                players.add(player.getName());

            }

            StringUtil.copyPartialMatches(args[0], players, completions);

            Collections.sort(completions);

        }

        if (args.length == 2){
            commands.add("hand");
            commands.add("all");
            StringUtil.copyPartialMatches(args[1], commands, completions);
            Collections.sort(completions);
        }



        return completions;
    }
}
