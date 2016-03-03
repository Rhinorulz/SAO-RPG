/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandSender
 *  org.bukkit.configuration.file.FileConfiguration
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.player.PlayerJoinEvent
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.java.JavaPlugin
 */
package libraries.com.shynixn.utilities;

import java.io.File;
import java.io.IOException;
import libraries.McStats.Metrics;
import libraries.net.gravitydevelopment.updater.Updater;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class BukkitPlugin
extends JavaPlugin {
    private boolean isUpdateAvailable = false;
    private boolean isUpdaterRegistered = false;
    private int id;
    private String newUpdateMessage;
    private String noUpdateMessage;

    public void onEnable() {
    }

    public void onDisable() {
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        return true;
    }

    public final void registerUpdater(String newUpdateMessage, String noUpdateMessage, int id) {
        if (!this.isUpdaterRegistered) {
            this.id = id;
            this.noUpdateMessage = noUpdateMessage;
            this.newUpdateMessage = newUpdateMessage;
            Bukkit.getPluginManager().registerEvents((Listener)new BukkitUpdaterListener(this, null), (Plugin)this);
            this.isUpdaterRegistered = true;
        }
    }

    public final void checkfPluginMetrics() {
        try {
            Metrics metrics = new Metrics((Plugin)this);
            metrics.start();
        }
        catch (IOException metrics) {
            // empty catch block
        }
    }

    public final void checkfForUpdates() {
        try {
            if (this.getConfig().getBoolean("auto-update")) {
                if (!this.isUpdaterRegistered) {
                    throw new IllegalAccessException("You have to register the updater first!.");
                }
                Updater updater = new Updater((Plugin)this, this.id, this.getFile(), Updater.UpdateType.NO_DOWNLOAD, true);
                if (updater.getResult() == Updater.UpdateResult.UPDATE_AVAILABLE) {
                    this.isUpdateAvailable = true;
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class BukkitUpdaterListener
    implements Listener {
        final /* synthetic */ BukkitPlugin this$0;

        private BukkitUpdaterListener(BukkitPlugin bukkitPlugin) {
            this.this$0 = bukkitPlugin;
        }

        @EventHandler
        public void onPlayerJoinEvent(PlayerJoinEvent event) {
            if (event.getPlayer().isOp() && this.this$0.getConfig().getBoolean("auto-update")) {
                if (this.this$0.isUpdateAvailable) {
                    event.getPlayer().sendMessage(this.this$0.newUpdateMessage);
                } else {
                    event.getPlayer().sendMessage(this.this$0.noUpdateMessage);
                }
            }
        }

        /* synthetic */ BukkitUpdaterListener(BukkitPlugin bukkitPlugin, BukkitUpdaterListener bukkitUpdaterListener) {
            BukkitUpdaterListener bukkitUpdaterListener2;
            bukkitUpdaterListener2(bukkitPlugin);
        }
    }

}

