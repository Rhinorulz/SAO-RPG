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

import com.shynixn.thegreatswordartonlinerpg.cardinal.Cardinal;
import com.shynixn.thegreatswordartonlinerpg.cardinal.CardinalException;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.scoreboard.SaoScoreboard;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.scoreboard.ScoreboardSystem;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.skills.Skill;
import com.shynixn.thegreatswordartonlinerpg.resources.enums.Priority;
import com.shynixn.thegreatswordartonlinerpg.resources.enums.ScoreboardType;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

public final class ScoreboardSkill
implements SaoScoreboard {
    private ScoreboardSystem displayManager;

    public ScoreboardSkill(ScoreboardSystem displayManager) {
        this.displayManager = displayManager;
    }

    @Override
    public void show(Player player) {
        Objective objective = this.displayManager.getScoreBoard(player).registerNewObjective(ScoreboardType.SKILLS.name(), "dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName((Object)ChatColor.AQUA + (Object)ChatColor.BOLD + (Object)ChatColor.ITALIC + "Skills");
        player.setScoreboard(this.displayManager.getScoreBoard(player));
    }

    protected void update(Player player, Skill skill, int runningTime) {
        if (skill.getDisplayName().length() <= 16) {
            Objective objective = this.displayManager.getScoreBoard(player).getObjective(ScoreboardType.SKILLS.name());
            Score score = objective.getScore(skill.getDisplayName());
            if (runningTime > 0) {
                score.setScore(runningTime);
            } else {
                score.setScore(0);
            }
        } else {
            Cardinal.call().sendException(new CardinalException("Scoreboard can break", "Player can get banned from craftbukkit", "Make the skillname shorter", Priority.MEDIUM));
        }
    }
}

