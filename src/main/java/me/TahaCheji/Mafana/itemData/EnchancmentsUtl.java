package me.TahaCheji.Mafana.itemData;

import de.tr7zw.changeme.nbtapi.NBTItem;
import me.TahaCheji.Mafana.Main;
import me.TahaCheji.Mafana.stats.PlayerStats;
import org.bukkit.*;
//import org.bukkit.craftbukkit.v1_17_R1.entity.CraftEntity;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class EnchancmentsUtl implements Listener {

    public static void Enchancments(ItemStack itemStack, Player player) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        int random = ThreadLocalRandom.current().nextInt(5);
        if (random == 1) {
            itemMeta.setDisplayName(EnchancmentsType.FIERY.getLore() + " " + itemStack.getItemMeta().getDisplayName());
        }
        if (random == 2) {
            itemMeta.setDisplayName(EnchancmentsType.NATURAL.getLore() + " " + itemStack.getItemMeta().getDisplayName());
        }
        if (random == 3) {
            itemMeta.setDisplayName(EnchancmentsType.LIQUEFIED.getLore() + " " + itemStack.getItemMeta().getDisplayName());
        }
        if (random == 4) {
            itemMeta.setDisplayName(EnchancmentsType.DAMAGED.getLore() + " " + itemStack.getItemMeta().getDisplayName());
        }
        if (random == 0) {
            ArrayList<String> new_lore = new ArrayList<>();
            for (String str : itemStack.getItemMeta().getLore()) {
                if (!str.contains("§d" + ChatColor.translateAlternateColorCodes('&', "§dStrength" + ": §c+")) ||
                        !str.contains("§c" + ChatColor.translateAlternateColorCodes('&', "§cHealth" + ": §c+")) ||
                        !str.contains("§9" + ChatColor.translateAlternateColorCodes('&', "§9Mana" + ": §c+")) ||
                        !str.contains("§b" + ChatColor.translateAlternateColorCodes('&', "§bSpeed" + ": §c+"))) {
                    new_lore.add(str);
                }
            }
            itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            itemMeta.setLore(new_lore);
            itemStack.setItemMeta(itemMeta);
            return;
        }

        if (random == 1) {
            ArrayList<String> new_lore = new ArrayList<>();
            for (String str : itemStack.getItemMeta().getLore()) {
                if (!str.contains("§d" + ChatColor.translateAlternateColorCodes('&', "§dStrength" + ": §c+")) ||
                        !str.contains("§c" + ChatColor.translateAlternateColorCodes('&', "§cHealth" + ": §c+")) ||
                        !str.contains("§9" + ChatColor.translateAlternateColorCodes('&', "§9Mana" + ": §c+")) ||
                        !str.contains("§b" + ChatColor.translateAlternateColorCodes('&', "§bSpeed" + ": §c+"))) {
                    new_lore.add(str);
                }
            }
            new_lore.set(12, ChatColor.GOLD + "" + ChatColor.BOLD + "Fiery Enhancement");
            new_lore.set(13, ChatColor.WHITE + "[Right Click] Launch a fire ball!");
            itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            itemMeta.setLore(new_lore);
            itemStack.setItemMeta(itemMeta);
        }

        if (random == 2) {
            ArrayList<String> new_lore = new ArrayList<>();
            for (String str : itemStack.getItemMeta().getLore()) {
                if (!str.contains("§d" + ChatColor.translateAlternateColorCodes('&', "§dStrength" + ": §c+")) ||
                        !str.contains("§c" + ChatColor.translateAlternateColorCodes('&', "§cHealth" + ": §c+")) ||
                        !str.contains("§9" + ChatColor.translateAlternateColorCodes('&', "§9Mana" + ": §c+")) ||
                        !str.contains("§b" + ChatColor.translateAlternateColorCodes('&', "§bSpeed" + ": §c+"))) {
                    new_lore.add(str);
                }
            }
            for (String lore : itemStack.getItemMeta().getLore()) {
                if (lore.contains("§dStrength: §c+")) {
                    int oldStats = Integer.valueOf(lore.replace("§dStrength: §c+", "")) * 40 / 100;
                    int newStats = Integer.valueOf(lore.replace("§dStrength: §c+", "")) + oldStats;
                    String str = "§d" + ChatColor.translateAlternateColorCodes('&', "§dStrength" + ": §c+" + newStats);
                    if (!new_lore.contains(str)) {
                        new_lore.set(6, str);
                        new_lore.set(12, ChatColor.GREEN + "" + ChatColor.BOLD + "Natural Enhancement");
                        new_lore.set(13, ChatColor.WHITE + "Increased Stats by 40%!");
                    }
                    itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                    itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
                    itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                    itemMeta.setLore(new_lore);
                    itemStack.setItemMeta(itemMeta);
                    NBTItem nbt = new NBTItem(itemStack);
                    nbt.setInteger("baseStrength", newStats);
                }
                if (lore.contains("§cHealth: §c+")) {
                    int oldStats = Integer.valueOf(lore.replace("§cHealth: §c+", "").replace(" HP", "")) * 40 / 100;
                    int newStats = Integer.valueOf(lore.replace("§cHealth: §c+", "").replace(" HP", "")) + oldStats;
                    String str = "§c" + ChatColor.translateAlternateColorCodes('&', "§cHealth" + ": §c+" + newStats + " HP");
                    if (!new_lore.contains(str)) {
                        new_lore.set(7, str);
                        new_lore.set(12, ChatColor.GREEN + "" + ChatColor.BOLD + "Natural Enhancement");
                        new_lore.set(13, ChatColor.WHITE + "Increased Stats by 40%!");
                    }
                    itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                    itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
                    itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                    itemMeta.setLore(new_lore);
                    itemStack.setItemMeta(itemMeta);
                    NBTItem nbt = new NBTItem(itemStack);
                    nbt.setInteger("baseHealth", newStats);
                }
                if (lore.contains("§9Mana: §c+")) {
                    int oldStats = Integer.valueOf(lore.replace("§9Mana: §c+", "")) * 40 / 100;
                    int newStats = Integer.valueOf(lore.replace("§9Mana: §c+", "")) + oldStats;
                    String str = "§9" + ChatColor.translateAlternateColorCodes('&', "§9Mana" + ": §c+" + newStats);
                    if (!new_lore.contains(str)) {
                        new_lore.set(8, str);
                        new_lore.set(12, ChatColor.GREEN + "" + ChatColor.BOLD + "Natural Enhancement");
                        new_lore.set(13, ChatColor.WHITE + "Increased Stats by 40%!");
                    }
                    itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                    itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
                    itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                    itemMeta.setLore(new_lore);
                    itemStack.setItemMeta(itemMeta);
                    NBTItem nbt = new NBTItem(itemStack);
                    nbt.setInteger("baseMana", newStats);
                }
                if (lore.contains("§bSpeed: §c+")) {
                    int oldStats = Integer.valueOf(lore.replace("§bSpeed: §c+", "")) * 40 / 100;
                    int newStats = Integer.valueOf(lore.replace("§bSpeed: §c+", "")) + oldStats;
                    String str = "§b" + ChatColor.translateAlternateColorCodes('&', "§bSpeed" + ": §c+" + newStats);
                    if (!new_lore.contains(str)) {
                        new_lore.set(9, str);
                        new_lore.set(12, ChatColor.GREEN + "" + ChatColor.BOLD + "Natural Enhancement");
                        new_lore.set(13, ChatColor.WHITE + "Increased Stats by 40%!");
                    }
                    itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                    itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
                    itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                    itemMeta.setLore(new_lore);
                    itemStack.setItemMeta(itemMeta);
                    NBTItem nbt = new NBTItem(itemStack);
                    nbt.setInteger("baseSpeed", newStats);

                }
            }
        }
        if (random == 3) {
            ArrayList<String> new_lore = new ArrayList<>();
            for (String str : itemStack.getItemMeta().getLore()) {
                if (!str.contains("§d" + ChatColor.translateAlternateColorCodes('&', "§dStrength" + ": §c+")) ||
                        !str.contains("§c" + ChatColor.translateAlternateColorCodes('&', "§cHealth" + ": §c+")) ||
                        !str.contains("§9" + ChatColor.translateAlternateColorCodes('&', "§9Mana" + ": §c+")) ||
                        !str.contains("§b" + ChatColor.translateAlternateColorCodes('&', "§bSpeed" + ": §c+"))) {
                    new_lore.add(str);
                }
            }
            for (String lore : itemStack.getItemMeta().getLore()) {
                if (lore.contains("§bSpeed: §c+")) {
                    int newStats = Integer.valueOf(lore.replace("§bSpeed: §c+", "")) + 200;
                    String str = "§b" + ChatColor.translateAlternateColorCodes('&', "§bSpeed" + ": §c+" + newStats);
                    if (!new_lore.contains(str)) {
                        new_lore.set(9, str);
                        new_lore.set(12, ChatColor.AQUA + "" + ChatColor.BOLD + "Liquefied Enhancement");
                        new_lore.set(13, ChatColor.WHITE + "+200 Speed!");
                    }
                    itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                    itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
                    itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                    itemMeta.setLore(new_lore);
                    itemStack.setItemMeta(itemMeta);
                    NBTItem nbt = new NBTItem(itemStack);
                    nbt.setInteger("baseSpeed", newStats);
                    return;
                }
            }
            for (String lore : itemStack.getItemMeta().getLore()) {
                if (!(lore.contains("§bSpeed: §c+"))) {
                    int newStats = 200;
                    String str = ChatColor.translateAlternateColorCodes('&', "§bSpeed" + ": §c+" + newStats);
                    if (!new_lore.contains(str)) {
                        new_lore.set(9, str);
                        new_lore.set(12, ChatColor.AQUA + "" + ChatColor.BOLD + "Liquefied Enhancement");
                        new_lore.set(13, ChatColor.WHITE + "+200 Speed!");
                    }
                    itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                    itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
                    itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                    itemMeta.setLore(new_lore);
                    itemStack.setItemMeta(itemMeta);
                    NBTItem nbt = new NBTItem(itemStack);
                    nbt.setInteger("baseSpeed", newStats);
                    return;
                }
            }
        }
        if (random == 4) {
            ArrayList<String> new_lore = new ArrayList<>();
            for (String str : itemStack.getItemMeta().getLore()) {
                if (!str.contains("§d" + ChatColor.translateAlternateColorCodes('&', "§dStrength" + ": §c+")) ||
                        !str.contains("§c" + ChatColor.translateAlternateColorCodes('&', "§cHealth" + ": §c+")) ||
                        !str.contains("§9" + ChatColor.translateAlternateColorCodes('&', "§9Mana" + ": §c+")) ||
                        !str.contains("§b" + ChatColor.translateAlternateColorCodes('&', "§bSpeed" + ": §c+"))) {
                    new_lore.add(str);
                }
            }
            for (String lore : itemStack.getItemMeta().getLore()) {
                if (lore.contains("§dStrength: §c+")) {
                    int oldStats = Integer.valueOf(lore.replace("§dStrength: §c+", "")) * 40 / 100;
                    int newStats = Integer.valueOf(lore.replace("§dStrength: §c+", "")) - oldStats;
                    String str = "§d" + ChatColor.translateAlternateColorCodes('&', "§dStrength" + ": §c+" + newStats);
                    if (!new_lore.contains(str)) {
                        new_lore.set(6, str);
                        new_lore.set(12, ChatColor.GRAY + "" + ChatColor.BOLD + "Damaged Enhancement");
                        new_lore.set(13, ChatColor.WHITE + "Reduced Stats by 40%!");
                    }
                    itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                    itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
                    itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                    itemMeta.setLore(new_lore);
                    itemStack.setItemMeta(itemMeta);
                    NBTItem nbt = new NBTItem(itemStack);
                    nbt.setInteger("baseStrength", newStats);
                }
                if (lore.contains("§cHealth: §c+")) {
                    int oldStats = Integer.valueOf(lore.replace("§cHealth: §c+", "").replace(" HP", "")) * 40 / 100;
                    int newStats = Integer.valueOf(lore.replace("§cHealth: §c+", "").replace(" HP", "")) - oldStats;
                    String str = "§c" + ChatColor.translateAlternateColorCodes('&', "§cHealth" + ": §c+" + newStats + " HP");
                    if (!new_lore.contains(str)) {
                        new_lore.set(7, str);
                        new_lore.set(12, ChatColor.GRAY + "" + ChatColor.BOLD + "Damaged Enhancement");
                        new_lore.set(13, ChatColor.WHITE + "Reduced Stats by 40%!");
                    }
                    itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                    itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
                    itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                    itemMeta.setLore(new_lore);
                    itemStack.setItemMeta(itemMeta);
                    NBTItem nbt = new NBTItem(itemStack);
                    nbt.setInteger("baseHealth", newStats);
                }
                if (lore.contains("§9Mana: §c+")) {
                    int oldStats = Integer.valueOf(lore.replace("§9Mana: §c+", "")) * 40 / 100;
                    int newStats = Integer.valueOf(lore.replace("§9Mana: §c+", "")) - oldStats;
                    String str = "§9" + ChatColor.translateAlternateColorCodes('&', "§9Mana" + ": §c+" + newStats);
                    if (!new_lore.contains(str)) {
                        new_lore.set(8, str);
                        new_lore.set(12, ChatColor.GRAY + "" + ChatColor.BOLD + "Damaged Enhancement");
                        new_lore.set(13, ChatColor.WHITE + "Reduced Stats by 40%!");
                    }
                    itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                    itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
                    itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                    itemMeta.setLore(new_lore);
                    itemStack.setItemMeta(itemMeta);
                    NBTItem nbt = new NBTItem(itemStack);
                    nbt.setInteger("baseMana", newStats);
                }
                if (lore.contains("§bSpeed: §c+")) {
                    int oldStats = Integer.valueOf(lore.replace("§bSpeed: §c+", "")) * 40 / 100;
                    int newStats = Integer.valueOf(lore.replace("§bSpeed: §c+", "")) - oldStats;
                    String str = "§b" + ChatColor.translateAlternateColorCodes('&', "§bSpeed" + ": §c+" + newStats);
                    if (!new_lore.contains(str)) {
                        new_lore.set(9, str);
                        new_lore.set(12, ChatColor.GRAY + "" + ChatColor.BOLD + "Damaged Enhancement");
                        new_lore.set(13, ChatColor.WHITE + "Reduced Stats by 40%!");
                    }
                    itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                    itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
                    itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                    itemMeta.setLore(new_lore);
                    itemStack.setItemMeta(itemMeta);
                    NBTItem nbt = new NBTItem(itemStack);
                    nbt.setInteger("baseSpeed", newStats);

                }
            }
        }

    }
}



