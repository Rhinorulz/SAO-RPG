/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.World
 *  org.bukkit.block.Block
 *  org.bukkit.entity.Player
 */
package com.shynixn.thegreatswordartonlinerpg.gamesystems.mobs;

import com.shynixn.thegreatswordartonlinerpg.gamesystems.mobs.Mob;
import libraries.com.shynixn.utilities.BukkitAreaAPI;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class MobArena {
    private BukkitAreaAPI.BukkitArea area;
    private Mob mob;
    private Player player;
    private boolean dayLightburn = false;
    private boolean damageAble = false;
    private boolean moveAble = false;
    private boolean attacking = false;
    private boolean showHealth = false;

    public MobArena(Player player, BukkitAreaAPI.BukkitArea area, Mob mob) {
        this.area = area;
        this.mob = mob;
        this.player = player;
    }

    public Player getPlayer() {
        return this.player;
    }

    public BukkitAreaAPI.BukkitArea getArea() {
        return this.area;
    }

    public Mob getMob() {
        return this.mob;
    }

    public boolean isDayLightburn() {
        return this.dayLightburn;
    }

    public void setDayLightburn(boolean dayLightburn) {
        this.dayLightburn = dayLightburn;
    }

    public boolean isDamageAble() {
        return this.damageAble;
    }

    public void setDamageAble(boolean damageAble) {
        this.damageAble = damageAble;
    }

    public boolean isMoveAble() {
        return this.moveAble;
    }

    public void setMoveAble(boolean moveAble) {
        this.moveAble = moveAble;
    }

    public boolean isAttacking() {
        return this.attacking;
    }

    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }

    public void saveDelete() {
        this.area.reset();
        this.mob.kill();
    }

    public void delete() {
        this.area.instantReset();
        this.mob.kill();
    }

    public void build() {
        int j;
        Location location = this.player.getLocation();
        int i = 0;
        while (i < 50) {
            j = 0;
            while (j < 50) {
                new Location(location.getWorld(), location.getX() + (double)i, location.getY(), location.getZ() + (double)j).getBlock().setType(Material.IRON_BLOCK);
                ++j;
            }
            ++i;
        }
        i = 0;
        while (i < 50) {
            j = 0;
            while (j < 8) {
                new Location(location.getWorld(), location.getX() + (double)i, location.getY() + (double)j, location.getZ()).getBlock().setType(Material.IRON_BLOCK);
                new Location(location.getWorld(), location.getX(), location.getY() + (double)j, location.getZ() + (double)i).getBlock().setType(Material.IRON_BLOCK);
                ++j;
            }
            ++i;
        }
        i = 0;
        while (i < 50) {
            j = 0;
            while (j < 8) {
                new Location(location.getWorld(), location.getX() + (double)i, location.getY() + (double)j, location.getZ() + 49.0).getBlock().setType(Material.IRON_BLOCK);
                new Location(location.getWorld(), location.getX() + 49.0, location.getY() + (double)j, location.getZ() + (double)i).getBlock().setType(Material.IRON_BLOCK);
                ++j;
            }
            ++i;
        }
        i = 1;
        while (i < 49) {
            j = 1;
            while (j < 8) {
                int k = 1;
                while (k < 49) {
                    new Location(location.getWorld(), location.getX() + (double)i, location.getY() + (double)j, location.getZ() + (double)k).getBlock().setType(Material.AIR);
                    ++k;
                }
                ++j;
            }
            ++i;
        }
        this.mob.respawn(new Location(location.getWorld(), location.getX() + 25.0, location.getY() + 3.0, location.getZ() + 25.0));
        this.player.teleport(new Location(location.getWorld(), location.getX() + 25.0, location.getY() + 3.0, location.getZ() + 20.0));
    }

    public boolean isShowHealth() {
        return this.showHealth;
    }

    public void setShowHealth(boolean showHealth) {
        this.showHealth = showHealth;
    }
}

