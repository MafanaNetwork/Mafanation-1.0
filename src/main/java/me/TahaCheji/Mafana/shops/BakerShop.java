package me.TahaCheji.Mafana.shops;

import me.TahaCheji.Mafana.Main;
import me.TahaCheji.Mafana.gameItems.Weapons.GoldenLife;
import me.TahaCheji.Mafana.shopData.ShopUtl;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;

public class BakerShop extends ShopUtl {

    public BakerShop() {
        super("BakersCook", null, new GoldenLife().getItem());
    }
}
