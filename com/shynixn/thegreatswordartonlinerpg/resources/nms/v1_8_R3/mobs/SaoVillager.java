/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  net.minecraft.server.v1_8_R3.Entity
 *  net.minecraft.server.v1_8_R3.EntityCreature
 *  net.minecraft.server.v1_8_R3.EntityInsentient
 *  net.minecraft.server.v1_8_R3.EntityVillager
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
import com.shynixn.thegreatswordartonlinerpg.resources.enums.VillagerProfession;
import com.shynixn.thegreatswordartonlinerpg.resources.enums.VillagerType;
import com.shynixn.thegreatswordartonlinerpg.resources.nms.FlyHelper;
import com.shynixn.thegreatswordartonlinerpg.resources.nms.SaoMob;
import com.shynixn.thegreatswordartonlinerpg.resources.nms.v1_8_R3.mobs.MobExtender;
import com.shynixn.thegreatswordartonlinerpg.resources.nms.v1_8_R3.mobs.MobType;
import net.minecraft.server.v1_8_R3.EntityCreature;
import net.minecraft.server.v1_8_R3.EntityInsentient;
import net.minecraft.server.v1_8_R3.EntityVillager;
import net.minecraft.server.v1_8_R3.PathfinderGoalSelector;
import net.minecraft.server.v1_8_R3.World;
import net.minecraft.server.v1_8_R3.WorldServer;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.Entity;

public final class SaoVillager
extends EntityVillager
implements SaoMob {
    private MobEquipment equipment;
    private boolean isSpecial = true;
    private FlyHelper helper;

    public SaoVillager(World world) {
        super(world);
        this.isSpecial = false;
    }

    public SaoVillager(World world, MobEquipment equipment) {
        super(world);
        if (equipment.getVillagerType() != VillagerType.PEASANT) {
            if (equipment.getVillagerType() == VillagerType.EWARRIOR) {
                MobType.enableAttackingMob((EntityCreature)this, this.goalSelector, this.targetSelector);
            } else if (equipment.getVillagerType() == VillagerType.WARRIOR) {
                MobType.enableDefendingMob((EntityCreature)this, this.goalSelector, this.targetSelector);
            }
        }
        this.equipment = equipment;
        this.setProfession(equipment.getProfession().getNumber());
        if (equipment.isBaby()) {
            this.setAge(-100000);
        }
    }

    public SaoVillager(org.bukkit.World world, MobEquipment mobEquipment) {
        this((World)((CraftWorld)world).getHandle(), mobEquipment);
    }

    public boolean r(net.minecraft.server.v1_8_R3.Entity entity) {
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

