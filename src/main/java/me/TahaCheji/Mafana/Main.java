package me.TahaCheji.Mafana;
//import com.bringholm.nametagchanger.NameTagChanger;

import de.tr7zw.changeme.nbtapi.NBTItem;
import me.TahaCheji.Mafana.commands.MainCommand;
import me.TahaCheji.Mafana.crafting.MasterTable;
import me.TahaCheji.Mafana.itemData.*;
import me.TahaCheji.Mafana.itemData.MasterItems;
import me.TahaCheji.Mafana.mobData.MasterBoss;
import me.TahaCheji.Mafana.mobData.MasterMob;
import me.TahaCheji.Mafana.shopData.ShopUtl;
import me.TahaCheji.Mafana.voting.MainVote;
import me.TahaCheji.Mafana.utils.*;
import me.TahaCheji.Mafana.stats.*;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.*;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.reflections.Reflections;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.*;

public class Main extends JavaPlugin {

    public static List<MasterTable> recipes = new ArrayList<>();
    public static List<ShopUtl> shopUtl = new ArrayList<>();
    public static Map<String, MasterItems> items = new HashMap();
    public static Map<Integer, MasterItems> itemIDs = new HashMap();
    public static List<MasterItems> allItems = new ArrayList<>();
    public static List<MasterMaterial> allMaterials = new ArrayList<>();
    public static List<MasterArmor> allArmor = new ArrayList<>();
    public static List<MasterMob> gameMobs = new ArrayList<>();
    public static List<MasterMob> activeMobs = new ArrayList<>();
    public static List<MasterBoss> gameBosses = new ArrayList<>();
    public static List<MasterBoss> activeBoss = new ArrayList<>();
    public static List<ArmorStand> armorStands = new ArrayList<>();

    public static HashMap<Player, MasterBoss> playerBossFight = new HashMap<>();



    private static Main instance;
    private FileConfiguration lang = null;
    private File langFile = null;
    private Connection connection;
    public String host, database, username, password, table;
    public int port;
    private static Economy econ = null;

