package me.TahaCheji.Mafana.shopData;

import me.TahaCheji.Mafana.Main;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.io.IOException;

public class ShopEvent implements Listener {


    @EventHandler
    public void onClick(InventoryClickEvent inventoryClickEvent) throws IOException {
        for (ShopUtl shopUtl : Main.shopUtl) {
            shopUtl.getClickedItem(inventoryClickEvent);
        }
    }

    @EventHandler
    public void onNpcClick(NPCRightClickEvent e) {
        for (ShopUtl shopUtl : Main.shopUtl) {
            shopUtl.openShop(e);
        }
    }

}
