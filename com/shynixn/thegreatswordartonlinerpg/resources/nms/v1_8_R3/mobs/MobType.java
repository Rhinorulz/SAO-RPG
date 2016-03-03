/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  net.minecraft.server.v1_8_R3.EntityCreature
 *  net.minecraft.server.v1_8_R3.EntityCreeper
 *  net.minecraft.server.v1_8_R3.EntityHuman
 *  net.minecraft.server.v1_8_R3.EntityInsentient
 *  net.minecraft.server.v1_8_R3.EntityLiving
 *  net.minecraft.server.v1_8_R3.EntitySkeleton
 *  net.minecraft.server.v1_8_R3.EntitySpider
 *  net.minecraft.server.v1_8_R3.EntityVillager
 *  net.minecraft.server.v1_8_R3.EntityZombie
 *  net.minecraft.server.v1_8_R3.IRangedEntity
 *  net.minecraft.server.v1_8_R3.PathfinderGoal
 *  net.minecraft.server.v1_8_R3.PathfinderGoalArrowAttack
 *  net.minecraft.server.v1_8_R3.PathfinderGoalBreakDoor
 *  net.minecraft.server.v1_8_R3.PathfinderGoalFloat
 *  net.minecraft.server.v1_8_R3.PathfinderGoalHurtByTarget
 *  net.minecraft.server.v1_8_R3.PathfinderGoalLeapAtTarget
 *  net.minecraft.server.v1_8_R3.PathfinderGoalLookAtPlayer
 *  net.minecraft.server.v1_8_R3.PathfinderGoalMeleeAttack
 *  net.minecraft.server.v1_8_R3.PathfinderGoalMoveThroughVillage
 *  net.minecraft.server.v1_8_R3.PathfinderGoalMoveTowardsRestriction
 *  net.minecraft.server.v1_8_R3.PathfinderGoalNearestAttackableTarget
 *  net.minecraft.server.v1_8_R3.PathfinderGoalNearestAttackableTargetInsentient
 *  net.minecraft.server.v1_8_R3.PathfinderGoalRandomLookaround
 *  net.minecraft.server.v1_8_R3.PathfinderGoalRandomStroll
 *  net.minecraft.server.v1_8_R3.PathfinderGoalSelector
 *  org.bukkit.craftbukkit.v1_8_R3.util.UnsafeList
 */
package com.shynixn.thegreatswordartonlinerpg.resources.nms.v1_8_R3.mobs;

import java.lang.reflect.Field;
import java.util.Random;
import net.minecraft.server.v1_8_R3.EntityCreature;
import net.minecraft.server.v1_8_R3.EntityCreeper;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EntityInsentient;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.EntitySkeleton;
import net.minecraft.server.v1_8_R3.EntitySpider;
import net.minecraft.server.v1_8_R3.EntityVillager;
import net.minecraft.server.v1_8_R3.EntityZombie;
import net.minecraft.server.v1_8_R3.IRangedEntity;
import net.minecraft.server.v1_8_R3.PathfinderGoal;
import net.minecraft.server.v1_8_R3.PathfinderGoalArrowAttack;
import net.minecraft.server.v1_8_R3.PathfinderGoalBreakDoor;
import net.minecraft.server.v1_8_R3.PathfinderGoalFloat;
import net.minecraft.server.v1_8_R3.PathfinderGoalHurtByTarget;
import net.minecraft.server.v1_8_R3.PathfinderGoalLeapAtTarget;
import net.minecraft.server.v1_8_R3.PathfinderGoalLookAtPlayer;
import net.minecraft.server.v1_8_R3.PathfinderGoalMeleeAttack;
import net.minecraft.server.v1_8_R3.PathfinderGoalMoveThroughVillage;
import net.minecraft.server.v1_8_R3.PathfinderGoalMoveTowardsRestriction;
import net.minecraft.server.v1_8_R3.PathfinderGoalNearestAttackableTarget;
import net.minecraft.server.v1_8_R3.PathfinderGoalNearestAttackableTargetInsentient;
import net.minecraft.server.v1_8_R3.PathfinderGoalRandomLookaround;
import net.minecraft.server.v1_8_R3.PathfinderGoalRandomStroll;
import net.minecraft.server.v1_8_R3.PathfinderGoalSelector;
import org.bukkit.craftbukkit.v1_8_R3.util.UnsafeList;

