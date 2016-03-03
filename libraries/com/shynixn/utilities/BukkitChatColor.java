/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 */
package libraries.com.shynixn.utilities;

import org.bukkit.ChatColor;

public enum BukkitChatColor {
    AQUA((Object)ChatColor.AQUA + (Object)ChatColor.BOLD),
    BLACK((Object)ChatColor.BLACK + (Object)ChatColor.BOLD),
    BLUE((Object)ChatColor.BLUE + (Object)ChatColor.BOLD),
    DARK_AQUA((Object)ChatColor.DARK_AQUA + (Object)ChatColor.BOLD),
    DARK_BLUE((Object)ChatColor.DARK_BLUE + (Object)ChatColor.BOLD),
    DARK_GRAY((Object)ChatColor.DARK_GRAY + (Object)ChatColor.BOLD),
    DARK_GREEN((Object)ChatColor.DARK_GREEN + (Object)ChatColor.BOLD),
    DARK_PURPLE((Object)ChatColor.DARK_PURPLE + (Object)ChatColor.BOLD),
    DARK_RED((Object)ChatColor.DARK_RED + (Object)ChatColor.BOLD),
    GOLD((Object)ChatColor.GOLD + (Object)ChatColor.BOLD),
    GRAY((Object)ChatColor.GRAY + (Object)ChatColor.BOLD),
    GREEN((Object)ChatColor.GREEN + (Object)ChatColor.BOLD),
    LIGHT_PURPLE((Object)ChatColor.LIGHT_PURPLE + (Object)ChatColor.BOLD),
    RED((Object)ChatColor.RED + (Object)ChatColor.BOLD),
    WHITE((Object)ChatColor.WHITE + (Object)ChatColor.BOLD),
    YELLOW((Object)ChatColor.YELLOW + (Object)ChatColor.BOLD),
    LINEBREAK("\n");
    
    private String color;

    private BukkitChatColor(String color, int n2, String string2) {
        this.color = color;
    }

    public String toString() {
        return this.color;
    }
}

