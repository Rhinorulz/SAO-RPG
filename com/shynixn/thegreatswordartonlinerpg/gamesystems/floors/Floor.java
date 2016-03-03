/*
 * Decompiled with CFR 0_110.
 */
package com.shynixn.thegreatswordartonlinerpg.gamesystems.floors;

import libraries.com.shynixn.utilities.BukkitLocation;
import libraries.com.shynixn.utilities.BukkitObject;

public final class Floor
extends BukkitObject {
    private static final long serialVersionUID = 1;
    private BukkitLocation spawnpoint;
    private BukkitLocation finishpoint;
    private BukkitLocation previouspoint;
    private BukkitLocation nextpoint;
    private int floorid;
    private String bossName = "";

    public Floor(BukkitLocation spawnpoint, int floorid) {
        super(String.valueOf(floorid), String.valueOf(floorid));
        this.spawnpoint = spawnpoint;
        this.floorid = floorid;
    }

    public Floor(String name, String displayName, BukkitLocation spawnpoint, BukkitLocation finishpoint, BukkitLocation previouspoint, BukkitLocation nextpoint, int floorid, String bossName) {
        super(name, displayName);
        this.spawnpoint = spawnpoint;
        this.finishpoint = finishpoint;
        this.previouspoint = previouspoint;
        this.nextpoint = nextpoint;
        this.floorid = floorid;
        this.bossName = bossName;
    }

    public Floor clone() {
        return new Floor(this.getName(), this.getDisplayName(), this.spawnpoint, this.finishpoint, this.previouspoint, this.nextpoint, this.floorid, this.bossName);
    }

    public void setFinishPoint(BukkitLocation loc) {
        this.finishpoint = loc;
    }

    public BukkitLocation getFinishPoint() {
        return this.finishpoint;
    }

    public int getFloorId() {
        return this.floorid;
    }

    public BukkitLocation getPreviousPortal() {
        return this.previouspoint;
    }

    public void setPreviousPortal(BukkitLocation location) {
        this.previouspoint = location;
    }

    public BukkitLocation getNextPortal() {
        return this.nextpoint;
    }

    public void setNextPortal(BukkitLocation location) {
        this.nextpoint = location;
    }

    public BukkitLocation getSpawnPoint() {
        return this.spawnpoint;
    }

    public String getBossName() {
        return this.bossName;
    }

    public void setBossName(String bossName) {
        this.bossName = bossName;
    }
}

