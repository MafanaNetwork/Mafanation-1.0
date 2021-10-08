package me.TahaCheji.Mafana.playerData.playerRightClick;

import me.TahaCheji.Mafana.Main;
import me.TahaCheji.Mafana.stats.PlayerStats;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class GuiPlayerInfo implements InventoryHolder {

    public Inventory GuiInv;

    public GuiPlayerInfo(Player player) {
        GuiInv = Bukkit.createInventory(null, 54, ChatColor.GRAY + "" + ChatColor.BOLD + player.getDisplayName() + "'s Stats");
        ArrayList<String> lore = new ArrayList<String>();


        ItemStack newItem = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta newmeta = newItem.getItemMeta();
        newmeta.setDisplayName(ChatColor.GRAY + " ");
        newmeta.setLore(lore);
        newItem.setItemMeta(newmeta);
        for (int emptySlot = 0; emptySlot < GuiInv.getSize(); emptySlot++) {
            if (GuiInv.getItem(emptySlot) == null || GuiInv.getItem(emptySlot).getType().equals(Material.AIR)) {
                GuiInv.setItem(emptySlot, newItem);
            }
        }

        Economy economy = Main.getEcon();

        ItemStack coins = new ItemStack(Material.GOLD_BLOCK);
        ItemMeta meta = coins.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + "Coins: " + economy.getBalance(player));
        meta.setLore(lore);
        coins.setItemMeta(meta);

        ItemStack stats = new ItemStack(Material.ANVIL);
        ItemMeta meta1 = stats.getItemMeta();
        meta1.setDisplayName(ChatColor.AQUA + "Stats");
        lore.add(ChatColor.LIGHT_PURPLE + "Strength: " + PlayerStats.getStrength(player));
        lore.add(ChatColor.RED + "Health: " + PlayerStats.getTotalHealth(player));
        lore.add(ChatColor.BLUE + "Mana: " + PlayerStats.getTotalIntelligence(player));
        lore.add(ChatColor.AQUA + "Speed: " + PlayerStats.getSpeed(player));
        meta1.setLore(lore);
        stats.setItemMeta(meta1);

        GuiInv.setItem(10, player.getInventory().getItem(103));
        GuiInv.setItem(19, player.getInventory().getItem(102));
        GuiInv.setItem(28, player.getInventory().getItem(101));
        GuiInv.setItem(37, player.getInventory().getItem(100));
        GuiInv.setItem(20, player.getItemInHand());
        GuiInv.setItem(24, coins);
        GuiInv.setItem(33, stats);


    }

    @Override
    public Inventory getInventory() {
        return GuiInv;
    }


}
