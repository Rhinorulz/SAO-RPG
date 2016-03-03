/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  net.minecraft.server.v1_8_R1.DamageSource
 *  net.minecraft.server.v1_8_R1.Entity
 *  net.minecraft.server.v1_8_R1.EntityCreature
 *  net.minecraft.server.v1_8_R1.EntityInsentient
 *  net.minecraft.server.v1_8_R1.EntityLiving
 *  net.minecraft.server.v1_8_R1.WorldServer
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.World
 *  org.bukkit.block.Block
 *  org.bukkit.craftbukkit.v1_8_R1.CraftWorld
 *  org.bukkit.craftbukkit.v1_8_R1.entity.CraftEntity
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Player
 *  org.bukkit.entity.Villager
 *  org.bukkit.event.entity.CreatureSpawnEvent
 *  org.bukkit.event.entity.CreatureSpawnEvent$SpawnReason
 *  org.bukkit.util.Vector
 */
package com.shynixn.thegreatswordartonlinerpg.resources.nms.v1_8_R1.mobs;

import com.shynixn.thegreatswordartonlinerpg.gamesystems.mobs.MobEquipment;
import com.shynixn.thegreatswordartonlinerpg.resources.nms.FlyHelper;
import com.shynixn.thegreatswordartonlinerpg.resources.nms.MobMoves;
import com.shynixn.thegreatswordartonlinerpg.resources.nms.SaoMob;
import java.util.List;
import java.util.Random;
import net.minecraft.server.v1_8_R1.DamageSource;
import net.minecraft.server.v1_8_R1.EntityCreature;
import net.minecraft.server.v1_8_R1.EntityInsentient;
import net.minecraft.server.v1_8_R1.EntityLiving;
import net.minecraft.server.v1_8_R1.WorldServer;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.util.Vector;

public final class MobExtender {
    public static void spawn(EntityInsentient entity, Location location) {
        WorldServer mcWorld = ((CraftWorld)location.getWorld()).getHandle();
        entity.setPosition(location.getX(), location.getY(), location.getZ());
        mcWorld.addEntity((net.minecraft.server.v1_8_R1.Entity)entity, CreatureSpawnEvent.SpawnReason.CUSTOM);
    }

    public static void attackPassive(EntityCreature damager, net.minecraft.server.v1_8_R1.Entity damaged, MobEquipment equipment) {
        damaged.damageEntity(DamageSource.mobAttack((EntityLiving)damager), (float)equipment.getDamage());
        MobMoves.kickEntity((Entity)damaged.getBukkitEntity(), (Entity)damager.getBukkitEntity(), equipment);
        damaged.setOnFire(equipment.getFireTime());
    }

    public static void attackAggresive(EntityCreature damager, net.minecraft.server.v1_8_R1.Entity damaged, MobEquipment equipment) {
        MobMoves.kickEntity((Entity)damaged.getBukkitEntity(), (Entity)damager.getBukkitEntity(), equipment);
        damaged.setOnFire(equipment.getFireTime());
    }

    public static void attackSlime(EntityInsentient damager, net.minecraft.server.v1_8_R1.Entity damaged, MobEquipment equipment) {
        if (damaged.getBukkitEntity() instanceof Villager || damaged.getBukkitEntity() instanceof Player) {
            MobMoves.kickEntity((Entity)damaged.getBukkitEntity(), (Entity)damager.getBukkitEntity(), equipment);
            damaged.setOnFire(equipment.getFireTime());
        }
    }

    public static void flyPathFinderMob(SaoMob saoMob, MobEquipment equipment, FlyHelper helper) {
        Random random;
        Vector vector = saoMob.getBukkitEntity().getVelocity();
        boolean check = false;
        Player player2 = null;
        double distance = 0.0;
        for (Player player : saoMob.getBukkitEntity().getLocation().getWorld().getPlayers()) {
            distance = saoMob.getBukkitEntity().getLocation().distance(player.getLocation());
            if (distance >= 20.0) continue;
            player2 = player;
            if (saoMob.getBukkitEntity().getLocation().add(0.0, 1.0, 0.0).distance(player.getLocation()) < distance) {
                saoMob.getBukkitEntity().setVelocity(vector.add(new Vector(0.0, 0.1, 0.0)));
            } else if (saoMob.getBukkitEntity().getLocation().add(0.0, -1.0, 0.0).distance(player.getLocation()) < distance) {
                saoMob.getBukkitEntity().setVelocity(vector.add(new Vector(0.0, -0.1, 0.0)));
            }
            check = true;
            break;
        }
        if (!check) {
            random = new Random();
            int value = random.nextInt(2);
            int switchvalue = 1;
            if (helper.switcher) {
                switchvalue = -1;
            }
            if (value == 0) {
                saoMob.getBukkitEntity().setVelocity(vector.add(new Vector(0.0, 0.1 * (double)switchvalue, 0.0)));
            } else if (value == 1) {
                saoMob.getBukkitEntity().setVelocity(vector.add(new Vector(0.0, 0.2 * (double)switchvalue, 0.0)));
            }
            if (saoMob.getBukkitEntity().getLocation().add(0.0, 1.0, 0.0).getBlock().getType() != Material.AIR || helper.spawnhigh + equipment.getMaxHight() <= (double)saoMob.getBukkitEntity().getLocation().getBlockY()) {
                helper.switcher = true;
            } else if (saoMob.getBukkitEntity().getLocation().add(0.0, -1.0, 0.0).getBlock().getType() != Material.AIR || helper.spawnhigh - equipment.getMinHight() >= (double)saoMob.getBukkitEntity().getLocation().getBlockY()) {
                helper.switcher = false;
            }
        } else {
            random = new Random();
            int value = random.nextInt(11);
            double speeder = (double)value / 10.0;
            if (player2.isSprinting()) {
                speeder *= 2.0;
            }
            if (saoMob.getBukkitEntity().getLocation().add(1.0, 0.0, 0.0).distance(player2.getLocation()) < distance) {
                saoMob.getBukkitEntity().setVelocity(vector.add(new Vector(0.1 * speeder, 0.0, 0.0)));
            }
            if (saoMob.getBukkitEntity().getLocation().add(-1.0, 0.0, 0.0).distance(player2.getLocation()) < distance) {
                saoMob.getBukkitEntity().setVelocity(vector.add(new Vector(-0.1 * speeder, 0.0, 0.0)));
            }
            if (saoMob.getBukkitEntity().getLocation().add(0.0, 0.0, 1.0).distance(player2.getLocation()) < distance) {
                saoMob.getBukkitEntity().setVelocity(vector.add(new Vector(0.0, 0.0, 0.1 * speeder)));
            }
            if (saoMob.getBukkitEntity().getLocation().add(0.0, 0.0, -1.0).distance(player2.getLocation()) < distance) {
                saoMob.getBukkitEntity().setVelocity(vector.add(new Vector(0.0, 0.0, -0.1 * speeder)));
            }
            if (helper.counter == 15) {
                saoMob.getBukkitEntity().setVelocity(vector.add(new Vector(0.0, 0.5, 0.0)));
            }
            if (helper.counter == 20) {
                if (saoMob.getBukkitEntity().getLocation().distance(player2.getLocation()) < 1.0) {
                    MobExtender.attackPassive((EntityCreature)saoMob, ((CraftEntity)player2).getHandle(), equipment);
                }
                helper.counter = -1;
            }
            ++helper.counter;
        }
    }
}

