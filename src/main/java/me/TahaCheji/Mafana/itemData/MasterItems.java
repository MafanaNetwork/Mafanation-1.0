package me.TahaCheji.Mafana.itemData;

import de.tr7zw.changeme.nbtapi.NBTItem;
import me.TahaCheji.Mafana.Main;
import me.TahaCheji.Mafana.crafting.MasterTable;
import me.TahaCheji.Mafana.itemData.*;
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
import java.util.Collections;
import java.util.List;

public abstract class MasterItems {
    private final Material material;
    private final String name;
    private final ItemType itemType;
    private final RarityType rarityType;
    private final Player player;
    private final int strength;
    private final int health;
    private final int mana;
    private final int speed;
    private final boolean glow;
    private final boolean enhancements;
    private final MasterAbility masterAbility;
    private final ItemValueUtl itemValueUtl;
    private MasterTable recipe;
    private boolean oneTimeUse;
    private int UUID;
    private final List<String> lore;

    public MasterItems(Material material, String name, ItemType itemType, RarityType rarityType, Player player, int strength, int health, int mana, int speed, boolean glow, boolean enhancements, MasterAbility masterAbility, ItemValueUtl itemValueUtl, MasterTable recipe, boolean oneTimeUse, String... lore) {
        this.material = material;
        this.name = name;
        this.itemType = itemType;
        this.rarityType = rarityType;
        this.player = player;
        this.strength = strength;
        this.health = health;
        this.mana = mana;
        this.speed = speed;
        this.glow = glow;
        this.enhancements = enhancements;
        this.masterAbility = masterAbility;
        this.itemValueUtl = itemValueUtl;
        this.recipe = recipe;
        this.oneTimeUse = oneTimeUse;
        this.UUID = ItemUtil.stringToSeed(material.name() + name + rarityType.toString());
        this.lore = Arrays.asList(lore);
    }

    public MasterItems(Material material, String name, ItemType itemType, RarityType rarityType, Player player, int strength, int health, int mana, int speed, boolean glow, boolean enhancements, MasterAbility masterAbility, ItemValueUtl itemValueUtl, String... lore) {
        this.material = material;
        this.name = name;
        this.itemType = itemType;
        this.rarityType = rarityType;
        this.player = player;
        this.strength = strength;
        this.health = health;
        this.mana = mana;
        this.speed = speed;
        this.glow = glow;
        this.enhancements = enhancements;
        this.masterAbility = masterAbility;
        this.itemValueUtl = itemValueUtl;
        this.UUID = ItemUtil.stringToSeed(material.name() + name + rarityType.toString());
        this.lore = Arrays.asList(lore);
    }

    public boolean compare(ItemStack other) {
        int otherUUID = ItemUtil.getIntFromItem(other, "MasterUUID");
        return otherUUID == this.UUID;
    }

