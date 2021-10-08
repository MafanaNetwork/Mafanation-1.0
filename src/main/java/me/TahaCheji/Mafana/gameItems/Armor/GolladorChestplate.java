package me.TahaCheji.Mafana.gameItems.Armor;

import me.TahaCheji.Mafana.crafting.MasterTable;
import me.TahaCheji.Mafana.gameItems.Material.GoldenBlock;
import me.TahaCheji.Mafana.gameItems.Material.GolladorStand;
import me.TahaCheji.Mafana.itemData.ItemType;
import me.TahaCheji.Mafana.itemData.ItemValueUtl;
import me.TahaCheji.Mafana.itemData.MasterArmor;
import me.TahaCheji.Mafana.itemData.RarityType;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class GolladorChestplate extends MasterArmor {


    public GolladorChestplate() {
        super(Material.CHAINMAIL_CHESTPLATE, "GolladorChestplate", ItemType.CHESTPLATE, RarityType.OBSIDAIN, null ,
                50, 55, 25, 5, true, true, null, "AHHHHHHHHH");
        setRecipe(new MasterTable(new GolladorStand().getItem(), 2, null, 1, null, 1, null, 1, null, 1,
                null, 1, null, 1, null ,1, null, 1, getItem()));
    }
}
