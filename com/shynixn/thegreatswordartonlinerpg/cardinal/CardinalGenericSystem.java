/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.GameMode
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.Server
 *  org.bukkit.World
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.PlayerInventory
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.java.JavaPlugin
 *  org.bukkit.potion.PotionEffect
 *  org.bukkit.potion.PotionEffectType
 *  org.bukkit.scheduler.BukkitScheduler
 */
package com.shynixn.thegreatswordartonlinerpg.cardinal;

import com.shynixn.thegreatswordartonlinerpg.cardinal.Cardinal;
import com.shynixn.thegreatswordartonlinerpg.cardinal.CardinalCommandExecutor;
import com.shynixn.thegreatswordartonlinerpg.cardinal.CardinalDeveloperCommandExecutor;
import com.shynixn.thegreatswordartonlinerpg.cardinal.CardinalException;
import com.shynixn.thegreatswordartonlinerpg.cardinal.CardinalGenericListener;
import com.shynixn.thegreatswordartonlinerpg.cardinal.CardinalLanguage;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.storage.PlayerSave;
import com.shynixn.thegreatswordartonlinerpg.resources.enums.CardinalSystem;
import com.shynixn.thegreatswordartonlinerpg.resources.enums.Priority;
import com.shynixn.thegreatswordartonlinerpg.resources.events.AincradEvent;
import com.shynixn.thegreatswordartonlinerpg.resources.events.cardinal.AincradBackCommandEvent;
import com.shynixn.thegreatswordartonlinerpg.resources.events.cardinal.AincradPlayerLogOutEvent;
import com.shynixn.thegreatswordartonlinerpg.resources.events.cardinal.AincradPlayerLoginEvent;
import com.shynixn.thegreatswordartonlinerpg.resources.events.cardinal.AincradPlayerPreLoginEvent;
import com.shynixn.thegreatswordartonlinerpg.resources.events.cardinal.AincradRespawnEvent;
import com.shynixn.thegreatswordartonlinerpg.resources.events.storage.AincradRequestPlayerSaveEvent;
import com.shynixn.thegreatswordartonlinerpg.resources.nms.NMSRegistry;
import com.shynixn.thegreatswordartonlinerpg.resources.nms.ScreenMessenger;
import com.shynixn.thegreatswordartonlinerpg.resources.nms.skillapi.SkillAPICompatibility;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import libraries.com.shynixn.utilities.BukkitChatColor;
import libraries.com.shynixn.utilities.BukkitCommands;
import libraries.com.shynixn.utilities.BukkitLocation;
import libraries.com.shynixn.utilities.BukkitUtilities;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;

public final class CardinalGenericSystem {
    private static CardinalGenericSystem instance;
    private List<Player> registeredPlayers = new ArrayList<Player>();
    private List<String> blackList = new ArrayList<String>();
    private HashMap<String, Integer> grayList = new HashMap();
    private HashMap<Player, Integer> nervegearAnimation = new HashMap();
    private boolean isRunning = false;
    private JavaPlugin plugin;
    private File blackListFile;

    protected static CardinalGenericSystem getInstance(JavaPlugin plugin) {
        if (instance == null) {
            instance = new CardinalGenericSystem(plugin);
        }
        return instance;
    }

    private CardinalGenericSystem(JavaPlugin plugin) {
        new com.shynixn.thegreatswordartonlinerpg.cardinal.CardinalCommandExecutor(plugin);
        new com.shynixn.thegreatswordartonlinerpg.cardinal.CardinalDeveloperCommandExecutor(plugin);
        new com.shynixn.thegreatswordartonlinerpg.cardinal.CardinalGenericListener(plugin);
        new com.shynixn.thegreatswordartonlinerpg.cardinal.CardinalGenericSystem$TeleportBackCommand("saob", plugin);
        this.plugin = plugin;
        this.runNervegearScheduler();
        this.blackListFile = new File(plugin.getDataFolder() + "/resources/cardinal", "blacklist.txt");
    }

