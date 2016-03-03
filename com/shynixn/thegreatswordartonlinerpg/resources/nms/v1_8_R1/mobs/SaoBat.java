/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  net.minecraft.server.v1_8_R1.AttributeInstance
 *  net.minecraft.server.v1_8_R1.AxisAlignedBB
 *  net.minecraft.server.v1_8_R1.Block
 *  net.minecraft.server.v1_8_R1.BlockPosition
 *  net.minecraft.server.v1_8_R1.DamageSource
 *  net.minecraft.server.v1_8_R1.DataWatcher
 *  net.minecraft.server.v1_8_R1.Entity
 *  net.minecraft.server.v1_8_R1.EntityCreature
 *  net.minecraft.server.v1_8_R1.EntityHuman
 *  net.minecraft.server.v1_8_R1.EntityInsentient
 *  net.minecraft.server.v1_8_R1.GenericAttributes
 *  net.minecraft.server.v1_8_R1.IAnimal
 *  net.minecraft.server.v1_8_R1.IAttribute
 *  net.minecraft.server.v1_8_R1.MathHelper
 *  net.minecraft.server.v1_8_R1.NBTTagCompound
 *  net.minecraft.server.v1_8_R1.PathfinderGoalSelector
 *  net.minecraft.server.v1_8_R1.World
 *  net.minecraft.server.v1_8_R1.WorldServer
 *  org.bukkit.Location
 *  org.bukkit.World
 *  org.bukkit.craftbukkit.v1_8_R1.CraftWorld
 *  org.bukkit.craftbukkit.v1_8_R1.entity.CraftEntity
 *  org.bukkit.entity.Entity
 *  org.bukkit.util.Vector
 */
package com.shynixn.thegreatswordartonlinerpg.resources.nms.v1_8_R1.mobs;

import com.shynixn.thegreatswordartonlinerpg.gamesystems.mobs.MobEquipment;
import com.shynixn.thegreatswordartonlinerpg.resources.nms.FlyHelper;
import com.shynixn.thegreatswordartonlinerpg.resources.nms.SaoMob;
import com.shynixn.thegreatswordartonlinerpg.resources.nms.v1_8_R1.mobs.MobExtender;
import com.shynixn.thegreatswordartonlinerpg.resources.nms.v1_8_R1.mobs.MobType;
import java.util.Calendar;
import java.util.Random;
import net.minecraft.server.v1_8_R1.AttributeInstance;
import net.minecraft.server.v1_8_R1.AxisAlignedBB;
import net.minecraft.server.v1_8_R1.Block;
import net.minecraft.server.v1_8_R1.BlockPosition;
import net.minecraft.server.v1_8_R1.DamageSource;
import net.minecraft.server.v1_8_R1.DataWatcher;
import net.minecraft.server.v1_8_R1.EntityCreature;
import net.minecraft.server.v1_8_R1.EntityHuman;
import net.minecraft.server.v1_8_R1.EntityInsentient;
import net.minecraft.server.v1_8_R1.GenericAttributes;
import net.minecraft.server.v1_8_R1.IAnimal;
import net.minecraft.server.v1_8_R1.IAttribute;
import net.minecraft.server.v1_8_R1.MathHelper;
import net.minecraft.server.v1_8_R1.NBTTagCompound;
import net.minecraft.server.v1_8_R1.PathfinderGoalSelector;
import net.minecraft.server.v1_8_R1.World;
import net.minecraft.server.v1_8_R1.WorldServer;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

