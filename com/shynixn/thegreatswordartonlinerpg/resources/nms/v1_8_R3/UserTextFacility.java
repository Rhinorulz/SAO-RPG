/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  net.minecraft.server.v1_8_R3.EntityPlayer
 *  net.minecraft.server.v1_8_R3.IChatBaseComponent
 *  net.minecraft.server.v1_8_R3.IChatBaseComponent$ChatSerializer
 *  net.minecraft.server.v1_8_R3.Packet
 *  net.minecraft.server.v1_8_R3.PacketPlayOutChat
 *  net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter
 *  net.minecraft.server.v1_8_R3.PacketPlayOutTitle
 *  net.minecraft.server.v1_8_R3.PacketPlayOutTitle$EnumTitleAction
 *  net.minecraft.server.v1_8_R3.PlayerConnection
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Server
 *  org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer
 *  org.bukkit.entity.Player
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.java.JavaPlugin
 *  org.bukkit.scheduler.BukkitScheduler
 */
package com.shynixn.thegreatswordartonlinerpg.resources.nms.v1_8_R3;

import com.shynixn.thegreatswordartonlinerpg.resources.nms.NMSRegistry;
import com.shynixn.thegreatswordartonlinerpg.resources.nms.ScreenMessenger;
import java.lang.reflect.Field;
import java.util.HashMap;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public final class UserTextFacility
implements ScreenMessenger {
    private static UserTextFacility instance;
    private HashMap<Player, Integer> movingSchedulerId = new HashMap();
    private HashMap<Player, Integer> movingSchedulerCounter = new HashMap();
    private HashMap<Player, Integer> movingSchedulerJumper = new HashMap();
    private HashMap<Player, Integer> movingSchedulerShow = new HashMap();
    private HashMap<Player, Integer> percentSchedulerId = new HashMap();
    private HashMap<Player, Integer> percentSchedulerCounter = new HashMap();
    private HashMap<Player, Integer> percentSchedulerPercent = new HashMap();
    private HashMap<Player, Integer> percentSchedulerNumber = new HashMap();
    private JavaPlugin plugin;

    private UserTextFacility(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public static UserTextFacility getInstance(JavaPlugin plugin) {
        if (instance == null) {
            instance = new UserTextFacility(plugin);
        }
        return instance;
    }

    @Override
    public void setActionBar(Player player, String message) {
        IChatBaseComponent cbc = IChatBaseComponent.ChatSerializer.a((String)("{\"text\": \"" + message + "\"}"));
        PacketPlayOutChat ppoc = new PacketPlayOutChat(cbc, 2);
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket((Packet)ppoc);
    }

    @Override
    public void setPlayerTitle(Player player, String title, String subtitle, int fadeIn, int stay, int fadeOut) {
        CraftPlayer craftplayer = (CraftPlayer)player;
        PlayerConnection connection = craftplayer.getHandle().playerConnection;
        IChatBaseComponent titleJSON = IChatBaseComponent.ChatSerializer.a((String)("{'text': '" + ChatColor.translateAlternateColorCodes((char)'&', (String)title) + "'}"));
        IChatBaseComponent subtitleJSON = IChatBaseComponent.ChatSerializer.a((String)("{'text': '" + ChatColor.translateAlternateColorCodes((char)'&', (String)subtitle) + "'}"));
        PacketPlayOutTitle length = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TIMES, titleJSON, fadeIn, stay, fadeOut);
        PacketPlayOutTitle titlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, titleJSON, fadeIn, stay, fadeOut);
        PacketPlayOutTitle subtitlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, subtitleJSON, fadeIn, stay, fadeOut);
        if (!title.equals("")) {
            connection.sendPacket((Packet)titlePacket);
            connection.sendPacket((Packet)length);
        }
        if (!subtitle.equals("")) {
            connection.sendPacket((Packet)subtitlePacket);
        }
    }

    private void stopPlayerTitleMoving(Player player) {
        Bukkit.getScheduler().cancelTask(this.movingSchedulerId.get((Object)player).intValue());
        this.plugin.getServer().getScheduler().cancelTask(this.movingSchedulerId.get((Object)player).intValue());
    }

    private void stopPlayerSubTitlePercent(Player player) {
        Bukkit.getScheduler().cancelTask(this.percentSchedulerId.get((Object)player).intValue());
        this.plugin.getServer().getScheduler().cancelTask(this.percentSchedulerId.get((Object)player).intValue());
    }

    @Override
    public void setPlayerSubTitlePercent(final Player player, final String[] subtitles, final NMSRegistry.TextSpeed speed) {
        if (this.percentSchedulerId.containsKey((Object)player)) {
            this.stopPlayerSubTitlePercent(player);
        }
        this.percentSchedulerCounter.put(player, 0);
        this.percentSchedulerNumber.put(player, 0);
        this.percentSchedulerPercent.put(player, 0);
        this.percentSchedulerId.put(player, this.plugin.getServer().getScheduler().scheduleSyncRepeatingTask((Plugin)this.plugin, new Runnable(){

            @Override
            public void run() {
                instance.setPlayerTitle(player, "", String.valueOf(subtitles[(Integer)UserTextFacility.this.percentSchedulerNumber.get((Object)player)]) + UserTextFacility.this.percentSchedulerPercent.get((Object)player) + "%", 0, 20, 0);
                if ((Integer)UserTextFacility.this.percentSchedulerCounter.get((Object)player) % speed.getNumber() == 0) {
                    UserTextFacility.this.percentSchedulerPercent.put(player, (Integer)UserTextFacility.this.percentSchedulerPercent.get((Object)player) + 1);
                }
                if ((Integer)UserTextFacility.this.percentSchedulerPercent.get((Object)player) == 101) {
                    UserTextFacility.this.percentSchedulerPercent.put(player, 0);
                    UserTextFacility.this.percentSchedulerNumber.put(player, (Integer)UserTextFacility.this.percentSchedulerNumber.get((Object)player) + 1);
                    if (subtitles.length == (Integer)UserTextFacility.this.percentSchedulerNumber.get((Object)player)) {
                        UserTextFacility.this.stopPlayerSubTitlePercent(player);
                    }
                }
                if ((Integer)UserTextFacility.this.percentSchedulerCounter.get((Object)player) == 1000) {
                    UserTextFacility.this.percentSchedulerCounter.put(player, 0);
                }
                UserTextFacility.this.percentSchedulerCounter.put(player, (Integer)UserTextFacility.this.percentSchedulerCounter.get((Object)player) + 1);
            }
        }, 0, 1));
    }

    @Override
    public void setPlayerTitleMoving(final Player player, final String title, final int titleamount, final NMSRegistry.TextSpeed speed, final int showAmount) {
        if (this.movingSchedulerId.containsKey((Object)player)) {
            this.stopPlayerTitleMoving(player);
        }
        this.movingSchedulerCounter.put(player, 0);
        this.movingSchedulerJumper.put(player, 0);
        this.movingSchedulerShow.put(player, 0);
        this.movingSchedulerId.put(player, this.plugin.getServer().getScheduler().scheduleSyncRepeatingTask((Plugin)this.plugin, new Runnable(){

            private String getSpaces(int amount) {
                String space = "";
                int i = 0;
                while (i < amount) {
                    space = String.valueOf(space) + " ";
                    ++i;
                }
                return space;
            }

            @Override
            public void run() {
                String text = String.valueOf(this.getSpaces(titleamount - (Integer)UserTextFacility.this.movingSchedulerJumper.get((Object)player))) + title + this.getSpaces((Integer)UserTextFacility.this.movingSchedulerJumper.get((Object)player));
                instance.setPlayerTitle(player, text, "", 0, 20, 0);
                if ((Integer)UserTextFacility.this.movingSchedulerCounter.get((Object)player) % speed.getNumber() == 0) {
                    UserTextFacility.this.movingSchedulerJumper.put(player, (Integer)UserTextFacility.this.movingSchedulerJumper.get((Object)player) + 1);
                }
                if ((Integer)UserTextFacility.this.movingSchedulerJumper.get((Object)player) == titleamount) {
                    UserTextFacility.this.movingSchedulerJumper.put(player, 0);
                    UserTextFacility.this.movingSchedulerShow.put(player, (Integer)UserTextFacility.this.movingSchedulerShow.get((Object)player) + 1);
                }
                if (showAmount == (Integer)UserTextFacility.this.movingSchedulerShow.get((Object)player)) {
                    UserTextFacility.this.stopPlayerTitleMoving(player);
                }
                if ((Integer)UserTextFacility.this.movingSchedulerCounter.get((Object)player) == 1000) {
                    UserTextFacility.this.movingSchedulerCounter.put(player, 0);
                }
                UserTextFacility.this.movingSchedulerCounter.put(player, (Integer)UserTextFacility.this.movingSchedulerCounter.get((Object)player) + 1);
            }
        }, 0, 1));
    }

    @Override
    public void setHeaderAndFooter(Player player, String headertext, String footertext) {
        CraftPlayer craftplayer = (CraftPlayer)player;
        PlayerConnection connection = craftplayer.getHandle().playerConnection;
        IChatBaseComponent header = IChatBaseComponent.ChatSerializer.a((String)("{'color': '', 'text': '" + ChatColor.translateAlternateColorCodes((char)'&', (String)headertext) + "'}"));
        IChatBaseComponent footer = IChatBaseComponent.ChatSerializer.a((String)("{'color': '', 'text': '" + ChatColor.translateAlternateColorCodes((char)'&', (String)footertext) + "'}"));
        PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter();
        try {
            Field headerField = packet.getClass().getDeclaredField("a");
            headerField.setAccessible(true);
            headerField.set((Object)packet, (Object)header);
            headerField.setAccessible(!headerField.isAccessible());
            Field footerField = packet.getClass().getDeclaredField("b");
            footerField.setAccessible(true);
            footerField.set((Object)packet, (Object)footer);
            footerField.setAccessible(!footerField.isAccessible());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        connection.sendPacket((Packet)packet);
    }

}

