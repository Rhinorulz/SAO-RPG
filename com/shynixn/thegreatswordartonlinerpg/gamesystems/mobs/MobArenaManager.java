/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.Server
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.java.JavaPlugin
 *  org.bukkit.scheduler.BukkitScheduler
 */
package com.shynixn.thegreatswordartonlinerpg.gamesystems.mobs;

import com.shynixn.thegreatswordartonlinerpg.cardinal.Cardinal;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.mobs.Mob;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.mobs.MobArena;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.mobs.MobArenaListener;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.mobs.MobEquipment;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.skills.Skill;
import com.shynixn.thegreatswordartonlinerpg.resources.events.AincradEvent;
import com.shynixn.thegreatswordartonlinerpg.resources.events.skill.AincradRequestSkillEvent;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;
import libraries.com.shynixn.utilities.BukkitAreaAPI;
import libraries.com.shynixn.utilities.BukkitUtilities;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public final class MobArenaManager {
    private JavaPlugin plugin;
    private boolean isSkillSchedulerRunning = false;
    private boolean isArenaSchedulerRunning = false;
    protected HashMap<Player, MobArena> arenas = new HashMap();

    public MobArenaManager(JavaPlugin plugin) {
        this.plugin = plugin;
        new com.shynixn.thegreatswordartonlinerpg.gamesystems.mobs.MobArenaListener(this, plugin);
        this.startArenaScheduler();
        this.startArenaSkillScheduler();
    }

    public void reset() {
        for (MobArena area : this.arenas.values()) {
            area.delete();
        }
    }

    private void startArenaSkillScheduler() {
        if (!this.isSkillSchedulerRunning) {
            this.isSkillSchedulerRunning = true;
            this.plugin.getServer().getScheduler().scheduleSyncRepeatingTask((Plugin)this.plugin, new Runnable(){

                @Override
                public void run() {
                    for (MobArena mobArena : MobArenaManager.this.arenas.values()) {
                        for (String s : mobArena.getMob().getEquipmentCopy().getSkillsAndHealth().keySet()) {
                            AincradRequestSkillEvent requestSkillEvent = new AincradRequestSkillEvent(s);
                            Cardinal.call().notifySkillSystem(requestSkillEvent);
                            if (requestSkillEvent.getSkill() == null) {
                                BukkitUtilities.u().sendColoredConsoleMessage("Warning! Mob " + mobArena.getMob().getName() + " tries to use an unknown skill.", ChatColor.RED, Cardinal.PREFIX_CONSOLE);
                                continue;
                            }
                            if (mobArena.getMob().getHealth() > mobArena.getMob().getEquipmentCopy().getSkillsAndHealth().get(s)) continue;
                            Cardinal.getSkillExecutor().executeSkill(mobArena.getMob().getEntity(), requestSkillEvent.getSkill());
                        }
                    }
                }
            }, 0, 20);
        }
    }

    private void startArenaScheduler() {
        if (!this.isArenaSchedulerRunning) {
            this.isArenaSchedulerRunning = true;
            this.plugin.getServer().getScheduler().scheduleSyncRepeatingTask((Plugin)this.plugin, new Runnable(){

                @Override
                public void run() {
                    for (MobArena mobArena : MobArenaManager.this.arenas.values()) {
                        if (mobArena.getMob().isDead()) {
                            mobArena.getMob().respawn(mobArena.getArea().getCenter());
                        }
                        if (mobArena.isMoveAble()) continue;
                        mobArena.getMob().teleport(mobArena.getArea().getCenter().add(0.0, -3.0, 0.0));
                    }
                }
            }, 0, 5);
        }
    }

}