public class MobType {
    public static void enableAttackingMob(EntityCreature entity, PathfinderGoalSelector goalSelector, PathfinderGoalSelector targetSelector) {
        try {
            Field bField = PathfinderGoalSelector.class.getDeclaredField("b");
            bField.setAccessible(true);
            Field cField = PathfinderGoalSelector.class.getDeclaredField("c");
            cField.setAccessible(true);
            bField.set((Object)goalSelector, (Object)new UnsafeList());
            bField.set((Object)targetSelector, (Object)new UnsafeList());
            cField.set((Object)goalSelector, (Object)new UnsafeList());
            cField.set((Object)targetSelector, (Object)new UnsafeList());
        }
        catch (Exception exc) {
            exc.printStackTrace();
        }
        goalSelector.a(1, (PathfinderGoal)new PathfinderGoalBreakDoor((EntityInsentient)entity));
        goalSelector.a(4, (PathfinderGoal)new PathfinderGoalMoveTowardsRestriction(entity, 1.0));
        goalSelector.a(5, (PathfinderGoal)new PathfinderGoalMoveThroughVillage(entity, 1.0, false));
        goalSelector.a(6, (PathfinderGoal)new PathfinderGoalRandomStroll(entity, 1.0));
        goalSelector.a(7, (PathfinderGoal)new PathfinderGoalLookAtPlayer((EntityInsentient)entity, (Class)EntityHuman.class, 8.0f));
        goalSelector.a(7, (PathfinderGoal)new PathfinderGoalRandomLookaround((EntityInsentient)entity));
        targetSelector.a(1, (PathfinderGoal)new PathfinderGoalHurtByTarget(entity, false, new Class[0]));
        goalSelector.a(2, (PathfinderGoal)new PathfinderGoalMeleeAttack(entity, (Class)EntityHuman.class, 1.0, false));
        goalSelector.a(3, (PathfinderGoal)new PathfinderGoalMeleeAttack(entity, (Class)EntityVillager.class, 1.0, true));
        targetSelector.a(2, (PathfinderGoal)new PathfinderGoalNearestAttackableTarget(entity, (Class)EntityVillager.class, false));
        targetSelector.a(2, (PathfinderGoal)new PathfinderGoalNearestAttackableTarget(entity, (Class)EntityHuman.class, false));
    }

