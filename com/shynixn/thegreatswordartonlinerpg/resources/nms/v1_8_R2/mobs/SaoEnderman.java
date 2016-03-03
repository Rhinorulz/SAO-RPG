/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  net.minecraft.server.v1_8_R2.Entity
 *  net.minecraft.server.v1_8_R2.EntityCreature
 *  net.minecraft.server.v1_8_R2.EntityEnderman
 *  net.minecraft.server.v1_8_R2.EntityInsentient
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
import com.shynixn.thegreatswordartonlinerpg.resources.nms.SaoMob;
import com.shynixn.thegreatswordartonlinerpg.resources.nms.v1_8_R2.mobs.MobExtender;
import com.shynixn.thegreatswordartonlinerpg.resources.nms.v1_8_R2.mobs.MobType;
import net.minecraft.server.v1_8_R2.EntityCreature;
import net.minecraft.server.v1_8_R2.EntityEnderman;
import net.minecraft.server.v1_8_R2.EntityInsentient;
import net.minecraft.server.v1_8_R2.PathfinderGoalSelector;
import net.minecraft.server.v1_8_R2.WorldServer;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R2.entity.CraftEntity;
import org.bukkit.entity.Entity;

public final class SaoEnderman
extends EntityEnderman
implements SaoMob {
    private MobEquipment equipment;
    private boolean isSpecial = true;

    public SaoEnderman(net.minecraft.server.v1_8_R2.World world) {
        super(world);
        this.isSpecial = false;
    }

    public SaoEnderman(net.minecraft.server.v1_8_R2.World world, MobEquipment equipment) {
        super(world);
        MobType.enableAttackingMob((EntityCreature)this, this.goalSelector, this.targetSelector);
        this.equipment = equipment;
    }

    public SaoEnderman(World world, MobEquipment mobEquipment) {
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
    }
}

