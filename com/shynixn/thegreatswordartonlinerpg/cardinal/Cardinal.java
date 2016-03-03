/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.plugin.java.JavaPlugin
 */
package com.shynixn.thegreatswordartonlinerpg.cardinal;

import com.shynixn.thegreatswordartonlinerpg.TheGreatSwordArtOnlineRPG;
import com.shynixn.thegreatswordartonlinerpg.cardinal.CardinalException;
import com.shynixn.thegreatswordartonlinerpg.cardinal.CardinalFileSystem;
import com.shynixn.thegreatswordartonlinerpg.cardinal.CardinalGenericSystem;
import com.shynixn.thegreatswordartonlinerpg.cardinal.CardinalLanguage;
import com.shynixn.thegreatswordartonlinerpg.cardinal.CardinalLogger;
import com.shynixn.thegreatswordartonlinerpg.cardinal.CardinalSettings;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.floors.FloorSystem;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.mobs.MobSystem;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.races.RaceSystem;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.scoreboard.ScoreboardSystem;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.skills.Skill;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.skills.SkillExecutor;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.skills.SkillSystem;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.skills.SkillTmp;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.storage.StorageItems;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.storage.StorageSystem;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.wings.WingsSystem;
import com.shynixn.thegreatswordartonlinerpg.resources.effects.AincradAnimation;
import com.shynixn.thegreatswordartonlinerpg.resources.enums.Priority;
import com.shynixn.thegreatswordartonlinerpg.resources.events.AincradEvent;
import com.shynixn.thegreatswordartonlinerpg.resources.nms.NMSRegistry;
import java.io.File;
import libraries.com.shynixn.utilities.BukkitAreaAPI;
import libraries.com.shynixn.utilities.BukkitChatColor;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class Cardinal {
    private static Cardinal instance;
    public static final String PREFIX;
    public static final String PREFIX_CONSOLE;
    public static final String PREFIX_ERROR;
    public static final String PREFIX_SUCCESS;
    public static final String TITLE;
    private TheGreatSwordArtOnlineRPG plugin;
    private FloorSystem floorSystem;
    private MobSystem mobSystem;
    private ScoreboardSystem scoreboardSystem;
    private SkillSystem skillSystem;
    private StorageSystem storageSystem;
    private RaceSystem raceSystem;
    private WingsSystem wingsSystem;
    private CardinalFileSystem fileSystem;
    private CardinalLogger logger;

    static {
        PREFIX = (Object)ChatColor.DARK_RED + (Object)ChatColor.BOLD + (Object)ChatColor.ITALIC + "[" + (Object)ChatColor.RED + (Object)ChatColor.BOLD + (Object)ChatColor.ITALIC + "SAO" + (Object)ChatColor.DARK_RED + (Object)ChatColor.BOLD + (Object)ChatColor.ITALIC + "]" + (Object)ChatColor.GOLD + " ";
        PREFIX_CONSOLE = (Object)ChatColor.YELLOW + "[SAO] ";
        PREFIX_ERROR = String.valueOf(PREFIX) + (Object)ChatColor.RED;
        PREFIX_SUCCESS = String.valueOf(PREFIX) + (Object)ChatColor.GREEN;
        TITLE = (Object)((Object)BukkitChatColor.AQUA) + (Object)ChatColor.ITALIC + (Object)ChatColor.UNDERLINE + "     The Great " + (Object)((Object)BukkitChatColor.RED) + (Object)ChatColor.ITALIC + (Object)ChatColor.UNDERLINE + "Sword " + (Object)((Object)BukkitChatColor.GREEN) + (Object)ChatColor.ITALIC + (Object)ChatColor.UNDERLINE + "Art " + (Object)((Object)BukkitChatColor.GOLD) + (Object)ChatColor.ITALIC + (Object)ChatColor.UNDERLINE + "Online" + (Object)((Object)BukkitChatColor.AQUA) + (Object)ChatColor.ITALIC + (Object)ChatColor.UNDERLINE + " RPG Plugin";
    }

    private Cardinal(TheGreatSwordArtOnlineRPG plugin) {
        this.plugin = plugin;
        this.fileSystem = new CardinalFileSystem(plugin);
        this.floorSystem = new FloorSystem(plugin);
        this.mobSystem = new MobSystem(plugin);
        this.scoreboardSystem = new ScoreboardSystem(plugin);
        this.skillSystem = new SkillSystem(plugin);
        this.storageSystem = new StorageSystem(plugin);
        this.raceSystem = new RaceSystem(plugin);
        this.wingsSystem = new WingsSystem(plugin);
        this.logger = new CardinalLogger(plugin);
    }

    public static void reload() {
        if (instance != null) {
            try {
                Priority.values();
                AincradAnimation.play(Cardinal.instance.plugin.getDataFolder(), Cardinal.instance.plugin);
                Cardinal.instance.fileSystem.prepareFileSystem();
                new com.shynixn.thegreatswordartonlinerpg.gamesystems.skills.SkillTmp(null);
                CardinalException.resetExceptions();
                Cardinal.getLanguage().reload();
                Cardinal.getRegistry().checkVersion();
                Cardinal.getRegistry().enable();
                Cardinal.getSettings().reload();
                Cardinal.getGenericSystem().reload();
                Cardinal.instance.floorSystem.reload();
                Cardinal.instance.mobSystem.reload();
                Cardinal.instance.skillSystem.reload();
                Cardinal.instance.raceSystem.reload();
                Cardinal.instance.wingsSystem.reload();
                Cardinal.getLogger().logCardinalStatus();
            }
            catch (CardinalException e) {
                instance.sendException(e);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void reloadPlayers() {
        if (Cardinal.getGenericSystem() != null) {
            Cardinal.getGenericSystem().reloadCompatibility();
        }
    }

    public static void activateCardinal(TheGreatSwordArtOnlineRPG plugin) {
        if (instance == null) {
            try {
                BukkitAreaAPI.PLUGIN = plugin;
                instance = new Cardinal(plugin);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void shutdown(TheGreatSwordArtOnlineRPG plugin) {
        if (plugin != null) {
            Cardinal.getGenericSystem().logOutAll();
            Cardinal.getRegistry().disable();
            Cardinal.instance.mobSystem.reset();
            Cardinal.instance.skillSystem.reset();
            Cardinal.instance.wingsSystem.reset();
        }
    }

    public void sendException(CardinalException exception) {
        Cardinal.getLogger().logException(exception);
        if (exception.getPriority() == Priority.HIGH) {
            Cardinal.getLogger().logShutDownMessage(exception);
        }
    }

    public static Cardinal call() {
        return instance;
    }

    public void notifyFloorSystem(AincradEvent event) {
        if (!event.isCancelled()) {
            try {
                this.floorSystem.handleCardinalEvent(event);
            }
            catch (CardinalException e) {
                event.setCancelled(true);
                instance.sendException(e);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void notifyScoreboardSystem(AincradEvent event) {
        if (!event.isCancelled()) {
            try {
                this.scoreboardSystem.handleCardinalEvent(event);
            }
            catch (CardinalException e) {
                event.setCancelled(true);
                instance.sendException(e);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void notifyRaceSystem(AincradEvent event) {
        if (!event.isCancelled()) {
            try {
                this.raceSystem.handleCardinalEvent(event);
            }
            catch (CardinalException e) {
                event.setCancelled(true);
                instance.sendException(e);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void notifySkillSystem(AincradEvent event) {
        if (!event.isCancelled()) {
            try {
                this.skillSystem.handleCardinalEvent(event);
            }
            catch (CardinalException e) {
                event.setCancelled(true);
                instance.sendException(e);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void notifyStorageSystem(AincradEvent event) {
        if (!event.isCancelled()) {
            try {
                this.storageSystem.handleCardinalEvent(event);
            }
            catch (CardinalException e) {
                event.setCancelled(true);
                instance.sendException(e);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void notifyMobSystem(AincradEvent event) {
        if (!event.isCancelled()) {
            try {
                this.mobSystem.handleCardinalEvent(event);
            }
            catch (CardinalException e) {
                event.setCancelled(true);
                instance.sendException(e);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void notifyWingsSystem(AincradEvent event) {
        if (!event.isCancelled()) {
            try {
                this.wingsSystem.handleCardinalEvent(event);
            }
            catch (CardinalException e) {
                event.setCancelled(true);
                instance.sendException(e);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static SkillExecutor getSkillExecutor() {
        block3 : {
            try {
                if (instance != null) break block3;
                return null;
            }
            catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return SkillExecutor.getInstance(Cardinal.instance.plugin);
    }

    public static StorageItems getStorageItems() {
        block3 : {
            try {
                if (instance != null) break block3;
                return null;
            }
            catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return StorageItems.getInstance();
    }

    public static CardinalSettings getSettings() {
        block3 : {
            try {
                if (instance != null) break block3;
                return null;
            }
            catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return CardinalSettings.getInstance(Cardinal.instance.plugin);
    }

    public static NMSRegistry getRegistry() {
        block3 : {
            try {
                if (instance != null) break block3;
                return null;
            }
            catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return NMSRegistry.getInstance(Cardinal.instance.plugin);
    }

    public static CardinalGenericSystem getGenericSystem() {
        block3 : {
            try {
                if (instance != null) break block3;
                return null;
            }
            catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return CardinalGenericSystem.getInstance(Cardinal.instance.plugin);
    }

    public static CardinalLogger getLogger() {
        block3 : {
            try {
                if (instance != null) break block3;
                return null;
            }
            catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return Cardinal.instance.logger;
    }

    private static CardinalLanguage getLanguage() {
        block3 : {
            try {
                if (instance != null) break block3;
                return null;
            }
            catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return CardinalLanguage.getInstance(Cardinal.instance.plugin);
    }
}