public final class SaoBat
extends EntityCreature
implements IAnimal,
SaoMob {
    private MobEquipment equipment;
    private boolean isSpecial = true;
    private FlyHelper helper;

    public SaoBat(World world) {
        super(world);
        this.isSpecial = false;
    }

    public SaoBat(World world, MobEquipment equipment) {
        super(world);
        MobType.enableAttackingMob(this, this.goalSelector, this.targetSelector);
        this.equipment = equipment;
        this.setAsleep(false);
    }

    public SaoBat(org.bukkit.World world, MobEquipment mobEquipment) {
        this((World)((CraftWorld)world).getHandle(), mobEquipment);
    }

    public boolean r(net.minecraft.server.v1_8_R1.Entity entity) {
        if (this.isSpecial && !this.equipment.isFlying()) {
            MobExtender.attackPassive(this, entity, this.equipment);
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

    protected void h() {
        super.h();
        this.datawatcher.a(16, (Object)new Byte(0));
    }

    protected float bA() {
        return 0.1f;
    }

    protected float bB() {
        return super.bB() * 0.95f;
    }

    public boolean ca() {
        return false;
    }

    protected boolean a(EntityHuman paramEntityHuman) {
        return false;
    }

    protected String z() {
        if (this.isAsleep() && this.random.nextInt(4) != 0) {
            return null;
        }
        return "mob.bat.idle";
    }

    protected String bn() {
        return "mob.bat.hurt";
    }

    protected String bo() {
        return "mob.bat.death";
    }

    public boolean ae() {
        return false;
    }

    protected void s(net.minecraft.server.v1_8_R1.Entity paramEntity) {
    }

    protected void bK() {
    }

    protected void aW() {
        super.aW();
        this.getAttributeInstance(GenericAttributes.maxHealth).setValue(6.0);
    }

    public boolean isAsleep() {
        if ((this.datawatcher.getByte(16) & 1) != 0) {
            return true;
        }
        return false;
    }

    public void setAsleep(boolean paramBoolean) {
        byte i = this.datawatcher.getByte(16);
        if (paramBoolean) {
            this.datawatcher.watch(16, (Object)Byte.valueOf((byte)(i | 1)));
        } else {
            this.datawatcher.watch(16, (Object)Byte.valueOf((byte)(i & -2)));
        }
    }

    public void s_() {
        super.s_();
        if (this.isAsleep()) {
            this.motZ = 0.0;
            this.motY = 0.0;
            this.motX = 0.0;
            this.locY = (double)MathHelper.floor((double)this.locY) + 1.0 - (double)this.length;
        } else {
            this.motY *= 0.6000000238418579;
        }
    }

    protected void E() {
        if (this.isSpecial) {
            if (this.equipment.isFlying()) {
                MobExtender.flyPathFinderMob(this, this.equipment, this.helper);
            } else {
                if (this.helper.counter >= 15) {
                    Vector vector = this.getBukkitEntity().getVelocity();
                    this.getBukkitEntity().setVelocity(vector.add(new Vector(0.0, 0.1, 0.0)));
                }
                if (this.helper.counter == 20) {
                    this.helper.counter = -1;
                }
                ++this.helper.counter;
            }
        }
    }

    protected boolean r_() {
        return false;
    }

    public void e(float paramFloat1, float paramFloat2) {
    }

    protected void a(double paramDouble, boolean paramBoolean, Block paramBlock, BlockPosition paramBlockPosition) {
    }

    public boolean aH() {
        return true;
    }

    public boolean damageEntity(DamageSource paramDamageSource, float paramFloat) {
        if (this.isInvulnerable(paramDamageSource)) {
            return false;
        }
        if (!this.world.isStatic && this.isAsleep()) {
            this.setAsleep(false);
        }
        return super.damageEntity(paramDamageSource, paramFloat);
    }

    public void a(NBTTagCompound paramNBTTagCompound) {
        super.a(paramNBTTagCompound);
        this.datawatcher.watch(16, (Object)Byte.valueOf(paramNBTTagCompound.getByte("BatFlags")));
    }

    public void b(NBTTagCompound paramNBTTagCompound) {
        super.b(paramNBTTagCompound);
        paramNBTTagCompound.setByte("BatFlags", this.datawatcher.getByte(16));
    }

    public boolean bQ() {
        BlockPosition localBlockPosition = new BlockPosition(this.locX, this.getBoundingBox().b, this.locZ);
        if (localBlockPosition.getY() >= 63) {
            return false;
        }
        int i = this.world.getLightLevel(localBlockPosition);
        int j = 4;
        if (this.a(this.world.Y())) {
            j = 7;
        } else if (this.random.nextBoolean()) {
            return false;
        }
        if (i > this.random.nextInt(j)) {
            return false;
        }
        return super.bQ();
    }

    private boolean a(Calendar paramCalendar) {
        if (!(paramCalendar.get(2) + 1 == 10 && paramCalendar.get(5) >= 20 || paramCalendar.get(2) + 1 == 11 && paramCalendar.get(5) <= 3)) {
            return false;
        }
        return true;
    }

    public float getHeadHeight() {
        return this.length / 2.0f;
    }
}

