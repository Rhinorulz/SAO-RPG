/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.command.ConsoleCommandSender
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.java.JavaPlugin
 */
package com.shynixn.thegreatswordartonlinerpg.resources.nms;

import com.shynixn.thegreatswordartonlinerpg.cardinal.CardinalException;
import com.shynixn.thegreatswordartonlinerpg.resources.enums.Priority;
import com.shynixn.thegreatswordartonlinerpg.resources.nms.MobRegistry;
import com.shynixn.thegreatswordartonlinerpg.resources.nms.NbtHandler;
import com.shynixn.thegreatswordartonlinerpg.resources.nms.ScreenMessenger;
import com.shynixn.thegreatswordartonlinerpg.resources.nms.skillapi.SkillAPICompatibility;
import com.shynixn.thegreatswordartonlinerpg.resources.nms.v1_8_R1.CustomMobFacility;
import com.shynixn.thegreatswordartonlinerpg.resources.nms.v1_8_R3.NbtManager;
import com.shynixn.thegreatswordartonlinerpg.resources.nms.v1_8_R3.UserTextFacility;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class NMSRegistry {
    private static NMSRegistry instanceNms;
    private JavaPlugin plugin;
    private SkillAPICompatibility skillAPICompatibility;

    private NMSRegistry(JavaPlugin plugin) {
        this.plugin = plugin;
        if (Bukkit.getPluginManager().getPlugin("SkillAPI") != null) {
            this.skillAPICompatibility = new SkillAPICompatibility();
        }
    }

    public static NMSRegistry getInstance(JavaPlugin plugin) {
        if (instanceNms == null) {
            instanceNms = new NMSRegistry(plugin);
        }
        return instanceNms;
    }

    public SkillAPICompatibility getSkillAPIRegistry() {
        return this.skillAPICompatibility;
    }

    public boolean isUsingSkillApi() {
        if (this.skillAPICompatibility == null) {
            return false;
        }
        return true;
    }

    public MobRegistry getMobRegistry() {
        if (this.getVersion() == SupportedVersion.v1_8_0) {
            return CustomMobFacility.Bat;
        }
        if (this.getVersion() == SupportedVersion.v1_8_3) {
            return com.shynixn.thegreatswordartonlinerpg.resources.nms.v1_8_R2.CustomMobFacility.Bat;
        }
        if (this.getVersion() == SupportedVersion.v1_8_6 || this.getVersion() == SupportedVersion.v1_8_7) {
            return com.shynixn.thegreatswordartonlinerpg.resources.nms.v1_8_R3.CustomMobFacility.Bat;
        }
        return null;
    }

    public void enable() {
        Bukkit.getServer().getConsoleSender().sendMessage((Object)ChatColor.WHITE + "====================================================");
        Bukkit.getServer().getConsoleSender().sendMessage((Object)ChatColor.WHITE + "##############  " + (Object)ChatColor.AQUA + "ENABLING CUSTOM MOBS" + (Object)ChatColor.WHITE + "  ##############");
        Bukkit.getServer().getConsoleSender().sendMessage((Object)ChatColor.WHITE + "====================================================");
        this.enableMobFactory(true);
    }

    public void disable() {
        this.enableMobFactory(false);
    }

    private void enableMobFactory(boolean enable) {
        if (this.getVersion() == SupportedVersion.v1_8_0) {
            if (enable) {
                CustomMobFacility.Bat.registerAll();
            } else {
                CustomMobFacility.Bat.unregisterAll();
            }
        } else if (this.getVersion() == SupportedVersion.v1_8_3) {
            if (enable) {
                com.shynixn.thegreatswordartonlinerpg.resources.nms.v1_8_R2.CustomMobFacility.Bat.registerAll();
            } else {
                com.shynixn.thegreatswordartonlinerpg.resources.nms.v1_8_R2.CustomMobFacility.Bat.unregisterAll();
            }
        } else if (this.getVersion() == SupportedVersion.v1_8_6 || this.getVersion() == SupportedVersion.v1_8_7) {
            if (enable) {
                com.shynixn.thegreatswordartonlinerpg.resources.nms.v1_8_R3.CustomMobFacility.Bat.registerAll();
            } else {
                com.shynixn.thegreatswordartonlinerpg.resources.nms.v1_8_R3.CustomMobFacility.Bat.unregisterAll();
            }
        }
    }

    public NbtHandler getNbtHandler() {
        if (this.getVersion() == SupportedVersion.v1_8_0) {
            return com.shynixn.thegreatswordartonlinerpg.resources.nms.v1_8_R1.NbtManager.getInstance();
        }
        if (this.getVersion() == SupportedVersion.v1_8_3) {
            return com.shynixn.thegreatswordartonlinerpg.resources.nms.v1_8_R2.NbtManager.getInstance();
        }
        if (this.getVersion() == SupportedVersion.v1_8_6 || this.getVersion() == SupportedVersion.v1_8_7) {
            return NbtManager.getInstance();
        }
        return null;
    }

    public ScreenMessenger getScreenMessenger() {
        if (this.getVersion() == SupportedVersion.v1_8_0) {
            return com.shynixn.thegreatswordartonlinerpg.resources.nms.v1_8_R1.UserTextFacility.getInstance(this.plugin);
        }
        if (this.getVersion() == SupportedVersion.v1_8_3) {
            return com.shynixn.thegreatswordartonlinerpg.resources.nms.v1_8_R2.UserTextFacility.getInstance(this.plugin);
        }
        if (this.getVersion() == SupportedVersion.v1_8_6 || this.getVersion() == SupportedVersion.v1_8_7) {
            return UserTextFacility.getInstance(this.plugin);
        }
        return null;
    }

    public String getSpigotVersion() {
        return Bukkit.getBukkitVersion().split("-")[0];
    }

    public void checkVersion() throws Exception {
        if (this.getVersion() == null) {
            throw new CardinalException("Server version and plugin version do not match", "Plugin cannot be loaded", "Install spigot/craftbukkit 1.8.0/1.8.3 or 1.8.6", Priority.HIGH);
        }
    }

    private SupportedVersion getVersion() {
        String[] parts = Bukkit.getBukkitVersion().split("-");
        if (parts[0].equals(SupportedVersion.v1_8_0.getId())) {
            return SupportedVersion.v1_8_0;
        }
        if (parts[0].equals(SupportedVersion.v1_8_3.getId())) {
            return SupportedVersion.v1_8_3;
        }
        if (parts[0].equals(SupportedVersion.v1_8_6.getId())) {
            return SupportedVersion.v1_8_6;
        }
        if (parts[0].equals(SupportedVersion.v1_8_7.getId())) {
            return SupportedVersion.v1_8_7;
        }
        return null;
    }

    public static enum SupportedVersion {
        v1_8_0("1.8"),
        v1_8_3("1.8.3"),
        v1_8_6("1.8.6"),
        v1_8_7("1.8.7"),
        vUnknown("unknown");
        
        private String id;

        private SupportedVersion(String id, int n2, String string2) {
            this.id = id;
        }

        public String getId() {
            return this.id;
        }
    }

    public static enum TextSpeed {
        VERYSLOW(5),
        SLOW(4),
        NORMAL(3),
        FAST(2),
        VERYFAST(1);
        
        private int number;

        private TextSpeed(String numer, int n2, int n3) {
            this.number = numer;
        }

        public int getNumber() {
            return this.number;
        }
    }

}

