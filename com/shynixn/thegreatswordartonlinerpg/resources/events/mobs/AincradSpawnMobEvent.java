/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  org.bukkit.Location
 */
package com.shynixn.thegreatswordartonlinerpg.resources.events.mobs;

import com.shynixn.thegreatswordartonlinerpg.gamesystems.mobs.Mob;
import com.shynixn.thegreatswordartonlinerpg.resources.events.AincradLocationEvent;
import org.bukkit.Location;

public class AincradSpawnMobEvent
extends AincradLocationEvent {
    private Mob mob;

    public AincradSpawnMobEvent(Mob mob, Location location) {
        super(location);
        if (mob == null) {
            throw new IllegalArgumentException("Mob cannot be null!");
        }
        this.mob = mob;
    }

    public Mob getMob() {
        return this.mob;
    }
}

