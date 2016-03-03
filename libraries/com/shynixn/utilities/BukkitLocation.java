/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  org.bukkit.Location
 *  org.bukkit.World
 */
package libraries.com.shynixn.utilities;

import java.io.Serializable;
import libraries.com.shynixn.utilities.BukkitUtilities;
import libraries.com.shynixn.utilities.YamlSaveGeneric;
import org.bukkit.Location;
import org.bukkit.World;

public class BukkitLocation
implements Serializable {
    private static final long serialVersionUID = 1;
    @YamlSaveGeneric
    private String world;
    @YamlSaveGeneric
    private double x;
    @YamlSaveGeneric
    private double y;
    @YamlSaveGeneric
    private double z;
    @YamlSaveGeneric
    private double yaw;
    @YamlSaveGeneric
    private double pitch;

    public BukkitLocation(String worldname, double x, double y, double z, float yaw, float pitch) {
        this.world = worldname;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public BukkitLocation() {
    }

    public BukkitLocation(String worldname, double x, double y, double z, double yaw, double pitch) {
        this(worldname, x, y, z, (float)yaw, (float)pitch);
    }

    public BukkitLocation(String worldname, double x, double y, double z) {
        this(worldname, x, y, z, 0.0f, 0.0f);
    }

    public BukkitLocation(Location location) {
        this(location.getWorld().getName(), location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
    }

    public BukkitLocation addCoordinates(double x, double y, double z) {
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }

    public boolean equals(Object arg0) {
        BukkitLocation location;
        if (arg0 instanceof BukkitLocation && (location = (BukkitLocation)arg0).getBlockX() == this.getBlockX() && location.getBlockY() == this.getBlockY() && location.getBlockZ() == this.getBlockZ()) {
            return true;
        }
        return false;
    }

    public Location getLocation() {
        return new Location(BukkitUtilities.u().getWorldbyName(this.world), this.x, this.y, this.z, (float)this.yaw, (float)this.pitch);
    }

    public double getYaw() {
        return this.yaw;
    }

    public double getPitch() {
        return this.pitch;
    }

    public World getWorld() {
        return BukkitUtilities.u().getWorldbyName(this.world);
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getZ() {
        return this.z;
    }

    public int getBlockX() {
        return (int)this.x;
    }

    public int getBlockY() {
        return (int)this.y;
    }

    public int getBlockZ() {
        return (int)this.z;
    }

    public String getWorldName() {
        return this.world;
    }

    public String toString() {
        return String.valueOf(this.getWorld().getName()) + " " + this.getBlockX() + "x " + this.getBlockY() + "y " + this.getBlockZ() + "z.";
    }

    public int distance(BukkitLocation to) {
        return (int)Math.sqrt(Math.pow(this.x - to.getX(), 2.0) + Math.pow(this.y - to.getY(), 2.0) + Math.pow(this.z - to.getZ(), 2.0));
    }
}

