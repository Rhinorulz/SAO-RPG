/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.World
 *  org.bukkit.block.Block
 *  org.bukkit.entity.Item
 *  org.bukkit.entity.LightningStrike
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 */
package com.shynixn.thegreatswordartonlinerpg.gamesystems.skills;

import com.shynixn.thegreatswordartonlinerpg.cardinal.Cardinal;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.skills.Skill;
import libraries.com.shynixn.utilities.BukkitAreaAPI;
import libraries.com.shynixn.utilities.BukkitUtilities;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Item;
import org.bukkit.entity.LightningStrike;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public final class SkillArena {
    private BukkitAreaAPI.BukkitArea area;
    private Skill skill;
    private Player player;
    private Item item;
    private boolean turner = false;

    public SkillArena(Player player, BukkitAreaAPI.BukkitArea area, Skill skill) {
        this.area = area;
        this.skill = skill;
        this.player = player;
    }

    public boolean turn() {
        this.turner = !this.turner;
        return this.turner;
    }

    public Item getItem() {
        return this.item;
    }

    public void respawnItem() {
        this.item = this.area.getCenter().getWorld().dropItem(new Location(this.area.getCenter().getWorld(), (double)this.area.getCenter().getBlockX(), (double)(this.area.getCenter().getBlockY() + 3), (double)this.area.getCenter().getBlockZ()), this.getPersonalSkill());
    }

    private ItemStack getPersonalSkill() {
        return BukkitUtilities.u().nameItem(new ItemStack(Material.WOOL, 1, 10), this.skill.getDisplayName(), new String[]{(Object)ChatColor.GREEN + "Arena-Skill " + this.skill.getName(), this.skill.getLore(0), this.skill.getLore(1), this.skill.getLore(2)});
    }

    public void refresh() {
        Cardinal.getSkillExecutor().cancelAllSkills((LivingEntity)this.player);
        this.area.getCenter().getWorld().strikeLightning(this.area.getCenter().add(0.0, -3.0, 0.0));
        this.item.remove();
        this.respawnItem();
    }

    public Player getPlayer() {
        return this.player;
    }

    public BukkitAreaAPI.BukkitArea getArea() {
        return this.area;
    }

    public Skill getSkill() {
        return this.skill;
    }

    public void saveDelete() {
        this.area.reset();
        this.item.remove();
    }

    public void delete() {
        this.area.instantReset();
        this.item.remove();
    }

    public void build() {
        int j;
        Location location = this.player.getLocation();
        int i = 0;
        while (i < 50) {
            j = 0;
            while (j < 50) {
                new Location(location.getWorld(), location.getX() + (double)i, location.getY(), location.getZ() + (double)j).getBlock().setType(Material.QUARTZ_BLOCK);
                ++j;
            }
            ++i;
        }
        i = 0;
        while (i < 50) {
            j = 0;
            while (j < 8) {
                new Location(location.getWorld(), location.getX() + (double)i, location.getY() + (double)j, location.getZ()).getBlock().setType(Material.QUARTZ_BLOCK);
                new Location(location.getWorld(), location.getX(), location.getY() + (double)j, location.getZ() + (double)i).getBlock().setType(Material.QUARTZ_BLOCK);
                ++j;
            }
            ++i;
        }
        i = 0;
        while (i < 50) {
            j = 0;
            while (j < 8) {
                new Location(location.getWorld(), location.getX() + (double)i, location.getY() + (double)j, location.getZ() + 49.0).getBlock().setType(Material.QUARTZ_BLOCK);
                new Location(location.getWorld(), location.getX() + 49.0, location.getY() + (double)j, location.getZ() + (double)i).getBlock().setType(Material.QUARTZ_BLOCK);
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
        this.player.teleport(new Location(location.getWorld(), location.getX() + 25.0, location.getY() + 3.0, location.getZ() + 20.0));
        Location location2 = new Location(this.area.getCenter().getWorld(), (double)this.area.getCenter().getBlockX(), (double)(this.area.getCenter().getBlockY() - 3), (double)this.area.getCenter().getBlockZ());
        location2.getBlock().setType(Material.ENCHANTMENT_TABLE);
        new Location(location2.getWorld(), (double)location2.getBlockX(), (double)location2.getBlockY(), (double)location2.getBlockZ()).add(0.0, 0.0, 1.0).getBlock().setType(Material.QUARTZ_BLOCK);
        new Location(location2.getWorld(), (double)location2.getBlockX(), (double)location2.getBlockY(), (double)location2.getBlockZ()).add(0.0, 0.0, 1.0).getBlock().setData(1);
        new Location(location2.getWorld(), (double)location2.getBlockX(), (double)location2.getBlockY(), (double)location2.getBlockZ()).add(0.0, 0.0, -1.0).getBlock().setType(Material.QUARTZ_BLOCK);
        new Location(location2.getWorld(), (double)location2.getBlockX(), (double)location2.getBlockY(), (double)location2.getBlockZ()).add(0.0, 0.0, -1.0).getBlock().setData(1);
        new Location(location2.getWorld(), (double)location2.getBlockX(), (double)location2.getBlockY(), (double)location2.getBlockZ()).add(1.0, 0.0, 0.0).getBlock().setType(Material.QUARTZ_BLOCK);
        new Location(location2.getWorld(), (double)location2.getBlockX(), (double)location2.getBlockY(), (double)location2.getBlockZ()).add(1.0, 0.0, 0.0).getBlock().setData(1);
        new Location(location2.getWorld(), (double)location2.getBlockX(), (double)location2.getBlockY(), (double)location2.getBlockZ()).add(-1.0, 0.0, 0.0).getBlock().setType(Material.QUARTZ_BLOCK);
        new Location(location2.getWorld(), (double)location2.getBlockX(), (double)location2.getBlockY(), (double)location2.getBlockZ()).add(-1.0, 0.0, 0.0).getBlock().setData(1);
        new Location(location2.getWorld(), (double)location2.getBlockX(), (double)location2.getBlockY(), (double)location2.getBlockZ()).add(1.0, 0.0, 1.0).getBlock().setType(Material.BEACON);
        new Location(location2.getWorld(), (double)location2.getBlockX(), (double)location2.getBlockY(), (double)location2.getBlockZ()).add(-1.0, 0.0, -1.0).getBlock().setType(Material.BEACON);
        new Location(location2.getWorld(), (double)location2.getBlockX(), (double)location2.getBlockY(), (double)location2.getBlockZ()).add(-1.0, 0.0, 1.0).getBlock().setType(Material.BEACON);
        new Location(location2.getWorld(), (double)location2.getBlockX(), (double)location2.getBlockY(), (double)location2.getBlockZ()).add(1.0, 0.0, -1.0).getBlock().setType(Material.BEACON);
        this.respawnItem();
    }
}

