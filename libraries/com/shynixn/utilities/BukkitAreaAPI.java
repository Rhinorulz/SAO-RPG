/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.Server
 *  org.bukkit.World
 *  org.bukkit.block.Block
 *  org.bukkit.block.BlockFace
 *  org.bukkit.block.BlockState
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.block.Action
 *  org.bukkit.event.block.BlockPlaceEvent
 *  org.bukkit.event.player.PlayerInteractEvent
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.PlayerInventory
 *  org.bukkit.material.Directional
 *  org.bukkit.material.MaterialData
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.java.JavaPlugin
 *  org.bukkit.scheduler.BukkitScheduler
 */
package libraries.com.shynixn.utilities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import libraries.com.shynixn.utilities.BukkitCommands;
import libraries.com.shynixn.utilities.BukkitFileManager;
import libraries.com.shynixn.utilities.BukkitLocation;
import libraries.com.shynixn.utilities.BukkitManager;
import libraries.com.shynixn.utilities.BukkitObject;
import libraries.com.shynixn.utilities.BukkitSelector;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.material.Directional;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public final class BukkitAreaAPI {
    public static JavaPlugin PLUGIN;

    private static class AreaCommandExecutor
    extends BukkitCommands {
        private AreaManager manager;

        public AreaCommandExecutor(AreaManager manager, String command, JavaPlugin plugin) {
            super(command, plugin);
            this.manager = manager;
        }

        @Override
        public void playerSendCommandEvent(Player player, String[] args) {
            if (args.length == 3 && args[0].equalsIgnoreCase("create")) {
                this.createAreaCommand(player, args[1], args[2]);
            } else if (args.length == 2 && args[0].equalsIgnoreCase("delete")) {
                this.deleteAreaCommand(player, args[1]);
            } else if (args.length == 1 && args[0].equalsIgnoreCase("save")) {
                this.saveAreaCommand(player);
            } else if (args.length == 1 && args[0].equalsIgnoreCase("reset")) {
                this.resetAreaCommand(player);
            } else if (args.length == 1 && args[0].equalsIgnoreCase("buildingmode")) {
                this.buildModeEnableDisableCommand(player);
            } else if (args.length == 1 && args[0].equalsIgnoreCase("unbreakingmode")) {
                this.unreabkingModeEnableDisableCommand(player);
            } else if (args.length == 1 && args[0].equalsIgnoreCase("list")) {
                this.printAreasCommand(player);
            } else if (args.length == 1 && args[0].equalsIgnoreCase("areaselector")) {
                player.getInventory().addItem(new ItemStack[]{BukkitSelector.selector().getSelector(this.manager.SELECTORID)});
                player.sendMessage(String.valueOf(this.manager.PREFIX_SUCCESS) + "Received " + this.manager.AREANAME + " selector.");
            } else {
                player.sendMessage(this.manager.HEADLINE);
                player.sendMessage(String.valueOf(this.manager.PREFIX) + "/" + this.manager.COMMAND + " create <name> <displayName>");
                player.sendMessage(String.valueOf(this.manager.PREFIX) + "/" + this.manager.COMMAND + " delete <name>");
                player.sendMessage(String.valueOf(this.manager.PREFIX) + "/" + this.manager.COMMAND + " save");
                player.sendMessage(String.valueOf(this.manager.PREFIX) + "/" + this.manager.COMMAND + " reset");
                player.sendMessage(String.valueOf(this.manager.PREFIX) + "/" + this.manager.COMMAND + " unbreakingmode");
                player.sendMessage(String.valueOf(this.manager.PREFIX) + "/" + this.manager.COMMAND + " buildingmode");
                player.sendMessage(String.valueOf(this.manager.PREFIX) + "/" + this.manager.COMMAND + " areaselector");
                player.sendMessage(String.valueOf(this.manager.PREFIX) + "/" + this.manager.COMMAND + " list");
            }
        }

        private void createAreaCommand(Player player, String name, String displayName) {
            if (this.manager.getItemKeys().contains(name)) {
                player.sendMessage(String.valueOf(this.manager.PREFIX_ERROR) + "This " + this.manager.AREANAME + " does already exist.");
            } else if (BukkitSelector.selector().getSelection(player, 1, BukkitSelector.SelectionType.LEFT) == null || BukkitSelector.selector().getSelection(player, 1, BukkitSelector.SelectionType.RIGHT) == null) {
                player.sendMessage(String.valueOf(this.manager.PREFIX_ERROR) + "Select the " + this.manager.AREANAME + " with the " + this.manager.AREANAME + " selector first.");
            } else if (!BukkitSelector.selector().getSelection(player, 1, BukkitSelector.SelectionType.LEFT).getWorld().getName().equals(BukkitSelector.selector().getSelection(player, 1, BukkitSelector.SelectionType.LEFT).getWorld().getName())) {
                player.sendMessage(String.valueOf(this.manager.PREFIX_ERROR) + "The " + this.manager.AREANAME + " cannot be created over two worlds.");
            } else {
                BukkitArea area = new BukkitArea(name, displayName, BukkitSelector.selector().getSelection(player, this.manager.SELECTORID, BukkitSelector.SelectionType.LEFT), BukkitSelector.selector().getSelection(player, this.manager.SELECTORID, BukkitSelector.SelectionType.RIGHT));
                this.manager.addItem(area);
                player.sendMessage(String.valueOf(this.manager.PREFIX_SUCCESS) + "Created " + this.manager.AREANAME + " " + name + ".");
            }
        }

        private void deleteAreaCommand(Player player, String name) {
            if (!this.manager.getItemKeys().contains(name)) {
                player.sendMessage(String.valueOf(this.manager.PREFIX_ERROR) + "This " + this.manager.AREANAME + " does not exist.");
            } else {
                this.manager.removeItem(this.manager.getItemFromName(name));
                player.sendMessage(String.valueOf(this.manager.PREFIX_SUCCESS) + "Removed " + this.manager.AREANAME + " " + name + ".");
            }
        }

        private void saveAreaCommand(Player player) {
            BukkitArea area = this.manager.getAreaFromLocation(player.getLocation());
            if (area == null) {
                player.sendMessage(String.valueOf(this.manager.PREFIX_ERROR) + "You are not in the " + this.manager.AREANAME + ".");
            } else if (area.getEditMode() == AreaEdit.UNBREAKING) {
                player.sendMessage(String.valueOf(this.manager.PREFIX_ERROR) + "You cannot save " + this.manager.AREANAME + " in unbreakingmode.");
            } else {
                area.save();
                player.sendMessage(String.valueOf(this.manager.PREFIX_SUCCESS) + "Saved " + this.manager.AREANAME + ".");
            }
        }

        private void resetAreaCommand(Player player) {
            BukkitArea area = this.manager.getAreaFromLocation(player.getLocation());
            if (area == null) {
                player.sendMessage(String.valueOf(this.manager.PREFIX_ERROR) + "You are not in the " + this.manager.AREANAME + ".");
            } else if (area.getEditMode() == AreaEdit.UNBREAKING) {
                player.sendMessage(String.valueOf(this.manager.PREFIX_ERROR) + "You cannot reset " + this.manager.AREANAME + " in unbreakingmode.");
            } else {
                area.reset();
                player.sendMessage(String.valueOf(this.manager.PREFIX_SUCCESS) + "Reset " + this.manager.AREANAME + ".");
            }
        }

        private void unreabkingModeEnableDisableCommand(Player player) {
            BukkitArea area = this.manager.getAreaFromLocation(player.getLocation());
            if (area == null) {
                player.sendMessage(String.valueOf(this.manager.PREFIX_ERROR) + "You are not in the " + this.manager.AREANAME + ".");
            } else if (area.getEditMode() == AreaEdit.UNBREAKING) {
                area.setEditMode(AreaEdit.NONE);
                area.saveUndrestroyAbleLocations();
                area.reset();
                this.manager.save(area);
                player.sendMessage(String.valueOf(this.manager.PREFIX_SUCCESS) + "Unbreaking mode disabled.");
                player.sendMessage(String.valueOf(this.manager.PREFIX_SUCCESS) + "Stored all bedrock locations and reset " + this.manager.AREANAME + ".");
            } else {
                area.setEditMode(AreaEdit.UNBREAKING);
                area.showUndestroyAbleLocations();
                player.sendMessage(String.valueOf(this.manager.PREFIX_SUCCESS) + "Unbreaking mode enabled.");
            }
        }

        private void buildModeEnableDisableCommand(Player player) {
            BukkitArea area = this.manager.getAreaFromLocation(player.getLocation());
            if (area == null) {
                player.sendMessage(String.valueOf(this.manager.PREFIX_ERROR) + "You are not in the " + this.manager.AREANAME + ".");
            } else if (area.getEditMode() == AreaEdit.UNBREAKING) {
                player.sendMessage(String.valueOf(this.manager.PREFIX_ERROR) + "You cannot edit the " + this.manager.AREANAME + " what is in unbreaking mode.");
            } else if (area.getEditMode() == AreaEdit.BUILDING) {
                area.setEditMode(AreaEdit.NONE);
                player.sendMessage(String.valueOf(this.manager.PREFIX_SUCCESS) + "Building mode disabled.");
            } else {
                area.setEditMode(AreaEdit.BUILDING);
                player.sendMessage(String.valueOf(this.manager.PREFIX_SUCCESS) + "Building mode enabled.");
            }
        }

        private void printAreasCommand(Player player) {
            player.sendMessage((Object)ChatColor.WHITE + (Object)ChatColor.BOLD + (Object)ChatColor.ITALIC + "Registered " + this.manager.AREANAME + "s:");
            for (BukkitObject s : this.manager.getItems()) {
                player.sendMessage((Object)ChatColor.GRAY + s.getName() + " " + s.getDisplayName());
            }
        }
    }

    public static enum AreaEdit {
        NONE,
        UNBREAKING,
        BUILDING;
        

        private AreaEdit(String string2, int n2) {
        }
    }

    private static class AreaListener
    implements Listener {
        private AreaManager manager;

        public AreaListener(AreaManager manager) {
            this.manager = manager;
        }

        @EventHandler
        public void onblockPlaceInArenaEvent(BlockPlaceEvent event) {
            BukkitArea area;
            if (this.manager.getAreaFromLocation(event.getBlock().getLocation()) != null && (area = this.manager.getAreaFromLocation(event.getBlock().getLocation())).getEditMode() == AreaEdit.NONE) {
                for (BukkitLocation location : area.getUndestroyAbleLocations()) {
                    if (!new BukkitLocation(event.getBlock().getLocation()).equals(location)) continue;
                    event.setCancelled(true);
                    break;
                }
            }
        }

        @EventHandler
        public void onDoSomethingInArenaEvent(PlayerInteractEvent event) {
            BukkitArea area;
            if (event.getAction() == Action.LEFT_CLICK_BLOCK && this.manager.getAreaFromLocation(event.getClickedBlock().getLocation()) != null && (area = this.manager.getAreaFromLocation(event.getClickedBlock().getLocation())).getEditMode() == AreaEdit.NONE) {
                for (BukkitLocation location : area.getUndestroyAbleLocations()) {
                    if (!new BukkitLocation(event.getClickedBlock().getLocation()).equals(location)) continue;
                    event.setCancelled(true);
                    break;
                }
            }
        }
    }

    public static class AreaManager
    extends BukkitManager {
        private String AREANAME;
        private String PREFIX;
        private String PREFIX_SUCCESS;
        private String PREFIX_ERROR;
        private String HEADLINE;
        private String COMMAND;
        private int SELECTORID;

        public AreaManager(JavaPlugin plugin, String command, String prefix, String headline, String areaName, String fileName, int selectorID) {
            super(new BukkitFileManager(plugin, fileName), BukkitManager.SaveType.SINGEL);
            this.PREFIX = prefix;
            this.PREFIX_ERROR = String.valueOf(prefix) + (Object)ChatColor.RED;
            this.PREFIX_SUCCESS = String.valueOf(prefix) + (Object)ChatColor.GREEN;
            this.HEADLINE = headline;
            this.COMMAND = command;
            this.AREANAME = areaName;
            this.SELECTORID = selectorID;
            BukkitAreaAPI.PLUGIN = plugin;
            if (command != null) {
                new libraries.com.shynixn.utilities.BukkitAreaAPI$AreaCommandExecutor(this, command, plugin);
            }
            Bukkit.getPluginManager().registerEvents((Listener)new AreaListener(this), (Plugin)plugin);
            BukkitSelector.generate(plugin);
            BukkitSelector.selector().registerSelector(selectorID, Material.GOLD_AXE, (Object)ChatColor.YELLOW + (Object)ChatColor.BOLD + areaName + " " + "selector", (Object)ChatColor.GREEN + "Select an area like wordledit.");
        }

        public BukkitArea getAreaFromLocation(Location location) {
            for (BukkitObject object : this.getItems()) {
                BukkitArea area = (BukkitArea)object;
                if (!area.isLocationInArea(location)) continue;
                return area;
            }
            return null;
        }

        @Override
        public BukkitArea getItemFromName(String name) {
            return (BukkitArea)super.getItemFromName(name);
        }
    }

    public static class BukkitArea
    extends BukkitObject
    implements Serializable {
        private static final long serialVersionUID = 1;
        private int[][][] types;
        private byte[][][] datas;
        private String[][][] blockfaces;
        private int xwidth = 0;
        private int ywidth = 0;
        private int zwidth = 0;
        private BukkitLocation downCorner;
        private BukkitLocation upCorner;
        private List<BukkitLocation> undestroyablelocations = new ArrayList<BukkitLocation>();
        private AreaEdit edit = AreaEdit.NONE;
        private transient int rj;

        public BukkitArea(String name, String displayName, Location corner1, Location corner2) {
            super(name, displayName);
            this.calculateDownLocation(corner1, corner2);
            this.calculateUpLocation(corner1, corner2);
            this.calculateWidth();
            this.save();
        }

        public BukkitArea() {
        }

        public int getTopLength() {
            if (this.xwidth > this.zwidth) {
                return this.xwidth;
            }
            return this.zwidth;
        }

        public void save() {
            this.datas = new byte[this.getXWidth()][this.getYWidth()][this.getZWidth()];
            this.types = new int[this.getXWidth()][this.getYWidth()][this.getZWidth()];
            this.blockfaces = new String[this.getXWidth()][this.getYWidth()][this.getZWidth()];
            Location location = this.downCorner.getLocation();
            int i = 0;
            while (i < this.getXWidth()) {
                int j = 0;
                while (j < this.getYWidth()) {
                    int k = 0;
                    while (k < this.getZWidth()) {
                        Location loc = new Location(location.getWorld(), (double)(location.getBlockX() + i), (double)(location.getBlockY() + j), (double)(location.getBlockZ() + k));
                        this.types[i][j][k] = loc.getBlock().getType().getId();
                        this.datas[i][j][k] = loc.getBlock().getData();
                        this.blockfaces[i][j][k] = loc.getBlock().getState().getData() instanceof Directional ? ((Directional)loc.getBlock().getState().getData()).getFacing().name() : "NONE";
                        ++k;
                    }
                    ++j;
                }
                ++i;
            }
        }

        public Location getCenter() {
            return new Location(this.getDownCornerLocation().getWorld(), (double)(this.getDownCornerLocation().getBlockX() + this.getXWidth() / 2), (double)(this.getDownCornerLocation().getBlockY() + this.getYWidth() / 2), (double)(this.getDownCornerLocation().getBlockZ() + this.getZWidth() / 2));
        }

        public void instantReset() {
            int x = 0;
            while (x < 2) {
                int j = this.getYWidth() - 1;
                while (j != -1) {
                    int i = 0;
                    while (i < this.getXWidth()) {
                        int k = 0;
                        while (k < this.getZWidth()) {
                            Location location = this.downCorner.getLocation();
                            Material tmpMaterial = null;
                            byte tmpdata = 0;
                            Location bewlowloc = null;
                            Location loc = new Location(location.getWorld(), (double)(location.getBlockX() + i), (double)(location.getBlockY() + j), (double)(location.getBlockZ() + k));
                            Material material = Material.getMaterial((int)this.types[i][j][k]);
                            if (material == Material.TORCH || material == Material.REDSTONE_TORCH_ON || material == Material.REDSTONE_TORCH_OFF || material == Material.WALL_SIGN) {
                                bewlowloc = new Location(location.getWorld(), (double)(location.getBlockX() + i), (double)(location.getBlockY() + j - 1), (double)(location.getBlockZ() + k));
                                tmpMaterial = bewlowloc.getBlock().getType();
                                tmpdata = bewlowloc.getBlock().getData();
                                bewlowloc.getBlock().setType(Material.DIRT);
                            }
                            loc.getBlock().setType(material);
                            loc.getBlock().setData(this.datas[i][j][k]);
                            if (loc.getBlock().getState().getData() instanceof Directional) {
                                ((Directional)loc.getBlock().getState().getData()).setFacingDirection(BlockFace.valueOf((String)this.blockfaces[i][j][k]));
                            }
                            if (material == Material.TORCH || material == Material.REDSTONE_TORCH_ON || material == Material.REDSTONE_TORCH_OFF || material == Material.WALL_SIGN) {
                                bewlowloc.getBlock().setType(tmpMaterial);
                                bewlowloc.getBlock().setData(tmpdata);
                            }
                            ++k;
                        }
                        ++i;
                    }
                    --j;
                }
                ++x;
            }
        }

        public void reset() {
            int x = 0;
            while (x < 2) {
                this.rj = this.getYWidth() - 1;
                while (this.rj != -1) {
                    final int j = this.rj;
                    BukkitAreaAPI.PLUGIN.getServer().getScheduler().scheduleSyncDelayedTask((Plugin)BukkitAreaAPI.PLUGIN, new Runnable(){

                        @Override
                        public void run() {
                            int i = 0;
                            while (i < BukkitArea.this.getXWidth()) {
                                int k = 0;
                                while (k < BukkitArea.this.getZWidth()) {
                                    Location location = BukkitArea.this.downCorner.getLocation();
                                    Material tmpMaterial = null;
                                    byte tmpdata = 0;
                                    Location bewlowloc = null;
                                    Location loc = new Location(location.getWorld(), (double)(location.getBlockX() + i), (double)(location.getBlockY() + j), (double)(location.getBlockZ() + k));
                                    Material material = Material.getMaterial((int)BukkitArea.this.types[i][j][k]);
                                    if (material == Material.TORCH || material == Material.REDSTONE_TORCH_ON || material == Material.REDSTONE_TORCH_OFF || material == Material.WALL_SIGN) {
                                        bewlowloc = new Location(location.getWorld(), (double)(location.getBlockX() + i), (double)(location.getBlockY() + j - 1), (double)(location.getBlockZ() + k));
                                        tmpMaterial = bewlowloc.getBlock().getType();
                                        tmpdata = bewlowloc.getBlock().getData();
                                        bewlowloc.getBlock().setType(Material.DIRT);
                                    }
                                    loc.getBlock().setType(material);
                                    loc.getBlock().setData(BukkitArea.this.datas[i][j][k]);
                                    if (loc.getBlock().getState().getData() instanceof Directional) {
                                        ((Directional)loc.getBlock().getState().getData()).setFacingDirection(BlockFace.valueOf((String)BukkitArea.this.blockfaces[i][j][k]));
                                    }
                                    if (material == Material.TORCH || material == Material.REDSTONE_TORCH_ON || material == Material.REDSTONE_TORCH_OFF || material == Material.WALL_SIGN) {
                                        bewlowloc.getBlock().setType(tmpMaterial);
                                        bewlowloc.getBlock().setData(tmpdata);
                                    }
                                    ++k;
                                }
                                ++i;
                            }
                        }
                    }, (long)this.rj * 1);
                    --this.rj;
                }
                ++x;
            }
        }

        public boolean isLocationInArea(Location location) {
            if (this.upCorner.getBlockX() >= location.getBlockX() && this.downCorner.getBlockX() <= location.getBlockX() && this.upCorner.getBlockY() >= location.getBlockY() && this.downCorner.getBlockY() <= location.getBlockY() && this.upCorner.getBlockZ() >= location.getBlockZ() && this.downCorner.getBlockZ() <= location.getBlockZ()) {
                return true;
            }
            return false;
        }

        public void addUndestroyAbleLocation(Location location) {
            this.undestroyablelocations.add(new BukkitLocation(location));
        }

        public List<BukkitLocation> getUndestroyAbleLocations() {
            return this.undestroyablelocations;
        }

        public void showUndestroyAbleLocations() {
            Location location = this.downCorner.getLocation();
            int i = 0;
            while (i < this.getXWidth()) {
                int j = 0;
                while (j < this.getYWidth()) {
                    int k = 0;
                    while (k < this.getZWidth()) {
                        Location loc = new Location(location.getWorld(), (double)(location.getBlockX() + i), (double)(location.getBlockY() + j), (double)(location.getBlockZ() + k));
                        for (BukkitLocation location2 : this.undestroyablelocations) {
                            if (!loc.equals((Object)location2.getLocation())) continue;
                            loc.getBlock().setType(Material.BEDROCK);
                        }
                        ++k;
                    }
                    ++j;
                }
                ++i;
            }
        }

        public void saveUndrestroyAbleLocations() {
            ArrayList<BukkitLocation> undestroyable = new ArrayList<BukkitLocation>();
            Location location = this.getDownCornerLocation();
            int i = 0;
            while (i < this.getXWidth()) {
                int j = 0;
                while (j < this.getYWidth()) {
                    int k = 0;
                    while (k < this.getZWidth()) {
                        Location loc = new Location(location.getWorld(), (double)(location.getBlockX() + i), (double)(location.getBlockY() + j), (double)(location.getBlockZ() + k));
                        if (loc.getBlock().getType().equals((Object)Material.BEDROCK)) {
                            undestroyable.add(new BukkitLocation(loc));
                        }
                        ++k;
                    }
                    ++j;
                }
                ++i;
            }
            this.undestroyablelocations = undestroyable;
        }

        public int getXWidth() {
            return this.xwidth;
        }

        public int getYWidth() {
            return this.ywidth;
        }

        public int getZWidth() {
            return this.zwidth;
        }

        public Location getUpCornerLocation() {
            return this.upCorner.getLocation();
        }

        public Location getDownCornerLocation() {
            return this.downCorner.getLocation();
        }

        private void calculateWidth() {
            this.xwidth = this.upCorner.getBlockX() - this.downCorner.getBlockX() + 1;
            this.ywidth = this.upCorner.getBlockY() - this.downCorner.getBlockY() + 1;
            this.zwidth = this.upCorner.getBlockZ() - this.downCorner.getBlockZ() + 1;
        }

        private void calculateUpLocation(Location corner1, Location corner2) {
            int x = corner1.getBlockX() > corner2.getBlockX() ? corner1.getBlockX() : corner2.getBlockX();
            int y = corner1.getBlockY() > corner2.getBlockY() ? corner1.getBlockY() : corner2.getBlockY();
            int z = corner1.getBlockZ() > corner2.getBlockZ() ? corner1.getBlockZ() : corner2.getBlockZ();
            this.upCorner = new BukkitLocation(new Location(corner1.getWorld(), (double)x, (double)y, (double)z));
        }

        private void calculateDownLocation(Location corner1, Location corner2) {
            int x = corner1.getBlockX() < corner2.getBlockX() ? corner1.getBlockX() : corner2.getBlockX();
            int y = corner1.getBlockY() < corner2.getBlockY() ? corner1.getBlockY() : corner2.getBlockY();
            int z = corner1.getBlockZ() < corner2.getBlockZ() ? corner1.getBlockZ() : corner2.getBlockZ();
            this.downCorner = new BukkitLocation(new Location(corner1.getWorld(), (double)x, (double)y, (double)z));
        }

        public void setEditMode(AreaEdit edit) {
            this.edit = edit;
        }

        public AreaEdit getEditMode() {
            return this.edit;
        }

    }

}

