package me.TahaCheji.Mafana.mobData;

import me.TahaCheji.Mafana.Main;
import me.TahaCheji.Mafana.utils.NBTUtils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public abstract class MasterMob{

    private final String name;
    private final double spawnChance;
    private final double maxHealth;
    private final EntityType type;
    private final int strength;
    private final int defense;
    private final int damage;
    private final int speed;
    private final ItemStack mainItem;
    private final ItemStack[] armor;
    private List<LootItem> lootTable;
    private UUID uuid;
    private MasterSpawn masterSpawn;

    LivingEntity entity;

    public MasterMob(String name, double spawnChance, EntityType type, double maxHealth, int strength, int defense, int damage, int speed,
                     ItemStack mainItem, ItemStack[] armor, LootItem... lootItems) {
        this.name = name;
        this.spawnChance = spawnChance;
        this.maxHealth = maxHealth;
        this.type = type;
        this.strength = strength;
        this.defense = defense;
        this.damage = damage;
        this.speed = speed;
        this.mainItem = mainItem;
        this.armor = armor;
        this.uuid = UUID.randomUUID();
        lootTable = Arrays.asList(lootItems);
    }

    public MasterMob(String name, double spawnChance, double maxHealth, EntityType type, int strength, int defense, int damage, int speed, ItemStack mainItem, ItemStack[] armor) {
        this.name = name;
        this.spawnChance = spawnChance;
        this.maxHealth = maxHealth;
        this.type = type;
        this.strength = strength;
        this.defense = defense;
        this.damage = damage;
        this.speed = speed;
        this.mainItem = mainItem;
        this.armor = armor;
    }

    public LivingEntity spawnMob(Location location, Player player) {
        LivingEntity entity = (LivingEntity) location.getWorld().spawnEntity(location, type);
        entity.setCustomNameVisible(true);
        entity.setCustomName(name + " " + ChatColor.RED + "♥" + maxHealth + "♥");
        entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(maxHealth);
        entity.setHealth(maxHealth);
        if(defense != 0) {
            entity.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(defense);
        }
        if(speed != 0) {
            entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(speed);
        }
        entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(strength * damage / 5);
        EntityEquipment inv = entity.getEquipment();
        if (armor != null) inv.setArmorContents(armor);
        inv.setHelmetDropChance(0f);
        inv.setChestplateDropChance(0f);
        inv.setLeggingsDropChance(0f);
        inv.setBootsDropChance(0f);
        inv.setItemInMainHand(mainItem);
        inv.setItemInMainHandDropChance(0f);
        NBTUtils.setEntityString(entity, "MobName", uuid.toString());
        this.entity = entity;
        Main.activeMobs.add(this);
        return entity;
    }

    public void killMob() {
        Main.activeMobs.remove(this);
        if(!getMob().isDead()) {
            getMob().remove();
        }
    }


    public void tryDropLoot(Location location, Player player) {
        for (LootItem item : lootTable) {
            item.tryDropItem(location, player);
        }
    }

    public void setMasterSpawn(MasterSpawn masterSpawn) {
        this.masterSpawn = masterSpawn;
    }

    public MasterSpawn getMasterSpawn() {
        return masterSpawn;
    }

    public void setLootTable(LootItem... lootTable) {
        this.lootTable = Arrays.asList(lootTable);
    }

    public void registerMob() {
        Main.gameMobs.add(this);
    }

    public Entity getMob() {
        return entity;
    }

    public abstract void onAbilityHit(Player player, Entity entity);

    public abstract void passiveAbility(Entity entity);

    public MasterMob getMob (String name) {
        if(Objects.equals(name, ChatColor.stripColor(getName()))) {
            return this;
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public double getSpawnChance() {
        return spawnChance;
    }

    public double getMaxHealth() {
        return maxHealth;
    }

    public EntityType getType() {
        return type;
    }

    public int getStrength() {
        return strength;
    }

    public int getDefense() {
        return defense;
    }

    public int getDamage() {
        return damage;
    }

    public int getSpeed() {
        return speed;
    }

    public ItemStack getMainItem() {
        return mainItem;
    }

    public ItemStack[] getArmor() {
        return armor;
    }

    public List<LootItem> getLootTable() {
        return lootTable;
    }

}
