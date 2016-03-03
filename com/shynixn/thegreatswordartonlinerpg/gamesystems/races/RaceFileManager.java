/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  org.bukkit.configuration.file.FileConfiguration
 *  org.bukkit.configuration.file.YamlConfiguration
 *  org.bukkit.plugin.java.JavaPlugin
 */
package com.shynixn.thegreatswordartonlinerpg.gamesystems.races;

import com.shynixn.thegreatswordartonlinerpg.cardinal.Cardinal;
import com.shynixn.thegreatswordartonlinerpg.cardinal.CardinalException;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.races.Race;
import com.shynixn.thegreatswordartonlinerpg.resources.enums.Priority;
import java.io.File;
import java.util.ArrayList;
import libraries.com.shynixn.utilities.BukkitFileManager;
import libraries.com.shynixn.utilities.BukkitLocation;
import libraries.com.shynixn.utilities.BukkitObject;
import libraries.com.shynixn.utilities.BukkitUtilities;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class RaceFileManager
extends BukkitFileManager {
    private File getFolder() {
        return new File(this.getDataFolder(), "resources/races");
    }

    public RaceFileManager(JavaPlugin plugin) {
        super(plugin, "");
    }

    @Override
    public boolean save(BukkitObject item) {
        try {
            Cardinal.getLogger().logHidden("Saving race " + item.getName() + ".");
            Race race = (Race)item;
            File floordata = BukkitUtilities.u().createFile(new File(this.getFolder(), String.valueOf(race.getName()) + ".yml"));
            YamlConfiguration dataconfig = new YamlConfiguration();
            dataconfig.load(floordata);
            this.saveRaceData(race, (FileConfiguration)dataconfig);
            dataconfig.save(floordata);
        }
        catch (Exception e) {
            e.printStackTrace();
            Cardinal.call().sendException(new CardinalException("Saving data from race " + item.getName() + " failed", "Race is not saved", "Make sure the filesystem is correct", Priority.MEDIUM));
            return false;
        }
        return true;
    }

    private void saveRaceData(Race race, FileConfiguration dataconfig) {
        dataconfig.set("race.general.name", (Object)race.getName());
        dataconfig.set("race.general.displayname", (Object)race.getDisplayName());
        if (race.getSpawnPoint() != null) {
            dataconfig.set("race.spawnpoint.world", (Object)race.getSpawnPoint().getWorldName());
            dataconfig.set("race.spawnpoint.x", (Object)race.getSpawnPoint().getX());
            dataconfig.set("race.spawnpoint.y", (Object)race.getSpawnPoint().getY());
            dataconfig.set("race.spawnpoint.z", (Object)race.getSpawnPoint().getZ());
            dataconfig.set("race.spawnpoint.yaw", (Object)race.getSpawnPoint().getYaw());
            dataconfig.set("race.spawnpoint.pitch", (Object)race.getSpawnPoint().getPitch());
        }
    }

    @Override
    public boolean delete(BukkitObject item) {
        try {
            Cardinal.getLogger().logHidden("Deleting race " + item.getName() + ".");
            File file = new File(this.getFolder(), String.valueOf(item.getName()) + ".yml");
            file.delete();
        }
        catch (Exception e) {
            Cardinal.call().sendException(new CardinalException("Deleting data from race " + item.getName() + " failed", "Race is not deleted", "Make sure the filesystem is correct", Priority.MEDIUM));
            return false;
        }
        return true;
    }

    @Override
    public BukkitObject[] load() {
        Cardinal.getLogger().logHeadLine("Loading Races");
        ArrayList<Race> races = new ArrayList<Race>();
        try {
            String[] files = this.getFolder().list();
            this.loadRaces(races, files);
        }
        catch (Exception e) {
            Cardinal.call().sendException(new CardinalException("Loading races from filesystem failed", "Races are missing", "Make sure the filesystem is correct", Priority.MEDIUM));
        }
        Cardinal.getLogger().logHeadLine("Completed");
        return races.toArray(new Race[races.size()]);
    }

    private void loadRaces(ArrayList<Race> races, String[] files) throws Exception {
        int i = 0;
        while (i < files.length) {
            YamlConfiguration file = new YamlConfiguration();
            File racedata = new File(this.getFolder(), files[i]);
            try {
                file.load(racedata);
                Race race = new Race(file.getString("race.general.name"), file.getString("race.general.displayname"));
                race.setSpawnPoint(new BukkitLocation(file.getString("race.spawnpoint.world"), file.getDouble("race.spawnpoint.x"), file.getDouble("race.spawnpoint.y"), file.getDouble("race.spawnpoint.z"), (float)file.getDouble("race.spawnpoint.yaw"), (float)file.getDouble("race.spawnpoint.pitch")));
                races.add(race);
            }
            catch (Exception e) {
                Cardinal.call().sendException(new CardinalException("File " + racedata + " cannot be loaded", "Race is missing", "Delete or fix the file", Priority.MEDIUM));
            }
            ++i;
        }
    }
}

