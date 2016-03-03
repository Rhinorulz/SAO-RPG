/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  org.bukkit.configuration.file.YamlConfiguration
 *  org.bukkit.entity.EntityType
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.plugin.java.JavaPlugin
 *  org.bukkit.potion.PotionEffect
 *  org.bukkit.potion.PotionEffectType
 */
package com.shynixn.thegreatswordartonlinerpg.gamesystems.mobs;

import com.shynixn.thegreatswordartonlinerpg.cardinal.Cardinal;
import com.shynixn.thegreatswordartonlinerpg.cardinal.CardinalException;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.mobs.MobEquipment;
import com.shynixn.thegreatswordartonlinerpg.resources.enums.CatType;
import com.shynixn.thegreatswordartonlinerpg.resources.enums.Priority;
import com.shynixn.thegreatswordartonlinerpg.resources.enums.VillagerProfession;
import com.shynixn.thegreatswordartonlinerpg.resources.enums.VillagerType;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import libraries.com.shynixn.utilities.BukkitFileManager;
import libraries.com.shynixn.utilities.BukkitObject;
import libraries.com.shynixn.utilities.BukkitUtilities;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public final class MobFileManager
extends BukkitFileManager {
    private File getFolder() {
        return new File(this.getDataFolder(), "resources/mobs");
    }

    public MobFileManager(JavaPlugin plugin, String fileName) {
        super(plugin, fileName);
    }

    @Override
    public boolean save(BukkitObject item) {
        this.createFolder(item);
        MobEquipment equipment = (MobEquipment)item;
        File folder = new File(this.getFolder(), item.getName());
        this.saveMobData(equipment, folder);
        this.saveMobEffectsAndSkills(equipment, folder);
        return true;
    }

    private void saveMobEffectsAndSkills(MobEquipment equipment, File folder) {
        try {
            if (equipment.getPotionEffects() != null) {
                String[] text = new String[equipment.getPotionEffects().length];
                int i = 0;
                while (i < text.length) {
                    PotionEffect p = equipment.getPotionEffects()[i];
                    text[i] = String.valueOf(p.getType().getName()) + ";" + p.getAmplifier() + ";" + p.getDuration();
                    ++i;
                }
                BukkitUtilities.u().writeAllLines(new File(folder, "potioneffects.txt"), text);
            }
            if (equipment.getSkills() != null) {
                int i = 0;
                String[] text = new String[equipment.getSkills().size()];
                for (String key : equipment.getSkillsAndHealth().keySet()) {
                    text[i] = equipment.getSkillsAndHealth().get(key) + ";" + key;
                    ++i;
                }
                BukkitUtilities.u().writeAllLines(new File(folder, "skills.txt"), text);
            }
        }
        catch (Exception e) {
            Cardinal.call().sendException(new CardinalException("Saving effects and skills from mob " + equipment.getName() + " failed", "Mob is not saved", "Make sure the filesystem is correct", Priority.MEDIUM));
        }
    }

    private void saveMobData(MobEquipment equipment, File folder) {
        try {
            YamlConfiguration config = new YamlConfiguration();
            File file = new File(folder, "mob.yml");
            BukkitUtilities.u().createFile(file);
            config.load(file);
            config.set("mob.general.name", (Object)equipment.getName());
            config.set("mob.general.displayname", (Object)equipment.getDisplayName());
            config.set("mob.general.type", (Object)equipment.getEntityType().name());
            config.set("mob.general.health", (Object)equipment.getHealth());
            config.set("mob.general.damage", (Object)equipment.getDamage());
            config.set("mob.general.firedamage", (Object)equipment.getFireTime());
            config.set("mob.general.rider", (Object)equipment.getRidingMob());
            config.set("mob.general.baby", (Object)equipment.isBaby());
            config.set("mob.general.flying", (Object)equipment.isFlying());
            config.save(file);
            config.set("mob.armor.helmet.itemstack", (Object)equipment.getHelmet());
            config.set("mob.armor.helmet.dropchance", (Object)equipment.getHelmetDropChance());
            config.set("mob.armor.chestplate.itemstack", (Object)equipment.getChestPlate());
            config.set("mob.armor.chestplate.dropchance", (Object)equipment.getChestPlateDropChance());
            config.set("mob.armor.leggings.itemstack", (Object)equipment.getLeggings());
            config.set("mob.armor.leggings.dropchance", (Object)equipment.getLeggingsDropChance());
            config.set("mob.armor.boots.itemstack", (Object)equipment.getBoots());
            config.set("mob.armor.boots.dropchance", (Object)equipment.getBootsDropChance());
            config.set("mob.armor.weapon.itemstack", (Object)equipment.getWeapon());
            config.set("mob.armor.weapon.dropchance", (Object)equipment.getWeaponDropChance());
            config.save(file);
            config.set("mob.knockback.up", (Object)equipment.getKnockBackUP());
            config.set("mob.knockback.down", (Object)equipment.getKnockBackDOWN());
            config.set("mob.knockback.left", (Object)equipment.getKnockBackLEFT());
            config.set("mob.knockback.right", (Object)equipment.getKnockBackRIGHT());
            config.set("mob.knockback.forward", (Object)equipment.getKnockBackFORWARD());
            config.set("mob.knockback.back", (Object)equipment.getKnockBackBACK());
            config.save(file);
            config.set("mob.wings.enable", (Object)equipment.isWings());
            config.set("mob.wings.name", (Object)equipment.getWingsName());
            config.save(file);
            config.set("mob.flying.maxhorizontal", (Object)equipment.getMaxLeft());
            config.set("mob.flying.minhorizontal", (Object)equipment.getMinLeft());
            config.set("mob.flying.maxvertical", (Object)equipment.getMaxHight());
            config.set("mob.flying.minvertical", (Object)equipment.getMinHight());
            config.save(file);
            config.set("mob.properties.daylightburn", (Object)equipment.isDayLightBurn());
            config.set("mob.properties.damageable", (Object)equipment.isDamageAble());
            config.set("mob.properties.damaging", (Object)equipment.isDoesDamage());
            config.set("mob.properties.movable", (Object)equipment.isMoveAble());
            config.set("mob.properties.attacking", (Object)equipment.isAttacking());
            config.set("mob.properties.classicdrops", (Object)equipment.isClassicDrops());
            config.set("mob.properties.showhealth", (Object)equipment.isShowingHealth());
            config.set("mob.properties.showname", (Object)equipment.isShowingName());
            config.save(file);
            config.set("mob.special.slimesize", (Object)equipment.getSlimeSize());
            config.set("mob.special.villagerprofession", (Object)equipment.getProfession().name().toLowerCase());
            config.set("mob.special.villagertype", (Object)equipment.getVillagerType().name().toLowerCase());
            config.set("mob.special.skeletonwithermode", (Object)equipment.isWitherSkeleton());
            config.set("mob.special.catcolor", (Object)equipment.getCatType().name().toLowerCase());
            config.set("mob.special.catmode", (Object)equipment.isCatMode());
            config.set("mob.special.dogmode", (Object)equipment.isDogMode());
            config.set("mob.special.creeperpowered", (Object)equipment.isPowered());
            config.set("mob.special.creeperexplosionradius", (Object)equipment.getExplosionRadius());
            config.set("mob.special.creeperdestroyblocks", (Object)equipment.isExplosionDestroy());
            config.save(file);
        }
        catch (Exception e) {
            e.printStackTrace();
            Cardinal.call().sendException(new CardinalException("Saving data from mob " + equipment.getName() + " failed", "Mob is not saved", "Make sure the filesystem is correct", Priority.MEDIUM));
        }
    }

    private void createFolder(BukkitObject item) {
        File mobfolder;
        if (item != null && !(mobfolder = new File(this.getFolder(), item.getName())).exists()) {
            mobfolder.mkdir();
        }
    }

    @Override
    public boolean delete(BukkitObject item) {
        try {
            this.createFolder(item);
            BukkitUtilities.u().saveFolderDeleting(new File(this.getFolder(), item.getName()));
        }
        catch (Exception e) {
            Cardinal.call().sendException(new CardinalException("Deleting data from mob " + item.getName() + " failed", "Mob is not deleted", "Make sure the filesystem is correct", Priority.MEDIUM));
            return false;
        }
        return true;
    }

    @Override
    public BukkitObject[] load() {
        Cardinal.getLogger().logHeadLine("Loading mobs");
        ArrayList<MobEquipment> equipments = new ArrayList<MobEquipment>();
        this.createFolder(null);
        String[] arrstring = this.getFolder().list();
        int n = arrstring.length;
        int n2 = 0;
        while (n2 < n) {
            String file = arrstring[n2];
            File folder = new File(this.getFolder(), file);
            try {
                MobEquipment mob = this.loadMobData(folder);
                if (mob != null) {
                    String[] parts;
                    String[] arrstring2 = BukkitUtilities.u().readAllLines(new File(folder, "skills.txt"));
                    int n3 = arrstring2.length;
                    int n4 = 0;
                    while (n4 < n3) {
                        String s = arrstring2[n4];
                        parts = s.split(";");
                        mob.addSkill(Double.parseDouble(parts[0]), parts[1]);
                        ++n4;
                    }
                    ArrayList<PotionEffect> potionEffects = new ArrayList<PotionEffect>();
                    parts = BukkitUtilities.u().readAllLines(new File(folder, "potioneffects.txt"));
                    int n5 = parts.length;
                    n3 = 0;
                    while (n3 < n5) {
                        String s = parts[n3];
                        String[] parts2 = s.split(";");
                        potionEffects.add(new PotionEffect(PotionEffectType.getByName((String)parts2[0]), Integer.parseInt(parts2[2]), Integer.parseInt(parts2[1])));
                        ++n3;
                    }
                    mob.setPotionEffects(potionEffects.toArray((T[])new PotionEffect[potionEffects.size()]));
                    equipments.add(mob);
                }
            }
            catch (Exception e) {
                Cardinal.call().sendException(new CardinalException("Folder " + folder + " cannot be loaded", "Mob is missing", "Delete or fix the file", Priority.MEDIUM));
            }
            ++n2;
        }
        Cardinal.getLogger().logHeadLine("Completed");
        return equipments.toArray(new MobEquipment[equipments.size()]);
    }

    private EntityType getEntityType(String value) {
        EntityType[] arrentityType = EntityType.values();
        int n = arrentityType.length;
        int n2 = 0;
        while (n2 < n) {
            EntityType entityType = arrentityType[n2];
            if (entityType.name().equalsIgnoreCase(value)) {
                return entityType;
            }
            ++n2;
        }
        return null;
    }

    private MobEquipment loadMobData(File folder) {
        File file = new File(folder, "mob.yml");
        try {
            YamlConfiguration config = new YamlConfiguration();
            config.load(file);
            if (this.getEntityType(config.getString("mob.general.type")) != null) {
                MobEquipment mobEquipment = new MobEquipment(config.getString("mob.general.name"), config.getString("mob.general.displayname"), this.getEntityType(config.getString("mob.general.type")));
                mobEquipment.setHealth(config.getDouble("mob.general.health"));
                mobEquipment.setDamage(config.getDouble("mob.general.damage"));
                mobEquipment.setFireTime(config.getInt("mob.general.firedamage"));
                mobEquipment.setRidingMob(config.getString("mob.general.rider"));
                mobEquipment.setBaby(config.getBoolean("mob.general.baby"));
                mobEquipment.setFlying(config.getBoolean("mob.general.flying"));
                mobEquipment.setHelmet(config.getItemStack("mob.armor.helmet.itemstack"));
                mobEquipment.setHelmetDropChance(config.getDouble("mob.armor.helmet.dropchance"));
                mobEquipment.setChestPlate(config.getItemStack("mob.armor.chestplate.itemstack"));
                mobEquipment.setChestPlateDropChance(config.getDouble("mob.armor.chestplate.dropchance"));
                mobEquipment.setLeggings(config.getItemStack("mob.armor.leggings.itemstack"));
                mobEquipment.setLeggingsDropChance(config.getDouble("mob.armor.leggings.dropchance"));
                mobEquipment.setBoots(config.getItemStack("mob.armor.boots.itemstack"));
                mobEquipment.setBootsDropChance(config.getDouble("mob.armor.boots.dropchance"));
                mobEquipment.setWeapon(config.getItemStack("mob.armor.weapon.itemstack"));
                mobEquipment.setWeaponDropChance(config.getDouble("mob.armor.weapon.dropchance"));
                mobEquipment.setKnockBackUP(config.getDouble("mob.knockback.up"));
                mobEquipment.setKnockBackDOWN(config.getDouble("mob.knockback.down"));
                mobEquipment.setKnockBackLEFT(config.getDouble("mob.knockback.left"));
                mobEquipment.setKnockBackRIGHT(config.getDouble("mob.knockback.right"));
                mobEquipment.setKnockBackFORWARD(config.getDouble("mob.knockback.forward"));
                mobEquipment.setKnockBackBACK(config.getDouble("mob.knockback.back"));
                mobEquipment.setWings(config.getBoolean("mob.wings.enable"));
                mobEquipment.setWingsName(config.getString("mob.wings.name"));
                if (mobEquipment.getWingsName() == null) {
                    mobEquipment.setWingsName("");
                }
                mobEquipment.setMaxHight(config.getDouble("mob.flying.maxvertical"));
                mobEquipment.setMinHight(config.getDouble("mob.flying.minvertical"));
                mobEquipment.setMaxLeft(config.getDouble("mob.flying.maxhorizontal"));
                mobEquipment.setMinLeft(config.getDouble("mob.flying.minhorizontal"));
                mobEquipment.setDayLightBurn(config.getBoolean("mob.properties.daylightburn"));
                mobEquipment.setDamageAble(config.getBoolean("mob.properties.damageable"));
                mobEquipment.setDoesDamage(config.getBoolean("mob.properties.damaging"));
                mobEquipment.setMoveAble(config.getBoolean("mob.properties.movable"));
                mobEquipment.setAttacking(config.getBoolean("mob.properties.attacking"));
                mobEquipment.setClassicDrops(config.getBoolean("mob.properties.classicdrops"));
                mobEquipment.setShowingHealth(config.getBoolean("mob.properties.showhealth"));
                mobEquipment.setShowingName(config.getBoolean("mob.properties.showname"));
                mobEquipment.setSlimeSize(config.getInt("mob.special.slimesize"));
                mobEquipment.setVillagerType(VillagerType.getVillagerType(config.getString("mob.special.villagertype")));
                mobEquipment.setProfession(VillagerProfession.getVillagerProfession(config.getString("mob.special.villagerprofession")));
                mobEquipment.setCatType(CatType.getCatTypeFromName(config.getString("mob.special.catcolor")));
                mobEquipment.setWitherSkeleton(config.getBoolean("mob.special.skeletonwithermode"));
                mobEquipment.setCatMode(config.getBoolean("mob.special.catmode"));
                mobEquipment.setDogMode(config.getBoolean("mob.special.dogmode"));
                mobEquipment.setPowered(config.getBoolean("mob.special.creeperpowered"));
                mobEquipment.setExplosionRadius(config.getInt("mob.special.creeperexplosionradius"));
                mobEquipment.setExplosionDestroy(config.getBoolean("mob.special.creeperdestroyblocks"));
                return mobEquipment;
            }
            return null;
        }
        catch (Exception e) {
            e.printStackTrace();
            Cardinal.call().sendException(new CardinalException("File " + file + " cannot be loaded", "Mob is missing", "Delete or fix the file", Priority.MEDIUM));
            return null;
        }
    }
}

