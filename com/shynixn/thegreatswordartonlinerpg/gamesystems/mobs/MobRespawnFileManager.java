/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  org.bukkit.Location
 *  org.bukkit.World
 *  org.bukkit.configuration.file.FileConfiguration
 *  org.bukkit.configuration.file.YamlConfiguration
 *  org.bukkit.plugin.java.JavaPlugin
 */
package com.shynixn.thegreatswordartonlinerpg.gamesystems.mobs;

import com.shynixn.thegreatswordartonlinerpg.cardinal.Cardinal;
import com.shynixn.thegreatswordartonlinerpg.cardinal.CardinalException;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.mobs.MobRespawnpoint;
import com.shynixn.thegreatswordartonlinerpg.resources.enums.Priority;
import java.io.File;
import java.util.ArrayList;
import libraries.com.shynixn.utilities.BukkitFileManager;
import libraries.com.shynixn.utilities.BukkitLocation;
import libraries.com.shynixn.utilities.BukkitObject;
import libraries.com.shynixn.utilities.BukkitUtilities;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class MobRespawnFileManager
extends BukkitFileManager {
    private File getFolder() {
        return new File(this.getDataFolder(), "resources/spawnpoints");
    }

    public MobRespawnFileManager(JavaPlugin plugin) {
        super(plugin, "mobspawns");
    }

    @Override
    public boolean save(BukkitObject item) {
        try {
            MobRespawnpoint mobRespawnpoint = (MobRespawnpoint)item;
            File mobdata = BukkitUtilities.u().createFile(new File(this.getFolder(), String.valueOf(item.getName()) + ".yml"));
            YamlConfiguration dataconfig = new YamlConfiguration();
            dataconfig.load(mobdata);
            this.saveMobData(mobRespawnpoint, (FileConfiguration)dataconfig);
            dataconfig.save(mobdata);
        }
        catch (Exception e) {
            Cardinal.call().sendException(new CardinalException("Saving data from spawnpoint " + item.getName() + " failed", "Spawnpoint is not saved", "Make sure the filesystem is correct", Priority.MEDIUM));
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(BukkitObject item) {
        try {
            new File(this.getFolder(), String.valueOf(item.getName()) + ".yml").delete();
            return true;
        }
        catch (Exception e) {
            Cardinal.call().sendException(new CardinalException("Deleting data from spawnpoint " + item.getName() + " failed", "Spawnpoint is not deleted", "Make sure the filesystem is correct", Priority.MEDIUM));
            return false;
        }
    }

    @Override
    public BukkitObject[] load() {
        Cardinal.getLogger().logHeadLine("Loading spawnpoints");
        ArrayList<MobRespawnpoint> points = new ArrayList<MobRespawnpoint>();
        String[] files = this.getFolder().list();
        int i = 0;
        while (i < files.length) {
            File mobpointData = new File(this.getFolder(), files[i]);
            try {
                YamlConfiguration dataconfig = new YamlConfiguration();
                dataconfig.load(mobpointData);
                points.add(this.loadMobData((FileConfiguration)dataconfig));
            }
            catch (Exception e) {
                Cardinal.call().sendException(new CardinalException("File " + mobpointData + " cannot be loaded", "Spawnpoint is missing", "Delete or fix the file", Priority.MEDIUM));
            }
            ++i;
        }
        Cardinal.getLogger().logHeadLine("Completed");
        return points.toArray(new MobRespawnpoint[points.size()]);
    }

    private MobRespawnpoint loadMobData(FileConfiguration dataconfig) {
        MobRespawnpoint mobRespawnpoint = new MobRespawnpoint(dataconfig.getString("spawnpoint.general.name"), new BukkitLocation(dataconfig.getString("spawnpoint.general.world"), dataconfig.getInt("spawnpoint.center.x"), dataconfig.getInt("spawnpoint.center.y"), dataconfig.getInt("spawnpoint.center.z")), dataconfig.getInt("spawnpoint.radius.x"), dataconfig.getInt("spawnpoint.radius.y"), dataconfig.getInt("spawnpoint.radius.z"), dataconfig.getInt("spawnpoint.radius.detection"), dataconfig.getInt("spawnpoint.mob.amount"), dataconfig.getString("spawnpoint.mob.name"));
        mobRespawnpoint.setEnabled(dataconfig.getBoolean("spawnpoint.general.enabled"));
        mobRespawnpoint.setMaxRespawnDelay(dataconfig.getInt("spawnpoint.delays.maxrespawn"));
        mobRespawnpoint.setMaxDespawnDelay(dataconfig.getInt("spawnpoint.delays.maxdespawn"));
        return mobRespawnpoint;
    }

    private void saveMobData(MobRespawnpoint point, FileConfiguration dataconfig) {
        dataconfig.set("spawnpoint.general.name", (Object)point.getName());
        dataconfig.set("spawnpoint.general.enabled", (Object)point.isEnabled());
        dataconfig.set("spawnpoint.general.world", (Object)point.getLocation().getWorld().getName());
        dataconfig.set("spawnpoint.mob.name", (Object)point.getMobName());
        dataconfig.set("spawnpoint.mob.amount", (Object)point.getAmount());
        dataconfig.set("spawnpoint.delays.maxrespawn", (Object)point.getMaxRespawnDelay());
        dataconfig.set("spawnpoint.delays.maxdespawn", (Object)point.getMaxDespawnDelay());
        dataconfig.set("spawnpoint.center.x", (Object)point.getLocation().getBlockX());
        dataconfig.set("spawnpoint.center.y", (Object)point.getLocation().getBlockY());
        dataconfig.set("spawnpoint.center.z", (Object)point.getLocation().getBlockZ());
        dataconfig.set("spawnpoint.radius.x", (Object)point.getXradius());
        dataconfig.set("spawnpoint.radius.y", (Object)point.getYradius());
        dataconfig.set("spawnpoint.radius.z", (Object)point.getZradius());
        dataconfig.set("spawnpoint.radius.detection", (Object)point.getDetectionradius());
    }
}

