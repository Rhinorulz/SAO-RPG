/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.Server
 *  org.bukkit.entity.Entity
 *  org.bukkit.inventory.meta.BannerMeta
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.java.JavaPlugin
 *  org.bukkit.scheduler.BukkitScheduler
 */
package com.shynixn.thegreatswordartonlinerpg.gamesystems.wings;

import com.shynixn.thegreatswordartonlinerpg.gamesystems.wings.BannerWingsApiListener;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.wings.Wings;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.wings.WingsData;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.wings.WingsSystem;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import libraries.com.shynixn.utilities.BukkitChatColor;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public final class BannerWingsApi {
    private static boolean isRunning = false;
    private static HashMap<Entity, Wings> wingsList = new HashMap();
    private static WingsSystem wingsDataManager;
    private static List<BannerWingsApiListener> listeners;
    public static final String DISPLAY_REMOVER;
    public static final String LORE_REMOVER;

    static {
        listeners = new ArrayList<BannerWingsApiListener>();
        DISPLAY_REMOVER = (Object)((Object)BukkitChatColor.GOLD) + "Wings Holder";
        LORE_REMOVER = (Object)ChatColor.YELLOW + "Rightclick to hide your wings.";
    }

    protected static void start(JavaPlugin plugin, WingsSystem wingsDataManager) {
        if (!isRunning) {
            isRunning = true;
            BannerWingsApi.wingsDataManager = wingsDataManager;
            BannerWingsApi.run(plugin);
        }
    }

    public static void addListener(BannerWingsApiListener listener) {
        listeners.add(listener);
    }

    public static void setWings(Entity entity, Wings wings) {
        if (wingsList.containsKey((Object)entity)) {
            throw new IllegalArgumentException("Entity does already have wings.");
        }
        wingsList.put(entity, wings);
    }

    public static WingsData getWingsDataClone(Entity entity) {
        if (!wingsList.containsKey((Object)entity)) {
            throw new IllegalArgumentException("Entity has not got wings.");
        }
        return wingsList.get((Object)entity).getDataCopy();
    }

    public static void setWings(Entity entity, String wingsName) {
        if (!wingsDataManager.contains(wingsName)) {
            throw new IllegalArgumentException("Wings " + wingsName + " do not exist.");
        }
        if (wingsList.containsKey((Object)entity)) {
            throw new IllegalArgumentException("Entity does already have wings.");
        }
        wingsList.put(entity, new Wings(entity, wingsDataManager.getItemFromName(wingsName)));
    }

    public static void damageWings(Entity entity) {
        if (!wingsList.containsKey((Object)entity)) {
            throw new IllegalArgumentException("Entity has not got wings.");
        }
        wingsList.get((Object)entity).playDamageAnimation();
    }

    public static boolean hasWings(Entity entity) {
        return wingsList.containsKey((Object)entity);
    }

    public static void removeWings(Entity entity) {
        if (wingsList.containsKey((Object)entity)) {
            wingsList.get((Object)entity).despawn();
            wingsList.remove((Object)entity);
            BannerWingsApiListener[] arrbannerWingsApiListener = listeners.toArray(new BannerWingsApiListener[0]);
            int n = arrbannerWingsApiListener.length;
            int n2 = 0;
            while (n2 < n) {
                BannerWingsApiListener listener = arrbannerWingsApiListener[n2];
                listener.disableWingsEvent(entity, wingsList.get((Object)entity));
                ++n2;
            }
        }
    }

    public static void showWings(Entity entity) {
        if (!wingsList.containsKey((Object)entity)) {
            throw new IllegalArgumentException("Entity has not got wings.");
        }
        wingsList.get((Object)entity).respawn();
        wingsList.get((Object)entity).forceUpdate();
        BannerWingsApiListener[] arrbannerWingsApiListener = listeners.toArray(new BannerWingsApiListener[0]);
        int n = arrbannerWingsApiListener.length;
        int n2 = 0;
        while (n2 < n) {
            BannerWingsApiListener listener = arrbannerWingsApiListener[n2];
            listener.enableWingsEvent(entity, wingsList.get((Object)entity));
            ++n2;
        }
    }

    public static void hideWings(Entity entity) {
        if (!wingsList.containsKey((Object)entity)) {
            throw new IllegalArgumentException("Entity has not got wings.");
        }
        wingsList.get((Object)entity).despawn();
    }

    public static void changeRightWing(Entity entity, BannerMeta bannerMeta) {
        if (!wingsList.containsKey((Object)entity)) {
            throw new IllegalArgumentException("Entity has not got wings.");
        }
        wingsList.get((Object)entity).changeRightWingDesign(bannerMeta);
    }

    public static void changeLeftWing(Entity entity, BannerMeta bannerMeta) {
        if (!wingsList.containsKey((Object)entity)) {
            throw new IllegalArgumentException("Entity has not got wings.");
        }
        wingsList.get((Object)entity).changeLeftWingDesign(bannerMeta);
    }

    public static void changeBothWings(Entity entity, BannerMeta bannerMeta) {
        if (!wingsList.containsKey((Object)entity)) {
            throw new IllegalArgumentException("Entity has not got wings.");
        }
        wingsList.get((Object)entity).changeLeftWingDesign(bannerMeta);
        wingsList.get((Object)entity).changeRightWingDesign(bannerMeta);
    }

    public static void changeSpeed(Entity entity, WingsData.WingsSpeed speed) {
        if (!wingsList.containsKey((Object)entity)) {
            throw new IllegalArgumentException("Entity has not got wings.");
        }
        wingsList.get((Object)entity).changeSpeed(speed);
    }

    protected static void disable(JavaPlugin plugin) {
        for (Entity entity : wingsList.keySet()) {
            wingsList.get((Object)entity).despawn();
        }
        wingsList.clear();
    }

    protected static boolean isWing(Entity entity) {
        for (Wings wings : wingsList.values()) {
            if (!wings.isWing(entity)) continue;
            return true;
        }
        return false;
    }

    private static void run(JavaPlugin plugin) {
        plugin.getServer().getScheduler().scheduleSyncRepeatingTask((Plugin)plugin, new Runnable(){

            @Override
            public void run() {
                Entity[] arrentity = wingsList.keySet().toArray((T[])new Entity[0]);
                int n = arrentity.length;
                int n2 = 0;
                while (n2 < n) {
                    Entity entity = arrentity[n2];
                    if (entity.isDead()) {
                        BannerWingsApi.removeWings(entity);
                    } else if (((Wings)wingsList.get((Object)entity)).update()) {
                        for (BannerWingsApiListener listener : listeners) {
                            listener.refreshWingsEvent(entity, (Wings)wingsList.get((Object)entity));
                        }
                    }
                    ++n2;
                }
            }
        }, 0, 1);
    }

}

