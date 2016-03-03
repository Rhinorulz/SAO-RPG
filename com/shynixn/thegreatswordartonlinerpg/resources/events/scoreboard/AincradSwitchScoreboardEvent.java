/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 */
package com.shynixn.thegreatswordartonlinerpg.resources.events.scoreboard;

import com.shynixn.thegreatswordartonlinerpg.resources.enums.ScoreboardType;
import com.shynixn.thegreatswordartonlinerpg.resources.events.AincradPlayerEvent;
import org.bukkit.entity.Player;

public class AincradSwitchScoreboardEvent
extends AincradPlayerEvent {
    private ScoreboardType scoreboardType;

    public AincradSwitchScoreboardEvent(Player player, ScoreboardType scoreboardType) {
        super(player);
        this.scoreboardType = scoreboardType;
    }

    public ScoreboardType getScoreboardType() {
        return this.scoreboardType;
    }
}

