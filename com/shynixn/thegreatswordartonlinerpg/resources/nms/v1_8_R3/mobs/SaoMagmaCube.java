/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  net.minecraft.server.v1_8_R3.Entity
 *  net.minecraft.server.v1_8_R3.EntityInsentient
 *  net.minecraft.server.v1_8_R3.EntityMagmaCube
 *  net.minecraft.server.v1_8_R3.PathfinderGoalSelector
 *  net.minecraft.server.v1_8_R3.World
 *  net.minecraft.server.v1_8_R3.WorldServer
 *  org.bukkit.Location
 *  org.bukkit.World
 *  org.bukkit.craftbukkit.v1_8_R3.CraftWorld
 *  org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity
 *  org.bukkit.entity.Entity
 */
package com.shynixn.thegreatswordartonlinerpg.resources.nms.v1_8_R3.mobs;

import com.shynixn.thegreatswordartonlinerpg.gamesystems.mobs.MobEquipment;
import com.shynixn.thegreatswordartonlinerpg.resources.nms.FlyHelper;
import com.shynixn.thegreatswordartonlinerpg.resources.nms.SaoMob;
import com.shynixn.thegreatswordartonlinerpg.resources.nms.v1_8_R3.mobs.MobExtender;
import com.shynixn.thegreatswordartonlinerpg.resources.nms.v1_8_R3.mobs.MobType;
import net.minecraft.server.v1_8_R3.EntityInsentient;
import net.minecraft.server.v1_8_R3.EntityMagmaCube;
import net.minecraft.server.v1_8_R3.PathfinderGoalSelector;
import net.minecraft.server.v1_8_R3.World;
import net.minecraft.server.v1_8_R3.WorldServer;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.Entity;

public final class SaoMagmaCube
extends EntityMagmaCube
implements SaoMob {
    private MobEquipment equipment;
    private boolean isSpecial = true;
    private FlyHelper helper;

    public SaoMagmaCube(World world) {
        super(world);
        this.isSpecial = false;
    }

    public SaoMagmaCube(World world, MobEquipment equipment) {
        super(world);
        MobType.enableSlimeMob((EntityInsentient)this, this.goalSelector, this.targetSelector);
        this.setSize(equipment.getSlimeSize());
        this.equipment = equipment;
    }

    public SaoMagmaCube(org.bukkit.World world, MobEquipment mobEquipment) {
        this((World)((CraftWorld)world).getHandle(), mobEquipment);
    }

    public void collide(net.minecraft.server.v1_8_R3.Entity entity) {
        super.collide(entity);
        if (this.isSpecial && this.ci()) {
            MobExtender.attackSlime((EntityInsentient)this, entity, this.equipment);
        }
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