    public static void enableDefendingMob(EntityCreature entity, PathfinderGoalSelector goalSelector, PathfinderGoalSelector targetSelector) {
        try {
            Field bField = PathfinderGoalSelector.class.getDeclaredField("b");
            bField.setAccessible(true);
            Field cField = PathfinderGoalSelector.class.getDeclaredField("c");
            cField.setAccessible(true);
            bField.set((Object)goalSelector, (Object)new UnsafeList());
            bField.set((Object)targetSelector, (Object)new UnsafeList());
            cField.set((Object)goalSelector, (Object)new UnsafeList());
            cField.set((Object)targetSelector, (Object)new UnsafeList());
        }
        catch (Exception exc) {
            exc.printStackTrace();
        }
        goalSelector.a(1, (PathfinderGoal)new PathfinderGoalBreakDoor((EntityInsentient)entity));
        goalSelector.a(4, (PathfinderGoal)new PathfinderGoalMoveTowardsRestriction(entity, 1.0));
        goalSelector.a(5, (PathfinderGoal)new PathfinderGoalMoveThroughVillage(entity, 1.0, false));
        goalSelector.a(6, (PathfinderGoal)new PathfinderGoalRandomStroll(entity, 1.0));
        goalSelector.a(7, (PathfinderGoal)new PathfinderGoalLookAtPlayer((EntityInsentient)entity, (Class)EntityHuman.class, 8.0f));
        goalSelector.a(7, (PathfinderGoal)new PathfinderGoalRandomLookaround((EntityInsentient)entity));
        targetSelector.a(1, (PathfinderGoal)new PathfinderGoalHurtByTarget(entity, false, new Class[0]));
        goalSelector.a(2, (PathfinderGoal)new PathfinderGoalMeleeAttack(entity, (Class)EntityZombie.class, 1.0, false));
        goalSelector.a(3, (PathfinderGoal)new PathfinderGoalMeleeAttack(entity, (Class)EntitySkeleton.class, 1.0, true));
        targetSelector.a(2, (PathfinderGoal)new PathfinderGoalNearestAttackableTarget(entity, (Class)EntitySkeleton.class, false));
        targetSelector.a(2, (PathfinderGoal)new PathfinderGoalNearestAttackableTarget(entity, (Class)EntityZombie.class, false));
        goalSelector.a(3, (PathfinderGoal)new PathfinderGoalMeleeAttack(entity, (Class)EntityCreeper.class, 1.0, true));
        targetSelector.a(2, (PathfinderGoal)new PathfinderGoalNearestAttackableTarget(entity, (Class)EntityCreeper.class, false));
        goalSelector.a(3, (PathfinderGoal)new PathfinderGoalMeleeAttack(entity, (Class)EntitySpider.class, 1.0, true));
        targetSelector.a(2, (PathfinderGoal)new PathfinderGoalNearestAttackableTarget(entity, (Class)EntitySpider.class, false));
    }

    public static void enableSpiderMob(EntityCreature entity, PathfinderGoalSelector goalSelector, PathfinderGoalSelector targetSelector) {
        try {
            Field bField = PathfinderGoalSelector.class.getDeclaredField("b");
            bField.setAccessible(true);
            Field cField = PathfinderGoalSelector.class.getDeclaredField("c");
            cField.setAccessible(true);
            bField.set((Object)goalSelector, (Object)new UnsafeList());
            bField.set((Object)targetSelector, (Object)new UnsafeList());
            cField.set((Object)goalSelector, (Object)new UnsafeList());
            cField.set((Object)targetSelector, (Object)new UnsafeList());
        }
        catch (Exception exc) {
            exc.printStackTrace();
        }
        goalSelector.a(1, (PathfinderGoal)new PathfinderGoalFloat((EntityInsentient)entity));
        goalSelector.a(3, (PathfinderGoal)new PathfinderGoalLeapAtTarget((EntityInsentient)entity, 0.4f));
        goalSelector.a(4, (PathfinderGoal)new PathfinderGoalSpiderMeleeAttack(entity, EntityHuman.class));
        goalSelector.a(4, (PathfinderGoal)new PathfinderGoalSpiderMeleeAttack(entity, EntityVillager.class));
        goalSelector.a(5, (PathfinderGoal)new PathfinderGoalRandomStroll(entity, 0.8));
        goalSelector.a(6, (PathfinderGoal)new PathfinderGoalLookAtPlayer((EntityInsentient)entity, (Class)EntityHuman.class, 8.0f));
        goalSelector.a(6, (PathfinderGoal)new PathfinderGoalRandomLookaround((EntityInsentient)entity));
        targetSelector.a(1, (PathfinderGoal)new PathfinderGoalHurtByTarget(entity, false, new Class[0]));
        targetSelector.a(2, (PathfinderGoal)new PathfinderGoalSpiderNearestAttackableTarget(entity, EntityHuman.class));
        targetSelector.a(3, (PathfinderGoal)new PathfinderGoalSpiderNearestAttackableTarget(entity, EntityVillager.class));
    }

