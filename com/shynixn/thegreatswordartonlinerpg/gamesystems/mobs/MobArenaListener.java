/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.Chunk
 *  org.bukkit.entity.Creeper
 *  org.bukkit.entity.EnderDragon
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.entity.EntityCombustEvent
 *  org.bukkit.event.entity.EntityCreatePortalEvent
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 *  org.bukkit.event.entity.EntityDamageEvent
 *  org.bukkit.event.entity.EntityDamageEvent$DamageCause
 *  org.bukkit.event.entity.EntityDeathEvent
 *  org.bukkit.event.entity.EntityExplodeEvent
 *  org.bukkit.event.entity.EntityTargetEvent
 *  org.bukkit.event.entity.ExplosionPrimeEvent
 *  org.bukkit.event.player.PlayerQuitEvent
 *  org.bukkit.event.world.ChunkUnloadEvent
 *  org.bukkit.plugin.java.JavaPlugin
 */
package com.shynixn.thegreatswordartonlinerpg.gamesystems.mobs;

import com.shynixn.thegreatswordartonlinerpg.cardinal.Cardinal;
import com.shynixn.thegreatswordartonlinerpg.cardinal.CardinalLanguage;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.mobs.Mob;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.mobs.MobArena;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.mobs.MobArenaManager;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.mobs.MobEquipment;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import libraries.com.shynixn.utilities.BukkitChatColor;
import libraries.com.shynixn.utilities.BukkitEvents;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityCreatePortalEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class MobArenaListener
extends BukkitEvents {
    private MobArenaManager arenaManager;

    public MobArenaListener(MobArenaManager arenaManager, JavaPlugin plugin) {
        super(plugin);
        this.arenaManager = arenaManager;
    }

    @EventHandler
    public void noFireDamageEvent(EntityCombustEvent event) {
        if (event.getEntity() instanceof LivingEntity) {
            for (MobArena arena : this.arenaManager.arenas.values()) {
                if (arena.isDayLightburn() || !arena.getMob().isSameEntity((LivingEntity)event.getEntity())) continue;
                event.setCancelled(true);
                event.setDuration(0);
                break;
            }
        }
    }

    @EventHandler
    public void explosionCreeperEvent(ExplosionPrimeEvent event) {
        if (event.getEntity() instanceof Creeper) {
            for (MobArena arena : this.arenaManager.arenas.values()) {
                if (!arena.getMob().isSameEntity((LivingEntity)event.getEntity())) continue;
                event.setRadius((float)arena.getMob().getEquipmentCopy().getExplosionRadius());
                break;
            }
        }
    }

    @EventHandler
    public void explosionCreeperEvent(EntityExplodeEvent event) {
        if (event.getEntity() instanceof Creeper) {
            for (MobArena arena : this.arenaManager.arenas.values()) {
                if (!arena.getMob().isSameEntity((LivingEntity)event.getEntity())) continue;
                if (arena.getMob().getEquipmentCopy().isExplosionDestroy()) break;
                event.blockList().clear();
                break;
            }
        }
    }

    @EventHandler
    public void chunkUnloadEvent(ChunkUnloadEvent chunkEvent) {
        Entity[] arrentity = chunkEvent.getChunk().getEntities();
        int n = arrentity.length;
        int n2 = 0;
        while (n2 < n) {
            Entity entity = arrentity[n2];
            if (entity instanceof LivingEntity) {
                for (MobArena arena : this.arenaManager.arenas.values()) {
                    if (!arena.getMob().isSameEntity((LivingEntity)entity)) continue;
                    arena.getMob().kill();
                    break;
                }
            }
            ++n2;
        }
    }

    @EventHandler
    public void onEntityCreatePortal(EntityCreatePortalEvent event) {
        if (event.getEntity() instanceof EnderDragon) {
            for (MobArena arena : this.arenaManager.arenas.values()) {
                if (!arena.getMob().isSameEntity(event.getEntity())) continue;
                event.setCancelled(true);
                break;
            }
        }
    }

    @EventHandler
    public void onEntityDeathEvent(EntityDeathEvent event) {
        Player player;
        if (event.getEntity() instanceof Player && this.arenaManager.arenas.containsKey((Object)(player = (Player)event.getEntity()))) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + (Object)((Object)BukkitChatColor.YELLOW) + CardinalLanguage.Cardinal.TYPE_SAO_BACK);
        }
        if (event.getEntity() instanceof LivingEntity) {
            for (MobArena arena : this.arenaManager.arenas.values()) {
                if (!arena.getMob().isSameEntity(event.getEntity()) || arena.getMob().getEquipmentCopy().isClassicDrops()) continue;
                event.getDrops().clear();
            }
        }
    }

    @EventHandler
    public void entityTargetEvent(EntityTargetEvent event) {
        if (event.getEntity() instanceof LivingEntity) {
            for (MobArena arena : this.arenaManager.arenas.values()) {
                if (arena.isMoveAble() || !arena.getMob().isSameEntity((LivingEntity)event.getEntity())) continue;
                event.setCancelled(true);
                event.setTarget(null);
                break;
            }
        }
    }

    @EventHandler
    public void noDamageEvent(EntityDamageEvent event) {
        if (event.getEntity() instanceof LivingEntity && !event.isCancelled()) {
            for (MobArena arena : this.arenaManager.arenas.values()) {
                if (!arena.getMob().isSameEntity((LivingEntity)event.getEntity())) continue;
                if (!arena.isDamageAble()) {
                    event.setCancelled(true);
                    break;
                }
                if (event.getCause() == EntityDamageEvent.DamageCause.FALL && (!arena.getMob().getEquipmentCopy().isMoveAble() || arena.getMob().getEquipmentCopy().isFlying())) {
                    event.setCancelled(true);
                    break;
                }
                boolean applyDamage = arena.getMob().damage(event.getDamage());
                if (arena.isShowHealth()) {
                    if (arena.getMob().getHealth() < 0.0) {
                        arena.getPlayer().sendMessage((Object)ChatColor.AQUA + "[MobArenaInfo]" + " " + (Object)((Object)BukkitChatColor.RED) + "Mobhealth: 0.0");
                    } else {
                        arena.getPlayer().sendMessage((Object)ChatColor.AQUA + "[MobArenaInfo]" + " " + (Object)((Object)BukkitChatColor.RED) + "Mobhealth: " + arena.getMob().getHealth());
                    }
                }
                if (applyDamage) {
                    event.setDamage(1.0E7);
                    break;
                }
                event.setDamage(0.0);
                break;
            }
        }
    }

    @EventHandler
    public void noDamageEvent(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof LivingEntity) {
            for (MobArena arena : this.arenaManager.arenas.values()) {
                if (arena.isAttacking() && arena.getMob().isSameEntity((LivingEntity)event.getDamager())) {
                    event.setDamage(arena.getMob().getDamage());
                    break;
                }
                if (arena.isAttacking() || !arena.getMob().isSameEntity((LivingEntity)event.getDamager())) continue;
                event.setCancelled(true);
                break;
            }
        }
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        if (this.arenaManager.arenas.containsKey((Object)event.getPlayer())) {
            this.arenaManager.arenas.get((Object)event.getPlayer()).saveDelete();
            this.arenaManager.arenas.remove((Object)event.getPlayer());
        }
    }
}

