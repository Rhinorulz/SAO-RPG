/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  net.minecraft.server.v1_8_R2.AxisAlignedBB
 *  net.minecraft.server.v1_8_R2.BlockPosition
 *  net.minecraft.server.v1_8_R2.ControllerLook
 *  net.minecraft.server.v1_8_R2.ControllerMove
 *  net.minecraft.server.v1_8_R2.Entity
 *  net.minecraft.server.v1_8_R2.EntityBlaze
 *  net.minecraft.server.v1_8_R2.EntityCreature
 *  net.minecraft.server.v1_8_R2.EntityHuman
 *  net.minecraft.server.v1_8_R2.EntityInsentient
 *  net.minecraft.server.v1_8_R2.EntityLiving
 *  net.minecraft.server.v1_8_R2.EntitySmallFireball
 *  net.minecraft.server.v1_8_R2.MathHelper
 *  net.minecraft.server.v1_8_R2.NavigationAbstract
 *  net.minecraft.server.v1_8_R2.PathfinderGoal
 *  net.minecraft.server.v1_8_R2.PathfinderGoalSelector
 *  net.minecraft.server.v1_8_R2.World
 *  net.minecraft.server.v1_8_R2.WorldServer
 *  org.bukkit.Location
 *  org.bukkit.World
 *  org.bukkit.craftbukkit.v1_8_R2.CraftWorld
 *  org.bukkit.craftbukkit.v1_8_R2.entity.CraftEntity
 *  org.bukkit.entity.Entity
 */
package com.shynixn.thegreatswordartonlinerpg.resources.nms.v1_8_R2.mobs;

import com.shynixn.thegreatswordartonlinerpg.gamesystems.mobs.MobEquipment;
import com.shynixn.thegreatswordartonlinerpg.resources.nms.FlyHelper;
import com.shynixn.thegreatswordartonlinerpg.resources.nms.SaoMob;
import com.shynixn.thegreatswordartonlinerpg.resources.nms.v1_8_R2.mobs.MobExtender;
import com.shynixn.thegreatswordartonlinerpg.resources.nms.v1_8_R2.mobs.MobType;
import java.util.Random;
import net.minecraft.server.v1_8_R2.AxisAlignedBB;
import net.minecraft.server.v1_8_R2.BlockPosition;
import net.minecraft.server.v1_8_R2.ControllerLook;
import net.minecraft.server.v1_8_R2.ControllerMove;
import net.minecraft.server.v1_8_R2.EntityBlaze;
import net.minecraft.server.v1_8_R2.EntityCreature;
import net.minecraft.server.v1_8_R2.EntityHuman;
import net.minecraft.server.v1_8_R2.EntityInsentient;
import net.minecraft.server.v1_8_R2.EntityLiving;
import net.minecraft.server.v1_8_R2.EntitySmallFireball;
import net.minecraft.server.v1_8_R2.MathHelper;
import net.minecraft.server.v1_8_R2.NavigationAbstract;
import net.minecraft.server.v1_8_R2.PathfinderGoal;
import net.minecraft.server.v1_8_R2.PathfinderGoalSelector;
import net.minecraft.server.v1_8_R2.WorldServer;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R2.entity.CraftEntity;
import org.bukkit.entity.Entity;

