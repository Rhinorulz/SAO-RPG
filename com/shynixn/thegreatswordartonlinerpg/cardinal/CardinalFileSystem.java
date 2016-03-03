/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  org.bukkit.configuration.file.FileConfiguration
 *  org.bukkit.configuration.file.YamlConfiguration
 *  org.bukkit.plugin.PluginDescriptionFile
 *  org.bukkit.plugin.java.JavaPlugin
 */
package com.shynixn.thegreatswordartonlinerpg.cardinal;

import com.shynixn.thegreatswordartonlinerpg.cardinal.Cardinal;
import com.shynixn.thegreatswordartonlinerpg.cardinal.CardinalException;
import com.shynixn.thegreatswordartonlinerpg.resources.enums.Priority;
import java.io.File;
import libraries.com.shynixn.utilities.BukkitFileManager;
import libraries.com.shynixn.utilities.BukkitUtilities;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public final class CardinalFileSystem
extends BukkitFileManager {
    private JavaPlugin plugin;
    private File aincradFolder;
    private File langFolder;
    private File cardinalFolder;
    private File mobsFolder;
    private File savesFolder;
    private File skillsFolder;
    private File floorsFolder;
    private File spawnPointsFolder;
    private File racesFolder;
    private File wingsFolder;

    protected CardinalFileSystem(JavaPlugin plugin) {
        super(plugin, "");
        this.plugin = plugin;
    }

    public void prepareFileSystem() throws Exception {
        Cardinal.getLogger().logHeadLine("Preparing Filesystem");
        this.createFolders();
        Cardinal.getLogger().logHeadLine("Creating folder structures");
        this.createNewFileSystem();
        Cardinal.getLogger().logHeadLine("Converting old filesystem");
        this.convertToNewFileSystem();
        Cardinal.getLogger().logHeadLine("Scanning folders");
        this.scanFileSystem();
        Cardinal.getLogger().logHeadLine("Completed");
    }

    private void createFolders() throws Exception {
        this.aincradFolder = new File(this.getDataFolder(), "resources");
        this.langFolder = new File(this.getDataFolder(), "lang");
        this.cardinalFolder = new File(this.getDataFolder(), "resources/cardinal");
        this.floorsFolder = new File(this.getDataFolder(), "resources/floors");
        this.mobsFolder = new File(this.getDataFolder(), "resources/mobs");
        this.savesFolder = new File(this.getDataFolder(), "resources/player");
        this.skillsFolder = new File(this.getDataFolder(), "resources/skills");
        this.spawnPointsFolder = new File(this.getDataFolder(), "resources/spawnpoints");
        this.racesFolder = new File(this.getDataFolder(), "resources/races");
        this.wingsFolder = new File(this.getDataFolder(), "resources/wings");
    }

    private void scanFileSystem() throws Exception {
        if (this.folderSize(this.getDataFolder()) > 10000000) {
            throw new CardinalException("File size too big", "Server can get stuck while loading", "Check the files and remove the big files", Priority.HIGH);
        }
    }

    private long folderSize(File directory) {
        long length = 0;
        File[] arrfile = directory.listFiles();
        int n = arrfile.length;
        int n2 = 0;
        while (n2 < n) {
            File file = arrfile[n2];
            length = file.isFile() ? (length += file.length()) : (length += this.folderSize(file));
            ++n2;
        }
        return length;
    }

    private void createNewFileSystem() throws Exception {
        if (!this.aincradFolder.exists()) {
            this.aincradFolder.mkdir();
        }
        if (!this.langFolder.exists()) {
            this.langFolder.mkdir();
        }
        if (!this.floorsFolder.exists()) {
            this.floorsFolder.mkdir();
        }
        if (!this.cardinalFolder.exists()) {
            this.cardinalFolder.mkdir();
        }
        if (!this.mobsFolder.exists()) {
            this.mobsFolder.mkdir();
        }
        if (!this.savesFolder.exists()) {
            this.savesFolder.mkdir();
        }
        if (!this.skillsFolder.exists()) {
            this.skillsFolder.mkdir();
        }
        if (!this.spawnPointsFolder.exists()) {
            this.spawnPointsFolder.mkdir();
        }
        if (!this.racesFolder.exists()) {
            this.racesFolder.mkdir();
        }
        if (!this.wingsFolder.exists()) {
            this.wingsFolder.mkdir();
        }
    }

    private void convertToNewFileSystem() throws Exception {
        File oldFloors = new File(this.getDataFolder(), "Floors");
        File oldMobs = new File(this.getDataFolder(), "Mobs");
        File oldspawnpoints = new File(this.getDataFolder(), "MobSpawnpoints");
        File oldskills = new File(this.getDataFolder(), "Skills");
        File inventories = new File(this.getDataFolder(), "Inventory");
        File worldsFile = new File(this.getDataFolder(), "worlds.txt");
        File storeFile = new File(this.getDataFolder(), "store.yml");
        File langFile = new File(this.getDataFolder(), "lang.yml");
        File configFile = new File(this.getDataFolder(), "config.yml");
        if (!this.plugin.getDescription().getVersion().equals(String.valueOf(this.plugin.getConfig().getDouble("version")))) {
            Cardinal.call().sendException(new CardinalException("Config version does not match plugin version", "Recreating config.yml", "Autosolution", Priority.MEDIUM));
            configFile.delete();
            this.plugin.saveDefaultConfig();
        }
        if (worldsFile.exists() || storeFile.exists() || langFile.exists() || oldFloors.exists() || oldMobs.exists() || oldskills.exists() || oldspawnpoints.exists()) {
            configFile.delete();
            this.plugin.saveDefaultConfig();
        }
        if (inventories.exists()) {
            BukkitUtilities.u().saveFolderDeleting(inventories);
        }
        if (worldsFile.exists()) {
            worldsFile.delete();
        }
        if (storeFile.exists()) {
            storeFile.delete();
        }
        if (langFile.exists()) {
            langFile.delete();
        }
        if (oldFloors.exists()) {
            this.convertToNewFloorSystem(oldFloors);
        }
        if (oldMobs.exists()) {
            this.convertToNewMobsSystem(oldMobs);
        }
        if (oldskills.exists()) {
            this.converToNewSkillSystem(oldskills);
        }
        if (oldspawnpoints.exists()) {
            this.converToNewSpawnpointsSystem(oldspawnpoints);
        }
    }

    private void converToNewSpawnpointsSystem(File oldspawnpoints) throws Exception {
        YamlConfiguration dataConfig = new YamlConfiguration();
        YamlConfiguration fileConfig = new YamlConfiguration();
        String[] arrstring = oldspawnpoints.list();
        int n = arrstring.length;
        int n2 = 0;
        while (n2 < n) {
            String name = arrstring[n2];
            try {
                File oldfile = new File(oldspawnpoints, name);
                File newfile = new File(this.spawnPointsFolder, name);
                dataConfig.load(oldfile);
                fileConfig.set("spawnpoint.general.name", dataConfig.get("point.name"));
                fileConfig.set("spawnpoint.general.enabled", dataConfig.get("point.enabled"));
                fileConfig.set("spawnpoint.general.world", dataConfig.get("center.world"));
                fileConfig.set("spawnpoint.mob.name", dataConfig.get("mob.name"));
                fileConfig.set("spawnpoint.mob.amount", dataConfig.get("mob.amount"));
                fileConfig.set("spawnpoint.delays.maxrespawn", dataConfig.get("point.maxrespawndelay"));
                fileConfig.set("spawnpoint.delays.maxdespawn", dataConfig.get("point.maxdespawndelay"));
                fileConfig.set("spawnpoint.center.x", dataConfig.get("center.x"));
                fileConfig.set("spawnpoint.center.y", dataConfig.get("center.y"));
                fileConfig.set("spawnpoint.center.z", dataConfig.get("center.z"));
                fileConfig.set("spawnpoint.radius.x", dataConfig.get("radius.x"));
                fileConfig.set("spawnpoint.radius.y", dataConfig.get("radius.y"));
                fileConfig.set("spawnpoint.radius.z", dataConfig.get("radius.z"));
                fileConfig.set("spawnpoint.radius.detection", dataConfig.get("radius.detection"));
                fileConfig.save(newfile);
            }
            catch (Exception e) {
                Cardinal.call().sendException(new CardinalException("Converting spawnpoints failed", "Cannot create new spawpoints", "Check the spawnpoint files and remove the invalid one", Priority.MEDIUM));
            }
            ++n2;
        }
        BukkitUtilities.u().saveFolderDeleting(oldspawnpoints);
    }

    private void converToNewSkillSystem(File oldskills) throws Exception {
        YamlConfiguration dataConfig = new YamlConfiguration();
        YamlConfiguration fileConfig = new YamlConfiguration();
        String[] arrstring = oldskills.list();
        int n = arrstring.length;
        int n2 = 0;
        while (n2 < n) {
            String name = arrstring[n2];
            try {
                File oldSkillFolder = new File(oldskills, name);
                File newSkillFolder = new File(this.skillsFolder, name);
                if (!newSkillFolder.exists()) {
                    newSkillFolder.mkdir();
                }
                File oldSkillFile = new File(oldSkillFolder, "skill.yml");
                File newSkillFile = new File(newSkillFolder, "skill.yml");
                dataConfig.load(oldSkillFile);
                fileConfig.set("skill.general.name", dataConfig.get("name"));
                fileConfig.set("skill.general.displayname", dataConfig.get("displayname"));
                fileConfig.set("skill.general.lore.1", dataConfig.get("lore1"));
                fileConfig.set("skill.general.lore.2", dataConfig.get("lore2"));
                fileConfig.set("skill.general.lore.3", dataConfig.get("lore3"));
                fileConfig.set("skill.general.enabled", dataConfig.get("enabled"));
                fileConfig.set("skill.general.type", dataConfig.get("type"));
                fileConfig.set("skill.duration.cooldown", dataConfig.get("cooldown"));
                fileConfig.set("skill.duration.loading", dataConfig.get("loading"));
                fileConfig.set("skill.duration.executing", dataConfig.get("duration"));
                fileConfig.save(newSkillFile);
                BukkitUtilities.u().writeAllLines(new File(newSkillFolder, "enchantments.txt"), BukkitUtilities.u().readAllLines(new File(oldSkillFolder, "enchantmentsdata.txt")));
                BukkitUtilities.u().writeAllLines(new File(newSkillFolder, "hitparticles.txt"), BukkitUtilities.u().readAllLines(new File(oldSkillFolder, "hitparticledata.txt")));
                BukkitUtilities.u().writeAllLines(new File(newSkillFolder, "hitpotioneffects.txt"), BukkitUtilities.u().readAllLines(new File(oldSkillFolder, "hitpotioneffectdata.txt")));
                BukkitUtilities.u().writeAllLines(new File(newSkillFolder, "hitsounds.txt"), BukkitUtilities.u().readAllLines(new File(oldSkillFolder, "hitsounddata.txt")));
                BukkitUtilities.u().writeAllLines(new File(newSkillFolder, "launches.txt"), BukkitUtilities.u().readAllLines(new File(oldSkillFolder, "launchsdata.txt")));
                BukkitUtilities.u().writeAllLines(new File(newSkillFolder, "particles.txt"), BukkitUtilities.u().readAllLines(new File(oldSkillFolder, "particledata.txt")));
                BukkitUtilities.u().writeAllLines(new File(newSkillFolder, "potioneffects.txt"), BukkitUtilities.u().readAllLines(new File(oldSkillFolder, "potioneffectdata.txt")));
                BukkitUtilities.u().writeAllLines(new File(newSkillFolder, "sounds.txt"), BukkitUtilities.u().readAllLines(new File(oldSkillFolder, "sounddata.txt")));
                BukkitUtilities.u().writeAllLines(new File(newSkillFolder, "teleports.txt"), BukkitUtilities.u().readAllLines(new File(oldSkillFolder, "teleportingdata.txt")));
                BukkitUtilities.u().saveFolderDeleting(oldSkillFolder);
            }
            catch (Exception e) {
                Cardinal.call().sendException(new CardinalException("Converting skills failed", "Cannot create new skills", "Check the skill files and remove the invalid one", Priority.MEDIUM));
            }
            ++n2;
        }
        BukkitUtilities.u().saveFolderDeleting(oldskills);
    }

    private void convertToNewMobsSystem(File oldMobsFolder) throws Exception {
        YamlConfiguration dataConfig = new YamlConfiguration();
        YamlConfiguration fileConfig = new YamlConfiguration();
        String[] arrstring = oldMobsFolder.list();
        int n = arrstring.length;
        int n2 = 0;
        while (n2 < n) {
            String name = arrstring[n2];
            try {
                File oldMobFolder = new File(oldMobsFolder, name);
                File newMobFolder = new File(this.mobsFolder, name);
                if (!newMobFolder.exists()) {
                    newMobFolder.mkdir();
                }
                File oldMobFile = new File(oldMobFolder, "mob.yml");
                File newMobFile = new File(newMobFolder, "mob.yml");
                dataConfig.load(oldMobFile);
                fileConfig.set("mob.general.name", dataConfig.get("mob.name"));
                fileConfig.set("mob.general.displayname", dataConfig.get("mob.displayName"));
                fileConfig.set("mob.general.type", dataConfig.get("mob.type"));
                fileConfig.set("mob.general.health", dataConfig.get("mob.health"));
                fileConfig.set("mob.general.damage", dataConfig.get("mob.damage"));
                fileConfig.set("mob.general.firedamage", dataConfig.get("burnduration"));
                fileConfig.set("mob.general.baby", dataConfig.get("special.properties.babymode"));
                fileConfig.set("mob.general.rider", dataConfig.get("mob.riding"));
                fileConfig.set("mob.armor.helmet.itemstack", dataConfig.get("armor.helmet.itemStack"));
                fileConfig.set("mob.armor.helmet.dropchance", dataConfig.get("armor.helmet.dropChance"));
                fileConfig.set("mob.armor.chestplate.itemstack", dataConfig.get("armor.chestplate.itemStack"));
                fileConfig.set("mob.armor.chestplate.dropchance", dataConfig.get("armor.chestplate.dropChance"));
                fileConfig.set("mob.armor.leggings.itemstack", dataConfig.get("armor.leggings.itemStack"));
                fileConfig.set("mob.armor.leggings.dropchance", dataConfig.get("armor.leggings.dropChance"));
                fileConfig.set("mob.armor.boots.itemstack", dataConfig.get("armor.boots.itemStack"));
                fileConfig.set("mob.armor.boots.dropchance", dataConfig.get("armor.boots.dropChance"));
                fileConfig.set("mob.armor.weapon.itemstack", dataConfig.get("armor.weapon.itemStack"));
                fileConfig.set("mob.armor.weapon.dropchance", dataConfig.get("armor.weapon.dropChance"));
                fileConfig.set("mob.knockback.up", dataConfig.get("knockback.up"));
                fileConfig.set("mob.knockback.down", dataConfig.get("knockback.down"));
                fileConfig.set("mob.knockback.left", dataConfig.get("knockback.left"));
                fileConfig.set("mob.knockback.right", dataConfig.get("knockback.right"));
                fileConfig.set("mob.knockback.forward", dataConfig.get("knockback.forward"));
                fileConfig.set("mob.knockback.back", dataConfig.get("knockback.back"));
                fileConfig.set("mob.properties.daylightburn", dataConfig.get("properties.dayLightBurn"));
                fileConfig.set("mob.properties.damageable", dataConfig.get("properties.damageAble"));
                fileConfig.set("mob.properties.damaging", dataConfig.get("properties.doesDamage"));
                fileConfig.set("mob.properties.movable", dataConfig.get("properties.moveAble"));
                fileConfig.set("mob.properties.attacking", dataConfig.get("properties.isAttacking"));
                fileConfig.set("mob.properties.classicdrops", dataConfig.get("properties.classicDrops"));
                fileConfig.set("mob.properties.showhealth", dataConfig.get("properties.showingHealth"));
                fileConfig.set("mob.properties.showname", dataConfig.get("properties.showingName"));
                fileConfig.set("mob.special.slimesize", dataConfig.get("special.properties.slimeSize"));
                fileConfig.set("mob.special.villagertype", dataConfig.get("special.properties.villagerType"));
                fileConfig.set("mob.special.villagerprofession", dataConfig.get("special.properties.villagerProfession"));
                fileConfig.set("mob.special.catcolor", dataConfig.get("special.properties.catColor"));
                fileConfig.set("mob.special.skeletonwithermode", dataConfig.get("special.properties.withermode"));
                fileConfig.set("mob.special.catmode", dataConfig.get("special.properties.catmode"));
                fileConfig.set("mob.special.dogmode", dataConfig.get("special.properties.dogmode"));
                fileConfig.set("mob.special.creeperpowered", dataConfig.get("special.properties.powered"));
                fileConfig.set("mob.special.creeperexplosionradius", dataConfig.get("special.properties.explosionradius"));
                fileConfig.set("mob.special.creeperdestroyblocks", dataConfig.get("special.properties.explosion"));
                fileConfig.save(newMobFile);
                BukkitUtilities.u().writeAllLines(new File(newMobFolder, "potioneffects.txt"), BukkitUtilities.u().readAllLines(new File(oldMobFolder, "potioneffects.txt")));
                BukkitUtilities.u().writeAllLines(new File(newMobFolder, "skills.txt"), BukkitUtilities.u().readAllLines(new File(oldMobFolder, "skills.txt")));
                BukkitUtilities.u().saveFolderDeleting(oldMobFolder);
            }
            catch (Exception e) {
                Cardinal.call().sendException(new CardinalException("Converting mobs failed", "Cannot create new mobs", "Check the mob files and remove the invalid one", Priority.MEDIUM));
            }
            ++n2;
        }
        BukkitUtilities.u().saveFolderDeleting(oldMobsFolder);
    }

    private void convertToNewFloorSystem(File oldFloorsFolder) throws Exception {
        YamlConfiguration dataConfig = new YamlConfiguration();
        YamlConfiguration fileConfig = new YamlConfiguration();
        String[] arrstring = oldFloorsFolder.list();
        int n = arrstring.length;
        int n2 = 0;
        while (n2 < n) {
            String name = arrstring[n2];
            try {
                File oldfloorFolder = new File(oldFloorsFolder, name);
                File oldfloorFile = new File(oldfloorFolder, "floor.yml");
                File newfloorFile = new File(this.floorsFolder, String.valueOf(name) + ".yml");
                dataConfig.load(oldfloorFile);
                fileConfig.set("floor.general.id", dataConfig.get("id"));
                fileConfig.set("floor.general.world", dataConfig.get("world"));
                fileConfig.set("floor.spawnpoint.x", dataConfig.get("x"));
                fileConfig.set("floor.spawnpoint.y", dataConfig.get("y"));
                fileConfig.set("floor.spawnpoint.z", dataConfig.get("z"));
                fileConfig.set("floor.spawnpoint.yaw", dataConfig.get("yaw"));
                fileConfig.set("floor.spawnpoint.pitch", dataConfig.get("pitch"));
                fileConfig.set("floor.endpoint.x", dataConfig.get("x1"));
                fileConfig.set("floor.endpoint.y", dataConfig.get("y1"));
                fileConfig.set("floor.endpoint.z", dataConfig.get("z1"));
                fileConfig.set("floor.endpoint.yaw", dataConfig.get("yaw1"));
                fileConfig.set("floor.endpoint.pitch", dataConfig.get("pitch1"));
                fileConfig.set("floor.nextfloorportal.x", dataConfig.get("x3"));
                fileConfig.set("floor.nextfloorportal.y", dataConfig.get("y3"));
                fileConfig.set("floor.nextfloorportal.z", dataConfig.get("z3"));
                fileConfig.set("floor.previousfloorportal.x", dataConfig.get("x2"));
                fileConfig.set("floor.previousfloorportal.y", dataConfig.get("y2"));
                fileConfig.set("floor.previousfloorportal.z", dataConfig.get("z2"));
                fileConfig.save(newfloorFile);
            }
            catch (Exception e) {
                Cardinal.call().sendException(new CardinalException("Converting floors failed", "Cannot create new floors", "Check the floor files and remove the invalid one", Priority.MEDIUM));
            }
            ++n2;
        }
        BukkitUtilities.u().saveFolderDeleting(oldFloorsFolder);
    }
}

