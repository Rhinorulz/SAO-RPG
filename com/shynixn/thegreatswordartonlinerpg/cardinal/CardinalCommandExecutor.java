/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.World
 *  org.bukkit.entity.Player
 *  org.bukkit.plugin.java.JavaPlugin
 */
package com.shynixn.thegreatswordartonlinerpg.cardinal;

import com.shynixn.thegreatswordartonlinerpg.cardinal.Cardinal;
import com.shynixn.thegreatswordartonlinerpg.cardinal.CardinalException;
import com.shynixn.thegreatswordartonlinerpg.cardinal.CardinalLanguage;
import java.util.List;
import libraries.com.shynixn.utilities.BukkitChatColor;
import libraries.com.shynixn.utilities.BukkitCommands;
import libraries.com.shynixn.utilities.BukkitUtilities;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class CardinalCommandExecutor
extends BukkitCommands {
    public CardinalCommandExecutor(JavaPlugin plugin) {
        super("cardinal", plugin);
    }

    @Override
    public void playerSendCommandEvent(Player player, String[] args) {
        if (args.length == 2 && args[0].equalsIgnoreCase("convert")) {
            this.convertSaoWorldCommand(player, args[1]);
        } else if (args.length == 1 && args[0].equalsIgnoreCase("worlds")) {
            this.printWorldsCommand(player);
        } else if (args.length == 2 && args[0].equalsIgnoreCase("show") && args[1].equalsIgnoreCase("exceptions")) {
            this.showExceptionsCommand(player);
        } else if (args.length == 2 && args[0].equalsIgnoreCase("show") && args[1].equalsIgnoreCase("blacklist")) {
            this.showBlacklistCommand(player);
        } else if (args.length == 2 && args[0].equalsIgnoreCase("clear") && args[1].equalsIgnoreCase("exceptions")) {
            this.clearExceptionsCommand(player);
        } else if (args.length == 2 && args[0].equalsIgnoreCase("clear") && args[1].equalsIgnoreCase("blacklist")) {
            this.clearBlackListCommand(player);
        } else {
            player.sendMessage("");
            player.sendMessage(String.valueOf(Cardinal.TITLE) + ": Cardinal");
            player.sendMessage("");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/cardinal show blacklist");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/cardinal show exceptions");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/cardinal clear blacklist");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/cardinal clear exceptions");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/cardinal convert <world>");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/cardinal worlds");
            player.sendMessage("");
            player.sendMessage((Object)((Object)BukkitChatColor.GOLD) + (Object)ChatColor.UNDERLINE + "                           \u250c1/1\u2510                            ");
            player.sendMessage("");
        }
    }

    private void clearBlackListCommand(Player player) {
        Cardinal.getGenericSystem().clearBlackList();
        Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.Cardinal.CLEAR_BLACKLIST);
    }

    private void clearExceptionsCommand(Player player) {
        Cardinal.getLogger().clearExceptions();
        Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.Cardinal.CLEAR_EXCEPTIONS);
    }

    private void showBlacklistCommand(Player player) {
        player.sendMessage((Object)((Object)BukkitChatColor.RED) + CardinalLanguage.Cardinal.LIST_BLACKLIST);
        boolean switcher = false;
        String[] arrstring = Cardinal.getGenericSystem().getBlackListPlayers();
        int n = arrstring.length;
        int n2 = 0;
        while (n2 < n) {
            String s = arrstring[n2];
            if (!switcher) {
                player.sendMessage((Object)ChatColor.RED + s);
            } else {
                player.sendMessage((Object)ChatColor.DARK_RED + s);
            }
            switcher = !switcher;
            ++n2;
        }
    }

    private void showExceptionsCommand(Player player) {
        player.sendMessage((Object)((Object)BukkitChatColor.RED) + CardinalLanguage.Cardinal.LIST_EXCEPTIONS);
        boolean switcher = false;
        CardinalException[] arrcardinalException = Cardinal.getLogger().getExceptions();
        int n = arrcardinalException.length;
        int n2 = 0;
        while (n2 < n) {
            CardinalException exception = arrcardinalException[n2];
            if (!switcher) {
                player.sendMessage((Object)ChatColor.RED + exception.getId() + ": " + exception.getReason() + "; " + exception.getSolution());
            } else {
                player.sendMessage((Object)ChatColor.DARK_RED + exception.getId() + ": " + exception.getReason() + "; " + exception.getSolution());
            }
            switcher = !switcher;
            ++n2;
        }
    }

    private void convertSaoWorldCommand(Player player, String worldname) {
        if (BukkitUtilities.u().getWorldbyName(worldname) == null) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.fix(CardinalLanguage.CommandExecutor.CARDINAL_WORLD_NOT_FOUND)[0] + worldname + CardinalLanguage.fix(CardinalLanguage.CommandExecutor.CARDINAL_WORLD_NOT_FOUND)[1]);
        } else if (Cardinal.getSettings().getWorlds().contains(worldname)) {
            Cardinal.getSettings().removeWorld(worldname);
            player.sendMessage(String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.fix(CardinalLanguage.CommandExecutor.CARDINAL_CONVERT_STANDARD_WORLD)[0] + worldname + CardinalLanguage.fix(CardinalLanguage.CommandExecutor.CARDINAL_CONVERT_STANDARD_WORLD)[1]);
        } else {
            Cardinal.getSettings().addWorld(worldname);
            player.sendMessage(String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.fix(CardinalLanguage.CommandExecutor.CARDINAL_CONVERT_SAO_WORLD)[0] + worldname + CardinalLanguage.fix(CardinalLanguage.CommandExecutor.CARDINAL_CONVERT_SAO_WORLD)[1]);
        }
    }

    private void printWorldsCommand(Player player) {
        player.sendMessage((Object)ChatColor.WHITE + (Object)ChatColor.ITALIC + (Object)ChatColor.BOLD + CardinalLanguage.CommandExecutor.CARDINAL_LIST_WORLDS);
        for (World world : Bukkit.getWorlds()) {
            if (Cardinal.getSettings().getWorlds().contains(world.getName())) {
                player.sendMessage((Object)ChatColor.WHITE + CardinalLanguage.fix(CardinalLanguage.CommandExecutor.CARDINAL_SHOW_SAO_WORLD)[0] + world.getName() + CardinalLanguage.fix(CardinalLanguage.CommandExecutor.CARDINAL_SHOW_SAO_WORLD)[1]);
                continue;
            }
            player.sendMessage((Object)ChatColor.GRAY + CardinalLanguage.fix(CardinalLanguage.CommandExecutor.CARDINAL_SHOW_STANDARD_WORLD)[0] + world.getName() + CardinalLanguage.fix(CardinalLanguage.CommandExecutor.CARDINAL_SHOW_STANDARD_WORLD)[1]);
        }
    }
}

