/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  net.minecraft.server.v1_8_R2.EntityInsentient
 *  net.minecraft.server.v1_8_R2.EntityWither
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
import net.minecraft.server.v1_8_R2.EntityInsentient;
import net.minecraft.server.v1_8_R2.EntityWither;
import net.minecraft.server.v1_8_R2.WorldServer;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R2.entity.CraftEntity;
import org.bukkit.entity.Entity;

public final class SaoWither
extends EntityWither
implements SaoMob {
    public SaoWither(net.minecraft.server.v1_8_R2.World world) {
        super(world);
    }

    public SaoWither(net.minecraft.server.v1_8_R2.World world, MobEquipment equipment) {
        super(world);
    }

    public SaoWither(World world, MobEquipment mobEquipment) {
        this((net.minecraft.server.v1_8_R2.World)((CraftWorld)world).getHandle(), mobEquipment);
    }

    @Override
    public void spawn(Location location) {
        MobExtender.spawn((EntityInsentient)this, location);
    }
}

