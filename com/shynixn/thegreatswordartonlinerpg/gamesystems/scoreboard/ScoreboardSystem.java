/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.plugin.java.JavaPlugin
 *  org.bukkit.scoreboard.DisplaySlot
 *  org.bukkit.scoreboard.Objective
 *  org.bukkit.scoreboard.Scoreboard
 */
package com.shynixn.thegreatswordartonlinerpg.gamesystems.scoreboard;

import com.shynixn.thegreatswordartonlinerpg.TheGreatSwordArtOnlineRPG;
import com.shynixn.thegreatswordartonlinerpg.cardinal.Cardinal;
import com.shynixn.thegreatswordartonlinerpg.cardinal.CardinalSystem;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.mobs.Mob;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.scoreboard.ScoreboardListener;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.scoreboard.ScoreboardMobAnalytics;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.scoreboard.ScoreboardPartyMember;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.scoreboard.ScoreboardSkill;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.skills.Skill;
import com.shynixn.thegreatswordartonlinerpg.resources.enums.ScoreboardType;
import com.shynixn.thegreatswordartonlinerpg.resources.events.AincradEvent;
import com.shynixn.thegreatswordartonlinerpg.resources.events.cardinal.AincradPlayerLogOutEvent;
import com.shynixn.thegreatswordartonlinerpg.resources.events.cardinal.AincradPlayerLoginEvent;
import com.shynixn.thegreatswordartonlinerpg.resources.events.mobs.AincradRequestMobFromEntity;
import com.shynixn.thegreatswordartonlinerpg.resources.events.scoreboard.AincradSwitchScoreboardEvent;
import java.util.HashMap;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public final class ScoreboardSystem
implements CardinalSystem {
    private ScoreboardMobAnalytics mobAnalysticScoreboard;
    private ScoreboardPartyMember partyMemberScoreboard;
    private ScoreboardSkill skillScoreboard;
    private HashMap<Player, Scoreboard> boards = new HashMap();

    public ScoreboardSystem(TheGreatSwordArtOnlineRPG plugin) {
        this.skillScoreboard = new ScoreboardSkill(this);
        this.mobAnalysticScoreboard = new ScoreboardMobAnalytics(this);
        this.partyMemberScoreboard = new ScoreboardPartyMember();
        new com.shynixn.thegreatswordartonlinerpg.gamesystems.scoreboard.ScoreboardListener(this, plugin);
    }

    @Override
    public void handleCardinalEvent(AincradEvent event) throws Exception {
        if (event instanceof AincradPlayerLoginEvent) {
            this.restorePlayerScoreboard(((AincradPlayerLoginEvent)event).getPlayer());
        } else if (event instanceof AincradPlayerLogOutEvent) {
            this.restorePlayerScoreboard(((AincradPlayerLogOutEvent)event).getPlayer());
        } else if (event instanceof AincradSwitchScoreboardEvent && !event.isCancelled()) {
            this.switchScoreboard(((AincradSwitchScoreboardEvent)event).getPlayer(), ((AincradSwitchScoreboardEvent)event).getScoreboardType());
        }
    }

    private void switchScoreboard(Player player, ScoreboardType scoreboardType) {
        scoreboardType = this.getNextScoreboardType(player, scoreboardType);
        this.restorePlayerScoreboard(player);
        if (scoreboardType == ScoreboardType.MOBS) {
            this.mobAnalysticScoreboard.show(player);
        }
        if (scoreboardType == ScoreboardType.SKILLS) {
            this.skillScoreboard.show(player);
        }
        if (scoreboardType == ScoreboardType.PARTY) {
            this.partyMemberScoreboard.show(player);
        }
    }

    private void restorePlayerScoreboard(Player player) {
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        player.setScoreboard(scoreboard);
        this.boards.put(player, scoreboard);
    }

    protected void updateSkillScoreboard(Player player, Skill skill, int timeLeft) {
        if (this.getActivatedScoreBoardType(player) == ScoreboardType.SKILLS) {
            this.skillScoreboard.update(player, skill, timeLeft);
        }
    }

    protected void updatePartyScoreboard(Player player, int health) {
        this.getActivatedScoreBoardType(player);
    }

    protected void updateMobScoreboard(Player player, LivingEntity entity) {
        if (this.getActivatedScoreBoardType(player) == ScoreboardType.MOBS) {
            AincradRequestMobFromEntity requestMobFromEntity = new AincradRequestMobFromEntity(entity);
            Cardinal.call().notifyMobSystem(requestMobFromEntity);
            if (requestMobFromEntity.getMob() != null) {
                this.mobAnalysticScoreboard.update(player, requestMobFromEntity.getMob());
            }
        }
    }

    protected Scoreboard getScoreBoard(Player player) {
        return this.boards.get((Object)player);
    }

    private ScoreboardType getActivatedScoreBoardType(Player player) {
        try {
            if (this.boards.containsKey((Object)player) && this.boards.get((Object)player).getObjective(DisplaySlot.SIDEBAR).getName().equals(ScoreboardType.PARTY.name())) {
                return ScoreboardType.PARTY;
            }
            if (this.boards.containsKey((Object)player) && this.boards.get((Object)player).getObjective(DisplaySlot.SIDEBAR).getName().equals(ScoreboardType.SKILLS.name())) {
                return ScoreboardType.SKILLS;
            }
            if (this.boards.containsKey((Object)player) && this.boards.get((Object)player).getObjective(DisplaySlot.SIDEBAR).getName().equals(ScoreboardType.MOBS.name())) {
                return ScoreboardType.MOBS;
            }
        }
        catch (Exception var2_2) {
            // empty catch block
        }
        return ScoreboardType.UNKOWN;
    }

    private ScoreboardType getNextScoreboardType(Player player, ScoreboardType scoreboardType) {
        if (scoreboardType == ScoreboardType.NEXT) {
            ScoreboardType activatedScoreboardType = this.getActivatedScoreBoardType(player);
            if (activatedScoreboardType != null && activatedScoreboardType == ScoreboardType.MOBS) {
                return ScoreboardType.PARTY;
            }
            if (activatedScoreboardType != null && activatedScoreboardType == ScoreboardType.PARTY) {
                return ScoreboardType.SKILLS;
            }
            if (activatedScoreboardType != null && activatedScoreboardType == ScoreboardType.SKILLS) {
                return ScoreboardType.MOBS;
            }
            if (activatedScoreboardType == null || activatedScoreboardType == ScoreboardType.UNKOWN) {
                return ScoreboardType.SKILLS;
            }
        }
        return scoreboardType;
    }
}

