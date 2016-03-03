/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  net.minecraft.server.v1_8_R1.EntityGhast
 *  net.minecraft.server.v1_8_R1.EntityInsentient
 *  net.minecraft.server.v1_8_R1.World
 *  net.minecraft.server.v1_8_R1.WorldServer
 *  org.bukkit.Location
 *  org.bukkit.World
 *  org.bukkit.craftbukkit.v1_8_R1.CraftWorld
 *  org.bukkit.craftbukkit.v1_8_R1.entity.CraftEntity
 *  org.bukkit.entity.Entity
 */
package com.shynixn.thegreatswordartonlinerpg.resources.nms.v1_8_R1.mobs;

import com.shynixn.thegreatswordartonlinerpg.gamesystems.mobs.MobEquipment;
import com.shynixn.thegreatswordartonlinerpg.resources.nms.FlyHelper;
import com.shynixn.thegreatswordartonlinerpg.resources.nms.SaoMob;
import com.shynixn.thegreatswordartonlinerpg.resources.nms.v1_8_R1.mobs.MobExtender;
import net.minecraft.server.v1_8_R1.EntityGhast;
import net.minecraft.server.v1_8_R1.EntityInsentient;
import net.minecraft.server.v1_8_R1.World;
import net.minecraft.server.v1_8_R1.WorldServer;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftEntity;
import org.bukkit.entity.Entity;

public final class SaoGhast
extends EntityGhast
implements SaoMob {
    private boolean isSpecial = true;
    private MobEquipment equipment;
    private FlyHelper helper;

    public SaoGhast(World world) {
        super(world);
        this.isSpecial = false;
    }

    public SaoGhast(World world, MobEquipment equipment) {
        super(world);
    }

    public SaoGhast(org.bukkit.World world, MobEquipment mobEquipment) {
        this((World)((CraftWorld)world).getHandle(), mobEquipment);
        this.equipment = mobEquipment;
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

