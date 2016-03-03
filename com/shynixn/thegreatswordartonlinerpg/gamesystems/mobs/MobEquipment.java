/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.EntityType
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.potion.PotionEffect
 */
package com.shynixn.thegreatswordartonlinerpg.gamesystems.mobs;

import com.shynixn.thegreatswordartonlinerpg.gamesystems.skills.Skill;
import com.shynixn.thegreatswordartonlinerpg.resources.enums.CatType;
import com.shynixn.thegreatswordartonlinerpg.resources.enums.VillagerProfession;
import com.shynixn.thegreatswordartonlinerpg.resources.enums.VillagerType;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import libraries.com.shynixn.utilities.BukkitObject;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

public class MobEquipment
extends BukkitObject {
    private static final long serialVersionUID = 1;
    private double health = 10.0;
    private double damage = 5.0;
    private EntityType entityType;
    private double bootsDropChance;
    private double leggingsDropChance;
    private double chestPlateDropChance;
    private double helmetDropChance;
    private double weaponDropChance;
    private ItemStack helmet;
    private ItemStack chestPlate;
    private ItemStack leggings;
    private ItemStack boots;
    private ItemStack weapon;
    private HashMap<String, Double> skills = new HashMap();
    private PotionEffect[] potionEffects;
    private boolean dayLightBurn = true;
    private boolean damageAble = true;
    private boolean doesDamage = true;
    private boolean moveAble = true;
    private boolean attacking = true;
    private boolean classicDrops = true;
    private boolean showingHealth = true;
    private boolean showingName = true;
    private String ridingMob = "";
    private double knockBackUP = 0.0;
    private double knockBackDOWN = 0.0;
    private double knockBackFORWARD = 0.0;
    private double knockBackBACK = 0.0;
    private double knockBackLEFT = 0.0;
    private double knockBackRIGHT = 0.0;
    private boolean flying = false;
    private double maxHight = 0.0;
    private double minHight = 0.0;
    private double minLeft = 0.0;
    private double maxLeft = 0.0;
    private boolean wings = false;
    private String wingsName = "";
    private int fireTime = 0;
    private VillagerType villagerType = VillagerType.PEASANT;
    private int slimeSize = 5;
    private VillagerProfession profession = VillagerProfession.FARMER;
    private boolean isBaby = false;
    private boolean isWitherSkeleton = false;
    private boolean catMode = false;
    private boolean dogMode = false;
    private boolean powered = false;
    private CatType catType = CatType.WHITE;
    private int explosionRadius = 3;
    private boolean explosionDestroy = false;

    public MobEquipment(String name, String displayName, EntityType entityType) {
        super(name, displayName);
        this.entityType = entityType;
    }

    public MobEquipment() {
    }

    public MobEquipment(String name, String displayName, double health, double damage, EntityType entityType, double bootsDropChance, double leggingsDropChance, double chestPlateDropChance, double helmetDropChance, double weaponDropChance, ItemStack helmet, ItemStack chestPlate, ItemStack leggings, ItemStack boots, ItemStack weapon, HashMap<String, Double> skills, PotionEffect[] potionEffects, boolean dayLightBurn, boolean damageAble, boolean doesDamage, boolean moveAble, boolean attacking, boolean classicDrops, boolean showingHealth, boolean showingName, String ridingMob, double knockBackUP, double knockBackDOWN, double knockBackFORWARD, double knockBackBACK, double knockBackLEFT, double knockBackRIGHT, boolean flying, double maxHight, double minHight, double minLeft, double maxLeft, boolean wings, String wingsName, int fireTime, VillagerType villagerType, int slimeSize, VillagerProfession profession, boolean isBaby, boolean isWitherSkeleton, boolean catMode, boolean dogMode, boolean powered, CatType catType, int explosionRadius, boolean explosionDestroy) {
        super(name, displayName);
        this.health = health;
        this.damage = damage;
        this.entityType = entityType;
        this.bootsDropChance = bootsDropChance;
        this.leggingsDropChance = leggingsDropChance;
        this.chestPlateDropChance = chestPlateDropChance;
        this.helmetDropChance = helmetDropChance;
        this.weaponDropChance = weaponDropChance;
        this.helmet = helmet;
        this.chestPlate = chestPlate;
        this.leggings = leggings;
        this.boots = boots;
        this.weapon = weapon;
        this.skills = skills;
        this.potionEffects = potionEffects;
        this.dayLightBurn = dayLightBurn;
        this.damageAble = damageAble;
        this.doesDamage = doesDamage;
        this.moveAble = moveAble;
        this.attacking = attacking;
        this.classicDrops = classicDrops;
        this.showingHealth = showingHealth;
        this.showingName = showingName;
        this.ridingMob = ridingMob;
        this.knockBackUP = knockBackUP;
        this.knockBackDOWN = knockBackDOWN;
        this.knockBackFORWARD = knockBackFORWARD;
        this.knockBackBACK = knockBackBACK;
        this.knockBackLEFT = knockBackLEFT;
        this.knockBackRIGHT = knockBackRIGHT;
        this.flying = flying;
        this.maxHight = maxHight;
        this.minHight = minHight;
        this.minLeft = minLeft;
        this.maxLeft = maxLeft;
        this.wings = wings;
        this.wingsName = wingsName;
        this.fireTime = fireTime;
        this.villagerType = villagerType;
        this.slimeSize = slimeSize;
        this.profession = profession;
        this.isBaby = isBaby;
        this.isWitherSkeleton = isWitherSkeleton;
        this.catMode = catMode;
        this.dogMode = dogMode;
        this.powered = powered;
        this.catType = catType;
        this.explosionRadius = explosionRadius;
        this.explosionDestroy = explosionDestroy;
    }

    public MobEquipment clone() {
        return new MobEquipment(this.getName(), this.getDisplayName(), this.health, this.damage, this.entityType, this.bootsDropChance, this.leggingsDropChance, this.chestPlateDropChance, this.helmetDropChance, this.weaponDropChance, this.helmet, this.chestPlate, this.leggings, this.boots, this.weapon, this.skills, this.potionEffects, this.dayLightBurn, this.damageAble, this.doesDamage, this.moveAble, this.attacking, this.classicDrops, this.showingHealth, this.showingName, this.ridingMob, this.knockBackUP, this.knockBackDOWN, this.knockBackFORWARD, this.knockBackBACK, this.knockBackLEFT, this.knockBackRIGHT, this.flying, this.maxHight, this.minHight, this.minLeft, this.maxLeft, this.wings, this.wingsName, this.fireTime, this.villagerType, this.slimeSize, this.profession, this.isBaby, this.isWitherSkeleton, this.catMode, this.dogMode, this.powered, this.catType, this.explosionRadius, this.explosionDestroy);
    }

    public double getKnockBackUP() {
        return this.knockBackUP;
    }

    public boolean isWings() {
        return this.wings;
    }

    public void setWings(boolean wings) {
        this.wings = wings;
    }

    public String getWingsName() {
        return this.wingsName;
    }

    public void setWingsName(String wingsName) {
        this.wingsName = wingsName;
    }

    public void setKnockBackUP(double knockBackUP) {
        this.knockBackUP = knockBackUP;
    }

    public double getKnockBackDOWN() {
        return this.knockBackDOWN;
    }

    public void setKnockBackDOWN(double knockBackDOWN) {
        this.knockBackDOWN = knockBackDOWN;
    }

    public double getKnockBackFORWARD() {
        return this.knockBackFORWARD;
    }

    public void setKnockBackFORWARD(double knockBackFORWARD) {
        this.knockBackFORWARD = knockBackFORWARD;
    }

    public double getKnockBackBACK() {
        return this.knockBackBACK;
    }

    public boolean isFlying() {
        return this.flying;
    }

    public void setFlying(boolean flying) {
        this.flying = flying;
    }

    public double getMaxHight() {
        return this.maxHight;
    }

    public void setMaxHight(double maxHight) {
        this.maxHight = maxHight;
    }

    public double getMinHight() {
        return this.minHight;
    }

    public void setMinHight(double minHight) {
        this.minHight = minHight;
    }

    public double getMinLeft() {
        return this.minLeft;
    }

    public void setMinLeft(double minLeft) {
        this.minLeft = minLeft;
    }

    public double getMaxLeft() {
        return this.maxLeft;
    }

    public void setMaxLeft(double maxLeft) {
        this.maxLeft = maxLeft;
    }

    public void setKnockBackBACK(double knockBackBACK) {
        this.knockBackBACK = knockBackBACK;
    }

    public double getKnockBackLEFT() {
        return this.knockBackLEFT;
    }

    public void setKnockBackLEFT(double knockBackLEFT) {
        this.knockBackLEFT = knockBackLEFT;
    }

    public double getKnockBackRIGHT() {
        return this.knockBackRIGHT;
    }

    public void setKnockBackRIGHT(double knockBackRIGHT) {
        this.knockBackRIGHT = knockBackRIGHT;
    }

    public boolean isDoesDamage() {
        return this.doesDamage;
    }

    public void setDoesDamage(boolean doesDamage) {
        this.doesDamage = doesDamage;
    }

    public boolean isMoveAble() {
        return this.moveAble;
    }

    public void setMoveAble(boolean moveAble) {
        this.moveAble = moveAble;
    }

    public boolean isAttacking() {
        return this.attacking;
    }

    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }

    public String getRidingMob() {
        return this.ridingMob;
    }

    public void setRidingMob(String ridingMob) {
        this.ridingMob = ridingMob;
    }

    public double getBootsDropChance() {
        return this.bootsDropChance;
    }

    public void setBootsDropChance(double bootsDropChance) {
        this.bootsDropChance = bootsDropChance;
    }

    public double getChestPlateDropChance() {
        return this.chestPlateDropChance;
    }

    public void setChestPlateDropChance(double chestPlateDropChance) {
        this.chestPlateDropChance = chestPlateDropChance;
    }

    public double getHelmetDropChance() {
        return this.helmetDropChance;
    }

    public void setHelmetDropChance(double helmetDropChance) {
        this.helmetDropChance = helmetDropChance;
    }

    public ItemStack getHelmet() {
        return this.helmet;
    }

    public void setHelmet(ItemStack helmet) {
        this.helmet = helmet;
    }

    public ItemStack getChestPlate() {
        return this.chestPlate;
    }

    public void setChestPlate(ItemStack chestPlate) {
        this.chestPlate = chestPlate;
    }

    public ItemStack getLeggings() {
        return this.leggings;
    }

    public void setLeggings(ItemStack leggings) {
        this.leggings = leggings;
    }

    public ItemStack getBoots() {
        return this.boots;
    }

    public void setBoots(ItemStack boots) {
        this.boots = boots;
    }

    public ItemStack getWeapon() {
        return this.weapon;
    }

    public void setWeapon(ItemStack weapon) {
        this.weapon = weapon;
    }

    public void setEntityType(EntityType entityType) {
        this.entityType = entityType;
    }

    public void setPotionEffects(PotionEffect[] potionEffects) {
        this.potionEffects = potionEffects;
    }

    public PotionEffect[] getPotionEffects() {
        return this.potionEffects;
    }

    public double getHealth() {
        return this.health;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public void addSkill(double health, Skill skill) {
        if (!this.skills.containsKey(skill.getName())) {
            this.skills.put(skill.getName(), health);
        }
    }

    public void removeSkill(String skillname) {
        if (this.skills.containsKey(skillname)) {
            // empty if block
        }
        this.skills.remove(skillname);
    }

    public void addSkill(double health, String skill) {
        if (!this.skills.containsKey(skill)) {
            this.skills.put(skill, health);
        }
    }

    public List<String> getSkills() {
        return Arrays.asList(this.skills.keySet().toArray(new String[this.skills.size()]));
    }

    public HashMap<String, Double> getSkillsAndHealth() {
        HashMap<String, Double> clone = new HashMap<String, Double>();
        for (String key : this.skills.keySet()) {
            clone.put(key, this.skills.get(key));
        }
        return clone;
    }

    public EntityType getEntityType() {
        return this.entityType;
    }

    public boolean isClassicDrops() {
        return this.classicDrops;
    }

    public void setClassicDrops(boolean classicDrops) {
        this.classicDrops = classicDrops;
    }

    public double getLeggingsDropChance() {
        return this.leggingsDropChance;
    }

    public void setLeggingsDropChance(double leggingsDropChance) {
        this.leggingsDropChance = leggingsDropChance;
    }

    public double getWeaponDropChance() {
        return this.weaponDropChance;
    }

    public void setWeaponDropChance(double weaponDropChance) {
        this.weaponDropChance = weaponDropChance;
    }

    public double getDamage() {
        return this.damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    public boolean isDayLightBurn() {
        return this.dayLightBurn;
    }

    public void setDayLightBurn(boolean dayLightBurn) {
        this.dayLightBurn = dayLightBurn;
    }

    public boolean isDamageAble() {
        return this.damageAble;
    }

    public void setDamageAble(boolean damageAble) {
        this.damageAble = damageAble;
    }

    public boolean isShowingHealth() {
        return this.showingHealth;
    }

    public void setShowingHealth(boolean showingHealth) {
        this.showingHealth = showingHealth;
    }

    public boolean isShowingName() {
        return this.showingName;
    }

    public void setShowingName(boolean showingName) {
        this.showingName = showingName;
    }

    public int getSlimeSize() {
        return this.slimeSize;
    }

    public void setSlimeSize(int slimeSize) {
        this.slimeSize = slimeSize;
    }

    public VillagerType getVillagerType() {
        return this.villagerType;
    }

    public void setVillagerType(VillagerType villagerType) {
        this.villagerType = villagerType;
    }

    public VillagerProfession getProfession() {
        return this.profession;
    }

    public void setProfession(VillagerProfession profession) {
        this.profession = profession;
    }

    public boolean isBaby() {
        return this.isBaby;
    }

    public void setBaby(boolean isBaby) {
        this.isBaby = isBaby;
    }

    public int getFireTime() {
        return this.fireTime;
    }

    public void setFireTime(int fireTime) {
        this.fireTime = fireTime;
    }

    public boolean isWitherSkeleton() {
        return this.isWitherSkeleton;
    }

    public void setWitherSkeleton(boolean isWitherSkeleton) {
        this.isWitherSkeleton = isWitherSkeleton;
    }

    public boolean isCatMode() {
        return this.catMode;
    }

    public void setCatMode(boolean catMode) {
        this.catMode = catMode;
    }

    public boolean isDogMode() {
        return this.dogMode;
    }

    public void setDogMode(boolean dogMode) {
        this.dogMode = dogMode;
    }

    public CatType getCatType() {
        return this.catType;
    }

    public void setCatType(CatType catType) {
        this.catType = catType;
    }

    public boolean isPowered() {
        return this.powered;
    }

    public void setPowered(boolean powered) {
        this.powered = powered;
    }

    public int getExplosionRadius() {
        return this.explosionRadius;
    }

    public void setExplosionRadius(int explosionRadius) {
        this.explosionRadius = explosionRadius;
    }

    public boolean isExplosionDestroy() {
        return this.explosionDestroy;
    }

    public void setExplosionDestroy(boolean explosionDestroy) {
        this.explosionDestroy = explosionDestroy;
    }
}