    @Override
    public void onEnable() {
        loadConfig();
        instance = this;

        String packageName = getClass().getPackage().getName();
        for (Class<?> clazz : new Reflections(packageName, ".listeners").getSubTypesOf(Listener.class)) {
            try {
                Listener listener = (Listener) clazz.getDeclaredConstructor().newInstance();
                getServer().getPluginManager().registerEvents(listener, this);
            } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        for (Class<?> clazz : new Reflections(packageName).getSubTypesOf(MasterItems.class)) {
            try {
                MasterItems masterItems = (MasterItems) clazz.getDeclaredConstructor().newInstance();
                masterItems.registerItem();
            } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }

        for (Class<?> clazz : new Reflections(packageName).getSubTypesOf(ShopUtl.class)) {
            try {
                ShopUtl shopUtl = (ShopUtl) clazz.getDeclaredConstructor().newInstance();
                shopUtl.registerShop();
            } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }

        for (Class<?> clazz : new Reflections(packageName).getSubTypesOf(MasterMob.class)) {
            try {
                MasterMob shopUtl = (MasterMob) clazz.getDeclaredConstructor().newInstance();
                shopUtl.registerMob();
                if(shopUtl.getMasterSpawn() != null) {
                    shopUtl.getMasterSpawn().spawnMasterMob();
                }
            } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }

        for (Class<?> clazz : new Reflections(packageName).getSubTypesOf(MasterMaterial.class)) {
            try {
                MasterMaterial shopUtl = (MasterMaterial) clazz.getDeclaredConstructor().newInstance();
                shopUtl.registerItem();
            } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }

        for (Class<?> clazz : new Reflections(packageName).getSubTypesOf(MasterArmor.class)) {
            try {
                MasterArmor shopUtl = (MasterArmor) clazz.getDeclaredConstructor().newInstance();
                shopUtl.registerItem();
            } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }

        for (Class<?> clazz : new Reflections(packageName).getSubTypesOf(MasterBoss.class)) {
            try {
                MasterBoss shopUtl = (MasterBoss) clazz.getDeclaredConstructor().newInstance();
                shopUtl.registerMob();
            } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }

        for(ArmorStand armorStand : armorStands) {
            armorStand.remove();
            armorStands.remove(armorStand);
        }

        try {
            MainVote.createFile();
        } catch (IOException e) {
            e.printStackTrace();
        }


        if (!setupEconomy()) {
            System.out.print("No econ plugin found.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                for(MasterMob mob : activeMobs) {
                    if(mob == null) {
                        continue;
                    }
                    if(mob.getMob().isDead()) {
                        continue;
                    }
                    mob.passiveAbility(mob.getMob());
                }
            }
        }, 0L, 20L);

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                for(MasterBoss mob : activeBoss) {
                    if(mob == null) {
                        continue;
                    }
                    if(mob.getMob().isDead()) {
                        continue;
                    }
                    mob.passiveAbility(mob.getMob());
                }
            }
        }, 0L, 20L);


        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

        this.saveDefaultConfig();

        //config
        getConfig().options().copyDefaults(true);
        saveConfig();

        saveDefaultLangConfig();
        reloadLangFile();

        getCommand("Mf").setExecutor(new MainCommand());


        try {
            Files.initFiles();
        } catch (IOException | InvalidConfigurationException e2) {
            e2.printStackTrace();
        }
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                regeneratePlayerStats();
            }
        }, 20L, 15L);
        Metrics m = new Metrics(this, 8795);
        m.getClass();


        for (Player p : Bukkit.getOnlinePlayers()) {
            PlayerStats pS = new PlayerStats();
            pS.getPlayer().setHealthScale(40);
            pS.setUUID(p.getUniqueId());
            pS.setPlayer(p);
            pS.setMaxHealth(PlayerStats.getTotalHealth(p));
            pS.setCurrentHealth(p.getMaxHealth());
            pS.setMaxIntelligence(PlayerStats.getTotalIntelligence(p));
            pS.setSpeed(PlayerStats.getSpeed(pS.getPlayer()));
            pS.setCurrentIntelligence(pS.getMaxIntelligence());
            PlayerStats.playerStats.put(p.getUniqueId(), pS);
        }

        if (Files.cfg.getBoolean("actionbar.enabled")) {
            Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
                @Override
                public void run() {
                    setPlayerActionBar();
                }
            }, 20L, 15L);
        }
        inventoryCheckEvent();
        statUpdate();
    }

    @Override
    public void onDisable() {
        for (LivingEntity entity : Bukkit.getWorld("world").getLivingEntities()) {
            if(entity instanceof Player) {
                continue;
            }
            entity.remove();
        }
        for(ArmorStand armorStand : armorStands) {
            armorStand.remove();
            armorStands.remove(armorStand);
        }
    }


    public static void statUpdate() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(getInstance(), new Runnable() {
            @Override
            public void run() {

                for (Player p : Bukkit.getOnlinePlayers()) {
                    for (int slot = 0; slot <= p.getInventory().getSize(); slot++) {

                        if (p.getInventory().getItem(slot) != null) {
                            ItemStack is = p.getInventory().getItem(slot);
                            if (!is.hasItemMeta() || !is.getItemMeta().hasDisplayName() || !is.getItemMeta().hasLore())
                                continue;
                            if (new NBTItem(is).hasKey("baseStrength") || new NBTItem(is).hasKey("baseHealth") || new NBTItem(is).hasKey("baseSpeed") || new NBTItem(is).hasKey("baseMana"))
                                continue;
                            is = prepare(is);
                            is = NBTUtils.setString(is, "originalName", is.getItemMeta().getDisplayName());
                            p.getInventory().setItem(slot, is);
                        }
                    }
                }
            }

            private ItemStack prepare(ItemStack is) {
                HashMap<Attribute, Integer> atts = getBaseAttributes(is);
                if (atts == null) return is;
                for (Attribute att : atts.keySet()) {
                    is = NBTUtils.setInt(is, "base" + att.getName().replace(" ", ""), atts.get(att));
                }
                return is;
            }

            private HashMap<Attribute, Integer> getBaseAttributes(ItemStack is) {
                HashMap<Attribute, Integer> atts = new HashMap<Attribute, Integer>();
                if (!is.hasItemMeta() || !is.getItemMeta().hasLore()) return null;
                for (String lore : is.getItemMeta().getLore()) {
                    if (lore.contains("§dStrength: §c+"))
                        atts.put(Attribute.STRENGTH, Integer.valueOf(lore.replace("§dStrength: §c+", "")));
                    if (lore.contains("§cHealth: §c+"))
                        atts.put(Attribute.HEALTH, Integer.valueOf(lore.replace("§cHealth: §c+", "").replace(" HP", "")));
                    if (lore.contains("§9Mana: §c+"))
                        atts.put(Attribute.MANA, Integer.valueOf(lore.replace("§9Mana: §c+", "")));
                    if (lore.contains("§bSpeed: §c+"))
                        atts.put(Attribute.SPEED, Integer.valueOf(lore.replace("§bSpeed: §c+", "")));
                }
                return atts;
            }
        }, 30, 50);
    }

    public static void setPlayerActionBar() {
        for (UUID pUUID : PlayerStats.playerStats.keySet()) {
            PlayerStats pS = PlayerStats.playerStats.get(pUUID);
            pS.getPlayer().setHealthScale(40);
            pS.getPlayer().setSaturation(20);
            pS.setStrength(PlayerStats.getStrength(pS.getPlayer()));
            pS.setMaxIntelligence(PlayerStats.getTotalIntelligence(pS.getPlayer()));
            pS.setMaxHealth(PlayerStats.getTotalHealth(pS.getPlayer()));
            pS.setCurrentHealth(pS.getPlayer().getHealth());
            pS.setSpeed(PlayerStats.getSpeed(pS.getPlayer()));
            String msg = ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("actionbar.msg"));
            msg = msg.replace("%cur_health%", ("" + Math.ceil(pS.getCurrentHealth())).replace(".0", "")).replace("%max_health%", ("" + Math.ceil(pS.getMaxHealth())).replace(".0", ""))
                    .replace("%cur_mana%", ("" + Math.ceil(pS.getCurrentIntelligence())).replace(".0", "")).replace("%max_mana%", ("" + Math.ceil(pS.getMaxIntelligence())).replace(".0", ""))
                    .replace("%cur_strength%", ("" + Math.ceil(pS.getStrength())).replace(".0", ""))
                    .replace("%cur_speed%", ("" + Math.ceil(pS.getSpeed())).replace(".0", ""));

            File playerData = new File("plugins/Mafanation/playerData/" + pS.getPlayer().getUniqueId() + "/data.yml");
            FileConfiguration pD = YamlConfiguration.loadConfiguration(playerData);

            try {
                pD.load(playerData);
            } catch (IOException | InvalidConfigurationException e) {
                e.printStackTrace();
            }
            pS.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(msg));
        }
    }

    /*
    private void VoterHologram() {
        Block block1 = Bukkit.getServer().getWorld("world").getBlockAt((int) 18.526, 73, 161);
        Block block2 = Bukkit.getServer().getWorld("world").getBlockAt((int) 13.512, 73, 162);
        Block block3 = Bukkit.getServer().getWorld("world").getBlockAt((int) 8.477, 73, 161);

        Hologram holo = HologramsAPI.createHologram(this, block1.getLocation());
        Hologram holo2 = HologramsAPI.createHologram(this, block1.getLocation().subtract(0, 1, 0));

        Hologram holo1 = HologramsAPI.createHologram(this, block2.getLocation());
        Hologram holo3 = HologramsAPI.createHologram(this, block2.getLocation().subtract(0, 1, 0));

        Hologram holo4 = HologramsAPI.createHologram(this, block3.getLocation());
        Hologram holo5 = HologramsAPI.createHologram(this, block3.getLocation().subtract(0, 1, 0));

        Bukkit.getScheduler().runTaskTimer(this, () -> {
            holo.insertTextLine(0, ChatColor.GOLD + "Update #1 Votes: " + VoterGuiEvent.option1);
            holo2.insertTextLine(0, ChatColor.WHITE + MainVote.getUpdate1Name());

            holo1.insertTextLine(0, ChatColor.GOLD + "Update #2 Votes: " + VoterGuiEvent.option2);
            holo3.insertTextLine(0, ChatColor.WHITE + MainVote.getUpdate2Name());

            holo4.insertTextLine(0, ChatColor.GOLD + "Update #3 Votes: " + VoterGuiEvent.option3);
            holo5.insertTextLine(0, ChatColor.WHITE + MainVote.getUpdate3Name());


        }, 0L, 0L);

        Bukkit.getScheduler().runTaskTimer(this, () -> {
            holo.removeLine(0);
            holo2.removeLine(0);

            holo1.removeLine(0);
            holo3.removeLine(0);

            holo4.removeLine(0);
            holo5.removeLine(0);


        }, 0L, 0L);
    }
     */

    public void regeneratePlayerStats() {
        for (UUID pUUID : PlayerStats.playerStats.keySet()) {
            PlayerStats pS = PlayerStats.playerStats.get(pUUID);
            if (!pS.getPlayer().isDead()) {
                if (pS.getPlayer().getMaxHealth() >= pS.getCurrentHealth() + (pS.getMaxHealth() / 200)) {
                    pS.getPlayer().setHealthScale(40);
                    pS.setCurrentHealth(pS.getCurrentHealth() + pS.getMaxHealth() / 200);
                } else pS.setCurrentHealth(pS.getMaxHealth());
                pS.getPlayer().setHealthScale(40);
            }
            pS.setCurrentIntelligence(Math.min(pS.getMaxIntelligence(), pS.getCurrentIntelligence() + (pS.getMaxIntelligence() / 25)));
            if (pS.getCurrentHealth() < 5) {
                pS.getPlayer().teleport(lobbyPoint);
                pS.getPlayer().sendMessage(ChatColor.RED + "You Died!");
                pS.getPlayer().playSound(pS.getPlayer().getLocation(), Sound.BLOCK_ANVIL_HIT, 10, 10);
                pS.setCurrentHealth(100);
                regeneratePlayerStats();
                MasterBoss masterBoss = playerBossFight.get(pS.getPlayer());
                if(masterBoss != null) {
                    masterBoss.killMob();
                    pS.getPlayer().sendMessage(ChatColor.RED + "You Failed");
                    playerBossFight.remove(pS.getPlayer(), masterBoss);
                    for(MasterMob minions : masterBoss.getMinions()) {
                        minions.killMob();
                    }
                }
            }
        }
    }


    public void inventoryCheckEvent() {
        BukkitTask t = new BukkitRunnable() {
            @Override
            public void run() {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    for (ItemStack itemStack : p.getInventory()) {
                        if (itemStack == null) {
                            continue;
                        }
                        if (itemStack.getItemMeta() == null) {
                            continue;
                        }
                        ItemStackUtil.addItemTags(itemStack, 10, ItemType.ITEM, RarityType.COAL);
                    }
                }
            }
        }.runTaskTimer(Main.getInstance(), 40L, 20L);
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return true;
    }

    public static Economy getEcon() {
        return econ;
    }

    public void reloadLangFile() {
        if (langFile == null) {
            langFile = new File(getDataFolder(), "lang.yml");
        }
        lang = YamlConfiguration.loadConfiguration(langFile);

        this.getLogger().info("Loaded Language File: " + lang.getString("LanguageName"));
    }

    public FileConfiguration getLangFile() {
        if (lang == null) {
            reloadLangFile();
        }
        return lang;
    }

    public void saveDefaultLangConfig() {
        if (langFile == null) {
            langFile = new File(getDataFolder(), "lang.yml");
        }
        if (!langFile.exists()) {
            this.saveResource("lang.yml", false);
        }
    }

    public String c(String name) {
        String caption = getLangFile().getString(name);
        if (caption == null) {
            this.getLogger().warning("Missing caption: " + name);
            caption = "&c[missing caption]";
        }

        caption = ChatColor.translateAlternateColorCodes('&', caption);

        return caption;
    }

    public void loadConfig() {
        getConfig().options().copyDefaults(true);
        saveConfig();
    }

    // MYSQL
    public void mysqlSetup() {
        host = this.getConfig().getString("host");
        port = this.getConfig().getInt("port");
        database = this.getConfig().getString("database");
        username = this.getConfig().getString("username");
        password = this.getConfig().getString("password");
        table = this.getConfig().getString("table");

        try {
            synchronized (this) {
                if (getConnection() != null && !getConnection().isClosed()) {
                    return;
                }
                Class.forName("com.mysql.jdbc.Driver");

                setConnection(DriverManager.getConnection("jdbc:mysql://" + this.host + ":"
                        + this.port + "/" + this.database, this.username, this.password));

                Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "MYSQL CONNECTED");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    private Location lobbyPoint = null;

    public Location getLobbyPoint() {
        if (lobbyPoint == null) {
            int x = 0;
            int y = 0;
            int z = 0;
            String world = "world";

            try {
                x = Main.getInstance().getConfig().getInt("lobby-point.x");
                y = Main.getInstance().getConfig().getInt("lobby-point.y");
                z = Main.getInstance().getConfig().getInt("lobby-point.z");
                world = Main.getInstance().getConfig().getString("lobby-point.world");
            } catch (Exception ex) {
                Main.getInstance().getLogger().severe("Lobby point failed with exception: " + ex);
                ex.printStackTrace();
            }

            lobbyPoint = new Location(Bukkit.getWorld(world), x, y, z);
        }

        return lobbyPoint;
    }

    public static MasterItems getItemFromID(int id) {
        MasterItems item = (MasterItems) itemIDs.get(id);

        return item == null ? (MasterItems) items.get("null") : item;
    }

    public static void putItem(String name, MasterItems item) {
        items.put(name, item);
        itemIDs.put(item.getUUID(), item);
    }


    public static Main getInstance() {
        return instance;
    }


}
