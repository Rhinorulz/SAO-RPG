/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  net.minecraft.server.v1_8_R2.Entity
 *  net.minecraft.server.v1_8_R2.EntityCreature
 *  net.minecraft.server.v1_8_R2.EntityInsentient
 *  net.minecraft.server.v1_8_R2.EntitySheep
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
import net.minecraft.server.v1_8_R2.EntityCreature;
import net.minecraft.server.v1_8_R2.EntityInsentient;
import net.minecraft.server.v1_8_R2.EntitySheep;
import net.minecraft.server.v1_8_R2.PathfinderGoalSelector;
import net.minecraft.server.v1_8_R2.WorldServer;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R2.entity.CraftEntity;
import org.bukkit.entity.Entity;

public final class SaoSheep
extends EntitySheep
implements SaoMob {
    private MobEquipment equipment;
    private boolean isSpecial = true;
    private FlyHelper helper;

    public SaoSheep(net.minecraft.server.v1_8_R2.World world) {
        super(world);
        this.isSpecial = false;
    }

    public SaoSheep(net.minecraft.server.v1_8_R2.World world, MobEquipment equipment) {
        super(world);
        MobType.enableAttackingMob((EntityCreature)this, this.goalSelector, this.targetSelector);
        this.equipment = equipment;
        if (equipment.isBaby()) {
            this.setAge(-100000);
        }
    }

    public SaoSheep(World world, MobEquipment mobEquipment) {
        this((net.minecraft.server.v1_8_R2.World)((CraftWorld)world).getHandle(), mobEquipment);
    }

    public boolean r(net.minecraft.server.v1_8_R2.Entity entity) {
        if (this.isSpecial) {
            MobExtender.attackPassive((EntityCreature)this, entity, this.equipment);
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
}

