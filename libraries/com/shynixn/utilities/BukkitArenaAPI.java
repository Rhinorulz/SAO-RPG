/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.GameMode
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.Server
 *  org.bukkit.World
 *  org.bukkit.block.Block
 *  org.bukkit.block.BlockState
 *  org.bukkit.block.Sign
 *  org.bukkit.entity.Damageable
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.block.Action
 *  org.bukkit.event.entity.EntityDamageEvent
 *  org.bukkit.event.player.PlayerInteractEvent
 *  org.bukkit.event.player.PlayerQuitEvent
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.PlayerInventory
 *  org.bukkit.inventory.meta.ItemMeta
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.java.JavaPlugin
 *  org.bukkit.scheduler.BukkitScheduler
 */
package libraries.com.shynixn.utilities;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import libraries.com.shynixn.utilities.BukkitAreaAPI;
import libraries.com.shynixn.utilities.BukkitChatColor;
import libraries.com.shynixn.utilities.BukkitCommands;
import libraries.com.shynixn.utilities.BukkitLocation;
import libraries.com.shynixn.utilities.BukkitObject;
import libraries.com.shynixn.utilities.BukkitSelector;
import libraries.com.shynixn.utilities.BukkitUtilities;
import libraries.com.shynixn.utilities.IGame;
import libraries.com.shynixn.utilities.IGameListener;
import libraries.com.shynixn.utilities.IGameManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public final class BukkitArenaAPI {

    public static class Arena
    extends BukkitAreaAPI.BukkitArea
    implements Serializable {
        private static final long serialVersionUID = 1;
        private ArenaState state;
        private Lobby lobby;
        private int minplayer = 5;
        private int maxplayer = 10;
        private int gametime = 300;
        private int lives = 5;
        private ArrayList<SpawnPoint> spawnpoints;

        public Arena(String name, String displayName, Location leftcorner, Location rightcorner) {
            super(name, displayName, leftcorner, rightcorner);
            this.lobby = new Lobby();
            this.spawnpoints = new ArrayList();
            this.state = ArenaState.DISABLED;
        }

        public Arena() {
        }

        public int getLives() {
            return this.lives;
        }

        public void setLives(int lives) {
            this.lives = lives;
        }

        public void setGameTime(int time) {
            this.gametime = time;
        }

        public int getGameTime() {
            return this.gametime;
        }

        public void setState(ArenaState state) {
            this.state = state;
        }

        public ArenaState getState() {
            return this.state;
        }

        public void setMinPlayer(int minplayer) {
            this.minplayer = minplayer;
        }

        public int getMinPlayer() {
            return this.minplayer;
        }

        public void setMaxPlayer(int maxplayer) {
            this.maxplayer = maxplayer;
        }

        public int getMaxPlayer() {
            return this.maxplayer;
        }

        public Lobby getLobby() {
            return this.lobby;
        }

        public void removeSpawnPoint(int id) {
            int i = 0;
            while (i < this.spawnpoints.size()) {
                if (this.spawnpoints.get(i).getId() == id) {
                    this.spawnpoints.remove(this.spawnpoints.get(i));
                    break;
                }
                ++i;
            }
        }

        public void addSpawnPoint(int id, Location location) {
            if (!this.spawnpoints.contains(id)) {
                this.spawnpoints.add(new SpawnPoint(id, location));
            }
        }

        public List<SpawnPoint> getSpawnPoints() {
            return Arrays.asList(this.spawnpoints.toArray(new SpawnPoint[this.spawnpoints.size()]));
        }

        public List<Integer> getSpawnPointIds() {
            ArrayList<Integer> spawnpointids = new ArrayList<Integer>();
            for (SpawnPoint spawnPoint : this.getSpawnPoints()) {
                spawnpointids.add(spawnPoint.getId());
            }
            return Arrays.asList(spawnpointids.toArray(new Integer[this.spawnpoints.size()]));
        }
    }

    private static class ArenaCommandExecutor
    extends BukkitCommands {
        private ArenaManager manager;

        public ArenaCommandExecutor(String command, ArenaManager manager, JavaPlugin plugin) {
            super(command, plugin);
            this.manager = manager;
        }

        @Override
        public void playerSendCommandEvent(Player player, String[] args) {
            if (args.length == 3 && args[0].equalsIgnoreCase("create")) {
                this.createAreaCommand(player, args[1], args[2]);
            } else if (args.length == 2 && args[0].equalsIgnoreCase("delete")) {
                this.deleteAreaCommand(player, args[1]);
            } else if (args.length == 2 && args[0].equalsIgnoreCase("joinsign")) {
                this.getJoinSignCommand(player, args[1]);
            } else if (args.length == 1 && args[0].equalsIgnoreCase("leavesign")) {
                this.getLeaveSignCommand(player);
            } else if (args.length == 1 && args[0].equalsIgnoreCase("save")) {
                this.saveAreaCommand(player);
            } else if (args.length == 1 && args[0].equalsIgnoreCase("reset")) {
                this.resetAreaCommand(player);
            } else if (args.length == 1 && args[0].equalsIgnoreCase("buildingmode")) {
                this.buildModeEnableDisableCommand(player);
            } else if (args.length == 1 && args[0].equalsIgnoreCase("unbreakingmode")) {
                this.unreabkingModeEnableDisableCommand(player);
            } else if (args.length == 2 && args[0].equalsIgnoreCase("addspawn") && BukkitUtilities.u().tryParseInt(args[1])) {
                this.addArenaSpawnPointCommand(player, Integer.parseInt(args[1]));
            } else if (args.length == 2 && args[0].equalsIgnoreCase("removespawn") && BukkitUtilities.u().tryParseInt(args[1])) {
                this.removeArenaSpawnPointCommand(player, Integer.parseInt(args[1]));
            } else if (args.length == 2 && args[0].equalsIgnoreCase("lobbyspawn")) {
                this.setLobbySpawnCommand(player, args[1]);
            } else if (args.length == 2 && args[0].equalsIgnoreCase("lobbyleave")) {
                this.setLobbyLeaveCommand(player, args[1]);
            } else if (args.length == 2 && args[0].equalsIgnoreCase("toggle")) {
                this.toggleArenaCommand(player, args[1]);
            } else if (args.length == 2 && args[0].equalsIgnoreCase("minamount") && BukkitUtilities.u().tryParseInt(args[1])) {
                this.setMinPlayer(player, Integer.parseInt(args[1]));
            } else if (args.length == 2 && args[0].equalsIgnoreCase("maxamount") && BukkitUtilities.u().tryParseInt(args[1])) {
                this.setMaxPlayer(player, Integer.parseInt(args[1]));
            } else if (args.length == 2 && args[0].equalsIgnoreCase("gametime") && BukkitUtilities.u().tryParseInt(args[1])) {
                this.setGameTime(player, Integer.parseInt(args[1]));
            } else if (args.length == 2 && args[0].equalsIgnoreCase("lives") && BukkitUtilities.u().tryParseInt(args[1])) {
                this.setGameLives(player, Integer.parseInt(args[1]));
            } else if (args.length == 1 && args[0].equalsIgnoreCase("list")) {
                this.printAreasCommand(player);
            } else if (args.length == 1 && args[0].equalsIgnoreCase("listspawns")) {
                this.printSpawnsCommand(player);
            } else if (args.length == 1 && args[0].equalsIgnoreCase("arenaselector")) {
                player.getInventory().addItem(new ItemStack[]{BukkitSelector.selector().getSelector(this.manager.SELECTORID)});
                player.sendMessage(String.valueOf(this.manager.PREFIX_SUCCESS) + "Received " + "arena" + " selector.");
            } else if (args.length == 1 && args[0].equals("2")) {
                player.sendMessage(this.manager.HEADLINE);
                player.sendMessage(String.valueOf(this.manager.PREFIX) + "/" + this.manager.COMMAND + " addspawn <spawnid>");
                player.sendMessage(String.valueOf(this.manager.PREFIX) + "/" + this.manager.COMMAND + " removespawn <spawnid>");
                player.sendMessage(String.valueOf(this.manager.PREFIX) + "/" + this.manager.COMMAND + " lobbyspawn <name>");
                player.sendMessage(String.valueOf(this.manager.PREFIX) + "/" + this.manager.COMMAND + " lobbyleave <name>");
                player.sendMessage(String.valueOf(this.manager.PREFIX) + "/" + this.manager.COMMAND + " minamount <amount>");
                player.sendMessage(String.valueOf(this.manager.PREFIX) + "/" + this.manager.COMMAND + " maxamount <amount>");
                player.sendMessage(String.valueOf(this.manager.PREFIX) + "/" + this.manager.COMMAND + " gametime <time>");
                player.sendMessage(String.valueOf(this.manager.PREFIX) + "/" + this.manager.COMMAND + " lives <amount>");
                player.sendMessage(String.valueOf(this.manager.PREFIX) + "/" + this.manager.COMMAND + " toggle <name>");
                player.sendMessage(String.valueOf(this.manager.PREFIX) + "/" + this.manager.COMMAND + " listspawns");
                player.sendMessage(String.valueOf(this.manager.PREFIX) + (Object)ChatColor.BOLD + "---- Page 2/2 ------");
            } else {
                player.sendMessage(this.manager.HEADLINE);
                player.sendMessage(String.valueOf(this.manager.PREFIX) + "/" + this.manager.COMMAND + " create <name> <displayName>");
                player.sendMessage(String.valueOf(this.manager.PREFIX) + "/" + this.manager.COMMAND + " delete <name>");
                player.sendMessage(String.valueOf(this.manager.PREFIX) + "/" + this.manager.COMMAND + " joinsign <name>");
                player.sendMessage(String.valueOf(this.manager.PREFIX) + "/" + this.manager.COMMAND + " leavesign");
                player.sendMessage(String.valueOf(this.manager.PREFIX) + "/" + this.manager.COMMAND + " save");
                player.sendMessage(String.valueOf(this.manager.PREFIX) + "/" + this.manager.COMMAND + " reset");
                player.sendMessage(String.valueOf(this.manager.PREFIX) + "/" + this.manager.COMMAND + " unbreakingmode");
                player.sendMessage(String.valueOf(this.manager.PREFIX) + "/" + this.manager.COMMAND + " buildingmode");
                player.sendMessage(String.valueOf(this.manager.PREFIX) + "/" + this.manager.COMMAND + " arenaselector");
                player.sendMessage(String.valueOf(this.manager.PREFIX) + "/" + this.manager.COMMAND + " list");
                player.sendMessage(String.valueOf(this.manager.PREFIX) + (Object)ChatColor.BOLD + "---- Page 1/2 ------");
            }
        }

        private void createAreaCommand(Player player, String name, String displayName) {
            if (this.manager.getItemKeys().contains(name)) {
                player.sendMessage(String.valueOf(this.manager.PREFIX_ERROR) + "This " + "arena" + " does already exist.");
            } else if (BukkitSelector.selector().getSelection(player, 1, BukkitSelector.SelectionType.LEFT) == null || BukkitSelector.selector().getSelection(player, 1, BukkitSelector.SelectionType.RIGHT) == null) {
                player.sendMessage(String.valueOf(this.manager.PREFIX_ERROR) + "Select the " + "arena" + " with the " + "arena" + " selector first.");
            } else if (!BukkitSelector.selector().getSelection(player, 1, BukkitSelector.SelectionType.LEFT).getWorld().getName().equals(BukkitSelector.selector().getSelection(player, 1, BukkitSelector.SelectionType.LEFT).getWorld().getName())) {
                player.sendMessage(String.valueOf(this.manager.PREFIX_ERROR) + "The " + "arena" + " cannot be created over two worlds.");
            } else {
                Arena area = new Arena(name, displayName, BukkitSelector.selector().getSelection(player, this.manager.SELECTORID, BukkitSelector.SelectionType.LEFT), BukkitSelector.selector().getSelection(player, this.manager.SELECTORID, BukkitSelector.SelectionType.RIGHT));
                this.manager.addItem(area);
                player.sendMessage(String.valueOf(this.manager.PREFIX_SUCCESS) + "Created " + "arena" + " " + name + ".");
            }
        }

        private void deleteAreaCommand(Player player, String name) {
            if (!this.manager.getItemKeys().contains(name)) {
                player.sendMessage(String.valueOf(this.manager.PREFIX_ERROR) + "This " + "arena" + " does not exist.");
            } else {
                this.manager.removeItem(this.manager.getItemFromName(name));
                player.sendMessage(String.valueOf(this.manager.PREFIX_SUCCESS) + "Removed " + "arena" + " " + name + ".");
            }
        }

        private void getJoinSignCommand(Player player, String name) {
            if (!this.manager.getItemKeys().contains(name)) {
                player.sendMessage(String.valueOf(this.manager.PREFIX_ERROR) + "This " + "arena" + " does not exist.");
            } else {
                player.getInventory().addItem(new ItemStack[]{BukkitUtilities.u().nameItem(new ItemStack(Material.GOLD_SPADE), String.valueOf(this.manager.ARENA_NAME) + " join sign selector", new String[]{(Object)ChatColor.GREEN + "Rightclick on a sign to convert it into an arena sign.", name})});
                player.sendMessage(String.valueOf(this.manager.PREFIX_SUCCESS) + "Recived join-sign selector.");
            }
        }

        private void getLeaveSignCommand(Player player) {
            player.getInventory().addItem(new ItemStack[]{BukkitUtilities.u().nameItem(new ItemStack(Material.GOLD_SPADE), String.valueOf(this.manager.ARENA_NAME) + " leave sign selector", new String[]{(Object)ChatColor.GREEN + "Rightclick on a sign to convert it into a leave sign."})});
            player.sendMessage(String.valueOf(this.manager.PREFIX_SUCCESS) + "Recived leave-sign selector.");
        }

        private void saveAreaCommand(Player player) {
            Arena area = this.manager.getAreaFromLocation(player.getLocation());
            if (area == null) {
                player.sendMessage(String.valueOf(this.manager.PREFIX_ERROR) + "You are not in the " + "arena" + ".");
            } else if (area.getEditMode() == BukkitAreaAPI.AreaEdit.UNBREAKING) {
                player.sendMessage(String.valueOf(this.manager.PREFIX_ERROR) + "You cannot save " + "arena" + " in unbreakingmode.");
            } else {
                area.save();
                player.sendMessage(String.valueOf(this.manager.PREFIX_SUCCESS) + "Saved " + "arena" + ".");
            }
        }

        private void resetAreaCommand(Player player) {
            Arena area = this.manager.getAreaFromLocation(player.getLocation());
            if (area == null) {
                player.sendMessage(String.valueOf(this.manager.PREFIX_ERROR) + "You are not in the " + "arena" + ".");
            } else if (area.getEditMode() == BukkitAreaAPI.AreaEdit.UNBREAKING) {
                player.sendMessage(String.valueOf(this.manager.PREFIX_ERROR) + "You cannot reset " + "arena" + " in unbreakingmode.");
            } else {
                area.reset();
                player.sendMessage(String.valueOf(this.manager.PREFIX_SUCCESS) + "Reset " + "arena" + ".");
                player.sendMessage(String.valueOf(this.manager.PREFIX_SUCCESS) + "This arena reset uses an anti-lag-system. It takes sometime for bigger arenas!");
            }
        }

        private void unreabkingModeEnableDisableCommand(Player player) {
            Arena area = this.manager.getAreaFromLocation(player.getLocation());
            if (area == null) {
                player.sendMessage(String.valueOf(this.manager.PREFIX_ERROR) + "You are not in the " + "arena" + ".");
            } else if (area.getEditMode() == BukkitAreaAPI.AreaEdit.UNBREAKING) {
                area.setEditMode(BukkitAreaAPI.AreaEdit.NONE);
                area.saveUndrestroyAbleLocations();
                area.reset();
                this.manager.save(area);
                player.sendMessage(String.valueOf(this.manager.PREFIX_SUCCESS) + "Unbreaking mode disabled.");
                player.sendMessage(String.valueOf(this.manager.PREFIX_SUCCESS) + "Stored all bedrock locations and reset " + "arena" + ".");
            } else {
                area.setEditMode(BukkitAreaAPI.AreaEdit.UNBREAKING);
                area.showUndestroyAbleLocations();
                player.sendMessage(String.valueOf(this.manager.PREFIX_SUCCESS) + "Unbreaking mode enabled.");
            }
        }

        private void buildModeEnableDisableCommand(Player player) {
            Arena area = this.manager.getAreaFromLocation(player.getLocation());
            if (area == null) {
                player.sendMessage(String.valueOf(this.manager.PREFIX_ERROR) + "You are not in the " + "arena" + ".");
            } else if (area.getEditMode() == BukkitAreaAPI.AreaEdit.UNBREAKING) {
                player.sendMessage(String.valueOf(this.manager.PREFIX_ERROR) + "You cannot edit the " + "arena" + " what is in unbreaking mode.");
            } else if (area.getEditMode() == BukkitAreaAPI.AreaEdit.BUILDING) {
                area.setEditMode(BukkitAreaAPI.AreaEdit.NONE);
                player.sendMessage(String.valueOf(this.manager.PREFIX_SUCCESS) + "Building mode disabled.");
            } else {
                area.setEditMode(BukkitAreaAPI.AreaEdit.BUILDING);
                player.sendMessage(String.valueOf(this.manager.PREFIX_SUCCESS) + "Building mode enabled.");
            }
        }

        private void printAreasCommand(Player player) {
            player.sendMessage((Object)ChatColor.WHITE + (Object)ChatColor.BOLD + (Object)ChatColor.ITALIC + "Registered " + "arena" + "s:");
            for (BukkitObject s : this.manager.getItems()) {
                player.sendMessage((Object)ChatColor.GRAY + s.getName() + " " + s.getDisplayName());
            }
        }

        private void setGameLives(Player player, int amount) {
            Arena arena = this.manager.getAreaFromLocation(player.getLocation());
            if (arena == null) {
                player.sendMessage(String.valueOf(this.manager.PREFIX_ERROR) + "You are not in an arena.");
            } else if (amount < 1) {
                player.sendMessage(String.valueOf(this.manager.PREFIX_ERROR) + "Lives has to be greater than 0.");
            } else {
                arena.setLives(amount);
                this.manager.save(arena);
                player.sendMessage(String.valueOf(this.manager.PREFIX_SUCCESS) + "Updated arena lives.");
            }
        }

        private void printSpawnsCommand(Player player) {
            Arena arena = this.manager.getAreaFromLocation(player.getLocation());
            if (arena == null) {
                player.sendMessage(String.valueOf(this.manager.PREFIX_ERROR) + "You are not in an arena.");
            } else {
                player.sendMessage((Object)ChatColor.WHITE + (Object)ChatColor.BOLD + (Object)ChatColor.ITALIC + "Registered Spawns:");
                for (SpawnPoint s : arena.getSpawnPoints()) {
                    player.sendMessage((Object)ChatColor.GRAY + s.getId() + " " + s.toString());
                }
            }
        }

        private void setGameTime(Player player, int time) {
            Arena arena = this.manager.getAreaFromLocation(player.getLocation());
            if (arena == null) {
                player.sendMessage(String.valueOf(this.manager.PREFIX_ERROR) + "You are not in an arena.");
            } else if (time < 0 || time > 1200) {
                player.sendMessage(String.valueOf(this.manager.PREFIX_ERROR) + "Time cannot be lower than 0 and higher than 1200.");
            } else {
                arena.setGameTime(time);
                this.manager.save(arena);
                player.sendMessage(String.valueOf(this.manager.PREFIX_SUCCESS) + "Updated gametime.");
            }
        }

        private void setLobbyLeaveCommand(Player player, String name) {
            if (!this.manager.getItemKeys().contains(name)) {
                player.sendMessage(String.valueOf(this.manager.PREFIX_ERROR) + "This arena name does not exist.");
            } else {
                this.manager.getItemFromName(name).getLobby().setLeavePoint(player.getLocation());
                this.manager.save(this.manager.getItemFromName(name));
                player.sendMessage(String.valueOf(this.manager.PREFIX_SUCCESS) + "Updated lobbyLeave.");
            }
        }

        private void setLobbySpawnCommand(Player player, String name) {
            if (!this.manager.getItemKeys().contains(name)) {
                player.sendMessage(String.valueOf(this.manager.PREFIX_ERROR) + "This arena name does not exist.");
            } else {
                this.manager.getItemFromName(name).getLobby().setSpawnPoint(player.getLocation());
                this.manager.save(this.manager.getItemFromName(name));
                player.sendMessage(String.valueOf(this.manager.PREFIX_SUCCESS) + "Updated spawnpoint.");
            }
        }

        private void setMaxPlayer(Player player, int maxplayers) {
            Arena arena = this.manager.getAreaFromLocation(player.getLocation());
            if (arena == null) {
                player.sendMessage(String.valueOf(this.manager.PREFIX_ERROR) + "You are not in an arena.");
            } else if (maxplayers < arena.getMinPlayer()) {
                player.sendMessage(String.valueOf(this.manager.PREFIX_ERROR) + "Maxplayeramount cannot be lower than minplayeramount.");
            } else {
                arena.setMaxPlayer(maxplayers);
                this.manager.save(arena);
                player.sendMessage(String.valueOf(this.manager.PREFIX_SUCCESS) + "Updated maxplayeramount.");
            }
        }

        private void setMinPlayer(Player player, int minplayers) {
            Arena arena = this.manager.getAreaFromLocation(player.getLocation());
            if (arena == null) {
                player.sendMessage(String.valueOf(this.manager.PREFIX_ERROR) + "You are not in an arena.");
            } else if (minplayers > arena.getMaxPlayer()) {
                player.sendMessage(String.valueOf(this.manager.PREFIX_ERROR) + "Minplayeramount cannot be higher than maxplayeramount.");
            } else if (minplayers < 1) {
                player.sendMessage(String.valueOf(this.manager.PREFIX_ERROR) + "Minplayeramount cannot be lower than 1.");
            } else {
                arena.setMinPlayer(minplayers);
                this.manager.save(arena);
                player.sendMessage(String.valueOf(this.manager.PREFIX_SUCCESS) + "Updated minplayeramount.");
            }
        }

        private void removeArenaSpawnPointCommand(Player player, int spawnpointid) {
            Arena arena = this.manager.getAreaFromLocation(player.getLocation());
            if (arena == null) {
                player.sendMessage(String.valueOf(this.manager.PREFIX_ERROR) + "You are not in an arena.");
            } else if (!arena.getSpawnPointIds().contains(spawnpointid)) {
                player.sendMessage(String.valueOf(this.manager.PREFIX_ERROR) + "This spawnpoint id does not exist.");
            } else {
                arena.removeSpawnPoint(spawnpointid);
                this.manager.save(arena);
                player.sendMessage(String.valueOf(this.manager.PREFIX_SUCCESS) + "Removed spawnpoint.");
            }
        }

        private void addArenaSpawnPointCommand(Player player, int spawnpointid) {
            Arena arena = this.manager.getAreaFromLocation(player.getLocation());
            if (arena == null) {
                player.sendMessage(String.valueOf(this.manager.PREFIX_ERROR) + "You are not in an arena.");
            } else if (arena.getSpawnPointIds().contains(spawnpointid)) {
                player.sendMessage(String.valueOf(this.manager.PREFIX_ERROR) + "This spawnpoint id already exists.");
            } else {
                arena.addSpawnPoint(spawnpointid, player.getLocation());
                this.manager.save(arena);
                player.sendMessage(String.valueOf(this.manager.PREFIX_SUCCESS) + "Added spawnpoint on your location.");
            }
        }

        private void toggleArenaCommand(Player player, String name) {
            if (!this.manager.getItemKeys().contains(name)) {
                player.sendMessage(String.valueOf(this.manager.PREFIX_ERROR) + "This arena name does not exist.");
            } else {
                if (this.manager.getItemFromName(name).getState() == ArenaState.ENABLED) {
                    this.manager.getItemFromName(name).setState(ArenaState.DISABLED);
                    player.sendMessage(String.valueOf(this.manager.PREFIX_SUCCESS) + "Disabled arena.");
                } else {
                    Arena arena = this.manager.getItemFromName(name);
                    if (arena.getLobby().getLeavePoint() == null) {
                        player.sendMessage(String.valueOf(this.manager.PREFIX_ERROR) + "The lobby hasn't a lobbyleave.");
                    } else if (arena.getLobby().getSpawnPoint() == null) {
                        player.sendMessage(String.valueOf(this.manager.PREFIX_ERROR) + "The lobby hasn't a lobbyspawn.");
                    } else if (arena.getSpawnPoints().size() == 0) {
                        player.sendMessage(String.valueOf(this.manager.PREFIX_ERROR) + "You have to set atleast 1 spawnpoint.");
                    } else {
                        this.manager.getItemFromName(name).setState(ArenaState.ENABLED);
                        player.sendMessage(String.valueOf(this.manager.PREFIX_SUCCESS) + "Enabled arena.");
                    }
                }
                this.manager.save(this.manager.getItemFromName(name));
            }
        }
    }

    private static class ArenaManager
    extends BukkitAreaAPI.AreaManager {
        public String COMMAND;
        public String PREFIX;
        public String PREFIX_SUCCESS;
        public String PREFIX_ERROR;
        public String HEADLINE;
        public int SELECTORID;
        public String ARENA_NAME;

        public ArenaManager(JavaPlugin plugin, String areaCommand, String areaname, String prefix, String headline, int selectorID) {
            super(plugin, null, prefix, headline, areaname, "arenas.dat", selectorID);
            this.COMMAND = areaCommand;
            this.PREFIX = prefix;
            this.PREFIX_SUCCESS = String.valueOf(prefix) + (Object)ChatColor.GREEN;
            this.PREFIX_ERROR = String.valueOf(prefix) + (Object)ChatColor.RED;
            this.HEADLINE = headline;
            this.SELECTORID = selectorID;
            this.ARENA_NAME = areaname;
            new libraries.com.shynixn.utilities.BukkitArenaAPI$ArenaCommandExecutor(areaCommand, this, plugin);
        }

        @Override
        public Arena getAreaFromLocation(Location location) {
            for (BukkitObject object : this.getItems()) {
                Arena arena = (Arena)object;
                if (!arena.isLocationInArea(location)) continue;
                return arena;
            }
            return null;
        }

        @Override
        public Arena getItemFromName(String name) {
            return (Arena)super.getItemFromName(name);
        }
    }

    private static class ArenaSign {
        private List<Location> locations = new ArrayList<Location>();
        private boolean isRunning = false;
        private JavaPlugin plugin;
        private GameManager manager;

        public ArenaSign(GameManager manager, JavaPlugin plugin) {
            this.plugin = plugin;
            this.manager = manager;
        }

        public synchronized void addLocation(Location location) {
            if (!this.locations.contains((Object)location)) {
                this.locations.add(location);
                this.save();
            }
        }

        public void reload() {
            this.locations.clear();
            File file = new File(this.plugin.getDataFolder(), "signs.dat");
            String[] lines = BukkitUtilities.u().readAllLines(file);
            int i = 0;
            while (i < lines.length) {
                String[] parts = lines[i].split(";");
                this.locations.add(new Location(Bukkit.getWorld((String)parts[0]), (double)Integer.parseInt(parts[1]), (double)Integer.parseInt(parts[2]), (double)Integer.parseInt(parts[3])));
                ++i;
            }
        }

        public void startScheduler() {
            if (!this.isRunning) {
                this.plugin.getServer().getScheduler().scheduleSyncRepeatingTask((Plugin)this.plugin, new Runnable(){

                    @Override
                    public void run() {
                        for (Location location : ArenaSign.this.locations) {
                            try {
                                if (location.getBlock() == null || location.getBlock().getType() != Material.WALL_SIGN && location.getBlock().getType() != Material.SIGN_POST) continue;
                                Sign s = (Sign)location.getBlock().getState();
                                String[] lines = s.getLines();
                                if (ArenaSign.this.manager.getGameByName(lines[1]) == null || !lines[0].equals((Object)ChatColor.BOLD + "[" + ArenaSign.this.manager.ARENA_NAME + (Object)((Object)BukkitChatColor.BLACK) + "]")) continue;
                                Game game = ArenaSign.this.manager.getGameByName(lines[1]);
                                s.setLine(0, (Object)((Object)BukkitChatColor.BLACK) + "[" + ArenaSign.this.manager.ARENA_NAME + (Object)((Object)BukkitChatColor.BLACK) + "]");
                                s.setLine(1, game.getArena().getName());
                                s.setLine(2, game.getState().getWord());
                                s.setLine(3, (Object)ChatColor.BOLD + game.getPlayers().size() + "/" + game.getArena().getMaxPlayer());
                                s.update();
                                continue;
                            }
                            catch (Exception s) {
                                // empty catch block
                            }
                        }
                    }
                }, 0, 20);
            }
        }

        private void save() {
            File file = new File(this.plugin.getDataFolder(), "signs.dat");
            String[] lines = new String[this.locations.size()];
            int i = 0;
            while (i < this.locations.size()) {
                Location l = this.locations.get(i);
                lines[i] = String.valueOf(l.getWorld().getName()) + ";" + l.getBlockX() + ";" + l.getBlockY() + ";" + l.getBlockZ();
                ++i;
            }
            BukkitUtilities.u().writeAllLines(file, lines);
        }

    }

    public static enum ArenaState {
        DISABLED,
        ENABLED;
        

        private ArenaState(String string2, int n2) {
        }
    }

    public static abstract class Game
    implements IGame {
        private JavaPlugin plugin;
        private Arena arena;
        private String PREFIX;
        private GameStatus gameStatus;
        private int lobbyschedulerid = 0;
        private int gameschedulerid = 0;
        private int lobbycountdown = 10;
        private int gamecountdown = 0;
        private boolean isRunning = false;
        private List<Player> players = new ArrayList<Player>();
        private HashMap<Player, ItemStack[]> contents = new HashMap();
        private HashMap<Player, ItemStack[]> armor = new HashMap();
        private HashMap<Player, Integer> level = new HashMap();
        private HashMap<Player, Float> points = new HashMap();
        private HashMap<Player, Location> playerSpawnPoints = new HashMap();

        public Game(Arena arena, JavaPlugin plugin, String prefix) {
            this.arena = arena;
            this.plugin = plugin;
            this.gameStatus = GameStatus.READY;
            this.gamecountdown = arena.getGameTime();
            this.PREFIX = prefix;
        }

        public Arena getArena() {
            return this.arena;
        }

        public String getName() {
            return this.arena.getName();
        }

        public List<Player> getPlayers() {
            return Arrays.asList(this.players.toArray((T[])new Player[this.players.size()]));
        }

        public Location getRespawnLocation(Player player) {
            if (this.playerSpawnPoints.containsKey((Object)player)) {
                return this.playerSpawnPoints.get((Object)player);
            }
            return null;
        }

        public void reset(boolean save) {
            this.gamecountdown = this.arena.getGameTime();
            this.kickAllPlayers();
            this.plugin.getServer().getScheduler().cancelTask(this.gameschedulerid);
            Bukkit.getServer().getScheduler().cancelTask(this.gameschedulerid);
            this.gameStatus = GameStatus.READY;
            this.contents.clear();
            this.armor.clear();
            this.players.clear();
            this.playerSpawnPoints.clear();
            this.level.clear();
            this.points.clear();
            if (save) {
                this.arena.reset();
            }
            this.resetGame();
        }

        public boolean isAvailAble() {
            if (this.gameStatus == GameStatus.READY && this.players.size() < this.arena.getMaxPlayer() && this.arena.getState() == ArenaState.ENABLED) {
                return true;
            }
            return false;
        }

        public void sendMessageToGamePlayers(String message) {
            for (Player player : this.players) {
                player.sendMessage(String.valueOf(this.PREFIX) + message);
            }
        }

        public void kickAllPlayers() {
            Player[] players2 = this.players.toArray((T[])new Player[this.players.size()]);
            int i = 0;
            while (i < players2.length) {
                this.kickPlayer(players2[i]);
                ++i;
            }
        }

        public GameStatus getState() {
            return this.gameStatus;
        }

        public void kickPlayer(Player player) {
            if (this.players.contains((Object)player)) {
                this.players.remove((Object)player);
                player.getInventory().setContents(this.contents.get((Object)player));
                player.getInventory().setArmorContents(this.armor.get((Object)player));
                player.setLevel(this.level.get((Object)player).intValue());
                player.setExp(this.points.get((Object)player).floatValue());
                if (this.playerSpawnPoints.containsKey((Object)player)) {
                    this.playerSpawnPoints.remove((Object)player);
                }
                this.players.remove((Object)player);
                this.contents.remove((Object)player);
                this.armor.remove((Object)player);
                this.level.remove((Object)player);
                this.points.remove((Object)player);
                this.playerLeaveBattle(player);
                player.teleport(this.arena.getLobby().getLeavePoint());
            }
        }

        public void join(Player player) {
            if (!this.players.contains((Object)player) && this.gameStatus == GameStatus.READY && this.arena.getState() == ArenaState.ENABLED) {
                this.players.add(player);
                this.contents.put(player, player.getInventory().getContents());
                this.armor.put(player, player.getInventory().getArmorContents());
                this.level.put(player, player.getLevel());
                this.points.put(player, Float.valueOf(player.getExp()));
                BukkitUtilities.u().clearCompleteInventory(player);
                player.teleport(this.arena.getLobby().getSpawnPoint());
                player.setGameMode(GameMode.SURVIVAL);
                player.setHealth(20.0);
                if (this.players.size() >= this.arena.getMinPlayer() && this.gameStatus == GameStatus.READY && !this.isRunning) {
                    this.isRunning = true;
                    this.start();
                }
            }
        }

        private void start() {
            this.lobbyschedulerid = this.plugin.getServer().getScheduler().scheduleSyncRepeatingTask((Plugin)this.plugin, new Runnable(){

                @Override
                public void run() {
                    if (Game.this.players.size() < Game.this.arena.getMinPlayer()) {
                        Game.access$2(Game.this, 10);
                        Game.access$3(Game.this, GameStatus.READY);
                        Game.this.plugin.getServer().getScheduler().cancelTask(Game.this.lobbyschedulerid);
                        Bukkit.getServer().getScheduler().cancelTask(Game.this.lobbyschedulerid);
                        Game.this.sendMessageToGamePlayers("Start was cancelled. More players are required.");
                    } else if (Game.this.lobbycountdown == 0) {
                        Game.this.sendMessageToGamePlayers((Object)ChatColor.GOLD + (Object)ChatColor.BOLD + "Game is starting in 0 seconds.");
                        Game.access$2(Game.this, 10);
                        Game.access$3(Game.this, GameStatus.RUNNING);
                        Game.access$7(Game.this, false);
                        Game.this.plugin.getServer().getScheduler().cancelTask(Game.this.lobbyschedulerid);
                        Bukkit.getServer().getScheduler().cancelTask(Game.this.lobbyschedulerid);
                        Game.this.teleportInArena();
                        Game.this.setUpGame();
                        Game.this.runGame();
                    } else {
                        Game.this.sendMessageToGamePlayers((Object)ChatColor.GOLD + (Object)ChatColor.BOLD + "Game is starting in " + Game.this.lobbycountdown + " seconds.");
                    }
                    Game game = Game.this;
                    Game.access$2(game, game.lobbycountdown - 1);
                }
            }, 0, 20);
        }

        private void teleportInArena() {
            int i = 0;
            for (Player player : this.players) {
                if (this.arena.getSpawnPoints().size() == i) {
                    i = 0;
                }
                player.teleport(this.arena.getSpawnPoints().get(i).getLocation());
                this.playerSpawnPoints.put(player, this.arena.getSpawnPoints().get(i).getLocation());
                player.setHealth(20.0);
                player.setFoodLevel(20);
                ++i;
            }
        }

        private void runGame() {
            this.gameschedulerid = this.plugin.getServer().getScheduler().scheduleSyncRepeatingTask((Plugin)this.plugin, new Runnable(){

                @Override
                public void run() {
                    Game.this.winGame(Game.this.gamecountdown);
                    if (Game.this.gamecountdown == 0) {
                        Game.this.sendMessageToGamePlayers((Object)ChatColor.RED + (Object)ChatColor.BOLD + "Game over! Nobody has won the match!");
                        Game.this.reset(true);
                    } else if (Game.this.gamecountdown == 30) {
                        Game.this.sendMessageToGamePlayers((Object)ChatColor.RED + (Object)ChatColor.BOLD + "30 seconds left.");
                    } else if (Game.this.gamecountdown <= 10) {
                        Game.this.sendMessageToGamePlayers((Object)ChatColor.RED + (Object)ChatColor.BOLD + Game.this.gamecountdown + " seconds left.");
                    } else if (Game.this.gamecountdown == 60) {
                        Game.this.sendMessageToGamePlayers((Object)ChatColor.RED + (Object)ChatColor.BOLD + "1 minute left.");
                    }
                    Game game = Game.this;
                    Game.access$11(game, game.gamecountdown - 1);
                }
            }, 0, 20);
        }

        static /* synthetic */ void access$2(Game game, int n) {
            game.lobbycountdown = n;
        }

        static /* synthetic */ void access$3(Game game, GameStatus gameStatus) {
            game.gameStatus = gameStatus;
        }

        static /* synthetic */ void access$7(Game game, boolean bl) {
            game.isRunning = bl;
        }

        static /* synthetic */ void access$11(Game game, int n) {
            game.gamecountdown = n;
        }

    }

    private static class GameCommandExecutor
    extends BukkitCommands {
        private GameManager manager;

        public GameCommandExecutor(String command, GameManager manager, JavaPlugin plugin) {
            super(command, plugin);
            this.manager = manager;
        }

        @Override
        public final void playerSendCommandEvent(Player player, String[] args) {
            if (args.length == 2 && args[0].equalsIgnoreCase("join")) {
                this.joinArenaCommand(player, args[1]);
            } else if (args.length == 1 && args[0].equalsIgnoreCase("leave")) {
                this.leaveArenaCommand(player);
            } else {
                player.sendMessage(this.manager.HEADLINE);
                player.sendMessage(String.valueOf(this.manager.PREFIX) + "/" + this.manager.COMMAND + " join <arena>");
                player.sendMessage(String.valueOf(this.manager.PREFIX) + "/" + this.manager.COMMAND + " leave");
                player.sendMessage(String.valueOf(this.manager.PREFIX) + "/" + this.manager.COMMAND + "reload");
            }
        }

        private void leaveArenaCommand(Player player) {
            if (!this.manager.isPlayerInGame(player)) {
                player.sendMessage(String.valueOf(this.manager.PREFIX_ERROR) + "You are not in a game.");
            } else {
                this.manager.getGameFromPlayer(player).kickPlayer(player);
                player.sendMessage(String.valueOf(this.manager.PREFIX_SUCCESS) + "You left the game.");
            }
        }

        private void joinArenaCommand(Player player, String gameName) {
            if (this.manager.isPlayerInGame(player)) {
                player.sendMessage(String.valueOf(this.manager.PREFIX_ERROR) + "You are already in a game.");
            } else if (this.manager.getGameByName(gameName) == null) {
                player.sendMessage(String.valueOf(this.manager.PREFIX_ERROR) + "This game does not exist.");
            } else if (!this.manager.getGameByName(gameName).isAvailAble()) {
                player.sendMessage(String.valueOf(this.manager.PREFIX_ERROR) + "This game is not available.");
            } else {
                this.manager.getGameByName(gameName).join(player);
                player.sendMessage(String.valueOf(this.manager.PREFIX_SUCCESS) + "You joined the game.");
            }
        }
    }

    public static abstract class GameListener
    implements Listener,
    IGameListener {
        private GameManager manager;

        public GameListener(GameManager manager, JavaPlugin plugin) {
            this.manager = manager;
            Bukkit.getPluginManager().registerEvents((Listener)this, (Plugin)plugin);
        }

        @EventHandler
        public void onInteract(PlayerInteractEvent e) {
            Player player = e.getPlayer();
            if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                try {
                    if (e.getClickedBlock().getType() == Material.WALL_SIGN || e.getClickedBlock().getType() == Material.SIGN_POST) {
                        if (BukkitUtilities.u().compareItemNames(e.getPlayer().getItemInHand(), String.valueOf(this.manager.ARENA_NAME) + " join sign selector", null, Material.GOLD_SPADE, null)) {
                            Game game = this.manager.getGameByName((String)player.getItemInHand().getItemMeta().getLore().get(1));
                            Sign s = (Sign)e.getClickedBlock().getState();
                            s.setLine(0, (Object)((Object)BukkitChatColor.BLACK) + "[" + this.manager.ARENA_NAME + (Object)((Object)BukkitChatColor.BLACK) + "]");
                            s.setLine(1, game.getArena().getName());
                            s.setLine(2, game.getState().getWord());
                            s.setLine(3, (Object)ChatColor.BOLD + game.getPlayers().size() + "/" + game.getArena().getMaxPlayer());
                            s.update();
                            this.manager.registerSign(s.getLocation());
                        } else if (BukkitUtilities.u().compareItemNames(e.getPlayer().getItemInHand(), String.valueOf(this.manager.ARENA_NAME) + " leave sign selector", null, Material.GOLD_SPADE, null)) {
                            Sign s = (Sign)e.getClickedBlock().getState();
                            s.setLine(0, (Object)((Object)BukkitChatColor.BLACK) + "[" + this.manager.ARENA_NAME + (Object)((Object)BukkitChatColor.BLACK) + "]");
                            s.setLine(1, (Object)((Object)BukkitChatColor.BLACK) + "LEAVE");
                            s.setLine(2, "");
                            s.setLine(3, "");
                            s.update();
                        } else if (player.hasPermission(this.manager.JOIN_PERMISSION)) {
                            Sign s = (Sign)e.getClickedBlock().getState();
                            String[] lines = s.getLines();
                            if (this.manager.getGameByName(lines[1]) != null && lines[0].equals((Object)ChatColor.BOLD + "[" + this.manager.ARENA_NAME + (Object)((Object)BukkitChatColor.BLACK) + "]")) {
                                Game game = this.manager.getGameByName(lines[1]);
                                if (this.manager.isPlayerInGame(player)) {
                                    player.sendMessage(String.valueOf(this.manager.PREFIX_ERROR) + "You are already in a game.");
                                } else if (!game.isAvailAble()) {
                                    s.setLine(2, game.getState().getWord());
                                    player.sendMessage(String.valueOf(this.manager.PREFIX_ERROR) + "This game is not available.");
                                } else {
                                    game.join(player);
                                    s.setLine(2, game.getState().getWord());
                                    s.setLine(3, (Object)ChatColor.BOLD + game.getPlayers().size() + "/" + game.getArena().getMaxPlayer());
                                    s.update();
                                    player.sendMessage(String.valueOf(this.manager.PREFIX_SUCCESS) + "You joined the game.");
                                }
                            } else if (lines[1].equals((Object)ChatColor.BOLD + "LEAVE") && lines[0].equals((Object)ChatColor.BOLD + "[" + this.manager.ARENA_NAME + (Object)((Object)BukkitChatColor.BLACK) + "]")) {
                                if (!this.manager.isPlayerInGame(player)) {
                                    player.sendMessage(String.valueOf(this.manager.PREFIX_ERROR) + "You are not in a game.");
                                } else {
                                    this.manager.getGameFromPlayer(player).kickPlayer(player);
                                    player.sendMessage(String.valueOf(this.manager.PREFIX_SUCCESS) + "You left the game.");
                                }
                            }
                        }
                    }
                }
                catch (Exception s) {
                    // empty catch block
                }
            }
        }

        @EventHandler
        public void onPlayerLeaveArenaEvent(PlayerQuitEvent event) {
            if (this.manager.isPlayerInGame(event.getPlayer())) {
                this.manager.getGameFromPlayer(event.getPlayer()).sendMessageToGamePlayers(String.valueOf(this.manager.PREFIX) + "Player " + event.getPlayer().getName() + "left the game.");
                this.manager.getGameFromPlayer(event.getPlayer()).kickPlayer(event.getPlayer());
            }
        }

        @EventHandler(priority=EventPriority.HIGHEST)
        public void onPLayerDamageEvent(EntityDamageEvent event) {
            if (event.getEntity() instanceof Player && this.manager.isPlayerInGame((Player)event.getEntity())) {
                Damageable damageable = (Damageable)event.getEntity();
                this.onPlayerDamageEvent((Player)event.getEntity(), this.manager.getGameFromPlayer((Player)event.getEntity()), event);
                if (!event.isCancelled() && damageable.getHealth() - event.getDamage() <= 0.0) {
                    event.setCancelled(true);
                    damageable.setHealth(20.0);
                    this.onPlayerDeathEvent((Player)event.getEntity(), this.manager.getGameFromPlayer((Player)event.getEntity()));
                    event.getEntity().teleport(this.manager.getGameFromPlayer((Player)event.getEntity()).getRespawnLocation((Player)event.getEntity()));
                }
            }
        }

        @EventHandler
        public void playerInterActInLobbyEvent(PlayerInteractEvent event) {
            if (this.manager.isPlayerInGame(event.getPlayer()) && this.manager.getGameFromPlayer(event.getPlayer()).getState() == GameStatus.READY) {
                event.setCancelled(true);
            }
        }

        @EventHandler
        public void onPlayerDamageArenaEvent(EntityDamageEvent event) {
            if (event.getEntity() instanceof Player && this.manager.isPlayerInGame((Player)event.getEntity()) && this.manager.getGameFromPlayer((Player)event.getEntity()).getState() == GameStatus.READY) {
                event.setCancelled(true);
            }
        }
    }

    public static abstract class GameManager
    implements IGameManager {
        private String PREFIX;
        private String PREFIX_SUCCESS;
        private String PREFIX_ERROR;
        private String HEADLINE;
        private String COMMAND;
        private String JOIN_PERMISSION;
        private String ARENA_NAME;
        private HashMap<String, Game> games = new HashMap();
        private ArenaManager arenaManager;
        private ArenaSign sign;

        public GameManager(JavaPlugin plugin, String arenaName, String arenaCreatorCommand, String arenaUserCommand, String prefix, String arenCreatorheadline, String arenaUserHeadline, String joinPermission, int selectorID) {
            this.PREFIX = prefix;
            this.PREFIX_SUCCESS = String.valueOf(prefix) + (Object)ChatColor.GREEN;
            this.PREFIX_ERROR = String.valueOf(prefix) + (Object)ChatColor.RED;
            this.HEADLINE = arenaUserHeadline;
            this.COMMAND = arenaUserCommand;
            this.ARENA_NAME = arenaName;
            this.JOIN_PERMISSION = joinPermission;
            this.sign = new ArenaSign(this, plugin);
            this.sign.reload();
            this.sign.startScheduler();
            new libraries.com.shynixn.utilities.BukkitArenaAPI$GameCommandExecutor(arenaUserCommand, this, plugin);
            this.arenaManager = new ArenaManager(plugin, arenaCreatorCommand, arenaName, prefix, arenCreatorheadline, selectorID);
            this.arenaManager.reload();
        }

        protected final void registerSign(Location location) {
            this.sign.addLocation(location);
        }

        public Arena[] getArenas() {
            Arena[] arenas = new Arena[this.arenaManager.getItems().size()];
            int i = 0;
            while (i < arenas.length) {
                arenas[i] = (Arena)this.arenaManager.getItems().get(i);
                ++i;
            }
            return arenas;
        }

        public final boolean isPlayerInGame(Player player) {
            for (Game game : this.games.values()) {
                if (!game.getPlayers().contains((Object)player)) continue;
                return true;
            }
            return false;
        }

        public final Game getGameFromPlayer(Player player) {
            for (Game game : this.games.values()) {
                if (!game.getPlayers().contains((Object)player)) continue;
                return game;
            }
            return null;
        }

        public final Arena getArenaFromLocation(Location location) {
            for (Game game : this.games.values()) {
                if (!game.getArena().isLocationInArea(location)) continue;
                return game.getArena();
            }
            return null;
        }

        public final Arena getArenaByName(String name) {
            return this.arenaManager.getItemFromName(name);
        }

        public final Game getGameByName(String name) {
            for (Game game : this.games.values()) {
                if (!game.getName().equalsIgnoreCase(name)) continue;
                return game;
            }
            return null;
        }

        public final void addGame(Game game) {
            if (game != null) {
                this.games.put(game.getName(), game);
            }
        }

        public final void clearGames() {
            for (Game game : this.games.values()) {
                game.reset(false);
            }
            this.games.clear();
        }
    }

    public static enum GameStatus {
        RUNNING((Object)((Object)BukkitChatColor.BLUE) + "RUNNING"),
        READY((Object)((Object)BukkitChatColor.GREEN) + "JOIN"),
        DISABLED((Object)((Object)BukkitChatColor.DARK_RED) + "DISABLED");
        
        private String name;

        private GameStatus(String name, int n2, String string2) {
            this.name = name;
        }

        public String getWord() {
            return this.name;
        }
    }

    public static class Lobby
    implements Serializable {
        private static final long serialVersionUID = 1;
        private BukkitLocation spawnpoint;
        private BukkitLocation leavepoint;

        public void setSpawnPoint(Location location) {
            this.spawnpoint = new BukkitLocation(location);
        }

        public void setLeavePoint(Location location) {
            this.leavepoint = new BukkitLocation(location);
        }

        public Location getSpawnPoint() {
            return this.spawnpoint.getLocation();
        }

        public Location getLeavePoint() {
            return this.leavepoint.getLocation();
        }
    }

    public static enum LobbyState {
        RUNNING,
        DISABLED,
        ENABLED;
        

        private LobbyState(String string2, int n2) {
        }
    }

    public static class SpawnPoint
    implements Serializable {
        private static final long serialVersionUID = 1;
        private int id;
        private BukkitLocation location;

        public SpawnPoint(int id, Location location) {
            this.id = id;
            this.location = new BukkitLocation(location);
        }

        public SpawnPoint() {
        }

        public int getId() {
            return this.id;
        }

        public Location getLocation() {
            return this.location.getLocation();
        }

        public String toString() {
            return this.location.toString();
        }
    }

}

