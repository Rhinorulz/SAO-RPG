/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 */
package com.shynixn.thegreatswordartonlinerpg.resources.events.floors;

import com.shynixn.thegreatswordartonlinerpg.resources.events.AincradPlayerEvent;
import org.bukkit.entity.Player;

public final class AincradBeatBossEvent
extends AincradPlayerEvent {
    private String mobName;

    public AincradBeatBossEvent(Player player, String mobName) {
        super(player);
        this.mobName = mobName;
    }

    public String getMobName() {
        return this.mobName;
    }
}

