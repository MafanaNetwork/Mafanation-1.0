package me.TahaCheji.Mafana.gameItems.Material;

import me.TahaCheji.Mafana.itemData.ItemType;
import me.TahaCheji.Mafana.itemData.ItemValueUtl;
import me.TahaCheji.Mafana.itemData.MasterMaterial;
import me.TahaCheji.Mafana.itemData.RarityType;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class GolladorStand extends MasterMaterial {


    public GolladorStand() {
        super(Material.STICK, "GolladorStand", ItemType.MATERIAL, RarityType.OBSIDAIN, null, true, new ItemValueUtl(100, true), "I mean its a really long stick");
    }
}
