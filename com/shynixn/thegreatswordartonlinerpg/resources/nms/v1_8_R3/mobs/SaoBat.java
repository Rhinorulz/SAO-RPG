/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  net.minecraft.server.v1_8_R3.AttributeInstance
 *  net.minecraft.server.v1_8_R3.AxisAlignedBB
 *  net.minecraft.server.v1_8_R3.BlockPosition
 *  net.minecraft.server.v1_8_R3.DamageSource
 *  net.minecraft.server.v1_8_R3.DataWatcher
 *  net.minecraft.server.v1_8_R3.Entity
 *  net.minecraft.server.v1_8_R3.EntityCreature
 *  net.minecraft.server.v1_8_R3.EntityInsentient
 *  net.minecraft.server.v1_8_R3.GenericAttributes
 *  net.minecraft.server.v1_8_R3.IAnimal
 *  net.minecraft.server.v1_8_R3.IAttribute
 *  net.minecraft.server.v1_8_R3.MathHelper
 *  net.minecraft.server.v1_8_R3.NBTTagCompound
 *  net.minecraft.server.v1_8_R3.PathfinderGoalSelector
 *  net.minecraft.server.v1_8_R3.World
 *  net.minecraft.server.v1_8_R3.WorldServer
 *  org.bukkit.Location
 *  org.bukkit.World
 *  org.bukkit.craftbukkit.v1_8_R3.CraftWorld
 *  org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity
 *  org.bukkit.entity.Entity
 *  org.bukkit.util.Vector
 */
package com.shynixn.thegreatswordartonlinerpg.resources.nms.v1_8_R3.mobs;

import com.shynixn.thegreatswordartonlinerpg.gamesystems.mobs.MobEquipment;
import com.shynixn.thegreatswordartonlinerpg.resources.nms.FlyHelper;
import com.shynixn.thegreatswordartonlinerpg.resources.nms.SaoMob;
import com.shynixn.thegreatswordartonlinerpg.resources.nms.v1_8_R3.mobs.MobExtender;
import com.shynixn.thegreatswordartonlinerpg.resources.nms.v1_8_R3.mobs.MobType;
import java.util.Calendar;
import java.util.Random;
import net.minecraft.server.v1_8_R3.AttributeInstance;
import net.minecraft.server.v1_8_R3.AxisAlignedBB;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.DamageSource;
import net.minecraft.server.v1_8_R3.DataWatcher;
import net.minecraft.server.v1_8_R3.EntityCreature;
import net.minecraft.server.v1_8_R3.EntityInsentient;
import net.minecraft.server.v1_8_R3.GenericAttributes;
import net.minecraft.server.v1_8_R3.IAnimal;
import net.minecraft.server.v1_8_R3.IAttribute;
import net.minecraft.server.v1_8_R3.MathHelper;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.PathfinderGoalSelector;
import net.minecraft.server.v1_8_R3.World;
import net.minecraft.server.v1_8_R3.WorldServer;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

public class SaoBat
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

    public boolean r(net.minecraft.server.v1_8_R3.Entity entity) {
        if (this.isSpecial) {
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

    protected float bB() {
        return 0.1f;
    }

    protected float bC() {
        return super.bC() * 0.95f;
    }

    protected String z() {
        if (this.isAsleep() && this.random.nextInt(4) != 0) {
            return null;
        }
        return "mob.bat.idle";
    }

    protected String bo() {
        return "mob.bat.hurt";
    }

    protected String bp() {
        return "mob.bat.death";
    }

    protected void initAttributes() {
        super.initAttributes();
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

    public void t_() {
        super.t_();
        if (this.isAsleep()) {
            this.motZ = 0.0;
            this.motY = 0.0;
            this.motX = 0.0;
            this.locY = (double)MathHelper.floor((double)this.locY) + 1.0 - (double)this.length;
        } else {
            this.motY *= 0.6000000238418579;
        }
    }

    public boolean damageEntity(DamageSource paramDamageSource, float paramFloat) {
        if (this.isInvulnerable(paramDamageSource)) {
            return false;
        }
        if (!this.world.isClientSide && this.isAsleep()) {
            this.setAsleep(false);
        }
        return super.damageEntity(paramDamageSource, paramFloat);
    }

    public void a(NBTTagCompound paramNBTTagCompound) {
        super.a(paramNBTTagCompound);
        this.datawatcher.watch(16, (Object)Byte.valueOf(paramNBTTagCompound.getByte("BatFlags")));
    }

    public boolean bR() {
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
        return super.bR();
    }

    public void b(NBTTagCompound paramNBTTagCompound) {
        super.b(paramNBTTagCompound);
        paramNBTTagCompound.setByte("BatFlags", this.datawatcher.getByte(16));
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
}

