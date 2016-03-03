/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  org.bukkit.Location
 *  org.bukkit.World
 */
package com.shynixn.thegreatswordartonlinerpg.gamesystems.mobs;

import libraries.com.shynixn.utilities.BukkitLocation;
import libraries.com.shynixn.utilities.BukkitObject;
import org.bukkit.Location;
import org.bukkit.World;

public class MobRespawnpoint
extends BukkitObject {
    private static final long serialVersionUID = 1;
    private BukkitLocation location;
    private int xradius = 0;
    private int yradius = 0;
    private int zradius = 0;
    private int detectionradius = 0;
    private int amount;
    private String mobName;
    private int maxRespawnDelay = 60;
    private int maxDespawnDelay = 30;
    private boolean isEnabled = false;

    public MobRespawnpoint(String name, BukkitLocation location, int xradius, int yradius, int zradius, int detectionradius, int amount, String mobName) {
        super(name, name);
        this.location = location;
        this.xradius = xradius;
        this.yradius = yradius;
        this.zradius = zradius;
        this.detectionradius = detectionradius;
        this.amount = amount;
        this.mobName = mobName;
    }

    public Location getLocation() {
        return new Location(this.location.getWorld(), this.location.getX(), this.location.getY(), this.location.getZ());
    }

    public int getXradius() {
        return this.xradius;
    }

    public int getYradius() {
        return this.yradius;
    }

    public int getZradius() {
        return this.zradius;
    }

    public int getDetectionradius() {
        return this.detectionradius;
    }

    public int getAmount() {
        return this.amount;
    }

    public String getMobName() {
        return this.mobName;
    }

    @Override
    public boolean equals(Object arg0) {
        if (arg0 instanceof MobRespawnpoint) {
            return this.getName().equals(((MobRespawnpoint)arg0).getName());
        }
        return false;
    }

    public String toString() {
        return String.valueOf(this.getName()) + " " + this.location.getWorld().getName() + " " + this.location.getBlockX() + "x " + this.location.getBlockY() + "y " + this.location.getBlockZ() + "z" + " " + this.mobName;
    }

    public boolean isEnabled() {
        return this.isEnabled;
    }

    public void setEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public int getMaxRespawnDelay() {
        return this.maxRespawnDelay;
    }

    public void setMaxRespawnDelay(int maxRespawnDelay) {
        this.maxRespawnDelay = maxRespawnDelay;
    }

    public int getMaxDespawnDelay() {
        return this.maxDespawnDelay;
    }

    public void setMaxDespawnDelay(int maxDespawnDelay) {
        this.maxDespawnDelay = maxDespawnDelay;
    }
}

