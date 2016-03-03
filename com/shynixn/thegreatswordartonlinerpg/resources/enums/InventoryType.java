/*
 * Decompiled with CFR 0_110.
 */
package com.shynixn.thegreatswordartonlinerpg.resources.enums;

public enum InventoryType {
    STANDARD(1),
    MENU(1),
    SKILLS(1),
    EQUIPMENT_ARMOR_1(1),
    EQUIPMENT_ARMOR_2(2),
    EQUIPMENT_ARMOR_3(3),
    EQUIPMENT_ARMOR_4(4),
    EQUIPMENT_DROPS_1(1),
    EQUIPMENT_DROPS_2(2),
    EQUIPMENT_DROPS_3(3),
    EQUIPMENT_DROPS_4(4),
    EQUIPMENT_WEAPON_1(1),
    EQUIPMENT_WEAPON_2(2),
    EQUIPMENT_WEAPON_3(3),
    EQUIPMENT_WEAPON_4(4),
    EQUIPMENT_FOOD_1(1),
    EQUIPMENT_FOOD_2(2),
    EQUIPMENT_FOOD_3(3),
    EQUIPMENT_FOOD_4(4),
    EQUIPMENT_MATERIALS_1(1),
    EQUIPMENT_MATERIALS_2(2),
    EQUIPMENT_MATERIALS_3(3),
    EQUIPMENT_MATERIALS_4(4),
    OFFLINE(1),
    SOCIAL(1);
    
    private int page;

    private InventoryType(String page, int n2, int n3) {
        this.page = page;
    }

    public int getPage() {
        return this.page;
    }

    public static boolean isEquipMent(InventoryType type) {
        return type.name().toUpperCase().contains("EQUIPMENT");
    }

    public static boolean isEquipMentDrop(InventoryType type) {
        return type.name().toUpperCase().contains("DROPS");
    }

    public static boolean isEquipMentFood(InventoryType type) {
        return type.name().toUpperCase().contains("FOOD");
    }

    public static boolean isEquipMentMaterial(InventoryType type) {
        return type.name().toUpperCase().contains("MATERIALS");
    }

    public static boolean isEquipMentArmor(InventoryType type) {
        return type.name().toUpperCase().contains("ARMOR");
    }

    public static boolean isEquipMentWeapon(InventoryType type) {
        return type.name().toUpperCase().contains("WEAPON");
    }
}

