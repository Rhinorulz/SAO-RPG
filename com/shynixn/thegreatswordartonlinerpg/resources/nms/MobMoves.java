/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.util.Vector
 */
package com.shynixn.thegreatswordartonlinerpg.resources.nms;

import com.shynixn.thegreatswordartonlinerpg.gamesystems.mobs.MobEquipment;
import com.shynixn.thegreatswordartonlinerpg.resources.effects.Launch;
import com.shynixn.thegreatswordartonlinerpg.resources.enums.Direction;
import libraries.com.shynixn.utilities.BukkitUtilities;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

public final class MobMoves {
    public static void kickEntity(Entity entity, Entity entity2, MobEquipment equipment) {
        if (entity instanceof LivingEntity) {
            MobMoves.launch((LivingEntity)entity, new Launch(Direction.UP, equipment.getKnockBackUP()), entity2);
            MobMoves.launch((LivingEntity)entity, new Launch(Direction.DOWN, equipment.getKnockBackDOWN()), entity2);
            MobMoves.launch((LivingEntity)entity, new Launch(Direction.LEFT, equipment.getKnockBackLEFT()), entity2);
            MobMoves.launch((LivingEntity)entity, new Launch(Direction.RIGHT, equipment.getKnockBackRIGHT()), entity2);
            MobMoves.launch((LivingEntity)entity, new Launch(Direction.BACK, equipment.getKnockBackBACK()), entity2);
            MobMoves.launch((LivingEntity)entity, new Launch(Direction.FORWARD, equipment.getKnockBackFORWARD()), entity2);
        }
    }

    private static void launch(LivingEntity entity, Launch effect, Entity entity2) {
        String direction = BukkitUtilities.u().getCardinalDirection(entity2);
        if (effect.getDirection().equals((Object)Direction.UP)) {
            entity.setVelocity(new Vector(0.0, effect.getAmplifier(), 0.0));
        } else if (effect.getDirection().equals((Object)Direction.DOWN)) {
            entity.setVelocity(new Vector(0.0, -1.0 * effect.getAmplifier(), 0.0));
        } else if (direction.equals("N")) {
            MobMoves.launchLivingEntityNorth(entity, effect);
        } else if (direction.equals("NE")) {
            MobMoves.launchLivingEntityNorthEast(entity, effect);
        } else if (direction.equals("E")) {
            MobMoves.launchLivingEntityEast(entity, effect);
        } else if (direction.equals("SE")) {
            MobMoves.launchLivingEntitySouthEast(entity, effect);
        } else if (direction.equals("S")) {
            MobMoves.launchLivingEntitySouth(entity, effect);
        } else if (direction.equals("SW")) {
            MobMoves.launchLivingEntitySouthWest(entity, effect);
        } else if (direction.equals("W")) {
            MobMoves.launchLivingEntityWest(entity, effect);
        } else if (direction.equals("NW")) {
            MobMoves.launchLivingEntityNorthWest(entity, effect);
        }
    }

    private static void launchLivingEntityNorth(LivingEntity entity, Launch effect) {
        if (effect.getDirection().equals((Object)Direction.LEFT)) {
            entity.setVelocity(entity.getVelocity().add(new Vector(0.0, 0.0, effect.getAmplifier())));
        } else if (effect.getDirection().equals((Object)Direction.RIGHT)) {
            entity.setVelocity(entity.getVelocity().add(new Vector(0.0, 0.0, -1.0 * effect.getAmplifier())));
        } else if (effect.getDirection().equals((Object)Direction.FORWARD)) {
            entity.setVelocity(entity.getVelocity().add(new Vector(-1.0 * effect.getAmplifier(), 0.0, 0.0)));
        } else if (effect.getDirection().equals((Object)Direction.BACK)) {
            entity.setVelocity(entity.getVelocity().add(new Vector(effect.getAmplifier(), 0.0, 0.0)));
        }
    }

    private static void launchLivingEntityNorthEast(LivingEntity entity, Launch effect) {
        if (effect.getDirection().equals((Object)Direction.LEFT)) {
            entity.setVelocity(entity.getVelocity().add(new Vector(-1.0 * effect.getAmplifier(), 0.0, effect.getAmplifier())));
        } else if (effect.getDirection().equals((Object)Direction.RIGHT)) {
            entity.setVelocity(entity.getVelocity().add(new Vector(effect.getAmplifier(), 0.0, -1.0 * effect.getAmplifier())));
        } else if (effect.getDirection().equals((Object)Direction.FORWARD)) {
            entity.setVelocity(entity.getVelocity().add(new Vector(-1.0 * effect.getAmplifier(), 0.0, -1.0 * effect.getAmplifier())));
        } else if (effect.getDirection().equals((Object)Direction.BACK)) {
            entity.setVelocity(entity.getVelocity().add(new Vector(effect.getAmplifier(), 0.0, effect.getAmplifier())));
        }
    }

