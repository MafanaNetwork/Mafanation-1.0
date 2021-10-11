package me.TahaCheji.Mafana.mobData;

import com.google.common.base.Preconditions;
import me.TahaCheji.Mafana.Main;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class MasterSpawn {

    private final Map<Entity, MasterMob> masterMobMap = new HashMap<>();
    private final Map<Entity, MasterBoss> masterBossMap = new HashMap<>();

    private List<MasterMob> masterMobList = new ArrayList<>();
    private List<MasterBoss> masterBossList = new ArrayList<>();
    private MasterMob mob;
    private MasterBoss boss;
    private int mobCap;
    private int spawnTime;
    private Location radius1;
    private Location radius2;
    private Location location;

    public MasterSpawn(int mobCap, int spawnTime, Location radius1, Location radius2, MasterMob... mobs) {
        this.mobCap = mobCap;
        this.spawnTime = spawnTime;
        this.radius1 = radius1;
        this.radius2 = radius2;
        this.masterMobList = Arrays.asList(mobs);
    }

    public MasterSpawn(int mobCap, int spawnTime, Location radius1, Location radius2, MasterBoss... bosses) {
        this.mobCap = mobCap;
        this.spawnTime = spawnTime;
        this.radius1 = radius1;
        this.radius2 = radius2;
        this.masterBossList = Arrays.asList(bosses);
    }

    public MasterSpawn(Location location, MasterMob mobs) {
        this.location = location;
        this.mob = mobs;
    }

    public MasterSpawn(Location location, MasterBoss boss) {
        this.location = location;
        this.boss = boss;
    }

    public void spawnMasterMobs() {
        BukkitTask task = new BukkitRunnable() {
            final Set<Entity> spawned = masterMobMap.keySet();
            final List<Entity> removal = new ArrayList<>();

            @Override
            public void run() {
                for (Entity entity : spawned) {
                    if (!entity.isValid() || entity.isDead()) removal.add(entity);
                }
                spawned.removeAll(removal);
                // Spawning Algorithm
                int diff = mobCap - masterMobMap.size();
                if (diff <= 0) return;
                int spawnAmount = (int) (Math.random() * (diff + 1)), count = 0;
                while (count <= spawnAmount) {
                    count++;
                    Location location = getRandomLocation(radius1, radius2);
                    if (!isSpawnable(location)) continue;
                    double random = Math.random() * 101, previous = 0;
                    MasterMob typeToSpawn = masterMobList.get(0);
                    for (MasterMob type : masterMobList) {
                        previous += type.getSpawnChance();
                        if (random <= previous) {
                            typeToSpawn = type;
                            break;
                        }
                    }
                    masterMobMap.put(typeToSpawn.spawnMob(location, null), typeToSpawn);
                }
            }
        }.runTaskTimer(Main.getInstance(), 0L, spawnTime * 20);
    }

    public void spawnBossesMobs() {
        BukkitTask task = new BukkitRunnable() {
            final Set<Entity> spawned = masterBossMap.keySet();
            final List<Entity> removal = new ArrayList<>();

            @Override
            public void run() {
                for (Entity entity : spawned) {
                    if (!entity.isValid() || entity.isDead()) removal.add(entity);
                }
                spawned.removeAll(removal);
                // Spawning Algorithm
                int diff = mobCap - masterBossMap.size();
                if (diff <= 0) return;
                int spawnAmount = (int) (Math.random() * (diff + 1)), count = 0;
                while (count <= spawnAmount) {
                    count++;
                    Location location = getRandomLocation(radius1, radius2);
                    if (!isSpawnable(location)) continue;
                    double random = Math.random() * 101, previous = 0;
                    MasterBoss typeToSpawn = masterBossList.get(0);
                    for (MasterBoss type : masterBossList) {
                        previous += type.getSpawnChance();
                        if (random <= previous) {
                            typeToSpawn = type;
                            break;
                        }
                    }
                    masterBossMap.put(typeToSpawn.spawnMob(location, null), typeToSpawn);
                }
            }
        }.runTaskTimer(Main.getInstance(), 0L, spawnTime * 20);
    }

    public void spawnMasterMob() {
        mob.spawnMob(location, null);
    }

    public void spawnMasterBoss() {
        boss.spawnMob(location, null);
    }

    private boolean isSpawnable(Location loc) {
        Block feetBlock = loc.getBlock(), headBlock = loc.clone().add(0, 1, 0).getBlock(), upperBlock = loc.clone().add(0, 2, 0).getBlock();
        return feetBlock.isPassable() && !feetBlock.isLiquid() && headBlock.isPassable() && !headBlock.isLiquid() && upperBlock.isPassable() && !upperBlock.isLiquid();
    }

    public static Location getRandomLocation(Location loc1, Location loc2) {
        Preconditions.checkArgument(loc1.getWorld() == loc2.getWorld());
        double minX = Math.min(loc1.getX(), loc2.getX());
        double minY = Math.min(loc1.getY(), loc2.getY());
        double minZ = Math.min(loc1.getZ(), loc2.getZ());
        double maxX = Math.max(loc1.getX(), loc2.getX());
        double maxY = Math.max(loc1.getY(), loc2.getY());
        double maxZ = Math.max(loc1.getZ(), loc2.getZ());
        return new Location(loc1.getWorld(), randomDouble(minX, maxX), randomDouble(minY, maxY), randomDouble(minZ, maxZ));
    }

    public static double randomDouble(double min, double max) {
        return min + ThreadLocalRandom.current().nextDouble(Math.abs(max - min + 1));
    }

}