public final class SaoBlaze
extends EntityBlaze
implements SaoMob {
    private MobEquipment equipment;
    private boolean isSpecial = true;
    private FlyHelper helper;

    public SaoBlaze(net.minecraft.server.v1_8_R2.World world) {
        super(world);
        this.isSpecial = false;
    }

    public SaoBlaze(net.minecraft.server.v1_8_R2.World world, MobEquipment equipment) {
        super(world);
        MobType.enableAttackingMob((EntityCreature)this, this.goalSelector, this.targetSelector);
        this.equipment = equipment;
        this.goalSelector.a(4, (PathfinderGoal)new PathfinderGoalBlazeFireball(this));
    }

    public SaoBlaze(World world, MobEquipment mobEquipment) {
        this((net.minecraft.server.v1_8_R2.World)((CraftWorld)world).getHandle(), mobEquipment);
    }

    public boolean r(net.minecraft.server.v1_8_R2.Entity entity) {
        if (this.isSpecial) {
            MobExtender.attackAggresive((EntityCreature)this, entity, this.equipment);
        }
        return super.r(entity);
    }

    @Override
    public void spawn(Location location) {
        MobExtender.spawn((EntityInsentient)this, location);
        if (this.helper == null) {
            this.helper = new FlyHelper();
        }
        this.helper.spawnhigh = location.getY();
    }

    protected void E() {
        if (this.isSpecial && this.equipment.isFlying()) {
            MobExtender.flyPathFinderMob(this, this.equipment, this.helper);
        }
        super.E();
    }

    private class PathfinderGoalBlazeFireball
    extends PathfinderGoal {
        private EntityBlaze a;
        private int b;
        private int c;

        public PathfinderGoalBlazeFireball(EntityBlaze paramEntityBlaze) {
            this.a = paramEntityBlaze;
            this.a(3);
        }

        public boolean a() {
            EntityLiving localEntityLiving = this.a.getGoalTarget();
            if (localEntityLiving == null || !localEntityLiving.isAlive()) {
                return false;
            }
            return true;
        }

        public void c() {
            this.b = 0;
        }

        public void d() {
            this.a.a(false);
        }

        public void e() {
            --this.c;
            EntityLiving localEntityLiving = this.a.getGoalTarget();
            double d1 = this.a.h((net.minecraft.server.v1_8_R2.Entity)localEntityLiving);
            if (d1 < 4.0) {
                if (this.c <= 0) {
                    this.c = 20;
                    this.a.r((net.minecraft.server.v1_8_R2.Entity)localEntityLiving);
                }
                this.a.getControllerMove().a(localEntityLiving.locX, localEntityLiving.locY, localEntityLiving.locZ, 1.0);
            } else if (d1 < 256.0) {
                double d2 = localEntityLiving.locX - this.a.locX;
                double d3 = localEntityLiving.getBoundingBox().b + (double)(localEntityLiving.length / 2.0f) - (this.a.locY + (double)(this.a.length / 2.0f));
                double d4 = localEntityLiving.locZ - this.a.locZ;
                if (this.c <= 0) {
                    ++this.b;
                    if (this.b == 1) {
                        this.c = 60;
                        this.a.a(true);
                    } else if (this.b <= 4) {
                        this.c = 6;
                    } else {
                        this.c = 100;
                        this.b = 0;
                        this.a.a(false);
                    }
                    if (this.b > 1) {
                        float f = MathHelper.c((float)MathHelper.sqrt((double)d1)) * 0.5f;
                        this.a.world.a(null, 1009, new BlockPosition((int)this.a.locX, (int)this.a.locY, (int)this.a.locZ), 0);
                        int i = 0;
                        while (i < 1) {
                            EntitySmallFireball localEntitySmallFireball = new EntitySmallFireball(this.a.world, (EntityLiving)this.a, d2 + this.a.bc().nextGaussian() * (double)f, d3, d4 + this.a.bc().nextGaussian() * (double)f);
                            localEntitySmallFireball.locY = this.a.locY + (double)(this.a.length / 2.0f) + 0.5;
                            this.a.world.addEntity((net.minecraft.server.v1_8_R2.Entity)localEntitySmallFireball);
                            ++i;
                        }
                    }
                }
                this.a.getControllerLook().a((net.minecraft.server.v1_8_R2.Entity)localEntityLiving, 10.0f, 10.0f);
            } else {
                this.a.getNavigation().n();
                this.a.getControllerMove().a(localEntityLiving.locX, localEntityLiving.locY, localEntityLiving.locZ, 1.0);
            }
            super.e();
        }
    }

}

