/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.entity.Player
 *  org.bukkit.scoreboard.DisplaySlot
 *  org.bukkit.scoreboard.Objective
 *  org.bukkit.scoreboard.Score
 *  org.bukkit.scoreboard.Scoreboard
 */
package com.shynixn.thegreatswordartonlinerpg.gamesystems.scoreboard;

import com.shynixn.thegreatswordartonlinerpg.gamesystems.mobs.Mob;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.mobs.MobEquipment;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.scoreboard.SaoScoreboard;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.scoreboard.ScoreboardSystem;
import com.shynixn.thegreatswordartonlinerpg.resources.enums.ScoreboardType;
import java.util.HashMap;
import libraries.com.shynixn.utilities.BukkitChatColor;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

public final class ScoreboardMobAnalytics
implements SaoScoreboard {
    private ScoreboardSystem displayManager;
    private HashMap<Player, String> latestEntity = new HashMap();

    public ScoreboardMobAnalytics(ScoreboardSystem displayManager) {
        this.displayManager = displayManager;
    }

    @Override
    public void show(Player player) {
        Objective objective = this.displayManager.getScoreBoard(player).registerNewObjective(ScoreboardType.MOBS.name(), "dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName((Object)ChatColor.BLUE + (Object)ChatColor.BOLD + (Object)ChatColor.ITALIC + "Mob");
        player.setScoreboard(this.displayManager.getScoreBoard(player));
    }

    protected void update(Player player, Mob mob) {
        Objective objective = this.displayManager.getScoreBoard(player).getObjective(ScoreboardType.MOBS.name());
        if (this.latestEntity.containsKey((Object)player)) {
            this.displayManager.getScoreBoard(player).resetScores(this.latestEntity.get((Object)player));
        }
        if (mob.getEquipmentCopy().getDisplayName().length() <= 16) {
            objective.getScore(mob.getEquipmentCopy().getDisplayName()).setScore(0);
            this.latestEntity.put(player, mob.getEquipmentCopy().getDisplayName());
        } else {
            objective.getScore((Object)((Object)BukkitChatColor.GRAY) + "Unknown mob").setScore(0);
            this.latestEntity.put(player, (Object)((Object)BukkitChatColor.GRAY) + "Unknown mob");
        }
        objective.getScore((Object)((Object)BukkitChatColor.GREEN) + "Health").setScore((int)mob.getHealth());
        objective.getScore((Object)((Object)BukkitChatColor.RED) + "Damage").setScore((int)mob.getOriginDamage());
    }
}

