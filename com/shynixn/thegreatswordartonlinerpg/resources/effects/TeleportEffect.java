/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.Location
 *  org.bukkit.Server
 *  org.bukkit.entity.Player
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.java.JavaPlugin
 *  org.bukkit.scheduler.BukkitScheduler
 */
package com.shynixn.thegreatswordartonlinerpg.resources.effects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public final class TeleportEffect {
    private static TeleportEffect instance;
    private JavaPlugin plugin;
    private HashMap<Player, Integer> teleportingPlayers = new HashMap();
    private HashMap<Player, Integer> countdowns = new HashMap();
    private List<ITeleport> teleportinghandlers = new ArrayList<ITeleport>();

    private TeleportEffect(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public static TeleportEffect getInstance(JavaPlugin plugin) {
        if (instance == null) {
            instance = new TeleportEffect(plugin);
        }
        return instance;
    }

    public void register(ITeleport iTeleport) {
        this.teleportinghandlers.add(iTeleport);
    }

    public void playTeleportEffect(final Player player, final Location location, int duration) {
        if (!this.teleportingPlayers.containsKey((Object)player)) {
            this.countdowns.put(player, duration);
            this.teleportingPlayers.put(player, this.plugin.getServer().getScheduler().scheduleSyncRepeatingTask((Plugin)this.plugin, new Runnable(){

                @Override
                public void run() {
                    if (player.isOnline()) {
                        if ((Integer)TeleportEffect.this.countdowns.get((Object)player) > 0) {
                            TeleportEffect.this.countdowns.put(player, (Integer)TeleportEffect.this.countdowns.get((Object)player) - 1);
                            for (ITeleport iTeleport : TeleportEffect.this.teleportinghandlers) {
                                iTeleport.tickTeleportCountDownPlayer(player, (Integer)TeleportEffect.this.countdowns.get((Object)player));
                            }
                        } else {
                            for (ITeleport iTeleport : TeleportEffect.this.teleportinghandlers) {
                                iTeleport.teleportPlayer(player, location);
                            }
                            TeleportEffect.this.clearSchedulerRessources(player);
                        }
                    } else {
                        TeleportEffect.this.clearSchedulerRessources(player);
                    }
                }
            }, 0, 20));
        }
    }

    private void clearSchedulerRessources(Player player) {
        this.plugin.getServer().getScheduler().cancelTask(this.teleportingPlayers.get((Object)player).intValue());
        Bukkit.getServer().getScheduler().cancelTask(this.teleportingPlayers.get((Object)player).intValue());
        this.teleportingPlayers.remove((Object)player);
        this.countdowns.remove((Object)player);
    }

    public static interface ITeleport {
        public void tickTeleportCountDownPlayer(Player var1, int var2);

        public void teleportPlayer(Player var1, Location var2);
    }

}

