/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 *  org.bukkit.event.entity.EntityDeathEvent
 *  org.bukkit.event.entity.EntityRegainHealthEvent
 *  org.bukkit.event.player.PlayerRespawnEvent
 *  org.bukkit.plugin.java.JavaPlugin
 */
package com.shynixn.thegreatswordartonlinerpg.gamesystems.scoreboard;

import com.shynixn.thegreatswordartonlinerpg.cardinal.Cardinal;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.scoreboard.ScoreboardSystem;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.skills.Skill;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.skills.SkillExecutor;
import libraries.com.shynixn.utilities.BukkitEvents;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class ScoreboardListener
extends BukkitEvents
implements SkillExecutor.SkillHandler {
    private ScoreboardSystem scoreboardSystem;

    public ScoreboardListener(ScoreboardSystem scoreboardSystem, JavaPlugin plugin) {
        super(plugin);
        this.scoreboardSystem = scoreboardSystem;
        SkillExecutor.getInstance(plugin).registerSkillHandler(this);
    }

    @EventHandler
    public void updateMobScoreboardOnEntityDamaged(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player && event.getDamager() instanceof LivingEntity && Cardinal.getGenericSystem().isValidAction((Player)event.getEntity())) {
            this.scoreboardSystem.updateMobScoreboard((Player)event.getEntity(), (LivingEntity)event.getDamager());
        }
    }

    @EventHandler
    public void updateMobScoreboardOnEntityDamager(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player && event.getEntity() instanceof LivingEntity && Cardinal.getGenericSystem().isValidAction((Player)event.getDamager())) {
            this.scoreboardSystem.updateMobScoreboard((Player)event.getDamager(), (LivingEntity)event.getEntity());
        }
    }

    @EventHandler
    public void updatePartyScoreboardOnDamageEvent(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player && Cardinal.getGenericSystem().isValidAction((Player)event.getEntity())) {
            this.scoreboardSystem.updatePartyScoreboard((Player)event.getEntity(), (int)((Player)event.getEntity()).getHealth() - (int)event.getDamage());
        }
    }

    @EventHandler
    public void updatePartyScoreboardOnRegainEvent(EntityRegainHealthEvent event) {
        if (event.getEntity() instanceof Player && Cardinal.getGenericSystem().isValidAction((Player)event.getEntity())) {
            this.scoreboardSystem.updatePartyScoreboard((Player)event.getEntity(), (int)((Player)event.getEntity()).getHealth() + (int)event.getAmount());
        }
    }

    @EventHandler
    public void updatePlayerScoreboardOnDeathEvent(EntityDeathEvent event) {
        if (event.getEntity() instanceof Player && Cardinal.getGenericSystem().isValidAction((Player)event.getEntity())) {
            this.scoreboardSystem.updatePartyScoreboard((Player)event.getEntity(), -300);
        }
    }

    @EventHandler
    public void updatePartyScoreboardOnRespawnEvent(PlayerRespawnEvent event) {
        if (Cardinal.getGenericSystem().isValidAction(event.getPlayer())) {
            this.scoreboardSystem.updatePartyScoreboard(event.getPlayer(), 20);
        }
    }

    @Override
    public void skillEffectEvent(LivingEntity entity, Skill skill, int timeLeft) {
        if (entity instanceof Player && Cardinal.getGenericSystem().isValidAction((Player)entity)) {
            this.scoreboardSystem.updateSkillScoreboard((Player)entity, skill, timeLeft);
        }
    }

    @Override
    public void skillDisabledEvent(LivingEntity entity, Skill skill) {
    }

    @Override
    public void skillLoadEvent(LivingEntity entity, Skill skill) {
    }

    @Override
    public void skillActivatedEvent(LivingEntity entity, Skill skill) {
    }

    @Override
    public void skillDeactivatedEvent(LivingEntity entity, Skill skill) {
        if (entity instanceof Player && Cardinal.getGenericSystem().isValidAction((Player)entity)) {
            this.scoreboardSystem.updateSkillScoreboard((Player)entity, skill, 0);
        }
    }

    @Override
    public void skillCooldownEvent(LivingEntity entity, Skill skill) {
    }

    @Override
    public void skillWrongTypeEvent(LivingEntity entity, Skill skill) {
    }
}

