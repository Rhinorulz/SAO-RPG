/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.block.Block
 *  org.bukkit.block.BlockState
 *  org.bukkit.block.Sign
 *  org.bukkit.configuration.file.FileConfiguration
 *  org.bukkit.configuration.file.YamlConfiguration
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.block.Action
 *  org.bukkit.event.player.PlayerInteractEvent
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.PlayerInventory
 *  org.bukkit.inventory.meta.ItemMeta
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.java.JavaPlugin
 *  org.bukkit.potion.PotionEffect
 *  org.bukkit.potion.PotionEffectType
 */
package libraries.com.shynixn.utilities;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import libraries.com.shynixn.utilities.BukkitChatColor;
import libraries.com.shynixn.utilities.BukkitCommands;
import libraries.com.shynixn.utilities.BukkitFileManager;
import libraries.com.shynixn.utilities.BukkitManager;
import libraries.com.shynixn.utilities.BukkitObject;
import libraries.com.shynixn.utilities.BukkitPotionEffect;
import libraries.com.shynixn.utilities.BukkitUtilities;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class BukkitKitAPI {

    private static class Kit
    extends BukkitObject
    implements Serializable {
        private static final long serialVersionUID = 1;
        private ItemStack[] armorcontents;
        private ItemStack[] contents;
        private BukkitPotionEffect[] potionEffects;

        public Kit(String name, String displayName, ItemStack[] contents, ItemStack[] armorContents, PotionEffect[] potionEffects) {
            super(name, displayName);
            this.contents = contents;
            this.armorcontents = armorContents;
            this.potionEffects = BukkitPotionEffect.convertToBukkitPotionEffects(potionEffects);
        }

        public ItemStack[] getInventoryContents() {
            return this.contents;
        }

        public ItemStack[] getArmorContents() {
            return this.armorcontents;
        }

        public BukkitPotionEffect[] getPotionEffects() {
            return this.potionEffects;
        }
    }

    private static class KitCommandExecutor
    extends BukkitCommands {
        private KitManager kitManager;

        public KitCommandExecutor(String command, JavaPlugin plugin, KitManager kitManager) {
            super(command, plugin);
            this.kitManager = kitManager;
        }

        @Override
        public void playerSendCommandEvent(Player player, String[] args) {
            if (args.length == 2 && args[0].equalsIgnoreCase("create")) {
                this.createKit(player, args[1]);
            } else if (args.length == 2 && args[0].equalsIgnoreCase("delete")) {
                this.deleteKit(player, args[1]);
            } else if (args.length == 2 && args[0].equalsIgnoreCase("kitsign")) {
                this.kitsignConverter(player, args[1]);
            } else if (args.length == 1 && args[0].equalsIgnoreCase("list")) {
                this.printsKits(player);
            } else {
                player.sendMessage(this.kitManager.HEADLINE);
                player.sendMessage(String.valueOf(this.kitManager.PREFIX) + "/" + this.kitManager.COMMAND + " create <name>");
                player.sendMessage(String.valueOf(this.kitManager.PREFIX) + "/" + this.kitManager.COMMAND + " delete <name>");
                player.sendMessage(String.valueOf(this.kitManager.PREFIX) + "/" + this.kitManager.COMMAND + " kitsign <name>");
                player.sendMessage(String.valueOf(this.kitManager.PREFIX) + "/" + this.kitManager.COMMAND + " list");
            }
        }

        private void kitsignConverter(Player player, String kitname) {
            if (!this.kitManager.getItemKeys().contains(kitname)) {
                player.sendMessage(String.valueOf(this.kitManager.PREFIX_ERROR) + "This kit does not exist.");
            } else {
                player.getInventory().addItem(new ItemStack[]{BukkitUtilities.u().nameItem(new ItemStack(Material.GOLD_SWORD), (Object)((Object)BukkitChatColor.GREEN) + "Sign Selector", new String[]{(Object)ChatColor.GREEN + "Rightclick on a sign to convert it into a kit sign.", kitname})});
            }
        }

        private void deleteKit(Player player, String kitname) {
            if (!this.kitManager.getItemKeys().contains(kitname)) {
                player.sendMessage(String.valueOf(this.kitManager.PREFIX_ERROR) + "This kit does not exist.");
            } else {
                this.kitManager.removeItem(this.kitManager.getItemFromName(kitname));
                player.sendMessage(String.valueOf(this.kitManager.PREFIX_SUCCESS) + "Removed kit " + kitname + ".");
            }
        }

        private void createKit(Player player, String name) {
            if (this.kitManager.getItemKeys().contains(name)) {
                player.sendMessage(String.valueOf(this.kitManager.PREFIX_ERROR) + "This kit does already exist.");
            } else {
                Kit kit = new Kit(name, name, player.getInventory().getContents(), player.getInventory().getArmorContents(), player.getActivePotionEffects().toArray((T[])new PotionEffect[player.getActivePotionEffects().size()]));
                this.kitManager.addItem(kit);
                player.sendMessage(String.valueOf(this.kitManager.PREFIX_SUCCESS) + "Created kit " + kit.getName() + ".");
            }
        }

        private void printsKits(Player player) {
            player.sendMessage((Object)ChatColor.WHITE + (Object)ChatColor.BOLD + (Object)ChatColor.ITALIC + "Registered Kits:");
            for (BukkitObject object : this.kitManager.getItems()) {
                player.sendMessage((Object)ChatColor.GRAY + "Kit: " + object.getName() + " " + object.getDisplayName());
            }
        }
    }

    private static class KitFilekitManager
    extends BukkitFileManager {
        public KitFilekitManager(JavaPlugin plugin, String fileName) {
            super(plugin, fileName);
        }

        @Override
        public boolean save(BukkitObject item) {
            try {
                File kitfolder;
                Kit kit = (Kit)item;
                File mainfolder = new File(this.getDataFolder() + "/" + "Kits");
                if (!mainfolder.exists()) {
                    mainfolder.mkdir();
                }
                if (!(kitfolder = new File(this.getDataFolder() + "/" + "Kits" + "/" + "Kit_" + kit.getName())).exists()) {
                    kitfolder.mkdir();
                }
                File contentsfile = new File(kitfolder, "contents.yml");
                File armorfile = new File(kitfolder, "armor.yml");
                if (contentsfile.exists()) {
                    contentsfile.delete();
                }
                if (armorfile.exists()) {
                    armorfile.delete();
                }
                contentsfile.createNewFile();
                armorfile.createNewFile();
                YamlConfiguration dataconfig = new YamlConfiguration();
                dataconfig.load(contentsfile);
                this.saveInvData(kit.getInventoryContents(), (FileConfiguration)dataconfig);
                dataconfig.save(contentsfile);
                dataconfig.load(armorfile);
                this.saveInvData(kit.getArmorContents(), (FileConfiguration)dataconfig);
                dataconfig.save(armorfile);
                ArrayList<String> poitons = new ArrayList<String>();
                BukkitPotionEffect[] arrbukkitPotionEffect = kit.getPotionEffects();
                int n = arrbukkitPotionEffect.length;
                int n2 = 0;
                while (n2 < n) {
                    BukkitPotionEffect potionEffect = arrbukkitPotionEffect[n2];
                    PotionEffect p = potionEffect.getPotionEffect();
                    poitons.add(String.valueOf(p.getType().getId()) + ";" + p.getDuration() + ";" + p.getAmplifier());
                    ++n2;
                }
                BukkitUtilities.u().writeAllLines(new File(kitfolder, "potions.txt"), poitons);
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
                File kitfolder;
                File mainfolder = new File(this.getDataFolder() + "/" + "Kits");
                if (!mainfolder.exists()) {
                    mainfolder.mkdir();
                }
                if (!(kitfolder = new File(this.getDataFolder() + "/" + "Kits" + "/" + "Kit_" + item.getName())).exists()) {
                    kitfolder.mkdir();
                }
                if (kitfolder.exists()) {
                    BukkitUtilities.u().saveFolderDeleting(kitfolder);
                }
            }
            catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }

        @Override
        public BukkitObject[] load() {
            ArrayList<Kit> kits = new ArrayList<Kit>();
            try {
                File mainfolder = new File(this.getDataFolder() + "/" + "Kits");
                if (!mainfolder.exists()) {
                    mainfolder.mkdir();
                }
                String[] arrstring = mainfolder.list();
                int n = arrstring.length;
                int n2 = 0;
                while (n2 < n) {
                    String folder = arrstring[n2];
                    File contentsfile = new File(mainfolder + "/" + folder, "contents.yml");
                    File armorfile = new File(mainfolder + "/" + folder, "armor.yml");
                    YamlConfiguration dataconfig = new YamlConfiguration();
                    String[] parts2 = folder.split("_");
                    dataconfig.load(contentsfile);
                    ItemStack[] contents = this.getContents((FileConfiguration)dataconfig, 36);
                    dataconfig.load(armorfile);
                    ItemStack[] armorcontents = this.getContents((FileConfiguration)dataconfig, 4);
                    ArrayList<PotionEffect> potionEffects = new ArrayList<PotionEffect>();
                    String[] arrstring2 = BukkitUtilities.u().readAllLines(new File(mainfolder + "/" + folder, "potions.txt"));
                    int n3 = arrstring2.length;
                    int n4 = 0;
                    while (n4 < n3) {
                        String s = arrstring2[n4];
                        String[] parts = s.split(";");
                        potionEffects.add(new PotionEffect(PotionEffectType.getById((int)Integer.parseInt(parts[0])), Integer.parseInt(parts[1]), Integer.parseInt(parts[2])));
                        ++n4;
                    }
                    Kit kit = new Kit(parts2[1], parts2[1], contents, armorcontents, potionEffects.toArray((T[])new PotionEffect[potionEffects.size()]));
                    kits.add(kit);
                    ++n2;
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            return kits.toArray(new Kit[kits.size()]);
        }

        private ItemStack[] getContents(FileConfiguration file, int number) {
            ItemStack[] items = new ItemStack[number];
            int i = 0;
            while (i < number) {
                if (file.contains(String.valueOf(i))) {
                    items[i] = file.getItemStack(String.valueOf(i));
                }
                ++i;
            }
            return items;
        }

        private void saveInvData(ItemStack[] contents, FileConfiguration dataconfig) {
            int i = 0;
            while (i < contents.length) {
                dataconfig.set(String.valueOf(i), (Object)contents[i]);
                ++i;
            }
        }
    }

    private static class KitListener
    implements Listener {
        private KitManager kitManager;

        public KitListener(KitManager kitManager) {
            this.kitManager = kitManager;
        }

        @EventHandler
        public void onInteract(PlayerInteractEvent e) {
            Player player = e.getPlayer();
            if (e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getClickedBlock() != null && (e.getClickedBlock().getType() == Material.WALL_SIGN || e.getClickedBlock().getType() == Material.SIGN_POST)) {
                if (BukkitUtilities.u().compareItemNames(e.getPlayer().getItemInHand(), (Object)((Object)BukkitChatColor.GREEN) + "Sign Selector", null, Material.GOLD_SWORD, null)) {
                    Sign s = (Sign)e.getClickedBlock().getState();
                    s.setLine(0, (Object)ChatColor.BLACK + (Object)ChatColor.BOLD + "[Kit]");
                    s.setLine(1, (String)player.getItemInHand().getItemMeta().getLore().get(1));
                    s.setLine(2, "");
                    s.setLine(3, "");
                    s.update();
                } else {
                    Sign s = (Sign)e.getClickedBlock().getState();
                    String[] lines = s.getLines();
                    if (this.kitManager.getItemKeys().contains(lines[1])) {
                        Kit kit = (Kit)this.kitManager.getItemFromName(lines[1]);
                        if (player.hasPermission(String.valueOf(this.kitManager.KITUSEPERMISSION) + kit.getName()) || player.hasPermission(String.valueOf(this.kitManager.KITUSEPERMISSION) + "all")) {
                            BukkitUtilities.u().clearCompleteInventory(player);
                            player.getInventory().setArmorContents(kit.getArmorContents());
                            player.getInventory().setContents(kit.getInventoryContents());
                            BukkitPotionEffect[] arrbukkitPotionEffect = kit.getPotionEffects();
                            int n = arrbukkitPotionEffect.length;
                            int n2 = 0;
                            while (n2 < n) {
                                BukkitPotionEffect effect = arrbukkitPotionEffect[n2];
                                player.addPotionEffect(effect.getPotionEffect());
                                ++n2;
                            }
                            player.updateInventory();
                        }
                    }
                }
            }
        }
    }

    public static class KitManager
    extends BukkitManager {
        private String HEADLINE;
        private String PREFIX;
        private String PREFIX_SUCCESS;
        private String PREFIX_ERROR;
        private String KITUSEPERMISSION;
        private String COMMAND;

        public KitManager(String command, String prefix, String headline, String kitusepermission, JavaPlugin plugin) {
            super(new KitFilekitManager(plugin, "kits.dat"), BukkitManager.SaveType.SINGEL);
            this.COMMAND = command;
            this.PREFIX = prefix;
            this.PREFIX_ERROR = String.valueOf(this.PREFIX) + (Object)ChatColor.RED;
            this.PREFIX_SUCCESS = String.valueOf(this.PREFIX) + (Object)ChatColor.GREEN;
            this.HEADLINE = headline;
            this.KITUSEPERMISSION = kitusepermission;
            new libraries.com.shynixn.utilities.BukkitKitAPI$KitCommandExecutor(command, plugin, this);
            Bukkit.getPluginManager().registerEvents((Listener)new KitListener(this), (Plugin)plugin);
        }
    }

}