    private void runNervegearScheduler() {
        if (!this.isRunning) {
            this.isRunning = true;
            this.plugin.getServer().getScheduler().scheduleSyncRepeatingTask((Plugin)this.plugin, new Runnable(){

                private String getText(Player player) {
                    String txt = CardinalLanguage.Cardinal.LINK_LOADING;
                    int i = 0;
                    while (i < (Integer)CardinalGenericSystem.this.nervegearAnimation.get((Object)player)) {
                        txt = String.valueOf(txt) + (Object)((Object)BukkitChatColor.YELLOW) + "\u258d";
                        ++i;
                    }
                    return txt;
                }

                @Override
                public void run() {
                    for (Player player : BukkitUtilities.u().getOnlinePlayers()) {
                        if (BukkitUtilities.u().compareItemNames(player.getInventory().getHelmet(), CardinalLanguage.Items.DISPLAYNAME_NERVEGEAR, CardinalLanguage.Items.LORE_NERVEGEAR, Material.IRON_HELMET)) {
                            if (!CardinalGenericSystem.this.nervegearAnimation.containsKey((Object)player)) {
                                CardinalGenericSystem.this.nervegearAnimation.put(player, 0);
                            }
                            CardinalGenericSystem.this.nervegearAnimation.put(player, (Integer)CardinalGenericSystem.this.nervegearAnimation.get((Object)player) + 1);
                            Cardinal.getRegistry().getScreenMessenger().setActionBar(player, this.getText(player));
                            if ((Integer)CardinalGenericSystem.this.nervegearAnimation.get((Object)player) == 10) {
                                Cardinal.getGenericSystem().loginPlayer(player);
                                CardinalGenericSystem.this.nervegearAnimation.remove((Object)player);
                            }
                        } else if (CardinalGenericSystem.this.nervegearAnimation.containsKey((Object)player)) {
                            CardinalGenericSystem.this.nervegearAnimation.remove((Object)player);
                        }
                        String[] arrstring = CardinalGenericSystem.this.grayList.keySet().toArray(new String[0]);
                        int n = arrstring.length;
                        int n2 = 0;
                        while (n2 < n) {
                            String name = arrstring[n2];
                            CardinalGenericSystem.this.grayList.put(name, (Integer)CardinalGenericSystem.this.grayList.get(name) - 1);
                            if ((Integer)CardinalGenericSystem.this.grayList.get(name) == 0) {
                                CardinalGenericSystem.this.grayList.remove(name);
                            }
                            ++n2;
                        }
                    }
                }
            }, 0, 20);
        }
    }

    public void clearBlackList() {
        this.blackList.clear();
        BukkitUtilities.u().writeAllLines(this.blackListFile, this.blackList);
    }

    public String[] getBlackListPlayers() {
        return this.blackList.toArray(new String[0]);
    }

    public void reload() {
        this.blackList.clear();
        ArrayList<String> items = new ArrayList<String>();
        String[] arrstring = BukkitUtilities.u().readAllLines(this.blackListFile);
        int n = arrstring.length;
        int n2 = 0;
        while (n2 < n) {
            String s = arrstring[n2];
            items.add(s);
            ++n2;
        }
        this.blackList = items;
    }

    public void putOnBlackList(Player player) {
        this.logoutPlayer(player);
        if (!this.isOnBlackList(player)) {
            this.blackList.add(String.valueOf(player.getUniqueId().toString()) + "-" + player.getName());
            BukkitUtilities.u().writeAllLines(this.blackListFile, this.blackList);
        }
        this.loginPlayer(player);
    }

    public void putOnGrayList(Player player) {
        this.logoutPlayer(player);
        this.grayList.put(player.getUniqueId().toString(), Cardinal.getSettings().getDeathKickRespawnDelay() * 60);
        this.loginPlayer(player);
    }

    public void reloadCompatibility() {
        Player[] arrplayer = Bukkit.getOnlinePlayers();
        int n = arrplayer.length;
        int n2 = 0;
        while (n2 < n) {
            Player player = arrplayer[n2];
            if (Cardinal.getSettings().getSystem() == CardinalSystem.CLASSIC) {
                this.loginPlayer(player);
            }
            ++n2;
        }
    }

    public void logOutAll() {
        Player[] arrplayer = this.registeredPlayers.toArray((T[])new Player[this.registeredPlayers.size()]);
        int n = arrplayer.length;
        int n2 = 0;
        while (n2 < n) {
            Player player = arrplayer[n2];
            this.logoutPlayer(player, true);
            ++n2;
        }
    }

    public void logoutPlayer(Player player) {
        this.logoutPlayer(player, false);
    }

    public void logoutPlayer(Player player, boolean fast) {
        if (this.registeredPlayers.contains((Object)player)) {
            AincradPlayerLogOutEvent logOutEvent = new AincradPlayerLogOutEvent(player);
            this.logOutSao(player, logOutEvent);
            this.logOutAllSystems(player, logOutEvent);
        }
    }

