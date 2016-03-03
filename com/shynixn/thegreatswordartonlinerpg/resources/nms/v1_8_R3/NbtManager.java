/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  net.minecraft.server.v1_8_R3.Entity
 *  net.minecraft.server.v1_8_R3.EntityArmorStand
 *  net.minecraft.server.v1_8_R3.EntityPlayer
 *  net.minecraft.server.v1_8_R3.NBTTagCompound
 *  net.minecraft.server.v1_8_R3.Packet
 *  net.minecraft.server.v1_8_R3.PacketPlayOutAnimation
 *  net.minecraft.server.v1_8_R3.PacketPlayOutEntityTeleport
 *  net.minecraft.server.v1_8_R3.PlayerConnection
 *  org.bukkit.World
 *  org.bukkit.craftbukkit.v1_8_R3.entity.CraftArmorStand
 *  org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity
 *  org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer
 *  org.bukkit.entity.ArmorStand
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Player
 */
package com.shynixn.thegreatswordartonlinerpg.resources.nms.v1_8_R3;

import com.shynixn.thegreatswordartonlinerpg.resources.nms.NbtHandler;
import java.util.List;
import net.minecraft.server.v1_8_R3.EntityArmorStand;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutAnimation;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityTeleport;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftArmorStand;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
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
        PacketPlayOutAnimation animation = new PacketPlayOutAnimation((net.minecraft.server.v1_8_R3.Entity)((CraftArmorStand)armorStand).getHandle(), 1);
        for (Entity entity : armorStand.getWorld().getEntities()) {
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

