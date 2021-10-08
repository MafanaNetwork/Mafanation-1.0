package me.TahaCheji.Mafana.gameItems.Material;

import me.TahaCheji.Mafana.itemData.ItemType;
import me.TahaCheji.Mafana.itemData.ItemValueUtl;
import me.TahaCheji.Mafana.itemData.MasterMaterial;
import me.TahaCheji.Mafana.itemData.RarityType;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class GoldenBlock extends MasterMaterial {


    public GoldenBlock() {
        super(Material.GOLD_BLOCK, "GoldenBlock", ItemType.MATERIAL, RarityType.GOLD, null, true, null, "Now this is true gold");
    }
}
