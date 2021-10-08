package me.TahaCheji.Mafana.itemData;

import de.tr7zw.changeme.nbtapi.NBTItem;
import me.TahaCheji.Mafana.Main;
import me.TahaCheji.Mafana.crafting.MasterTable;
import me.TahaCheji.Mafana.utils.ItemUtil;
import me.TahaCheji.Mafana.utils.NBTUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class MasterMaterial {

    private final Material material;
    private final String name;
    private final ItemType itemType;
    private final RarityType rarityType;
    private final Player player;
    private final boolean glow;
    private final ItemValueUtl itemValueUtl;
    private final List<String> lore;
    private int UUID;

    public MasterMaterial(Material material, String name, ItemType itemType, RarityType rarityType, Player player, boolean glow, ItemValueUtl itemValueUtl, String... lore) {
        this.material = material;
        this.name = name;
        this.itemType = itemType;
        this.rarityType = rarityType;
        this.player = player;
        this.glow = glow;
        this.itemValueUtl = itemValueUtl;
        this.lore = Arrays.asList(lore);
        this.UUID = ItemUtil.stringToSeed(material.name() + name + rarityType.toString());
    }

    public boolean compare(ItemStack other) {
        int otherUUID = ItemUtil.getIntFromItem(other, "MasterUUID");
        return otherUUID == this.UUID;
    }

    public ItemStack getItem () {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        List<String> list = new ArrayList<>();
        if(name != null) {
            meta.setDisplayName(rarityType.getColor() + name);
        }
        if(lore != null) {
            for(String string : lore) {
                list.add('"' + string + '"');
                list.add("");
                list.add(ChatColor.DARK_GRAY + "(Right Click To View Recipe)");
                list.add("");
                list.add(rarityType.getColor() + itemType.getLore());
                meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            }
        }
        if(glow) {
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            meta.addEnchant(Enchantment.DURABILITY, 1, true);
        }
        meta.setLore(list);
        item.setItemMeta(meta);
        if(itemValueUtl != null && itemValueUtl.isSellable()) {
            item = NBTUtils.setBoolean(item, "Sellable", true);
            item = NBTUtils.setInt(item, "value",  itemValueUtl.getValue());
            item = NBTUtils.setString(item, "SellValue", ChatColor.GOLD + "Sell Value: $" +  itemValueUtl.getValue());
            item = NBTUtils.setString(item, "BuyValue", ChatColor.GOLD + "Buy Value: $" + itemValueUtl.getValue());
        }
        NBTItem tags = new NBTItem(item);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        if(player != null) {
            tags.setUUID("PlayerUUID", player.getUniqueId());
        } else {
            tags.setString("PlayerUUID", "Nothing");
        }

        tags.setItemStack("Item", item);

        item = tags.getItem();

        item = NBTUtils.setString(item, "ItemKey", item.getItemMeta().getDisplayName());
        item = NBTUtils.setString(item, "ItemType", itemType.getLore());
        item = NBTUtils.setString(item, "ItemRarity", rarityType.getLore());

        item = NBTUtils.setString(item, "ItemDate", dtf.format(now));
        return item;

    }

    public static void destroy(ItemStack item, int quantity) {
        if (item.getAmount() <= quantity) {
            item.setAmount(0);
        } else {
            item.setAmount(item.getAmount() - quantity);
        }

    }

    public void registerItem() {
        Main.allMaterials.add(this);
        System.out.println("Registered " + name);
    }


    public String getName() {
        return name;
    }

    public Material getItemMaterial() {
        return material;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public RarityType getRarityType() {
        return rarityType;
    }

    public boolean isGlow() {
        return glow;
    }

    public List<String> getLore() {
        return lore;
    }
}