    private static void launchLivingEntityEast(LivingEntity entity, Launch effect) {
        if (effect.getDirection().equals((Object)Direction.LEFT)) {
            entity.setVelocity(entity.getVelocity().add(new Vector(-1.0 * effect.getAmplifier(), 0.0, 0.0)));
        } else if (effect.getDirection().equals((Object)Direction.RIGHT)) {
            entity.setVelocity(entity.getVelocity().add(new Vector(effect.getAmplifier(), 0.0, 0.0)));
        } else if (effect.getDirection().equals((Object)Direction.FORWARD)) {
            entity.setVelocity(entity.getVelocity().add(new Vector(0.0, 0.0, -1.0 * effect.getAmplifier())));
        } else if (effect.getDirection().equals((Object)Direction.BACK)) {
            entity.setVelocity(entity.getVelocity().add(new Vector(0.0, 0.0, effect.getAmplifier())));
        }
    }

    private static void launchLivingEntitySouthEast(LivingEntity entity, Launch effect) {
        if (effect.getDirection().equals((Object)Direction.LEFT)) {
            entity.setVelocity(entity.getVelocity().add(new Vector(-1.0 * effect.getAmplifier(), 0.0, -1.0 * effect.getAmplifier())));
        } else if (effect.getDirection().equals((Object)Direction.RIGHT)) {
            entity.setVelocity(entity.getVelocity().add(new Vector(effect.getAmplifier(), 0.0, effect.getAmplifier())));
        } else if (effect.getDirection().equals((Object)Direction.FORWARD)) {
            entity.setVelocity(entity.getVelocity().add(new Vector(effect.getAmplifier(), 0.0, -1.0 * effect.getAmplifier())));
        } else if (effect.getDirection().equals((Object)Direction.BACK)) {
            entity.setVelocity(entity.getVelocity().add(new Vector(-1.0 * effect.getAmplifier(), 0.0, effect.getAmplifier())));
        }
    }

    private static void launchLivingEntitySouth(LivingEntity entity, Launch effect) {
        if (effect.getDirection().equals((Object)Direction.LEFT)) {
            entity.setVelocity(entity.getVelocity().add(new Vector(0.0, 0.0, -1.0 * effect.getAmplifier())));
        } else if (effect.getDirection().equals((Object)Direction.RIGHT)) {
            entity.setVelocity(entity.getVelocity().add(new Vector(0.0, 0.0, effect.getAmplifier())));
        } else if (effect.getDirection().equals((Object)Direction.FORWARD)) {
            entity.setVelocity(entity.getVelocity().add(new Vector(effect.getAmplifier(), 0.0, 0.0)));
        } else if (effect.getDirection().equals((Object)Direction.BACK)) {
            entity.setVelocity(entity.getVelocity().add(new Vector(-1.0 * effect.getAmplifier(), 0.0, 0.0)));
        }
    }

    private static void launchLivingEntitySouthWest(LivingEntity entity, Launch effect) {
        if (effect.getDirection().equals((Object)Direction.LEFT)) {
            entity.setVelocity(entity.getVelocity().add(new Vector(effect.getAmplifier(), 0.0, -1.0 * effect.getAmplifier())));
        } else if (effect.getDirection().equals((Object)Direction.RIGHT)) {
            entity.setVelocity(entity.getVelocity().add(new Vector(-1.0 * effect.getAmplifier(), 0.0, effect.getAmplifier())));
        } else if (effect.getDirection().equals((Object)Direction.FORWARD)) {
            entity.setVelocity(entity.getVelocity().add(new Vector(effect.getAmplifier(), 0.0, effect.getAmplifier())));
        } else if (effect.getDirection().equals((Object)Direction.BACK)) {
            entity.setVelocity(entity.getVelocity().add(new Vector(-1.0 * effect.getAmplifier(), 0.0, -1.0 * effect.getAmplifier())));
        }
    }

    private static void launchLivingEntityWest(LivingEntity entity, Launch effect) {
        if (effect.getDirection().equals((Object)Direction.LEFT)) {
            entity.setVelocity(entity.getVelocity().add(new Vector(effect.getAmplifier(), 0.0, 0.0)));
        } else if (effect.getDirection().equals((Object)Direction.RIGHT)) {
            entity.setVelocity(entity.getVelocity().add(new Vector(-1.0 * effect.getAmplifier(), 0.0, 0.0)));
        } else if (effect.getDirection().equals((Object)Direction.FORWARD)) {
            entity.setVelocity(entity.getVelocity().add(new Vector(0.0, 0.0, effect.getAmplifier())));
        } else if (effect.getDirection().equals((Object)Direction.BACK)) {
            entity.setVelocity(entity.getVelocity().add(new Vector(0.0, 0.0, -1.0 * effect.getAmplifier())));
        }
    }

    private static void launchLivingEntityNorthWest(LivingEntity entity, Launch effect) {
        if (effect.getDirection().equals((Object)Direction.LEFT)) {
            entity.setVelocity(entity.getVelocity().add(new Vector(effect.getAmplifier(), 0.0, effect.getAmplifier())));
        } else if (effect.getDirection().equals((Object)Direction.RIGHT)) {
            entity.setVelocity(entity.getVelocity().add(new Vector(-1.0 * effect.getAmplifier(), 0.0, -1.0 * effect.getAmplifier())));
        } else if (effect.getDirection().equals((Object)Direction.FORWARD)) {
            entity.setVelocity(entity.getVelocity().add(new Vector(-1.0 * effect.getAmplifier(), 0.0, effect.getAmplifier())));
        } else if (effect.getDirection().equals((Object)Direction.BACK)) {
            entity.setVelocity(entity.getVelocity().add(new Vector(effect.getAmplifier(), 0.0, -1.0 * effect.getAmplifier())));
        }
    }
}

