/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandExecutor
 *  org.bukkit.command.CommandSender
 *  org.bukkit.command.PluginCommand
 *  org.bukkit.entity.Player
 *  org.bukkit.plugin.java.JavaPlugin
 */
package libraries.com.shynixn.utilities;

import libraries.com.shynixn.utilities.IBukkitCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class BukkitCommands
implements CommandExecutor,
IBukkitCommand {
    public BukkitCommands(String command, JavaPlugin plugin) {
        plugin.getCommand(command).setExecutor((CommandExecutor)this);
    }

    public final boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
        if (arg0 instanceof Player) {
            this.playerSendCommandEvent((Player)arg0, arg3);
        }
        return true;
    }
}

