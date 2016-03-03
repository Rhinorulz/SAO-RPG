/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 */
package com.shynixn.thegreatswordartonlinerpg.resources.events.floors;

import com.shynixn.thegreatswordartonlinerpg.resources.events.AincradPlayerEvent;
import org.bukkit.entity.Player;

public class AincradBeatFloorEvent
extends AincradPlayerEvent {
    private int nextFloorId;

    public AincradBeatFloorEvent(Player player, int nextFloorId) {
        super(player);
        this.nextFloorId = nextFloorId;
    }

    public int getNextFloorId() {
        return this.nextFloorId;
    }
}

