/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  org.bukkit.configuration.file.YamlConfiguration
 *  org.bukkit.plugin.java.JavaPlugin
 */
package com.shynixn.thegreatswordartonlinerpg.gamesystems.wings;

import com.shynixn.thegreatswordartonlinerpg.cardinal.Cardinal;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.wings.WingsData;
import java.io.File;
import java.util.ArrayList;
import libraries.com.shynixn.utilities.BukkitFileManager;
import libraries.com.shynixn.utilities.BukkitObject;
import libraries.com.shynixn.utilities.BukkitUtilities;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class WingsDataFileManager
extends BukkitFileManager {
    private File getFolder() {
        return new File(this.getDataFolder(), "resources/wings");
    }

    public WingsDataFileManager(JavaPlugin plugin) {
        super(plugin, "");
    }

    @Override
    public boolean save(BukkitObject item) {
        try {
            Cardinal.getLogger().logHidden("Saving wings " + item.getName() + ".");
            YamlConfiguration dataConfig = new YamlConfiguration();
            File dataFile = BukkitUtilities.u().createFile(new File(this.getFolder(), String.valueOf(item.getName()) + ".yml"));
            File leftFile = BukkitUtilities.u().createFile(new File(this.getFolder(), String.valueOf(item.getName()) + "_left.txt"));
            File rightFile = BukkitUtilities.u().createFile(new File(this.getFolder(), String.valueOf(item.getName()) + "_right.txt"));
            dataConfig.load(dataFile);
            WingsData data = (WingsData)item;
            dataConfig.set("wing.general.name", (Object)data.getName());
            dataConfig.set("wing.general.displayname", (Object)data.getDisplayName());
            dataConfig.set("wing.general.speed", (Object)data.getSpeed().name());
            dataConfig.set("wing.general.small", (Object)data.isSmall());
            BukkitUtilities.u().writeAllLines(leftFile, data.toLeftTxt());
            BukkitUtilities.u().writeAllLines(rightFile, data.toRightTxt());
            dataConfig.save(dataFile);
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(BukkitObject item) {
        try {
            File dataFile = new File(this.getFolder(), String.valueOf(item.getName()) + ".yml");
            File leftFile = new File(this.getFolder(), String.valueOf(item.getName()) + "_left.txt");
            File rightFile = new File(this.getFolder(), String.valueOf(item.getName()) + "_right.txt");
            dataFile.delete();
            leftFile.delete();
            rightFile.delete();
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public BukkitObject[] load() {
        Cardinal.getLogger().logHeadLine("Loading Wings");
        ArrayList<WingsData> wingsDatas = new ArrayList<WingsData>();
        try {
            YamlConfiguration dataConfig = new YamlConfiguration();
            String[] arrstring = this.getFolder().list();
            int n = arrstring.length;
            int n2 = 0;
            while (n2 < n) {
                String s = arrstring[n2];
                if (s.contains(".yml")) {
                    File sfile = new File(this.getFolder(), s);
                    dataConfig.load(sfile);
                    WingsData wingsData = new WingsData(dataConfig.getString("wing.general.name"), dataConfig.getString("wing.general.displayname"));
                    wingsData.setSpeed(WingsData.WingsSpeed.getWingsSpeedFromName(dataConfig.getString("wing.general.speed")));
                    wingsData.setSmall(dataConfig.getBoolean("wing.general.small"));
                    File leftFile = new File(this.getFolder(), String.valueOf(wingsData.getName()) + "_left.txt");
                    File rightFile = new File(this.getFolder(), String.valueOf(wingsData.getName()) + "_right.txt");
                    wingsData.toLeftItemStack(BukkitUtilities.u().readAllLines(leftFile));
                    wingsData.toRightItemStack(BukkitUtilities.u().readAllLines(rightFile));
                    wingsDatas.add(wingsData);
                }
                ++n2;
            }
            Cardinal.getLogger().logHeadLine("Completed");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return wingsDatas.toArray(new WingsData[wingsDatas.size()]);
    }
}

