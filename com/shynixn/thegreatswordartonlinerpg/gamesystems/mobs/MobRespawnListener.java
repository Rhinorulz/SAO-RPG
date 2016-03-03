/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  org.bukkit.Chunk
 *  org.bukkit.entity.Creeper
 *  org.bukkit.entity.EnderDragon
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Projectile
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.block.EntityBlockFormEvent
 *  org.bukkit.event.entity.EntityCombustEvent
 *  org.bukkit.event.entity.EntityCreatePortalEvent
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 *  org.bukkit.event.entity.EntityDamageEvent
 *  org.bukkit.event.entity.EntityDamageEvent$DamageCause
 *  org.bukkit.event.entity.EntityDeathEvent
 *  org.bukkit.event.entity.EntityExplodeEvent
 *  org.bukkit.event.entity.EntityRegainHealthEvent
 *  org.bukkit.event.entity.EntityTargetEvent
 *  org.bukkit.event.entity.ExplosionPrimeEvent
 *  org.bukkit.event.entity.PlayerDeathEvent
 *  org.bukkit.event.world.ChunkUnloadEvent
 *  org.bukkit.plugin.java.JavaPlugin
 */
package com.shynixn.thegreatswordartonlinerpg.gamesystems.mobs;

import com.shynixn.thegreatswordartonlinerpg.gamesystems.mobs.Mob;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.mobs.MobEquipment;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.mobs.MobRespawnManager;
import com.shynixn.thegreatswordartonlinerpg.resources.nms.MobMoves;
import java.util.List;
import libraries.com.shynixn.utilities.BukkitEvents;
import org.bukkit.Chunk;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.EntityBlockFormEvent;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityCreatePortalEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class MobRespawnListener
extends BukkitEvents {
    private MobRespawnManager manager;

    public MobRespawnListener(MobRespawnManager manager, JavaPlugin plugin) {
        super(plugin);
        this.manager = manager;
    }

    @EventHandler
    public void damageEventToMobDamageHandler(EntityDamageEvent event) {
        if (event.getEntity() instanceof LivingEntity && this.manager.getMobFromEntity((LivingEntity)event.getEntity()) != null && !event.isCancelled()) {
            boolean applyDeathDamage = false;
            Mob mob = this.manager.getMobFromEntity((LivingEntity)event.getEntity());
            if (event.getCause() == EntityDamageEvent.DamageCause.FALL && (!mob.getEquipmentCopy().isMoveAble() || mob.getEquipmentCopy().isFlying())) {
                event.setCancelled(true);
            } else {
                if (mob.getEquipmentCopy().isDamageAble()) {
                    applyDeathDamage = mob.damage(event.getDamage());
                }
                if (applyDeathDamage) {
                    event.setDamage(1.0E7);
                } else {
                    event.setDamage(0.0);
                }
            }
        }
    }

    @EventHandler
    public void explosionCreeperEvent(ExplosionPrimeEvent event) {
        if (event.getEntity() instanceof Creeper && this.manager.getMobFromEntity((LivingEntity)event.getEntity()) != null) {
            Mob mob = this.manager.getMobFromEntity((LivingEntity)event.getEntity());
            event.setRadius((float)mob.getEquipmentCopy().getExplosionRadius());
        }
    }

    @EventHandler
    public void explosionCreeperEvent(EntityExplodeEvent event) {
        Mob mob;
        if (event.getEntity() instanceof Creeper && this.manager.getMobFromEntity((LivingEntity)event.getEntity()) != null && !(mob = this.manager.getMobFromEntity((LivingEntity)event.getEntity())).getEquipmentCopy().isExplosionDestroy()) {
            event.blockList().clear();
        }
    }

    @EventHandler
    public void regainhealthEvent(EntityRegainHealthEvent event) {
        if (event.getEntity() instanceof LivingEntity && this.manager.getMobFromEntity((LivingEntity)event.getEntity()) != null) {
            Mob mob = this.manager.getMobFromEntity((LivingEntity)event.getEntity());
            mob.health(event.getAmount());
        }
    }

    @EventHandler
    public void chunkUnloadEvent(ChunkUnloadEvent event) {
        Entity[] arrentity = event.getChunk().getEntities();
        int n = arrentity.length;
        int n2 = 0;
        while (n2 < n) {
            Entity entity = arrentity[n2];
            if (entity instanceof LivingEntity && this.manager.getMobFromEntity((LivingEntity)entity) != null) {
                this.manager.getMobFromEntity((LivingEntity)entity).kill();
            }
            ++n2;
        }
    }

    @EventHandler
    public void onEntityCreatePortal(EntityCreatePortalEvent event) {
        if (event.getEntity() instanceof EnderDragon && this.manager.getMobFromEntity(event.getEntity()) != null) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void fireDamageEvent(EntityCombustEvent event) {
        Mob mob;
        if (event.getEntity() instanceof LivingEntity && this.manager.getMobFromEntity((LivingEntity)event.getEntity()) != null && !(mob = this.manager.getMobFromEntity((LivingEntity)event.getEntity())).getEquipmentCopy().isDayLightBurn() && mob.isSameEntity((LivingEntity)event.getEntity())) {
            event.setCancelled(true);
            event.setDuration(0);
        }
    }

    @EventHandler
    public void entityDeathEventAvoidDrops(EntityDeathEvent event) {
        Mob mob;
        if (event.getEntity() instanceof LivingEntity && this.manager.getMobFromEntity(event.getEntity()) != null && !(mob = this.manager.getMobFromEntity(event.getEntity())).getEquipmentCopy().isClassicDrops() && mob.isSameEntity(event.getEntity())) {
            event.getDrops().clear();
        }
    }

    @EventHandler
    public void entityTargetEvent(EntityTargetEvent event) {
        Mob mob;
        if (event.getEntity() instanceof LivingEntity && this.manager.getMobFromEntity((LivingEntity)event.getEntity()) != null && !(mob = this.manager.getMobFromEntity((LivingEntity)event.getEntity())).getEquipmentCopy().isAttacking() && mob.isSameEntity((LivingEntity)event.getEntity())) {
            event.setTarget(null);
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void entityDamageEventDoesDamage(EntityDamageByEntityEvent event) {
        LivingEntity damager = null;
        if (event.getCause() == EntityDamageEvent.DamageCause.PROJECTILE && ((Projectile)event.getDamager()).getShooter() instanceof LivingEntity) {
            damager = ((Projectile)event.getDamager()).getShooter();
        } else if (event.getDamager() instanceof LivingEntity) {
            damager = (LivingEntity)event.getDamager();
        }
        if (damager != null && this.manager.getMobFromEntity(damager) != null) {
            Mob mob = this.manager.getMobFromEntity(damager);
            if (!mob.getEquipmentCopy().isAttacking()) {
                event.setCancelled(true);
            } else {
                if (event.getCause() == EntityDamageEvent.DamageCause.PROJECTILE) {
                    MobMoves.kickEntity((Entity)damager, event.getEntity(), mob.getEquipmentCopy());
                }
                event.setDamage(mob.getDamage());
            }
        }
    }

    @EventHandler
    public void snow(EntityBlockFormEvent event) {
        if (event.getEntity() instanceof LivingEntity && this.manager.getMobFromEntity((LivingEntity)event.getEntity()) != null && this.manager.getMobFromEntity((LivingEntity)event.getEntity()) != null) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void entityDeahtEvent(PlayerDeathEvent event) {
        if (event.getDeathMessage().contains("\u258d")) {
            event.setDeathMessage("");
        }
    }
}

