/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  org.apache.logging.log4j.Level
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Marker
 *  org.apache.logging.log4j.core.Filter
 *  org.apache.logging.log4j.core.Filter$Result
 *  org.apache.logging.log4j.core.LogEvent
 *  org.apache.logging.log4j.core.Logger
 *  org.apache.logging.log4j.message.Message
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Server
 *  org.bukkit.command.ConsoleCommandSender
 *  org.bukkit.entity.Player
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.PluginDescriptionFile
 *  org.bukkit.plugin.java.JavaPlugin
 *  org.bukkit.scheduler.BukkitScheduler
 */
package com.shynixn.thegreatswordartonlinerpg.cardinal;

import com.shynixn.thegreatswordartonlinerpg.cardinal.Cardinal;
import com.shynixn.thegreatswordartonlinerpg.cardinal.CardinalException;
import com.shynixn.thegreatswordartonlinerpg.resources.enums.CardinalSystem;
import com.shynixn.thegreatswordartonlinerpg.resources.enums.Priority;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import libraries.com.shynixn.utilities.BukkitUtilities;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.message.Message;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public final class CardinalLogger {
    private JavaPlugin plugin;
    private File folder;
    private List<CardinalException> exceptions = new ArrayList<CardinalException>();
    private List<String> flush = new ArrayList<String>();
    private boolean isRunning = false;

    protected CardinalLogger(JavaPlugin plugin) {
        this.plugin = plugin;
        this.folder = new File(plugin.getDataFolder() + "/resources/cardinal", "latest.log");
        this.runScheduler();
        if (this.folder.exists()) {
            this.folder.delete();
        }
        ((Logger)LogManager.getRootLogger()).addFilter(new Filter(){

            public Filter.Result filter(LogEvent event) {
                if (event.getMessage().toString().toLowerCase().contains("com.shynixn.thegreatswordartonline")) {
                    CardinalLogger.this.flush.add(event.getMessage().toString());
                }
                return null;
            }

            public /* varargs */ Filter.Result filter(Logger arg0, Level arg1, Marker arg2, String arg3, Object ... arg4) {
                return null;
            }

            public Filter.Result filter(Logger arg0, Level arg1, Marker arg2, Object arg3, Throwable arg4) {
                return null;
            }

            public Filter.Result filter(Logger arg0, Level arg1, Marker arg2, Message arg3, Throwable arg4) {
                return null;
            }

            public Filter.Result getOnMatch() {
                return null;
            }

            public Filter.Result getOnMismatch() {
                return null;
            }
        });
    }

    private void runScheduler() {
        if (!this.isRunning) {
            this.isRunning = true;
            this.plugin.getServer().getScheduler().scheduleSyncRepeatingTask((Plugin)this.plugin, new Runnable(){

                @Override
                public void run() {
                    for (String s : CardinalLogger.this.flush) {
                        CardinalLogger.this.writeAllLine(s);
                    }
                    CardinalLogger.this.flush.clear();
                }
            }, 0, 80);
        }
    }

    public void clearExceptions() {
        this.exceptions.clear();
    }

    private void writeAllLine(String message) {
        if (new File(this.plugin.getDataFolder() + "/resources", "cardinal").exists()) {
            BukkitUtilities.u().writeAddLine(this.folder, this.filterText(message));
        }
    }

    public void log(String message) {
        Bukkit.getServer().getConsoleSender().sendMessage(message);
        this.writeAllLine(message);
    }

    public void logHidden(String message) {
        BukkitUtilities.u().writeAddLine(this.folder, this.filterText(message));
    }

    public void logPlayer(Player player, String message) {
        player.sendMessage(message);
        this.writeAllLine("<" + player.getName() + "> " + this.filterText(message));
    }

    public CardinalException[] getExceptions() {
        return this.exceptions.toArray(new CardinalException[0]);
    }

    public void logCardinalStatus() {
        this.log((Object)ChatColor.GREEN + "====================================================");
        this.log((Object)ChatColor.GREEN + "                  CARDINAL REPORT                   ");
        this.log((Object)ChatColor.GREEN + "Server version: v" + Cardinal.getRegistry().getSpigotVersion());
        this.log((Object)ChatColor.GREEN + "Plugin version: v" + this.plugin.getDescription().getVersion());
        this.log((Object)ChatColor.GREEN + "Supported server versions: v1.8.0 - v1.8.7");
        this.log((Object)ChatColor.GREEN + "Selected gamesystem: " + Cardinal.getSettings().getSystem().name());
        this.log((Object)ChatColor.GREEN + "Using [SkillAPI]: " + Cardinal.getRegistry().isUsingSkillApi());
        this.log((Object)ChatColor.GREEN + "Amount of reported exceptions: " + CardinalException.getExceptionAmount());
        this.log((Object)ChatColor.GREEN + "====================================================");
    }

    public void logShutDownMessage(CardinalException exception) {
        this.log((Object)ChatColor.RED + "====================================================");
        this.log((Object)ChatColor.RED + "\t           CARDINAL SHUTDOWN                 ");
        this.log((Object)ChatColor.RED + "Reason ID: " + exception.getId());
        this.log((Object)ChatColor.RED + "Conclusion: " + "Cardinal does not work any longer.");
        this.log((Object)ChatColor.RED + "====================================================");
    }

    public void logException(CardinalException exception) {
        this.exceptions.add(exception);
        this.log((Object)ChatColor.RED + "====================================================");
        this.log((Object)ChatColor.RED + "                  CARDINAL EXCEPTION                ");
        this.log((Object)ChatColor.RED + "ID: " + exception.getId());
        this.log((Object)ChatColor.RED + "Reason: " + exception.getReason());
        this.log((Object)ChatColor.RED + "Conclusion: " + exception.getConclusion());
        this.log((Object)ChatColor.RED + "Priority: " + exception.getPriority().name().toLowerCase());
        this.log((Object)ChatColor.RED + "Solution: " + exception.getSolution());
        this.log((Object)ChatColor.RED + "====================================================");
    }

    public void logHeadLine(String name) {
        this.log((Object)ChatColor.WHITE + "====================================================");
        String txt = (Object)ChatColor.WHITE;
        int length = 26 - name.length() / 2 - 2;
        int i = 0;
        while (i < length) {
            txt = String.valueOf(txt) + "#";
            ++i;
        }
        txt = String.valueOf(txt) + " " + (Object)ChatColor.AQUA + name.toUpperCase() + (Object)ChatColor.WHITE + " ";
        while (txt.length() < 58) {
            txt = String.valueOf(txt) + "#";
        }
        this.log(txt);
        this.log((Object)ChatColor.WHITE + "====================================================");
    }

    private String filterText(String text) {
        String newText = "";
        int i = 0;
        while (i < text.length()) {
            if (text.charAt(i) == '\u00a7') {
                ++i;
            } else {
                newText = String.valueOf(newText) + text.charAt(i);
            }
            ++i;
        }
        return newText;
    }

}

