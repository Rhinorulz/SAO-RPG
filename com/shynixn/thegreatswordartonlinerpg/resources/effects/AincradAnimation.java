/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.plugin.java.JavaPlugin
 */
package com.shynixn.thegreatswordartonlinerpg.resources.effects;

import com.shynixn.thegreatswordartonlinerpg.cardinal.Cardinal;
import java.io.File;
import java.io.InputStream;
import java.util.Random;
import libraries.com.shynixn.utilities.BukkitUtilities;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class AincradAnimation {
    public static void play(File folder, JavaPlugin plugin) {
        Cardinal.getLogger().logHeadLine("INITIALIZING CARDINAL SYSTEM");
        BukkitUtilities.u().copyFile(plugin.getResource("aincrad.txt"), new File(folder, "aincrad.txt"));
        String[] arrstring = BukkitUtilities.u().readAllLines(new File(folder, "aincrad.txt"));
        int n = arrstring.length;
        int n2 = 0;
        while (n2 < n) {
            String sl = arrstring[n2];
            String text = "";
            int i = 0;
            while (i < sl.length()) {
                if (sl.charAt(i) == '#') {
                    text = String.valueOf(text) + (Object)ChatColor.WHITE;
                    text = String.valueOf(text) + sl.charAt(i);
                } else if (sl.charAt(i) == '.') {
                    Random random = new Random();
                    int number = random.nextInt(10);
                    text = number > 5 ? String.valueOf(text) + (Object)ChatColor.BLUE + String.valueOf(number) : String.valueOf(text) + (Object)ChatColor.DARK_BLUE + String.valueOf(number);
                }
                ++i;
            }
            Cardinal.getLogger().log(text);
            ++n2;
        }
        Cardinal.getLogger().logHeadLine("Completed");
        new File(folder, "aincrad.txt").delete();
    }
}

