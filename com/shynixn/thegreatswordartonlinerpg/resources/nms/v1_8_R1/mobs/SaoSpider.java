/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  net.minecraft.server.v1_8_R1.Entity
 *  net.minecraft.server.v1_8_R1.EntityCreature
 *  net.minecraft.server.v1_8_R1.EntityInsentient
 *  net.minecraft.server.v1_8_R1.EntitySpider
 *  net.minecraft.server.v1_8_R1.PathfinderGoalSelector
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
import com.shynixn.thegreatswordartonlinerpg.resources.nms.v1_8_R1.mobs.MobType;
import net.minecraft.server.v1_8_R1.Entity;
import net.minecraft.server.v1_8_R1.EntityCreature;
import net.minecraft.server.v1_8_R1.EntityInsentient;
import net.minecraft.server.v1_8_R1.EntitySpider;
import net.minecraft.server.v1_8_R1.PathfinderGoalSelector;
import net.minecraft.server.v1_8_R1.World;
import net.minecraft.server.v1_8_R1.WorldServer;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftEntity;

public final class SaoSpider
extends EntitySpider
implements SaoMob {
    private MobEquipment equipment;
    private boolean isSpecial = true;
    private FlyHelper helper;

    public SaoSpider(World world) {
        super(world);
        this.isSpecial = false;
    }

    public SaoSpider(World world, MobEquipment equipment) {
        super(world);
        MobType.enableSpiderMob((EntityCreature)this, this.goalSelector, this.targetSelector);
        this.equipment = equipment;
    }

    public SaoSpider(org.bukkit.World world, MobEquipment mobEquipment) {
        this((World)((CraftWorld)world).getHandle(), mobEquipment);
    }

    public boolean r(Entity entity) {
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
}

