package me.TahaCheji.Mafana.commands;

import me.TahaCheji.Mafana.Main;
import me.TahaCheji.Mafana.playerData.playerInfo.playerCoinSpent;
import me.TahaCheji.Mafana.playerData.playerSellHistory;
import me.TahaCheji.Mafana.utils.NBTUtils;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;

public class Commands implements CommandExecutor {


    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (label.equalsIgnoreCase("Sell")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("You cannot do this!");
                return true;
            }
            Player player = (Player) sender;
            if (args.length == 0) {
                if (player.getItemInHand() == null || player.getItemInHand().getType() == Material.AIR) {
                    player.sendMessage(ChatColor.GRAY + "You need to hold something to sell it!");
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 10, 10);
                    return true;
                }
                if (NBTUtils.getBoolean(player.getItemInHand(), "Sellable") == false) {
                    player.sendMessage(ChatColor.GRAY + "You are not able to sell this item!");
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 10, 10);
                    return true;
                }
                TextComponent message = new TextComponent("Would you like to sell this " +
                        "item for: " + net.md_5.bungee.api.ChatColor.WHITE + "$" + NBTUtils.getInt(player.getItemInHand(), "value") + " x" + player.getItemInHand().getAmount());
                message.setColor(net.md_5.bungee.api.ChatColor.GOLD);
                message.setBold(false);
                message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new
                        ComponentBuilder("Click Accept or Decline").color(net.md_5.bungee.api.ChatColor.WHITE).italic(true).create()));
                player.spigot().sendMessage(message);
                TextComponent accept = new TextComponent("[Accept]");
                accept.setColor(net.md_5.bungee.api.ChatColor.GREEN);
                accept.setBold(true);
                accept.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new
                        ComponentBuilder("Click to Accept").color(net.md_5.bungee.api.ChatColor.GREEN).italic(true).create()));
                accept.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/Sell Accept"));
                TextComponent decline = new TextComponent("[Decline]");
                decline.setColor(net.md_5.bungee.api.ChatColor.RED);
                decline.setBold(true);
                decline.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new
                        ComponentBuilder("Click to Decline").color(net.md_5.bungee.api.ChatColor.RED).italic(true).create()));
                decline.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/Sell Decline"));
                player.spigot().sendMessage(accept);
                player.sendMessage(ChatColor.GOLD + "Or");
                player.spigot().sendMessage(decline);
                return true;
            }
            if (args[0].equalsIgnoreCase("Accept")) {
                player.getItemInHand();
                if (player.getItemInHand().getType() == Material.AIR) {
                    player.sendMessage(ChatColor.GRAY + "You need to hold something to sell it!");
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 10, 10);
                    return true;
                }
                if (!NBTUtils.getBoolean(player.getItemInHand(), "Sellable")) {
                    player.sendMessage(ChatColor.GRAY + "You are not able to sell this item!");
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 10, 10);
                    return true;
                }
                Economy economy = Main.getEcon();
                double amount = NBTUtils.getInt(player.getItemInHand(), "value") * player.getInventory().getItemInHand().getAmount();
                economy.depositPlayer(player, amount);
                player.sendMessage(ChatColor.GREEN + "Accepted! " + ChatColor.GOLD + "You Sold Your Item For: " + ChatColor.WHITE + "$" + NBTUtils.getInt(player.getItemInHand(), "value") + " x" + player.getItemInHand().getAmount());
                try {
                    playerCoinSpent.setCoinsSpent(player, playerCoinSpent.getCoinsSpent(player) + NBTUtils.getInt(player.getItemInHand(), "value") * player.getItemInHand().getAmount());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    playerSellHistory.setSellHistory(player, player.getItemInHand());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ItemStack item = new ItemStack(Material.AIR);
                player.setItemInHand(item);
                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES, 10, 10);
                return true;
            }
            if (args[0].equalsIgnoreCase("Decline")) {
                player.sendMessage(ChatColor.RED + "Declined!");
                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 10, 10);
                return true;
            }
        }
        if (label.equalsIgnoreCase("Reboot")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("You cannot do this!");
                return true;
            }
            Player player = (Player) sender;
            if (player.getGameMode() == GameMode.ADVENTURE) {
                player.sendMessage("You do not have perms");
                return true;
            }
            Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
                @Override
                public void run() {
                    player.performCommand("stop");
                }
            }, 1200L);


            return true;
        }

        return false;
    }
}
