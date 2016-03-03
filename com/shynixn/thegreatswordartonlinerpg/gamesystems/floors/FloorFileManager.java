/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  org.bukkit.World
 *  org.bukkit.configuration.file.FileConfiguration
 *  org.bukkit.configuration.file.YamlConfiguration
 *  org.bukkit.plugin.java.JavaPlugin
 */
package com.shynixn.thegreatswordartonlinerpg.gamesystems.floors;

import com.shynixn.thegreatswordartonlinerpg.cardinal.Cardinal;
import com.shynixn.thegreatswordartonlinerpg.cardinal.CardinalException;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.floors.Floor;
import com.shynixn.thegreatswordartonlinerpg.resources.enums.Priority;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import libraries.com.shynixn.utilities.BukkitFileManager;
import libraries.com.shynixn.utilities.BukkitLocation;
import libraries.com.shynixn.utilities.BukkitObject;
import libraries.com.shynixn.utilities.BukkitUtilities;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class FloorFileManager
extends BukkitFileManager {
    private File getFolder() {
        return new File(this.getDataFolder(), "resources/floors");
    }

    public FloorFileManager(JavaPlugin plugin) {
        super(plugin, "");
    }

    @Override
    public boolean save(BukkitObject item) {
        try {
            Cardinal.getLogger().logHidden("Saving floor " + item.getName() + ".");
            Floor floor = (Floor)item;
            File floordata = BukkitUtilities.u().createFile(new File(this.getFolder(), String.valueOf(floor.getFloorId()) + ".yml"));
            YamlConfiguration dataconfig = new YamlConfiguration();
            dataconfig.load(floordata);
            this.saveFloorData(floor, (FileConfiguration)dataconfig);
            dataconfig.save(floordata);
        }
        catch (Exception e) {
            Cardinal.call().sendException(new CardinalException("Saving data from floor " + item.getName() + " failed", "Floor is not saved", "Make sure the filesystem is correct", Priority.MEDIUM));
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(BukkitObject item) {
        try {
            Cardinal.getLogger().logHidden("Deleting floor " + item.getName() + ".");
            Floor floor = (Floor)item;
            File file = new File(this.getFolder(), String.valueOf(floor.getFloorId()) + ".yml");
            file.delete();
        }
        catch (Exception e) {
            Cardinal.call().sendException(new CardinalException("Deleting data from floor " + item.getName() + " failed", "Floor is not deleted", "Make sure the filesystem is correct", Priority.MEDIUM));
            return false;
        }
        return true;
    }

    @Override
    public BukkitObject[] load() {
        Cardinal.getLogger().logHeadLine("Loading floors");
        ArrayList<Floor> floors = new ArrayList<Floor>();
        try {
            String[] files = this.getFolder().list();
            this.loadFloors(floors, files);
            this.saveScan(floors);
        }
        catch (Exception e) {
            Cardinal.call().sendException(new CardinalException("Loading floors from filesystem failed", "Floors are missing", "Make sure the filesystem is correct", Priority.MEDIUM));
        }
        Cardinal.getLogger().logHeadLine("Completed");
        return floors.toArray(new Floor[floors.size()]);
    }

    private void loadFloors(ArrayList<Floor> floors, String[] files) throws Exception {
        int i = 0;
        while (i < files.length) {
            YamlConfiguration file = new YamlConfiguration();
            File floordata = new File(this.getFolder(), files[i]);
            try {
                file.load(floordata);
                Floor floor = new Floor(new BukkitLocation(file.getString("floor.general.world"), file.getDouble("floor.spawnpoint.x"), file.getDouble("floor.spawnpoint.y"), file.getDouble("floor.spawnpoint.z"), (float)file.getDouble("floor.spawnpoint.yaw"), (float)file.getDouble("floor.spawnpoint.pitch")), file.getInt("floor.general.id"));
                floor.setFinishPoint(new BukkitLocation(file.getString("floor.general.world"), file.getDouble("floor.endpoint.x"), file.getDouble("floor.endpoint.y"), file.getDouble("floor.endpoint.z"), (float)file.getDouble("floor.endpoint.yaw"), (float)file.getDouble("floor.endpoint.pitch")));
                floor.setPreviousPortal(new BukkitLocation(file.getString("floor.general.world"), file.getDouble("floor.previousfloorportal.x"), file.getDouble("floor.previousfloorportal.y"), file.getDouble("floor.previousfloorportal.z")));
                floor.setNextPortal(new BukkitLocation(file.getString("floor.general.world"), file.getDouble("floor.nextfloorportal.x"), file.getDouble("floor.nextfloorportal.y"), file.getDouble("floor.nextfloorportal.z")));
                floor.setBossName(file.getString("floor.general.boss"));
                floors.add(floor);
            }
            catch (Exception e) {
                Cardinal.call().sendException(new CardinalException("File " + floordata + " cannot be loaded", "Floor is missing", "Delete or fix the file", Priority.MEDIUM));
            }
            ++i;
        }
    }

    private void saveScan(List<Floor> floors) {
        for (Floor floor : floors) {
            if (floor != null && floor.getName() != null && floor.getFinishPoint() != null && floor.getFloorId() >= 0 && floor.getNextPortal() != null && floor.getPreviousPortal() != null && floor.getSpawnPoint() != null) continue;
            Cardinal.call().sendException(new CardinalException("Scanning loaded floor files has reported an error", "Floor has invalid data", "Search for the invalid floor", Priority.MEDIUM));
        }
    }

    private void saveFloorData(Floor floor, FileConfiguration dataconfig) {
        dataconfig.set("floor.general.id", (Object)floor.getFloorId());
        dataconfig.set("floor.general.world", (Object)floor.getSpawnPoint().getWorld().getName());
        dataconfig.set("floor.general.boss", (Object)floor.getBossName());
        dataconfig.set("floor.spawnpoint.x", (Object)floor.getSpawnPoint().getX());
        dataconfig.set("floor.spawnpoint.y", (Object)floor.getSpawnPoint().getY());
        dataconfig.set("floor.spawnpoint.z", (Object)floor.getSpawnPoint().getZ());
        dataconfig.set("floor.spawnpoint.yaw", (Object)floor.getSpawnPoint().getYaw());
        dataconfig.set("floor.spawnpoint.pitch", (Object)floor.getSpawnPoint().getPitch());
        if (floor.getFinishPoint() != null) {
            dataconfig.set("floor.endpoint.x", (Object)floor.getFinishPoint().getX());
            dataconfig.set("floor.endpoint.y", (Object)floor.getFinishPoint().getY());
            dataconfig.set("floor.endpoint.z", (Object)floor.getFinishPoint().getZ());
            dataconfig.set("floor.endpoint.yaw", (Object)floor.getFinishPoint().getYaw());
            dataconfig.set("floor.endpoint.pitch", (Object)floor.getFinishPoint().getPitch());
        }
        if (floor.getPreviousPortal() != null) {
            dataconfig.set("floor.previousfloorportal.x", (Object)floor.getPreviousPortal().getX());
            dataconfig.set("floor.previousfloorportal.y", (Object)floor.getPreviousPortal().getY());
            dataconfig.set("floor.previousfloorportal.z", (Object)floor.getPreviousPortal().getZ());
        }
        if (floor.getNextPortal() != null) {
            dataconfig.set("floor.nextfloorportal.x", (Object)floor.getNextPortal().getX());
            dataconfig.set("floor.nextfloorportal.y", (Object)floor.getNextPortal().getY());
            dataconfig.set("floor.nextfloorportal.z", (Object)floor.getNextPortal().getZ());
        }
    }
}