    public ItemStack getItem () {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        List<String> list = new ArrayList<>();
        String strength = "§d" + ChatColor.translateAlternateColorCodes('&', "§dStrength" + ": §c+" + this.strength);
        String health = "§c" + ChatColor.translateAlternateColorCodes('&', "§cHealth" + ": §c+" + this.health + " HP");
        String mana = "§9" + ChatColor.translateAlternateColorCodes('&', "§9Mana" + ": §c+" + this.mana);
        String speed = "§b" + ChatColor.translateAlternateColorCodes('&', "§bSpeed" + ": §c+" + this.speed);
        if(name != null || rarityType != null) {
            meta.setDisplayName(rarityType.getColor() + name);
        }
        if(lore != null) {
            list.add("");
            list.add("§7XP §f" + (double) 0 + " §7/ §f" + (double) 10);
            list.add("§7Level §f" + (double) 0);
            if(me.TahaCheji.Mafana.itemData.itemLevel.managers.ConfigManager.getBoolean("use.owner-binding")) {
                if(player == null) {
                    list.add("§c" + "Non");
                } else {
                    list.add("§c" + player.getName());
                }
            }
            list.add("");
            if(player == null) {
                Player newPlayer = Bukkit.getPlayer("Msked");
                list.add(me.TahaCheji.Mafana.itemData.itemLevel.managers.MilestoneManager.getLoreMilestone(newPlayer, item));
            } else {
                list.add(me.TahaCheji.Mafana.itemData.itemLevel.managers.MilestoneManager.getLoreMilestone(player, item));
            }
            if(this.strength != 0) {
                list.add(strength);
            } else {
                list.add("");
            }
            if(this.health != 0) {
                list.add(health);
            } else {
                list.add("");
            }
            if(this.mana != 0) {
                list.add(mana);
            } else {
                list.add("");
            }
            if(this.speed != 0) {
                list.add(speed);
            } else {
                list.add("");
            }
            list.add("");
            list.add("");
            list.add("");
            list.add("");
            list.add("");
            list.add("");
            if(masterAbility != null) {
                list.addAll(masterAbility.toLore());
            }
            list.add("");
            list.add(ChatColor.GOLD + "" + ChatColor.BOLD + "Item Lore:");
            for(String string : lore) {
                list.add('"' + string + '"');
            }
            list.add("");
            list.add(rarityType.getLore() + itemType.getLore());
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        }
        if(glow) {
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            meta.addEnchant(Enchantment.DURABILITY, 1, true);
        }
            meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
            meta.setUnbreakable(true);
        meta.setLore(list);
        item.setItemMeta(meta);
        if(enhancements) {
            EnchancmentsUtl.Enchancments(item, player);
        }
        if(itemValueUtl != null && itemValueUtl.isSellable()) {
            item = NBTUtils.setBoolean(item, "Sellable", true);
            item = NBTUtils.setInt(item, "value",  itemValueUtl.getValue());
            item = NBTUtils.setString(item, "SellValue", ChatColor.GOLD + "Sell Value: $" +  itemValueUtl.getValue());
            item = NBTUtils.setString(item, "BuyValue", ChatColor.GOLD + "Buy Value: $" + itemValueUtl.getValue());
        }
        if(masterAbility != null) {
            item = NBTUtils.setBoolean(item, "hasAbility", true);
            item = NBTUtils.setInt(item, "ManaCost", masterAbility.getManaCost());
            item = NBTUtils.setString(item, "AbilityName", masterAbility.getName());
            item = NBTUtils.setInt(item, "AbilityDamage", masterAbility.getAbilityDamage());
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
        item = ItemUtil.storeIntInItem(item, this.UUID, "MasterUUID");
        if(recipe != null) {
            recipe.setFinalItem(item);
        }
        return item;

    }

    public void onItemUse(Player player, ItemStack item) {
        if (this.oneTimeUse && player.getGameMode() != GameMode.CREATIVE) {
            destroy(item, 1);
        }
    }

    public static void destroy(ItemStack item, int quantity) {
        if (item.getAmount() <= quantity) {
            item.setAmount(0);
        } else {
            item.setAmount(item.getAmount() - quantity);
        }

    }

    public void registerItem() {
        Main.allItems.add(this);
        Main.putItem(name, this);
        System.out.println("Registered " + name);
        if(recipe != null) {
            Main.recipes.add(recipe);
        }
    }

    public abstract void onItemStackCreate(ItemStack var1);

    public abstract boolean leftClickAirAction(Player var1, ItemStack var2);

    public abstract boolean leftClickBlockAction(Player var1, PlayerInteractEvent var2, Block var3, ItemStack var4);

    public abstract boolean rightClickAirAction(Player var1, ItemStack var2);

    public abstract boolean rightClickBlockAction(Player var1, PlayerInteractEvent var2, Block var3, ItemStack var4);

    public abstract boolean shiftLeftClickAirAction(Player var1, ItemStack var2);

    public abstract boolean shiftLeftClickBlockAction(Player var1, PlayerInteractEvent var2, Block var3, ItemStack var4);

    public abstract boolean shiftRightClickAirAction(Player var1, ItemStack var2);

    public abstract boolean shiftRightClickBlockAction(Player var1, PlayerInteractEvent var2, Block var3, ItemStack var4);

    public abstract boolean middleClickAction(Player var1, ItemStack var2);

    public abstract boolean hitEntityAction(Player var1, EntityDamageByEntityEvent var2, Entity var3, ItemStack var4);

    public abstract boolean breakBlockAction(Player var1, BlockBreakEvent var2, Block var3, ItemStack var4);

    public abstract boolean clickedInInventoryAction(Player var1, InventoryClickEvent var2, ItemStack var3, ItemStack var4);

    public int getUUID() {
        return this.UUID;
    }

    public void setRecipe(MasterTable recipe) {
        this.recipe = recipe;
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

    public MasterAbility getMasterAbility() {
        return masterAbility;
    }

    public List<String> getLore() {
        return lore;
    }
}
