/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.Server
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Item
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.block.Action
 *  org.bukkit.event.entity.EntityDamageEvent
 *  org.bukkit.event.entity.EntityDamageEvent$DamageCause
 *  org.bukkit.event.entity.EntityDeathEvent
 *  org.bukkit.event.player.PlayerInteractEvent
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.java.JavaPlugin
 *  org.bukkit.scheduler.BukkitScheduler
 *  org.bukkit.util.Vector
 */
package com.shynixn.thegreatswordartonlinerpg.gamesystems.skills;

import com.shynixn.thegreatswordartonlinerpg.cardinal.Cardinal;
import com.shynixn.thegreatswordartonlinerpg.cardinal.CardinalLanguage;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.skills.Skill;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.skills.SkillArena;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.skills.SkillSystem;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import libraries.com.shynixn.utilities.BukkitAreaAPI;
import libraries.com.shynixn.utilities.BukkitChatColor;
import libraries.com.shynixn.utilities.BukkitEvents;
import libraries.com.shynixn.utilities.BukkitObject;
import libraries.com.shynixn.utilities.BukkitUtilities;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.Vector;

public final class SkillArenaManager
extends BukkitEvents {
    private JavaPlugin plugin;
    private boolean isArenaSchedulerRunning = false;
    protected HashMap<Player, SkillArena> arenas = new HashMap();
    private SkillSystem skillSystem;

    public SkillArenaManager(SkillSystem skillSystem, JavaPlugin plugin) {
        super(plugin);
        this.plugin = plugin;
        this.skillSystem = skillSystem;
        this.startArenaScheduler();
    }

    public void reset() {
        for (SkillArena area : this.arenas.values()) {
            area.delete();
        }
    }

    private void startArenaScheduler() {
        if (!this.isArenaSchedulerRunning) {
            this.isArenaSchedulerRunning = true;
            this.plugin.getServer().getScheduler().scheduleSyncRepeatingTask((Plugin)this.plugin, new Runnable(){

                @Override
                public void run() {
                    for (SkillArena arena : SkillArenaManager.this.arenas.values()) {
                        if (arena.getItem() == null) continue;
                        if (arena.getItem().isDead()) {
                            arena.respawnItem();
                            SkillArenaManager.this.fixSpawn();
                        }
                        if (arena.turn()) {
                            arena.getItem().setVelocity(new Vector(0.0, 0.5, 0.0));
                            continue;
                        }
                        arena.getItem().setVelocity(new Vector(0.0, -0.1, 0.0));
                    }
                }
            }, 0, 25);
        }
    }

    public void fixSpawn() {
        this.plugin.getServer().getScheduler().scheduleSyncDelayedTask((Plugin)this.plugin, new Runnable(){

            @Override
            public void run() {
                for (SkillArena arena : SkillArenaManager.this.arenas.values()) {
                    if (arena.getItem() == null) continue;
                    arena.getItem().teleport(arena.getArea().getCenter().add(0.5, 0.5, 0.5));
                }
            }
        }, 10);
    }

    @EventHandler
    public void activateSkillEvent(PlayerInteractEvent event) {
        Skill skill;
        if ((event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) && this.arenas.containsKey((Object)event.getPlayer()) && (skill = this.getSkillItem(event.getPlayer().getItemInHand())) != null) {
            Cardinal.getSkillExecutor().executeSkill((LivingEntity)event.getPlayer(), skill);
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityDeathEvent(EntityDeathEvent event) {
        Player player;
        if (event.getEntity() instanceof Player && this.arenas.containsKey((Object)(player = (Player)event.getEntity()))) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + (Object)((Object)BukkitChatColor.YELLOW) + CardinalLanguage.Cardinal.TYPE_SAO_BACK);
        }
    }

    @EventHandler
    public void lightningStrikeEvent(EntityDamageEvent event) {
        if (this.arenas.containsKey((Object)event.getEntity()) && event.getCause() == EntityDamageEvent.DamageCause.LIGHTNING) {
            event.setCancelled(true);
        }
    }

    private Skill getSkillItem(ItemStack item) {
        for (BukkitObject object : this.skillSystem.getItems()) {
            Skill skill = (Skill)object;
            if (!BukkitUtilities.u().compareItemNames(item, skill.getDisplayName(), new String[]{(Object)ChatColor.GREEN + "Arena-Skill " + skill.getName()}, null, new int[1])) continue;
            return skill;
        }
        return null;
    }

}