    private void logOutSao(Player player, AincradPlayerLogOutEvent logOutEvent) {
        if (Cardinal.getSettings().getSystem() == CardinalSystem.SAO) {
            Cardinal.call().notifyFloorSystem(logOutEvent);
        }
    }

    private AincradPlayerLogOutEvent logOutAllSystems(Player player, AincradPlayerLogOutEvent logOutEvent) {
        this.registeredPlayers.remove((Object)player);
        Cardinal.getSkillExecutor().cancelAllSkills((LivingEntity)player);
        AincradRequestPlayerSaveEvent requestPlayerSaveEvent = new AincradRequestPlayerSaveEvent(player);
        Cardinal.call().notifyStorageSystem(requestPlayerSaveEvent);
        Cardinal.call().notifyScoreboardSystem(logOutEvent);
        PlayerSave playerSave = requestPlayerSaveEvent.getPlayerSave();
        playerSave.setLastLocation(new BukkitLocation(player.getLocation()));
        player.setGameMode(Cardinal.getSettings().getLogoutGameMode());
        Cardinal.call().notifyStorageSystem(logOutEvent);
        player.teleport(Cardinal.getSettings().getLogoutLocation().getLocation());
        if (Cardinal.getSettings().isLogoutAnnouncement()) {
            BukkitUtilities.u().sendServerMessage(Cardinal.getSettings().getLogoutText(), this.registeredPlayers);
        }
        return logOutEvent;
    }

    private boolean isOnBlackList(Player player) {
        for (String s : this.blackList) {
            if (!s.contains(player.getUniqueId().toString())) continue;
            return true;
        }
        return false;
    }

    public void loginPlayer(Player player) {
        if (this.isOnBlackList(player)) {
            player.kickPlayer(CardinalLanguage.Cardinal.YOU_ARE_DEAD_FOREVER);
        } else if (this.grayList.containsKey(player.getUniqueId().toString())) {
            player.kickPlayer(String.valueOf(CardinalLanguage.fix(CardinalLanguage.Cardinal.YOU_ARE_DEAD_RESPAWN)[0]) + this.grayList.get(player.getUniqueId().toString()) / 60 + CardinalLanguage.fix(CardinalLanguage.Cardinal.YOU_ARE_DEAD_RESPAWN)[1]);
        } else if (!this.registeredPlayers.contains((Object)player)) {
            AincradPlayerPreLoginEvent preLoginEvent = new AincradPlayerPreLoginEvent(player);
            AincradPlayerLoginEvent loginEvent = new AincradPlayerLoginEvent(player);
            this.loginSao(loginEvent, preLoginEvent);
            this.loginAlo(loginEvent, preLoginEvent);
            if (!preLoginEvent.isCancelled()) {
                this.loginAllSystems(player, loginEvent);
            }
        }
    }

