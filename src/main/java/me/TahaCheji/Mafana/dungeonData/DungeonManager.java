package me.TahaCheji.Mafana.dungeonData;

import me.TahaCheji.Mafana.mobData.MasterBoss;
import me.TahaCheji.Mafana.mobData.MasterMob;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public abstract class DungeonManager {

    public String name;
    public ItemStack icon;
    public int maxPlayers;
    public List<MasterMob> mobs = new ArrayList<>();
    public List<MasterBoss> bosses = new ArrayList<>();
    public int maxDungeonMobs;
    public int maxPlayerPerDungeon;
    public List<MasterMob> killedMobs = new ArrayList<>();

    public DungeonManager(String name, ItemStack icon, List<MasterMob> mobs, List<MasterBoss> bosses) {
        this.name = name;
        this.icon = icon;
        this.mobs = mobs;
        this.bosses = bosses;
    }

    /*
    void joinDungeon();
    void startDungeon();
    void endDungeon();
    void spawnMobs();
    void spawnBoss();
    void deSpawnMobs();
    void deSpawnBoss();
    int maxDungeonMobs();
    int maxPlayersPerDungeon();
     */

    public void setMaxDungeonMobs(int maxDungeonMobs) {
        this.maxDungeonMobs = maxDungeonMobs;
    }

    public void setMaxPlayerPerDungeon(int maxPlayerPerDungeon) {
        this.maxPlayerPerDungeon = maxPlayerPerDungeon;
    }

    public void setKilledMobs(List<MasterMob> killedMobs) {
        this.killedMobs = killedMobs;
    }

    public String getName() {
        return name;
    }

    public ItemStack getIcon() {
        return icon;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public List<MasterMob> getMobs() {
        return mobs;
    }

    public List<MasterBoss> getBosses() {
        return bosses;
    }

    public int getMaxDungeonMobs() {
        return maxDungeonMobs;
    }

    public int getMaxPlayerPerDungeon() {
        return maxPlayerPerDungeon;
    }

    public List<MasterMob> getKilledMobs() {
        return killedMobs;
    }
}
