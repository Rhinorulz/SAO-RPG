/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  org.bukkit.Material
 *  org.bukkit.Server
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.EntityEquipment
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.PlayerInventory
 *  org.bukkit.inventory.meta.ItemMeta
 *  org.bukkit.inventory.meta.SkullMeta
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.java.JavaPlugin
 *  org.bukkit.scheduler.BukkitScheduler
 */
package libraries.com.shynixn.utilities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public class BukkitShyUtilities {
    public static double getDamageReducedEntity(LivingEntity entity) {
        try {
            EntityEquipment inv = entity.getEquipment();
            ItemStack boots = inv.getBoots();
            ItemStack helmet = inv.getHelmet();
            ItemStack chest = inv.getChestplate();
            ItemStack pants = inv.getLeggings();
            double red = 0.0;
            if (helmet.getType() == Material.LEATHER_HELMET) {
                red += 0.04;
            } else if (helmet.getType() == Material.GOLD_HELMET) {
                red += 0.08;
            } else if (helmet.getType() == Material.CHAINMAIL_HELMET) {
                red += 0.08;
            } else if (helmet.getType() == Material.IRON_HELMET) {
                red += 0.08;
            } else if (helmet.getType() == Material.DIAMOND_HELMET) {
                red += 0.12;
            }
            if (boots.getType() == Material.LEATHER_BOOTS) {
                red += 0.04;
            } else if (boots.getType() == Material.GOLD_BOOTS) {
                red += 0.04;
            } else if (boots.getType() == Material.CHAINMAIL_BOOTS) {
                red += 0.04;
            } else if (boots.getType() == Material.IRON_BOOTS) {
                red += 0.08;
            } else if (boots.getType() == Material.DIAMOND_BOOTS) {
                red += 0.12;
            }
            if (pants.getType() == Material.LEATHER_LEGGINGS) {
                red += 0.08;
            } else if (pants.getType() == Material.GOLD_LEGGINGS) {
                red += 0.12;
            } else if (pants.getType() == Material.CHAINMAIL_LEGGINGS) {
                red += 0.16;
            } else if (pants.getType() == Material.IRON_LEGGINGS) {
                red += 0.2;
            } else if (pants.getType() == Material.DIAMOND_LEGGINGS) {
                red += 0.24;
            }
            if (chest.getType() == Material.LEATHER_CHESTPLATE) {
                red += 0.12;
            } else if (chest.getType() == Material.GOLD_CHESTPLATE) {
                red += 0.2;
            } else if (chest.getType() == Material.CHAINMAIL_CHESTPLATE) {
                red += 0.2;
            } else if (chest.getType() == Material.IRON_CHESTPLATE) {
                red += 0.24;
            } else if (chest.getType() == Material.DIAMOND_CHESTPLATE) {
                red += 0.32;
            }
            return red;
        }
        catch (Exception inv) {
            return 0.0;
        }
    }

    public static String getFormatedDouble(double z) {
        int size = 3;
        if (z < 0.0) {
            ++size;
        }
        String txt = String.valueOf(z);
        String newTxt = "";
        int i = 0;
        while (i < txt.length() && i < size) {
            newTxt = String.valueOf(newTxt) + txt.charAt(i);
            ++i;
        }
        return newTxt;
    }

    public static double getDamageReducedPlayer(Player player) {
        try {
            PlayerInventory inv = player.getInventory();
            ItemStack boots = inv.getBoots();
            ItemStack helmet = inv.getHelmet();
            ItemStack chest = inv.getChestplate();
            ItemStack pants = inv.getLeggings();
            double red = 0.0;
            if (helmet.getType() == Material.LEATHER_HELMET) {
                red += 0.04;
            } else if (helmet.getType() == Material.GOLD_HELMET) {
                red += 0.08;
            } else if (helmet.getType() == Material.CHAINMAIL_HELMET) {
                red += 0.08;
            } else if (helmet.getType() == Material.IRON_HELMET) {
                red += 0.08;
            } else if (helmet.getType() == Material.DIAMOND_HELMET) {
                red += 0.12;
            }
            if (boots.getType() == Material.LEATHER_BOOTS) {
                red += 0.04;
            } else if (boots.getType() == Material.GOLD_BOOTS) {
                red += 0.04;
            } else if (boots.getType() == Material.CHAINMAIL_BOOTS) {
                red += 0.04;
            } else if (boots.getType() == Material.IRON_BOOTS) {
                red += 0.08;
            } else if (boots.getType() == Material.DIAMOND_BOOTS) {
                red += 0.12;
            }
            if (pants.getType() == Material.LEATHER_LEGGINGS) {
                red += 0.08;
            } else if (pants.getType() == Material.GOLD_LEGGINGS) {
                red += 0.12;
            } else if (pants.getType() == Material.CHAINMAIL_LEGGINGS) {
                red += 0.16;
            } else if (pants.getType() == Material.IRON_LEGGINGS) {
                red += 0.2;
            } else if (pants.getType() == Material.DIAMOND_LEGGINGS) {
                red += 0.24;
            }
            if (chest.getType() == Material.LEATHER_CHESTPLATE) {
                red += 0.12;
            } else if (chest.getType() == Material.GOLD_CHESTPLATE) {
                red += 0.2;
            } else if (chest.getType() == Material.CHAINMAIL_CHESTPLATE) {
                red += 0.2;
            } else if (chest.getType() == Material.IRON_CHESTPLATE) {
                red += 0.24;
            } else if (chest.getType() == Material.DIAMOND_CHESTPLATE) {
                red += 0.32;
            }
            return red;
        }
        catch (Exception inv) {
            return 0.0;
        }
    }

    public static void refreshInventory(Player player, JavaPlugin plugin) {
        plugin.getServer().getScheduler().scheduleSyncDelayedTask((Plugin)plugin, new Runnable(){

            @Override
            public void run() {
                Player.this.updateInventory();
            }
        }, 20);
    }

    public static void addLore(ItemStack itemStack, String text) {
        ItemMeta im = itemStack.getItemMeta();
        ArrayList<String> test = new ArrayList<String>();
        if (im.getLore() != null) {
            test.addAll(im.getLore());
        }
        test.add(text);
        im.setLore(test);
        itemStack.setItemMeta(im);
    }

    public static ItemStack activateHead(Player player, ItemStack itemStack) {
        try {
            if (itemStack.getItemMeta() instanceof SkullMeta) {
                SkullMeta meta = (SkullMeta)itemStack.getItemMeta();
                meta.setOwner(player.getName());
                itemStack.setItemMeta((ItemMeta)meta);
            }
        }
        catch (Exception meta) {
            // empty catch block
        }
        return itemStack;
    }

    public static boolean isAxe(ItemStack itemStack) {
        if (itemStack != null && (itemStack.getType() == Material.WOOD_AXE || itemStack.getType() == Material.STONE_AXE || itemStack.getType() == Material.IRON_AXE || itemStack.getType() == Material.GOLD_AXE || itemStack.getType() == Material.DIAMOND_AXE)) {
            return true;
        }
        return false;
    }

    public static boolean isSword(ItemStack itemStack) {
        if (itemStack != null && (itemStack.getType() == Material.WOOD_SWORD || itemStack.getType() == Material.STONE_SWORD || itemStack.getType() == Material.IRON_SWORD || itemStack.getType() == Material.GOLD_SWORD || itemStack.getType() == Material.DIAMOND_SWORD)) {
            return true;
        }
        return false;
    }

    public static boolean isBow(ItemStack itemStack) {
        if (itemStack != null && itemStack.getType() == Material.BOW) {
            return true;
        }
        return false;
    }

}

