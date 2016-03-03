/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  net.minecraft.server.v1_8_R1.Entity
 *  net.minecraft.server.v1_8_R1.EntityArmorStand
 *  net.minecraft.server.v1_8_R1.EntityPlayer
 *  net.minecraft.server.v1_8_R1.NBTTagCompound
 *  net.minecraft.server.v1_8_R1.Packet
 *  net.minecraft.server.v1_8_R1.PacketPlayOutAnimation
 *  net.minecraft.server.v1_8_R1.PacketPlayOutEntityTeleport
 *  net.minecraft.server.v1_8_R1.PlayerConnection
 *  org.bukkit.World
 *  org.bukkit.craftbukkit.v1_8_R1.entity.CraftArmorStand
 *  org.bukkit.craftbukkit.v1_8_R1.entity.CraftEntity
 *  org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer
 *  org.bukkit.entity.ArmorStand
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Player
 */
package com.shynixn.thegreatswordartonlinerpg.resources.nms.v1_8_R1;

import com.shynixn.thegreatswordartonlinerpg.resources.nms.NbtHandler;
import java.util.List;
import net.minecraft.server.v1_8_R1.Entity;
import net.minecraft.server.v1_8_R1.EntityArmorStand;
import net.minecraft.server.v1_8_R1.EntityPlayer;
import net.minecraft.server.v1_8_R1.NBTTagCompound;
import net.minecraft.server.v1_8_R1.Packet;
import net.minecraft.server.v1_8_R1.PacketPlayOutAnimation;
import net.minecraft.server.v1_8_R1.PacketPlayOutEntityTeleport;
import net.minecraft.server.v1_8_R1.PlayerConnection;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftArmorStand;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;

public final class NbtManager
implements NbtHandler {
    private static NbtManager instance;

    private NbtManager() {
    }

    public static NbtManager getInstance() {
        if (instance == null) {
            instance = new NbtManager();
        }
        return instance;
    }

    @Override
    public void prepareArmorStand(ArmorStand armorStand) {
        EntityArmorStand standford = ((CraftArmorStand)armorStand).getHandle();
        NBTTagCompound compound = new NBTTagCompound();
        compound.setBoolean("invulnerable", true);
        compound.setBoolean("Invisible", true);
        compound.setBoolean("NoGravity", true);
        compound.setBoolean("PersistenceRequired", true);
        standford.a(compound);
    }

    @Override
    public void damageArmorStand(ArmorStand armorStand) {
        PacketPlayOutAnimation animation = new PacketPlayOutAnimation((Entity)((CraftArmorStand)armorStand).getHandle(), 1);
        for (org.bukkit.entity.Entity entity : armorStand.getWorld().getEntities()) {
            if (!(entity instanceof Player)) continue;
            ((CraftPlayer)entity).getHandle().playerConnection.sendPacket((Packet)animation);
        }
    }

    @Override
    public void updateFlyPosition(Player player, ArmorStand armorStand) {
        PacketPlayOutEntityTeleport animation = new PacketPlayOutEntityTeleport(((CraftEntity)armorStand).getHandle());
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket((Packet)animation);
    }
}

