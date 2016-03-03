/*
 * Decompiled with CFR 0_110.
 */
package com.shynixn.thegreatswordartonlinerpg.gamesystems.races;

import libraries.com.shynixn.utilities.BukkitLocation;
import libraries.com.shynixn.utilities.BukkitObject;

public class Race
extends BukkitObject {
    private static final long serialVersionUID = 1;
    private BukkitLocation spawnPoint;

    public Race(String name, String displayName) {
        super(name, displayName);
    }

    public BukkitLocation getSpawnPoint() {
        return this.spawnPoint;
    }

    public void setSpawnPoint(BukkitLocation spawnPoint) {
        this.spawnPoint = spawnPoint;
    }
}

