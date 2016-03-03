/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  org.bukkit.configuration.file.FileConfiguration
 *  org.bukkit.configuration.file.YamlConfiguration
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.plugin.java.JavaPlugin
 */
package com.shynixn.thegreatswordartonlinerpg.gamesystems.storage;

import com.shynixn.thegreatswordartonlinerpg.gamesystems.storage.PlayerSave;
import com.shynixn.thegreatswordartonlinerpg.resources.enums.InventoryType;
import java.io.File;
import java.util.UUID;
import libraries.com.shynixn.utilities.BukkitFileManager;
import libraries.com.shynixn.utilities.BukkitLocation;
import libraries.com.shynixn.utilities.BukkitObject;
import libraries.com.shynixn.utilities.BukkitUtilities;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public final class StorageFileManager
extends BukkitFileManager {
    public StorageFileManager(JavaPlugin plugin) {
        super(plugin, "");
    }

    @Override
    public boolean save(BukkitObject item) {
        if (item instanceof PlayerSave) {
            try {
                PlayerSave playerSave = (PlayerSave)item;
                YamlConfiguration dataconfig = new YamlConfiguration();
                File file = BukkitUtilities.u().createFile(new File(this.getDataFolder() + "/resources/player", String.valueOf(playerSave.getName()) + ".save"));
                dataconfig.load(file);
                dataconfig.set("uuid", (Object)playerSave.getName());
                dataconfig.set("mclevel", (Object)playerSave.getOnlineMClevel());
                dataconfig.set("mcexp", (Object)Float.valueOf(playerSave.getOnlineMCexp()));
                dataconfig.set("flooraccess", (Object)playerSave.getFormatedFloorText());
                dataconfig.set("floorid", (Object)playerSave.getFloorId());
                dataconfig.set("hunger", (Object)playerSave.getOnlineHunger());
                dataconfig.set("health", (Object)playerSave.getOnlineHealth());
                dataconfig.set("race", (Object)playerSave.getRaceName());
                dataconfig.set("lastlocation.world", (Object)playerSave.getLastLocation().getWorldName());
                dataconfig.set("lastlocation.x", (Object)playerSave.getLastLocation().getX());
                dataconfig.set("lastlocation.y", (Object)playerSave.getLastLocation().getY());
                dataconfig.set("lastlocation.z", (Object)playerSave.getLastLocation().getZ());
                dataconfig.set("lastlocation.yaw", (Object)playerSave.getLastLocation().getYaw());
                dataconfig.set("lastlocation.pitch", (Object)playerSave.getLastLocation().getPitch());
                this.saveContents(playerSave.getOnlineContents(), "contents", (FileConfiguration)dataconfig);
                this.saveContents(playerSave.getOnlineearmorContents(), "armorcontents", (FileConfiguration)dataconfig);
                this.saveContents(playerSave.getSkillbarContents(), "skillcontents", (FileConfiguration)dataconfig);
                this.saveContents(playerSave.getContents(InventoryType.EQUIPMENT_ARMOR_1), "equipmentcontents.armor.1", (FileConfiguration)dataconfig);
                this.saveContents(playerSave.getContents(InventoryType.EQUIPMENT_ARMOR_2), "equipmentcontents.armor.2", (FileConfiguration)dataconfig);
                this.saveContents(playerSave.getContents(InventoryType.EQUIPMENT_ARMOR_3), "equipmentcontents.armor.3", (FileConfiguration)dataconfig);
                this.saveContents(playerSave.getContents(InventoryType.EQUIPMENT_ARMOR_4), "equipmentcontents.armor.4", (FileConfiguration)dataconfig);
                this.saveContents(playerSave.getContents(InventoryType.EQUIPMENT_WEAPON_1), "equipmentcontents.weapon.1", (FileConfiguration)dataconfig);
                this.saveContents(playerSave.getContents(InventoryType.EQUIPMENT_WEAPON_2), "equipmentcontents.weapon.2", (FileConfiguration)dataconfig);
                this.saveContents(playerSave.getContents(InventoryType.EQUIPMENT_WEAPON_3), "equipmentcontents.weapon.3", (FileConfiguration)dataconfig);
                this.saveContents(playerSave.getContents(InventoryType.EQUIPMENT_WEAPON_4), "equipmentcontents.weapon.4", (FileConfiguration)dataconfig);
                this.saveContents(playerSave.getContents(InventoryType.EQUIPMENT_MATERIALS_1), "equipmentcontents.material.1", (FileConfiguration)dataconfig);
                this.saveContents(playerSave.getContents(InventoryType.EQUIPMENT_MATERIALS_2), "equipmentcontents.material.2", (FileConfiguration)dataconfig);
                this.saveContents(playerSave.getContents(InventoryType.EQUIPMENT_MATERIALS_3), "equipmentcontents.material.3", (FileConfiguration)dataconfig);
                this.saveContents(playerSave.getContents(InventoryType.EQUIPMENT_MATERIALS_4), "equipmentcontents.material.4", (FileConfiguration)dataconfig);
                this.saveContents(playerSave.getContents(InventoryType.EQUIPMENT_FOOD_1), "equipmentcontents.food.1", (FileConfiguration)dataconfig);
                this.saveContents(playerSave.getContents(InventoryType.EQUIPMENT_FOOD_2), "equipmentcontents.food.2", (FileConfiguration)dataconfig);
                this.saveContents(playerSave.getContents(InventoryType.EQUIPMENT_FOOD_3), "equipmentcontents.food.3", (FileConfiguration)dataconfig);
                this.saveContents(playerSave.getContents(InventoryType.EQUIPMENT_FOOD_4), "equipmentcontents.food.4", (FileConfiguration)dataconfig);
                this.saveContents(playerSave.getContents(InventoryType.EQUIPMENT_DROPS_1), "equipmentcontents.drops.1", (FileConfiguration)dataconfig);
                this.saveContents(playerSave.getContents(InventoryType.EQUIPMENT_DROPS_2), "equipmentcontents.drops.2", (FileConfiguration)dataconfig);
                this.saveContents(playerSave.getContents(InventoryType.EQUIPMENT_DROPS_3), "equipmentcontents.drops.3", (FileConfiguration)dataconfig);
                this.saveContents(playerSave.getContents(InventoryType.EQUIPMENT_DROPS_4), "equipmentcontents.drops.4", (FileConfiguration)dataconfig);
                dataconfig.save(file);
            }
            catch (Exception e) {
                return false;
            }
        }
        return true;
    }

    public PlayerSave load(Player player) {
        PlayerSave playerSave = new PlayerSave(player.getUniqueId().toString());
        try {
            YamlConfiguration dataconfig = new YamlConfiguration();
            File file = new File(this.getDataFolder() + "/resources/player", String.valueOf(playerSave.getName()) + ".save");
            if (file.exists()) {
                dataconfig.load(file);
                playerSave.setOnlineMClevel(dataconfig.getInt("mclevel"));
                playerSave.setOnlineMCexp((float)dataconfig.getDouble("mcexp"));
                playerSave.setFormatedFloorText(dataconfig.getString("flooraccess"));
                playerSave.setOnlineContents(this.getContents(36, "contents", (FileConfiguration)dataconfig));
                playerSave.setOnlineearmorContents(this.getContents(4, "armorcontents", (FileConfiguration)dataconfig));
                playerSave.setSkillbarContents(this.getContents(36, "skillcontents", (FileConfiguration)dataconfig));
                playerSave.setContents(this.getContents(36, "equipmentcontents.armor.1", (FileConfiguration)dataconfig), InventoryType.EQUIPMENT_ARMOR_1);
                playerSave.setContents(this.getContents(36, "equipmentcontents.armor.2", (FileConfiguration)dataconfig), InventoryType.EQUIPMENT_ARMOR_2);
                playerSave.setContents(this.getContents(36, "equipmentcontents.armor.3", (FileConfiguration)dataconfig), InventoryType.EQUIPMENT_ARMOR_3);
                playerSave.setContents(this.getContents(36, "equipmentcontents.armor.4", (FileConfiguration)dataconfig), InventoryType.EQUIPMENT_ARMOR_4);
                playerSave.setContents(this.getContents(36, "equipmentcontents.weapon.1", (FileConfiguration)dataconfig), InventoryType.EQUIPMENT_WEAPON_1);
                playerSave.setContents(this.getContents(36, "equipmentcontents.weapon.2", (FileConfiguration)dataconfig), InventoryType.EQUIPMENT_WEAPON_2);
                playerSave.setContents(this.getContents(36, "equipmentcontents.weapon.3", (FileConfiguration)dataconfig), InventoryType.EQUIPMENT_WEAPON_3);
                playerSave.setContents(this.getContents(36, "equipmentcontents.weapon.4", (FileConfiguration)dataconfig), InventoryType.EQUIPMENT_WEAPON_4);
                playerSave.setContents(this.getContents(36, "equipmentcontents.material.1", (FileConfiguration)dataconfig), InventoryType.EQUIPMENT_MATERIALS_1);
                playerSave.setContents(this.getContents(36, "equipmentcontents.material.2", (FileConfiguration)dataconfig), InventoryType.EQUIPMENT_MATERIALS_2);
                playerSave.setContents(this.getContents(36, "equipmentcontents.material.3", (FileConfiguration)dataconfig), InventoryType.EQUIPMENT_MATERIALS_3);
                playerSave.setContents(this.getContents(36, "equipmentcontents.material.4", (FileConfiguration)dataconfig), InventoryType.EQUIPMENT_MATERIALS_4);
                playerSave.setContents(this.getContents(36, "equipmentcontents.food.1", (FileConfiguration)dataconfig), InventoryType.EQUIPMENT_FOOD_1);
                playerSave.setContents(this.getContents(36, "equipmentcontents.food.2", (FileConfiguration)dataconfig), InventoryType.EQUIPMENT_FOOD_2);
                playerSave.setContents(this.getContents(36, "equipmentcontents.food.3", (FileConfiguration)dataconfig), InventoryType.EQUIPMENT_FOOD_3);
                playerSave.setContents(this.getContents(36, "equipmentcontents.food.4", (FileConfiguration)dataconfig), InventoryType.EQUIPMENT_FOOD_4);
                playerSave.setContents(this.getContents(36, "equipmentcontents.drops.1", (FileConfiguration)dataconfig), InventoryType.EQUIPMENT_DROPS_1);
                playerSave.setContents(this.getContents(36, "equipmentcontents.drops.2", (FileConfiguration)dataconfig), InventoryType.EQUIPMENT_DROPS_2);
                playerSave.setContents(this.getContents(36, "equipmentcontents.drops.3", (FileConfiguration)dataconfig), InventoryType.EQUIPMENT_DROPS_3);
                playerSave.setContents(this.getContents(36, "equipmentcontents.drops.4", (FileConfiguration)dataconfig), InventoryType.EQUIPMENT_DROPS_4);
                playerSave.setFloorId(dataconfig.getInt("floorid"));
                playerSave.setOnlineHealth(dataconfig.getInt("health"));
                playerSave.setOnlineHunger(dataconfig.getInt("hunger"));
                playerSave.setRaceName(dataconfig.getString("race"));
                playerSave.setLastLocation(new BukkitLocation(dataconfig.getString("lastlocation.world"), dataconfig.getDouble("lastlocation.x"), dataconfig.getDouble("lastlocation.y"), dataconfig.getDouble("lastlocation.z"), dataconfig.getDouble("lastlocation.yaw"), dataconfig.getDouble("lastlocation.pitch")));
            }
        }
        catch (Exception dataconfig) {
            // empty catch block
        }
        return playerSave;
    }

    private void saveContents(ItemStack[] contents, String key, FileConfiguration dataconfig) {
        int i = 0;
        while (i < contents.length) {
            dataconfig.set(String.valueOf(key) + String.valueOf(i), (Object)contents[i]);
            ++i;
        }
    }

    private ItemStack[] getContents(int number, String key, FileConfiguration dataconfig) {
        ItemStack[] items = new ItemStack[number];
        int i = 0;
        while (i < number) {
            if (dataconfig.contains(String.valueOf(key) + String.valueOf(i))) {
                items[i] = dataconfig.getItemStack(String.valueOf(key) + String.valueOf(i));
            }
            ++i;
        }
        return items;
    }
}