    public static void enableSlimeMob(EntityInsentient entity, PathfinderGoalSelector goalSelector, PathfinderGoalSelector targetSelector) {
        try {
            Field bField = PathfinderGoalSelector.class.getDeclaredField("b");
            bField.setAccessible(true);
            Field cField = PathfinderGoalSelector.class.getDeclaredField("c");
            cField.setAccessible(true);
        }
        catch (Exception exc) {
            exc.printStackTrace();
        }
        targetSelector.a(5, (PathfinderGoal)new PathfinderGoalNearestAttackableTargetInsentient(entity, (Class)EntityVillager.class));
    }

    public static void enableShootingMob(IRangedEntity entity, PathfinderGoalSelector goalSelector, PathfinderGoalSelector targetSelector) {
        try {
            Field bField = PathfinderGoalSelector.class.getDeclaredField("b");
            bField.setAccessible(true);
            Field cField = PathfinderGoalSelector.class.getDeclaredField("c");
            cField.setAccessible(true);
            bField.set((Object)goalSelector, (Object)new UnsafeList());
            bField.set((Object)targetSelector, (Object)new UnsafeList());
            cField.set((Object)goalSelector, (Object)new UnsafeList());
            cField.set((Object)targetSelector, (Object)new UnsafeList());
        }
        catch (Exception exc) {
            exc.printStackTrace();
        }
        goalSelector.a(4, (PathfinderGoal)new PathfinderGoalArrowAttack(entity, 1.0, 20, 60, 15.0f));
        goalSelector.a(4, (PathfinderGoal)new PathfinderGoalMeleeAttack((EntityCreature)entity, (Class)EntityHuman.class, 1.2, false));
        goalSelector.a(4, (PathfinderGoal)new PathfinderGoalMeleeAttack((EntityCreature)entity, (Class)EntityVillager.class, 1.2, false));
        goalSelector.a(1, (PathfinderGoal)new PathfinderGoalFloat((EntityInsentient)((EntityCreature)entity)));
        goalSelector.a(4, (PathfinderGoal)new PathfinderGoalRandomStroll((EntityCreature)entity, 1.0));
        goalSelector.a(6, (PathfinderGoal)new PathfinderGoalLookAtPlayer((EntityInsentient)((EntityCreature)entity), (Class)EntityHuman.class, 8.0f));
        goalSelector.a(6, (PathfinderGoal)new PathfinderGoalRandomLookaround((EntityInsentient)((EntityCreature)entity)));
        targetSelector.a(1, (PathfinderGoal)new PathfinderGoalHurtByTarget((EntityCreature)entity, false, new Class[0]));
        targetSelector.a(2, (PathfinderGoal)new PathfinderGoalNearestAttackableTarget((EntityCreature)entity, (Class)EntityHuman.class, true));
        targetSelector.a(3, (PathfinderGoal)new PathfinderGoalNearestAttackableTarget((EntityCreature)entity, (Class)EntityVillager.class, true));
    }

    public static class PathfinderGoalSpiderMeleeAttack
    extends PathfinderGoalMeleeAttack {
        public PathfinderGoalSpiderMeleeAttack(EntityCreature paramEntitySpider, Class paramClass) {
            super(paramEntitySpider, paramClass, 1.0, true);
        }

        public boolean b() {
            float f = this.b.c(1.0f);
            if (f >= 0.5f && this.b.bc().nextInt(100) == 0) {
                this.b.setGoalTarget(null);
                return false;
            }
            return super.b();
        }

        protected double a(EntityLiving paramEntityLiving) {
            return 4.0f + paramEntityLiving.width;
        }
    }

    public static class PathfinderGoalSpiderNearestAttackableTarget
    extends PathfinderGoalNearestAttackableTarget<EntitySpider> {
        public PathfinderGoalSpiderNearestAttackableTarget(EntityCreature paramEntitySpider, Class paramClass) {
            super(paramEntitySpider, paramClass, true);
        }

        public boolean a() {
            float f = this.e.c(1.0f);
            if (f >= 0.5f) {
                return false;
            }
            return super.a();
        }
    }

}

