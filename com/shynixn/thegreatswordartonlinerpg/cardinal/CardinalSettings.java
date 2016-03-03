/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.GameMode
 *  org.bukkit.Sound
 *  org.bukkit.command.ConsoleCommandSender
 *  org.bukkit.configuration.file.YamlConfiguration
 *  org.bukkit.plugin.java.JavaPlugin
 */
package com.shynixn.thegreatswordartonlinerpg.cardinal;

import com.shynixn.thegreatswordartonlinerpg.cardinal.Cardinal;
import com.shynixn.thegreatswordartonlinerpg.cardinal.CardinalException;
import com.shynixn.thegreatswordartonlinerpg.resources.enums.CardinalSystem;
import com.shynixn.thegreatswordartonlinerpg.resources.enums.Priority;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import libraries.com.darkblade12.particleeffects.ParticleEffect;
import libraries.com.shynixn.utilities.BukkitLocation;
import libraries.com.shynixn.utilities.BukkitUtilities;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class CardinalSettings {
    private boolean performanceBooster = false;
    private CardinalSystem system = CardinalSystem.SAO;
    private ArrayList<String> worlds = new ArrayList();
    private GameMode loginGameMode = GameMode.SURVIVAL;
    private boolean loginSpawnLastLocation = true;
    private boolean loginScreenMessage = true;
    private String loginTitle = "TheGreatSwordArtOnlineRPG";
    private String[] loginsubTitle = new String[]{"Loading player resources ...", "Loading custom entities ...", "Joining server ..."};
    private boolean loginAnnouncement = false;
    private String loginText = "Player <player> has joined SwordArtOnlineRPG.";
    private GameMode logoutGameMode = GameMode.SURVIVAL;
    private boolean logoutAnnouncement = false;
    private String logoutText = "Player <player> has left SwordArtOnlineRPG.";
    private BukkitLocation logoutLocation = new BukkitLocation("world", 0.0, 0.0, 0.0, 0.0f, 0.0f);
    private com.shynixn.thegreatswordartonlinerpg.resources.effects.ParticleEffect destroyingParticleEffect = new com.shynixn.thegreatswordartonlinerpg.resources.effects.ParticleEffect(ParticleEffect.FIREWORKS_SPARK, 50, 0.1f, 2.0f, 2.0f, 2.0f);
    private boolean destroyingIemDespawnAnimation = true;
    private boolean destroyingMobKilledAnimation = true;
    private boolean destroyingPlayerKilledAnimation = true;
    private com.shynixn.thegreatswordartonlinerpg.resources.effects.Sound destroyingSound = new com.shynixn.thegreatswordartonlinerpg.resources.effects.Sound(Sound.ANVIL_BREAK, 1.0, 1.0);
    private int teleportDelay = 3;
    private boolean teleportEffectEnabled = true;
    private boolean teleportJump = true;
    private com.shynixn.thegreatswordartonlinerpg.resources.effects.ParticleEffect teleportParticleEffect = new com.shynixn.thegreatswordartonlinerpg.resources.effects.ParticleEffect(ParticleEffect.PORTAL, 100, 0.1f, 2.0f, 2.0f, 2.0f);
    private com.shynixn.thegreatswordartonlinerpg.resources.effects.Sound teleportSound = new com.shynixn.thegreatswordartonlinerpg.resources.effects.Sound(Sound.ENDERMAN_TELEPORT, 1.0, 1.0);
    private boolean keepInventory = true;
    private boolean noDrops = false;
    private boolean completeInventoryReset = false;
    private boolean loseXPToNextLevel = false;
    private boolean deathKick = false;
    private int deathKickRespawnDelay = 60;
    private boolean deathBan = false;
    private int weaponSlot = 0;
    private boolean menuEnabled = true;
    private int menuSlot = 4;
    private boolean socialEnabled = true;
    private int socialSlot = 5;
    private boolean scoreboardEnabled = true;
    private int scoreboardSlot = 6;
    private boolean equipMentEnabled = true;
    private int equipMentdSlot = 7;
    private boolean skillEnabled = true;
    private int skilldSlot = 8;
    private boolean worldprotection = false;
    private boolean commandblocker = false;
    private int echopetsSlot = 2;
    private boolean echopetEnabled = true;
    private int skillapiSlot = 1;
    private boolean skillapienabled = true;
    private boolean skillapiInsertskills = false;
    private boolean skillapiselectracebyclass = false;
    private boolean floorBeatScreenMessage = true;
    private String floorBeatScreenTitle = "Congratulation";
    private String floorBeatScreenSubTitle = "You have beaten floor <floorid>.";
    private boolean floorBeatAnounceMent = false;
    private String floorBeatAnounceMentText = "Player <player> has beaten floor <floorid>.";
    private boolean floorBeatautomaticTeleport = true;
    private boolean raceJoinAnnounceMent = false;
    private String raceJoinAnnounceMentText = "Welcome player <player> in your race.";
    private static CardinalSettings instance;
    private JavaPlugin plugin;

    private CardinalSettings(JavaPlugin plugin) {
        if (plugin == null) {
            throw new IllegalArgumentException("Plugin cannot be null!");
        }
        this.plugin = plugin;
    }

    public static CardinalSettings getInstance(JavaPlugin plugin) {
        if (instance == null) {
            instance = new CardinalSettings(plugin);
        }
        return instance;
    }

    private File getFile() {
        return new File(this.plugin.getDataFolder() + "/resources", "settings.yml");
    }

    public void reload() throws Exception {
        try {
            Bukkit.getServer().getConsoleSender().sendMessage((Object)ChatColor.WHITE + "====================================================");
            Bukkit.getServer().getConsoleSender().sendMessage((Object)ChatColor.WHITE + "################  " + (Object)ChatColor.AQUA + "LOADING SETTINGS" + (Object)ChatColor.WHITE + "  ################");
            Bukkit.getServer().getConsoleSender().sendMessage((Object)ChatColor.WHITE + "====================================================");
            YamlConfiguration config = new YamlConfiguration();
            if (!this.getFile().exists()) {
                BukkitUtilities.u().copyFile(this.plugin.getResource("settings.yml"), this.getFile());
            }
            config.load(this.getFile());
            this.performanceBooster = config.getBoolean("world-separator");
            boolean[] systems = new boolean[]{config.getBoolean("sao-floor-system"), config.getBoolean("alo-race-system"), config.getBoolean("ggo-gun-system"), config.getBoolean("classic-rpg-system")};
            if (this.getSystemsNumber(systems) != 1) {
                throw new Exception();
            }
            if (this.getSystemNumber(systems) == 0) {
                this.system = CardinalSystem.SAO;
            }
            if (this.getSystemNumber(systems) == 1) {
                this.system = CardinalSystem.ALO;
            }
            if (this.getSystemNumber(systems) == 2) {
                this.system = CardinalSystem.GGO;
            }
            if (this.getSystemNumber(systems) == 3) {
                this.system = CardinalSystem.CLASSIC;
            }
            this.loginGameMode = this.getGameModeFromName(config.getString("login.gamemode"));
            if (this.loginGameMode == null) {
                throw new Exception();
            }
            this.loginSpawnLastLocation = config.getBoolean("login.spawn-last-location");
            this.loginScreenMessage = config.getBoolean("login.messages.screen.enabled");
            this.loginTitle = ChatColor.translateAlternateColorCodes((char)'&', (String)config.getString("login.messages.screen.title"));
            this.loginsubTitle = new String[]{ChatColor.translateAlternateColorCodes((char)'&', (String)config.getString("login.messages.screen.subtitle.1")), ChatColor.translateAlternateColorCodes((char)'&', (String)config.getString("login.messages.screen.subtitle.2")), ChatColor.translateAlternateColorCodes((char)'&', (String)config.getString("login.messages.screen.subtitle.3"))};
            this.loginAnnouncement = config.getBoolean("login.messages.announcement.enabled");
            this.loginText = ChatColor.translateAlternateColorCodes((char)'&', (String)config.getString("login.messages.announcement.text"));
            this.logoutGameMode = this.getGameModeFromName(config.getString("logout.gamemode"));
            if (this.loginGameMode == null) {
                throw new Exception();
            }
            this.logoutAnnouncement = config.getBoolean("logout.messages.announcement.enabled");
            this.logoutText = ChatColor.translateAlternateColorCodes((char)'&', (String)config.getString("logout.messages.announcement.text"));
            this.logoutLocation = new BukkitLocation(config.getString("logout.location.world"), config.getDouble("logout.location.x"), config.getDouble("logout.location.y"), config.getDouble("logout.location.z"), config.getDouble("logout.location.yaw"), config.getDouble("logout.location.pitch"));
            ParticleEffect particleEffect = ParticleEffect.getParticelEffectByName(config.getString("destroying-effect.effect.type"));
            if (particleEffect == null) {
                throw new Exception();
            }
            this.destroyingParticleEffect = new com.shynixn.thegreatswordartonlinerpg.resources.effects.ParticleEffect(particleEffect, config.getDouble("destroying-effect.effect.amount"), config.getDouble("destroying-effect.effect.speed"), config.getDouble("destroying-effect.effect.size.x"), config.getDouble("destroying-effect.effect.size.y"), config.getDouble("destroying-effect.effect.size.z"));
            this.destroyingIemDespawnAnimation = config.getBoolean("destroying-effect.itemdespawn");
            this.destroyingMobKilledAnimation = config.getBoolean("destroying-effect.entitydeath");
            this.destroyingPlayerKilledAnimation = config.getBoolean("destroying-effect.playerdeath");
            Sound sound = BukkitUtilities.u().getSoundByName(config.getString("destroying-effect.sound.type"));
            if (sound == null) {
                throw new Exception();
            }
            this.destroyingSound = new com.shynixn.thegreatswordartonlinerpg.resources.effects.Sound(sound, config.getDouble("destroying-effect.sound.volume"), config.getDouble("destroying-effect.sound.pitch"));
            particleEffect = ParticleEffect.getParticelEffectByName(config.getString("sao.floor-teleport.effect.type"));
            if (particleEffect == null) {
                throw new Exception();
            }
            this.teleportParticleEffect = new com.shynixn.thegreatswordartonlinerpg.resources.effects.ParticleEffect(particleEffect, config.getDouble("sao.floor-teleport.effect.amount"), config.getDouble("sao.floor-teleport.effect.speed"), config.getDouble("sao.floor-teleport.effect.size.x"), config.getDouble("sao.floor-teleport.effect.size.y"), config.getDouble("sao.floor-teleport.effect.size.z"));
            this.teleportDelay = config.getInt("sao.floor-teleport.teleport-delay");
            this.teleportEffectEnabled = config.getBoolean("sao.floor-teleport.enabled");
            sound = BukkitUtilities.u().getSoundByName(config.getString("sao.floor-teleport.sound.type"));
            if (sound == null) {
                throw new Exception();
            }
            this.teleportSound = new com.shynixn.thegreatswordartonlinerpg.resources.effects.Sound(sound, config.getDouble("sao.floor-teleport.sound.volume"), config.getDouble("sao.floor-teleport.sound.pitch"));
            this.keepInventory = config.getBoolean("player-death.keepinventory");
            this.noDrops = config.getBoolean("player-death.no-drops");
            this.completeInventoryReset = config.getBoolean("player-death.complete-inventory-reset");
            this.loseXPToNextLevel = config.getBoolean("player-death.lose-nextlevel-xp");
            this.deathKick = config.getBoolean("player-death.kick.enabled");
            this.deathKickRespawnDelay = config.getInt("player-death.kick.minutes-until-rejoin");
            this.deathBan = config.getBoolean("player-death.bann.enabled");
            this.weaponSlot = config.getInt("inventory-items.weapon.slot");
            this.menuEnabled = config.getBoolean("inventory-items.menu.enabled");
            this.menuSlot = config.getInt("inventory-items.menu.slot");
            this.socialEnabled = config.getBoolean("inventory-items.social.enabled");
            this.socialSlot = config.getInt("inventory-items.social.slot");
            this.scoreboardEnabled = config.getBoolean("inventory-items.scoreboard.enabled");
            this.scoreboardSlot = config.getInt("inventory-items.scoreboard.slot");
            this.equipMentEnabled = config.getBoolean("inventory-items.equipment.enabled");
            this.equipMentdSlot = config.getInt("inventory-items.equipment.slot");
            this.skillEnabled = config.getBoolean("inventory-items.skills.enabled");
            this.skilldSlot = config.getInt("inventory-items.skills.slot");
            if (this.weaponSlot < 0 || this.weaponSlot > 8 || this.menuSlot < 0 || this.menuSlot > 8 || this.socialSlot < 0 || this.socialSlot > 8 || this.scoreboardSlot < 0 || this.scoreboardSlot > 8 || this.equipMentdSlot < 0 || this.equipMentdSlot > 8 || this.skilldSlot < 0 || this.skilldSlot > 8) {
                throw new Exception();
            }
            this.commandblocker = config.getBoolean("random-settings.commandblocker");
            this.worldprotection = config.getBoolean("random-settings.worldprotection");
            this.loadWorlds();
            this.raceJoinAnnounceMent = config.getBoolean("alo.race-join.announcement.enabled");
            this.raceJoinAnnounceMentText = ChatColor.translateAlternateColorCodes((char)'&', (String)config.getString("alo.race-join.announcement.text"));
            this.floorBeatScreenMessage = config.getBoolean("sao.floor-beat.screen-message.enabled");
            this.floorBeatScreenTitle = ChatColor.translateAlternateColorCodes((char)'&', (String)config.getString("sao.floor-beat.screen-message.title"));
            this.floorBeatScreenSubTitle = ChatColor.translateAlternateColorCodes((char)'&', (String)config.getString("sao.floor-beat.screen-message.subtitle"));
            this.floorBeatAnounceMent = config.getBoolean("sao.floor-beat.announcement.enabled");
            this.floorBeatAnounceMentText = ChatColor.translateAlternateColorCodes((char)'&', (String)config.getString("sao.floor-beat.announcement.text"));
            this.floorBeatautomaticTeleport = config.getBoolean("sao.floor-beat.automatic-teleport");
            this.echopetEnabled = config.getBoolean("echopet.inventory-items.petmenu.enabled");
            this.echopetsSlot = config.getInt("echopet.inventory-items.petmenu.slot");
            this.skillapienabled = config.getBoolean("skillapi.inventory-items.skillmenu.enabled");
            this.skillapiSlot = config.getInt("skillapi.inventory-items.skillmenu.slot");
            this.skillapiInsertskills = config.getBoolean("skillapi.auto.insert-class-skills-in-skillbar");
            this.skillapiselectracebyclass = config.getBoolean("skillapi.auto.select-race-by-class");
            Bukkit.getServer().getConsoleSender().sendMessage((Object)ChatColor.WHITE + "====================================================");
            Bukkit.getServer().getConsoleSender().sendMessage((Object)ChatColor.WHITE + "###################  " + (Object)ChatColor.AQUA + "COMPLETED" + (Object)ChatColor.WHITE + "  ####################");
            Bukkit.getServer().getConsoleSender().sendMessage((Object)ChatColor.WHITE + "====================================================");
            if (this.system == CardinalSystem.GGO) {
                Cardinal.call().sendException(new CardinalException("Selected gamesystem GGO", "GGO gamesystem is not supported yet", "Select another system", Priority.HIGH));
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new CardinalException("Settings cannot be loaded", "Plugin cannot run correctly", "Delete settings.yml", Priority.HIGH);
        }
    }

    private GameMode getGameModeFromName(String name) throws Exception {
        GameMode[] arrgameMode = GameMode.values();
        int n = arrgameMode.length;
        int n2 = 0;
        while (n2 < n) {
            GameMode value = arrgameMode[n2];
            if (value.toString().equalsIgnoreCase(name)) {
                return value;
            }
            ++n2;
        }
        return null;
    }

    private int getSystemNumber(boolean[] systems) {
        int i = 0;
        while (i < systems.length) {
            if (systems[i]) {
                return i;
            }
            ++i;
        }
        return -1;
    }

    private int getSystemsNumber(boolean[] systems) {
        int counter = 0;
        int i = 0;
        while (i < systems.length) {
            if (systems[i]) {
                ++counter;
            }
            ++i;
        }
        return counter;
    }

    public boolean isPerformanceBooster() {
        return this.performanceBooster;
    }

    public CardinalSystem getSystem() {
        return this.system;
    }

    public GameMode getLoginGameMode() {
        return this.loginGameMode;
    }

    public boolean isLoginSpawnLastLocation() {
        return this.loginSpawnLastLocation;
    }

    public boolean isLoginScreenMessage() {
        return this.loginScreenMessage;
    }

    public String getLoginTitle() {
        return this.loginTitle;
    }

    public String[] getLoginsubTitle() {
        return this.loginsubTitle;
    }

    public boolean isLoginAnnouncement() {
        return this.loginAnnouncement;
    }

    public String getLoginText() {
        return this.loginText;
    }

    public GameMode getLogoutGameMode() {
        return this.logoutGameMode;
    }

    public boolean isLogoutAnnouncement() {
        return this.logoutAnnouncement;
    }

    public String getLogoutText() {
        return this.logoutText;
    }

    public com.shynixn.thegreatswordartonlinerpg.resources.effects.ParticleEffect getDestroyingParticleEffect() {
        return this.destroyingParticleEffect;
    }

    public boolean isDestroyingIemDespawnAnimation() {
        return this.destroyingIemDespawnAnimation;
    }

    public boolean isDestroyingMobKilledAnimation() {
        return this.destroyingMobKilledAnimation;
    }

    public boolean isDestroyingPlayerKilledAnimation() {
        return this.destroyingPlayerKilledAnimation;
    }

    public com.shynixn.thegreatswordartonlinerpg.resources.effects.Sound getDestroyingSound() {
        return this.destroyingSound;
    }

    public int getTeleportDelay() {
        return this.teleportDelay;
    }

    public boolean isTeleportEffectEnabled() {
        return this.teleportEffectEnabled;
    }

    public com.shynixn.thegreatswordartonlinerpg.resources.effects.ParticleEffect getTeleportParticleEffect() {
        return this.teleportParticleEffect;
    }

    public com.shynixn.thegreatswordartonlinerpg.resources.effects.Sound getTeleportSound() {
        return this.teleportSound;
    }

    public boolean isKeepInventory() {
        return this.keepInventory;
    }

    public boolean isNoDrops() {
        return this.noDrops;
    }

    public boolean isCompleteInventoryReset() {
        return this.completeInventoryReset;
    }

    public boolean isLoseXPToNextLevel() {
        return this.loseXPToNextLevel;
    }

    public boolean isDeathKick() {
        return this.deathKick;
    }

    public int getDeathKickRespawnDelay() {
        return this.deathKickRespawnDelay;
    }

    public boolean isDeathBan() {
        return this.deathBan;
    }

    public int getWeaponSlot() {
        return this.weaponSlot;
    }

    public boolean isMenuEnabled() {
        return this.menuEnabled;
    }

    public int getMenuSlot() {
        return this.menuSlot;
    }

    public boolean isRaceJoinAnnounceMent() {
        return this.raceJoinAnnounceMent;
    }

    public String getRaceJoinAnnounceMentText() {
        return this.raceJoinAnnounceMentText;
    }

    public boolean isSocialEnabled() {
        return this.socialEnabled;
    }

    public int getSocialSlot() {
        return this.socialSlot;
    }

    public boolean isScoreboardEnabled() {
        return this.scoreboardEnabled;
    }

    public int getScoreboardSlot() {
        return this.scoreboardSlot;
    }

    public boolean isEquipMentEnabled() {
        return this.equipMentEnabled;
    }

    public int getEquipMentdSlot() {
        return this.equipMentdSlot;
    }

    public boolean isSkillEnabled() {
        return this.skillEnabled;
    }

    public int getSkilldSlot() {
        return this.skilldSlot;
    }

    public boolean isWorldprotection() {
        return this.worldprotection;
    }

    public boolean isCommandblocker() {
        return this.commandblocker;
    }

    public boolean isFloorBeatScreenMessage() {
        return this.floorBeatScreenMessage;
    }

    public String getFloorBeatScreenTitle() {
        return this.floorBeatScreenTitle;
    }

    public String getFloorBeatScreenSubTitle() {
        return this.floorBeatScreenSubTitle;
    }

    public boolean isFloorBeatAnounceMent() {
        return this.floorBeatAnounceMent;
    }

    public String getFloorBeatAnounceMentText() {
        return this.floorBeatAnounceMentText;
    }

    public boolean isFloorBeatautomaticTeleport() {
        return this.floorBeatautomaticTeleport;
    }

    public boolean isTeleportJump() {
        return this.teleportJump;
    }

    public int getEchopetsSlot() {
        return this.echopetsSlot;
    }

    public boolean isEchopetEnabled() {
        return this.echopetEnabled;
    }

    public int getSkillapiSlot() {
        return this.skillapiSlot;
    }

    public boolean isSkillapienabled() {
        return this.skillapienabled;
    }

    public boolean isSkillapiInsertskills() {
        return this.skillapiInsertskills;
    }

    public boolean isSkillapiselectracebyclass() {
        return this.skillapiselectracebyclass;
    }

    public BukkitLocation getLogoutLocation() {
        return this.logoutLocation;
    }

    public void addWorld(String world) {
        if (!this.containsWorld(world)) {
            this.worlds.add(world);
            this.saveWorlds();
        }
    }

    public void removeWorld(String world) {
        if (this.containsWorld(world)) {
            this.worlds.remove(world);
            this.saveWorlds();
        }
    }

    public boolean containsWorld(String world) {
        for (String s : this.getWorlds()) {
            if (!s.equalsIgnoreCase(world)) continue;
            return true;
        }
        return false;
    }

    public List<String> getWorlds() {
        return Arrays.asList(this.worlds.toArray(new String[this.worlds.size()]));
    }

    private void loadWorlds() throws Exception {
        try {
            File file = new File(this.plugin.getDataFolder() + "/resources/cardinal", "worlds.dat");
            this.worlds.clear();
            String[] arrstring = BukkitUtilities.u().readAllLines(file);
            int n = arrstring.length;
            int n2 = 0;
            while (n2 < n) {
                String s = arrstring[n2];
                this.worlds.add(s);
                ++n2;
            }
        }
        catch (Exception e) {
            throw new CardinalException("Loading worlds from worlds.dat failed", "Plugin will not work correctly", "Fix or Delete worlds.dat", Priority.MEDIUM);
        }
    }

    private void saveWorlds() {
        try {
            File file = new File(this.plugin.getDataFolder() + "/resources/cardinal", "worlds.dat");
            BukkitUtilities.u().writeAllLines(file, this.worlds);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}