    private void loginAllSystems(Player player, AincradPlayerLoginEvent loginEvent) {
        if (!loginEvent.isCancelled()) {
            this.registeredPlayers.add(player);
            Cardinal.call().notifyScoreboardSystem(loginEvent);
            Cardinal.call().notifyStorageSystem(loginEvent);
            player.setGameMode(Cardinal.getSettings().getLoginGameMode());
            Cardinal.getStorageItems().setStandardItems(player);
            if (Cardinal.getSettings().isLoginScreenMessage()) {
                Cardinal.getRegistry().getScreenMessenger().setPlayerTitleMoving(player, Cardinal.getSettings().getLoginTitle(), Cardinal.getSettings().getLoginTitle().length() * 2, NMSRegistry.TextSpeed.NORMAL, 2);
                Cardinal.getRegistry().getScreenMessenger().setPlayerSubTitlePercent(player, Cardinal.getSettings().getLoginsubTitle(), NMSRegistry.TextSpeed.VERYFAST);
                player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 300, 30));
                player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 300, 30));
                player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 300, -30));
                player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 300, 30));
            }
            if (Cardinal.getSettings().isLoginAnnouncement()) {
                BukkitUtilities.u().sendServerMessage(String.valueOf(CardinalLanguage.fix(Cardinal.getSettings().getLoginText())[0]) + player.getDisplayName() + CardinalLanguage.fix(Cardinal.getSettings().getLoginText())[1], this.registeredPlayers);
            }
        }
    }

    private void loginAlo(AincradPlayerLoginEvent loginEvent, AincradPlayerPreLoginEvent preLoginEvent) {
        if (Cardinal.getSettings().getSystem() == CardinalSystem.ALO) {
            AincradRequestPlayerSaveEvent playerSaveEvent = new AincradRequestPlayerSaveEvent(loginEvent.getPlayer());
            Cardinal.call().notifyStorageSystem(loginEvent);
            Cardinal.call().notifyStorageSystem(playerSaveEvent);
            if (playerSaveEvent.getPlayerSave().getRaceName() == null || playerSaveEvent.getPlayerSave().getRaceName().equals("")) {
                boolean joinable = false;
                if (Cardinal.getRegistry().isUsingSkillApi() && Cardinal.getSettings().isSkillapiselectracebyclass()) {
                    joinable = Cardinal.getRegistry().getSkillAPIRegistry().joinRace(loginEvent.getPlayer(), playerSaveEvent);
                    if (Cardinal.getSettings().isRaceJoinAnnounceMent()) {
                        BukkitUtilities.u().sendServerMessage(String.valueOf(CardinalLanguage.fix(Cardinal.getSettings().getRaceJoinAnnounceMentText())[0]) + loginEvent.getPlayer().getDisplayName() + CardinalLanguage.fix(Cardinal.getSettings().getRaceJoinAnnounceMentText())[1] + playerSaveEvent.getPlayerSave().getRaceName() + CardinalLanguage.fix(Cardinal.getSettings().getRaceJoinAnnounceMentText())[2], this.registeredPlayers);
                    }
                }
                if (!joinable && playerSaveEvent.getPlayerSave().getRaceName().equals("")) {
                    Cardinal.call().sendException(new CardinalException("Race not found", "You cannot link start", "Create a race", Priority.MEDIUM));
                    Cardinal.call().sendException(new CardinalException("ALO gamesystem only works with skillapi", "You cannot link start", "Install skillapi plugin and set a class name as race", Priority.MEDIUM));
                    loginEvent.setCancelled(true);
                    this.logoutPlayer(loginEvent.getPlayer());
                    return;
                }
            }
            Cardinal.call().notifyRaceSystem(loginEvent);
        }
    }

    private void loginSao(AincradPlayerLoginEvent loginEvent, AincradPlayerPreLoginEvent preLoginEvent) {
        if (Cardinal.getSettings().getSystem() == CardinalSystem.SAO) {
            Cardinal.call().notifyFloorSystem(preLoginEvent);
            if (!preLoginEvent.isCancelled()) {
                Cardinal.call().notifyStorageSystem(loginEvent);
                Cardinal.call().notifyFloorSystem(loginEvent);
            }
        }
    }

    public boolean isValidAction(Player player) {
        if (this.registeredPlayers.contains((Object)player) && (!Cardinal.getSettings().isPerformanceBooster() || Cardinal.getSettings().getWorlds().contains(player.getWorld().getName()))) {
            return true;
        }
        return false;
    }

    public boolean isValidAction(Location location) {
        if (!Cardinal.getSettings().isPerformanceBooster() || Cardinal.getSettings().getWorlds().contains(location.getWorld().getName())) {
            return true;
        }
        return false;
    }

    public Location getRespawnPoint(Player player) {
        if (Cardinal.getSettings().getSystem() == CardinalSystem.SAO) {
            AincradRespawnEvent respawnEvent = new AincradRespawnEvent(player);
            Cardinal.call().notifyFloorSystem(respawnEvent);
            return respawnEvent.getRespawnLocation();
        }
        if (Cardinal.getSettings().getSystem() == CardinalSystem.ALO) {
            AincradRespawnEvent respawnEvent = new AincradRespawnEvent(player);
            Cardinal.call().notifyRaceSystem(respawnEvent);
            return respawnEvent.getRespawnLocation();
        }
        return null;
    }

    private class TeleportBackCommand
    extends BukkitCommands {
        public TeleportBackCommand(String command, JavaPlugin plugin) {
            super(command, plugin);
        }

        @Override
        public void playerSendCommandEvent(Player player, String[] args) {
            AincradBackCommandEvent event = new AincradBackCommandEvent(player);
            Cardinal.call().notifyMobSystem(event);
            Cardinal.call().notifySkillSystem(event);
        }
    }

}

